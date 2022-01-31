package it.webred.cet.permission;

import it.webred.amprofiler.model.AmGroup;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;



public class CeTUser  implements Serializable {

	 private String username;
	 private HashMap<String, String> permList;
	 private List<String> enteList;
	 private String currentEnte;
	 private String sessionId;
	 private List<String> permRangeIp;
	 private String permOraDa;
	 private String permOraA;
	 private List<AmGroup> groupList;
 
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	
	public String getName() {
		return this.username;
	}
	
	public List<String> getEnteList() {
		return enteList;
	}
	public void setEnteList(List<String> enteList) {
		this.enteList = enteList;
	}
	public HashMap<String, String> getPermList() {
		return permList;
	}
	public void setPermList(HashMap<String, String> permList) {
		this.permList = permList;
	}
	public String getCurrentEnte() {
		return currentEnte;
	}
	public void setCurrentEnte(String currentEnte) {
		this.currentEnte = currentEnte;
	}
	public List<String> getPermRangeIp() {
		return permRangeIp;
	}
	public void setPermRangeIp(List<String> permRangeIp) {
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
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public List<AmGroup> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<AmGroup> groupList) {
		this.groupList = groupList;
	}
 
}
