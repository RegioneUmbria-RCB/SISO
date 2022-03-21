package it.umbriadigitale.interscambio.factory;

import org.hl7.v3.request.AD;
import org.hl7.v3.request.ActClassCareProvision;
import org.hl7.v3.request.ActClassCondition;
import org.hl7.v3.request.ActClassControlAct;
import org.hl7.v3.request.ActClassEncounter;
import org.hl7.v3.request.ActMoodEventOccurrence;
import org.hl7.v3.request.ActMoodIntent;
import org.hl7.v3.request.ActRelationshipHasComponent;
import org.hl7.v3.request.ActRelationshipHasSubject;
import org.hl7.v3.request.ActRelationshipPertains;
import org.hl7.v3.request.ActRelationshipRefersTo;
import org.hl7.v3.request.AdxpCensusTract;
import org.hl7.v3.request.CD;
import org.hl7.v3.request.CE;
import org.hl7.v3.request.COCTMT030000ZZCitizen;
import org.hl7.v3.request.COCTMT030000ZZGuarantor;
import org.hl7.v3.request.COCTMT030000ZZOtherIDs;
import org.hl7.v3.request.COCTMT030000ZZPerson;
import org.hl7.v3.request.COCTMT030000ZZPersonalRelationship;
import org.hl7.v3.request.COCTMT030007UVBirthPlace;
import org.hl7.v3.request.COCTMT030007UVPerson;
import org.hl7.v3.request.COCTMT050000ZZIdentityDocument;
import org.hl7.v3.request.COCTMT050000ZZPatient;
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
import org.hl7.v3.request.CS;
import org.hl7.v3.request.CV;
import org.hl7.v3.request.CommunicationFunctionType;
import org.hl7.v3.request.ED;
import org.hl7.v3.request.EN;
import org.hl7.v3.request.EnFamily;
import org.hl7.v3.request.EnGiven;
import org.hl7.v3.request.EntityClassDevice;
import org.hl7.v3.request.EntityClassOrganization;
import org.hl7.v3.request.EntityClassPerson;
import org.hl7.v3.request.EntityDeterminerSpecific;
import org.hl7.v3.request.II;
import org.hl7.v3.request.IVLTS;
import org.hl7.v3.request.IVXBTS;
import org.hl7.v3.request.MCCIMT000100UV01Device;
import org.hl7.v3.request.MCCIMT000100UV01Receiver;
import org.hl7.v3.request.MCCIMT000100UV01Sender;
import org.hl7.v3.request.ON;
import org.hl7.v3.request.ObjectFactory;
import org.hl7.v3.request.PQ;
import org.hl7.v3.request.PRSSIN001004ZZ;
import org.hl7.v3.request.PRSSIN001004ZZMCAIMT700201UV01ControlActProcess;
import org.hl7.v3.request.PRSSIN001004ZZMCAIMT700201UV01Subject2;
import org.hl7.v3.request.PRSSMT001004ZZAttender;
import org.hl7.v3.request.PRSSMT001004ZZConsultant;
import org.hl7.v3.request.PRSSMT001004ZZEncounterEvent;
import org.hl7.v3.request.PRSSMT001004ZZInformant;
import org.hl7.v3.request.PRSSMT001004ZZNeed;
import org.hl7.v3.request.PRSSMT001004ZZPertinentInformation2;
import org.hl7.v3.request.PRSSMT001004ZZReason2;
import org.hl7.v3.request.PRSSMT001004ZZReference;
import org.hl7.v3.request.PRSSMT001004ZZSubject;
import org.hl7.v3.request.PRSSMT999002ZZCommonObservationEvent;
import org.hl7.v3.request.PRSSMT999003ZZAct;
import org.hl7.v3.request.PRSSMT999003ZZLocation;
import org.hl7.v3.request.PRSSMT999003ZZPerformer3;
import org.hl7.v3.request.PRSSMT999003ZZServiceFacility;
import org.hl7.v3.request.PRSSMT999005ZZCarePlan;
import org.hl7.v3.request.PRSSMT999005ZZComponent10;
import org.hl7.v3.request.PRSSMT999005ZZFinalGoal;
import org.hl7.v3.request.ParticipationAttender;
import org.hl7.v3.request.ParticipationConsultant;
import org.hl7.v3.request.ParticipationInformant;
import org.hl7.v3.request.ParticipationTargetLocation;
import org.hl7.v3.request.RoleClassAssignedEntity;
import org.hl7.v3.request.ST;
import org.hl7.v3.request.TEL;
import org.hl7.v3.request.TS;
import org.hl7.v3.request.XActMoodIntentEvent;
import org.hl7.v3.request.COCTMT030000ZZPerson.AdministrativeGenderCode;
import org.hl7.v3.request.COCTMT030000ZZPerson.MaritalStatusCode;

