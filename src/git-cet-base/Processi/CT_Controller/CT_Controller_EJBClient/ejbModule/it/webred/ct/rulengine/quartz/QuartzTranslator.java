package it.webred.ct.rulengine.quartz;

import java.util.Calendar;

import it.webred.ct.rulengine.controller.model.RSchedulerTime;
import it.webred.ct.rulengine.quartz.dto.QuartzTranslatorSource;
import it.webred.ct.rulengine.quartz.dto.TriggerIntervalInfo;

import static org.quartz.DateBuilder.IntervalUnit;

import org.apache.log4j.Logger;

public class QuartzTranslator {
	
	private Logger log = Logger.getLogger(QuartzTranslator.class.getName());
	
	private static QuartzTranslator me;
	private QuartzTranslatorSource qts;
	
	private static final Long MAX_MINUTI_TENTATIVI = new Long(20);
	private static final Long MAX_ORE_TENTATIVI = new Long(23);
	private static final Long MAX_GIORNI_TENTATIVI = new Long(3);
	private static final Long MAX_SETTIMANE_TENTATIVI = new Long(1);
	private static final Long MAX_MESI_TENTATIVI = new Long(1);
	private static final Long MAX_NULL_TENTATIVI = new Long(5);
	
	private Long maxTentativi;
	
	public QuartzTranslator() {
		super();
	}


	public Long getMaxTentativi() {
		return maxTentativi;
	}


	public static QuartzTranslator getInstance() {
		if(me == null) {
			me = new QuartzTranslator();
		}
		
		return me;
	}
	
	/**
	 * Il metodo indica quale metodo utilizzare per la codifica dell'intervallo temporale
	 * del processo schedulato
	 * 
	 * @param rst
	 * @return
	 */
	public String getMethod(RSchedulerTime rst) {
		String methodName = "encode";
		String suffix = null;
		
		try {
			Long minuti = rst.getOgniMinuti();
			if(minuti != null) {
				qts = new QuartzTranslatorSource(rst.getDtStart(),minuti.intValue());
				maxTentativi = MAX_MINUTI_TENTATIVI;
				throw new Throwable("Minuti");
			}
			
			Long ore = rst.getOgniOre();
			if(ore != null) {
				qts = new QuartzTranslatorSource(rst.getDtStart(),ore.intValue());
				maxTentativi = MAX_ORE_TENTATIVI;
				throw new Throwable("Ore");
			}
			
			Long giorni = rst.getOgniGiorni();
			if(giorni != null) {
				qts = new QuartzTranslatorSource(rst.getDtStart(),giorni.intValue());
				maxTentativi = MAX_GIORNI_TENTATIVI;
				throw new Throwable("Giorni");
			}
			
			Long settimane = rst.getOgniSettimane();
			if(settimane != null) {
				qts = new QuartzTranslatorSource(rst.getDtStart(),settimane.intValue());
				maxTentativi = MAX_SETTIMANE_TENTATIVI;
				throw new Throwable("Settimane");
			}
			
			Long mesi = rst.getOgniMesi();
			if(mesi != null) {
				qts = new QuartzTranslatorSource(rst.getDtStart(),mesi.intValue());
				maxTentativi = MAX_MESI_TENTATIVI;
				throw new Throwable("Mesi");
			}	
			
			//se nn è stato impostato l'intervallo: job che parte solo una volta
			qts = new QuartzTranslatorSource(rst.getDtStart(),null);
			maxTentativi = MAX_NULL_TENTATIVI;
			throw new Throwable("None");
			
		}catch(Throwable t) {
			suffix = t.getMessage();
		}finally {
			methodName += suffix;
			log.debug("Metodo per encoding: "+methodName);
		}
		
		return methodName;
	}
	
	
	public String encodeNone() {
		StringBuilder encoded = new StringBuilder();
		
		log.debug("Generazione chiave codifica nessun intervallo");
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(qts.getTargetTime().getTime());
		log.debug("Target date: "+c.getTime());
		
		//0 20 15 17 1 ? 2011
		encoded.append(c.get(Calendar.SECOND));
		encoded.append(" ");
		encoded.append(c.get(Calendar.MINUTE));
		encoded.append(" ");
		encoded.append(c.get(Calendar.HOUR_OF_DAY));
		encoded.append(" ");
		encoded.append(c.get(Calendar.DAY_OF_MONTH));
		encoded.append(" ");
		encoded.append(c.get(Calendar.MONTH) + 1);
		encoded.append(" ");
		encoded.append("?");
		encoded.append(" ");
		encoded.append(c.get(Calendar.YEAR));
		
		log.info("Encoded quartz key: "+encoded.toString());
		
		return encoded.toString();
	}
	
	public String encodeOre() {
		StringBuilder encoded = new StringBuilder();
		
		log.debug("Generazione chiave codifica intervallo in Ore");
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(qts.getTargetTime().getTime());
		log.debug("Target date: "+c.getTime());
		log.debug("Intervallo: "+qts.getIntervallo());
				
		encoded.append(c.get(Calendar.SECOND));
		encoded.append(" ");
		encoded.append(c.get(Calendar.MINUTE));
		encoded.append(" ");
		encoded.append("0");
		encoded.append("/");
		encoded.append(qts.getIntervallo());
		encoded.append(" * * ? ");
		
		log.info("Encoded quartz key: "+encoded.toString());
		
		return encoded.toString();
	}
	
