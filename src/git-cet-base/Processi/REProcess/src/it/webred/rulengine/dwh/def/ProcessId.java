package it.webred.rulengine.dwh.def;

public class ProcessId implements Campo
{
	private String valore;
	
	public ProcessId(String valore) {
		this.valore = valore;
	}
	
	public void setValore (String valore)
	{
		this.valore = valore;
	}

	public String getValore()
	{
		return valore;
	}

	public void setValore(Object o)
	{
		valore =(String) o;		
	}

	public boolean equals(Object o) {
		try {
			ProcessId pid = (ProcessId) o;
			return equals(pid);
		} catch (Exception e) {
			return false;
		}
	}
	private boolean equals(ProcessId o) {
		if (o==null)
			return false;
		if (o.getValore()!=null && o.getValore().equals(valore))
			return true;
		else if (valore!=null && valore.equals(o.getValore()))
			return true;
		else
			return false; // anche nel caso di null null

		
		
	}


	
}