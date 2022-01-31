package it.escsolution.escwebgis.crif.bean;

import it.escsolution.escwebgis.anagrafe.bean.Anagrafe;
import it.escsolution.escwebgis.common.EscObject;
import it.escsolution.escwebgis.redditiAnnuali.bean.RedditiAnnuali;
import it.escsolution.escwebgis.toponomastica.bean.Civico;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

public class RedditoTotFamiglia extends EscObject implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Civico civico;
	private String codiceFamiglia;
	private String anno;
	private List<RedditoTotSoggetto> listaSoggetti;
	
	private Double importo = 0d;

	public Civico getCivico() {
		return civico;
	}

	public void setCivico(Civico civico) {
		this.civico = civico;
	}

	public List<RedditoTotSoggetto> getListaSoggetti() {
		return listaSoggetti;
	}

	public void setListaSoggetti(List<RedditoTotSoggetto> listaSoggetti) {
		this.listaSoggetti = listaSoggetti;
	}

	public Double getImporto() {
		
		Double somma = 0d;
		boolean reddNonDisp = true;
		
		for(RedditoTotSoggetto s: listaSoggetti){
			
			List<RedditoTotDichiarato> redditi = s.getRedditi();
			reddNonDisp = reddNonDisp && (redditi.size()==0);
			
			for(RedditoTotDichiarato r : redditi)
				somma += r.getImportoComplessivo()!=null ? r.getImportoComplessivo() : 0d ;
		}
		
		if(reddNonDisp)
			somma = null;
		
		
		this.setImporto(somma);
		
		return importo;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}
	
	public void addSoggetto(RedditoTotSoggetto s){
		this.listaSoggetti.add(s);
	}

	public String getCodiceFamiglia() {
		return codiceFamiglia;
	}

	public void setCodiceFamiglia(String codiceFamiglia) {
		this.codiceFamiglia = codiceFamiglia;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}
	
}
