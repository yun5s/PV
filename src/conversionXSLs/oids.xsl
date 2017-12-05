<?xml version="1.0" encoding="UTF-8"?><!--		Conversion Style-Sheet (IODs)		Version:		0.9		Date:			21/06/2011		Status:		Step 2		Author:		Laurent DESQUEPER (EU)--><xsl:stylesheet version="1.0"	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"	xmlns="urn:hl7-org:v3" xmlns:mif="urn:hl7-org:v3/mif" >		<xsl:variable name="oidISOCountry">1.0.3166.1.2.2</xsl:variable>	<xsl:variable name="oidGenderCode">1.0.5218</xsl:variable>	<xsl:variable name="oidUCUM">2.16.840.1.113883.6.8</xsl:variable>			<xsl:variable name="oidMedDRA">2.16.840.1.113883.6.163</xsl:variable>	<xsl:variable name="oidSenderIdentifierValue">sender-identifier-value</xsl:variable>	<xsl:variable name="oidObservationCode">ich-observation-code-oid</xsl:variable>	<xsl:variable name="oidAttentionLineCode">ich-attentionLine-code-oid</xsl:variable>		<!-- M.1 Fields - Batch Wrapper -->	<!-- M.1.1 -->				<xsl:variable name="oidMessageType">ich-type-of-message-in-batch-oid</xsl:variable>	<!-- M.1.4 -->				<xsl:variable name="oidBatchNumber" select="$oidSenderIdentifierValue"/> 	<!-- M.1.5 -->				<xsl:variable name="oidBatchSenderId">ich-batch-sender-identifier-oid</xsl:variable>	<!-- M.1.6 -->				<xsl:variable name="oidBatchReceiverId">ich-batch-receiver-identifier-oid</xsl:variable>		<!-- M.2 Fields - Message Wrapper -->	<!-- M.2.r.4 -->				<xsl:variable name="oidMessageNumber">ich-senders-safety-report-identifier-oid</xsl:variable>	<!-- M.2.r.5 -->				<xsl:variable name="oidMessageSenderId">ich-message-sender-identifier-oid</xsl:variable>	<!-- M.2.r.6 -->				<xsl:variable name="oidMessageReceiverId">ich-message-receiver-identifier-oid</xsl:variable>		<!-- A.1 Fields - Case Safety Report -->	<!-- A.1.0.1 -->			<xsl:variable name="oidSendersReportNamespace">ich-senders-safety-report-identifier-oid</xsl:variable>	<!-- A.1.4 -->				<xsl:variable name="ReportType">ichReportType</xsl:variable>										<xsl:variable name="oidReportType">ich-type-of-report-oid</xsl:variable>	<!-- A.1.8 -->				<xsl:variable name="AdditionalDocumentsAvailable">additionalDocumentsAvailable</xsl:variable>	<!-- A.1.9 -->				<xsl:variable name="LocalCriteriaForExpedited">localCriteriaForExpedited</xsl:variable>	<!-- A.1.10.1/12 -->		<xsl:variable name="oidWorldWideCaseID">ich-worldwide-case-identifier-oid</xsl:variable>	<!-- A.1.10.2 -->			<xsl:variable name="InitialReport">initialReport</xsl:variable>										<xsl:variable name="oidFirstSender">ich-first-sender-of-this-case-oid</xsl:variable>	<!-- A.1.11 -->				<xsl:variable name="OtherCaseIDs">otherCaseIds</xsl:variable>	<!-- A.1.11.r.2 -->		<xsl:variable name="oidCaseIdentifier">caseIdentifierOid</xsl:variable>	<!-- A.1.13 -->				<xsl:variable name="NullificationAmendmentCode">nullificationAmendmentCode</xsl:variable>										<xsl:variable name="oidNullificationAmendment">ich-report-nullification-amendment-oid</xsl:variable>		<!-- A.1.13.1 -->			<xsl:variable name="NullificationAmendmentReason">nullificationAmendmentReason</xsl:variable>		<!-- A.2 - Primary Source -->	<!-- A.2 -->					<xsl:variable name="SourceReport">sourceReport</xsl:variable>	<!-- A.2.r.1.4 -->			<xsl:variable name="oidQualification">ich-qualification-oid</xsl:variable>		<!-- A.3 - Sender -->	<!-- A.3.1 -->				<xsl:variable name="oidSenderType">ich-sender-type-oid</xsl:variable>			<!-- A.5 - Study Identification -->	<!-- A.5 -->					<xsl:variable name="SponsorStudyNumber">oidSponsorStudyNumber</xsl:variable>	<!-- A.5.1.r.1 -->			<xsl:variable name="StudyRegistrationNumber">oidStudyRegistrationNumber</xsl:variable>	<!-- A.5.4 -->				<xsl:variable name="oidStudyType">ich-study-type-oid</xsl:variable>		<!-- B.1 / B.1.10 - Patient / Parent -->	<!-- B.1.1.1a -->			<xsl:variable name="GPMrn">gpmrn</xsl:variable>										<xsl:variable name="oidGPMedicalRecordNumber">ich-gp-medical-record-number-oid</xsl:variable>	<!-- B.1.1.1b -->			<xsl:variable name="SpecialistMrn">specialistMrn</xsl:variable>										<xsl:variable name="oidSpecialistRecordNumber">ich-specialist-record-number-oid</xsl:variable>	<!-- B.1.1.1c -->			<xsl:variable name="HospitalMrn">hospitalMrn</xsl:variable>										<xsl:variable name="oidHospitalRecordNumber">ich-hospital-record-number-oid</xsl:variable>	<!-- B.1.1.1d -->			<xsl:variable name="Investigation">investigation</xsl:variable>										<xsl:variable name="oidInvestigationNumber">ich-investigation-number-oid</xsl:variable>	<!-- B.1.2.2 -->			<xsl:variable name="Age">age</xsl:variable>	<!-- B.1.2.2.1 -->			<xsl:variable name="GestationPeriod">gestationPeriod</xsl:variable>	<!-- B.1.2.3 -->			<xsl:variable name="AgeGroup">ageGroup</xsl:variable>	<!-- B.1.2.3 -->			<xsl:variable name="oidAgeGroup">ich-patient-age-group-oid</xsl:variable>	<!-- B.1.3 -->				<xsl:variable name="BodyWeight">bodyweight</xsl:variable>	<!-- B.1.4 -->				<xsl:variable name="Height">height</xsl:variable>	<!-- B.1.6 -->				<xsl:variable name="LastMenstrualPeriodDate">lastMenstrualPeriodDate</xsl:variable>	<!-- B.1.10 -->				<xsl:variable name="Parent">PRN</xsl:variable>		<!-- B.1.7 / B.1.10.7 - Medical History -->	<!-- B.1.7 -->				<xsl:variable name="RelevantMedicalHistoryAndConcurrentConditions">relevantMedicalHistoryAndConcurrentConditions</xsl:variable>	<!-- B.1.7.1.r.d -->		<xsl:variable name="Continuing">continuing</xsl:variable>	<!-- B.1.7.1.r.g -->		<xsl:variable name="Comment">comment</xsl:variable>	<!-- B.1.7.2 -->			<xsl:variable name="HistoryAndConcurrentConditionText">historyAndConcurrentConditionText</xsl:variable>	<!-- B.1.7.3 -->			<xsl:variable name="ConcommitantTherapy">concommitantTherapy</xsl:variable>		<!-- B.1.8 / B.1.10.8 - Drug History -->	<!-- B.1.8 -->				<xsl:variable name="DrugHistory">drugHistory</xsl:variable>	<!-- B.1.8.r.a1 -->		<xsl:variable name="MPID">MPID</xsl:variable>	<!-- B.1.8.r.a3 -->		<xsl:variable name="PhPID">PhPID</xsl:variable>	<!-- B.1.8.r.f.2 -->		<xsl:variable name="Indication">indication</xsl:variable>	<!-- B.1.8.r.g.2 -->		<xsl:variable name="Reaction">reaction</xsl:variable>		<!-- B.1.9 -->	<!-- B.1.9.2 -->			<xsl:variable name="ReportedCauseOfDeath">reportedCauseOfDeath</xsl:variable>	<!-- B.1.9.3 -->			<xsl:variable name="Autopsy">autopsy</xsl:variable>	<!-- B.1.9.4 -->			<xsl:variable name="CauseOfDeath">causeOfDeath</xsl:variable>		<!-- B.2 - Reaction -->	<!-- B.2.i -->					<xsl:variable name="oidInternalReferencesToReaction">oidInternalReferencesToReaction</xsl:variable>	<!-- B.2.i.0.b -->			<xsl:variable name="ReactionForTranslation">reactionForTranslation</xsl:variable>	<!-- B.2.i.2.1 -->			<xsl:variable name="TermHighlightedByReporter">termHighlightedByReporter</xsl:variable>										<xsl:variable name="oidTermHighlighted">ich-term-highlighted-oid</xsl:variable>	<!-- B.2.i.2.2 -->			<xsl:variable name="ResultsInDeath">resultsInDeath</xsl:variable>										<xsl:variable name="LifeThreatening">isLifeThreatening</xsl:variable>										<xsl:variable name="CausedProlongedHospitalisation">requiresInpatientHospitalization</xsl:variable>										<xsl:variable name="DisablingIncapaciting">resultsInPersistentOrSignificantDisability</xsl:variable>										<xsl:variable name="CongenitalAnomalyBirthDefect">congenitalAnomalyBirthDefect</xsl:variable>										<xsl:variable name="OtherMedicallyImportantCondition">otherMedicallyImportantCondition</xsl:variable>	<!-- B.2.i.6 -->				<xsl:variable name="Outcome">outcome</xsl:variable>										<xsl:variable name="oidOutcome">ich-outcome-of-reaction-event-oid</xsl:variable>	<!-- B.3 - Test -->	<!-- B.3 -->					<xsl:variable name="TestsAndProceduresRelevantToTheInvestigation">testsAndProceduresRelevantToTheInvestigation</xsl:variable>	<!-- B.3.r.4 -->				<xsl:variable name="MoreInformationAvailable">moreInformationAvailable</xsl:variable>		<!-- B.4 - Drug -->	<!-- B.4 -->					<xsl:variable name="DrugInformation">drugInformation</xsl:variable>										<xsl:variable name="oidInternalReferencesToSubstanceAdministration">oidInternalReferencesToSubstanceAdministration</xsl:variable>	<!-- B.4.k.1 -->				<xsl:variable name="InterventionCharacterization">interventionCharacterization</xsl:variable>										<xsl:variable name="oidDrugRole">ich-characterisation-of-drug-role-oid</xsl:variable>	<!-- B.4.k.2.4 -->			<xsl:variable name="RetailSupply">retailSupply</xsl:variable>	<!-- B.4.k.2.5 -->			<xsl:variable name="Blinded">blinded</xsl:variable>	<!-- B.4.k.3 -->				<xsl:variable name="oidAuthorisationNumber">oidAuthorisationNumber</xsl:variable>	<!-- B.4.k.4.r.12/13-->	<xsl:variable name="oidICHRoute">ich-route-of-administration-oid</xsl:variable>	<!-- B.4.k.4.r.13.2 -->	<xsl:variable name="ParentRouteOfAdministration">parentRouteOfAdministration</xsl:variable>	<!-- B.4.k.5.1 -->			<xsl:variable name="CumulativeDoseToReaction">cumulativeDoseToReaction</xsl:variable>	<!-- B.4.k.7 -->				<xsl:variable name="SourceReporter">sourceReporter</xsl:variable>	<!-- B.4.k.8 -->				<xsl:variable name="oidActionTaken">ich-action-taken-with-drug-oid</xsl:variable>	<!-- B.4.k.9.i.2 -->		<xsl:variable name="Causality">causality</xsl:variable>	<!-- B.4.k.9.i.4 -->		<xsl:variable name="RecurranceOfReaction">recurranceOfReaction</xsl:variable>										<xsl:variable name="oidRechallenge">ich-recur-on-readministration-oid</xsl:variable>	<!-- B.4.k.10 -->			<xsl:variable name="CodedDrugInformation">codedDrugInformation</xsl:variable>	<!-- B.4.k.11 -->			<xsl:variable name="AdditionalInformation">additionalInformation</xsl:variable>		<!-- B.5 - Summary -->	<!-- B.5.3 -->				<xsl:variable name="Diagnosis">diagnosis</xsl:variable>										<xsl:variable name="Sender">sender</xsl:variable>	<!-- B.5.5 -->				<xsl:variable name="SummaryAndComment">summaryAndComment</xsl:variable>										<xsl:variable name="Reporter">reporter</xsl:variable>		<!-- ACK -->										<xsl:variable name="oidAckBatchNumber">ich-ack-batch-number-oid</xsl:variable>										<xsl:variable name="oidAckBatchReceiverID">ich-ack-batch-receiver-identifier-oid</xsl:variable>										<xsl:variable name="oidAckBatchSenderID">ich-ack-batch-sender-identifier-oid</xsl:variable>										<xsl:variable name="AckLocalMessageNumber">acknowledgementLocalMessageNumber</xsl:variable>										<xsl:variable name="oidAckLocalMessageNumber">ich-ack-local-message-number-oid</xsl:variable>										<xsl:variable name="DateOfIcsrBatchTransmission">dateOfIcsrBatchTransmission</xsl:variable>										<xsl:variable name="oidLocalReportNumber">ich-local-report-number-oid</xsl:variable>										<xsl:variable name="oidAckReceiverID">ich-ack-receiver-identifier-oid</xsl:variable>										<xsl:variable name="oidAckSenderID">ich-ack-sender-identifier-oid</xsl:variable>										<xsl:variable name="oidDateOfCreation">ich-date-of-creation-oid</xsl:variable>										<xsl:variable name="receiptDate">receiptDate</xsl:variable></xsl:stylesheet>