package it.webred.cs.csa.ejb.dto.pai.affido;

import java.io.Serializable;

public class CsPaiAffidoDominioDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String dominio;
	
	private String codice;
	
	private String descrizione;
	private String codiceSinba;

	public CsPaiAffidoDominioDTO(String dominio, String codice, String descrizione) {
		this.dominio = dominio;
		this.codice = codice;
		this.descrizione = descrizione;
	}
	//SISO-981 Inizio
	public CsPaiAffidoDominioDTO(String dominio, String codice, String descrizione, String codiceSinba) {
		 
		this(dominio, codice, descrizione);
		this.codiceSinba = codiceSinba;
	}
	//SISO-981 Fine

	public String getDominio() {
		return dominio;
	}

	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

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

		//SISO-981 Inizio

		public String getCodiceSinba() {
			return codiceSinba;
		}

		public void setCodiceSinba(String codiceSinba) {
			this.codiceSinba = codiceSinba;
		}
		//SISO-981 Fine
}
