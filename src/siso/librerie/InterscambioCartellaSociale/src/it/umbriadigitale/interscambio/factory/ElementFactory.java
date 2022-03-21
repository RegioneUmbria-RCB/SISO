package it.umbriadigitale.interscambio.factory;

import static it.umbriadigitale.interscambio.factory.DatatypesModFactory.*;
import static it.umbriadigitale.interscambio.factory.DefaultElementFactory.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hl7.v3.request.CE;
import org.hl7.v3.request.COCTMT030000ZZCitizen;
import org.hl7.v3.request.COCTMT030000ZZGuarantor;
import org.hl7.v3.request.COCTMT030000ZZOtherIDs;
import org.hl7.v3.request.COCTMT030000ZZPersonalRelationship;
import org.hl7.v3.request.COCTMT030007UVBirthPlace;
import org.hl7.v3.request.COCTMT030007UVPerson;
import org.hl7.v3.request.COCTMT050000ZZIdentityDocument;
import org.hl7.v3.request.COCTMT050000ZZSubject;
import org.hl7.v3.request.COCTMT090000UV01AssignedEntity;
import org.hl7.v3.request.COCTMT090000UV01Person;
import org.hl7.v3.request.COCTMT090003UV01AssignedEntity;
import org.hl7.v3.request.COCTMT090003UV01Person;
import org.hl7.v3.request.COCTMT090103UV01AssignedPerson;
import org.hl7.v3.request.COCTMT090103UV01Person;
import org.hl7.v3.request.COCTMT150000UV02Organization;
import org.hl7.v3.request.COCTMT150002UV01Organization;
import org.hl7.v3.request.COCTMT150003UV03Organization;
import org.hl7.v3.request.COCTMT710000UV07Place;
import org.hl7.v3.request.II;
import org.hl7.v3.request.IVLTS;
import org.hl7.v3.request.MCCIMT000100UV01Device;
import org.hl7.v3.request.MCCIMT000100UV01Receiver;
import org.hl7.v3.request.MCCIMT000100UV01Sender;
import org.hl7.v3.request.PRSSMT001004ZZAttender;
import org.hl7.v3.request.PRSSMT001004ZZConsultant;
import org.hl7.v3.request.PRSSMT001004ZZInformant;
import org.hl7.v3.request.PRSSMT001004ZZNeed;
import org.hl7.v3.request.PRSSMT001004ZZReason2;
import org.hl7.v3.request.PRSSMT001004ZZReference;
import org.hl7.v3.request.PRSSMT999002ZZCommonObservationEvent;
import org.hl7.v3.request.PRSSMT999003ZZAct;
import org.hl7.v3.request.PRSSMT999003ZZLocation;
import org.hl7.v3.request.PRSSMT999003ZZPerformer3;
import org.hl7.v3.request.PRSSMT999003ZZServiceFacility;
import org.hl7.v3.request.PRSSMT999005ZZComponent10;
import org.hl7.v3.request.PRSSMT999005ZZFinalGoal;

import it.umbriadigitale.interscambio.constants.OIDDefaults;
import it.umbriadigitale.interscambio.data.wrapper.AssistentePersonaleWrapper;
import it.umbriadigitale.interscambio.data.wrapper.AttenderWrapper;
import it.umbriadigitale.interscambio.data.wrapper.MedicoPediatraWrapper;
import it.umbriadigitale.interscambio.data.wrapper.ObiettivoWrapper;
import it.umbriadigitale.interscambio.data.wrapper.PerformerWrapper;
import it.umbriadigitale.interscambio.data.wrapper.PersonalRelationshipWrapper;
import it.umbriadigitale.interscambio.data.wrapper.PrestazioneWrapper;
import it.umbriadigitale.interscambio.data.wrapper.SegnalazioneWrapper;
import it.umbriadigitale.interscambio.data.wrapper.WrapperConverter;
import it.umbriadigitale.interscambio.enums.EAddress;
import it.umbriadigitale.interscambio.enums.EAttender;
import it.umbriadigitale.interscambio.enums.EGuarantor;
import it.umbriadigitale.interscambio.enums.EObservation;
import it.umbriadigitale.interscambio.enums.EReference;

/**
 * Factory per la produzione di elementi XML JAXB complessi, costruiti secondo specifiche
 * 
 * @author Iacopo Sorce
 */
class ElementFactory implements OIDDefaults {
	
	/**
	 * Restituisce un elemento &lt;v3:interactionId extension="PRSS_IN001004ZZ"/&gt;
	 */
	static II createDefaultInteractionId() {
		return createIIRootExtension(null, "PRSS_IN001004ZZ");	// default
	}
	
	/*
		<v3:receiver typeCode="RCV">
			<v3:device classCode="DEV" determinerCode="INSTANCE">
				<v3:id root="2.16.840.1.113883.2.9.2.30.3.2.4.3" extension="a"/>
			</v3:device>
		</v3:receiver>
	 */
	static MCCIMT000100UV01Receiver createReceiver(String idReceiver) {
		MCCIMT000100UV01Receiver receiver = createDefaultReceiver();	// oggetto di ritorno
		
		MCCIMT000100UV01Device device = createDefaultDevice();
		
		// receiver.device.id
		device.getId().add(createIIRootExtension(OID_RECEIVER_ID, idReceiver));
		
		
		receiver.setDevice(device);
		
		return receiver;
	}
	
