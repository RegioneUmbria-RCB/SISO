package it.umbriadigitale.interscambio.factory;

import static it.umbriadigitale.interscambio.factory.DatatypesModFactory.*;
import static it.umbriadigitale.interscambio.factory.DefaultElementFactory.*;
import static it.umbriadigitale.interscambio.factory.ElementFactory.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hl7.v3.request.COCTMT030000ZZGuarantor;
import org.hl7.v3.request.COCTMT030000ZZPerson;
import org.hl7.v3.request.COCTMT050000ZZPatient;
import org.hl7.v3.request.PRSSMT001004ZZSubject;

import it.umbriadigitale.interscambio.constants.OIDDefaults;
import it.umbriadigitale.interscambio.data.wrapper.AssistitoWrapper;
import it.umbriadigitale.interscambio.data.wrapper.PersonalRelationshipWrapper;
import it.umbriadigitale.interscambio.enums.EAddress;
import it.umbriadigitale.interscambio.enums.EGuarantor;

class SubjectFactory implements OIDDefaults {
	/*
		<v3:subject>
			<v3:patient>
				<v3:addr use="comune residenza">
					<v3:censusTract>156146</v3:censusTract><!--comune residenza assistito-->
				</v3:addr>
				<v3:addr use="regione residenza">
					<v3:censusTract>03</v3:censusTract><!--regione residenza assistito-->
				</v3:addr>
				<v3:addr use="nazione residenza">
					<v3:censusTract>IT</v3:censusTract><!--nazione residenza assistito-->
				</v3:addr>
				<v3:addr use="comune domicilio">
					<v3:censusTract>156146</v3:censusTract><!--comune domicilio assistito-->
				</v3:addr>
				<v3:telecom value="3662345"/><!--numero telefono assistito-->
				<v3:telecom value="3662345"/><!--numero telefono mobile assistito-->
				<v3:telecom value="indirizzo@mail.com"/><!--email assistito-->
				<v3:subjectOf>
					[...]
				</v3:subjectOf>
				<v3:patientPerson>
					[...]
				</v3:patientPerson>
			</v3:patient>
		</v3:subject>
	 */
	static PRSSMT001004ZZSubject createSubject(AssistitoWrapper assistitoData) {
		PRSSMT001004ZZSubject subject = createDefaultEncounterEventSubject();	// oggetto di ritorno
		
		COCTMT050000ZZPatient patient = createDefaultPatient();
		
		// subject.patient.addr
		patient.getAddr().add(createADAddrCensusTract(assistitoData.getComuneResidenzaCode(), EAddress.COMUNE_RESIDENZA));
		patient.getAddr().add(createADAddrCensusTract(assistitoData.getRegioneResidenzaCode(), EAddress.REGIONE_RESIDENZA));
		patient.getAddr().add(createADAddrCensusTract(assistitoData.getNazioneResidenzaCode(), EAddress.NAZIONE_RESIDENZA));
		patient.getAddr().add(createADAddrCensusTract(assistitoData.getComuneDomicilioCode(), EAddress.COMUNE_DOMICILIO));
		
		// subject.patient.telecom
		patient.getTelecom().add(createTELValue(assistitoData.getNumeroTelefono()));
		patient.getTelecom().add(createTELValue(assistitoData.getNumeroCellulare()));
		patient.getTelecom().add(createTELValue(assistitoData.getEmail()));
		
		// subject.patient.subjectOf
		patient.setSubjectOf(createSubjectOf(assistitoData.getEstremiDocumento(), assistitoData.getTipoDocumentoCode()));
		
		// subject.patient.patientPerson
		patient.setPatientPerson(REQUEST_OBJECT_FACTORY.createCOCTMT050000ZZPatientPatientPerson(createPatientPerson(assistitoData)));
		
		
		subject.setPatient(patient);
		
		return subject;
	}
	
	
	/*
		<v3:patientPerson>
			<v3:id root="2.16.840.1.113883.2.9.4.1.2" extension="aaa"></v3:id>
			<v3:name>
				<v3:given>Nome Assistito</v3:given><!--Nome Assistito-->
				<v3:family>Cognome Assistito</v3:family><!--Cognome Assistito-->
			</v3:name>
			<v3:administrativeGenderCode code="1" codeSystem="2.16.840.1.113883.5.1" classCode="PLC"/><!--genere assistito-->
			<v3:birthTime value="10011982"/><!--data nascita assistito-->
			<v3:maritalStatusCode code="1" codeSystem="2.16.840.1.113883.5.2"/><!--stato civile assistito-->
			<v3:educationLevelCode code="8" codeSystem="2.16.840.1.113883.2.9.6.2.8"/><!--titolo di studio assistito-->
			<v3:asCitizen>
				<v3:politicalOrganization classCode="ORG">
					<v3:id  root="2.16.840.1.113883.2.9.6.2.5" extension="380"/><!-- Cittadinanza assistito-->
				</v3:politicalOrganization>
			</v3:asCitizen>
			<v3:asCitizen>
				<v3:politicalOrganization classCode="ORG">
					<v3:id  root="2.16.840.1.113883.2.9.6.2.5" extension="380"/><!-- Seconda cittadinanza assistito-->
				</v3:politicalOrganization>
			</v3:asCitizen>
			<v3:asOtherIDs>
				<v3:id root="2.16.840.1.113883.2.9.4.1.4" extension="12345678944453"></v3:id><!--tessera team assistito-->
				<v3:id root="2.16.840.1.113883.2.9.4.1.2" extension="CDFCDF90DS23P089Y"></v3:id><!--codice fiscale assistito-->
			</v3:asOtherIDs>
			<v3:guarantor>
				[...]
			</v3:guarantor>
			<v3:personalRelationship> <!-- Componenti Nucleo Familiare -->
				[...]
			</v3:personalRelationship>
			<v3:personalRelationship> <!-- Componenti Rete Sociale -->
				[...]
			</v3:personalRelationship>
			<v3:birthPlace>
				<v3:birthplace>
					<v3:addr>
						<v3:code code="015146" codeSystem="2.16.840.1.113883.2.9.4.2.3"/><!-- comune di nascita assistito-->
					</v3:addr>
				</v3:birthplace>
			</v3:birthPlace>
		</v3:patientPerson>
	 */
	private static COCTMT030000ZZPerson createPatientPerson(AssistitoWrapper assistitoData) {
		COCTMT030000ZZPerson patientPerson = createDefaultPatientPerson();	// oggetto di ritorno
		
		// patientPerson.id
		patientPerson.getId().add(createIIRootExtension(OID_PATIENTPERSON_CODICE_FISCALE, assistitoData.getId()));	// TODO confermare OID
		
		// patientPerson.name
		patientPerson.getName().add(createENNameGivenFamily(assistitoData.getNome(), assistitoData.getCognome()));
		
		// patientPerson.administrativeGenderCode
		patientPerson.setAdministrativeGenderCode(createAdministrativeGenderCode(assistitoData.getGenereCode(), "PLC"));
		
		// patientPerson.birthTime
		patientPerson.setBirthTime(createTSValue(assistitoData.getDataNascitaText()));
		
		// patientPerson.maritalStatusCode
		patientPerson.setMaritalStatusCode(createMaritalStatusCode(assistitoData.getStatoCivileCode()));
		
		// patientPerson.educationLevelCode
		patientPerson.setEducationLevelCode(createCECodeCodeSystem(assistitoData.getTitoloStudioCode(), OID_EDUCATION_LEVEL_CODE));
		
		// patientPerson.asCitizen
		patientPerson.getAsCitizen().add(createAsCitizenCittadinanza(assistitoData.getCittadinanzaCode()));
		patientPerson.getAsCitizen().add(createAsCitizenCittadinanza(assistitoData.getSecondaCittadinanzaCode()));
		
		// patientPerson.asOtherIDs
		patientPerson.getAsOtherIDs().add(createAsOtherIDs(assistitoData.getTesseraTEAM(), assistitoData.getCodiceFiscale()));
		
		// patientPerson.guarantor
		patientPerson.getGuarantor().addAll(
			createGuarantors(assistitoData.getAtsResidenzaCode(), assistitoData.getAsstResidenzaCode(), assistitoData.getAmbitoResidenzaCode()));
		
		// patientPerson.personalRelationship (Nucleo Familiare)
		for (PersonalRelationshipWrapper nucleoFamiliarePersonalRelationship : assistitoData.getNucleoFamiliare()) {
			patientPerson.getPersonalRelationship().add(createPersonalRelationship(nucleoFamiliarePersonalRelationship));
		}
		
		// patientPerson.personalRelationship (Rete Sociale)
		for (PersonalRelationshipWrapper reteSocialePersonalRelationship : assistitoData.getReteSociale()) {
			patientPerson.getPersonalRelationship().add(createPersonalRelationship(reteSocialePersonalRelationship));
		}
		
		// patientPerson.birthPlace
		patientPerson.setBirthPlace(
			REQUEST_OBJECT_FACTORY.createCOCTMT030000ZZPersonBirthPlace(createBirthPlaceCode(assistitoData.getComuneNascitaCode())));
		
		
		return patientPerson;
	}


