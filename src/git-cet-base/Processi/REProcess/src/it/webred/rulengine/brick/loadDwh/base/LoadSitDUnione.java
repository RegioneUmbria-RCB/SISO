package it.webred.rulengine.brick.loadDwh.base;

import it.webred.rulengine.Context;
import it.webred.rulengine.DbCommand;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.Dao.DaoException;
import it.webred.rulengine.dwh.Dao.DaoFactory;
import it.webred.rulengine.dwh.Dao.SitDUnioneDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.SitComune;
import it.webred.rulengine.dwh.table.SitDUnione;
import it.webred.rulengine.dwh.table.SitDVia;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
/**
 * Inserisce un ricord nela tabella unioni, se l'id_orig di una delle persone è null allora il record non viene inserito 
 *
 * @author marcoric
 *
 */
public class LoadSitDUnione extends AbstractLoaderCommand implements Rule
{
	public LoadSitDUnione(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadSitDUnione.class.getName());

	//blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitDUnione.class;
	}
	
	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
		throws CommandException
	{
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
			String generUnione = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
			Timestamp dataUnione = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
			
			
			
			String id_orig_d_comune = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
			String id_orig_d_provincia = (String) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
			String id_orig_d_persona1 = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			String id_orig_d_persona2 = (String) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			
			//POTREI ANCHE ESSERE UN DIVORZIO
			/*if(id_orig_d_persona2==null || id_orig_d_persona1==null)
				return(new ApplicationAck("Non si procede all'inserimento - valore nullo su coniuge"));
				*/
			
			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
			String flag_coniuge = (String) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
				
			// n.b. l'id_orig può essere vuoto in quel caso ci andrà il ctrhash
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_D_UNIONE - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_D_UNIONE - flag_dt_val_dato non valido");

			SitDUnione tab = (SitDUnione)getTabellaDwhInstance(ctx);

			
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);
			
			tab.setIdOrig(chiaveOriginale);
			tab.setGenerUnione(generUnione);
			tab.setDataUnione(DwhUtils.getDataDwh(new DataDwh(),dataUnione));

				
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			
			
			if (id_orig_d_comune!=null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_d_comune);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtComune(ce);
			}
			if (id_orig_d_provincia!=null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_d_provincia);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtProvincia(ce);
			}
			if (id_orig_d_persona1!=null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_d_persona1);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtDPersona1(ce);
			}			
			
			if (id_orig_d_persona2!=null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_d_persona2);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtDPersona2(ce);
			}			
			
			SitDUnioneDao dao = (SitDUnioneDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			if((id_orig_d_persona2==null || id_orig_d_persona1==null) && "0".equals(flag_coniuge)){
				dao.faiScadereRecords();
				return(new ApplicationAck("Record SIT_D_UNIONE aggiornato con scadenza"));
			}
			
			boolean salvato = dao.save(ctx.getBelfiore());
			
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitDUnione",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		return(new ApplicationAck("Record SIT_D_UNIONE inserito"));

	}



}