	/*
		<v3:receiver typeCode="RCV">
			<v3:device classCode="DEV" determinerCode="INSTANCE">
				<v3:id root="2.16.840.1.113883.2.9.2.30.3.2.4.3" extension="a"/>
			</v3:device>
		</v3:receiver>
	 */
	static MCCIMT000100UV01Sender createSender(String idSender) {
		MCCIMT000100UV01Sender sender = createDefaultSender();	// oggetto di ritorno
		
		MCCIMT000100UV01Device device = createDefaultDevice();
		
		// sender.device.id
		device.getId().add(createIIRootExtension(OID_SENDER_ID, idSender));
		
		
		sender.setDevice(device);
		
		return sender;
	}
	
	/*
		<v3:subjectOf>
			<v3:identityDocument>
				<v3:id root="2.16.840.1.113883.2.9.2.30.3.2.3.1.1.7" extension="0784374394309"></v3:id><!--estremi documento-->
				<v3:code code="4" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.3.1.1.6"/> <!-- tipo documento-->
			</v3:identityDocument>
		</v3:subjectOf>
	 */
	static COCTMT050000ZZSubject createSubjectOf(String estremiDocumento, String tipoDocumentoCode) {
		COCTMT050000ZZSubject subjectOf = createDefaultSubjectOf();	// oggetto di ritorno
		
		COCTMT050000ZZIdentityDocument identityDocument = createDefaultIdentityDocument();
		
		// subjectOf.identityDocument.id
		identityDocument.setId(createIIRootExtension(OID_ESTREMI_DOCUMENTO, estremiDocumento));
		
		// subjectOf.identityDocument.code
		identityDocument.setCode(createCDCodeCodeSystem(tipoDocumentoCode, OID_TIPO_DOCUMENTO));
		
		
		subjectOf.setIdentityDocument(identityDocument);
		
		return subjectOf;
	}
	
	/*
		<v3:asCitizen>
			<v3:politicalOrganization classCode="ORG">
				<v3:id  root="2.16.840.1.113883.2.9.6.2.5" extension="380"/><!-- Cittadinanza assistito-->
			</v3:politicalOrganization>
		</v3:asCitizen>
	 */
	static COCTMT030000ZZCitizen createAsCitizenCittadinanza(String cittadinanzaCode) {
		COCTMT030000ZZCitizen asCitizen = createDefaultAsCitizen();	// oggetto di ritorno
		
		COCTMT150000UV02Organization politicalOrganization = createDefaultPoliticalOrganization();
		
		politicalOrganization.getId().add(createIIRootExtension(OID_PATIENTPERSON_CITTADINANZA, cittadinanzaCode));
		
		asCitizen.setPoliticalOrganization(politicalOrganization);
		
		return asCitizen;
	}
	
	/*
		<v3:asOtherIDs>
			<v3:id root="2.16.840.1.113883.2.9.4.1.4" extension="12345678944453"></v3:id><!--tessera team assistito-->
			<v3:id root="2.16.840.1.113883.2.9.4.1.2" extension="CDFCDF90DS23P089Y"></v3:id><!--codice fiscale assistito-->
		</v3:asOtherIDs>
	 */
	static COCTMT030000ZZOtherIDs createAsOtherIDs(String tesseraTeamCode, String codiceFiscale) {
		COCTMT030000ZZOtherIDs asOtherIDs = createDefaultAsOtherIDs();	// oggetto di ritorno
		
		asOtherIDs.getId().add(createIIRootExtension(OID_PATIENTPERSON_TESSERA_TEAM, tesseraTeamCode));
		
		// TODO confermare
		if (codiceFiscale != null) {
			asOtherIDs.getId().add(createIIRootExtension(OID_PATIENTPERSON_CODICE_FISCALE, codiceFiscale));
		}
		
		return asOtherIDs;
	}
	
	private static COCTMT030000ZZGuarantor createGuarantor(EGuarantor guarantorType, String significantValue) {
		COCTMT030000ZZGuarantor guarantor = createDefaultGuarantor();	// oggetto di ritorno
		
		COCTMT150002UV01Organization guarantorOrganization;
		switch (guarantorType.getGuarantorOrganizationType()) {
			case ORG:
				guarantorOrganization = createORGGuarantorOrganization();
				break;

			case DEFAULT:	// fall through
			default:	// non dovrebbe mai accadere
				guarantorOrganization = createDefaultGuarantorOrganization();
				break;
		}
		
		// guarantor.guarantorOrganization.id
		guarantorOrganization.getId().add(createIIRootExtension(guarantorType.getId_Root(), significantValue));
		
		
		guarantor.setGuarantorOrganization(REQUEST_OBJECT_FACTORY.createCOCTMT030000ZZGuarantorGuarantorOrganization(guarantorOrganization));
		
		return guarantor;
	}
	
