package it.webred.rulengine.brick.idoneitaAlloggi.bean;

import it.webred.rulengine.brick.idoneitaAlloggi.bean.Indirizzo;
import it.webred.rulengine.brick.idoneitaAlloggi.bean.Titolare;

import java.io.Serializable;

public class Catasto implements Serializable {
	
	private static final long serialVersionUID = 8673249207008306302L;
	
	private String foglio;
	private String mappale;
	private String sub;
	private String categoria;
	private String classe;
	private double rendita;
	private String renditaStrFmt;
	
	private Titolare titolare;
  	private Indirizzo indirizzo;
  	
  	private String listaCodFiscTitolari;
  	private String listaDenomTitolari;
  	private String listaTipoTitoloTitolari;
  	
	private String prefisso = "";
	private String nomeVia = "";
	private String civico = "";
	private int idCivico = 0;
  	
	public Catasto() {
		// TODO Auto-generated constructor stub
	}//-------------------------------------------------------------------------

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getMappale() {
		return mappale;
	}

	public void setMappale(String mappale) {
		this.mappale = mappale;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public double getRendita() {
		return rendita;
	}

	public void setRendita(double rendita) {
		this.rendita = rendita;
	}

	public String getRenditaStrFmt() {
		return renditaStrFmt;
	}

	public void setRenditaStrFmt(String renditaStrFmt) {
		this.renditaStrFmt = renditaStrFmt;
	}

	public Titolare getTitolare() {
		return titolare;
	}

	public void setTitolare(Titolare titolare) {
		this.titolare = titolare;
	}

	public Indirizzo getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(Indirizzo indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getListaCodFiscTitolari() {
		return listaCodFiscTitolari;
	}

	public void setListaCodFiscTitolari(String listaCodFiscTitolari) {
		this.listaCodFiscTitolari = listaCodFiscTitolari;
	}

	public String getListaDenomTitolari() {
		return listaDenomTitolari;
	}

	public void setListaDenomTitolari(String listaDenomTitolari) {
		this.listaDenomTitolari = listaDenomTitolari;
	}

	public String getListaTipoTitoloTitolari() {
		return listaTipoTitoloTitolari;
	}

	public void setListaTipoTitoloTitolari(String listaTipoTitoloTitolari) {
		this.listaTipoTitoloTitolari = listaTipoTitoloTitolari;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getPrefisso() {
		return prefisso;
	}

	public void setPrefisso(String prefisso) {
		this.prefisso = prefisso;
	}

	public String getNomeVia() {
		return nomeVia;
	}

	public void setNomeVia(String nomeVia) {
		this.nomeVia = nomeVia;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public int getIdCivico() {
		return idCivico;
	}

	public void setIdCivico(int idCivico) {
		this.idCivico = idCivico;
	}
	
	

}

