package it.webred.cs.csa.ejb.dto.erogazioni;

import it.webred.cs.data.model.CsCfgIntEsegStato;

import java.io.Serializable;
import java.util.Date;

public class ErogazioneDettaglioDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Date dataErogazione;
	private CsCfgIntEsegStato statoErogazione;
	private Long idInterventoEseg = null;
	private String descrizione;
	
	private SpesaDTO spesa;
	
	private CompartecipazioneDTO compartecipazione;
	
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
	
}
