package it.webred.amprofiler.model.perm;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PermAccesso implements Serializable{

	@Id
	private String id;
	private String ipRange;
	private String oraDa;
	private String oraA;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIpRange() {
		return ipRange;
	}
	public void setIpRange(String ipRange) {
		this.ipRange = ipRange;
	}
	public String getOraDa() {
		return oraDa;
	}
	public void setOraDa(String oraDa) {
		this.oraDa = oraDa;
	}
	public String getOraA() {
		return oraA;
	}
	public void setOraA(String oraA) {
		this.oraA = oraA;
	}
	
}
