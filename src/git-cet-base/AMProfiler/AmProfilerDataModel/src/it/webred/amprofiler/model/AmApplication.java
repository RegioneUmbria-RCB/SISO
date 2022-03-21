package it.webred.amprofiler.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AM_APPLICATION database table.
 * 
 */
@Entity
@Table(name="AM_APPLICATION")
public class AmApplication implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String name;

	@Column(name="APP_CATEGORY")
	private String appCategory;

	@Column(name="APP_TYPE")
	private String appType;
	
	@Column(name="FLG_DATA_ROUTER")
	private String flgDataRouter;

    public AmApplication() {
    }

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppCategory() {
		return this.appCategory;
	}

	public void setAppCategory(String appCategory) {
		this.appCategory = appCategory;
	}

	public String getAppType() {
		return this.appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getFlgDataRouter() {
		return flgDataRouter;
	}

	public void setFlgDataRouter(String flgDataRouter) {
		this.flgDataRouter = flgDataRouter;
	}

}