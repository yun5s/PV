<?xml version="1.0" encoding="UTF-8"?>
<!--
		Conversion Style-Sheet (Upgrade - A.4 Part)

		Version:		0.9
		Date:			21/06/2011
		Status:		Step 2
		Author:		Laurent DESQUEPER (EU)
-->
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="urn:hl7-org:v3" xmlns:mif="urn:hl7-org:v3/mif">

	<!-- Literature reference : 
	E2B(R2): element "literaturereference"
	E2B(R3): element "reference"
	-->
	<xsl:template match="literaturereference">
		<xsl:if test="string-length(.) > 0">
			<reference typeCode="REFR">
				<!-- A.4.r Literature Reference(s) -->
				<document classCode="DOC" moodCode="EVN">
					<bibliographicDesignationText><xsl:value-of select="."/></bibliographicDesignationText>
				</document>
			</reference>
		</xsl:if>
	</xsl:template>
	
</xsl:stylesheet>
