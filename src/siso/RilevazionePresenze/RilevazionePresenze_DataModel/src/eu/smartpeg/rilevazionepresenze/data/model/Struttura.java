package eu.smartpeg.rilevazionepresenze.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import eu.smartpeg.rilevazionepresenze.data.model.pai.ArCsPaiPTI;
import eu.smartpeg.rilevazionepresenze.datautil.DataModelCostanti.Villaggi.TIPO_VILLAGGIO;


/**
 * The persistent class for the AR_RP_STRUTTURE database table.
 * 
 */
@Entity
@Table(name="AR_RP_STRUTTURA")
@NamedQueries(value={
		@NamedQuery(name="Struttura.findAll", query="SELECT s FROM Struttura s ORDER BY s.nomeStruttura"),
	    @NamedQuery(name = "Struttura.deleteById", query="DELETE FROM Struttura s where s.id= :id"),
	    @NamedQuery(name = "Struttura.findStrutturaById",query="SELECT s FROM Struttura s where s.id= :id"),
	    @NamedQuery(name = "Struttura.findStruttura", query="SELECT s.id, s.nomeStruttura FROM  Struttura s"),
	    @NamedQuery(name = "Struttura.findStrutturaByTipo",query="SELECT s FROM Struttura s where s.tipoStruttura.id IN (:idTipiSTruttura)"),
	    @NamedQuery(name = "Struttura.findStrutturaByCodRouting",query="SELECT s FROM Struttura s WHERE s.codBelfioreFittizio = :codRouting"),
})

public class Struttura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AR_RP_STRUTTURA_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AR_RP_STRUTTURA_ID_GENERATOR")
	private long id;

	private String abilitato;

	private String indirizzo;
	
	@Column(name="TIPOLOGIA_SERVIZIO")
	private String tipologiaServizio;
	
	@Column(name="GESTORE_SERVIZIO")
	private String gestoreServizio;
	
	@Column(name="TELEFONO_FISSO_GESTORE")
	private String telefonoFissoGestore;
	
	@Column(name="MAIL_GESTORE")
	private String mailGestore;
	
	@Column(name="PEC_GESTORE")
	private String pecGestore;
	
	@Column(name="COORDINATORE")
	private String coordinatore;
	
	@Column(name="TELEFONO_FISSO_COORDINATORE")
	private String telefonoFissoCoordinatore;
	
	@Column(name="MAIL_COORDINATORE")
	private String mailCoordinatore;
	
	@Column(name="CODICE_COMUNE")
	private String codComune;
	
	@Column(name="DESCRIZIONE_COMUNE")
	private String descrizioneComune;

	@Column(name="NOME_STRUTTURA")
	private String nomeStruttura;
	
	@Column(name="COD_BELFIORE_FITTIZIO")
	private String codBelfioreFittizio;
	
	@Column(name="COD_BELFIORE_COMUNE")
	private String codBelfioreComune;

	@Column(name="FLAG_ATTREZZATO")
	private Long flagAttrezzato;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="TIPO_STRUTTURA")
	private TipoStruttura tipoStruttura;
	
	@OneToMany(mappedBy="struttura")	
	private List<ArCsPaiPTI> minori;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_TIPOLOGIA_SERVIZIO")
	private TipologiaServizio tipoServizio;
	
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
		this.flagAttrezzato= 0L;
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

	public long getFlagAttrezzato() {
		return flagAttrezzato;
	}

	public void setFlagAttrezzato(long flagAttrezzato) {
		this.flagAttrezzato = flagAttrezzato;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public String getFlagAttrezzatoDescrizione() {		
		if(flagAttrezzato!=null) {
		TIPO_VILLAGGIO eTipoVillaggio= TIPO_VILLAGGIO.values()[(Integer) flagAttrezzato.intValue()];
		return eTipoVillaggio.getDescrizione();
		}

		return null;
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

		public String getTipologiaServizio() {
			return tipologiaServizio;
		}

		public void setTipologiaServizio(String tipologiaServizio) {
			this.tipologiaServizio = tipologiaServizio;
		}


		public String getGestoreServizio() {
			return gestoreServizio;
		}

		public void setGestoreServizio(String gestoreServizio) {
			this.gestoreServizio = gestoreServizio;
		}

		public String getTelefonoFissoGestore() {
			return telefonoFissoGestore;
		}

		public void setTelefonoFissoGestore(String telefonoFissoGestore) {
			this.telefonoFissoGestore = telefonoFissoGestore;
		}

		public String getMailGestore() {
			return mailGestore;
		}

		public void setMailGestore(String mailGestore) {
			this.mailGestore = mailGestore;
		}

		public String getPecGestore() {
			return pecGestore;
		}

		public void setPecGestore(String pecGestore) {
			this.pecGestore = pecGestore;
		}

		public String getCoordinatore() {
			return coordinatore;
		}

		public void setCoordinatore(String coordinatore) {
			this.coordinatore = coordinatore;
		}

		public String getTelefonoFissoCoordinatore() {
			return telefonoFissoCoordinatore;
		}

		public void setTelefonoFissoCoordinatore(String telefonoFissoCoordinatore) {
			this.telefonoFissoCoordinatore = telefonoFissoCoordinatore;
		}

		public String getMailCoordinatore() {
			return mailCoordinatore;
		}

		public void setMailCoordinatore(String mailCoordinatore) {
			this.mailCoordinatore = mailCoordinatore;
		}

		public String getCodComune() {
			return codComune;
		}

		public void setCodComune(String codComune) {
			this.codComune = codComune;
		}

		public String getDescrizioneComune() {
			return descrizioneComune;
		}

		public void setDescrizioneComune(String descrizioneComune) {
			this.descrizioneComune = descrizioneComune;
		}

		public TipoStruttura getTipoStruttura() {
			return tipoStruttura;
		}

		public void setTipoStruttura(TipoStruttura tipoStruttura) {
			this.tipoStruttura = tipoStruttura;
		}

		public String getCodBelfioreFittizio() {
			return codBelfioreFittizio;
		}

		public void setCodBelfioreFittizio(String codBelfioreFittizio) {
			this.codBelfioreFittizio = codBelfioreFittizio;
		}

		public String getCodBelfioreComune() {
			return codBelfioreComune;
		}

		public void setCodBelfioreComune(String codBelfioreComune) {
			this.codBelfioreComune = codBelfioreComune;
		}

		public List<ArCsPaiPTI> getMinori() {
			return minori;
		}

		public void setMinori(List<ArCsPaiPTI> minori) {
			this.minori = minori;
		}

		public TipologiaServizio getTipoServizio() {
			return tipoServizio;
		}

		public void setTipoServizio(TipologiaServizio tipoServizio) {
			this.tipoServizio = tipoServizio;
		}

}