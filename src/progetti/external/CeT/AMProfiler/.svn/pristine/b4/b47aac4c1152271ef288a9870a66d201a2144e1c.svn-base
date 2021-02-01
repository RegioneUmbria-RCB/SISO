package it.webred.amprofiler.model;

import java.io.Serializable;
import javax.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the AM_USER database table.
 * 
 */
@Entity
@Table(name = "AM_USER")
public class AmUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String name;

	@Column(name = "DISABLE_CAUSE")
	private String disableCause;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_UPD_PWD")
	private Date dtUpdPwd;

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_ACCESS")
	private Date lastAccess;

	private String pwd;

	@Column(name = "USER_INS")
	private String userIns;

	private String email;
	
	@Column(name = "PERM_RANGE_IP")
	private String permRangeIp;
	
	@Column(name = "PERM_ORA_DA")
	private String permOraDa;
	
	@Column(name = "PERM_ORA_A")
	private String permOraA;
	
	//uni-directional many-to-many association to AmGroup
    @ManyToMany
	@JoinTable(
		name="AM_USER_GROUP"
		, joinColumns={
			@JoinColumn(name="FK_AM_USER")
			}
		, inverseJoinColumns={
			@JoinColumn(name="FK_AM_GROUP")
			}
		)
	private List<AmGroup> amGroups;
	
	public AmUser() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisableCause() {
		return this.disableCause;
	}

	public void setDisableCause(String disableCause) {
		this.disableCause = disableCause;
	}

	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Date getDtUpdPwd() {
		return this.dtUpdPwd;
	}

	public String getDtUpdPwdToString() {
		if (this.dtUpdPwd != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(this.dtUpdPwd);
		}
		return null;
	}

	public void setDtUpdPwd(Date dtUpdPwd) {
		this.dtUpdPwd = dtUpdPwd;
	}

	public Date getLastAccess() {
		return this.lastAccess;
	}

	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUserIns() {
		return this.userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public List<AmGroup> getAmGroups() {
		return amGroups;
	}

	public void setAmGroups(List<AmGroup> amGroups) {
		this.amGroups = amGroups;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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