	/*
		<v3:guarantor>
			<v3:guarantorOrganization classCode="ORG">
				<v3:id root="2.16.840.1.113883.2.9.2.30.4.11" extension="321"/> <!--ATS residenza assistito-->
			</v3:guarantorOrganization>
		</v3:guarantor>
		<v3:guarantor>
			<v3:guarantorOrganization classCode="ORG">
				<v3:id root="2.16.840.1.113883.2.9.2.30.4.11" extension="701"/><!--ASST residenza assistito-->
			</v3:guarantorOrganization>
		</v3:guarantor>
		<v3:guarantor>
			<v3:guarantorOrganization>
				<v3:id root="2.16.840.1.113883.2.9.2.30.3.2.3.1.1.8" extension="30100"/><!--Ambito residenza assistito-->
			</v3:guarantorOrganization>
		</v3:guarantor>
	 */
	private static List<COCTMT030000ZZGuarantor> createGuarantors(String atsResidenzaCode, String asstResidenzaCode, String ambitoResidenzaCode) {
		Map<EGuarantor, String> guarantorMap = new HashMap<>();
		
		guarantorMap.put(EGuarantor.ATS_RESIDENZA_ASSISTITO, atsResidenzaCode);
		guarantorMap.put(EGuarantor.ASST_RESIDENZA_ASSISTITO, asstResidenzaCode);
		guarantorMap.put(EGuarantor.AMBITO_RESIDENZA_ASSISTITO, ambitoResidenzaCode);
		
		return buildGuarantors(guarantorMap);
	}
}
