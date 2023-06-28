package it.webred.rulengine.brick.elab.cartellaSociale;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class GeolocalizzazioneIndirizzi extends Command implements Rule {

	private static final Logger log = Logger.getLogger(GeolocalizzazioneIndirizzi.class.getName());
	private Connection conn = null;
	private String enteID;
//	private SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

	public GeolocalizzazioneIndirizzi(BeanCommand bc) {
		super(bc);
	}

	public GeolocalizzazioneIndirizzi(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		log.info("Cartella Sociale - Geolocalizzazione Indirizzi run()");
		CommandAck retAck = null;
		enteID = ctx.getBelfiore();
		log.info("ENTE IN ELABORAZIONE (DA CTX): " + enteID);
		conn = ctx.getConnection((String) ctx.get("connessione"));

		try {

			log.info(" * Database Username: " + conn.getMetaData().getUserName());
			// 1. identificazione degli indirizzi da geolocalizzare
			log.info(" * 1.identificazione degli indirizzi da geolocalizzare ");

			ArgoDbClient argoDbClient = new ArgoDbClient(conn);
			List<IndirizziZoneDto> addressBatch = argoDbClient.findUnmappedAddresses();
			log.info(" * 1.1 chiamo findUnmappedAddresses() --> addressBatch.size = " + addressBatch.size());

			GeolocalizationServiceFactory gFactory = GeolocalizationServiceFactory.getInstance(ctx);

			//2. geolocalizzazione
			log.info(" * 2. geolocalizzazione ");
			GeolocalizationService geolocalizationService = gFactory.createGeolocalizationService();
			geolocalizationService.geolocalizzaIndirizzi(addressBatch);
			//3. aggiornamento
			log.info(" * 3. aggiornamento db ");
			argoDbClient.updateMappedAddresses(addressBatch);
			
			log.info(" * 3.1 aggiornamento db updateZonaCensuaria ");
			argoDbClient.updateZonaCensuaria();
			// fine
			log.info(" * 4. Fine ");
			retAck = new ApplicationAck("ESECUZIONE OK");
			return retAck;

		} catch (DataAccessException dae) {
			log.error("ERRORE nell'accesso ai dati " + dae, dae);
			ErrorAck ea = new ErrorAck(dae);
			return ea;
		} catch (Exception eg) {
			log.error("ERRORE Generico " + eg, eg);
			ErrorAck ea = new ErrorAck(eg);
			return ea;
		}
	}

}