	static List<COCTMT030000ZZGuarantor> buildGuarantors(Map<EGuarantor, String> guarantorMap) {
		List<COCTMT030000ZZGuarantor> listGuarantors = new ArrayList<>();	// oggetto di ritorno, di default lista vuota
		
		for (EGuarantor guarantorType : guarantorMap.keySet()) {
			String significantValue = guarantorMap.get(guarantorType);
			listGuarantors.add(createGuarantor(guarantorType, significantValue));
		}
		
		return listGuarantors;
	}
	
	/*
		<v3:personalRelationship> <!-- Componenti Nucleo Familiare -->
			<v3:code code="3" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.3.1.1.22"/><!-- Tipologia relazione familiare -->
			<v3:addr use="comune di residenza">
				<v3:censusTract>156146</v3:censusTract><!-- comune di residenza --> 
			</v3:addr>
			<v3:relationshipHolder1>
				[...]
			</v3:relationshipHolder1>
		</v3:personalRelationship>
	 */
	static COCTMT030000ZZPersonalRelationship createPersonalRelationship(PersonalRelationshipWrapper personalRelationshipData) {
		COCTMT030000ZZPersonalRelationship personalRelationship = createDefaultPersonalRelationship();	// oggetto di ritorno
		
		// personalRelationship.code
		personalRelationship.setCode(
			createCECodeCodeSystem(
				personalRelationshipData.getTipologiaRelazioneCode(),
				personalRelationshipData.getPersonalRelationshipType().getCode_CodeSystem()));
		
		// personalRelationship.addr
		personalRelationship.getAddr().add(createADAddrCensusTract(personalRelationshipData.getComuneResidenzaCode(), EAddress.COMUNE_RESIDENZA));
		
		// personalRelationship.relationshipHolder1
		personalRelationship.setRelationshipHolder1(
			REQUEST_OBJECT_FACTORY.createCOCTMT030000ZZPersonalRelationshipRelationshipHolder1(createRelationshipHolder1(personalRelationshipData)));
		
		
		return personalRelationship;
	}
	
	/*
		<v3:relationshipHolder1>
			<v3:id root="2.16.840.1.113883.2.9.4.3.2" extension="CDFCDF12Q12A123S"/> <!-- codice fiscale -->
			<v3:name>
				<v3:given>Nome CNF</v3:given> <!-- nome -->
				<v3:family>Cognome CNF</v3:family><!-- cognome -->
			</v3:name>
			<v3:desc>1</v3:desc><!-- Caregiver -->
			<v3:administrativeGenderCode code="2" codeSystem="2.16.840.1.113883.5.1"/> <!-- genere -->
			<v3:birthTime value="10012011"/><!-- data di nascita --> 
			<v3:birthPlace>
				[...]
			</v3:birthPlace>
		</v3:relationshipHolder1>
	 */
	private static COCTMT030007UVPerson createRelationshipHolder1(PersonalRelationshipWrapper personalRelationshipData) {
		COCTMT030007UVPerson relationshipHolder1 = createDefaultRelationshipHolder1();	// oggetto di ritorno
		
		// relationshipHolder1.id
		relationshipHolder1.getId().add(
			createIIRootExtension(OID_PATIENTPERSON_PERSONALRELATIONSHIP_CODICE_FISCALE, personalRelationshipData.getCodiceFiscale()));
		
		// relationshipHolder1.name
		relationshipHolder1.getName().add(createENNameGivenFamily(personalRelationshipData.getNome(), personalRelationshipData.getCognome()));
		
		// relationshipHolder1.desc
		relationshipHolder1.setDesc(createEDWithText(personalRelationshipData.getCaregiverText()));
		
		// relationshipHolder1.administrativeGenderCode
		relationshipHolder1.setAdministrativeGenderCode(createCECodeCodeSystem(personalRelationshipData.getGenereCode(), OID_ADMINISTRATIVE_GENDER_CODE));
		
		// relationshipHolder1.birthTime
		relationshipHolder1.setBirthTime(createTSValue(personalRelationshipData.getDataNascitaText()));
		
		// relationshipHolder1.birthPlace
		relationshipHolder1.setBirthPlace(
			REQUEST_OBJECT_FACTORY.createCOCTMT030007UVPersonBirthPlace(createBirthPlaceCensusTract(personalRelationshipData.getComuneNascitaCode())));
		
		
		return relationshipHolder1;
	}
	
	/*
		<v3:birthPlace>
			<v3:birthplace>
				<v3:addr use="comune di nascita">
					<v3:censusTract>156146</v3:censusTract><!-- comune di nascita --> 
				</v3:addr>
			</v3:birthplace>
		</v3:birthPlace>
	 */
	private static COCTMT030007UVBirthPlace createBirthPlaceCensusTract(String comuneNascita) {
		COCTMT030007UVBirthPlace birthPlace = createDefaultBirthPlace();	// oggetto di ritorno
		
		COCTMT710000UV07Place subBirthplace = createDefaultSubBirthplace();
		
		// birthPlace.birthplace.addr
		subBirthplace.setAddr(createADAddrCensusTract(comuneNascita, EAddress.COMUNE_NASCITA));
		
		
		birthPlace.setBirthplace(subBirthplace);
		
		return birthPlace;
	}
	
