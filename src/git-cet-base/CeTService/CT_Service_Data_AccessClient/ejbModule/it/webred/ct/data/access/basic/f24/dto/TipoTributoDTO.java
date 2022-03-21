
package it.webred.ct.data.access.basic.f24.dto;

import java.io.Serializable;


public class TipoTributoDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String codice;
	private String descrizione;
	private String anno;
	
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	
}
