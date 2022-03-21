package it.webred.ct.rulengine.dto;

import it.webred.rulengine.brick.bean.CommandAck;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;


public class Task implements Serializable {
	
	/*
	 * Parametri in input per qualunque tipo di chiamata - INPUT
	 */
	private String belfiore;
	private Long idFonte;
	private Long idTipologia;
	private Object freeObj;
	

	
	/*
	 * Parametro di output finale - OUTPUT
	 */
	private CommandAck ack;
	
	
	
	
	
	/*
	 * Parametri di runtime
	 */
	private String taskId;
	private String description;
	private String status;
	private Date startTime;
	private Date endTime;
	
	/*
	 * Process ID di runtime del comando RE da lanciare
	 */
	private String processId;  
	
	
	//falg che indica se il task o processo rispetta i criteri per essere eseguito
	private boolean isProcessable;

	
	private HashMap diagParams;  //opzionale (riservato all'esecuzione delle diagnostiche)
	
	
	public Task() {
		super();
		// TODO Auto-generated constructor stub
	}




	public Task(String belfiore, Long idFonte, Long idTipologia) {
		super();
		this.belfiore = belfiore;
		this.idFonte = idFonte;
		this.idTipologia = idTipologia;
	}
	
	
	
	
	public boolean isProcessable() {
		return isProcessable;
	}
	public void setProcessable(boolean isProcessable) {
		this.isProcessable = isProcessable;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getBelfiore() {
		return belfiore;
	}
	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}
	public Long getIdFonte() {
		return idFonte;
	}
	public void setIdFonte(Long idFonte) {
		this.idFonte = idFonte;
	}
	public Long getIdTipologia() {
		return idTipologia;
	}
	public void setIdTipologia(Long idTipologia) {
		this.idTipologia = idTipologia;
	}
	public Object getFreeObj() {
		return freeObj;
	}
	public void setFreeObj(Object freeObj) {
		this.freeObj = freeObj;
	}
	public CommandAck getAck() {
		return ack;
	}
	public void setAck(CommandAck ack) {
		this.ack = ack;
	}	

	public Task getCopy() {
		//copia esatta
		Task t = new Task(this.belfiore,this.idFonte,this.idTipologia);
		t.setAck(this.ack);
		t.setDescription(this.description);
		t.setEndTime(this.endTime);
		t.setFreeObj(this.freeObj);
		t.setProcessable(this.isProcessable);
		t.setProcessId(this.processId);
		t.setStartTime(this.startTime);
		t.setStatus(this.status);
		t.setTaskId(this.taskId);
		
		return t;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}




	public HashMap getDiagParams() {
		return diagParams;
	}




	public void setDiagParams(HashMap diagParams) {
		this.diagParams = diagParams;
	}




	
}
