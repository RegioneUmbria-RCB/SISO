package it.webred.cs.csa.ejb.dto.pai.pti;

public enum PaiPTITipoProrogaEnum {
	TIPO1(new Long(1), "Tipo 1"), TIPO2(new Long(2), "Tipo 2"),
	TIPO3(new Long(3), "Tipo 3");

	private Long id;
	private String descrizione;

	private PaiPTITipoProrogaEnum(Long id, String descrizione) {
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
public static String getDescrizioneFromValore(Long val){
		
		PaiPTITipoProrogaEnum[] sa = PaiPTITipoProrogaEnum.values();
		
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
