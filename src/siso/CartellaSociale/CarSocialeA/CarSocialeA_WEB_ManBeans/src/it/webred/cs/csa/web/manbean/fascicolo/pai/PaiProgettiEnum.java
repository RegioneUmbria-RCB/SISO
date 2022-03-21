package it.webred.cs.csa.web.manbean.fascicolo.pai;

public enum PaiProgettiEnum {

	AFFIDO("affido"),
	PTI("pti"),
	SAL("sal"), ;
	
	
	private String progetto;
	
	private PaiProgettiEnum(String tipo){
		this.progetto = tipo;
	}

	public String getProgetto() {
		return progetto;
	}
	
}
