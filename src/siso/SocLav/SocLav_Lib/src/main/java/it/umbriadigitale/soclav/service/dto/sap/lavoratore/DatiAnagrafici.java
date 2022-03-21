package it.umbriadigitale.soclav.service.dto.sap.lavoratore;

import it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAnagrafici.DatiPersonali;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAnagrafici.Indirizzo;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAnagrafici.Recapito;

public class DatiAnagrafici {

	/*
	<datianagrafici>        
		<datipersonali>
			<codicefiscale>BTTCLD87L62E690I</codicefiscale>
			<cognome>BATTISTONI</cognome>
			<nome>CLAUDIA</nome>
			<sesso>F</sesso>
			<datanascita>1992-12-23</datanascita>
			<codcomune>A944</codcomune>
			<codcittadinanza>000</codcittadinanza> 
		</datipersonali>        
		<domicilio>
			<codcomune>A462</codcomune>
			<cap>63100</cap>
			<indirizzo>VIA M.L.KING 41 A</indirizzo>
		</domicilio>        
		<recapiti>
			<telefono>3912547896301</telefono>
			<email>email@email.com</email>        
		</recapiti>    
	</datianagrafici>    
	 * */
	private DatiPersonali datipersonali;
	private Indirizzo domicilio;
	private Indirizzo residenza;
	private Recapito recapiti;
	
	public DatiPersonali getDatipersonali() {
		return datipersonali;
	}
	public void setDatipersonali(DatiPersonali datipersonali) {
		this.datipersonali = datipersonali;
	}
	public Indirizzo getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(Indirizzo domicilio) {
		this.domicilio = domicilio;
	}
	public Indirizzo getResidenza() {
		return residenza;
	}
	public void setResidenza(Indirizzo residenza) {
		this.residenza = residenza;
	}
	public Recapito getRecapiti() {
		return recapiti;
	}
	public void setRecapiti(Recapito recapiti) {
		this.recapiti = recapiti;
	}
}
