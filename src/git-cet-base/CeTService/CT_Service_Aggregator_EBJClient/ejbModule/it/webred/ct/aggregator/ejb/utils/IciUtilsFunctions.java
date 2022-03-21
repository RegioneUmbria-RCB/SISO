package it.webred.ct.aggregator.ejb.utils;

public class IciUtilsFunctions {
	private static final float PERC_RIVALUTAZIONE = 105;
	
	public static float getImponibileIci (float renditaCat, String categoria) {
		float imponibile = 0;
		imponibile = renditaCat * PERC_RIVALUTAZIONE /100 * getMoltiplicatore(categoria) ;
		return imponibile;
	}
	private static float getMoltiplicatore(String categoria) {
		float moltiplicatore=-1;
		if (categoria==null || categoria.equals(""))
			return moltiplicatore	;
		String gruppo=categoria.substring(0,1).toUpperCase();;
		String str = categoria.substring(1);
		int sottoGruppo = -1;
		try {sottoGruppo = Integer.parseInt(str);} catch(NumberFormatException nfe ) {}
		if (gruppo.equals("A")) {
			moltiplicatore=100;
			if (sottoGruppo ==10)
				moltiplicatore=50;
		}
		if (gruppo.equals("B"))
		{
			moltiplicatore=140;
		}
		if (gruppo.equals("C"))
		{
			moltiplicatore=100;
		}
		if (gruppo.equals("D")){
			moltiplicatore=50;
			if (sottoGruppo ==1)
				moltiplicatore=34;
		}
		return moltiplicatore;
	}
		
	
}
