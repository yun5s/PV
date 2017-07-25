<?xml version="1.0" encoding="UTF-8"?>
<!-Viewsion Style-Sheet (Downgrade - B.4 Part)
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
	
	<!--	B.4. Drug(s) Information	-->
	<xsl:template name="Drug" >
		<xsl:for-each select="hl7:component/hl7:adverseEventAssessment/hl7:subject1/hl7:primaryRole/hl7:subjectOf2/hl7:organizer[hl7:code/@code=$DrugInformation]/hl7:component/hl7:substanceAdministration">
			<xsl:choose>
				<!-- Drug with dosage -->
				<xsl:when test="count(hl7:outboundRelationship2[@typeCode='COMP'])>0">
					<xsl:for-each select="hl7:outboundRelationship2[@typeCode='COMP']">
						<xsl:apply-templates select=".." mode="drug-tag">
							<xsl:with-param name="DosageNum"><xsl:value-of select="position()" /></xsl:with-param>
						</xsl:apply-templates>
					</xsl:for-each>
				</xsl:when>
				<!-- Drug without dosage -->
				<xsl:otherwise>
					<xsl:apply-templates select="." mode="drug-tag">
					</xsl:apply-templates>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>		
	</xsl:template>	
	
	<xsl:template match="hl7:substanceAdministration"  mode="drug-tag">
		<xsl:param name="DosageNum">0</xsl:param>
		<xsl:variable name="DrugId" select="hl7:id/@extension"/>
		<drug>
			<!-- B.4.k.1. Characterization of drug role -->
			<xsl:if test="../../../../../../hl7:component/hl7:causalityAssessment[hl7:code/@code=$InterventionCharacterization and hl7:subject2/hl7:productUseReference/hl7:id/@extension=$DrugId]/hl7:value/@code != 4">
				<drugcharacterization>
					<xsl:value-of select="../../../../../../hl7:component/hl7:causalityAssessment[hl7:code/@code=$InterventionCharacterization and hl7:subject2/hl7:productUseReference/hl7:id/@extension=$DrugId]/hl7:value/@code"/>
				</drugcharacterization>
			</xsl:if>
			<!-- B.4.k.2.0; B.4.k.2.1; B.4.k.2.2; B.4.k.2.4 -->
			<xsl:apply-templates select="hl7:consumable/hl7:instanceOfKind" mode="drug-manufactured-product"/>
			<!-- B.4.k.4.r.9 Batch/lot number -->
			<xsl:for-each select="hl7:outboundRelationship2/hl7:substanceAdministration" >
				<xsl:if test="position() = $DosageNum">
					<xsl:apply-templates select="." mode="drug-batch-number"/>
				</xsl:if>
			</xsl:for-each>
			<!-- B.4.k.3 Holder and authorization/application number of drug -->
			<xsl:apply-templates select="hl7:consumable/hl7:instanceOfKind/hl7:kindOfProduct/hl7:asManufacturedProduct" mode="drug-holder"/>
			<!-- B.4.k.4.r1 - 5 Dosage Information -->
			<xsl:for-each select="hl7:outboundRelationship2/hl7:substanceAdministration" >
				<xsl:if test="position() = $DosageNum">
					<xsl:apply-templates select="." mode="drug-dosage-information1"/>
				</xsl:if>
			</xsl:for-each>
			<!-- B.4.k.5 cumulative dose to the reaction/event -->
			<xsl:apply-templates select="hl7:outboundRelationship2[@typeCode='SUMM']/hl7:observation[hl7:code/@code=$CumulativeDoseToReaction]" mode="drug-cumulative-dosage"/>
			<!-- B.4.k.4.r.10 - 13 Dosage Information -->
			<xsl:for-each select="hl7:outboundRelationship2/hl7:substanceAdministration" >
				<xsl:if test="position() = $DosageNum">
					<xsl:apply-templates select="." mode="drug-dosage-information2"/>
				</xsl:if>
			</xsl:for-each>
			<!-- B.4.k.6 Gestation period at time of exposure -->
			<xsl:apply-templates select="hl7:outboundRelationship2/hl7:observation[hl7:code/@code=$GestationPeriod]" mode="reaction-gestation-period"/>
			<!-- B.4.k.7.r.1 Indication for use in the case from primary source -->
			<xsl:for-each select="hl7:inboundRelationship/hl7:observation[hl7:code/@code=$Indication and hl7:performer/hl7:assignedEntity/hl7:code/@code=$SourceReporter]" >
				<xsl:if test="position()=1">
					<xsl:apply-templates select="." mode="drug-indication"/>
				</xsl:if>
			</xsl:for-each>
			<!-- B.4.k.4.r.6 Date of start of drug -->
			<xsl:for-each select="hl7:outboundRelationship2[@typeCode='COMP']/hl7:substanceAdministration" >
				<xsl:if test="position() = $DosageNum">
					<xsl:apply-templates select="." mode="drug-start-date"/>
				</xsl:if>
			</xsl:for-each>
			<!-- B.4.k.9.r.3.1; B.4.k.9.r.3.2 - Time interval between beginning  of drug administration and start/end of reaction/event -->
			<xsl:for-each select="hl7:outboundRelationship1[@typeCode='SAS']">
				<xsl:if test="position()=1">
					<xsl:call-template name="drug-period-sas"/>
				</xsl:if>
			</xsl:for-each>
			<xsl:for-each select="hl7:outboundRelationship1[@typeCode='SAE']">
				<xsl:if test="position()=1">
					<xsl:call-template name="drug-period-sae"/>
				</xsl:if>
			</xsl:for-each>
			<!-- B.4.k.4.r.7; B.4.k.4.r.8 - Date of last/ Duration of drug administration -->
			<xsl:for-each select="hl7:outboundRelationship2[@typeCode='COMP']/hl7:substanceAdministration" >
				<xsl:if test="position() = $DosageNum">
					<xsl:apply-templates select="." mode="drug-end-date"/>
				</xsl:if>
			</xsl:for-each>
			<!-- B.4.k.8 Action(s) taken with drug -->
			<actiondrug>
				<xsl:choose>
					<xsl:when test="hl7:inboundRelationship/hl7:act/hl7:code/@code = 0">5</xsl:when>
					<xsl:when test="hl7:inboundRelationship/hl7:act/hl7:code/@code = 9">6</xsl:when>
					<xsl:otherwise><xsl:value-of select="hl7:inboundRelationship/hl7:act/hl7:code/@code"/></xsl:otherwise>
				</xsl:choose>
			</actiondrug>
			<!-- B.4.k.9 Recurrance of reaction -->
			<drugrecurreadministration>
				<xsl:choose>
					<xsl:when test="count(hl7:outboundRelationship2/hl7:observation[hl7:code/@code=$RecurranceOfReaction and hl7:value/@code = 1]) > 0">
						<xsl:text>1</xsl:text>
					</xsl:when>
					<xsl:when test="count(hl7:outboundRelationship2/hl7:observation[hl7:code/@code=$RecurranceOfReaction and hl7:value/@code = 2]) > 0">
						<xsl:text>2</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>3</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</drugrecurreadministration>
			<!-- B.4.k.10 Additional information on drug -->
			<drugadditional>
				<xsl:variable name="drug-additional">
					<xsl:apply-templates select="." mode="drug-additional">
						<xsl:with-param name="DrugId" select="$DrugId"/>
					</xsl:apply-templates>
				</xsl:variable>
				<xsl:call-template name="truncate">
					<xsl:with-param name="string"><xsl:value-of select="$drug-additional"/></xsl:with-param>
					<xsl:with-param name="string-length">1000</xsl:with-param>
				</xsl:call-template>
			</drugadditional>
			<!-- B.4.k.2.3.r.1 Active Ingredient name -->
			<xsl:apply-templates select="hl7:consumable/hl7:instanceOfKind/hl7:kindOfProduct/hl7:ingredient/hl7:ingredientSubstance" mode="active-substance-name"/>
			<!-- B.4.k.9.r.4 Did reaction recur on readministration? -->
			<xsl:apply-templates select="hl7:outboundRelationship2/hl7:observation[hl7:code/@code=$RecurranceOfReaction]" mode="drug-recurrence"/>
			<!-- B.4.k.9.r.2.r Relatedness of drug to reaction(s)/event(s) -->
			<xsl:apply-templates select="../../../../../../hl7:component/hl7:causalityAssessment[hl7:code/@code = $Causality and hl7:subject2/hl7:productUseReference/hl7:id/@extension = $DrugId]" mode="drug-reaction"/>
		</drug>
	</xsl:template>
	
	<!-- B.4.k.10 Additional information on drug -->
	<xsl:template match="hl7:substanceAdministration" mode="drug-additional">
		<xsl:param name="DrugId"/>
		<xsl:apply-templates select="hl7:consumable/hl7:instanceOfKind/hl7:kindOfProduct/hl7:ingredient" mode="drug-additional1"/>
		<xsl:variable name="AdditionalInfo">
			<xsl:if test="../../../../../../hl7:component/hl7:causalityAssessment[hl7:code/@code=$InterventionCharacterization and hl7:subject2/hl7:productUseReference/hl7:id/@extension=$DrugId]/hl7:value/@code = 4">
				DRUG NOT ADMINISTERED<xsl:text>, </xsl:text>
			</xsl:if>
			<xsl:if test="hl7:outboundRelationship2/hl7:observation[hl7:code/@code=$Blinded]/hl7:value/@value = 'true'">INVESTIGATIONAL<xsl:if test="count(hl7:outboundRelationship2/hl7:observation[hl7:code/@code=$CodedDrugInformation]) + count(hl7:outboundRelationship2/hl7:observation[hl7:code/@code=$AdditionalInformation]) > 0"><xsl:text>, </xsl:text></xsl:if>
			</xsl:if>
			<xsl:for-each select="hl7:outboundRelationship2/hl7:substanceAdministration/hl7:routeCode[@codeSystem != $oidICHRoute]">
				; ROUTE : <xsl:value-of select="@code"/> <xsl:if test="hl7:originalText"> [<xsl:value-of select="hl7:originalText"/>]</xsl:if>
				(<xsl:value-of select="@codeSystem"/> ; <xsl:value-of select="@codeSystemVersion"/>)
			</xsl:for-each>
			<xsl:for-each select="hl7:outboundRelationship2/hl7:substanceAdministration/hl7:inboundRelationship/hl7:observation[hl7:code/@code = $ParentRouteOfAdministration]/hl7:value[@codeSystem != $oidICHRoute]">
				; PARENT ROUTE : <xsl:value-of select="@code"/> <xsl:if test="hl7:originalText"> [<xsl:value-of select="hl7:originalText"/>]</xsl:if>
				(<xsl:value-of select="@codeSystem"/> ; <xsl:value-of select="@codeSystemVersion"/>)
			</xsl:for-each>
			
			<xsl:apply-templates select="hl7:outboundRelationship2/hl7:observation[hl7:code/@code=$CodedDrugInformation]" mode="drug-additional2"/>
			<xsl:apply-templates select="hl7:outboundRelationship2/hl7:observation[hl7:code/@code=$AdditionalInformation]" mode="drug-additional3"/>
		</xsl:variable>
		<xsl:if test="string-length($AdditionalInfo) > 0">
			<xsl:text>Additional info: </xsl:text>
			<xsl:value-of select="$AdditionalInfo" />
		</xsl:if>
	</xsl:template>
	
	<!-- B.4.k.2.0; B.4.k.2.1; B.4.k.2.2; B.4.k.2.4 -->
	<xsl:template match="hl7:instanceOfKind" mode="drug-manufactured-product">
		<medicinalproduct>
			<xsl:variable name="medicinalProduct" >
				<xsl:call-template name="MedicinalProduct" />
			</xsl:variable>
			<xsl:call-template name="truncate">
				<xsl:with-param name="string">
					<xsl:value-of select="$medicinalProduct"/>
				</xsl:with-param>
				<xsl:with-param name="string-length">70</xsl:with-param>
			</xsl:call-template>
		</medicinalproduct>
		<obtaindrugcountry>
			<xsl:value-of select="hl7:subjectOf/hl7:productEvent[hl7:code/@code=$RetailSupply]/hl7:performer/hl7:assignedEntity/hl7:representedOrganization/hl7:addr/hl7:country"/>
		</obtaindrugcountry>
	</xsl:template>
		
	<!-- B.4.k.4.r.9 Batch/lot number -->
	<xsl:template match="hl7:substanceAdministration" mode="drug-batch-number">
		<drugbatchnumb>
			<xsl:value-of select="hl7:consumable/hl7:instanceOfKind/hl7:productInstanceInstance/hl7:lotNumberText"/>
		</drugbatchnumb>
	</xsl:template>
	
	<!-- B.4.k.3 Holder and authorization/application number of drug -->
	<xsl:template match="hl7:asManufacturedProduct" mode="drug-holder">		
		<drugauthorizationnumb>
			<xsl:value-of select="hl7:subjectOf/hl7:approval/hl7:id/@extension"/>
		</drugauthorizationnumb>
		<drugauthorizationcountry>
			<xsl:value-of select="hl7:subjectOf/hl7:approval/hl7:author/hl7:territorialAuthority/hl7:territory/hl7:code/@code"/>
		</drugauthorizationcountry>
		<drugauthorizationholder>
			<xsl:value-of select="hl7:subjectOf/hl7:approval/hl7:holder/hl7:role/hl7:playingOrganization/hl7:name"/>
		</drugauthorizationholder>
	</xsl:template>
	
	<!-- B.4.k.2.2 Medicinal product name as reported by the primary source -->
	<xsl:template name="MedicinalProduct">
		<xsl:if test="hl7:kindOfProduct/hl7:code/@codeSystem=$MPID">
			<xsl:text>MPID: </xsl:text>
		</xsl:if>
		<xsl:if test="hl7:kindOfProduct/hl7:code/@codeSystem=$PhPID">
			<xsl:text>PhPID: </xsl:text>
		</xsl:if>
		<xsl:value-of select="hl7:kindOfProduct/hl7:code/@code"/>
		<xsl:text> (</xsl:text>
		<xsl:value-of select="hl7:kindOfProduct/hl7:code/@codeSystemVersion"/>
		<xsl:text>): </xsl:text>
		<xsl:value-of select="hl7:kindOfProduct/hl7:name"/>
	</xsl:template>
	
	<!-- B.4.k.2.3.r.1 Active Ingredient name -->
	<xsl:template match="hl7:ingredientSubstance" mode="active-substance-name">
		<activesubstance>
			<activesubstancename>
				<xsl:variable name="activeIngredient" >
					<xsl:call-template name="ActiveIngredient" />
				</xsl:variable>
				<xsl:call-template name="truncate">
					<xsl:with-param name="string">
						<xsl:value-of select="$activeIngredient"/>
					</xsl:with-param>
					<xsl:with-param name="string-length">100</xsl:with-param>
				</xsl:call-template>
			</activesubstancename>
		</activesubstance>
	</xsl:template>
	
	<!-- B.4.k.2.3.r.1 Active Ingredient name -->
	<xsl:template name="ActiveIngredient">
		<xsl:if test="string-length(hl7:code/@code) > 0">
			<xsl:text>TERMID: </xsl:text>
			<xsl:value-of select="hl7:code/@code"/>
			<xsl:text> (</xsl:text>
			<xsl:value-of select="hl7:code/@codeSystemVersion"/>
			<xsl:text>): </xsl:text>
		</xsl:if>
		<xsl:value-of select="hl7:name"/>
	</xsl:template>
	
	<!-- B.4.k.4.r1 - 5 Dosage Information -->
	<xsl:template match="hl7:substanceAdministration" mode="drug-dosage-information1">
		<drugstructuredosagenumb>
			<xsl:value-of select="hl7:doseQuantity/@value"/>
		</drugstructuredosagenumb>
		<drugstructuredosageunit>
			<xsl:call-template name="getMapping">
				<xsl:with-param name="type">UCUM</xsl:with-param>
				<xsl:with-param name="code" select="hl7:doseQuantity/@unit"/>
			</xsl:call-template>
		</drugstructuredosageunit>
		<drugseparatedosagenumb/>
		<xsl:if test="string-length(hl7:effectiveTime//hl7:period/@value) &lt; 4 and not(contains(hl7:effectiveTime//hl7:period/@unit, '{'))">
			<drugintervaldosageunitnumb>
				<xsl:value-of select="hl7:effectiveTime//hl7:period/@value"/>
			</drugintervaldosageunitnumb>
			<drugintervaldosagedefinition>
				<xsl:call-template name="getMapping">
					<xsl:with-param name="type">UCUM</xsl:with-param>
					<xsl:with-param name="code" select="hl7:effectiveTime//hl7:period/@unit"/>
				</xsl:call-template>
			</drugintervaldosagedefinition>
		</xsl:if>
	</xsl:template>
	
	<!-- B.4.k.5 cumulative dose to the reaction/event -->
	<xsl:template match="hl7:observation" mode="drug-cumulative-dosage">
		<drugcumulativedosagenumb>
			<xsl:value-of select="hl7:value/@value"/>
		</drugcumulativedosagenumb>
		<drugcumulativedosageunit>
			<xsl:call-template name="getMapping">
				<xsl:with-param name="type">UCUM</xsl:with-param>
				<xsl:with-param name="code" select="hl7:value/@unit"/>
			</xsl:call-template>
		</drugcumulativedosageunit>
	</xsl:template>
	
	<!-- B.4.k.4.r.10 - 13 Dosage Information -->
	<xsl:template match="hl7:substanceAdministration" mode="drug-dosage-information2">
		<drugdosagetext>
			<xsl:call-template name="truncate">
				<xsl:with-param name="string">
					<!-- Dosage text -->
					<xsl:value-of select="hl7:text"/>
					<!-- Case of time interval of 4N -->
					<xsl:if test="string-length(hl7:effectiveTime//hl7:period/@value) > 3">
						<xsl:if test="string-length(hl7:text) > 0"><xsl:text> ; </xsl:text></xsl:if>Time Interval: <xsl:value-of select="hl7:effectiveTime/hl7:comp/hl7:period/@value"/><xsl:text> </xsl:text><xsl:value-of select="hl7:effectiveTime/hl7:comp/hl7:period/@unit"/>
					</xsl:if>
					<!-- Case of CYCLICAL dosage -->
					<xsl:if test="hl7:effectiveTime//hl7:period/@unit = '{Cyclical}'">
						<xsl:if test="string-length(hl7:text) > 0"><xsl:text> ; </xsl:text></xsl:if>CYCLICAL
					</xsl:if>
					<!-- Case of AS NECESSARY dosage -->
					<xsl:if test="hl7:effectiveTime//hl7:period/@unit = '{AsNecessary}'">
						<xsl:if test="string-length(hl7:text) > 0"><xsl:text> ; </xsl:text></xsl:if>AS NECESSARY
					</xsl:if>
					<!-- Case of TOTAL dosage -->
					<xsl:if test="hl7:effectiveTime//hl7:period/@unit = '{Total}'">
						<xsl:if test="string-length(hl7:text) > 0"><xsl:text> ; </xsl:text></xsl:if>IN TOTAL
					</xsl:if>
				</xsl:with-param>
				<xsl:with-param name="string-length">100</xsl:with-param>
			</xsl:call-template>
		</drugdosagetext>
		<drugdosageform>
			<xsl:value-of select="hl7:consumable/hl7:instanceOfKind/hl7:kindOfProduct/hl7:formCode/hl7:originalText"/>
			<xsl:if test="string-length(hl7:consumable/hl7:instanceOfKind/hl7:kindOfProduct/hl7:formCode/hl7:originalText) = 0 and string-length(hl7:consumable/hl7:instanceOfKind/hl7:kindOfProduct/hl7:formCode/@code) > 0"><xsl:value-of select="hl7:consumable/hl7:instanceOfKind/hl7:kindOfProduct/hl7:formCode/@code"/> (<xsl:value-of select="hl7:consumable/hl7:instanceOfKind/hl7:kindOfProduct/hl7:formCode/@codeSystemVersion"/>)</xsl:if>
		</drugdosageform>
		<xsl:if test="string-length(hl7:routeCode/@code) + string-length(hl7:routeCode/originalText) > 0">
			<drugadministrationroute>
				<xsl:choose>
					<xsl:when test="hl7:routeCode/@codeSystem = $oidICHRoute">
						<xsl:value-of select="hl7:routeCode/@code"/>
					</xsl:when>
					<xsl:otherwise>050</xsl:otherwise>
				</xsl:choose>
			</drugadministrationroute>
		</xsl:if>
		<xsl:if test="string-length(hl7:inboundRelationship/hl7:observation[hl7:code/@code=$ParentRouteOfAdministration]/hl7:value/@code) + string-length(hl7:inboundRelationship/hl7:observation[hl7:code/@code=$ParentRouteOfAdministration]/hl7:value/orignalText) > 0">
			<drugparadministration>
				<xsl:choose>
					<xsl:when test="hl7:inboundRelationship/hl7:observation[hl7:code/@code=$ParentRouteOfAdministration]/hl7:value/@codeSystem = $oidICHRoute">
						<xsl:value-of select="hl7:inboundRelationship/hl7:observation[hl7:code/@code=$ParentRouteOfAdministration]/hl7:value/@code"/>
					</xsl:when>
					<xsl:otherwise>050</xsl:otherwise>
				</xsl:choose>
			</drugparadministration>
		</xsl:if>
	</xsl:template>
	
	<!-- B.4.k.6 Gestation period at time of exposure -->
	<xsl:template match="hl7:observation" mode="reaction-gestation-period">
		<reactiongestationperiod>
			<xsl:choose>
				<xsl:when test="hl7:value/@xsi:type = 'PQ'"><xsl:value-of select="hl7:value/@value"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="hl7:value/@code"/></xsl:otherwise>
			</xsl:choose>
		</reactiongestationperiod>
		<reactiongestationperiodunit>
			<xsl:choose>
				<xsl:when test="hl7:value/@xsi:type = 'PQ'">
					<xsl:call-template name="getMapping">
						<xsl:with-param name="type">UCUM</xsl:with-param>
						<xsl:with-param name="code" select="hl7:value/@unit"/>
					</xsl:call-template>
				</xsl:when>
			</xsl:choose>
		</reactiongestationperiodunit>
	</xsl:template>
	
	<!-- B.4.k.7.r.1 Indication for use in the case from primary source -->
	<xsl:template match="hl7:observation" mode="drug-indication">
		<drugindicationmeddraversion>
			<xsl:value-of select="hl7:value/@codeSystemVersion"/>
		</drugindicationmeddraversion>
		<drugindication>
			<xsl:value-of select="hl7:value/@code"/>
		</drugindication>
	</xsl:template>
	
	<!-- B.4.k.4.r.6 Date of start of drug -->
	<xsl:template match="hl7:substanceAdministration" mode="drug-start-date">
		<xsl:if test="hl7:effectiveTime/hl7:comp[@xsi:type='IVL_TS']/hl7:low/@value">
			<xsl:call-template name="convertDate">
				<xsl:with-param name="elementName">drugstartdate</xsl:with-param>
				<xsl:with-param name="date-value" select="hl7:effectiveTime/hl7:comp[@xsi:type='IVL_TS']/hl7:low/@value"/>
				<xsl:with-param name="min-format">CCYY</xsl:with-param>
				<xsl:with-param name="max-format">CCYYMMDD</xsl:with-param>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>
	
	<!-- B.4.k.9.r.3.1 - Time interval between beginning  of drug administration and start of reaction/event -->
	<xsl:template name="drug-period-sas">
		<drugstartperiod>
			<xsl:value-of select="hl7:pauseQuantity/@value" />
		</drugstartperiod>
		<drugstartperiodunit>
			<xsl:call-template name="getMapping">
				<xsl:with-param name="type">UCUM</xsl:with-param>
				<xsl:with-param name="code" select="hl7:pauseQuantity/@unit"/>
			</xsl:call-template>
		</drugstartperiodunit>
	</xsl:template>
	
	<!-- B.4.k.9.r.3.2 - Time interval between beginning  of drug administration and end of reaction/event -->
	<xsl:template name="drug-period-sae">
		<druglastperiod>
			<xsl:value-of select="hl7:pauseQuantity/@value" />
		</druglastperiod>
		<druglastperiodunit>
			<xsl:call-template name="getMapping">
				<xsl:with-param name="type">UCUM</xsl:with-param>
				<xsl:with-param name="code" select="hl7:pauseQuantity/@unit"/>
			</xsl:call-template>
		</druglastperiodunit>
	</xsl:template>
	
	<!-- B.4.k.4.r.7; B.4.k.4.r.8 - Date of last/ Duration of drug administration -->
	<xsl:template match="hl7:substanceAdministration" mode="drug-end-date">
		<xsl:if test="hl7:effectiveTime/hl7:comp[@xsi:type='IVL_TS']/hl7:high/@value">
			<xsl:call-template name="convertDate">
				<xsl:with-param name="elementName">drugenddate</xsl:with-param>
				<xsl:with-param name="date-value" select="hl7:effectiveTime/hl7:comp[@xsi:type='IVL_TS']/hl7:high/@value"/>
				<xsl:with-param name="min-format">CCYY</xsl:with-param>
				<xsl:with-param name="max-format">CCYYMMDD</xsl:with-param>
			</xsl:call-template>
		</xsl:if>
		<xsl:if test="count(hl7:effectiveTime/hl7:comp[@xsi:type='IVL_TS']/hl7:width)>0">
			<drugtreatmentduration>
				<xsl:value-of select="hl7:effectiveTime/hl7:comp[@xsi:type='IVL_TS']/hl7:width/@value"/>
			</drugtreatmentduration>
			<drugtreatmentdurationunit>
				<xsl:call-template name="getMapping">
					<xsl:with-param name="type">UCUM</xsl:with-param>
					<xsl:with-param name="code" select="hl7:effectiveTime/hl7:comp[@xsi:type='IVL_TS']/hl7:width/@unit"/>
				</xsl:call-template>
			</drugtreatmentdurationunit>
		</xsl:if>
	</xsl:template>
	
	<!-- B.4.k.19. Additional information on drug - Part 1 -->
	<xsl:template match="hl7:ingredient" mode="drug-additional1">
		<xsl:if test="string-length(hl7:quantity/hl7:numerator/@value) > 0">
			<xsl:text>Ingredient (</xsl:text>
			<xsl:value-of select="hl7:ingredientSubstance/hl7:name" />
			<xsl:text>): </xsl:text>
			<xsl:value-of select="hl7:quantity/hl7:numerator/@value"/>
			<xsl:if test="hl7:quantity/hl7:denominator/@value and hl7:quantity/hl7:denominator/@value != 1">
				<xsl:text> / </xsl:text><xsl:value-of select="hl7:quantity/hl7:denominator/@value"/>
			</xsl:if>
			<xsl:text> </xsl:text>
			<xsl:value-of select="hl7:quantity/hl7:numerator/@unit"/>
			<xsl:if test="hl7:quantity/hl7:denominator/@unit">
				<xsl:text> / </xsl:text><xsl:value-of select="hl7:quantity/hl7:denominator/@unit"/>
			</xsl:if>
			<xsl:text>; </xsl:text>
		</xsl:if>
	</xsl:template>
	
	<!-- B.4.k.19. Additional information on drug - Part 2 -->
	<xsl:template match="hl7:observation" mode="drug-additional2">
		<xsl:call-template name="getText">
			<xsl:with-param name="id" ><xsl:value-of select="hl7:value/@code" /></xsl:with-param>
		</xsl:call-template>
		<xsl:value-of select="hl7:value/hl7:originalText" />
		<xsl:if test="not (position()=last())">
			<xsl:text>, </xsl:text>
		</xsl:if>
	</xsl:template>
	
	<!-- B.4.k.19. Additional information on drug - Part 3 -->
	<xsl:template match="hl7:observation" mode="drug-additional3">
		<xsl:text>, </xsl:text><xsl:value-of select="hl7:value"/>
	</xsl:template>
	
	<!-- B.4.k.9.r.4 Did reaction recur on readministration? -->
	<xsl:template match="hl7:observation" mode="drug-recurrence">
		<xsl:if test="hl7:value/@code = 1">
			<xsl:variable name="DrugRecurationId" select="hl7:outboundRelationship1/hl7:actReference/hl7:id/@extension"/>
			<drugrecurrence>
				<drugrecuractionmeddraversion>
					<xsl:value-of select="../../../../../../hl7:subjectOf2/hl7:observation[hl7:code/@code=$Reaction and hl7:id/@extension=$DrugRecurationId]/hl7:value/@codeSystemVersion" />
				</drugrecuractionmeddraversion>
				<drugrecuraction>
					<xsl:value-of select="../../../../../../hl7:subjectOf2/hl7:observation[hl7:code/@code=$Reaction and hl7:id/@extension=$DrugRecurationId]/hl7:value/@code" />
				</drugrecuraction>
			</drugrecurrence>
		</xsl:if>
	</xsl:template>

	<!-- B.4.k.9.r.2.r Relatedness of drug to reaction(s)/event(s) -->
	<xsl:template match="hl7:causalityAssessment" mode="drug-reaction">
		<xsl:variable name="ReactionId" select="hl7:subject1/hl7:adverseEffectReference/hl7:id/@extension"/>
		<drugreactionrelatedness>
			<drugreactionassesmeddraversion>
				<xsl:value-of select="../../hl7:subject1/hl7:primaryRole/hl7:subjectOf2/hl7:observation[hl7:code/@code=$Reaction and hl7:id/@extension=$ReactionId]/hl7:value/@codeSystemVersion" />
			</drugreactionassesmeddraversion>
			<drugreactionasses>
				<xsl:value-of select="../../hl7:subject1/hl7:primaryRole/hl7:subjectOf2/hl7:observation[hl7:code/@code=$Reaction and hl7:id/@extension=$ReactionId]/hl7:value/@code" />
			</drugreactionasses>
			<drugassessmentsource>
				<xsl:value-of select="hl7:author/hl7:assignedEntity/hl7:code/hl7:originalText"/>
			</drugassessmentsource>
			<drugassessmentmethod>
				<xsl:call-template name="truncate">
					<xsl:with-param name="string"><xsl:value-of select="hl7:methodCode/hl7:originalText"/></xsl:with-param>
					<xsl:with-param name="string-length">35</xsl:with-param>
				</xsl:call-template>
			</drugassessmentmethod>
			<drugresult>
				<xsl:call-template name="truncate">
					<xsl:with-param name="string"><xsl:value-of select="hl7:value"/></xsl:with-param>
					<xsl:with-param name="string-length">35</xsl:with-param>
				</xsl:call-template>
			</drugresult>
		</drugreactionrelatedness>
	</xsl:template>

</xsl:stylesheet>
