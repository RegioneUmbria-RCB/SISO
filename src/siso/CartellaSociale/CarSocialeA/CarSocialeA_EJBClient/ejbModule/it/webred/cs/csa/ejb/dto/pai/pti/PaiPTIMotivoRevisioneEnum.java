package it.webred.cs.csa.ejb.dto.pai.pti;

public enum PaiPTIMotivoRevisioneEnum {
	ELAB(new Long(1), "Elaborazione"), VALU(new Long(2), "Valutazione"),
	VERI(new Long(3), "Verifica"), RIVAL(new Long(4), "Rivalutazione");

	private Long id;
	private String descrizione;

	private PaiPTIMotivoRevisioneEnum(Long id, String descrizione) {
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

		PaiPTIMotivoRevisioneEnum[] sa = PaiPTIMotivoRevisioneEnum.values();

		String toReturn = null;
		if (val != null) {
			for (int i = 0; i < sa.length; i++) {
				if (sa[i].getId().intValue() == val) {
					toReturn = sa[i].descrizione;
					break;
				}
			}
		}
		return toReturn;
	}

}
