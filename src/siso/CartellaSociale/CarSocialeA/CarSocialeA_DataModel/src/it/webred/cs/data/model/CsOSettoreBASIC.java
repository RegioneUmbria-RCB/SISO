package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="CS_O_SETTORE")
public class CsOSettoreBASIC implements Serializable {
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

	
	private String nome2;
	
	private String tel;

	private String tooltip;
	
	private String orario;
	
	@Column(name="N_ORD")
	private Integer nOrd;
	
	@Column(name="ID_SECONDO_LIVELLO")  
	private Long idSecondoLivello;
	
	
	//mono-directional one-to-one association to CsAAnaIndirizzo
	@OneToOne
	@JoinColumn(name="ANA_INDIRIZZO_ID")
	private CsAAnaIndirizzo csAAnaIndirizzo;
	
	//SISO-812
	//mono-directional one-to-one association to CsTbSecondoLivello
	@OneToOne(optional=true, fetch=FetchType.EAGER)
	@JoinColumn(name="ID_SECONDO_LIVELLO", insertable=false, updatable=false)
	private CsTbSecondoLivello csTbSecondoLivello;

	@Basic
	@Column(name= "ORGANIZZAZIONE_ID", insertable=false, updatable=false)
	protected Long csOOrganizzazioneId;
	
	public CsAAnaIndirizzo getCsAAnaIndirizzo() {
		return csAAnaIndirizzo;
	}

	public void setCsAAnaIndirizzo(CsAAnaIndirizzo csAAnaIndirizzo) {
		this.csAAnaIndirizzo = csAAnaIndirizzo;
	}

	

	public Long getCsOOrganizzazioneId() {
		return csOOrganizzazioneId;
	}

	public void setCsOOrganizzazioneId(Long csOOrganizzazioneId) {
		this.csOOrganizzazioneId = csOOrganizzazioneId;
	}

	public CsOSettoreBASIC() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}

	public Boolean getFlgInviante() {
		return flgInviante;
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
		return flgInCaricoA;
	}

	public Integer getnOrd() {
		return nOrd;
	}

	public void setnOrd(Integer nOrd) {
		this.nOrd = nOrd;
	}

	public Boolean getFlgRiunioneCon() {
		return flgRiunioneCon;
	}

	public void setFlgRiunioneCon(Boolean flgRiunioneCon) {
		this.flgRiunioneCon = flgRiunioneCon;
	}

	public Boolean getFlgIntTitolare() {
		return flgIntTitolare;
	}

	public void setFlgIntTitolare(Boolean flgIntTitolare) {
		this.flgIntTitolare = flgIntTitolare;
	}

	public Boolean getFlgIntEroga() {
		return flgIntEroga;
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

	public CsTbSecondoLivello getCsTbSecondoLivello() {
		return csTbSecondoLivello;
	}

	public void setCsTbSecondoLivello(CsTbSecondoLivello csTbSecondoLivello) {
		this.csTbSecondoLivello = csTbSecondoLivello;
	}
	
	//SISO-812
	public String getDescrizione(){
		
		String slDesc = this.getCsTbSecondoLivello() != null ? (this.getCsTbSecondoLivello().getDescrizione() == null ? "" : this.getCsTbSecondoLivello().getDescrizione()) : ""; 
		
		return this.nome + " - " + slDesc;
	}

	public Long getIdSecondoLivello() {
		return idSecondoLivello;
	}

	public void setIdSecondoLivello(Long idSecondoLivello) {
		this.idSecondoLivello = idSecondoLivello;
	}
	public Boolean getFlgEnteInterlocutore() {
		return flgEnteInterlocutore = flgEnteInterlocutore!=null ? flgEnteInterlocutore : false;
	}

	public void setFlgEnteInterlocutore(Boolean flgEnteInterlocutore) {
		this.flgEnteInterlocutore = flgEnteInterlocutore;
	}
	
}