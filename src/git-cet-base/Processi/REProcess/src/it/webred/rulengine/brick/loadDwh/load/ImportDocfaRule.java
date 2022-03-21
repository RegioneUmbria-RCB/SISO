package it.webred.rulengine.brick.loadDwh.load;


import java.io.ByteArrayInputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.loadDwh.load.docfa.Caricatore;
import it.webred.rulengine.brick.loadDwh.load.docfa.CaricatoreDocfa;
import it.webred.rulengine.brick.loadDwh.load.docfa.DocfaEnv;
import it.webred.rulengine.brick.loadDwh.load.docfa.LeggiTracciato;
import it.webred.rulengine.brick.loadDwh.load.docfa.TracciatoXml;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.def.TypeFactory;

public class ImportDocfaRule extends Command implements Rule{
	private static Logger log = Logger.getLogger(ImportDocfaRule.class.getName());
	
	public ImportDocfaRule(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		CommandAck retAck = null;
		
		String nomeTracciatoXml = null;
		String tracciatoXml = null;
		
		try {
			DocfaEnv env = new DocfaEnv((String)ctx.get("connessione"), ctx);

			//recupero parametri da jelly
			ComplexParam nomeTracciato = 
				(ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.1.descr"));
			
			HashMap<String,ComplexParamP> pars = nomeTracciato.getParams();
			Set set = pars.entrySet();
			Iterator it = set.iterator();
			int i =1;
			while (it.hasNext()) {
				Entry entry = (Entry)it.next();
				ComplexParamP p = (ComplexParamP)entry.getValue();
				Object o = TypeFactory.getType(p.getValore(),p.getType());
				nomeTracciatoXml = o.toString();
			}
			
			log.debug("Nome tracciato DOCFA: "+nomeTracciatoXml);
			
			ComplexParam streamTracciato = 
				(ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.2.descr"));
			
			HashMap<String,ComplexParamP> pars2 = streamTracciato.getParams();
			Set set2 = pars2.entrySet();
			Iterator it2 = set2.iterator();
			int i2 =1;
			while (it2.hasNext()) {
				Entry entry = (Entry)it2.next();
				ComplexParamP p = (ComplexParamP)entry.getValue();
				Object o = TypeFactory.getType(p.getValore(),p.getType());
				tracciatoXml = o.toString();
			}
			
			log.debug("XML tracciato DOCFA {$DIR_FILES}:\n"+tracciatoXml);
			//sostituire nello stream xml la variabile $DIR_FILES
			String dirFiles = env.getPercorsoFiles();
			tracciatoXml = tracciatoXml.replaceAll("DIR_FILES", dirFiles);
			log.debug("XML tracciato DOCFA:\n"+tracciatoXml);
			
			//lettura tracciato
			ByteArrayInputStream bais = new ByteArrayInputStream(tracciatoXml.getBytes());
			TracciatoXml tXml = LeggiTracciato.caricaTracciatoXml(bais);
			log.debug("Tracciato XML: "+tXml.getSourceTab());
			
			//chiamata caricatore docfa
			Caricatore caricatore = new CaricatoreDocfa("");
			int esito = caricatore.executeCaricamentoSpec(tXml, nomeTracciatoXml, ctx.getProcessID(), 
								Calendar.getInstance().getTimeInMillis(),env);
			log.debug("Esito: "+esito);
			
			retAck = new ApplicationAck("Import DOCFA eseguita con successo");
			
		}catch (Exception e) {
			log.error("Errore grave importando i dati", e);
			retAck =  new ErrorAck(e);
		}
		
		return retAck;
	}

}
