package it.webred.cs.json.orientamentoistruzione.ver1;

import it.webred.cs.json.dto.JsonBaseBean;

import java.math.BigDecimal;
import java.util.*;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrientamentoIstruzioneBean extends JsonBaseBean {

	private BigDecimal idTitoloStudio;
	private List<SelectItem> lstTitoliStudio;
	private Boolean frequentatoCorsi;
	private List<CorsoFormazione> corsiFrequentati;
	private Boolean conoscenzeInformatiche;
	private String livelloConoscenzeInformatiche;

	// richiesta
	private boolean richiestaCorsoLingua;
	private boolean richiestaCorsoLinguaAltra;
	private String richiestaCorsoLinguaSpecifica;
	private String motivoRichiestaLingua;
	private boolean richiestaCorsoFormazioneProfessionale;
	private String motivoRichiestaFormazioneProfessionale;
	private boolean richiestaCorsoInformatica;
	private String motivoRichiestaInformatica;
	private boolean richiestaCorsoAltro;
	private String richiestaCorsoAltroSpecifica;
	private String motivoRichiestaAltro;

	// disponibilità
	private boolean fasciaMattina;
	private boolean fasciaPomeriggio;
	private boolean fasciaSera;
	private Boolean alcuniGiorni;
	private String alcuniGiorniSpecifica;
	private Boolean disponibileSpostarsi;

	// segnalazioni
	private String segnalazioni;

	public BigDecimal getIdTitoloStudio() {
		return idTitoloStudio;
	}

	public void setIdTitoloStudio(BigDecimal idTitoloStudio) {
		this.idTitoloStudio = idTitoloStudio;
	}

	public List<SelectItem> getLstTitoliStudio() {
		return lstTitoliStudio;
	}

	public void setLstTitoliStudio(List<SelectItem> lstTitoliStudio) {
		this.lstTitoliStudio = lstTitoliStudio;
	}

	public Boolean getFrequentatoCorsi() {
		return frequentatoCorsi;
	}

	public void setFrequentatoCorsi(Boolean frequentatoCorsi) {
		this.frequentatoCorsi = frequentatoCorsi;
	}

	public List<CorsoFormazione> getCorsiFrequentati() {
		return corsiFrequentati;
	}

	public void setCorsiFrequentati(List<CorsoFormazione> corsiFrequentati) {
		this.corsiFrequentati = corsiFrequentati;
	}

	public Boolean getConoscenzeInformatiche() {
		return conoscenzeInformatiche;
	}

	public void setConoscenzeInformatiche(Boolean conoscenzeInformatiche) {
		this.conoscenzeInformatiche = conoscenzeInformatiche;
	}

	public String getLivelloConoscenzeInformatiche() {
		return livelloConoscenzeInformatiche;
	}

	public void setLivelloConoscenzeInformatiche(String livelloConoscenzeInformatiche) {
		this.livelloConoscenzeInformatiche = livelloConoscenzeInformatiche;
	}

	public boolean isRichiestaCorsoLingua() {
		return richiestaCorsoLingua;
	}

	public void setRichiestaCorsoLingua(boolean richiestaCorsoLingua) {
		this.richiestaCorsoLingua = richiestaCorsoLingua;
	}

	public boolean isRichiestaCorsoLinguaAltra() {
		return richiestaCorsoLinguaAltra;
	}

	public void setRichiestaCorsoLinguaAltra(boolean richiestaCorsoLinguaAltra) {
		this.richiestaCorsoLinguaAltra = richiestaCorsoLinguaAltra;
	}

	public String getRichiestaCorsoLinguaSpecifica() {
		return richiestaCorsoLinguaSpecifica;
	}

	public void setRichiestaCorsoLinguaSpecifica(String richiestaCorsoLinguaSpecifica) {
		this.richiestaCorsoLinguaSpecifica = richiestaCorsoLinguaSpecifica;
	}

	public String getMotivoRichiestaLingua() {
		return motivoRichiestaLingua;
	}

	public void setMotivoRichiestaLingua(String motivoRichiestaLingua) {
		this.motivoRichiestaLingua = motivoRichiestaLingua;
	}

	public boolean isRichiestaCorsoFormazioneProfessionale() {
		return richiestaCorsoFormazioneProfessionale;
	}

	public void setRichiestaCorsoFormazioneProfessionale(boolean richiestaCorsoFormazioneProfessionale) {
		this.richiestaCorsoFormazioneProfessionale = richiestaCorsoFormazioneProfessionale;
	}

	public String getMotivoRichiestaFormazioneProfessionale() {
		return motivoRichiestaFormazioneProfessionale;
	}

	public void setMotivoRichiestaFormazioneProfessionale(String motivoRichiestaFormazioneProfessionale) {
		this.motivoRichiestaFormazioneProfessionale = motivoRichiestaFormazioneProfessionale;
	}

	public boolean isRichiestaCorsoInformatica() {
		return richiestaCorsoInformatica;
	}

	public void setRichiestaCorsoInformatica(boolean richiestaCorsoInformatica) {
		this.richiestaCorsoInformatica = richiestaCorsoInformatica;
	}

	public String getMotivoRichiestaInformatica() {
		return motivoRichiestaInformatica;
	}

	public void setMotivoRichiestaInformatica(String motivoRichiestaInformatica) {
		this.motivoRichiestaInformatica = motivoRichiestaInformatica;
	}

	public boolean isRichiestaCorsoAltro() {
		return richiestaCorsoAltro;
	}

	public void setRichiestaCorsoAltro(boolean richiestaCorsoAltro) {
		this.richiestaCorsoAltro = richiestaCorsoAltro;
	}

	public String getRichiestaCorsoAltroSpecifica() {
		return richiestaCorsoAltroSpecifica;
	}

	public void setRichiestaCorsoAltroSpecifica(String richiestaCorsoAltroSpecifica) {
		this.richiestaCorsoAltroSpecifica = richiestaCorsoAltroSpecifica;
	}

	public String getMotivoRichiestaAltro() {
		return motivoRichiestaAltro;
	}

	public void setMotivoRichiestaAltro(String motivoRichiestaAltro) {
		this.motivoRichiestaAltro = motivoRichiestaAltro;
	}

	public boolean isFasciaMattina() {
		return fasciaMattina;
	}

	public void setFasciaMattina(boolean fasciaMattina) {
		this.fasciaMattina = fasciaMattina;
	}

	public boolean isFasciaPomeriggio() {
		return fasciaPomeriggio;
	}

	public void setFasciaPomeriggio(boolean fasciaPomeriggio) {
		this.fasciaPomeriggio = fasciaPomeriggio;
	}

	public boolean isFasciaSera() {
		return fasciaSera;
	}

	public void setFasciaSera(boolean fasciaSera) {
		this.fasciaSera = fasciaSera;
	}

	public Boolean getAlcuniGiorni() {
		return alcuniGiorni;
	}

	public void setAlcuniGiorni(Boolean alcuniGiorni) {
		this.alcuniGiorni = alcuniGiorni;
	}

	public String getAlcuniGiorniSpecifica() {
		return alcuniGiorniSpecifica;
	}

	public void setAlcuniGiorniSpecifica(String alcuniGiorniSpecifica) {
		this.alcuniGiorniSpecifica = alcuniGiorniSpecifica;
	}

	public Boolean getDisponibileSpostarsi() {
		return disponibileSpostarsi;
	}

	public void setDisponibileSpostarsi(Boolean disponibileSpostarsi) {
		this.disponibileSpostarsi = disponibileSpostarsi;
	}

	public String getSegnalazioni() {
		return segnalazioni;
	}

	public void setSegnalazioni(String segnalazioni) {
		this.segnalazioni = segnalazioni;
	}

	public OrientamentoIstruzioneBean() {
		super();
		corsiFrequentati = new ArrayList<CorsoFormazione>();
	}

	@Override
	public OrientamentoIstruzioneBean autoClone() throws Exception {
		return (OrientamentoIstruzioneBean) super.autoClone(); // TODO implementare?
	}

	@Override
	public List<String> checkObbligatorieta() {
		List<String> messages = new LinkedList<String>();

/*		if (this.idTitoloStudio == null || this.idTitoloStudio.equals(new BigDecimal(0))) {
			messages.add("È necessario specificare il titolo di studio.");
		}*/

		if (this.frequentatoCorsi) {
			if (this.corsiFrequentati == null || this.corsiFrequentati.size() == 0) {
				messages.add("Indicare i corsi di formazione frequentati.");
			} else {
				int i = 0;
				for (CorsoFormazione c : this.corsiFrequentati) {
					i++;
					List<String> corsoMsgs = c.checkObbligatorieta();
					for (String msg : corsoMsgs) {
						messages.add(i + "° corso: " + msg);
					}
				}
			}
		} else {
			if (this.corsiFrequentati != null && this.corsiFrequentati.size() > 0) {
				messages.add("Sono stati inseriti corsi di formazione, senza indicare che sono stati frequentati.");
			}
		}

/*		if ((this.richiestaCorsoLingua || this.richiestaCorsoFormazioneProfessionale || this.richiestaCorsoInformatica || this.richiestaCorsoAltro) == false) {
			messages.add("Non è stato richiesto nessun tipo di corso.");
		}*/

		if (this.richiestaCorsoLingua) {
			if (StringUtils.isBlank(this.motivoRichiestaLingua))
				messages.add("Indicare il motivo della richiesta del corso di lingua.");
			if (this.richiestaCorsoLinguaAltra && StringUtils.isBlank(this.richiestaCorsoLinguaSpecifica))
				messages.add("Indicare la lingua del corso richiesto.");
		}

		if (this.richiestaCorsoFormazioneProfessionale) {
			if (StringUtils.isBlank(this.motivoRichiestaFormazioneProfessionale))
				messages.add("Indicare il motivo della richiesta del corso di formazione professionale.");
		}

		if (this.richiestaCorsoInformatica) {
			if (StringUtils.isBlank(this.motivoRichiestaInformatica))
				messages.add("Indicare il motivo della richiesta del corso di informatica.");
		}

		if (this.richiestaCorsoAltro) {
			if (StringUtils.isBlank(this.richiestaCorsoAltroSpecifica))
				messages.add("Specificare il corso di altro tipo.");
			if (StringUtils.isBlank(this.motivoRichiestaAltro))
				messages.add("Indicare il motivo della richiesta del corso di altro tipo.");
		}

		if (this.alcuniGiorni && StringUtils.isBlank(this.alcuniGiorniSpecifica)) {
			messages.add("Indicare i giorni di disponibilità.");
		}

		return messages;
	}

	public void addCorsoFrequentato() {
		this.corsiFrequentati.add(new CorsoFormazione());
	}

	public void deleteCorsoFrequentato(CorsoFormazione cf) {
		this.corsiFrequentati.remove(cf);
	}
}
