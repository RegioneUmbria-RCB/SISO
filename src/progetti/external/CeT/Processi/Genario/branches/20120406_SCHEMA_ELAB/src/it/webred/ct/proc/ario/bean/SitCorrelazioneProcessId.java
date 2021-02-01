package it.webred.ct.proc.ario.bean;

import java.sql.Date;

public class SitCorrelazioneProcessId {

	private String processId;	
	private int idEnte;	
	private Date dataAggiornamento;
	private int progEs;
	
		
		
	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}
	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}	
	public int getIdEnte() {
		return idEnte;
	}
	public void setIdEnte(int idEnte) {
		this.idEnte = idEnte;
	}
	public int getProgEs() {
		return progEs;
	}
	public void setProgEs(int progEs) {
		this.progEs = progEs;
	}	
	
	
	
}
