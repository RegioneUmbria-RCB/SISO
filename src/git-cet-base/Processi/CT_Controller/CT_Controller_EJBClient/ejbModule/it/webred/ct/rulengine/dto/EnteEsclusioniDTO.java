package it.webred.ct.rulengine.dto;

import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.controller.model.REnteEsclusioni;

import java.io.Serializable;

public class EnteEsclusioniDTO implements Serializable {
	
	private RCommand rCommand;
	private REnteEsclusioni rEnteEsclusioni;
	private String enable;
	
	public RCommand getrCommand() {
		return rCommand;
	}
	public void setrCommand(RCommand rCommand) {
		this.rCommand = rCommand;
	}
	public REnteEsclusioni getrEnteEsclusioni() {
		return rEnteEsclusioni;
	}
	public void setrEnteEsclusioni(REnteEsclusioni rEnteEsclusioni) {
		this.rEnteEsclusioni = rEnteEsclusioni;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}

	
}
