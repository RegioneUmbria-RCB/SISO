package it.webred.ct.data.access.basic.common.utils;

import java.util.ArrayList;
import java.util.Hashtable;

public class Info {
	
	public static Hashtable<String, String> htCausali = new Hashtable<String, String>();
	public static Hashtable<String, String> htTipiOperazioni = new Hashtable<String, String>();

	public static ArrayList<String> g1 = new ArrayList<String>();
	public static ArrayList<String> g2 = new ArrayList<String>();
	public static Hashtable<String, String> htCategorieCatastali = new Hashtable<String, String>();
	public static Hashtable<String, String> htFlagNuovaCostituzione = new Hashtable<String, String>();
	public static Hashtable<String, String> htSuperficiMedie = new Hashtable<String, String>();
	public static Hashtable<String, String> htCoeffSoppressioni = new Hashtable<String, String>();
	public static Hashtable<String, String> htFornitureStatus = new Hashtable<String, String>();
	public static Hashtable<String, String> htMesi = new Hashtable<String, String>();
	
	static{
		htCategorieCatastali.put("A01", "ABITAZIONI CIVILI");
		htCategorieCatastali.put("A02", "ABITAZIONI CIVILI");
		htCategorieCatastali.put("A07", "ABITAZIONI CIVILI");
		htCategorieCatastali.put("A08", "ABITAZIONI CIVILI");
		htCategorieCatastali.put("A09", "ABITAZIONI CIVILI");
		htCategorieCatastali.put("A03", "ABITAZIONI TIPO ECONOMICO");
		htCategorieCatastali.put("A04", "ABITAZIONI TIPO ECONOMICO");
		htCategorieCatastali.put("A05", "ABITAZIONI TIPO ECONOMICO");
		htCategorieCatastali.put("A06", "ABITAZIONI TIPO ECONOMICO");
		
		g1.add("A01");
		g1.add("A02");
		g1.add("A07");
		g1.add("A08");
		g1.add("A09");
		
		g2.add("A03");
		g2.add("A04");
		g2.add("A05");
		g2.add("A06");

		htFlagNuovaCostituzione.put("0", "Normale");
		htFlagNuovaCostituzione.put("1", "Ottimo");
		
		htSuperficiMedie.put("A01", "24");
		htSuperficiMedie.put("A02", "19");
		htSuperficiMedie.put("A03", "18");
		htSuperficiMedie.put("A04", "19");
		htSuperficiMedie.put("A05", "19");
		//htSuperficiMedie.put("A06", "0");
		htSuperficiMedie.put("A07", "19");
		htSuperficiMedie.put("A08", "24");
		//htSuperficiMedie.put("A09", "0");
		htSuperficiMedie.put("A10", "22");
		//htSuperficiMedie.put("C06", "0");

		htCoeffSoppressioni.put("A01", "100");
		htCoeffSoppressioni.put("A02", "100");
		htCoeffSoppressioni.put("A03", "100");
		htCoeffSoppressioni.put("A04", "100");
		htCoeffSoppressioni.put("A05", "100");
		htCoeffSoppressioni.put("A06", "100");
		htCoeffSoppressioni.put("A07", "100");
		htCoeffSoppressioni.put("A08", "100");
		htCoeffSoppressioni.put("A09", "100");
		htCoeffSoppressioni.put("A10", "50");
		htCoeffSoppressioni.put("C01", "34");
		htCoeffSoppressioni.put("C02", "100");
		htCoeffSoppressioni.put("C03", "100");
		htCoeffSoppressioni.put("C04", "100");
		htCoeffSoppressioni.put("C05", "100");
		htCoeffSoppressioni.put("C06", "100");
		htCoeffSoppressioni.put("C07", "100");
		htCoeffSoppressioni.put("D01", "50");
		htCoeffSoppressioni.put("D02", "50");
		htCoeffSoppressioni.put("D03", "50");
		htCoeffSoppressioni.put("D04", "50");
		htCoeffSoppressioni.put("D05", "50");
		htCoeffSoppressioni.put("D06", "50");
		htCoeffSoppressioni.put("D07", "50");
		htCoeffSoppressioni.put("D08", "50");
		htCoeffSoppressioni.put("D09", "50");
		htCoeffSoppressioni.put("D10", "50");
		htCoeffSoppressioni.put("E01", "34");
		htCoeffSoppressioni.put("E01", "34");
		htCoeffSoppressioni.put("E02", "34");
		htCoeffSoppressioni.put("E03", "34");
		htCoeffSoppressioni.put("E04", "34");
		htCoeffSoppressioni.put("E05", "34");
		htCoeffSoppressioni.put("E06", "34");
		htCoeffSoppressioni.put("E07", "34");
		htCoeffSoppressioni.put("E08", "34");
		htCoeffSoppressioni.put("E09", "34");
		
		htFornitureStatus.put("0", "DA SCARICARE");
		htFornitureStatus.put("1", "SCARICATA");
		
		htMesi.put("01", "Gennaio");
		htMesi.put("02", "Febbraio");
		htMesi.put("03", "Marzo");
		htMesi.put("04", "Aprile");
		htMesi.put("05", "Maggio");
		htMesi.put("06", "Giugno");
		htMesi.put("07", "Luglio");
		htMesi.put("08", "Agosto");
		htMesi.put("09", "Settembre");
		htMesi.put("10", "Ottobre");
		htMesi.put("11", "Novembre");
		htMesi.put("12", "Dicembre");
	}
}
