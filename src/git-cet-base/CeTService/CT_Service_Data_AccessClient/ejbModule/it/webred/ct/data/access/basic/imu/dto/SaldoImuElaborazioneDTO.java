package it.webred.ct.data.access.basic.imu.dto;

import it.webred.ct.data.access.basic.anagrafe.dto.DatiAnagrafeDTO;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.List;

public class SaldoImuElaborazioneDTO extends CeTBaseObject{

	private static final long serialVersionUID = 1L;

	private List<DatiAnagrafeDTO> tabAnagrafe;
	private List<JsonDatiCatastoDTO> tabCatastoImm;
	private List<JsonDatiCatastoDTO> tabCatastoTerr;
	private String urlSportello;
	private String sidSportello;
	private String codbelSportello;
	private String codfisSportello;
	private DatiAnagrafeDTO dichiarante;
	private Integer numFigli;

	
	public List<DatiAnagrafeDTO> getTabAnagrafe() {
		return tabAnagrafe;
	}


	public void setTabAnagrafe(List<DatiAnagrafeDTO> tabAnagrafe) {
		this.tabAnagrafe = tabAnagrafe;
	}

	
	public void addCatasto(JsonDatiCatastoDTO dto){
		
		String tipo = dto.getTipoImm();
		
		if("F".equalsIgnoreCase(tipo)){
			if(tabCatastoImm==null)
				tabCatastoImm = new ArrayList<JsonDatiCatastoDTO>();
			tabCatastoImm.add(dto);
		}else if("T".equalsIgnoreCase(tipo)){
			if(tabCatastoTerr==null)
				tabCatastoTerr = new ArrayList<JsonDatiCatastoDTO>();
			tabCatastoTerr.add(dto);
		}
	
	}


	public String getUrlSportello() {
		return urlSportello;
	}


	public void setUrlSportello(String urlSportello) {
		this.urlSportello = urlSportello;
	}


	public String getSidSportello() {
		return sidSportello;
	}


	public void setSidSportello(String sidSportello) {
		this.sidSportello = sidSportello;
	}


	public String getCodbelSportello() {
		return codbelSportello;
	}


	public void setCodbelSportello(String codbelSportello) {
		this.codbelSportello = codbelSportello;
	}


	public String getCodfisSportello() {
		return codfisSportello;
	}


	public void setCodfisSportello(String codfisSportello) {
		this.codfisSportello = codfisSportello;
	}


	public Integer getNumFigli() {
		return numFigli;
	}


	public void setNumFigli(Integer numFigli) {
		this.numFigli = numFigli;
	}

	public List<JsonDatiCatastoDTO> getTabCatastoImm() {
		return tabCatastoImm;
	}


	public void setTabCatastoImm(List<JsonDatiCatastoDTO> tabCatastoImm) {
		this.tabCatastoImm = tabCatastoImm;
	}


	public List<JsonDatiCatastoDTO> getTabCatastoTerr() {
		return tabCatastoTerr;
	}


	public void setTabCatastoTerr(List<JsonDatiCatastoDTO> tabCatastoTerr) {
		this.tabCatastoTerr = tabCatastoTerr;
	}


	public DatiAnagrafeDTO getDichiarante() {
		return dichiarante;
	}


	public void setDichiarante(DatiAnagrafeDTO dichiarante) {
		this.dichiarante = dichiarante;
	}



	
}