	/*
		<v3:birthPlace>
			<v3:birthplace>
				<v3:addr>
					<v3:code code="015146" codeSystem="2.16.840.1.113883.2.9.4.2.3"/><!-- comune di nascita assistito-->
				</v3:addr>
			</v3:birthplace>
		</v3:birthPlace>
	 */
	static COCTMT030007UVBirthPlace createBirthPlaceCode(String comuneNascita) {
		COCTMT030007UVBirthPlace birthPlace = createDefaultBirthPlace();	// oggetto di ritorno
		
		COCTMT710000UV07Place subBirthplace = createDefaultSubBirthplace();
		
		// birthPlace.birthplace.addr
		subBirthplace.setAddr(createADAddrCode(comuneNascita, OID_PATIENTPERSON_COMUNE_NASCITA_CODE));
		
		
		birthPlace.setBirthplace(subBirthplace);
		
		return birthPlace;
	}
	
	/*
		<v3:consultant typeCode="CON">
			<v3:assignedPerson classCode="ASSIGNED">
				<v3:id root="2.16.840.1.113883.2.9.4.3.2" extension="MMGMMG12M12M123E"/> <!--codice fiscale MMG-->
				<v3:assignedPerson>
					<v3:name>
						<v3:given> Nome MMG</v3:given><!-- Nome MMG / PDF -->
						<v3:family>Cognome MMG</v3:family><!-- Cognome MMG / PDF -->
					</v3:name>
				</v3:assignedPerson>
			</v3:assignedPerson>
		</v3:consultant>
	 */
	static PRSSMT001004ZZConsultant createConsultantMMGPDF(MedicoPediatraWrapper mmgpdf) {
		PRSSMT001004ZZConsultant consultant = createDefaultConsultant();	// oggetto di ritorno
		
		COCTMT090103UV01AssignedPerson assignedPerson = createDefaultAssignedPerson();
		
		// consultant.assignedPerson.id
		assignedPerson.getId().add(createIIRootExtension(OID_MMGPDF_CODICE_FISCALE, mmgpdf.getCodiceFiscale()));
		
		
		// consultant.assignedPerson.assignedPerson (attenzione alla gerarchia!)
		COCTMT090103UV01Person subAssignedPerson = createDefaultSubAssignedPerson();
		
		// consultant.assignedPerson.assignedPerson.name
		subAssignedPerson.getName().add(createENNameGivenFamily(mmgpdf.getNome(), mmgpdf.getCognome()));
		
		
		assignedPerson.setAssignedPerson(REQUEST_OBJECT_FACTORY.createCOCTMT090103UV01AssignedPersonAssignedPerson(subAssignedPerson));
		consultant.setAssignedPerson(assignedPerson);
		
		return consultant;
	}
	
	/*
		<v3:consultant typeCode="CON"> <!-- Assistente personale -->
			<v3:assignedPerson classCode="ASSIGNED">
				<v3:id root="2.16.840.1.113883.2.9.4.3.2" extension="ASSASS12A12A223O"/> <!-- codice fiscale -->
				<v3:code code="6" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.3.1.1.24"/> <!-- Tipologia Assistente personale -->
				<v3:addr use="comune di residenza">
					<v3:censusTract>123456</v3:censusTract><!-- comune di residenza --> 
				</v3:addr>
				<v3:administrativeGenderCode code="5" codeSystem="2.16.840.1.113883.5.1"/> <!-- genere-->
				<v3:assignedPerson>
					<v3:name>
						<v3:given>Nome ASS</v3:given><!-- Nome -->
						<v3:family>Cognome ASS</v3:family><!-- Cognome -->
					</v3:name>
				</v3:assignedPerson>
				<v3:birthTime value="12101978"/><!-- data di nascita --> 
			</v3:assignedPerson>
		</v3:consultant>
	 */
	private static PRSSMT001004ZZConsultant createConsultantAssistentePersonale(AssistentePersonaleWrapper assistentePersonale) {
		PRSSMT001004ZZConsultant consultant = createDefaultConsultant();	// oggetto di ritorno
		
		COCTMT090103UV01AssignedPerson assignedPerson = createDefaultAssignedPerson();
		
		// consultant.assignedPerson.id
		assignedPerson.getId().add(createIIRootExtension(OID_ASSITENTE_PERSONALE_CODICE_FISCALE, assistentePersonale.getCodiceFiscale()));
		
		// consultant.assignedPerson.code
		assignedPerson.setCode(createCECodeCodeSystem(assistentePersonale.getTipologiaCode(), OID_TIPOLOGIA_ASSITENTE_PERSONALE));
		
		// consultant.assignedPerson.addr
		assignedPerson.getAddr().add(createADAddrCensusTract(assistentePersonale.getComuneResidenza(), EAddress.COMUNE_RESIDENZA));
		
		// consultant.assignedPerson.administrativeGenderCode
		assignedPerson.setAdministrativeGenderCode(createCECodeCodeSystem(assistentePersonale.getGenereCode(), OID_ADMINISTRATIVE_GENDER_CODE));
		
		
		// consultant.assignedPerson.assignedPerson (attenzione alla gerarchia!)
		COCTMT090103UV01Person subAssignedPerson = createDefaultSubAssignedPerson();
		
		// consultant.assignedPerson.assignedPerson.name
		subAssignedPerson.getName().add(createENNameGivenFamily(assistentePersonale.getNome(), assistentePersonale.getCognome()));
		
		assignedPerson.setAssignedPerson(REQUEST_OBJECT_FACTORY.createCOCTMT090103UV01AssignedPersonAssignedPerson(subAssignedPerson));
		
		
		// consultant.assignedPerson.birthTime
		assignedPerson.setBirthTime(createTSValue(assistentePersonale.getDataNascitaText()));
		
		
		consultant.setAssignedPerson(assignedPerson);
		
		return consultant;
	}
	
