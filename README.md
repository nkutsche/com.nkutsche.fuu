# File/URI/URL converter

Helper project to handle conversions of file-URL/URIs to system file paths and back.

In my XML based projects I have always the problem, that XML tools expect URI paths, but other tools (like Ant, batch/shell scripts, external java libraries) are expecting system paths. I see often in XSLT something like `replace($path, '\\', '/')` and `concat('file:/', $path)`. This works fine, as long as I don't have any special characters in my path like whitespace.

I don't like such incomplete solutions, especially as Java is able to convert system paths easily into URIs and back - respecting the use file path separator on the current system.

This tiny library provides extension functions to convert URIs into file paths and back for:

* Ant
* XSLT
* Executable Java Class call

## Version log

### Version 0.1.1

* Makes XSLT extension function compatible with Saxon 10.x
* Update components
    * Ant
    * JUnit

### Version 0.1.0

* First release

## How to use

### Ant

#### System Path to URL conversion

```xml
<taskdef name="fuu-make-url" classname="com.nkutsche.fuu.ant.MakeUrlTask" classpath="/path/to/fuu.ant-xxx.jar;/path/to/fuu.core-xxx.jar"/>

<fuu-make-url path="${some.path}" property="some.url"/>
```

#### URL to System Path conversion

```xml
<taskdef name="fuu-make-loc" classname="com.nkutsche.fuu.ant.MakeLocationTask" classpath="/path/to/fuu.ant-xxx.jar;/path/to/fuu.core-xxx.jar"/>

<fuu-make-loc url="${some.url}" property="some.path"/>
```

### XSLT

To use the Saxon extension function, you have to add or create a saxon config file:

```xml
<configuration edition="HE" xmlns="http://saxon.sf.net/ns/configuration">
     <resources>
          <extensionFunction>com.nkutsche.fuu.saxon.PathInfo</extensionFunction>
     </resources>
</configuration>
```

and add the fuu.core-xxx.jar and one of fuu.saxon99-xxx.jar or fuu.saxon100-xxx.jar to the classpath of your Java-Saxon call. Use the 99-variant if you run your stylesheet on a Saxon 9.9.x If you run it on a Saxon 10.x use the 100-variant.

The extension function `fuu:path-info` (namespace = `http://nkutsche.com/fuu`) expects in its only argument an URI or a system path. It returns a map (map(xs:string, xs:string)) with the following keys:

* `file` -> `java.io.File.getAbsolutePath()`
* `uri` -> `java.net.URI.toString()`
* `url` -> `java.net.URL.toString()`

The function detects if the given string is an URI or a system path and fills the map accordingly.

```xml
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:math="http://www.w3.org/2005/xpath-functions/math"
    xmlns:fuu="http://nkutsche.com/fuu"
    exclude-result-prefixes="xs math"
    version="3.0">
    
    
    <xsl:template match="root/*">
        <xsl:variable name="path-info" select="fuu:path-info(., ())" as="map(*)"/>
        <xsl:copy>
            <xsl:apply-templates select="@*"/>
            <xsl:attribute name="file" select="$path-info('file')"/>
            <xsl:attribute name="uri" select="$path-info('uri')"/>
            <xsl:attribute name="url" select="$path-info('url')"/>
            <xsl:apply-templates select="node()"/>
        </xsl:copy>
    </xsl:template>
    
</xsl:stylesheet>
```

### Executable Java Class call

The given example calls the class `com.example.Main` with two arguments, both are file system paths:

```
java -cp "path/to/classpath/jars" com.example.Main path\to\file.xml file:/uri/to/file.xml 
```

To convert them into URIs bevor the Main class is called, you can do the following:

```
java -cp "path/to/classpath/jars;path/to/fuu.core-xxx.jar" com.nkutsche.fuu.exec.JExecTest com.example.Main url(path\to\file.xml) file(file:/uri/to/file.xml) 
```

### Maven

To add one of the modules to your Maven project you have to add the following to your pom.xml:

```xml
<repositories>
    <repository>
        <id>d2tnexus</id>
        <url>https://repo.data2type.de/repository/maven-public/</url>
    </repository>
</repositories>
```

Then you can add one of modules as dependencies to your project:

| Module artifactId | Description |
|---|---|
| `fuu.core` | For the executable Java class  |
| `fuu.ant` | For the custom Ant step |
| `fuu.saxon99` | For the Saxon extension functions running with Saxon 9.9.x  |
| `fuu.saxon100` | For the Saxon extension functions running with Saxon 10.x  |
 
Add the dependency like this to your pom.xml:

```xml

<dependency>
  <groupId>com.nkutsche</groupId>
  <artifactId>{module-artifactId}</artifactId>
  <version>{fuu-version}</version>
</dependency>
```
