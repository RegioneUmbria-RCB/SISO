package it.webred.cs.csa.ejb.dto.pai.sal;

public enum PaiSALFaseEnum {
	FASE_PRELIMINARE(10,"Fase preliminare / conoscitiva"),
	ORIENTAMENTO(20,"Orientamento"),
	PROGETTAZIONE(30,"Progettazione"),
	ATTIVAZIONE_STRUMENTI(40,"Attivazione strumenti"),
	ACCOMPAGNAMENTO(50,"Accompagnamento"),
	MONITORAGGIO(60,"Monitoraggio"),
	VALUTAZIONE_FINALE(70,"Valutazione Finale");


	private Integer valore;
	private String descrizione;

	private PaiSALFaseEnum(Integer valore, String descrizione){
		this.valore = valore;
		this.descrizione = descrizione;
	}

	public Integer getValore() {
		return valore;
	}

	public String getDescrizione() {
		return descrizione;
	}
	
	public static String getDescrizioneFromValore(Integer val){
		
		PaiSALFaseEnum[] sa = PaiSALFaseEnum.values();
		
		String toReturn = null;
		
		for (int i = 0; i < sa.length; i++) {
			if(sa[i].valore.equals(val)){
				toReturn = sa[i].descrizione;
				break;
			}
		}
		
		return toReturn;
	}
}
