package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the CS_O_SETTORE database table.
 * 
 */
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
	
	private String nome2;
	
	private String tel;

	private String tooltip;
	
	private String orario;
	
	@Column(name="N_ORD")
	private Integer nOrd;
	
	//mono-directional one-to-one association to CsAAnaIndirizzo
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ANA_INDIRIZZO_ID")
	private CsAAnaIndirizzo csAAnaIndirizzo;
	

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
	
}