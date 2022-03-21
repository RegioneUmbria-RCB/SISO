package it.webred.rulengine.brick.core;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.brick.loadDwh.load.util.Util;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class RiversaTmpDwh extends Command implements Rule {
	
	private static Logger log = Logger.getLogger(RiversaTmpDwh.class.getName());
	
	
	public RiversaTmpDwh(BeanCommand bc) {
		super(bc);
		// TODO Auto-generated constructor stub
	}

	public RiversaTmpDwh(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		CommandAck retAck = null;
		
		try {
			log.debug("Recupero parametro da contesto");
			String belfiore = ctx.getBelfiore();
			Long idFonte = ctx.getIdFonte();
			Map reverseObject = ctx.getReverseObjects();
			log.debug("Estrazione info tabelle da reverseObject");
			if (reverseObject==null)
				return  new RejectAck("Niente da riversare");
			if (reverseObject.get("reverse.tabelleDWH")==null)
				return  new RejectAck("Niente da riversare");

			ArrayList<String> tabelleDWH = (ArrayList)reverseObject.get("reverse.tabelleDWH");
			LinkedHashMap<String, String> tabs = (LinkedHashMap)reverseObject.get("reverse.tabs");
			
			Connection conn = ctx.getConnection((String)ctx.get("connessione"));
			
			log.debug("Recupero info replace fonte dati "+belfiore+"@"+idFonte);
			ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setComune(belfiore);
			criteria.setFonte(idFonte.toString());
			criteria.setKey("fornitura.in.replace");
			AmKeyValueExt amkvext = cdm.getAmKeyValueExt(criteria);
			String replace = amkvext.getValueConf();
			log.debug("Fornitura in replace ? "+replace);
			
			criteria = new ParameterSearchCriteria();
			criteria.setComune(belfiore);
			criteria.setFonte(idFonte.toString());
			criteria.setKey("disabilita.storico");
			amkvext = cdm.getAmKeyValueExt(criteria);
			String disabilitaStorico = amkvext.getValueConf();
			
			log.info("Inizio operazione di normalizzazione e reverse");
			boolean gestisciTmp = false;
			if(replace.equals("1" )) {
				gestisciTmp = true;
			}

			boolean disabilitaStoricoBool = false;
			if ("1".equals(disabilitaStorico))
				disabilitaStoricoBool = true;
			
			Util.postNormalizza(tabelleDWH, gestisciTmp, conn, null, tabs, disabilitaStoricoBool );

			// ho fattola reverse delle RET_ svuoto la mappa in modo da non rifarlo piu'
			ctx.riempiReverseObjects(null);

			retAck = new ApplicationAck("Reverse dati eseguito con successo");
			
		}catch (Exception e) {
			log.error("Errore grave importando i dati", e);
			retAck = new ErrorAck(e);
		}
		
		return retAck;
	}

}
