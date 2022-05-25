package it.webred.cs.json.familiariConviventi.ver2;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.GrVulnerabile;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.dto.JsonBaseBean;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FamConviventiBean extends JsonBaseBean {
    
	private int tipologiaFamiliare;
	
	private String gruppoVulnerabile;
	private String gruppoVulnerabile2; //SISO-794
	private String gruppoVulnerabile3; //SISO-794
	
	@JsonIgnore
	private boolean validaGVulnerabileMigrante;

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
		if(StringUtils.isEmpty(gruppoVulnerabile))
			messages.add("Gruppo vulnerabile principale è un campo obbligatorio");
		
		if(this.validaGVulnerabileMigrante){
			//Almeno uno dei gruppi vulnerabili deve essere migrante
			if(!existsProfugoMigrante())
				messages.add("Almeno un gruppo vulnerabile deve essere 'Migrante' o 'Profugo (migrante forzato, sfollato o in transito)'");
			
		}
		
		return messages;
	}

	private boolean isMigrante(String val) {
		return !StringUtils.isBlank(val) && Arrays.asList(GrVulnerabile.stampaMigrante).contains(val);
	}
	
	public boolean existsProfugoMigrante() {
		boolean isMigrante = this.isMigrante(gruppoVulnerabile) || this.isMigrante(gruppoVulnerabile2) || this.isMigrante(gruppoVulnerabile3);
		return isMigrante;
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

	public void setValidaGVulnerabileMigrante(boolean valida) {
		this.validaGVulnerabileMigrante = valida;
	}

		
}
