package it.webred.ct.rulengine.dto;

import it.webred.ct.config.model.AmFonte;
import it.webred.ct.rulengine.controller.model.RAnagStati;

import java.io.Serializable;

public class MonitorDTO implements Serializable {
	
	private AmFonte fonte;
	private boolean repProcessable;
	private String repDescription;
	private String repStatus;
	private Long repCommandId;
	private boolean acqProcessable;
	private String acqDescription;
	private String acqStatus;
	private Long acqCommandId;
	private boolean trtProcessable;
	private String trtStatus;
	private Long trtCommandId;
	
	public AmFonte getFonte() {
		return fonte;
	}
	public void setFonte(AmFonte fonte) {
		this.fonte = fonte;
	}
	public boolean isRepProcessable() {
		return repProcessable;
	}
	public void setRepProcessable(boolean repProcessable) {
		this.repProcessable = repProcessable;
	}
	public String getRepStatus() {
		return repStatus;
	}
	public void setRepStatus(String repStatus) {
		this.repStatus = repStatus;
	}
	public boolean isAcqProcessable() {
		return acqProcessable;
	}
	public void setAcqProcessable(boolean acqProcessable) {
		this.acqProcessable = acqProcessable;
	}
	public String getAcqStatus() {
		return acqStatus;
	}
	public void setAcqStatus(String acqStatus) {
		this.acqStatus = acqStatus;
	}
	public boolean isTrtProcessable() {
		return trtProcessable;
	}
	public void setTrtProcessable(boolean trtProcessable) {
		this.trtProcessable = trtProcessable;
	}
	public String getTrtStatus() {
		return trtStatus;
	}
	public void setTrtStatus(String trtStatus) {
		this.trtStatus = trtStatus;
	}
	public String getRepDescription() {
		return repDescription;
	}
	public void setRepDescription(String repDescription) {
		this.repDescription = repDescription;
	}
	public String getAcqDescription() {
		return acqDescription;
	}
	public void setAcqDescription(String acqDescription) {
		this.acqDescription = acqDescription;
	}
	public Long getRepCommandId() {
		return repCommandId;
	}
	public void setRepCommandId(Long repCommandId) {
		this.repCommandId = repCommandId;
	}
	public Long getAcqCommandId() {
		return acqCommandId;
	}
	public void setAcqCommandId(Long acqCommandId) {
		this.acqCommandId = acqCommandId;
	}
	public Long getTrtCommandId() {
		return trtCommandId;
	}
	public void setTrtCommandId(Long trtCommandId) {
		this.trtCommandId = trtCommandId;
	}	
	
}
