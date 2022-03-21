package it.webred.ct.data.access.basic.docfa.dto;

import java.io.Serializable;

import it.webred.ct.data.access.basic.catasto.dto.ParametriCatastaliDTO;

public class BeniNonCensDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private ParametriCatastaliDTO params01;
	private ParametriCatastaliDTO params02;
	private ParametriCatastaliDTO params03;
	
	public ParametriCatastaliDTO getParams01() {
		return params01;
	}
	public void setParams01(ParametriCatastaliDTO params01) {
		this.params01 = params01;
	}
	public ParametriCatastaliDTO getParams02() {
		return params02;
	}
	public void setParams02(ParametriCatastaliDTO params02) {
		this.params02 = params02;
	}
	public ParametriCatastaliDTO getParams03() {
		return params03;
	}
	public void setParams03(ParametriCatastaliDTO params03) {
		this.params03 = params03;
	}
	
	
}
