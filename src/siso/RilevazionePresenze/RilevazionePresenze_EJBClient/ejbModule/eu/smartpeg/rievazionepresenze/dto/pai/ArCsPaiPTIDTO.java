package eu.smartpeg.rievazionepresenze.dto.pai;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import eu.smartpeg.rilevazionepresenze.data.model.Struttura;
import eu.smartpeg.rilevazionepresenze.data.model.TipoStruttura;

public class ArCsPaiPTIDTO implements Serializable {
	
	private static final long serialVersionUID = 3579237437791697320L;

	
	private Long id;
	
	private String nome;
	
	private String cognome;
	
	private String cf;
	
	private String sesso;
	
	private String cittadinanza;
	
	private int annoNascita;
	
	private String comuneResidenza;
	
	private String nazioneResidenza;
	
	private String viaResidenza;
	
	private Struttura struttura;
	
	private byte[] documentoPai;
	
	private String nomeDocPai;
	
	private byte[] documentoPtiEqui;
	
	private String nomeDocPtiEqui;
	
	private String codRouting;
	
	private Date dataInizioPermamenza;
	
    private Date dataFinePermanenza;
	
	private String stato;
	
	private TipoStruttura tipoStruttura;
	
	private Long diarioPaiId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public int getAnnoNascita() {
		return annoNascita;
	}
	

	public String getCodRouting() {
		return codRouting;
	}

	public void setCodRouting(String codRouting) {
		this.codRouting = codRouting;
	}

	public Date getDataInizioPermamenza() {
		return dataInizioPermamenza;
	}

	public void setDataInizioPermamenza(Date dataInizioPermamenza) {
		this.dataInizioPermamenza = dataInizioPermamenza;
	}

	public Date getDataFinePermanenza() {
		return dataFinePermanenza;
	}

	public void setDataFinePermanenza(Date dataFinePermanenza) {
		this.dataFinePermanenza = dataFinePermanenza;
	}

	public void setAnnoNascita(int annoNascita) {
		this.annoNascita = annoNascita;
	}

	public String getComuneResidenza() {
		return comuneResidenza;
	}

	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}

	public String getNazioneResidenza() {
		return nazioneResidenza;
	}

	public void setNazioneResidenza(String nazioneResidenza) {
		this.nazioneResidenza = nazioneResidenza;
	}

	public String getViaResidenza() {
		return viaResidenza;
	}

	public void setViaResidenza(String viaResidenza) {
		this.viaResidenza = viaResidenza;
	}


	

	public byte[] getDocumentoPai() {
		return documentoPai;
	}

	public void setDocumentoPai(byte[] documentoPai) {
		this.documentoPai = documentoPai;
	}

	public String getNomeDocPai() {
		return nomeDocPai;
	}

	public void setNomeDocPai(String nomeDocPai) {
		this.nomeDocPai = nomeDocPai;
	}

	public byte[] getDocumentoPtiEqui() {
		return documentoPtiEqui;
	}

	public void setDocumentoPtiEqui(byte[] documentoPtiEqui) {
		this.documentoPtiEqui = documentoPtiEqui;
	}

	public String getNomeDocPtiEqui() {
		return nomeDocPtiEqui;
	}

	public void setNomeDocPtiEqui(String nomeDocPtiEqui) {
		this.nomeDocPtiEqui = nomeDocPtiEqui;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Struttura getStruttura() {
		return struttura;
	}

	public void setStruttura(Struttura struttura) {
		this.struttura = struttura;
	}

	public TipoStruttura getTipoStruttura() {
		return tipoStruttura;
	}

	public void setTipoStruttura(TipoStruttura tipoStruttura) {
		this.tipoStruttura = tipoStruttura;
	}

	public Long getDiarioPaiId() {
		return diarioPaiId;
	}

	public void setDiarioPaiId(Long diarioPaiId) {
		this.diarioPaiId = diarioPaiId;
	}
	
	
	
	
	
	
	
	
	
}
