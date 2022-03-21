package it.webred.rulengine.brick.loadDwh.base;

import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.Dao.DaoException;
import it.webred.rulengine.dwh.Dao.DaoFactory;
import it.webred.rulengine.dwh.Dao.SitTIciRiduzioniDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.table.SitTIciRiduzioni;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadSitTIciRiduzioni extends AbstractLoaderCommand implements Rule
{
	public LoadSitTIciRiduzioni(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadSitTIciRiduzioni.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitTIciRiduzioni.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
	throws CommandException
	{
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			
			String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
			String id_orig_ogg_ici = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
			BigDecimal val_rid = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
			String tipo_riduzione = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());			
			String descr_riduzione = (String) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());			
			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
			String provenienza = (String) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());
	
			//n.b. in questo caso id_orig è null, ed è gestito in seguito
			if(/*id_orig==null || */fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_T_ICI_RIDUZIONI - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_T_ICI_RIDUZIONI - flag_dt_val_dato non valido");
			
			SitTIciRiduzioni tab = new SitTIciRiduzioni();
			tab.setProcessid(new ProcessId(ctx.getProcessID()));
			
			//n.b. in questo caso id_orig è null, ed è gestito in seguito
			/*ChiaveOriginale chiaveOriginale = new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);			
			tab.setIdOrig(chiaveOriginale);*/
			tab.setIdOrig(null);
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			if (id_orig_ogg_ici != null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_ogg_ici);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(provenienza, co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtOggIci(ce);
			}
			tab.setValRid(val_rid);
			tab.setTipoRiduzione(tipo_riduzione);
			tab.setDescrRiduzione(descr_riduzione);
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			tab.setProvenienza(provenienza);
			
			SitTIciRiduzioniDao dao = (SitTIciRiduzioniDao) DaoFactory.createDao(conn, tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitTIciRiduzioni",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	
		return(new ApplicationAck("Record SIT_T_ICI_RIDUZIONI inserito"));
	
	}

}