	static List<PRSSMT001004ZZConsultant> buildConsultantsAssistentePersonale(List<AssistentePersonaleWrapper> assistentiPersonali) {
		List<PRSSMT001004ZZConsultant> listConsultants = new ArrayList<>();	// oggetto di ritorno, di default lista vuota
		
		for (AssistentePersonaleWrapper assistentePersonale : assistentiPersonali) {
			listConsultants.add(createConsultantAssistentePersonale(assistentePersonale));
		}
		
		return listConsultants;
	}
	
	/*
		<v3:reason>
			<v3:need classCode="COND" moodCode="EVN">
				<v3:code code="2" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.3.1.1.9"/> <!--bisogno espresso-->
				<v3:informant typeCode="INF">
					<v3:time value="18092017"/> <!-- data segnalazione-->
					<v3:assignedEntity>
						<v3:id root="2.16.840.1.113883.2.9.4.3.2" extension="CDFCDF12A59O098U"></v3:id> <!-- codice fiscale segnalante -->
						<v3:assignedPerson>
							<v3:name>
								<v3:given>Nome</v3:given><!--Nome segnalante-->
								<v3:family>Cognome</v3:family><!--Cogome segnalante-->
							</v3:name>
						</v3:assignedPerson>
						<v3:representedOrganization>
							<v3:id root="2.16.840.1.113883.2.9.2.30.3.2.3.1.1.20" extension="0000"/><!--codice ente segnalante-->
							<v3:code code="1" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.3.1.1.5"/><!-- tipologia ente segnalante -->
							<v3:name>Altro ente</v3:name>									 
						</v3:representedOrganization>
						
					</v3:assignedEntity>
				</v3:informant>
			</v3:need>
		</v3:reason>
	 */
	static PRSSMT001004ZZReason2 createReason(SegnalazioneWrapper segnalazione) {
		PRSSMT001004ZZReason2 reason = createDefaultReason();	// oggetto di ritorno
		
		PRSSMT001004ZZNeed need = createDefaultNeed();
		
		// reason.need.code
		need.setCode(createCECodeCodeSystem(segnalazione.getBisognoEspressoCode(), OID_BISOGNO_ESPRESSO_CODE));
		
		
		// reason.need.informant
		PRSSMT001004ZZInformant informant = createDefaultInformant();
		
		// reason.need.informant.time
		IVLTS time = createDefaultIVLTS();
		time.setValue(segnalazione.getDataSegnalazioneText());
		
		informant.setTime(time);
		
		
		// reason.need.informant.assignedEntity
		COCTMT090003UV01AssignedEntity assignedEntity = createDefaultAssignedEntity();
		
		// reason.need.informant.assignedEntity.id
		assignedEntity.getId().add(createIIRootExtension(OID_CODICE_FISCALE_SEGNALANTE, segnalazione.getCodiceFiscaleSegnalante()));
		
		
		// reason.need.informant.assignedEntity.assignedPerson
		COCTMT090003UV01Person assignedPerson = createDefaultAssignedEntityAssignedPerson();
		
		// reason.need.informant.assignedEntity.assignedPerson.name
		assignedPerson.getName().add(createENNameGivenFamily(segnalazione.getNomeSegnalante(), segnalazione.getCognomeSegnalante()));
		
		assignedEntity.setAssignedPerson(REQUEST_OBJECT_FACTORY.createCOCTMT090003UV01AssignedEntityAssignedPerson(assignedPerson));
		
		
		// reason.need.informant.assignedEntity.representedOrganization
		COCTMT150003UV03Organization representedOrganization = createDefaultRepresentedOrganization();
		
		// reason.need.informant.assignedEntity.representedOrganization.id
		representedOrganization.getId().add(createIIRootExtension(OID_CODICE_ENTE_SEGNALANTE, segnalazione.getCodiceEnteSegnalante()));
		
		// reason.need.informant.assignedEntity.representedOrganization.code
		representedOrganization.setCode(createCECodeCodeSystem(segnalazione.getCodiceEnteSegnalante(), OID_TIPOLOGIA_ENTE_SEGNALANTE));
		
		// reason.need.informant.assignedEntity.representedOrganization.name
		representedOrganization.getName().add(createONWithText(segnalazione.getDenominazioneEnteSegnalante()));
		
		
		// chiudo tutti i set rimasti aperti
		assignedEntity.setRepresentedOrganization(REQUEST_OBJECT_FACTORY.createCOCTMT090003UV01AssignedEntityRepresentedOrganization(representedOrganization));
		informant.setAssignedEntity(assignedEntity);
		need.getInformant().add(informant);
		reason.setNeed(need);
		
		return reason;
	}
	
