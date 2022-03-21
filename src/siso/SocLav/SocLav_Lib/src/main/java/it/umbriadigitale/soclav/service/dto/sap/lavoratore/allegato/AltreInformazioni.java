package it.umbriadigitale.soclav.service.dto.sap.lavoratore.allegato;

import java.io.Serializable;

public class AltreInformazioni implements Serializable {
	
	/*
	 * 	<altreinformazioni_lst>
			<altreinformazioni>    <codpatenteguida>01</codpatenteguida></altreinformazioni>
			<altreinformazioni>    <codpatenteguida>02</codpatenteguida></altreinformazioni>
			<altreinformazioni>    <codpatenteguida>03</codpatenteguida></altreinformazioni>
			<altreinformazioni>    <codpatenteguida>04</codpatenteguida></altreinformazioni>
			<altreinformazioni>    <codpatenteguida>05</codpatenteguida></altreinformazioni>
			<altreinformazioni>    <codpatenteguida>06</codpatenteguida></altreinformazioni>
			<altreinformazioni>    <codpatenteguida>12</codpatenteguida></altreinformazioni>
			<altreinformazioni>    <codpatenteguida>13</codpatenteguida></altreinformazioni>
		</altreinformazioni_lst>    
	 * */
    private String codpatenteguida;
    
    //TODO:iscrizione ad albie ordini professionali (Tabella Albi)
    //TODO:possesso patentini (Tabella Abilitazioni) 
    
    private String despatenteguida;
    private String desalbo;
    private String desabilitazione;

	public String getCodpatenteguida() {
		return codpatenteguida;
	}

	public void setCodpatenteguida(String codpatenteguida) {
		this.codpatenteguida = codpatenteguida;
	}

	public String getDespatenteguida() {
		return despatenteguida;
	}

	public void setDespatenteguida(String despatenteguida) {
		this.despatenteguida = despatenteguida;
	}

	public String getDesalbo() {
		return desalbo;
	}

	public void setDesalbo(String desalbo) {
		this.desalbo = desalbo;
	}

	public String getDesabilitazione() {
		return desabilitazione;
	}

	public void setDesabilitazione(String desabilitazione) {
		this.desabilitazione = desabilitazione;
	}
}
