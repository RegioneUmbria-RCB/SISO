package eu.smartpeg.rilevazionepresenze.data.model;

import java.io.Serializable;

import javax.persistence.*;

import eu.smartpeg.rilevazionepresenze.datautil.DataModelCostanti.Villaggi.TIPO_VILLAGGIO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * The persistent class for the AR_RP_STRUTTURE database table.
 * 
 */
@Entity
@Table(name="AR_RP_STRUTTURA")
@NamedQueries(value={
		@NamedQuery(name="Struttura.findAll", query="SELECT s FROM Struttura s ORDER BY s.nomeStruttura"),
	    @NamedQuery(
	      name = "Struttura.deleteById",
	      query="DELETE FROM Struttura s where s.id= :id"),
	    @NamedQuery(
	  	      name = "Struttura.findStrutturaById",
	  	      query="SELECT s FROM Struttura s where s.id= :id"),
	    @NamedQuery(
		  	      name = "Struttura.findStruttura",
		  	      query="SELECT s.id, s.nomeStruttura FROM  Struttura s")
	})

public class Struttura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AR_RP_STRUTTURA_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AR_RP_STRUTTURA_ID_GENERATOR")
	private long id;

	private String abilitato;

	private String indirizzo;

	@Column(name="NOME_STRUTTURA")
	private String nomeStruttura;

	@Column(name="TIPO_STRUTTURA")
	private long tipoStruttura;

	//bi-directional many-to-many association to ArRpArea
	@ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinTable(
		name="AR_RP_REL_STRUTTURA_AREA"
		, joinColumns={
			@JoinColumn(name="ID_STRUTTURA")
			}
		, inverseJoinColumns={
			@JoinColumn(name="ID_AREA")
			}
		)
	
	
	private List<Area> areas;

	
	
	
	public Struttura() {
		this.areas = new ArrayList<Area>();
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
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

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public String getTipoStrutturaDescrizione() {		
		TIPO_VILLAGGIO eTipoVillaggio= TIPO_VILLAGGIO.values()[(int)tipoStruttura];
		return eTipoVillaggio.getDescrizione();
	}

	//bi-directional many-to-one association to ArRpAnagrafica
		@OneToMany(mappedBy="Struttura" )
		private List<Anagrafica> anagraficas;
		
		public List<Anagrafica> getAnagraficas() {
			return this.anagraficas;
		}

		public void setAnagraficas(List<Anagrafica> anagraficas) {
			this.anagraficas = anagraficas;
		}

		public Anagrafica addAnagrafica(Anagrafica anagrafica) {
			getAnagraficas().add(anagrafica);
			anagrafica.setStruttura(this);   

			return anagrafica;
		}

		public Anagrafica removeAnagrafica(Anagrafica anagrafica) {
			getAnagraficas().remove(anagrafica);
			anagrafica.setStruttura(null);

			return anagrafica;
		}
}