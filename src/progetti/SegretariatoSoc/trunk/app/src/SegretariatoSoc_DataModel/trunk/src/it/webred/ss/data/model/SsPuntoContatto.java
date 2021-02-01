package it.webred.ss.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SS_PUNTO_CONTATTO")
@NamedQuery(name="SsPuntoContatto.findAll", query="SELECT c FROM SsPuntoContatto c order by c.nome")
public class SsPuntoContatto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SS_PCONT_ID_GENERATOR", sequenceName="SQ_SS_ID", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SS_PCONT_ID_GENERATOR")
	private Long id;
	
	private String nome;
	
	@Column(name="INDIR_LOCALIZZAZIONE")
	private String indirLocalizzazione;
	
	@Column(name="SITO_WEB")
	private String sitoWeb;
	
	private String orario;
	private String tel;
	private String mail;
	private Boolean abilitato;
	
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

	public String getIndirLocalizzazione() {
		return indirLocalizzazione;
	}

	public void setIndirLocalizzazione(String indirLocalizzazione) {
		this.indirLocalizzazione = indirLocalizzazione;
	}

	public String getSitoWeb() {
		return sitoWeb;
	}

	public void setSitoWeb(String sitoWeb) {
		this.sitoWeb = sitoWeb;
	}

	public String getOrario() {
		return orario;
	}

	public void setOrario(String orario) {
		this.orario = orario;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}