	/*
		<v3:reference typeCode="REFR">
			<v3:commonObservationEvent><!--ISEE-->
				<v3:code code="ISEE" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.3.1.1.18"/>
				<v3:value code="123.2" type="MO" currency="EUR"/>
			</v3:commonObservationEvent>
		</v3:reference>
	 */
	private static PRSSMT001004ZZReference createReferenceCodeValue(EReference referenceType, String significantValue) {
		PRSSMT001004ZZReference reference = createDefaultReference();	// oggetto di ritorno
		
		PRSSMT999002ZZCommonObservationEvent commonObservationEvent = createDefaultCommonObservationEvent();
		
		// reference.commonObservationEvent.code - se codeSystem è null, non comparirà l'attributo nell'XML prodotto
		commonObservationEvent.setCode(createCECodeCodeSystem(referenceType.getCode_Code(), referenceType.getCode_CodeSystem()));
		
		// reference.commonObservationEvent.value
		CE value = createCECodeType(significantValue, referenceType.getValue_Type());
		if (referenceType.isValueTypeMO()) {
			value.setCurrency("EUR");	// TODO c'è solo in un caso ed è "EUR"
		}
		
		commonObservationEvent.setValue(value);
		
		
		reference.getObservationGroupOrCommonObservationEvent().add(commonObservationEvent);
		
		return reference;
	}
	
	/*
		<v3:reference typeCode="REFR"> <!-- Servizi sociosanitari già erogati all’assistito-->
			<v3:commonObservationEvent>
				<v3:code code="SSGE" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.3.1.2.5"/>
				<v3:text>servizi sociosanitari</v3:text>
			</v3:commonObservationEvent>
		</v3:reference>
	 */
	private static PRSSMT001004ZZReference createReferenceCodeText(EReference referenceType, String significantValue) {
		PRSSMT001004ZZReference reference = createDefaultReference();	// oggetto di ritorno
		
		PRSSMT999002ZZCommonObservationEvent commonObservationEvent = createDefaultObservation();
		
		// reference.commonObservationEvent.code - se codeSystem è null, non comparirà l'attributo nell'XML prodotto
		commonObservationEvent.setCode(createCECodeCodeSystem(referenceType.getCode_Code(), referenceType.getCode_CodeSystem()));
		
		// reference.commonObservationEvent.text
		commonObservationEvent.setText(createEDWithText(significantValue));
		
		
		reference.getObservationGroupOrCommonObservationEvent().add(commonObservationEvent);
		
		return reference;
	}
	
	static List<PRSSMT001004ZZReference> buildReferenceElements(Map<EReference, String> referenceData) {
		List<PRSSMT001004ZZReference> referenceElements = new ArrayList<>();	// lista di ritorno, default vuota
		
		for (EReference referenceType : referenceData.keySet()) {
			String keyValue = referenceData.get(referenceType);
			
			// aggiungo solo gli elementi reference se la rispettiva informazione è presente (ossia, non null)
			if (keyValue != null) {
				switch (referenceType.getElementContentType()) {
					case CODE_TEXT:
						referenceElements.add(createReferenceCodeText(referenceType, keyValue));
						break;
						
					case CODE_VALUE:
						referenceElements.add(createReferenceCodeValue(referenceType, keyValue));
						break;
						
					default:	// non dovrebbe mai succedere - per sicurezza non faccio nulla
						break;
				}
			}
		}
		
		return referenceElements;
	}
	
	
	/*
		<v3:attender typeCode="ATND">
			<v3:assignedPerson classCode="ASSIGNED">
				<v3:id root="2.16.840.1.113883.2.9.4.3.2" extension="RSPRSP12Q12Q123Q"/> <!-- Responsabile valutazione-->
				<v3:code code="1" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.3.1.2.1"/> <!--Profilo professionale-->
			</v3:assignedPerson>
		</v3:attender>
	 */
	// TODO gestire i null
	static PRSSMT001004ZZAttender createAttender(EAttender attenderType, AttenderWrapper attenderData) {
		PRSSMT001004ZZAttender attender = createDefaultAttender();	// oggetto di ritorno
		
		COCTMT090103UV01AssignedPerson assignedPerson = createDefaultAssignedPerson();
		
		// attender.assignedPerson.id
		assignedPerson.getId().add(createIIRootExtension(attenderType.getId_Root(), attenderData.getId()));
		
		// attender.assignedPerson.code
		String attenderCode = attenderData.getCode();
		if (attenderCode != null) {
			assignedPerson.setCode(createCECodeCodeSystem(attenderCode, attenderType.getCode_CodeSystem()));
		}
		
		
		attender.setAssignedPerson(assignedPerson);
		
		return attender;
	}
	
	
	/*
		<v3:finalGoal> <!-- Obiettivi -->
			<v3:observationGoal> 
				<v3:code code="1" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.3.1.3.9" displayName="nome obiettivo"/> <!-- codice obiettivo-->
				<v3:effectiveTime> 
					<v3:width value="12"/> <!--Tempistiche di monitoraggio-->
				</v3:effectiveTime>
				<v3:value code="indicatore_esito" codeSystem="CD"/> <!-- Indicatore di esito-->
			</v3:observationGoal>
		</v3:finalGoal>
	 */
	private static PRSSMT999005ZZFinalGoal createFinalGoal(ObiettivoWrapper obiettivo) {
		PRSSMT999005ZZFinalGoal finalGoal = createDefaultFinalGoal();	// oggetto di ritorno
		
		PRSSMT999002ZZCommonObservationEvent observationGoal = createDefaultObservationGoal();
		
		// finalGoal.observationGoal.code
		CE code = createCECodeCodeSystem(obiettivo.getCodiceObiettivo(), OID_CODICE_OBIETTIVO);
		code.setDisplayName("nome obiettivo");	// TODO default
		
		observationGoal.setCode(code);
		
		// finalGoal.observationGoal.effectiveTime
		observationGoal.getEffectiveTime().add(EffectiveTimeFactory.createEffectiveTimeWidth(obiettivo.getTempisticheMonitoraggioText()));
		
		// finalGoal.observationGoal.value
		observationGoal.setValue(createCECodeType(obiettivo.getIndicatoreEsitoCode(), "CD"));	// TODO default
		
		
		finalGoal.setObservationGoal(observationGoal);
		
		return finalGoal;
	}
	
