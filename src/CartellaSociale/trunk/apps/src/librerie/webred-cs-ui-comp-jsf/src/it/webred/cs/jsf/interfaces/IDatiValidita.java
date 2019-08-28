package it.webred.cs.jsf.interfaces;

import java.util.Date;

public interface IDatiValidita {
	
	public Long getId();
	
	public Date getDataInizio();
	
	public Date getDataFine();
	
	public boolean isAttivo();
	
	public boolean isFinito();
	
	public Date getDataTemp();
	
	public String getDescrizione();
	
	public String getTipo();

	public boolean isPrevalente();
	
}
