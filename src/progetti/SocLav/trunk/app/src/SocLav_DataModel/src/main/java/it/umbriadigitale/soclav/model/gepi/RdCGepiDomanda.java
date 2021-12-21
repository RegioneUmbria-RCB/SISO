package it.umbriadigitale.soclav.model.gepi;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="RDC_GEPI_DOMANDA")
public class RdCGepiDomanda implements Serializable {
	
	@Id
	@Column(name="COD_IDENTIFICATIVO_DOM")
	private String codIdentificativoDomanda;
	
	private String cf;
	
	@Column(name="NOME_COMPLETO")
	private String nomeCompleto;
	
	@Column(name="AMBITO_APPARTENENZA")
	private String ambito;
	
	@Column(name="DATA_PRESENTAZIONE_DOMANDA")
	private Date dataPresentazioneDomanda;
	
	@Column(name="DATA_ASSEGNAZIONE_DOMANDA")
	private Date dataAssegnazioneDomanda;
	
	private String assegnante;
	private String assegnatario;
	
	@Column(name="RESIDENZA_INDIRIZZO")
	private String residenzaIndirizzo;
	
	@Column(name="RESIDENZA_COMUNE_DES")
	private String residenzaComuneDes;
	
	@Column(name="RESIDENZA_COMUNE_COD")
	private String residenzaComuneCod;
	
	@Column(name="RESIDENZA_CAP")
	private String residenzaCap;

	@OneToMany(mappedBy="domanda", fetch = FetchType.EAGER)
	private List<RdCGepiComponenteFamiglia> componenteFamiglia;

	
	public String getCodIdentificativoDomanda() {
		return codIdentificativoDomanda;
	}

	public void setCodIdentificativoDomanda(String codIdentificativoDomanda) {
		this.codIdentificativoDomanda = codIdentificativoDomanda;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getAmbito() {
		return ambito;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	public Date getDataPresentazioneDomanda() {
		return dataPresentazioneDomanda;
	}

	public void setDataPresentazioneDomanda(Date dataPresentazioneDomanda) {
		this.dataPresentazioneDomanda = dataPresentazioneDomanda;
	}

	public Date getDataAssegnazioneDomanda() {
		return dataAssegnazioneDomanda;
	}

	public void setDataAssegnazioneDomanda(Date dataAssegnazioneDomanda) {
		this.dataAssegnazioneDomanda = dataAssegnazioneDomanda;
	}

	public String getAssegnante() {
		return assegnante;
	}

	public void setAssegnante(String assegnante) {
		this.assegnante = assegnante;
	}

	public String getAssegnatario() {
		return assegnatario;
	}

	public void setAssegnatario(String assegnatario) {
		this.assegnatario = assegnatario;
	}

	public String getResidenzaIndirizzo() {
		return residenzaIndirizzo;
	}

	public void setResidenzaIndirizzo(String residenzaIndirizzo) {
		this.residenzaIndirizzo = residenzaIndirizzo;
	}

	public String getResidenzaComuneDes() {
		return residenzaComuneDes;
	}

	public void setResidenzaComuneDes(String residenzaComuneDes) {
		this.residenzaComuneDes = residenzaComuneDes;
	}

	public String getResidenzaComuneCod() {
		return residenzaComuneCod;
	}

	public void setResidenzaComuneCod(String residenzaComuneCod) {
		this.residenzaComuneCod = residenzaComuneCod;
	}

	public String getResidenzaCap() {
		return residenzaCap;
	}

	public void setResidenzaCap(String residenzaCap) {
		this.residenzaCap = residenzaCap;
	}

	public List<RdCGepiComponenteFamiglia> getComponenteFamiglia() {
		return componenteFamiglia;
	}

	public void setComponenteFamiglia(List<RdCGepiComponenteFamiglia> componenteFamiglia) {
		this.componenteFamiglia = componenteFamiglia;
	}
	
}

