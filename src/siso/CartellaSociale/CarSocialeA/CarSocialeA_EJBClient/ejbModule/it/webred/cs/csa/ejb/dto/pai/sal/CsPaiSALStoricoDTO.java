package it.webred.cs.csa.ejb.dto.pai.sal;

import java.io.Serializable;
import java.util.Date;

public class CsPaiSALStoricoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private Long id;

	private Long idPai;

	private String mansione;

	private String tutorContesto;

	private String userIns;
	
	private Date dtIns;
	
	private CsPaiSalDTO sal;
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Long getIdPai() {
		return idPai;
	}

	public void setIdPai(Long idPai) {
		this.idPai = idPai;
	}

	public String getMansione() {
		return mansione;
	}

	public void setMansione(String mansione) {
		this.mansione = mansione;
	}

	public String getTutorContesto() {
		return tutorContesto;
	}

	public void setTutorContesto(String tutorContesto) {
		this.tutorContesto = tutorContesto;
	}

	public String getUserIns() {
		return userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public CsPaiSalDTO getSal() {
		return sal;
	}

	public void setSal(CsPaiSalDTO sal) {
		this.sal = sal;
	}
	
	
	
}
