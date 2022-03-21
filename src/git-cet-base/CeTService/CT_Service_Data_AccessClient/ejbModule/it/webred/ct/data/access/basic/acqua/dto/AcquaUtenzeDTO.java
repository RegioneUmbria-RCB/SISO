package it.webred.ct.data.access.basic.acqua.dto;

import it.webred.ct.data.model.acqua.SitAcquaCatasto;
import it.webred.ct.data.model.acqua.SitAcquaUtenze;

import java.io.Serializable;
import java.util.List;

public class AcquaUtenzeDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private SitAcquaUtenze sitAcquaUtenze;
	private List<SitAcquaCatasto> listaSitAcquaCatasto;
	private String consumo;
	
	public SitAcquaUtenze getSitAcquaUtenze() {
		return sitAcquaUtenze;
	}
	public void setSitAcquaUtenze(SitAcquaUtenze sitAcquaUtenze) {
		this.sitAcquaUtenze = sitAcquaUtenze;
	}
	public List<SitAcquaCatasto> getListaSitAcquaCatasto() {
		return listaSitAcquaCatasto;
	}
	public void setListaSitAcquaCatasto(List<SitAcquaCatasto> listaSitAcquaCatasto) {
		this.listaSitAcquaCatasto = listaSitAcquaCatasto;
	}
	public String getConsumo() {
		return consumo;
	}
	public void setConsumo(String consumo) {
		this.consumo = consumo;
	}
	
}
