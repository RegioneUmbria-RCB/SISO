package it.webred.cs.data.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CS_D_DIARIO_ANA")
public class CsDDiarioAna {

	@Id
	@SequenceGenerator(name = "CS_D_DIARIO_ANA_ID_GENERATOR", sequenceName = "SQ_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CS_D_DIARIO_ANA_ID_GENERATOR")
	@Column(name="ID")
	private Long id;
	
	@Column(name="DIARIO_ID")
	private Long diarioId;
	
	@Column(name="ANAGRAFICA_ID")
	private Long anagraficaId;
	
	@Column(name="USER_INS")
	private String userIns;
	
	@Column(name="DT_INS")
	private Date dataIns;
	
	@Column(name="USER_MOD")
	private String userMod;
	
	@Column(name="DT_MOD")
	private Date dataMod;
	
	@Column(name="NOTE")
	private String note;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDiarioId() {
		return diarioId;
	}

	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}

	public Long getAnagraficaId() {
		return anagraficaId;
	}

	public void setAnagraficaId(Long anagraficaId) {
		this.anagraficaId = anagraficaId;
	}

	public String getUserIns() {
		return userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public Date getDataIns() {
		return dataIns;
	}

	public void setDataIns(Date dataIns) {
		this.dataIns = dataIns;
	}

	public String getUserMod() {
		return userMod;
	}

	public void setUserMod(String userMod) {
		this.userMod = userMod;
	}

	public Date getDataMod() {
		return dataMod;
	}

	public void setDataMod(Date dataMod) {
		this.dataMod = dataMod;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
