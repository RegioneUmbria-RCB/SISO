package it.webred.cs.csa.ejb.dto.pai.pti;


public enum PaiPTIFaseEnum {
	
	INS(new Long(1), "INSERITO"), 
	INS_STRUTT(new Long(2), "INSERITO_DA_STRUTTURA"),
	RICH_DISP(new Long(3), "RICHIESTA_DISPONIBILITA"), 
	STRUTT_OK(new Long(4), "STRUTTURA_OK"),
	EROG_OK(new Long(5), "EROGAZIONE_OK"),
	CHIUSA_OK(new Long(6), "CHIUSA_OK");

	private Long id;
	private String descrizione;

	private PaiPTIFaseEnum(Long id, String descrizione) {
		this.id = id;
		this.descrizione = descrizione;
	}

	public Long getId() {
		return id;
	}

	public String getDescrizione() {
		return descrizione;
	}
	
	
@SuppressWarnings("unlikely-arg-type")
public static String getDescrizioneFromValore(Integer val){
		
		PaiPTIFaseEnum[] sa = PaiPTIFaseEnum.values();
		
		String toReturn = null;
		
		for (int i = 0; i < sa.length; i++) {
			if(sa[i].getId().intValue() == val){
//			if(sa[i].id.equals(val)){
				toReturn = sa[i].descrizione;
				break;
			}
		}
		
		return toReturn;
	}

}
