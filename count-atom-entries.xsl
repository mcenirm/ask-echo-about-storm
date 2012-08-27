<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
      xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
      xmlns:atom="http://www.w3.org/2005/Atom"
    >
<xsl:output method='text'/>

<xsl:template match='/'>
  <xsl:value-of select="count(//atom:entry)"/>
  <xsl:text>&#10;</xsl:text>
</xsl:template>

</xsl:stylesheet> 

