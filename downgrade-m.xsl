<?xml version="1.0" encoding="UTF-8"?>
<!-Viewsion Style-Sheet (Downgrade - M Part)

		Version:		0.92
		Date:			29/06/2011
		Status:		Step 2
		Author:		Laurent DESQUEPER (EU)
-->
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:hl7="urn:hl7-org:v3" xmlns:mif="urn:hl7-org:v3/mif" >
	
	<!-- M.1. ICH ICSR Message Header -->
	<xsl:template match="hl7:MCCI_IN200100UV01">
		<ichicsrmessageheader>
			<messagetype><xsl:value-of select="hl7:name/@code"/></messagetype>
			<messageformatversion>2.1</messageformatversion>
			<messageformatrelease>1.0</messageformatrelease>
			<messagenumb>
				<xsl:value-of select="hl7:id/@extension"/>
			</messagenumb>
			<messagesenderidentifier>
				<xsl:value-of select="hl7:sender/hl7:device/hl7:id/@extension"/>
			</messagesenderidentifier>
			<messagereceiveridentifier>
				<xsl:value-of select="hl7:receiver/hl7:device/hl7:id/@extension"/>
			</messagereceiveridentifier>
			<xsl:call-template name="convertDate">
				<xsl:with-param name="elementName">messagedate</xsl:with-param>
				<xsl:with-param name="date-value" select="hl7:creationTime/@value"/>
				<xsl:with-param name="min-format">CCYYMMDDHHMMSS</xsl:with-param>
				<xsl:with-param name="max-format">CCYYMMDDHHMMSS</xsl:with-param>
			</xsl:call-template>
		</ichicsrmessageheader>
	</xsl:template>
</xsl:stylesheet>
