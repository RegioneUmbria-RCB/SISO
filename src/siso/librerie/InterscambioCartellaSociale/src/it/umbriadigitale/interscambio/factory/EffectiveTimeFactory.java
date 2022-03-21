package it.umbriadigitale.interscambio.factory;

import static it.umbriadigitale.interscambio.factory.DatatypesModFactory.*;
import static it.umbriadigitale.interscambio.factory.DefaultElementFactory.*;

import org.hl7.v3.request.IVLTS;

class EffectiveTimeFactory {
	private static IVLTS createEffectiveTime(String lowValue, String highValue, String dischargeDispositionCodeValue, String widthValue) {
		IVLTS effectiveTime = createDefaultIVLTS();
		
		// effectiveTime.low
		if (lowValue != null) {
			effectiveTime.getRest().add(REQUEST_OBJECT_FACTORY.createIVLTSLow(createIVXBTSValue(lowValue)));
		}
		
		// effectiveTime.high
		if (highValue != null) {
			effectiveTime.getRest().add(REQUEST_OBJECT_FACTORY.createIVLTSHigh(createIVXBTSValue(highValue)));
		}
		
		// effectiveTime.width
		if (widthValue != null) {
			effectiveTime.getRest().add(REQUEST_OBJECT_FACTORY.createIVLTSWidth(createPQValue(widthValue)));
		}
		
		// effectiveTime.dischargeDispositionCodeValue
		if (dischargeDispositionCodeValue != null) {
			effectiveTime.getRest().add(
				REQUEST_OBJECT_FACTORY.createIVLTSDischargeDispositionCode(
					createCVCodeCodeSystem(dischargeDispositionCodeValue, OID_CODICE_ESITO_FASE)));
		}
		
		
		return effectiveTime;
	}
	
	/*
		<v3:effectiveTime>
			<v3:low value="123"/> <!-- Data ora inizio fase -->
			<v3:high value="123"/> <!-- Data ora fine fase -->
			<v3:dischargeDispositionCode code="2.16.840.1.113883.2.9.2.30.3.2.3.1.1.2" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.3.1.1.2"/> <!-- codice dell'esito della fase -->
		</v3:effectiveTime>
	 */
	static IVLTS createEffectiveTimeLowHighDischargeDispositionCodeValue(String lowValue, String highValue, String dischargeDispositionCodeValue) {
		return createEffectiveTime(lowValue, highValue, dischargeDispositionCodeValue, null);
	}
	
	/*
		<v3:effectiveTime>
			<v3:low value="123"/> <!-- Data ora inizio fase -->
			<v3:high value="123"/> <!-- Data ora fine fase -->
		</v3:effectiveTime>
	 */
	static IVLTS createEffectiveTimeLowHigh(String lowValue, String highValue) {
		return createEffectiveTime(lowValue, highValue, null, null);
	}
	
	/*
		<v3:effectiveTime>
			<v3:low value="123"/> <!-- Data ora inizio fase -->
		</v3:effectiveTime>
	 */
	static IVLTS createEffectiveTimeLow(String lowValue) {
		return createEffectiveTime(lowValue, null, null, null);
	}
	
	/*
		<v3:effectiveTime>
			<v3:high value="123"/> <!-- Data ora fine fase -->
		</v3:effectiveTime>
	 */
	static IVLTS createEffectiveTimeHigh(String highValue) {
		return createEffectiveTime(null, highValue, null, null);
	}
	
	/*
		<v3:effectiveTime> 
			<v3:width value="12"/> <!--Tempistiche di monitoraggio-->
		</v3:effectiveTime>
	 */
	static IVLTS createEffectiveTimeWidth(String widthValue) {
		return createEffectiveTime(null, null, null, widthValue);
	}
}
