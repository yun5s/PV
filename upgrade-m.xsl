<?xml version="1.0" encoding="UTF-8"?>
<!--Viewsion Style-Sheet (Upgrade - M Part)

		Version:		0.9
		Date:			21/06/2011
		Status:		Step 2
		Author:		Laurent DESQUEPER (EU)
-->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="urn:hl7-org:v3" xmlns:mif="urn:hl7-org:v3/mif">

	<!-- Batch Header : M.1.1, M.1.4 and M.1.7 -->
	<xsl:template match="ichicsrmessageheader" mode="part-a">
		<id extension="{messagenumb}" root="{$oidBatchNumber}"/>										<!-- M.1.4	- Batch Number-->
		<creationTime value="{messagedate}"/>																		<!-- M.1.7 - Date of Batch Transmission -->
		<!-- Mandatory element -->
		<responseModeCode code="D"/>
		<interactionId root="2.16.840.1.113883.1.6" extension="MCCI_IN200100UV01"/>
		<name code="{messagetype}" codeSystem="{$oidMessageType}"/>							<!-- M.1.1 - Message Type in Batch -->
	</xsl:template>

	<!-- Date of this transmission -->
	<xsl:template match="safetyreport" mode="header">
		<xsl:variable name="version-number" select="safetyreportversion"/>
		<xsl:choose>
			<xsl:when test="string-length($version-number) = 0"><creationTime value="{transmissiondate}000000"/></xsl:when>
			<xsl:when test="string-length($version-number) = 1"><creationTime value="{transmissiondate}00000{$version-number}"/></xsl:when>
			<xsl:when test="string-length($version-number) = 2"><creationTime value="{transmissiondate}0000{$version-number}"/></xsl:when>
			<xsl:otherwise><creationTime value="{transmissiondate}000000"/></xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- Message Header : M.2.r.5 and M.2.r.6 -->
	<xsl:template match="ichicsrmessageheader" mode="part-b">
		<!-- M.2.r.7 - Message Date -->
		<xsl:apply-templates select="../safetyreport[1]" mode="header"/>
		<interactionId root="2.16.840.1.113883.1.6" extension="MCCI_IN200100UV01"/>
		<processingCode code="P"/>
		<processingModeCode code="T"/>
		<acceptAckCode code="AL"/>
		<receiver typeCode="RCV">
			<device classCode="DEV" determinerCode="INSTANCE">
				<id extension="{messagereceiveridentifier}" root="{$oidMessageReceiverId}"/>
			</device>
		</receiver>
		<sender typeCode="SND">
			<device classCode="DEV" determinerCode="INSTANCE">
				<id extension="{messagesenderidentifier}" root="{$oidMessageSenderId}"/>
			</device>
		</sender>
	</xsl:template>

	<!-- Batch Footer : M.1.5 and M.1.6 -->
	<xsl:template match="ichicsrmessageheader" mode="part-c">
		<receiver typeCode="RCV">
			<device classCode="DEV" determinerCode="INSTANCE">
				<id extension="{messagereceiveridentifier}" root="{$oidBatchReceiverId}"/>
			</device>
		</receiver>
		<sender typeCode="SND">
			<device classCode="DEV" determinerCode="INSTANCE">
				<id extension="{messagesenderidentifier}" root="{$oidBatchSenderId}"/>
			</device>
		</sender>
	</xsl:template>

</xsl:stylesheet>
