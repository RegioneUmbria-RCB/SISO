package it.webred.rulengine.brick.loadDwh.base;


import it.webred.rulengine.Rule;
import it.webred.rulengine.dwh.table.SitEnelUtenza;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadSitEnelUtenza extends AbstractLoaderCommand implements Rule
{

	
	public LoadSitEnelUtenza(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

	private static List parametriIn = null;

	private static final Logger log = Logger.getLogger(LoadSitEnelUtenza.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitEnelUtenza.class;
	}


/**
	@Override
	public CommandAck runWithConnection(Context ctx, Connection conn)
		throws CommandException
	{
		try {
		
			if (parametriIn==null)
				parametriIn = this.getParametersIn();
			String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
			String codiceFiscale = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
			String denominazione = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
			String sesso = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
			Timestamp dataNascita = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
			String provinciaNascitaSede = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			String comuneDomFiscale = (String) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			String provinciaDomFiscale = (String) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
			String qualificaTitolare = (String) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
			
			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(13)).getDescr());


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
			ute.setProvinciaNascitaSede(provinciaNascitaSede);
			ute.setComuneDomFiscale(comuneDomFiscale);
			ute.setProvinciaDomFiscale(provinciaDomFiscale);
			ute.setQualificaTitolare(qualificaTitolare);
			
			ute.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			ute.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			ute.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			ute.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			ute.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente));
			
			

			
			
			SitEnelUtenteDao dao = (SitEnelUtenteDao) DaoFactory.createDao(conn,ute, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			
			boolean salvato = dao.save();
			
			
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSit",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		return(new ApplicationAck("Record Sit_Enel_Utente inserito"));

	}

*/

}
