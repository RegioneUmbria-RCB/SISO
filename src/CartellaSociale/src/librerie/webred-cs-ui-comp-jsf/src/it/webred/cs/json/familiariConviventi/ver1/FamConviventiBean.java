package it.webred.cs.json.familiariConviventi.ver1;

import it.webred.cs.json.dto.JsonBaseBean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

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
	
	//vive con altri
	private int conFigliNum=0;
	private boolean conCompagnoConiuge=false;
	private int conGenitoriAffidatariNum=0;
	private int conAltriParentiNum=0;
	private int conStranieriNum=0;
	private int conItalianiNum=0;
	
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
		
		return messages;
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


}
