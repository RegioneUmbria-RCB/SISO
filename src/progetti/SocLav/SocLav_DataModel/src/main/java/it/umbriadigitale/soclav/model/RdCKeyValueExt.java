package it.umbriadigitale.soclav.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RDC_KEY_VALUE_EXT")
public class RdCKeyValueExt {
	
	@Id
	public String id;
	
	@Column(name="KEY_CONF")
	public String keyConf;
	
	@Column(name="VALUE_CONF")
	public String keyValue;
	
	@Column(name="SERVIZIO")
	public String servizio;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeyConf() {
		return keyConf;
	}

	public void setKeyConf(String keyConf) {
		this.keyConf = keyConf;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getServizio() {
		return servizio;
	}

	public void setServizio(String servizio) {
		this.servizio = servizio;
	}
	
	
	
}