/**
 * Factory generica per la costruzione dei vari elementi XML via JAXB, con eventuali attributi default per specifica già valorizzati
 * 
 * @author Iacopo Sorce
 */
class DefaultElementFactory {
	static final ObjectFactory REQUEST_OBJECT_FACTORY = new ObjectFactory();
	
	
	/**
	 * Restituisce l'elemento root di tipo {@link org.hl7.v3.request.PRSSIN001004ZZ PRSSIN001004ZZ}, da usare per un messaggio di request
	 */
	static PRSSIN001004ZZ createDefaultRequest() {
		return REQUEST_OBJECT_FACTORY.createPRSSIN001004ZZ();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;receiver typeCode="RCV" /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento root {@link #createDefaultRequest() PRSSIN001004ZZ} (messaggio di request)</i>
	 */
	static MCCIMT000100UV01Receiver createDefaultReceiver() {
		MCCIMT000100UV01Receiver receiver = REQUEST_OBJECT_FACTORY.createMCCIMT000100UV01Receiver();	// oggetto di ritorno
		
		receiver.setTypeCode(CommunicationFunctionType.RCV);	// default (3.2.1.1, colonna Nota)
		
		return receiver;
	}
	
	/**
	 * Restituisce un elemento <code>&lt;sender typeCode="SND" /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento root {@link #createDefaultRequest() PRSSIN001004ZZ} (messaggio di request)</i>
	 */
	static MCCIMT000100UV01Sender createDefaultSender() {
		MCCIMT000100UV01Sender sender = REQUEST_OBJECT_FACTORY.createMCCIMT000100UV01Sender();	// oggetto di ritorno
		
		sender.setTypeCode(CommunicationFunctionType.SND);	// default (3.2.1.1, colonna Nota)
		
		return sender;
	}
	
	/**
	 * Restituisce un elemento <code>&lt;device classCode="DEV" determinerCode="INSTANCE" /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultReceiver() receiver} oppure {@link #createDefaultSender() sender}</i>
	 */
	static MCCIMT000100UV01Device createDefaultDevice() {
		MCCIMT000100UV01Device device = REQUEST_OBJECT_FACTORY.createMCCIMT000100UV01Device();
		
		device.setClassCode(EntityClassDevice.DEV);
		device.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
		
		return device;
	}
	
	/**
	 * Restituisce un elemento <code>&lt;controlActProcess classCode="CACT" moodCode="EVN" /&gt;</code>
	 */
	static PRSSIN001004ZZMCAIMT700201UV01ControlActProcess createDefaultControlActProcess() {
		PRSSIN001004ZZMCAIMT700201UV01ControlActProcess controlActProcess = REQUEST_OBJECT_FACTORY.createPRSSIN001004ZZMCAIMT700201UV01ControlActProcess();
		
		// default (3.2.1.2, colonna Nota)
		controlActProcess.setClassCode(ActClassControlAct.CACT);
		controlActProcess.setMoodCode(XActMoodIntentEvent.EVN);
		
		return controlActProcess;
	}
	
	/**
	 * Restituisce un elemento <code>&lt;subject typeCode="SUBJ" /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultControlActProcess() controlActProcess}</i>
	 * <p>
	 * <i>NB: si tratta dell'elemento <code>subject</code> di livello superiore, quello che contiene tutti gli <code>encounterEvent</code>;
	 * da non confondersi con gli elementi <code>subject</code> di livello più basso, i cui tipi XSD e Java sono diversi da questo!</i>
	 */
	static PRSSIN001004ZZMCAIMT700201UV01Subject2 createDefaultSubject() {
		PRSSIN001004ZZMCAIMT700201UV01Subject2 subject = REQUEST_OBJECT_FACTORY.createPRSSIN001004ZZMCAIMT700201UV01Subject2();
		
		// default (3.2.1.2, colonna Nota)
		subject.setTypeCode(ActRelationshipHasSubject.SUBJ);
		
		return subject;
	}
	
	/**
	 * Restituisce un elemento <code>&lt;encounterEvent classCode="ENC" moodCode="EVN" /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultSubject() controlActProcess.subject}</i>
	 */
	static PRSSMT001004ZZEncounterEvent createDefaultEncounterEvent() {
		PRSSMT001004ZZEncounterEvent encounterEvent = REQUEST_OBJECT_FACTORY.createPRSSMT001004ZZEncounterEvent();
		
		// default (3.2.1.3, colonna Nota)
		encounterEvent.setClassCode(ActClassEncounter.ENC);
		encounterEvent.setMoodCode(ActMoodEventOccurrence.EVN);
		
		return encounterEvent;
	}
	
	/**
	 * Restituisce un elemento <code>&lt;subject /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultEncounterEvent() encounterEvent}, da non confondersi con
	 * {@link #createDefaultSubject() controlActProcess.subject}</i>
	 */
	static PRSSMT001004ZZSubject createDefaultEncounterEventSubject() {
		return REQUEST_OBJECT_FACTORY.createPRSSMT001004ZZSubject();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;patient /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultEncounterEventSubject() encounterEvent.subject}</i>
	 */
	static COCTMT050000ZZPatient createDefaultPatient() {
		return REQUEST_OBJECT_FACTORY.createCOCTMT050000ZZPatient();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;subjectOf /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultPatient() encounterEvent.subject.patient}</i>
	 */
	static COCTMT050000ZZSubject createDefaultSubjectOf() {
		return REQUEST_OBJECT_FACTORY.createCOCTMT050000ZZSubject();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;identityDocument /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultSubjectOf() encounterEvent.subject.patient.subjectOf}</i>
	 */
	static COCTMT050000ZZIdentityDocument createDefaultIdentityDocument() {
		return REQUEST_OBJECT_FACTORY.createCOCTMT050000ZZIdentityDocument();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;patientPerson /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultPatient() encounterEvent.subject.patient}</i>
	 */
	static COCTMT030000ZZPerson createDefaultPatientPerson() {
		return REQUEST_OBJECT_FACTORY.createCOCTMT030000ZZPerson();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;asCitizen /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultPatientPerson() encounterEvent.subject.patient.patientPerson}</i>
	 */
	static COCTMT030000ZZCitizen createDefaultAsCitizen() {
		return REQUEST_OBJECT_FACTORY.createCOCTMT030000ZZCitizen();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;politicalOrganization classCode="ORG" /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultAsCitizen() encounterEvent.subject.patient.patientPerson.asCitizen}</i>
	 */
	static COCTMT150000UV02Organization createDefaultPoliticalOrganization() {
		COCTMT150000UV02Organization politicalOrganization = REQUEST_OBJECT_FACTORY.createCOCTMT150000UV02Organization();
		
		politicalOrganization.setClassCode(EntityClassOrganization.ORG);	// default (3.3.1, colonna Nota nei due elementi di cittadinanza)
		
		return politicalOrganization;
	}
	
	/**
	 * Restituisce un elemento <code>&lt;asOtherIDs /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultPatientPerson() encounterEvent.subject.patient.patientPerson}</i>
	 */
	static COCTMT030000ZZOtherIDs createDefaultAsOtherIDs() {
		return REQUEST_OBJECT_FACTORY.createCOCTMT030000ZZOtherIDs();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;guarantor /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultPatientPerson() encounterEvent.subject.patient.patientPerson}</i>
	 */
	static COCTMT030000ZZGuarantor createDefaultGuarantor() {
		return REQUEST_OBJECT_FACTORY.createCOCTMT030000ZZGuarantor();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;guarantorOrganization /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultGuarantor() encounterEvent.subject.patient.patientPerson.guarantor}</i>
	 * 
	 * @see	#createORGGuarantorOrganization()
	 */
	static COCTMT150002UV01Organization createDefaultGuarantorOrganization() {
		return REQUEST_OBJECT_FACTORY.createCOCTMT150002UV01Organization();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;guarantorOrganization classCode="ORG" /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultGuarantor() encounterEvent.subject.patient.patientPerson.guarantor}</i>
	 * 
	 * @see	#createDefaultGuarantorOrganization()
	 */
	static COCTMT150002UV01Organization createORGGuarantorOrganization() {
		COCTMT150002UV01Organization guarantorOrganization = createDefaultGuarantorOrganization();
		
		guarantorOrganization.setClassCode(EntityClassOrganization.ORG);
		
		return guarantorOrganization;
	}
	
	/**
	 * Restituisce un elemento <code>&lt;personalRelationship /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultPatientPerson() encounterEvent.subject.patient.patientPerson}</i>
	 */
	static COCTMT030000ZZPersonalRelationship createDefaultPersonalRelationship() {
		return REQUEST_OBJECT_FACTORY.createCOCTMT030000ZZPersonalRelationship();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;relationshipHolder1 /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultPersonalRelationship() encounterEvent.subject.patient.patientPerson.personalRelationship}</i>
	 */
	static COCTMT030007UVPerson createDefaultRelationshipHolder1() {
		return REQUEST_OBJECT_FACTORY.createCOCTMT030007UVPerson();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;birthPlace /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento
	 * {@link #createDefaultRelationshipHolder1() encounterEvent.subject.patient.patientPerson.personalRelationship.relationshipHolder1},
	 * da non confondere con il suo diretto figlio {@link #createDefaultSubBirthplace() birthplace} (tutto in minuscolo); i due elementi hanno
	 * comunque tipo XSD e Java differente</i>
	 */
	static COCTMT030007UVBirthPlace createDefaultBirthPlace() {
		return REQUEST_OBJECT_FACTORY.createCOCTMT030007UVBirthPlace();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;birthplace /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento
	 * {@link #createDefaultBirthPlace() encounterEvent.subject.patient.patientPerson.personalRelationship.relationshipHolder1.birthPlace},
	 * con il quale non confonderlo (attenzione anche al camel case dell'elemento padre); i due elementi hanno comunque tipo XSD e Java differente</i>
	 */
	static COCTMT710000UV07Place createDefaultSubBirthplace() {
		return REQUEST_OBJECT_FACTORY.createCOCTMT710000UV07Place();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;consultant typeCode="CON" /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultEncounterEvent() encounterEvent}</i>
	 */
	static PRSSMT001004ZZConsultant createDefaultConsultant() {
		PRSSMT001004ZZConsultant consultant = REQUEST_OBJECT_FACTORY.createPRSSMT001004ZZConsultant();
		
		consultant.setTypeCode(ParticipationConsultant.CON);	// default (3.3.6 nota 3)
		
		return consultant;
	}
	
	/**
	 * Restituisce un elemento <code>&lt;reason /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultEncounterEvent() encounterEvent}</i>
	 */
	static PRSSMT001004ZZReason2 createDefaultReason() {
		return REQUEST_OBJECT_FACTORY.createPRSSMT001004ZZReason2();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;need classCode="COND" moodCode="EVN" /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultReason() reason}</i>
	 */
	static PRSSMT001004ZZNeed createDefaultNeed() {
		PRSSMT001004ZZNeed need = REQUEST_OBJECT_FACTORY.createPRSSMT001004ZZNeed();
		
		// default (3.3.6 nota 4)
		need.setClassCode(ActClassCondition.COND);
		need.setMoodCode(ActMoodEventOccurrence.EVN);
		
		return need;
	}
	
	/**
	 * Restituisce un elemento <code>&lt;informant typeCode="INF" /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultNeed() need}</i>
	 */
	static PRSSMT001004ZZInformant createDefaultInformant() {
		PRSSMT001004ZZInformant informant = REQUEST_OBJECT_FACTORY.createPRSSMT001004ZZInformant();
		
		// default (3.3.6 nota 5)
		informant.setTypeCode(ParticipationInformant.INF);
		
		return informant;
	}
	
	/**
	 * Restituisce un elemento <code>&lt;assignedEntity /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultInformant() informant}</i>
	 */
	static COCTMT090003UV01AssignedEntity createDefaultAssignedEntity() {
		return REQUEST_OBJECT_FACTORY.createCOCTMT090003UV01AssignedEntity();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;assignedPerson /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultAssignedEntity() assignedEntity}</i>
	 */
	static COCTMT090003UV01Person createDefaultAssignedEntityAssignedPerson() {
		return REQUEST_OBJECT_FACTORY.createCOCTMT090003UV01Person();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;representedOrganization /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultAssignedEntity() assignedEntity}</i>
	 */
	static COCTMT150003UV03Organization createDefaultRepresentedOrganization() {
		return REQUEST_OBJECT_FACTORY.createCOCTMT150003UV03Organization();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;reference typeCode="REFR" /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultEncounterEvent() encounterEvent}</i>
	 */
	static PRSSMT001004ZZReference createDefaultReference() {
		PRSSMT001004ZZReference reference = REQUEST_OBJECT_FACTORY.createPRSSMT001004ZZReference();
		
		reference.setTypeCode(ActRelationshipRefersTo.REFR);	// default (3.3.6 nota 7)
		
		return reference;
	}
	
	/**
	 * Restituisce un elemento <code>&lt;commonObservationEvent /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultReference() reference}</i>
	 */
	static PRSSMT999002ZZCommonObservationEvent createDefaultCommonObservationEvent() {
		return REQUEST_OBJECT_FACTORY.createPRSSMT999002ZZCommonObservationEvent();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;v3:attender typeCode="ATND" /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultEncounterEvent() encounterEvent}</i>
	 */
	static PRSSMT001004ZZAttender createDefaultAttender() {
		PRSSMT001004ZZAttender attender = REQUEST_OBJECT_FACTORY.createPRSSMT001004ZZAttender();
		
		attender.setTypeCode(ParticipationAttender.ATND);	// default (3.3.6 nota 1)
		
		return attender;
	}
	
	/**
	 * Restituisce un elemento <code>&lt;pertinentInformation2 typeCode="PERT" /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultEncounterEvent() encounterEvent}</i>
	 */
	static PRSSMT001004ZZPertinentInformation2 createDefaultPertinentInformation2() {
		PRSSMT001004ZZPertinentInformation2 pertinentInformation2 = REQUEST_OBJECT_FACTORY.createPRSSMT001004ZZPertinentInformation2();
		
		pertinentInformation2.setTypeCode(ActRelationshipPertains.PERT);	// default (3.3.6 nota 8)
		
		return pertinentInformation2;
	}
	
	/**
	 * Restituisce un elemento <code>&lt;carePlan classCode="PCPR" moodCode="INT" /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultPertinentInformation2() pertinentInformation2}</i>
	 */
	static PRSSMT999005ZZCarePlan createDefaultCarePlan() {
		PRSSMT999005ZZCarePlan carePlan = REQUEST_OBJECT_FACTORY.createPRSSMT999005ZZCarePlan();
		
		// default (3.3.6 nota 9)
		carePlan.setClassCode(ActClassCareProvision.PCPR);
		carePlan.setMoodCode(ActMoodIntent.INT);
		
		return carePlan;
	}
	
	/**
	 * Restituisce un elemento <code>&lt;finalGoal /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultCarePlan() carePlan}</i>
	 */
	static PRSSMT999005ZZFinalGoal createDefaultFinalGoal() {
		return REQUEST_OBJECT_FACTORY.createPRSSMT999005ZZFinalGoal();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;observationGoal /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultFinalGoal() finalGoal}</i>
	 */
	static PRSSMT999002ZZCommonObservationEvent createDefaultObservationGoal() {
		return REQUEST_OBJECT_FACTORY.createPRSSMT999002ZZCommonObservationEvent();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;component2 typeCode="COMP" /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultCarePlan() carePlan}</i>
	 * <p>
	 * <i>NB: la classe (sia XSD che Java) che implementa component2 si chiama PRSSMT999005ZZComponent10, fare attenzione a possibili confusioni!</i>
	 */
	static PRSSMT999005ZZComponent10 createDefaultComponent2() {
		PRSSMT999005ZZComponent10 component2 = REQUEST_OBJECT_FACTORY.createPRSSMT999005ZZComponent10();
		
		component2.setTypeCode(ActRelationshipHasComponent.COMP);	// default (3.3.6 ultima nota)
		
		return component2;
	}
	
	/**
	 * Restituisce un elemento <code>&lt;observation /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultComponent2() component2}</i>
	 */
	static PRSSMT999002ZZCommonObservationEvent createDefaultObservation() {
		return REQUEST_OBJECT_FACTORY.createPRSSMT999002ZZCommonObservationEvent();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;act /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultComponent2() component2}</i>
	 */
	static PRSSMT999003ZZAct createDefaultAct() {
		return REQUEST_OBJECT_FACTORY.createPRSSMT999003ZZAct();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;performer /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultAct() act}</i>
	 */
	static PRSSMT999003ZZPerformer3 createDefaultPerformer() {
		return REQUEST_OBJECT_FACTORY.createPRSSMT999003ZZPerformer3();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;assignedEntity /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultPerformer() performer}</i>
	 */
	static COCTMT090000UV01AssignedEntity createDefaultPerformerAssignedEntity() {
		return REQUEST_OBJECT_FACTORY.createCOCTMT090000UV01AssignedEntity();
	}
	
	/**
	 * Restituisce un elemento <code>&lt;assignedPerson classCode="ASSIGNED" /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultPerformerAssignedEntity() assignedEntity}</i>
	 */
	static COCTMT090000UV01Person createDefaultPerformerAssignedEntityAssignedPerson() {
		COCTMT090000UV01Person assignedPerson = REQUEST_OBJECT_FACTORY.createCOCTMT090000UV01Person();	// oggetto di ritorno
		
		assignedPerson.setClassCode(EntityClassPerson.ASSIGNED);	// default (3.3.6 nota 2)
		
		return assignedPerson;
	}
	
	/**
	 * Restituisce un elemento <code>&lt;location typeCode="LOC" /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultAct() act}</i>
	 */
	static PRSSMT999003ZZLocation createDefaultLocation() {
		PRSSMT999003ZZLocation location = REQUEST_OBJECT_FACTORY.createPRSSMT999003ZZLocation();
		
		location.setTypeCode(ParticipationTargetLocation.LOC);	// default (3.3.6 nota 6)
		
		return location;
	}
	
	/**
	 * Restituisce un elemento <code>&lt;serviceFacility /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultLocation() location}</i>
	 */
	static PRSSMT999003ZZServiceFacility createDefaultServiceFacility() {
		return REQUEST_OBJECT_FACTORY.createPRSSMT999003ZZServiceFacility();
	}
	
	
	/**
	 * Restituisce un elemento <code>&lt;assignedPerson classCode="ASSIGNED" /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultConsultant() consultant} oppure {@link #createDefaultAttender() attender}</i>
	 * <p>
	 * <i>NB: da non confondersi con l'elemento {@link #createDefaultSubAssignedPerson() assignedPerson} di livello più basso, suo diretto figlio,
	 * e il cui tipo XSD e Java è diverso da questo!</i>
	 */
	static COCTMT090103UV01AssignedPerson createDefaultAssignedPerson() {
		COCTMT090103UV01AssignedPerson assignedPerson = REQUEST_OBJECT_FACTORY.createCOCTMT090103UV01AssignedPerson();
		
		assignedPerson.setClassCode(RoleClassAssignedEntity.ASSIGNED);	// default (3.3.6 nota 2)
		
		return assignedPerson;
	}
	
	/**
	 * Restituisce un elemento <code>&lt;assignedPerson /&gt;</code>
	 * <p>
	 * <i>Figlio dell'elemento {@link #createDefaultAssignedPerson() assignedPerson}</i>
	 * <p>
	 * <i>NB: fare attenzione all'omonimia con l'elemento padre!</i>
	 */
	static COCTMT090103UV01Person createDefaultSubAssignedPerson() {
		return REQUEST_OBJECT_FACTORY.createCOCTMT090103UV01Person();
	}
	
	
	static AD createDefaultAD() {
		return REQUEST_OBJECT_FACTORY.createAD();
	}
	
	static AdxpCensusTract createDefaultAdxpCensusTract() {
		return REQUEST_OBJECT_FACTORY.createAdxpCensusTract();
	}
	
	static CD createDefaultCD() {
		return REQUEST_OBJECT_FACTORY.createCD();
	}
	
	static CE createDefaultCE() {
		return REQUEST_OBJECT_FACTORY.createCE();
	}
	
	static AdministrativeGenderCode createDefaultAdministrativeGenderCode() {
		return REQUEST_OBJECT_FACTORY.createCOCTMT030000ZZPersonAdministrativeGenderCode();
	}
	
	static MaritalStatusCode createDefaultMaritalStatusCode() {
		return REQUEST_OBJECT_FACTORY.createCOCTMT030000ZZPersonMaritalStatusCode();
	}
	
	static CS createDefaultCS() {
		return REQUEST_OBJECT_FACTORY.createCS();
	}
	
	static CV createDefaultCV() {
		return REQUEST_OBJECT_FACTORY.createCV();
	}
	
	static ED createDefaultED() {
		return REQUEST_OBJECT_FACTORY.createED();
	}
	
	static EN createDefaultEN() {
		return REQUEST_OBJECT_FACTORY.createEN();
	}
	
	static EnGiven createDefaultEnGiven() {
		return REQUEST_OBJECT_FACTORY.createEnGiven();
	}
	
	static EnFamily createDefaultEnFamily() {
		return REQUEST_OBJECT_FACTORY.createEnFamily();
	}
	
	static II createDefaultII() {
		return REQUEST_OBJECT_FACTORY.createII();
	}
	
	static IVLTS createDefaultIVLTS() {
		return REQUEST_OBJECT_FACTORY.createIVLTS();
	}
	
	static IVXBTS createDefaultIVXBTS() {
		return REQUEST_OBJECT_FACTORY.createIVXBTS();
	}
	
	static ON createDefaultON() {
		return REQUEST_OBJECT_FACTORY.createON();
	}
	
	static PQ createDefaultPQ() {
		return REQUEST_OBJECT_FACTORY.createPQ();
	}
	
	static ST createDefaultST() {
		return REQUEST_OBJECT_FACTORY.createST();
	}
	
	static TEL createDefaultTEL() {
		return REQUEST_OBJECT_FACTORY.createTEL();
	}
	
	static TS createDefaultTS() {
		return REQUEST_OBJECT_FACTORY.createTS();
	}
}
