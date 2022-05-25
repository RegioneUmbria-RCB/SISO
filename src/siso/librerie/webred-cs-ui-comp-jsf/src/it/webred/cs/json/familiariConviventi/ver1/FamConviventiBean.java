package it.webred.cs.json.familiariConviventi.ver1;

import it.webred.cs.data.DataModelCostanti.GrVulnerabile;
import it.webred.cs.json.dto.JsonBaseBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FamConviventiBean extends JsonBaseBean {
    
	private boolean conFigli=false;
	private FigliBean minoriIta;
	private FigliBean maggioriIta;
	private FigliBean minoriEst;
	private FigliBean maggioriEst;
	
	private int tipologiaFamiliare;
	private int conMinoriNum=0;
	
	private String gruppoVulnerabile;
	private String gruppoVulnerabile2; //SISO-794
	private String gruppoVulnerabile3; //SISO-794
	
	
	//vive con altri
	private int conFigliNum=0;
	private boolean conCompagnoConiuge=false;
	private int conGenitoriAffidatariNum=0;
	private int conAltriParentiNum=0;
	private int conStranieriNum=0;
	private int conItalianiNum=0;
	
	@JsonIgnore
	private boolean validaGVulnerabileMigrante;
	
	public void initPnlFigli(){
		minoriIta = new FigliBean();
		maggioriIta= new FigliBean();
		minoriEst= new FigliBean();
		maggioriEst= new FigliBean();
	}
	
	@Override
	public FamConviventiBean autoClone() throws Exception{
		FamConviventiBean fcb = (FamConviventiBean)super.autoClone(); //TODO: Implementare, altrimenti vengono copiati i soli riferimenti
		fcb.setMaggioriEst(this.maggioriEst!=null ? this.maggioriEst.autoClone() : new FigliBean());
		fcb.setMaggioriIta(this.maggioriIta!=null ? this.maggioriIta.autoClone() : new FigliBean());
		fcb.setMinoriEst(this.minoriEst!=null ? this.minoriEst.autoClone() : new FigliBean());
		fcb.setMinoriIta(this.minoriIta!=null ? this.minoriIta.autoClone() : new FigliBean());
		return fcb;
	}
	
	@Override
	public List<String> checkObbligatorieta() {
		
		List<String> messages = new LinkedList<String>();
	
		if(tipologiaFamiliare <= 0)
			messages.add("Tipologia Nucleo è un campo obbligatorio");
		
		//SISO-794
		if(gruppoVulnerabile.isEmpty())
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
	
	public FigliBean getMinoriIta() {
		return minoriIta;
	}

	public FigliBean getMaggioriIta() {
		return maggioriIta;
	}

	public FigliBean getMinoriEst() {
		return minoriEst;
	}

	public FigliBean getMaggioriEst() {
		return maggioriEst;
	}

	public int getTipologiaFamiliare() {
		return tipologiaFamiliare;
	}

	public int getConFigliNum() {
		return conFigliNum;
	}

	public boolean isConCompagnoConiuge() {
		return conCompagnoConiuge;
	}

	public int getConAltriParentiNum() {
		return conAltriParentiNum;
	}

	public int getConStranieriNum() {
		return conStranieriNum;
	}

	public int getConItalianiNum() {
		return conItalianiNum;
	}

	public int getConMinoriNum() {
		return conMinoriNum;
	}

	public void setMinoriIta(FigliBean minoriIta) {
		this.minoriIta = minoriIta;
	}

	public void setMaggioriIta(FigliBean maggioriIta) {
		this.maggioriIta = maggioriIta;
	}

	public void setMinoriEst(FigliBean minoriEst) {
		this.minoriEst = minoriEst;
	}

	public void setMaggioriEst(FigliBean maggioriEst) {
		this.maggioriEst = maggioriEst;
	}

	public void setTipologiaFamiliare(int tipologiaFamiliare) {
		this.tipologiaFamiliare = tipologiaFamiliare;
	}

	public void setConFigliNum(int conFigliNum) {
		this.conFigliNum = conFigliNum;
	}

	public void setConCompagnoConiuge(boolean conCompagnoConiuge) {
		this.conCompagnoConiuge = conCompagnoConiuge;
	}

	public void setConAltriParentiNum(int conAltriParentiNum) {
		this.conAltriParentiNum = conAltriParentiNum;
	}
	
	public void setConStranieriNum(int conStranieriNum) {
		this.conStranieriNum = conStranieriNum;
	}

	public void setConItalianiNum(int conItalianiNum) {
		this.conItalianiNum = conItalianiNum;
	}

	public void setConMinoriNum(int conMinoriNum) {
		this.conMinoriNum = conMinoriNum;
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

	public int getConGenitoriAffidatariNum() {
		return conGenitoriAffidatariNum;
	}

	public void setConGenitoriAffidatariNum(int conGenitoriAffidatariNum) {
		this.conGenitoriAffidatariNum = conGenitoriAffidatariNum;
	}

	public boolean isConFigli() {
		return conFigli;
	}

	public void setConFigli(boolean conFigli) {
		this.conFigli = conFigli;
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
	public void setValidaGVulnerabileMigrante(boolean valida) {
		this.validaGVulnerabileMigrante = valida;
	}	
}
