package it.webred.cs.jsf.bean.erogazioneIntervento;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

public class ErogazioneInterventoUtils {

	public static class SumDTO
	{
		protected String label = "";
		protected String totale = null;
		protected String unitaMisura = "";
		
		public SumDTO(){
			
		}
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public String getTotale() {
			return totale;
		}
		public void setTotale(String total) {
			this.totale = total;
		}
		public String getUnitaMisura() {
			return unitaMisura;
		}
		public void setUnitaMisura(String unitaMisura) {
			this.unitaMisura = unitaMisura;
		}
		public String getTotaleUnitaMisura(){
			
			if( totale == null ) return "-";
			
			String res = totale;
			if( unitaMisura != null ) res += " " + unitaMisura;
			
			return res;
		}
	}
	
	protected static Integer sumAsInteger( String sA, String sB ) {
		sA = StringUtils.isEmpty( sA ) ? "0" : sA;
		sB = StringUtils.isEmpty( sB ) ? "0" : sB;

		Integer ret = Integer.parseInt(sA) + Integer.parseInt(sB);
		return ret;
	}
	
	protected static Double sumAsDouble( String sA, String sB ) {
		sA = StringUtils.isEmpty( sA ) ? "0" : sA;
		sB = StringUtils.isEmpty( sB ) ? "0" : sB;

		Double ret = Double.parseDouble(sA) + Double.parseDouble(sB);
		return ret;
	}
	
	protected static String sumAsTimestamp( String sA, String sB ) {
		sA = StringUtils.isEmpty( sA ) ? "00:00" : sA;
		sB = StringUtils.isEmpty( sB ) ? "00:00" : sB;

		try
		{
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
			timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

			Date date1 = timeFormat.parse(sA);
			Date date2 = timeFormat.parse(sB);

			long sum = date1.getTime() + date2.getTime();

			int ss = (int)((sum/=1000) % 60);
			int mm = (int)((sum/=60) % 60);
			int hh = (int)(sum/=60);
			int gg = (int)(sum/=24);
			
			String sSum = String.format("%02d:%02d", hh, mm);
			
			return sSum;
		}
		catch(Exception ex) {
			return "Errore";
		}
	}
	
	public static SumDTO sum( InterventoErogazAttrBean val, SumDTO dto){
		
		if (val.isInteger() && StringUtils.isNotEmpty( (String)val.getValue() ) ) {
			Integer sum = ErogazioneInterventoUtils.sumAsInteger( dto.getTotale(), (String)val.getValue() );
			dto.setTotale( String.valueOf(sum) );
			dto.setUnitaMisura( StringUtils.trimToEmpty(val.getUnitaMisuraSelezionataValore()) );
			dto.setLabel(val.getLabel());
		}
		else if (val.isDouble() && StringUtils.isNotEmpty( (String)val.getValue() )) {
			Double sum = ErogazioneInterventoUtils.sumAsDouble( dto.getTotale(), (String)val.getValue() );
			dto.setTotale( String.format("%.2f", sum));
			dto.setUnitaMisura( StringUtils.trimToEmpty(val.getUnitaMisuraSelezionataValore()) );
			dto.setLabel(val.getLabel());
		}
		else if (val.isTime() && StringUtils.isNotEmpty( (String)val.getValue() )) {
			dto.setTotale( ErogazioneInterventoUtils.sumAsTimestamp( dto.getTotale(), (String)val.getValue() ) );
			dto.setUnitaMisura( StringUtils.trimToEmpty(val.getUnitaMisuraSelezionataValore()) );
			dto.setLabel(val.getLabel());
		}
		
		return dto;
	}

}
