package it.umbriadigitale.interscambio.factory;

import static it.umbriadigitale.interscambio.factory.DefaultElementFactory.*;

import org.hl7.v3.request.AD;
import org.hl7.v3.request.AdxpCensusTract;
import org.hl7.v3.request.CD;
import org.hl7.v3.request.CE;
import org.hl7.v3.request.CS;
import org.hl7.v3.request.CV;
import org.hl7.v3.request.ED;
import org.hl7.v3.request.EN;
import org.hl7.v3.request.EnFamily;
import org.hl7.v3.request.EnGiven;
import org.hl7.v3.request.II;
import org.hl7.v3.request.IVXBTS;
import org.hl7.v3.request.ON;
import org.hl7.v3.request.PQ;
import org.hl7.v3.request.ST;
import org.hl7.v3.request.TEL;
import org.hl7.v3.request.TS;
import org.hl7.v3.request.COCTMT030000ZZPerson.AdministrativeGenderCode;
import org.hl7.v3.request.COCTMT030000ZZPerson.MaritalStatusCode;

import it.umbriadigitale.interscambio.constants.OIDDefaults;
import it.umbriadigitale.interscambio.enums.EAddress;

/**
 * Factory degli element contenuti nei file di XSD di specifica <code>datatypes_mod.xsd</code> e <code>datatypes-base_mod.xsd</code>; si tratta
 * di elementi di carattere generico (es: indirizzi, nomi) utilizzati in svariati punti
 */
class DatatypesModFactory implements OIDDefaults {
	/*
		<v3:addr use="comune residenza">
			<v3:censusTract>156146</v3:censusTract><!--comune residenza assistito-->
		</v3:addr>
	*/
	static AD createADAddrCensusTract(String value, EAddress addressType) {
		AD ad = createDefaultAD();
		ad.setUse(addressType.getUse());
		
		AdxpCensusTract censusTract = createDefaultAdxpCensusTract();
		censusTract.getContent().add(value);
		
		ad.getContent().add(REQUEST_OBJECT_FACTORY.createADCensusTract(censusTract));
		
		return ad;
	}
	
	/*
		<v3:addr>
			<v3:code code="015146" codeSystem="2.16.840.1.113883.2.9.4.2.3"/><!-- comune di nascita assistito-->
		</v3:addr>
	 */
	static AD createADAddrCode(String codeValue, String codeSystemValue) {
		AD ad = createDefaultAD();
		
		CD code = createCDCodeCodeSystem(codeValue, codeSystemValue);
		
		ad.getContent().add(REQUEST_OBJECT_FACTORY.createADCode(code));
		
		return ad;
	}
	
	/*
		<v3:code code="4" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.3.1.1.6"/> <!-- tipo documento-->
	 */
	static CD createCDCodeCodeSystem(String code, String codeSystem) {
		CD cd = createDefaultCD();
		cd.setCode(code);
		cd.setCodeSystem(codeSystem);
		
		return cd;
	}
	
	/*
		<v3:code code="3" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.3.1.1.22"/><!-- Tipologia relazione familiare -->
	 */
	static CE createCECodeCodeSystem(String code, String codeSystem) {
		CE ce = createDefaultCE();
		ce.setCode(code);
		ce.setCodeSystem(codeSystem);
		
		return ce;
	}
	
	/*
		<v3:value code="indicatore_esito" type="CD"/> <!-- Indicatore di esito-->
	 */
	static CE createCECodeType(String code, String type) {
		CE ce = createDefaultCE();
		ce.setCode(code);
		ce.setType(type);
		
		return ce;
	}
	
	/*
		<v3:administrativeGenderCode code="1" codeSystem="2.16.840.1.113883.5.1" classCode="PLC"/><!--genere assistito-->
	 */
	static AdministrativeGenderCode createAdministrativeGenderCode(String code, String classCode) {
		AdministrativeGenderCode administrativeGenderCode = createDefaultAdministrativeGenderCode();
		administrativeGenderCode.setCode(code);
		administrativeGenderCode.setCodeSystem(OID_ADMINISTRATIVE_GENDER_CODE);
		administrativeGenderCode.setClassCode(classCode);
		
		return administrativeGenderCode;
	}
	