	public String encodeGiorni() {
		StringBuilder encoded = new StringBuilder();
		
		log.debug("Generazione chiave codifica intervallo in Giorni");
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(qts.getTargetTime().getTime());
		log.debug("Target date: "+c.getTime());
		log.debug("Intervallo: "+qts.getIntervallo());
		
		//int houres = qts.getIntervallo().intValue() * 24;
		
		encoded.append(c.get(Calendar.SECOND));
		encoded.append(" ");
		encoded.append(c.get(Calendar.MINUTE));
		encoded.append(" ");
		encoded.append(c.get(Calendar.HOUR_OF_DAY));
		encoded.append(" ");
		encoded.append(c.get(Calendar.DAY_OF_MONTH));
		encoded.append("/");
		encoded.append(qts.getIntervallo());
		encoded.append(" * ? ");
		
		log.info("Encoded quartz key: "+encoded.toString());
		
		return encoded.toString();
	}
	
	public String encodeSettimane() {
		StringBuilder encoded = new StringBuilder();
		
		log.debug("Generazione chiave codifica intervallo in Settimane");
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(qts.getTargetTime().getTime());
		log.debug("Target date: "+c.getTime());
		log.debug("Intervallo: "+qts.getIntervallo());
				
		//encoded.append(c.get(Calendar.SECOND));
		//encoded.append(" ");
		//encoded.append(c.get(Calendar.MINUTE));
		//encoded.append(" ");
		//encoded.append(c.get(Calendar.HOUR_OF_DAY));
		//encoded.append(" ?");
		//encoded.append(" * ");
		//encoded.append(c.get(Calendar.DAY_OF_WEEK));
		//encoded.append("/");
		//encoded.append(qts.getIntervallo());
		
		encoded.append(c.get(Calendar.SECOND));
		encoded.append(" ");
		encoded.append(c.get(Calendar.MINUTE));
		encoded.append(" ");
		encoded.append(c.get(Calendar.HOUR_OF_DAY));
		encoded.append(" ");
		encoded.append(c.get(Calendar.DAY_OF_MONTH));
		encoded.append("/");
		encoded.append(qts.getIntervallo() * 7);
		encoded.append(" * ? ");
		
		log.info("Encoded quartz key: "+encoded.toString());
		
		return encoded.toString();
	}
	
	public String encodeMesi() {
		StringBuilder encoded = new StringBuilder();
		
		log.debug("Generazione chiave codifica intervallo in Mesi");
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(qts.getTargetTime().getTime());
		log.debug("Target date: "+c.getTime());
		log.debug("Intervallo: "+qts.getIntervallo());
		
		encoded.append("0 0 0 * "+(c.get(Calendar.MONTH)+1)+"/"+qts.getIntervallo()+" ?");
		log.info("Encoded quartz key: "+encoded.toString());
		
		return encoded.toString();
	}
	
	
	
	
	public TriggerIntervalInfo getTriggerIntervalInfo(RSchedulerTime rst) {
		TriggerIntervalInfo tif = null;
		
		try {
			Long minuti = rst.getOgniMinuti();
			if(minuti != null) {
				tif = new TriggerIntervalInfo(IntervalUnit.MINUTE,minuti.intValue());
				maxTentativi = MAX_MINUTI_TENTATIVI;
				throw new Throwable("MINUTI::"+minuti);
			}
			
			Long ore = rst.getOgniOre();
			if(ore != null) {
				tif = new TriggerIntervalInfo(IntervalUnit.HOUR,ore.intValue());
				maxTentativi = MAX_ORE_TENTATIVI;
				throw new Throwable("ORE::"+ore);
			}
			
			Long giorni = rst.getOgniGiorni();
			if(giorni != null) {
				tif = new TriggerIntervalInfo(IntervalUnit.DAY,giorni.intValue());
				maxTentativi = MAX_GIORNI_TENTATIVI;
				throw new Throwable("GIORNI::"+giorni);
			}
			
			Long settimane = rst.getOgniSettimane();
			if(settimane != null) {
				tif = new TriggerIntervalInfo(IntervalUnit.WEEK,settimane.intValue());
				maxTentativi = MAX_SETTIMANE_TENTATIVI;
				throw new Throwable("SETTIMANE::"+settimane);
			}
			
			Long mesi = rst.getOgniMesi();
			if(mesi != null) {
				tif = new TriggerIntervalInfo(IntervalUnit.MONTH,mesi.intValue());
				maxTentativi = MAX_MESI_TENTATIVI;
				throw new Throwable("MESI:"+mesi);
			}	
			
			//se nn è stato impostato l'intervallo: job che parte solo una volta con cron trigger
			maxTentativi = MAX_NULL_TENTATIVI;
			qts = new QuartzTranslatorSource(rst.getDtStart(),null);
			tif = new TriggerIntervalInfo(true,encodeNone());
			throw new Throwable("IMMEDIATO");
			
		}catch(Throwable t) {
			log.debug("Info intervallo del trigger: "+t.getMessage());
		}
		
		
		return tif;
	}
	
	
	
}
