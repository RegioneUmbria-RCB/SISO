package it.webred.amprofiler.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AM_INSTANCE database table.
 * 
 */
@Entity
@Table(name="AM_INSTANCE")
public class AmInstance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String name;

	@Column(name="FK_AM_APPLICATION")
	private String fkAmApplication;

	private String url;

    public AmInstance() {
    }

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFkAmApplication() {
		return this.fkAmApplication;
	}

	public void setFkAmApplication(String fkAmApplication) {
		this.fkAmApplication = fkAmApplication;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}