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

import it.webred.rulengine.dwh.Dao.SitEnelUtenteDao;

import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;

import it.webred.rulengine.dwh.table.SitEnelUtente;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

/**
 * La cfg della regola è su file properties
 * @author webred
 *
 */
public class LoadSitEnelUtente extends AbstractLoaderCommand implements Rule
{

	private static final Logger log = Logger.getLogger(LoadSitEnelUtente.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitEnelUtente.class;
	}

	
	public LoadSitEnelUtente(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}
	
	
	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
		throws CommandException
	{
		try {
			
			int paramCount = Integer.parseInt(super._jrulecfg.getProperty("rengine.rule.number.params.in"));
			
			String id_orig = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.1.descr"));		
			Integer fk_ente_sorgente = (Integer) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.2.descr"));
			String codiceFiscale = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.3.descr"));
			String denominazione = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.4.descr"));
			String sesso = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.5.descr"));
			Timestamp dataNascita = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.6.descr"));
			String comuneNascitaSede = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.7.descr"));
			String provinciaNascitaSede = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.8.descr"));
			String comuneDomFiscale = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.9.descr"));
			String provinciaDomFiscale = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.10.descr"));
			String qualificaTitolare = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.11.descr"));
						
			Timestamp dt_exp_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.12.descr"));
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.13.descr"));
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.14.descr"));
			Integer flag_dt_val_dato = (Integer) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.15.descr"));

			if(id_orig==null || fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_ENEL_UTENTE - Dati obbligatori non forniti");
			
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_ENEL_UTENTE - flag_dt_val_dato non valido");
			
			SitEnelUtente ute = (SitEnelUtente)getTabellaDwhInstance(ctx);
			ChiaveOriginale co =  new ChiaveOriginale();
			co.setValore(id_orig);
			
			ute.setIdOrig(co);
			ute.setCodiceFiscale(codiceFiscale);
			ute.setDenominazione(denominazione);
			ute.setSesso(sesso);
			ute.setDataNascita(DwhUtils.getDataDwh(new DtFineDato(),dataNascita));
			ute.setComuneNascitaSede(comuneNascitaSede);
			ute.setProvinciaNascitaSede(provinciaNascitaSede);
			ute.setComuneDomFiscale(comuneDomFiscale);
			ute.setProvinciaDomFiscale(provinciaDomFiscale);
			ute.setQualificaTitolare(qualificaTitolare);
			
			ute.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			ute.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			ute.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			ute.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			ute.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente), ctx.getBelfiore());

			SitEnelUtenteDao dao = (SitEnelUtenteDao) DaoFactory.createDao(conn,ute, ctx.getEnteSorgenteById(fk_ente_sorgente)); 	
			
			@SuppressWarnings("unused")
			boolean salvato = dao.save(ctx.getBelfiore());
			
		} catch (DaoException e)	{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)	{
			log.error("LoadSit",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		return(new ApplicationAck("Record Sit_Enel_Utente inserito"));

	}



}
