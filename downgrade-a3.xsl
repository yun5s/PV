<?xml version="1.0" encoding="UTF-8"?>
<!-Viewsion Style-Sheet (Downgrade - A.3 Part)
		Input : 			ICSR File compliant with E2B(R3)
		Output : 		ICSR File compliant with E2B(R2)

		Version:		0.9
		Date:			21/06/2011
		Status:		Step 2
		Author:		Laurent DESQUEPER (EU)
-->
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:hl7="urn:hl7-org:v3" xmlns:mif="urn:hl7-org:v3/mif"  exclude-result-prefixes="hl7 xsi xsl fo mif">
	
	<!--	A.3.1. Sender -->
	<xsl:template match="hl7:assignedEntity" mode="sender">
		<sendertype>
			<xsl:choose>
				<xsl:when test="hl7:code/@code = 7">6</xsl:when>
				<xsl:otherwise><xsl:value-of select="hl7:code/@code"/></xsl:otherwise>
			</xsl:choose>
		</sendertype>
		<senderorganization>
			<xsl:if test="hl7:representedOrganization/hl7:assignedEntity/hl7:representedOrganization/hl7:name/@nullFlavor = 'MSK'">PRIVACY</xsl:if>
			<xsl:call-template name="truncate">
				<xsl:with-param name="string" select="hl7:representedOrganization/hl7:assignedEntity/hl7:representedOrganization/hl7:name"/>
				<xsl:with-param name="string-length">60</xsl:with-param>
			</xsl:call-template>
		</senderorganization>
		<senderdepartment>
			<xsl:if test="hl7:representedOrganization/hl7:name/@nullFlavor = 'MSK'">PRIVACY</xsl:if>
			<xsl:call-template name="truncate">
				<xsl:with-param name="string" select="hl7:representedOrganization/hl7:name"/>
				<xsl:with-param name="string-length">60</xsl:with-param>
			</xsl:call-template>
		</senderdepartment>
		<sendertitle>
			<xsl:if test="hl7:assignedPerson/hl7:name/hl7:prefix/@nullFlavor = 'MSK'">PRIVACY</xsl:if>
			<xsl:call-template name="truncate">
				<xsl:with-param name="string" select="hl7:assignedPerson/hl7:name/hl7:prefix"/>
				<xsl:with-param name="string-length">10</xsl:with-param>
			</xsl:call-template>
		</sendertitle>
		<sendergivename>
			<xsl:if test="hl7:assignedPerson/hl7:name/hl7:given[1]/@nullFlavor = 'MSK'">PRIVACY</xsl:if>
			<xsl:call-template name="truncate">
				<xsl:with-param name="string" select="hl7:assignedPerson/hl7:name/hl7:given[1]"/>
				<xsl:with-param name="string-length">35</xsl:with-param>
			</xsl:call-template>
		</sendergivename>
		<sendermiddlename>
			<xsl:if test="hl7:assignedPerson/hl7:name/hl7:given[2]/@nullFlavor = 'MSK'">PRIVACY</xsl:if>
			<xsl:call-template name="truncate">
				<xsl:with-param name="string" select="hl7:assignedPerson/hl7:name/hl7:given[2]"/>
				<xsl:with-param name="string-length">15</xsl:with-param>
			</xsl:call-template>
		</sendermiddlename>
		<senderfamilyname>
			<xsl:if test="hl7:assignedPerson/hl7:name/hl7:family/@nullFlavor = 'MSK'">PRIVACY</xsl:if>
			<xsl:call-template name="truncate">
				<xsl:with-param name="string" select="hl7:assignedPerson/hl7:name/hl7:family"/>
				<xsl:with-param name="string-length">35</xsl:with-param>
			</xsl:call-template>
		</senderfamilyname>
		<senderstreetaddress>
			<xsl:if test="hl7:addr/hl7:streetAddressLine/@nullFlavor = 'MSK'">PRIVACY</xsl:if>
			<xsl:value-of select="hl7:addr/hl7:streetAddressLine"/>
		</senderstreetaddress>
		<sendercity>
			<xsl:if test="hl7:addr/hl7:city/@nullFlavor = 'MSK'">PRIVACY</xsl:if>
			<xsl:value-of select="hl7:addr/hl7:city"/>
		</sendercity>
		<senderstate>
			<xsl:if test="hl7:addr/hl7:state/@nullFlavor = 'MSK'">PRIVACY</xsl:if>
			<xsl:value-of select="hl7:addr/hl7:state"/>
		</senderstate>
		<senderpostcode>
			<xsl:if test="hl7:addr/hl7:postalCode/@nullFlavor = 'MSK'">PRIVACY</xsl:if>
			<xsl:value-of select="hl7:addr/hl7:postalCode"/>
		</senderpostcode>
		<sendercountrycode>
			<xsl:value-of select="hl7:assignedPerson/hl7:asLocatedEntity/hl7:location/hl7:code/@code"/>
		</sendercountrycode>
		<sendertel>
			<xsl:call-template name="telecom">
				<xsl:with-param name="type">tel:</xsl:with-param>
				<xsl:with-param name="len">10</xsl:with-param>
			</xsl:call-template>
		</sendertel>
		<senderfax>
			<xsl:call-template name="telecom">
				<xsl:with-param name="type">fax:</xsl:with-param>
				<xsl:with-param name="len">10</xsl:with-param>
			</xsl:call-template>
		</senderfax>
		<senderemailaddress>
			<xsl:call-template name="telecom">
				<xsl:with-param name="type">mailto:</xsl:with-param>
				<xsl:with-param name="len">100</xsl:with-param>
			</xsl:call-template>
		</senderemailaddress>
	</xsl:template>
	
	<xsl:template name="telecom">
		<xsl:param name="type"/>
		<xsl:param name="len"/>
		<xsl:value-of select="substring(substring-after(hl7:telecom[starts-with(@value, $type)]/@value, $type), 1, $len)"/>
	</xsl:template>
	
</xsl:stylesheet>
