package it.webred.rulengine.entrypoint;

import java.io.Serializable;
import java.util.Date;

public class StatoRe implements Serializable
{
	private boolean finito;
	private int numeroAnomalie;
	private int numeroErrori;
	private int numeroReject;
	private Date dataInizio;
	private Date dataFine;
	
	public String processId;

	public boolean isFinito()
	{
		return finito;
	}
	public void setFinito(boolean finito)
	{
		this.finito = finito;
	}
	public int getNumeroAnomalie()
	{
		return numeroAnomalie;
	}
	public void setNumeroAnomalie(int numeroAnomalie)
	{
		this.numeroAnomalie = numeroAnomalie;
	}
	public int getNumeroErrori()
	{
		return numeroErrori;
	}
	public void setNumeroErrori(int numeroErrori)
	{
		this.numeroErrori = numeroErrori;
	}
	public String getProcessId()
	{
		return processId;
	}
	public void setProcessId(String processId)
	{
		this.processId = processId;
	}
	public int getNumeroReject()
	{
		return numeroReject;
	}
	public void setNumeroReject(int numeroReject)
	{
		this.numeroReject = numeroReject;
	}
	public Date getDataFine()
	{
		return dataFine;
	}
	public void setDataFine(Date dataFine)
	{
		this.dataFine = dataFine;
	}
	public Date getDataInizio()
	{
		return dataInizio;
	}
	public void setDataInizio(Date dataInizio)
	{
		this.dataInizio = dataInizio;
	}
	
}
