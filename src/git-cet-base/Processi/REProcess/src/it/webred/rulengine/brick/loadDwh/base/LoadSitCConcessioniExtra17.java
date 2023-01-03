package it.webred.rulengine.brick.loadDwh.base;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

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
import it.webred.rulengine.dwh.Dao.SitCConcessioniExtra17Dao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.SitCConcessioniExtra17;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import oracle.sql.CLOB;

public class LoadSitCConcessioniExtra17 extends AbstractLoaderCommand implements Rule
{

	private static final Logger log = Logger.getLogger(LoadSitCConcessioniExtra17.class.getName());

	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitCConcessioniExtra17.class;
	}

	public LoadSitCConcessioniExtra17(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
	throws CommandException
	{
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());	
			String id_orig_c_concessioni = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());			
			CLOB altriNumeroPg = (CLOB) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
			CLOB altriAnnoPg = (CLOB) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
			CLOB altriSubPg = (CLOB) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
			CLOB altriDataPg = (CLOB) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			CLOB altriOggettoPg = (CLOB) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			CLOB npgAttoPratRif = (CLOB) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
			CLOB annoPgAttoPratRif = (CLOB) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
			CLOB praticheCollegate = (CLOB) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());			
			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(13)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(14)).getDescr());
			String provenienza = (String) ctx.get(((RRuleParamIn) parametriIn.get(15)).getDescr());	
	
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_C_CONCESSIONI_EXTRA17 - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_C_CONCESSIONI_EXTRA17 - flag_dt_val_dato non valido");
			
			
			SitCConcessioniExtra17 tab = (SitCConcessioniExtra17)getTabellaDwhInstance(ctx);
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);
			
			tab.setIdOrig(chiaveOriginale);
	
			tab.setAltriNumeroPg(altriNumeroPg);
			tab.setAltriAnnoPg(altriAnnoPg);
			tab.setAltriSubPg(altriSubPg);
			tab.setAltriDataPg(altriDataPg);
			tab.setAltriOggettoPg(altriOggettoPg);
			tab.setNpgAttoPratRif(npgAttoPratRif);
			tab.setAnnoPgAttoPratRif(annoPgAttoPratRif);
			tab.setPraticheCollegate(praticheCollegate);
			
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente), ctx.getBelfiore());
			tab.setProvenienza(provenienza);
			
			if (id_orig_c_concessioni!=null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_c_concessioni);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(provenienza, co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtCConcessioni(ce);
			}
	
			SitCConcessioniExtra17Dao dao = (SitCConcessioniExtra17Dao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente));			
			
			boolean salvato = dao.save(ctx.getBelfiore());
			
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitCConcessioniExtra17",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	
		return(new ApplicationAck("Record SIT_C_CONCESSIONI_EXTRA17 inserito"));
	
	}

}

