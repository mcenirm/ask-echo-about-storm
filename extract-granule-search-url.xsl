<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
      xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
      xmlns:atom="http://www.w3.org/2005/Atom"
      xmlns:echo="http://www.echo.nasa.gov/esip"
    >
<xsl:output method='text'/>

<xsl:template match='/'>
  <xsl:value-of select='//atom:entry[1]/atom:link[@hreflang="en-US"][@type="application/atom+xml"][@rel="http://esipfed.org/ns/fedsearch/1.0/search#"][@title="Search for granules"]/@href'/>
  <xsl:text>&#10;</xsl:text>
</xsl:template>

</xsl:stylesheet> 

