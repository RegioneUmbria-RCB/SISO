package it.umbriadigitale.argo.data.cs.data;

// Generated 26-ott-2015 13.12.17 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "AR_O_ORGANIZZAZIONE_FSE")
public class ArOOrganizzazioneFse implements java.io.Serializable {

	@Id
	@Column(name = "AR_O_ORG_ID")
	private long arOOrganizzazioneId;
	
	@Column(name = "AR_O_ORG_NOME")
	private String nome;
	
	@Column(name = "AR_O_ORG_BELFIORE")
	private String belfiore;
	
	@Column(name = "SIRU_BELFIORE")
	private String capofilaBelfiore;
	
	@Column(name = "SIRU_DENOMINAZIONE")
	private String capofilaDescrizione;

	public long getArOOrganizzazioneId() {
		return arOOrganizzazioneId;
	}

	public void setArOOrganizzazioneId(long arOOrganizzazioneId) {
		this.arOOrganizzazioneId = arOOrganizzazioneId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getBelfiore() {
		return belfiore;
	}

	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

	public String getCapofilaBelfiore() {
		return capofilaBelfiore;
	}

	public void setCapofilaBelfiore(String capofilaBelfiore) {
		this.capofilaBelfiore = capofilaBelfiore;
	}

	public String getCapofilaDescrizione() {
		return capofilaDescrizione;
	}

	public void setCapofilaDescrizione(String capofilaDescrizione) {
		this.capofilaDescrizione = capofilaDescrizione;
	}
	

}
