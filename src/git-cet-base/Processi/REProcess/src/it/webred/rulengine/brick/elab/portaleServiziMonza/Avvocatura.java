package it.webred.rulengine.brick.elab.portaleServiziMonza;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import it.webred.amprofiler.ejb.user.UserService;
import it.webred.ct.config.model.AmInstance;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.application.ApplicationService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.ct.data.access.basic.ici.IciService;
import it.webred.ct.data.access.basic.indice.ricerca.IndiceCorrelazioneService;
import it.webred.ct.data.access.basic.indice.ricerca.RicercaIndiceDTO;
import it.webred.ct.data.access.basic.tarsu.TarsuService;
import it.webred.ct.data.model.ici.SitTIciOggetto;
import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.tarsu.SitTTarOggetto;
import it.webred.portaleservizi.ejb.scheduler.SchedulerDataBean;
import it.webred.portaleservizi.ejb.scheduler.alerting.AlertingSessionFacade;
import it.webred.portaleservizi.ejb.scheduler.scambiobuste.ScambioBusteSessionFacade;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.diagnostics.bean.CivicoBean;
import it.webred.rulengine.diagnostics.bean.DatiTitolariUI;
import it.webred.rulengine.diagnostics.utils.ChkDatiUI;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class Avvocatura extends Command implements Rule {
	private static final Logger log = Logger.getLogger(Avvocatura.class.getName());

	private UserService userService;
	private String enteID;

	public Avvocatura(BeanCommand bc) {
		super(bc);
	}

	public Avvocatura(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		log.debug("Processo di Verifica Avvocatura run()");
		CommandAck retAck = null;
		enteID = ctx.getBelfiore();
		log.debug("ENTE IN ELABORAZIONE (DA CTX): " + enteID);
		try {
			
			userService = (UserService) ServiceLocator.getInstance()
					.getService("AmProfiler", "AmProfilerEjb", "UserServiceBean");

			
			
		/*	boolean value = userService.verificaAggiornaAvvocato("CRNMRN73S53D969O", enteID);
			 		value = userService.verificaAggiornaAvvocato("GZZGPP75C18G337L", enteID);
			 		value = userService.verificaAggiornaAvvocato("LSSSTG39L21A014B", enteID);
			 		value = userService.verificaAggiornaAvvocato("ZNTSRA81H54F704C", enteID);*/
			 		
			int rimossi = userService.verificaGruppoAvvocati(enteID);
			
			log.debug("Processo di Verifica completato. "+rimossi+" rimozioni effettuate dal Gruppo AVVOCATI_"+enteID.toUpperCase());

		} catch (Exception e) {
			log.error("ERRORE " + e, e);
			return new ErrorAck(e.getMessage());
		}
		retAck = new ApplicationAck("ESECUZIONE OK");
		return retAck;
	}
}
