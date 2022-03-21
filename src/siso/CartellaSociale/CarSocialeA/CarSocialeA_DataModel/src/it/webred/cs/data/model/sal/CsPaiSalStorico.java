package it.webred.cs.data.model.sal;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CS_PAI_SAL_STORICO database table.
 * 
 */
@Entity
@Table(name="CS_PAI_SAL_STORICO")
@NamedQuery(name="CsPaiSalStorico.findAll", query="SELECT c FROM CsPaiSalStorico c")
public class CsPaiSalStorico implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@SequenceGenerator(name="CS_PAI_SAL_STORICO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_PAI_SAL_STORICO_ID_GENERATOR")
	private Long id;

	@Column(name="ID_PAI",insertable=false,updatable=false)
	private Long idPai;

	private String mansione;

	@Column(name="TUTOR_CONTESTO")
	private String tutorContesto;

	@Column(name="USER_INS")
	private String userIns;
	
	@Column(name="DT_INS")
	private Date dtIns;

	@ManyToOne
	@JoinColumn(name = "ID_PAI")
	private CsPaiSAL paiSal;	
	
	public CsPaiSalStorico() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPai() {
		return this.idPai;
	}

	public void setIdPai(Long idPai) {
		this.idPai = idPai;
	}

	public String getMansione() {
		return this.mansione;
	}

	public void setMansione(String mansione) {
		this.mansione = mansione;
	}

	public String getTutorContesto() {
		return this.tutorContesto;
	}

	public void setTutorContesto(String tutorContesto) {
		this.tutorContesto = tutorContesto;
	}

	public String getUserIns() {
		return this.userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public CsPaiSAL getPaiSal() {
		return paiSal;
	}

	public void setPaiSal(CsPaiSAL paiSal) {
		this.paiSal = paiSal;
	}

}
