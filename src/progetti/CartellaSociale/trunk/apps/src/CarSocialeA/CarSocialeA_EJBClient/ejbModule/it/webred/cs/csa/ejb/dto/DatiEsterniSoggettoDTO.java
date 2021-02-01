package it.webred.cs.csa.ejb.dto;

public class DatiEsterniSoggettoDTO extends BaseDTO {

	private static final long serialVersionUID = -4562413567251054648L;

	public DatiEsterniSoggettoDTO() {
	}

	public DatiEsterniSoggettoDTO(String cfSoggetto, String codiceEnte) {
		setObj(cfSoggetto);
		setObj2(codiceEnte);
	}

	public String getCodiceFiscale() {
		return (String) getObj();
	}

	public String getCodiceEnte() {
		return (String) getObj2();
	}

	@Override
	public String toString() {
		return "DatiEsterniSoggettoDTO [cf=" + getCodiceFiscale() + ", codiceEnte=" + getCodiceEnte() + "]";
	}

}
