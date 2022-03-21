package it.webred.cs.data.model;


import java.io.Serializable;
import javax.persistence.*;

//MOD-RL

/**
 * The persistent class for the AR_REL_CLASSEMEMO_PRES_INPS database table.
 * 
 */
@Entity
@Table(name="AR_REL_CLASSEMEMO_PRES_INPS")
@NamedQueries(value={
			@NamedQuery(name="ArRelClassememoPresInps.findAll", query="SELECT a FROM ArRelClassememoPresInps a"),
		    @NamedQuery(
		      name = "ArRelClassememoPresInps.findByCodiceMeno",
		      query="SELECT a FROM ArRelClassememoPresInps a join fetch a.arTClasse b WHERE b.codiceMemo = :codiceMemo") ,
		    @NamedQuery(
				      name = "ArRelClassememoPresInps.findByTipoInterventoId",
				      query="SELECT a FROM ArRelClassememoPresInps a join fetch a.arTClasse  WHERE a.arTClasse.id = :tipoInterventoId")
		})

 
public class ArRelClassememoPresInps implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	//bi-directional many-to-one association to ArTbPrestazioniInps
	@ManyToOne
	@JoinColumn(name="PRESTAZIONI_INPS_CODICE")
	private ArTbPrestazioniInps arTbPrestazioniInp;

	//bi-directional many-to-one association to ArTClasse
	@ManyToOne
	@JoinColumn(name="T_CLASSE_ID")
	private ArTClasse arTClasse;

	public ArRelClassememoPresInps() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ArTbPrestazioniInps getArTbPrestazioniInp() {
		return this.arTbPrestazioniInp;
	}

	public void setArTbPrestazioniInp(ArTbPrestazioniInps arTbPrestazioniInp) {
		this.arTbPrestazioniInp = arTbPrestazioniInp;
	}

	public ArTClasse getArTClasse() {
		return this.arTClasse;
	}

	public void setArTClasse(ArTClasse arTClasse) {
		this.arTClasse = arTClasse;
	}


}