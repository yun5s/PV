<?xml version="1.0" encoding="UTF-8"?>
<!--
		Conversion Style-Sheet (Upgrade - A.5 Part)

		Version:		0.9
		Date:			21/06/2011
		Status:		Step 2
		Author:		Laurent DESQUEPER (EU)
-->
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="urn:hl7-org:v3" xmlns:mif="urn:hl7-org:v3/mif">

	<!-- Study : 
	E2B(R2): element "primarysource"
	E2B(R3): element "primaryRole"
	-->
	<xsl:template match="primarysource" mode="study">
		<xsl:if test="string-length(studyname) > 0">
			<subjectOf1 typeCode="SBJ">
				<researchStudy classCode="CLNTRL" moodCode="EVN">
					<!-- A.5.3 Sponsor Study Number -->
					<xsl:if test="string-length(sponsorstudynumb) > 0">
						<id extension="{sponsorstudynumb}" root="{$SponsorStudyNumber}"/>
					</xsl:if>
					<!-- A.5.4 Study Type in which the Reaction(s)/Event(s) were Observed -->
					<xsl:if test="string-length(observestudytype) > 0">
						<code code="{observestudytype}" codeSystem="{$oidStudyType}"/>
					</xsl:if>
					<!-- A.5.2 Study Name -->
					<xsl:if test="string-length(studyname) > 0">
						<title><xsl:value-of select="studyname"/></title>
					</xsl:if>
				</researchStudy>
			</subjectOf1>
		</xsl:if>
	</xsl:template>
	
</xsl:stylesheet>
