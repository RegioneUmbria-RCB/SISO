package it.webred.cs.data.model;


import java.io.Serializable;
import javax.persistence.*;

//MOD-RL

/**
 * The persistent class for the AR_REL_CLASSEMEMO_PRES_INPS database table.
 * 
 */
@Entity
@Table(name="AR_REL_INT_CUSTOM_PRES_INPS")
@NamedQueries(value={
			@NamedQuery(name="ArRelIntCustomPresInps.findAll", query="SELECT a FROM ArRelIntCustomPresInps a"),
		    @NamedQuery(
		      name = "ArRelIntCustomPresInps.findByCodiceMeno",
		      query="SELECT a FROM ArRelIntCustomPresInps a join a.csCInterventoCustom b WHERE b.codiceMemo = :codiceMemo") ,
		    @NamedQuery(
				      name = "ArRelIntCustomPresInps.findByTipoInterventoId",
				      query="SELECT a FROM ArRelIntCustomPresInps a join a.csCInterventoCustom b WHERE b.id = :tipoInterventoId")
		})

 
public class ArRelIntCustomPresInps implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	//bi-directional many-to-one association to ArTbPrestazioniInps
	@ManyToOne
	@JoinColumn(name="PRESTAZIONE_INPS_CODICE")
	private ArTbPrestazioniInps arTbPrestazioniInpCustom;

	//bi-directional many-to-one association to ArTClasse
	@ManyToOne
	@JoinColumn(name="CUSTOM_CLASSE_ID")
	private CsCTipoInterventoCustom csCInterventoCustom;

	public ArRelIntCustomPresInps() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	 

	public ArTbPrestazioniInps getArTbPrestazioniInpCustom() {
		return arTbPrestazioniInpCustom;
	}

	public void setArTbPrestazioniInpCustom(
			ArTbPrestazioniInps arTbPrestazioniInpCustom) {
		this.arTbPrestazioniInpCustom = arTbPrestazioniInpCustom;
	}

	public CsCTipoInterventoCustom getCsCInterventoCustom() {
		return csCInterventoCustom;
	}

	public void setCsCInterventoCustom(CsCTipoInterventoCustom csCInterventoCustom) {
		this.csCInterventoCustom = csCInterventoCustom;
	}

 

}