	/*
		<v3:maritalStatusCode code="1" codeSystem="2.16.840.1.113883.5.2"/><!--stato civile assistito-->
	 */
	static MaritalStatusCode createMaritalStatusCode(String code) {
		MaritalStatusCode maritalStatusCode = createDefaultMaritalStatusCode();
		maritalStatusCode.setCode(code);
		maritalStatusCode.setCodeSystem(OID_MARITAL_STATUS_CODE);
		
		return maritalStatusCode;
	}
	
	/*
		<v3:statusCode code="Stato_Fase"/> <!-- Stato della fase -->
	 */
	static CS createCSCode(String code) {
		CS cs = createDefaultCS();
		cs.setCode(code);
		
		return cs;
	}
	
	/*
		<v3:acceptAckCode code="ackCode" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.4.3"/>
	 */
	static CS createCSCodeCodeSystem(String code, String codeSystem) {
		CS cs = createCSCode(code);
		cs.setCodeSystem(codeSystem);
		
		return cs;
	}
	
	/*
		<v3:dischargeDispositionCode code="xxx" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.3.1.1.2"/> <!-- codice dell'esito della fase -->
	 */
	static CV createCVCodeCodeSystem(String code, String codeSystem) {
		CV cv = createDefaultCV();
		cv.setCode(code);
		cv.setCodeSystem(codeSystem);
		
		return cv;
	}
	
	/*
		<v3:text>textValue</v3:text>
	 */
	static ED createEDWithText(String textValue) {
		ED ed = createDefaultED();
		ed.getContent().add(textValue);
		
		return ed;
	}
	
	/*
	    <v3:name>
			<v3:given>Nome Assistito</v3:given><!--Nome Assistito-->
			<v3:family>Cognome Assistito</v3:family><!--Cognome Assistito-->
		</v3:name>
	*/
	static EN createENNameGivenFamily(String given, String family) {
		EN en = createDefaultEN();
		
		EnGiven enGiven = createDefaultEnGiven();
		EnFamily enFamily = createDefaultEnFamily();
		
		enGiven.getContent().add(given);
		enFamily.getContent().add(family);
		
		en.getContent().add(REQUEST_OBJECT_FACTORY.createENGiven(enGiven));
		en.getContent().add(REQUEST_OBJECT_FACTORY.createENFamily(enFamily));
		
		return en;
	}
	
	/*
		<v3:id root="2.16.840.1.113883.2.9.2.30.3.2.3.1.1.7" extension="0784374394309"></v3:id><!--estremi documento-->
	 */
	static II createIIRootExtension(String root, String extension) {
		II id = createDefaultII();
		id.setRoot(root);
		id.setExtension(extension);
		
		return id;
	}
	
	/*
		<v3:low value="123"/> <!-- Data ora inizio fase -->
	 */
	static IVXBTS createIVXBTSValue(String value) {
		IVXBTS ivxbts = createDefaultIVXBTS();
		ivxbts.setValue(value);
		
		return ivxbts;
	}
	
	/*
		<v3:name>Altro ente</v3:name>
	 */
	static ON createONWithText(String textValue) {
		ON on = createDefaultON();
		on.getContent().add(textValue);
		
		return on;
	}
	
	/*
		<v3:width value="12"/> <!--Tempistiche di monitoraggio-->
	 */
	static PQ createPQValue(String value) {
		PQ pq = createDefaultPQ();
		pq.setValue(value);
		
		return pq;
	}
	
	/*
		<v3:title>oggetto del progetto</v3:title> <!-- Oggetto del progetto individuale -->
	 */
	static ST createSTWithText(String textValue) {
		ST st = createDefaultST();
		st.getContent().add(textValue);
		
		return st;
	}
	
	/*
		<v3:telecom value="3662345"/><!--numero telefono assistito-->
	 */
	static TEL createTELValue(String value) {
		TEL tel = createDefaultTEL();
		tel.setValue(value);
		
		return tel;
	}
	
	/*
		<v3:birthTime value="10011982"/><!--data nascita assistito-->
	 */
	static TS createTSValue(String value) {
		TS ts = createDefaultTS();
		ts.setValue(value);
		
		return ts;
	}
}
