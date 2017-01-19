package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id; 
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="AR_TB_PRESTAZIONI_INPS")


@NamedQueries(value={ 
			@NamedQuery(name="ArTbPrestazioniInps.findAll", query="SELECT a FROM ArTbPrestazioniInps a"),   
			@NamedQuery(
			  name = "ArTbPrestazioniInps.findByCodice",
			  query="SELECT a FROM ArTbPrestazioniInps a  WHERE a.codice = :codice") 
		    })



public class ArTbPrestazioniInps implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private String codice;

	private String classificazione;

	private String denominazione;

	private String descrizione;

	//bi-directional many-to-one association to ArRelClassememoPresInps
	@OneToMany(mappedBy="arTbPrestazioniInp")
	private List<ArRelClassememoPresInps> arRelClassememoPresInps;

	public ArTbPrestazioniInps() {
	}

	public String getCodice() {
		return this.codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getClassificazione() {
		return this.classificazione;
	}

	public void setClassificazione(String classificazione) {
		this.classificazione = classificazione;
	}

	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<ArRelClassememoPresInps> getArRelClassememoPresInps() {
		return this.arRelClassememoPresInps;
	}

	public void setArRelClassememoPresInps(List<ArRelClassememoPresInps> arRelClassememoPresInps) {
		this.arRelClassememoPresInps = arRelClassememoPresInps;
	}

	public ArRelClassememoPresInps addArRelClassememoPresInp(ArRelClassememoPresInps arRelClassememoPresInp) {
		getArRelClassememoPresInps().add(arRelClassememoPresInp);
		arRelClassememoPresInp.setArTbPrestazioniInp(this);

		return arRelClassememoPresInp;
	}

	public ArRelClassememoPresInps removeArRelClassememoPresInp(ArRelClassememoPresInps arRelClassememoPresInp) {
		getArRelClassememoPresInps().remove(arRelClassememoPresInp);
		arRelClassememoPresInp.setArTbPrestazioniInp(null);

		return arRelClassememoPresInp;
	}


}
