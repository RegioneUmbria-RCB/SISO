package it.webred.ct.config.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


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

	//bi-directional many-to-many association to AmComune
    @ManyToMany
	@JoinTable(
		name="AM_INSTANCE_COMUNE"
		, joinColumns={
			@JoinColumn(name="FK_AM_INSTANCE")
			}
		, inverseJoinColumns={
			@JoinColumn(name="FK_AM_COMUNE")
			}
		)
	private List<AmComune> amComunes;

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

	public List<AmComune> getAmComunes() {
		return this.amComunes;
	}

	public void setAmComunes(List<AmComune> amComunes) {
		this.amComunes = amComunes;
	}
	
}