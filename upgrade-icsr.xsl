<?xml version="1.0" encoding="UTF-8"?>
<!-Viewsion Style-Sheet (Upgrade)
		Input : 			ICSR File compliant with E2B(R2)
		Output : 		ICSR File compliant with E2B(R3)

		Version:		0.9
		Date:			21/06/2011
		Status:		Step 2
		Author:		Laurent DESQUEPER (EU)
-->
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="urn:hl7-org:v3" xmlns:mif="urn:hl7-org:v3/mif">

	<xsl:include href="upgrade.xsl"/>
	<xsl:include href="upgrade-m.xsl"/>
	<xsl:include href="upgrade-a1.xsl"/>
	
	<xsl:output indent="yes" method="xml" omit-xml-declaration="no" encoding="utf-8"/>
	<xsl:strip-space elements="*"/>
	
	<!-- ICH ICSviewsion of the main structure incl. root element and controlActProcess
	E2B(R2): root element "ichicsr"
	E2B(R3): root element "PORR_IN049016UV"
	-->
	<xsl:template match="/">
		<MCCI_IN200100UV01 ITSVersion="XML_1.0">
			<xsl:attribute name="xsi:schemaLocation">urn:hl7-org:v3 ./multicacheschemas/MCCI_IN200100UV01.xsd</xsl:attribute>
			<!-- M.x - Message Header -->
			<xsl:apply-templates select="/ichicsr/ichicsrmessageheader" mode="part-a"/>
			<!-- Report -->
			<xsl:apply-templates select="/ichicsr/safetyreport" mode="report"/>
			<!-- M.x - Message Footer -->
			<xsl:apply-templates select="/ichicsr/ichicsrmessageheader" mode="part-c"/>
		</MCCI_IN200100UV01>
	</xsl:template>
	
	<xsl:template match="safetyreport" mode="report">
		<PORR_IN049016UV>
			<!-- M.2.r.4 - Message Number-->
			<id extension="{safetyreportid}" root="{$oidMessageNumber}"/>
			<xsl:apply-templates select="../ichicsrmessageheader" mode="part-b"/>
			<controlActProcess classCode="CACT" moodCode="EVN">
				<code code="PORR_TE049016UV" codeSystem="2.16.840.1.113883.1.18"/>
				<!-- A.1.3 - Date of Transmission -->
				<xsl:apply-templates select="transmissiondate"/>
				<!-- A.1.x - Safety Report -->
				<xsl:apply-templates select="." mode="main"/>
			</controlActProcess>
		</PORR_IN049016UV>
	</xsl:template>
	
</xsl:stylesheet>
