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
import it.webred.rulengine.dwh.Dao.SitCConcIndirizziDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.TipoXml;
import it.webred.rulengine.dwh.table.SitCConcIndirizzi;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadSitCConcIndirizzi extends AbstractLoaderCommand implements Rule
{
	public LoadSitCConcIndirizzi(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadSitCConcIndirizzi.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitCConcIndirizzi.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
		throws CommandException
	{
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
			ComplexParam civico_composto = (ComplexParam) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
			String civLiv1 = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
			String codiceVia = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
			String sedime = (String) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
			String indirizzo = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			
			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());
			String provenienza = (String) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
			String id_orig_c_concessioni = (String) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());


			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_C_CONC_INDIRIZZI - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_C_CONC_INDIRIZZI - flag_dt_val_dato non valido");
			SitCConcIndirizzi tab = (SitCConcIndirizzi)getTabellaDwhInstance(ctx);
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);
			
			tab.setIdOrig(chiaveOriginale);

			if (civico_composto!=null) {
				TipoXml xml = DwhUtils.composeCivicoComposto(civico_composto);
				tab.setCivicoComposto(xml);
			}	
				
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			tab.setProvenienza(provenienza);
			
			tab.setCodiceVia(codiceVia);
			tab.setIndirizzo(indirizzo);
			tab.setSedime(sedime);
			tab.setCivLiv1(civLiv1);
			if (id_orig_c_concessioni!=null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_c_concessioni);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(provenienza,co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtCConcessioni(ce);
			}
			
			
			SitCConcIndirizziDao dao = (SitCConcIndirizziDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			
			boolean salvato = dao.save(ctx.getBelfiore());
			
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitCConcessioni",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		return(new ApplicationAck("Record SIT_C_CONC_INDIRIZZI inserito"));

	}



}
