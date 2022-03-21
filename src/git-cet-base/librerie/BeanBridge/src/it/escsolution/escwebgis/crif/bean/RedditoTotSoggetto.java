package it.escsolution.escwebgis.crif.bean;

import it.escsolution.escwebgis.anagrafe.bean.Anagrafe;
import it.escsolution.escwebgis.common.EscObject;
import it.escsolution.escwebgis.redditiAnnuali.bean.RedditiAnnuali;
import it.escsolution.escwebgis.toponomastica.bean.Civico;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

public class RedditoTotSoggetto extends EscObject implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Civico civico;
	private Anagrafe residente;
	private List<RedditoTotDichiarato> redditi;
	
	public Civico getCivico() {
		return civico;
	}
	public void setCivico(Civico civico) {
		this.civico = civico;
	}
	public Anagrafe getResidente() {
		return residente;
	}
	public void setResidente(Anagrafe residente) {
		this.residente = residente;
	}

	public List<RedditoTotDichiarato> getRedditi() {
		return redditi;
	}
	public void setRedditi(List<RedditoTotDichiarato> redditi) {
		this.redditi = redditi;
	}

	
}