	static List<PRSSMT999005ZZFinalGoal> buildFinalGoals(List<ObiettivoWrapper> obiettivi) {
		List<PRSSMT999005ZZFinalGoal> listFinalGoal = new ArrayList<>();	// oggetto di ritorno, di default lista vuota
		
		for (ObiettivoWrapper obiettivo : obiettivi) {
			listFinalGoal.add(createFinalGoal(obiettivo));
		}
		
		return listFinalGoal;
	}
	
	/*
		<v3:component2 typeCode="COMP">
			<v3:observation> <!-- Frequenza prestazioni-->
				<v3:code code="FRPR" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.3.1.3.4"/> 
				<v3:value code="2" type="ANY"/>
			</v3:observation>
		</v3:component2>
	 */
	private static PRSSMT999005ZZComponent10 createComponent2ObservationCodeValue(EObservation observationType, String significantValue) {
		PRSSMT999005ZZComponent10 component2 = createDefaultComponent2();	// oggetto di ritorno
		
		PRSSMT999002ZZCommonObservationEvent observation = createDefaultObservation();
		
		// component2.observation.code
		observation.setCode(createCECodeCodeSystem(observationType.getCode_Code(), observationType.getCode_CodeSystem()));
		
		// component2.observation.value
		observation.setValue(createCECodeCodeSystem(significantValue, observationType.getValue_Type()));
		
		
		component2.setObservation(REQUEST_OBJECT_FACTORY.createPRSSMT999005ZZComponent10Observation(observation));
		
		return component2;
	}
	
	/*
		<v3:component2 typeCode="COMP">
			<v3:observation> <!-- Valutazione finale -->
				<v3:code code="VAFI" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.3.1.5.2"/> 
				<v3:text>Valutazione</v3:text>
			</v3:observation>
		</v3:component2>
	 */
	private static PRSSMT999005ZZComponent10 createComponent2ObservationCodeText(EObservation observationType, String significantValue) {
		PRSSMT999005ZZComponent10 component2 = createDefaultComponent2();	// oggetto di ritorno
		
		PRSSMT999002ZZCommonObservationEvent observation = createDefaultObservation();
		
		// component2.observation.code
		observation.setCode(createCECodeCodeSystem(observationType.getCode_Code(), observationType.getCode_CodeSystem()));
		
		// component2.observation.text
		observation.setText(createEDWithText(significantValue));
		
		
		component2.setObservation(REQUEST_OBJECT_FACTORY.createPRSSMT999005ZZComponent10Observation(observation));
		
		return component2;
	}
	
	static List<PRSSMT999005ZZComponent10> buildComponent2ObservationElements(Map<EObservation, String> observationData) {
		List<PRSSMT999005ZZComponent10> observationElements = new ArrayList<>();	// lista di ritorno, default vuota
		
		for (EObservation observationType : observationData.keySet()) {
			String keyValue = observationData.get(observationType);
			
			// aggiungo solo gli elementi reference se la rispettiva informazione è presente (ossia, non null)
			if (keyValue != null) {
				switch (observationType.getElementContentType()) {
					case CODE_TEXT:
						observationElements.add(createComponent2ObservationCodeText(observationType, keyValue));
						break;
						
					case CODE_VALUE:
						observationElements.add(createComponent2ObservationCodeValue(observationType, keyValue));
						break;
						
					default:	// non dovrebbe mai succedere - per sicurezza non faccio nulla
						break;
				}
			}
		}
		
		return observationElements;
	}
	
