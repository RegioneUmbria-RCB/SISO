package it.webred.cs.json.familiariConviventi.ver2;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.dto.JsonBaseBean;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FamConviventiBean extends JsonBaseBean {
    
	private int tipologiaFamiliare;
	
	private String gruppoVulnerabile;
	private String gruppoVulnerabile2; //SISO-794
	private String gruppoVulnerabile3; //SISO-794
	

	@Override
	public FamConviventiBean autoClone() throws Exception{
		FamConviventiBean fcb = (FamConviventiBean)super.autoClone(); //TODO: Implementare, altrimenti vengono copiati i soli riferimenti
		
		return fcb;
	}
	
	@Override
	public List<String> checkObbligatorieta() {
		
		List<String> messages = new LinkedList<String>();
	
		if(isRenderTipologiaNucleo() && tipologiaFamiliare <= 0)
			messages.add("Tipologia Nucleo è un campo obbligatorio");
		
		//SISO-794
		if(gruppoVulnerabile.isEmpty())
			messages.add("Gruppo vulnerabile principale è un campo obbligatorio");
		
		return messages;
	}

	public int getTipologiaFamiliare() {
		return tipologiaFamiliare;
	}


	public void setTipologiaFamiliare(int tipologiaFamiliare) {
		this.tipologiaFamiliare = tipologiaFamiliare;
	}
	
/*	public void resetParametriConAltri(){
		this.conAltriParentiNum=0;
		this.conCompagnoConiuge=false;
		this.conGenitoriAffidatariNum=0;
		this.conFigliNum=0;
		this.conItalianiNum=0;
		this.conMinoriNum=0;
		this.conStranieriNum=0;
	}*/

	public String getGruppoVulnerabile() {
		return gruppoVulnerabile;
	}

	public void setGruppoVulnerabile(String gruppoVulnerabile) {
		this.gruppoVulnerabile = gruppoVulnerabile;
	}
	
	//inizio SISO-794
		public String getGruppoVulnerabile2() {
			return gruppoVulnerabile2;
		}

		public String getGruppoVulnerabile3() {
			return gruppoVulnerabile3;
		}
		
	    
		public void setGruppoVulnerabile2(String gruppoVulnerabile2) {
			this.gruppoVulnerabile2 = gruppoVulnerabile2;
		}

		public void setGruppoVulnerabile3(String gruppoVulnerabile3) {
			this.gruppoVulnerabile3 = gruppoVulnerabile3;
		}
		//fine SISO-794

		public boolean isRenderTipologiaNucleo(){
			String gestioneTipoFamiglia= CsUiCompBaseBean.getGestioneTipoFamiglia();
			return !DataModelCostanti.GestioneTipoFamiglia.DETTAGLIO.equalsIgnoreCase(gestioneTipoFamiglia);
		}

		
}
