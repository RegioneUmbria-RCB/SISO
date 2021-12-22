package it.webred.cs.data.model.pti;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the SIT_LOAD_DOCUMENTO database table.
 * 
 */
@Entity
@Table(name = "AR_CS_PAI_PTI_DOCUMENTO")
@NamedQuery(name = "CsPaiPtiDocumento.findAll", query = "SELECT c FROM CsPaiPtiDocumento c")
public class CsPaiPtiDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CS_PAI_PTI_DOC_ID_GENERATOR", sequenceName = "SQ_PAI_PTI_DOC", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CS_PAI_PTI_DOC_ID_GENERATOR")
	private Long id;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "ID_PAI_PTI", referencedColumnName = "ID", insertable = false, updatable = false),
			@JoinColumn(name = "COD_ROUTING", referencedColumnName = "COD_ROUTING", insertable = false, updatable = false) })
	private CsPaiPTI paiPTI;

	@Column(name = "ID_PAI_PTI")
	private Long idPaiPTI;

	@Column(name = "COD_ROUTING")
	private String codRouting;

	@Lob
	private byte[] documento;

	private String tipo;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INS")
	private Date dtIns;

	private String nome;

	@Column(name = "TIPO_DOCUMENTO")
	private String tipoDocumento;

	@Column(name = "USR_INS")
	private String usrIns;

	@Temporal(TemporalType.DATE)
	@Column(name = "VALIDO_DA")
	private Date validoDa;

	@Temporal(TemporalType.DATE)
	@Column(name = "VALIDO_A")
	private Date validoA;

	@Column(name = "FLG_NOTIFICA")
	private Boolean flgNotifica; 
	
	@Column(name = "FLG_APPROPRIATO")
	private Boolean flgAppropriato;
	
	@Column(name = "NOTE")
	private String note;
	
	@OneToOne(mappedBy = "csPaiPtiDocumento")
    private ArCsPaiInfoSintetiche arCsPaiInfoSintetiche;
	
	public CsPaiPtiDocumento() {
	}

	public CsPaiPtiDocumento(Long idPaiPTI, String codRouting) {
		this.idPaiPTI = idPaiPTI;
		this.codRouting = codRouting;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getDocumento() {
		return this.documento;
	}

	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsrIns() {
		return this.usrIns;
	}

	public void setUsrIns(String usrIns) {
		this.usrIns = usrIns;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public CsPaiPTI getPaiPTI() {
		return paiPTI;
	}

	public void setPaiPTI(CsPaiPTI paiPTI) {
		this.paiPTI = paiPTI;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Date getValidoDa() {
		return validoDa;
	}

	public void setValidoDa(Date validoDa) {
		this.validoDa = validoDa;
	}

	public Date getValidoA() {
		return validoA;
	}

	public void setValidoA(Date validoA) {
		this.validoA = validoA;
	}

	public Long getIdPaiPTI() {
		return idPaiPTI;
	}

	public void setIdPaiPTI(Long idPaiPTI) {
		this.idPaiPTI = idPaiPTI;
	}

	public String getCodRouting() {
		return codRouting;
	}

	public void setCodRouting(String codRouting) {
		this.codRouting = codRouting;
	}
	public ArCsPaiInfoSintetiche getArCsPaiInfoSintetiche() {
		return arCsPaiInfoSintetiche;
	}

	public void setArCsPaiInfoSintetiche(ArCsPaiInfoSintetiche arCsPaiInfoSintetiche) {
		this.arCsPaiInfoSintetiche = arCsPaiInfoSintetiche;
	}

	public Boolean getFlgAppropriato() {
		return flgAppropriato;
	}

	public void setFlgAppropriato(Boolean flgAppropriato) {
		this.flgAppropriato = flgAppropriato;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getFlgNotifica() {
		return flgNotifica;
	}

	public void setFlgNotifica(Boolean flgNotifica) {
		this.flgNotifica = flgNotifica;
	}

}
