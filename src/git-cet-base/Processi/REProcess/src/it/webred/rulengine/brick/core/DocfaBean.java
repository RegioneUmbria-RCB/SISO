package it.webred.rulengine.brick.core;

import java.util.ArrayList;

public class DocfaBean {
	
	private String protocolloReg;
	private String dataFornitura;
	private String foglio;
	private String particella;
	private String subalterno;
	private ArrayList elencoUiu;
	private DocfaUIUTitolareBean dichiarante;
	
	public DocfaBean() {
		protocolloReg = "";
		dataFornitura = "";
		foglio = "";
		particella = "";
		subalterno = "";
		elencoUiu = new ArrayList();
		dichiarante = null;
	}
	
	public String getDataFornitura() {
		return dataFornitura;
	}
	public void setDataFornitura(String dataFornitura) {
		this.dataFornitura = dataFornitura;
	}
	
	public ArrayList getElencoUiu() {
		return elencoUiu;
	}
	public void setElencoUiu(ArrayList elencoUiu) {
		this.elencoUiu = elencoUiu;
	}
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public String getParticella() {
		return particella;
	}
	public void setParticella(String particella) {
		this.particella = particella;
	}
	public String getProtocolloReg() {
		return protocolloReg;
	}
	public void setProtocolloReg(String protocolloReg) {
		this.protocolloReg = protocolloReg;
	}
	public String getSubalterno() {
		return subalterno;
	}
	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public DocfaUIUTitolareBean getDichiarante() {
		return dichiarante;
	}

	public void setDichiarante(DocfaUIUTitolareBean dichiarante) {
		this.dichiarante = dichiarante;
	}

}
