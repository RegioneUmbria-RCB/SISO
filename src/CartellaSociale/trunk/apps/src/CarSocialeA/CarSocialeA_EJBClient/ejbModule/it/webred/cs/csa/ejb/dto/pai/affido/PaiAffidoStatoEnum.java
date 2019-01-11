package it.webred.cs.csa.ejb.dto.pai.affido;

public enum PaiAffidoStatoEnum {
	
	VALUTAZIONE_CASO(10,"Valutazione caso"),
	RICERCA_FAMIGLIA(20,"Ricerca famiglia"),
	VALUTAZIONE(30,"Valutazione"),
	FAMIGLIA_INDIVIDUATA(40,"Famiglia individuata"),
	AFFIDO_ATTIVO(50,"Affido attivo"),
	AFFIDO_CHIUSO(60,"Affido chiuso");
	
	private Integer valore;
	private String descrizione;

	private PaiAffidoStatoEnum(Integer valore, String descrizione){
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
		
		PaiAffidoStatoEnum[] sa = PaiAffidoStatoEnum.values();
		
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
