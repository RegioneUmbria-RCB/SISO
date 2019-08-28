package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CS_A_SOGGETTO database table.
 * 
 */
@Entity
@Table(name="CS_A_SOGGETTO")
@NamedQuery(name="CsASoggetto.findAll", query="SELECT c FROM CsASoggettoLAZY c")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class CsASoggettoLAZY implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ANAGRAFICA_ID")
	private Long anagraficaId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_MOD")
	private Date dtMod;

	@Column(name="USER_INS")
	private String userIns;

	@Column(name="USR_MOD")
	private String usrMod;

	//bi-directional one-to-one association to CsACaso
	@OneToOne(optional=false, fetch = FetchType.LAZY)
	@JoinColumn(name = "CASO_ID", referencedColumnName = "id", nullable=false)
	private CsACaso csACaso;

	//bi-directional many-to-one association to CsAFamigliaGruppo
	@OneToMany(mappedBy="csASoggetto")
	private List<CsAFamigliaGruppo> csAFamigliaGruppos;



	//bi-directional one-to-one association to CsAAnagrafica
	@OneToOne
	@JoinColumn(name="ANAGRAFICA_ID")
	private CsAAnagrafica csAAnagrafica;
	
	public CsASoggettoLAZY() {
	}

	public Long getAnagraficaId() {
		return this.anagraficaId;
	}

	public void setAnagraficaId(Long anagraficaId) {
		this.anagraficaId = anagraficaId;
	}

	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Date getDtMod() {
		return this.dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public String getUserIns() {
		return this.userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public String getUsrMod() {
		return this.usrMod;
	}

	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}

	public CsACaso getCsACaso() {
		return this.csACaso;
	}

	public void setCsACaso(CsACaso csACaso) {
		this.csACaso = csACaso;
	}

	public List<CsAFamigliaGruppo> getCsAFamigliaGruppos() {
		return this.csAFamigliaGruppos;
	}

	public void setCsAFamigliaGruppos(List<CsAFamigliaGruppo> csAFamigliaGruppos) {
		this.csAFamigliaGruppos = csAFamigliaGruppos;
	}
	
	public CsAAnagrafica getCsAAnagrafica() {
		return this.csAAnagrafica;
	}

	public void setCsAAnagrafica(CsAAnagrafica csAAnagrafica) {
		this.csAAnagrafica = csAAnagrafica;
	}
	
	
	@MappedSuperclass
	public class CsASoggettoLAZYIndirizzos extends CsASoggettoLAZY implements Serializable {
		
		//bi-directional many-to-one association to CsAIndirizzo
		@OneToMany(mappedBy="csASoggetto")
		private List<CsAIndirizzo> csAIndirizzos;
		public List<CsAIndirizzo> getCsAIndirizzos() {
			return this.csAIndirizzos;
		}

		public void setCsAIndirizzos(List<CsAIndirizzo> csAIndirizzos) {
			this.csAIndirizzos = csAIndirizzos;
		}


		public CsAIndirizzo addCsAIndirizzo(CsAIndirizzo csAIndirizzo) {
			getCsAIndirizzos().add(csAIndirizzo);
			csAIndirizzo.setCsASoggetto(this);

			return csAIndirizzo;
		}

		public CsAIndirizzo removeCsAIndirizzo(CsAIndirizzo csAIndirizzo) {
			getCsAIndirizzos().remove(csAIndirizzo);
			csAIndirizzo.setCsASoggetto(null);

			return csAIndirizzo;
		}
		
	}
	

}




	

