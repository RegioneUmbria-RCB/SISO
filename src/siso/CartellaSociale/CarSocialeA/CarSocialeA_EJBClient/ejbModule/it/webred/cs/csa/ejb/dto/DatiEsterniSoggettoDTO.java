package it.webred.cs.csa.ejb.dto;

public class DatiEsterniSoggettoDTO extends BaseDTO {

	private static final long serialVersionUID = -4562413567251054648L;
	 
	private ConfiguratoreDatiEsterniDTO configuratoreDatiEsterni = null;
	
	private ParametriDatiEsterniSoggettoDTO parametriEsterni = null;
	
	
	public DatiEsterniSoggettoDTO() {
	}

	
	
	public ParametriDatiEsterniSoggettoDTO getParametriEsterni() {
		return parametriEsterni;
	}



	public void setParametriEsterni(ParametriDatiEsterniSoggettoDTO parametriEsterni) {
		this.parametriEsterni = parametriEsterni;
	}



	public DatiEsterniSoggettoDTO(String cfSoggetto, String codiceEnte) {
		setObj(cfSoggetto);
		setObj2(codiceEnte);
		this.parametriEsterni = new ParametriDatiEsterniSoggettoDTO(cfSoggetto);
	}

	public String getCodiceFiscale() {
		return (String) getObj();
	}
	
	public String getCodiceEnte() {
		return (String) getObj2();
	}
    
	public String getStatoDomanda() {
		return (String) getObj4();
	}
	
	public String getCodicePrestazione() {
		return (String) getObj3();
	}
	
	
	@Override
	public String toString() {
		return "DatiEsterniSoggettoDTO [cf=" + getCodiceFiscale() + ", codiceEnte=" + getCodiceEnte() + "]";
	}

	public ConfiguratoreDatiEsterniDTO getConfiguratoreDatiEsterni() {
		return configuratoreDatiEsterni;
	}

	public void setConfiguratoreDatiEsterni(ConfiguratoreDatiEsterniDTO configuratoreDatiEsterni) {
		this.configuratoreDatiEsterni = configuratoreDatiEsterni;
	}

	

 

}
