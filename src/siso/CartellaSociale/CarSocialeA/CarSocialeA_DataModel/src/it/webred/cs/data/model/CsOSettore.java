package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the CS_O_SETTORE database table.
 * 
 */
@Entity
@Table(name="CS_O_SETTORE")
public class CsOSettore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_O_SETTORE_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_O_SETTORE_ID_GENERATOR")
	private Long id;

	private Boolean abilitato;

	private String cell;

	private String email;

	private String fax;

	private String nome;
	
	@Column(name="FLAG_INVIANTE")
	private Boolean flgInviante;
	
	@Column(name="FLAG_INVIATO_A")
	private Boolean flgInviatoA;
	
	@Column(name="FLAG_IN_CARICO_A")
	private Boolean flgInCaricoA;
	
	@Column(name="FLAG_RIUNIONE_CON")
	private Boolean flgRiunioneCon;
	
	@Column(name="FLAG_INT_TITOLARE")
	private Boolean flgIntTitolare;
	
	@Column(name="FLAG_INT_EROGA")
	private Boolean flgIntEroga;

	/* SISO-663 SM */
	@Column(name="FLAG_INT_GESTORE")
	private Boolean flgIntGestore;
	/* -=- */		
	
	@Column(name="FLAG_ENTE_INTERLOCUTORE")
    private Boolean flgEnteInterlocutore;
	
	@Column(name="ID_SECONDO_LIVELLO")  
	private Long idSecondoLivello;
	
	private String nome2;
	
	private String tel;

	private String tooltip;
	
	private String orario;
	
	@Column(name="N_ORD")
	private Integer nOrd;

	//mono-directional one-to-one association to CsAAnaIndirizzo
	@OneToOne
	@JoinColumn(name="ANA_INDIRIZZO_ID")
	private CsAAnaIndirizzo csAAnaIndirizzo;
	
	//SISO-812
	//mono-directional one-to-one association to CsTbSecondoLivello
	@OneToOne(optional=true, fetch=FetchType.EAGER)
	@JoinColumn(name="ID_SECONDO_LIVELLO", insertable=false, updatable=false)
	private CsTbSecondoLivello csTbSecondoLivello;

	//bi-directional many-to-one association to CsOOperatoreSettore
	@OneToMany(mappedBy="csOSettore", fetch=FetchType.LAZY)
	@JsonIgnore
	private Set<CsOOperatoreSettore> csOOperatoreSettores;

	//bi-directional many-to-one association to CsOOrganizzazione
	@ManyToOne
	@JoinColumn(name="ORGANIZZAZIONE_ID")
	private CsOOrganizzazione csOOrganizzazione;

	//bi-directional one-to-one association to CsCComunita
	@OneToMany(mappedBy="csOSettore", fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn
	@JsonIgnore
	private Set<CsCComunita> csCComunita;
	
	

	public CsOSettore() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CsAAnaIndirizzo getCsAAnaIndirizzo() {
		return csAAnaIndirizzo;
	}

	public void setCsAAnaIndirizzo(CsAAnaIndirizzo csAAnaIndirizzo) {
		this.csAAnaIndirizzo = csAAnaIndirizzo;
	}

	public String getCell() {
		return this.cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public CsOOperatoreSettore addCsOOperatoreSettore(CsOOperatoreSettore csOOperatoreSettore) {
		getCsOOperatoreSettores().add(csOOperatoreSettore);
		csOOperatoreSettore.setCsOSettore(this);

		return csOOperatoreSettore;
	}

	public Set<CsOOperatoreSettore> getCsOOperatoreSettores() {
		return csOOperatoreSettores;
	}

	public void setCsOOperatoreSettores(Set<CsOOperatoreSettore> csOOperatoreSettores) {
		this.csOOperatoreSettores = csOOperatoreSettores;
	}

	public CsOOperatoreSettore removeCsOOperatoreSettore(CsOOperatoreSettore csOOperatoreSettore) {
		getCsOOperatoreSettores().remove(csOOperatoreSettore);
		csOOperatoreSettore.setCsOSettore(null);

		return csOOperatoreSettore;
	}

	public CsOOrganizzazione getCsOOrganizzazione() {
		return this.csOOrganizzazione;
	}

	public void setCsOOrganizzazione(CsOOrganizzazione csOOrganizzazione) {
		this.csOOrganizzazione = csOOrganizzazione;
	}

	public Set<CsCComunita> getCsCComunita() {
		return this.csCComunita;
	}

	public void setCsCComunita(Set<CsCComunita> csCComunita) {
		this.csCComunita = csCComunita;
	}



	public String getNome2() {
		return nome2;
	}

	public void setNome2(String nome2) {
		this.nome2 = nome2;
	}

	public String getOrario() {
		return orario;
	}

	public void setOrario(String orario) {
		this.orario = orario;
	}

	public Boolean getAbilitato() {
		return abilitato = abilitato!=null ? abilitato : false;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}

	public Boolean getFlgInviante() {
		return flgInviante = flgInviante!=null ? flgInviante : false;
	}

	public Boolean getFlgInviatoA() {
		return flgInviatoA;
	}

	public void setFlgInviante(Boolean flgInviante) {
		this.flgInviante = flgInviante;
	}

	public void setFlgInviatoA(Boolean flgInviatoA) {
		this.flgInviatoA = flgInviatoA;
	}

	public void setFlgInCaricoA(Boolean flgInCaricoA) {
		this.flgInCaricoA = flgInCaricoA;
	}
	
	public Boolean getFlgInCaricoA() {
		return flgInCaricoA = flgInCaricoA!=null ? flgInCaricoA : false;
	}

	public Integer getnOrd() {
		return nOrd;
	}

	public void setnOrd(Integer nOrd) {
		this.nOrd = nOrd;
	}

	public Boolean getFlgIntTitolare() {
		return flgIntTitolare = flgIntTitolare!=null ? flgIntTitolare : false;
	}

	public Boolean getFlgIntEroga() {
		return flgIntEroga = flgIntEroga!=null ? flgIntEroga : false;
	}

	public void setFlgIntTitolare(Boolean flagIntTitolare) {
		this.flgIntTitolare = flagIntTitolare;
	}

	public void setFlgIntEroga(Boolean flgIntEroga) {
		this.flgIntEroga = flgIntEroga;
	}
	
	public Boolean getFlgIntGestore() {
		return flgIntGestore;
	}

	public void setFlgIntGestore(Boolean flgIntGestore) {
		this.flgIntGestore = flgIntGestore;
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsOSettore)) {
			return false;
		}
		CsOSettore castOther = (CsOSettore)other;
		return 
			this.id.equals(castOther.id);
	}

	/**
	 * @return the flgRiunioneCon
	 */
	public Boolean getFlgRiunioneCon() {
		return flgRiunioneCon = flgRiunioneCon!=null ? flgRiunioneCon : false;
	}

	/**
	 * @param flgRiunioneCon the flgRiunioneCon to set
	 */
	public void setFlgRiunioneCon(Boolean flgRiunioneCon) {
		this.flgRiunioneCon = flgRiunioneCon;
	}

	
	
	public Boolean getFlgEnteInterlocutore() {
		return flgEnteInterlocutore = flgEnteInterlocutore!=null ? flgEnteInterlocutore : false;
	}

	public void setFlgEnteInterlocutore(Boolean flgEnteInterlocutore) {
		this.flgEnteInterlocutore = flgEnteInterlocutore;
	}

		//SISO-812
		public String getDescrizione(){
			if(this.getCsTbSecondoLivello() != null){
				String slDesc = this.getCsTbSecondoLivello().getDescrizione() == null ? "" : this.getCsTbSecondoLivello().getDescrizione(); 
			    return this.nome + " - " + slDesc;
			}else
				return this.nome;
		}
		
		public String getDescrizioneCompleta(){
			return this.getDescrizione() + (this.csOOrganizzazione!=null ? " (Org. "+this.csOOrganizzazione.getNome()+")" : "");
		}

		public Long getIdSecondoLivello() {
			return idSecondoLivello;
		}

		public void setIdSecondoLivello(Long idSecondoLivello) {
			this.idSecondoLivello = (idSecondoLivello!=null && idSecondoLivello>0) ? idSecondoLivello : null;
		}
		
		public CsTbSecondoLivello getCsTbSecondoLivello() {
			return csTbSecondoLivello;
		}

		public void setCsTbSecondoLivello(CsTbSecondoLivello csTbSecondoLivello) {
			this.csTbSecondoLivello = csTbSecondoLivello;
		}

		@Override
		public String toString() {
			return "CsOSettore [id=" + id + ", abilitato=" + abilitato + ", cell=" + cell + ", email=" + email
					+ ", fax=" + fax + ", nome=" + nome + ", flgInviante=" + flgInviante + ", flgInviatoA="
					+ flgInviatoA + ", flgInCaricoA=" + flgInCaricoA + ", flgRiunioneCon=" + flgRiunioneCon
					+ ", flgIntTitolare=" + flgIntTitolare + ", flgIntEroga=" + flgIntEroga + ", flgIntGestore="
					+ flgIntGestore + ", flgEnteInterlocutore=" + flgEnteInterlocutore + ", idSecondoLivello="
					+ idSecondoLivello + ", nome2=" + nome2 + ", tel=" + tel + ", tooltip=" + tooltip + ", orario="
					+ orario + ", nOrd=" + nOrd + ", csAAnaIndirizzo=" + csAAnaIndirizzo + ", csTbSecondoLivello="
					+ csTbSecondoLivello + ", csOOperatoreSettores=" + csOOperatoreSettores + ", csOOrganizzazione="
					+ csOOrganizzazione + ", csCComunita=" + csCComunita + ", toString()=" + super.toString() + "]";
		}
	
		
}