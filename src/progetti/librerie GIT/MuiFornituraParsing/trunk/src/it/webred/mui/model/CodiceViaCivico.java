package it.webred.mui.model;

import it.webred.mui.consolidation.ViarioDecoder;

public class CodiceViaCivico {
	private String codiceVia;
	private String civico;
	private String civicoUnico;
	
	private static ViarioDecoder viario = new ViarioDecoder();
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public String getCodiceVia() {
		return codiceVia;
	}
	public String getVia() throws Exception {
		return viario.decodeViaSIT(this);
	}
	public void setCodiceVia(String codiceVia) {
		this.codiceVia = codiceVia;
	}
	public String getCivicoUnico() {
		return civicoUnico;
	}
	public void setCivicoUnico(String civicoUnico) {
		this.civicoUnico = civicoUnico;
	}

}

