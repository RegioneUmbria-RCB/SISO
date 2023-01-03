package it.webred.cs.json.valSinba.ver1;

import it.webred.cs.json.dto.JsonBaseBean;
import it.webred.cs.json.valSinba.ver1.tabs.DatiAffidamentoBean;
import it.webred.cs.json.valSinba.ver1.tabs.DatiDisabilitaBean;
import it.webred.cs.json.valSinba.ver1.tabs.DatiFamigliaBean;
import it.webred.cs.json.valSinba.ver1.tabs.DatiGeneraliBean;
import it.webred.cs.json.valSinba.ver1.tabs.DatiSegnalazioniBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValSinbaBean extends JsonBaseBean {
	private static final String JsnonName = "Scheda SINBA"; //	private static final String JsnonName = "SchedaSinbaBean";
	
	private DatiGeneraliBean datiGenerali;
	private DatiFamigliaBean datiFamiglia;
	private DatiDisabilitaBean datiDisabilita;
	private DatiSegnalazioniBean datiSegnalazioni;
	private DatiAffidamentoBean datiAffidamento;
	
	
	public ValSinbaBean()
	{
		this.datiGenerali = new DatiGeneraliBean();
		this.datiFamiglia = new DatiFamigliaBean();
		this.datiDisabilita = new DatiDisabilitaBean();
		this.datiSegnalazioni = new DatiSegnalazioniBean();
		this.datiAffidamento = new DatiAffidamentoBean();
//		this.prescrizioniSpecialistiche = new PrescrizioniSpecialisticheBean();
	}

	public ValSinbaBean(ValSinbaBean jsonOriginal) {

		try {
			if (jsonOriginal != null) {
				datiGenerali = (DatiGeneraliBean) BeanUtils.cloneBean(jsonOriginal.getDatiGenerali());
				datiFamiglia = (DatiFamigliaBean) BeanUtils.cloneBean(jsonOriginal.getDatiFamiglia());
				datiDisabilita = (DatiDisabilitaBean) BeanUtils.cloneBean(jsonOriginal.getDatiDisabilita());
				datiSegnalazioni = (DatiSegnalazioniBean) BeanUtils.cloneBean(jsonOriginal.getDatiSegnalazioni());
				datiAffidamento = (DatiAffidamentoBean) BeanUtils.cloneBean(jsonOriginal.getDatiAffidamento());
//				prescrizioniSpecialistiche = (PrescrizioniSpecialisticheBean) BeanUtils.cloneBean(jsonOriginal.getPrescrizioniSpecialistiche());
			}
			else
			{
				this.datiGenerali = new DatiGeneraliBean();
				this.datiFamiglia = new DatiFamigliaBean();
				this.datiDisabilita = new DatiDisabilitaBean();
				this.datiSegnalazioni = new DatiSegnalazioniBean();
				this.datiAffidamento = new DatiAffidamentoBean();
//				this.prescrizioniSpecialistiche = new PrescrizioniSpecialisticheBean();
			}

		} catch (Exception ex) {
			logger.error(ex);
			throw new Error(ex);
		}
	}

	public DatiGeneraliBean getDatiGenerali() {
		return datiGenerali;
	}
	
	public void setDatiGenerali(DatiGeneraliBean datiGenerali) {
		this.datiGenerali = datiGenerali;
	}
	
	public DatiFamigliaBean getDatiFamiglia() {
		return datiFamiglia;
	}

	public void setDatiFamiglia(DatiFamigliaBean datiFamiglia) {
		this.datiFamiglia = datiFamiglia;
	}

	public DatiDisabilitaBean getDatiDisabilita() {
		return datiDisabilita;
	}

	public void setDatiDisabilita(DatiDisabilitaBean datiDisabilita) {
		this.datiDisabilita = datiDisabilita;
	}
	
	public DatiSegnalazioniBean getDatiSegnalazioni() {
		return datiSegnalazioni;
	}

	public void setDatiSegnalazioni(DatiSegnalazioniBean datiSegnalazioni) {
		this.datiSegnalazioni = datiSegnalazioni;
	}

	public DatiAffidamentoBean getDatiAffidamento() {
		return datiAffidamento;
	}

	public void setDatiAffidamento(DatiAffidamentoBean datiAffidamento) {
		this.datiAffidamento = datiAffidamento;
	}


	public static String getJsnonname() {
		return JsnonName;
	}

	public Date getDataValutazione() {
		return this.getDatiGenerali().getDataRiferimentoValutazione();
	}

	public void setDataValutazione(Date dataValutazione) {
		this.getDatiGenerali().setDataRiferimentoValutazione(dataValutazione);
	}

	public String getDescrizioneScheda() {
		return getJsnonname();
	}

	// /**JsonBaseBean Methods*///
	@Override
	public List<String> checkObbligatorieta() {
		List<String> messagges = new LinkedList<String>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
			// TODO: check campi obbligatori frontend
			// TAB Dati Generali
			if (getDatiGenerali().getDataRiferimentoValutazione() == null)
				messagges.add("Dati Generali: Data di riferimento valutazione è un campo obbligatorio");
			
			if (getDatiGenerali().getCodiceAnonimoBeneficiario() == null || "".equals(getDatiGenerali().getCodiceAnonimoBeneficiario()))
				messagges.add("Dati Generali: Codice Anonimo Beneficiario è un campo obbligatorio");
			
			if (getDatiGenerali().getAnnoNascita() == null || getDatiGenerali().getAnnoNascita().isEmpty())
				messagges.add("Dati Generali: Anno di Nascita è un campo obbligatorio");
			
			if (getDatiGenerali().getNazioneResidenzaBeneficiario() == null || "".equals(getDatiGenerali().getNazioneResidenzaBeneficiario()))
				messagges.add("Dati Generali: Nazione di Residenza del Beneficiario è un campo obbligatorio");
			
			if (getDatiGenerali().getScuolaFrequentata() == 0)
				messagges.add("Dati Generali: Scuola Frequentata è un campo obbligatorio");
			
			if (getDatiGenerali().getCondizioneLavoro() == 0)
				messagges.add("Dati Generali: Condizione Lavoro è un campo obbligatorio");
			
			if(getDatiGenerali().getPrestazioniSel() == null || getDatiGenerali().getPrestazioniSel().size() == 0){
				//messagges.add("Dati Generali: Selezionate almeno una prestazione dall'elenco");	
				messagges.add("Dati Generali: non esistono prestazioni per la data: "+ formatter.format(this.getDatiGenerali().getDataRiferimentoValutazione()));
			}
			// TAB Dati Famiglia
			if (getDatiFamiglia().getCondizioneMinore() == 0)
				messagges.add("Dati Famiglia: Condizione Minore è un campo obbligatorio");
			
			if (getDatiFamiglia().getLuogoVita() == 0)
				messagges.add("Dati Famiglia: Luogo Vita è un campo obbligatorio");
			
			if (getDatiFamiglia().getNazioneResidenza() == null || "".equals(getDatiFamiglia().getNazioneResidenza()))
				messagges.add("Dati Famiglia: Nazione Residenza e' un campo obbligatorio");
		
			if (getDatiFamiglia().getRegione() == 0)
				messagges.add("Dati Famiglia: Regione è un campo obbligatorio");
			
			if (getDatiFamiglia().getMinoreStranieroNonAccompagnato() == 0)
				messagges.add("Dati Famiglia: Minore straniero non accompagnato è un campo obbligatorio");
			
			if (getDatiFamiglia().getLstComponentiFamiglia() == null || getDatiFamiglia().getLstComponentiFamiglia().isEmpty())
				messagges.add("Dati Famiglia: Composizione familiare non è valorizzato");
						
			// TAB Disabilita'
			if (getDatiDisabilita().getDisabile() == 0)
				messagges.add("Disabilità: Disabilità è un campo obbligatorio");
			
			//if (getDatiDisabilita().getTipoDisabilita() == 0)
				//messagges.add("Disabilità: Tipo Disabilità è un campo obbligatorio");
			
			//if (getDatiDisabilita().getCertificazioneInvCivile() == 0)
				//messagges.add("Disabilità: Certificazione Invalidità Civile è un campo obbligatorio");
			
			// TAB Segnalazioni
			if (getDatiSegnalazioni().getFonte() == 0)
				messagges.add("Segnalazioni: Fonte della Segnalazione è un campo obbligatorio");
			
			if (getDatiSegnalazioni().getValutazioneMinore() == 0)
				messagges.add("Segnalazioni: Valutazione del Minore è un campo obbligatorio");
			
			if (getDatiSegnalazioni().getDataSegnalazione() == null)
				messagges.add("Segnalazioni: Data Segnalazione è un campo obbligatorio");
			
			if (getDatiSegnalazioni().getValutazioneFamiglia() == 0)
				messagges.add("Segnalazioni: Valutazione Famiglia del Minore è un campo obbligatorio");
			
			if (getDatiSegnalazioni().getAutoritaGiudiziaria() == 0)
				messagges.add("Segnalazioni: Segnalazione Autorità Giudiziaria è un campo obbligatorio");
			
			if (getDatiSegnalazioni().getProvvedimentoGiudiziario() == 0)
				messagges.add("Segnalazioni: Provvedimento Giudiziario è un campo obbligatorio");
			
			// TAB Affidamento
			
		
		
		return messagges;
	}
}