	/*
		<v3:component2 typeCode="COMP">  <!-- servizi da erogare -->
			<v3:act>
				<v3:code code="codice_prestazione" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.3.1.4.17"/> <!-- codice prestazione -->
				<v3:effectiveTime>
					<v3:low value="123213"/> <!-- Data-ora inizio -->
					<v3:high value="312323"/> <!-- Data-ora fine -->
				</v3:effectiveTime>
				<v3:performer>
					[...]
				</v3:performer>
				<v3:location typeCode="LOC">
					[...]
				</v3:location>
			</v3:act>
		</v3:component2>
	 */
	private static PRSSMT999005ZZComponent10 createComponent2Act(PrestazioneWrapper prestazione) {
		PRSSMT999005ZZComponent10 component2 = createDefaultComponent2();	// oggetto di ritorno
		
		PRSSMT999003ZZAct act = createDefaultAct();
		
		// component2.act.code
		act.setCode(createCDCodeCodeSystem(prestazione.getCodPrestazione(), OID_CODICE_PRESTAZIONE));
		
		// component2.act.effectiveTime (LowHigh)
		act.setEffectiveTime(
			EffectiveTimeFactory.createEffectiveTimeLowHigh(prestazione.getDataInizioPrestazioneText(), prestazione.getDataFinePrestazioneText()));
		
		// component2.act.performer
		act.getPerformer().add(createPerformer(WrapperConverter.generatePerformerFrom(prestazione)));
		
		// component2.act.location
		act.getLocation().add(createLocation(prestazione.getLuogoPrestazioneCode()));
		
		
		component2.setAct(REQUEST_OBJECT_FACTORY.createPRSSMT999005ZZComponent10Act(act));
		
		return component2;
	}
	
	static List<PRSSMT999005ZZComponent10> buildComponent2Acts(List<PrestazioneWrapper> prestazioni) {
		List<PRSSMT999005ZZComponent10> listActs = new ArrayList<>();	// oggetto di ritorno, di default lista vuota
		
		for (PrestazioneWrapper prestazione : prestazioni) {
			listActs.add(createComponent2Act(prestazione));
		}
		
		return listActs;
	}
	
	/*
		<v3:performer>
			<v3:assignedEntity>
				<v3:id root="2.16.840.1.113883.2.9.4.3.2" extension="cf operatore erogante"/> <!--Codice fiscale operatore erogante-->
				<v3:code code="profilo_op_erog" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.3.1.4.19"/> <!-- Profilo professionale operatore erogante -->
				<v3:assignedPerson classCode="ASSIGNED">
					<v3:name>
						<v3:given></v3:given> <!--Nome operatore erogante-->
						<v3:family></v3:family><!--Cognome operatore erogante -->
					</v3:name>
				</v3:assignedPerson>
			</v3:assignedEntity>
		</v3:performer>
	 */
	private static PRSSMT999003ZZPerformer3 createPerformer(PerformerWrapper performerData) {
		PRSSMT999003ZZPerformer3 performer = createDefaultPerformer();	// oggetto di ritorno
		
		COCTMT090000UV01AssignedEntity assignedEntity = createDefaultPerformerAssignedEntity();
		
		// performer.assignedEntity.id
		assignedEntity.getId().add(createIIRootExtension(OID_CODICE_FISCALE_OPERATORE_EROGANTE, performerData.getCodiceFiscaleOperatoreErogante()));
		
		// performer.assignedEntity.code
		assignedEntity.setCode(
			createCECodeCodeSystem(performerData.getProfiloProfessionaleOperatoreEroganteCode(), OID_PROFILO_PROFESSIONALE_OPERATORE_EROGANTE_CODE));
		
		
		// performer.assignedEntity.assignedPerson
		COCTMT090000UV01Person assignedPerson = createDefaultPerformerAssignedEntityAssignedPerson();
		
		// performer.assignedEntity.assignedPerson.name
		assignedPerson.getName().add(createENNameGivenFamily(performerData.getNomeOperatoreErogante(), performerData.getCognomeOperatoreErogante()));
		
		
		// chiudo tutti gli elementi rimasti aperti
		assignedEntity.setAssignedPerson(REQUEST_OBJECT_FACTORY.createCOCTMT090000UV01AssignedEntityAssignedPerson(assignedPerson));
		performer.setAssignedEntity(assignedEntity);
		
		return performer;
	}
	
	/*
		<v3:location typeCode="LOC">
			<v3:serviceFacility>
				<v3:code code="luogo_prestazione" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.3.1.4.18"></v3:code><!-- Luogo prestazione -->
			</v3:serviceFacility>
		</v3:location>
	 */
	private static PRSSMT999003ZZLocation createLocation(String serviceFacilityCode_Code) {
		PRSSMT999003ZZLocation location = createDefaultLocation();	// oggetto di ritorno
		
		PRSSMT999003ZZServiceFacility serviceFacility = createDefaultServiceFacility();
		
		// location.serviceFacility.code
		serviceFacility.setCode(createCECodeCodeSystem(serviceFacilityCode_Code, OID_LUOGO_PRESTAZIONE));
		
		
		location.setServiceFacility(serviceFacility);
		
		return location;
	}
}