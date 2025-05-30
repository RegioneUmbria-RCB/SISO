package it.webred.cs.csa.ejb.dto.erogazioni;

import it.webred.cs.data.model.CsCfgIntEsegStato;
import it.webred.cs.data.model.CsIInterventoEsegValore;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ErogazioneDettaglioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Date dataErogazione;
	private Date dataErogazioneA; //SISO-556
	private Date dataEvento;
	private CsCfgIntEsegStato statoErogazione;
	private Long idInterventoEseg = null;
	private String descrizione;
	
	private SpesaDTO spesa;
	
	private CompartecipazioneDTO compartecipazione;
	
	private String entita;
	
	private List<CsIInterventoEsegValore> valori;
	
	public Date getDataErogazione() {
		return dataErogazione;
	}
	public CsCfgIntEsegStato getStatoErogazione() {
		return statoErogazione;
	}
	public Long getIdInterventoEseg() {
		return idInterventoEseg;
	}
	public String getDescrizione() {
		return descrizione;
	}
	
	public SpesaDTO getSpesa() {
		return spesa;
	}
	public CompartecipazioneDTO getCompartecipazione() {
		return compartecipazione;
	}
	public void setDataErogazione(Date dataErogazione) {
		this.dataErogazione = dataErogazione;
	}
	public void setStatoErogazione(CsCfgIntEsegStato statoErogazione) {
		this.statoErogazione = statoErogazione;
	}
	public void setIdInterventoEseg(Long idInterventoEseg) {
		this.idInterventoEseg = idInterventoEseg;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public void setSpesa(SpesaDTO spesa) {
		this.spesa = spesa;
	}
	public void setCompartecipazione(CompartecipazioneDTO compartecipazione) {
		this.compartecipazione = compartecipazione;
	}
	
	 //INIZIO SISO-556
	public Date getDataErogazioneA() {
		return dataErogazioneA;
	}
	public void setDataErogazioneA(Date dataErogazioneA) {
		this.dataErogazioneA = dataErogazioneA;
	}
	 //FINE SISO-556
	public String getEntita() {
		return entita;
	}
	public void setEntita(String entita) {
		this.entita = entita;
	}
	public List<CsIInterventoEsegValore> getValori() {
		return valori;
	}
	public void setValori(List<CsIInterventoEsegValore> valori) {
		this.valori = valori;
	}
	public Date getDataEvento() {
		return dataEvento;
	}
	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}

}
