package it.webred.amprofiler.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AM_GROUP database table.
 * 
 */
@Entity
@Table(name="AM_GROUP")
public class AmGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String name;

	@Column(name="FK_AM_COMUNE")
	private String fkAmComune;

	@Column(name = "PERM_RANGE_IP")
	private String permRangeIp;
	
	@Column(name = "PERM_ORA_DA")
	private String permOraDa;
	
	@Column(name = "PERM_ORA_A")
	private String permOraA;
	
    public AmGroup() {
    }

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFkAmComune() {
		return this.fkAmComune;
	}

	public void setFkAmComune(String fkAmComune) {
		this.fkAmComune = fkAmComune;
	}

	public String getPermRangeIp() {
		return permRangeIp;
	}

	public void setPermRangeIp(String permRangeIp) {
		this.permRangeIp = permRangeIp;
	}

	public String getPermOraDa() {
		return permOraDa;
	}

	public void setPermOraDa(String permOraDa) {
		this.permOraDa = permOraDa;
	}

	public String getPermOraA() {
		return permOraA;
	}

	public void setPermOraA(String permOraA) {
		this.permOraA = permOraA;
	}
}