package it.webred.ct.proc.ario.aggregatori;

import java.io.Serializable;

public class BeanViario implements Serializable
{
	private String idvia;
	private String iddwh;
	private String indirizzo;
	private String sedime;
	private String fkentesorgente;
	private String proges;
	private String rating;
	private String codiceViaOrig;
	private String reldescr;
	
	public String getProges()
	{
		return proges;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public void setProges(String proges)
	{
		this.proges = proges;
	}

	public String getFkentesorgente()
	{
		return fkentesorgente;
	}

	public void setFkentesorgente(String fkentesorgente)
	{
		this.fkentesorgente = fkentesorgente;
	}

	public String getIdvia()
	{
		return idvia;
	}
	


	public String getIddwh()
	{
		return iddwh;
	}

	public void setIddwh(String iddwh)
	{
		this.iddwh = iddwh;
	}

	public void setIdvia(String idvia)
	{
		this.idvia = idvia;
	}
	public String getIndirizzoNormalizzato()
	{
		if(indirizzo != null)
		{
			if (indirizzo.startsWith("S")) {
				if (indirizzo.contains("SANTA"))
					indirizzo = indirizzo.replace("SANTA", "S.");
				else if (indirizzo.contains("SANTO"))
					indirizzo = indirizzo.replace("SANTO", "S.");	
				else if (indirizzo.contains("SAN"))
					indirizzo = indirizzo.replace("SANT", "S.");
			}
			// ' e spazio con apostofo senza spazio
			indirizzo = indirizzo.replaceAll("[\\'][\\s]","'");
			indirizzo =  indirizzo.replaceAll("[\\.][\\s]",".");			
		}
		return indirizzo;
	}
	public String getIndirizzo()
	{
		return indirizzo;
	}	
	public void setIndirizzo(String indirizzo)
	{
		this.indirizzo = indirizzo;
	}
	public String getSedime()
	{
		return sedime;
	}
	public void setSedime(String sedime)
	{
		this.sedime = sedime;
	}

	public String getCodiceViaOrig() {
		return codiceViaOrig;
	}

	public void setCodiceViaOrig(String codiceViaOrig) {
		this.codiceViaOrig = codiceViaOrig;
	}

	public String getReldescr() {
		return reldescr;
	}

	public void setReldescr(String reldescr) {
		this.reldescr = reldescr;
	}
	
}
