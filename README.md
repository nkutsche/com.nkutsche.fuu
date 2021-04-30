# File/URI/URL converter

Helper project to handle conversions of file-URL/URIs to system file paths and back.

In my XML based projects I have always the problem, that XML tools expect URI paths, but other tools (like Ant, batch/shell scripts, external java libraries) are expecting system paths. I see often in XSLT something like `replace($path, '\\', '/')` and `concat('file:/', $path)`. This works fine, as long as I don't have any special characters in my path like whitespace.

I don't like such uncomplete solutions, especially as Java is able to convert system paths easily into URIs and back - respecting the use file path separator on the current system.

This tiny library provides extension functions to convert URIs into file paths and back for:

* Ant
* XSLT
* Executable Java Class call

## Version log

### Version 0.1.1

* Makes compatible with Saxon 10.x

### Version 0.1.0

* First release

## How to use

### Ant

#### System Path to URL conversion

```
<taskdef name="fuu-make-url" classname="com.nkutsche.fuu.ant.MakeUrlTask" classpath="/path/to/fuu-xxx.jar"/>/>

<fuu-make-url path="${some.path}" property="some.url"/>
```

#### URL to System Path conversion

```
<taskdef name="fuu-make-loc" classname="com.nkutsche.fuu.ant.MakeLocationTask" classpath="/path/to/fuu-xxx.jar"/>

<fuu-make-loc url="${some.url}" property="some.path"/>
```

### XSLT

To use the Saxon extension function, you have to add or create a saxon config file:

```
<configuration edition="HE" xmlns="http://saxon.sf.net/ns/configuration">
     <resources>
          <extensionFunction>com.nkutsche.fuu.saxon.PathInfo</extensionFunction>
     </resources>
</configuration>
```

and add the fuu-xxx.jar to the classpath of your Java-Saxon call.

The extension function `fuu:path-info` (namespace = `http://nkutsche.com/fuu`) expects in its only argument an URI or a system path. It returns a map (map(xs:string, xs:string)) with the following keys:

* `file` -> `java.io.File.getAbsolutePath()`
* `uri` -> `java.net.URI.toString()`
* `url` -> `java.net.URL.toString()`

The function detects if the given string is an URI or a system path and fills the map accordingly.

```
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
java -cp "path/to/classpath/jars;path/to/fuu-xxx.jar" com.nkutsche.fuu.exec.JExecTest com.example.Main url(path\to\file.xml) file(file:/uri/to/file.xml) 
```