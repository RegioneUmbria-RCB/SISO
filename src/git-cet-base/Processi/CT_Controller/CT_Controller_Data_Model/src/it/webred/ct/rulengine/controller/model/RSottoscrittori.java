package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="R_SOTTOSCRITTORI")
public class RSottoscrittori implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="IDSOTTOSCRITTORE")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_r_sottoscrittori" )
	@SequenceGenerator(name="seq_r_sottoscrittori", sequenceName="SEQ_R_SOTTOSCRITTORI")
	private Long idSottoscrittore;
	
	
	@Column(name="FK_NAME")
	private String fkName;
	
	private String belfiore;
		
	@Column(name="FK_COD_COMMAND")
	private String fkCodCommand;
	
	@Column(name="FK_USER_EMAIL")
	private String fkUserEmail;
	
	public Long getIdSottoscrittore() {
		return idSottoscrittore;
	}

	public void setIdSottoscrittore(Long idSottoscrittore) {
		this.idSottoscrittore = idSottoscrittore;
	}

	public String getFkName() {
		return fkName;
	}

	public void setFkName(String fkName) {
		this.fkName = fkName;
	}

	public String getBelfiore() {
		return belfiore;
	}

	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

	public String getFkCodCommand() {
		return fkCodCommand;
	}

	public void setFkCodCommand(String fkCodCommand) {
		this.fkCodCommand = fkCodCommand;
	}

	public String getFkUserEmail() {
		return fkUserEmail;
	}

	public void setFkUserEmail(String fkUserEmail) {
		this.fkUserEmail = fkUserEmail;
	}
	

}
