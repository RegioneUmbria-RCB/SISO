package it.webred.cs.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "V_STRUTTURA_AREA")
@NamedQueries(value={
		@NamedQuery(name = "VStrutturaArea.findAll", query = "SELECT vs FROM VStrutturaArea vs"),
		@NamedQuery(name = "VStrutturaArea.findStruttura", query = "SELECT distinct s.idStruttura, s.nomeStruttura FROM  VStrutturaArea s"),
	    @NamedQuery(name = "VStrutturaArea.findArea", query = "SELECT s.id, s.descrizione FROM  VStrutturaArea s where idStruttura = :idStruttura"),
	    @NamedQuery(name = "VStrutturaArea.findAllArea", query = "SELECT DISTINCT a.idArea, a.descrizione FROM  VStrutturaArea a WHERE idArea IS NOT null ")
	})
public class VStrutturaArea {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_STRUTTURA")
	private long idStruttura;

	private String abilitato;

	private String indirizzo;

	@Column(name="NOME_STRUTTURA")
	private String nomeStruttura;

	@Column(name="TIPO_STRUTTURA")
	private long tipoStruttura;

	@Column(name="ID_AREA")
	private long idArea;

	private String descrizione;
	
	public VStrutturaArea() {
	}

	
	public String getAbilitato() {
		return this.abilitato;
	}

	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato; 
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNomeStruttura() {
		return this.nomeStruttura;
	}

	public void setNomeStruttura(String nomeStruttura) {
		this.nomeStruttura = nomeStruttura;
	}

	public long getTipoStruttura() {
		return tipoStruttura;
	}

	public void setTipoStruttura(long tipoStruttura) {
		this.tipoStruttura = tipoStruttura;
	}

	public long getIdStruttura() {
		return idStruttura;
	}


	public void setIdStruttura(long idStruttura) {
		this.idStruttura = idStruttura;
	}


	public long getIdArea() {
		return idArea;
	}


	public void setIdArea(long idArea) {
		this.idArea = idArea;
	}


	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}
