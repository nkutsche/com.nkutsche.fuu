<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:math="http://www.w3.org/2005/xpath-functions/math"
    xmlns:fuu="http://nkutsche.com/fuu"
    exclude-result-prefixes="xs math"
    version="3.0">
    
    <xsl:mode on-no-match="shallow-copy"/>
    
    <xsl:template match="root/*">
        <xsl:variable name="path-info" select="fuu:path-info(., ())" as="map(*)"/>
        <xsl:copy>
            <xsl:apply-templates select="@*"/>
            <xsl:attribute name="file" select="$path-info('file')"/>
            <xsl:attribute name="uri" select="$path-info('uri')"/>
            <xsl:attribute name="url" select="$path-info('url')"/>
            <xsl:attribute name="ext" select="$path-info('extension')"/>
            <xsl:apply-templates select="node()"/>
        </xsl:copy>
    </xsl:template>
    
</xsl:stylesheet>