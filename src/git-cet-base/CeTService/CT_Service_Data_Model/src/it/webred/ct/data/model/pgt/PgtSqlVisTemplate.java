package it.webred.ct.data.model.pgt;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the PGT_SQL_VIS_TEMPLATE database table.
 * 
 */
@Entity
@Table(name="PGT_SQL_VIS_TEMPLATE")
public class PgtSqlVisTemplate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PGT_SQL_VIS_TEMPLATE_ID_GENERATOR", sequenceName="SEQ_PGT_SQL_VIS_TEMPLATE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PGT_SQL_VIS_TEMPLATE_ID_GENERATOR")
	private long id;

	@Column(name="DESCR_APP")
	private String descrApp;

	@Column(name="FK_AM_APPLICATION")
	private String fkAmApplication;

	@Column(name="SQL_TEMPLATE")
	private String sqlTemplate;
	

	public PgtSqlVisTemplate() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescrApp() {
		return this.descrApp;
	}

	public void setDescrApp(String descrApp) {
		this.descrApp = descrApp;
	}

	public String getFkAmApplication() {
		return this.fkAmApplication;
	}

	public void setFkAmApplication(String fkAmApplication) {
		this.fkAmApplication = fkAmApplication;
	}

	public String getSqlTemplate() {
		return this.sqlTemplate;
	}

	public void setSqlTemplate(String sqlTemplate) {
		this.sqlTemplate = sqlTemplate;
	}

}