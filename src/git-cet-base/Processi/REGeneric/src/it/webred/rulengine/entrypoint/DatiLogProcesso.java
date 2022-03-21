package it.webred.rulengine.entrypoint;

import java.io.Serializable;

public class DatiLogProcesso implements Serializable {
	
	private String processId;
	private String nomeTabella;
	private String inseriti;
	private String aggiornati;
	private String sostituiti;
	
	public String getAggiornati()
	{
		return aggiornati;
	}
	public void setAggiornati(String aggiornati)
	{
		this.aggiornati = aggiornati;
	}
	public String getNomeTabella()
	{
		return nomeTabella;
	}
	public void setNomeTabella(String nomeTabella)
	{
		this.nomeTabella = nomeTabella;
	}
	public String getInseriti()
	{
		return inseriti;
	}
	public void setInseriti(String inseriti)
	{
		this.inseriti = inseriti;
	}
	public String getProcessId()
	{
		return processId;
	}
	public void setProcessId(String processId)
	{
		this.processId = processId;
	}
	public String getSostituiti()
	{
		return sostituiti;
	}
	public void setSostituiti(String sostituiti)
	{
		this.sostituiti = sostituiti;
	}
}
