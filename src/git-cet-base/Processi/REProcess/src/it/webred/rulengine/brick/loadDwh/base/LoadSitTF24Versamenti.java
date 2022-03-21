package it.webred.rulengine.brick.loadDwh.base;

import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.Dao.DaoException;
import it.webred.rulengine.dwh.Dao.DaoFactory;
import it.webred.rulengine.dwh.Dao.SitTF24VersamentiDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.SitTF24Versamenti;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;


public class LoadSitTF24Versamenti extends AbstractLoaderCommand implements Rule
{
	
	
	public LoadSitTF24Versamenti(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}


	private static final Logger log = Logger.getLogger(LoadSitTF24Versamenti.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitTF24Versamenti.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
	throws CommandException
	{
		
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			
			int index = 0;
			
			String id_orig = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Integer fk_ente_sorgente = (Integer) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dtFornitura = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			/*
			 * ho fatto questa modifica su codice già presente perchè da errore 
			 */
			//String progFornitura = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);			
			Object param = this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal progFornitura = param instanceof BigDecimal ? (BigDecimal) param : (param == null ? null : new BigDecimal(param.toString()));
			
			Timestamp dtRipartizione = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String progRipartizione = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dtBonifico = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			/*
			 * ho fatto questa modifica su codice già presente perchè da errore 
			 */
			//String progDelega = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			param = this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal progDelega = param instanceof BigDecimal ? (BigDecimal) param : (param == null ? null : new BigDecimal(param.toString()));
			/*
			 * ho fatto questa modifica su codice già presente perchè da errore 
			 */
			//String progRiga = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			param = this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal progRiga = param instanceof BigDecimal ? (BigDecimal) param : (param == null ? null : new BigDecimal(param.toString()));
			String codEnteRd = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String tipoEnteRd = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String cab = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String cf = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			/*
			 * ho fatto questa modifica su codice già presente perchè da errore 
			 */
			//String flagErrCf = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			param = this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal flagErrCf = param instanceof BigDecimal ? (BigDecimal) param : (param == null ? null : new BigDecimal(param.toString()));
			Timestamp dtRiscossione = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String codEnteCom = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String codTributo = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			/*
			 * ho fatto questa modifica su codice già presente perchè da errore 
			 */
			//String flagErrCt = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			param = this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal flagErrCt = param instanceof BigDecimal ? (BigDecimal) param : (param == null ? null : new BigDecimal(param.toString()));
			BigDecimal rateazione = (BigDecimal) this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal annoRif = (BigDecimal) this.getRRuleParameterIn(parametriIn, ctx, index++);
			//String flagErrAr = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			param = this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal flagErrAr = param instanceof BigDecimal ? (BigDecimal) param : (param == null ? null : new BigDecimal(param.toString()));
		    String codValuta = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    BigDecimal impDebito = (BigDecimal) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    BigDecimal impCredito = (BigDecimal) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    //String ravvedimento = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    param = this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal ravvedimento = param instanceof BigDecimal ? (BigDecimal) param : (param == null ? null : new BigDecimal(param.toString()));
			//String varImmIciImu = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			param = this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal varImmIciImu = param instanceof BigDecimal ? (BigDecimal) param : (param == null ? null : new BigDecimal(param.toString()));
			//String acconto = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			param = this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal acconto = param instanceof BigDecimal ? (BigDecimal) param : (param == null ? null : new BigDecimal(param.toString()));
		    //String saldo = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			param = this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal saldo = param instanceof BigDecimal ? (BigDecimal) param : (param == null ? null : new BigDecimal(param.toString()));
		    BigDecimal numImm = (BigDecimal) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    //String flagErrIciImu = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    param = this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal flagErrIciImu = param instanceof BigDecimal ? (BigDecimal) param : (param == null ? null : new BigDecimal(param.toString()));
		    BigDecimal detrazione = (BigDecimal) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    String denominazione = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    String nome = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    String sesso = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    String dtNascita = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    String comuNasc = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    String prov = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    String tipoImposta = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    String cf2 = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    String codIdCf2 = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);   
		    String idOperazione = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);   
			Timestamp dt_exp_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_ini_val_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_fine_val_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Integer flag_dt_val_dato = (Integer) this.getRRuleParameterIn(parametriIn, ctx, index++);
			
			String idOrigTestata = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			
			//campi aggiunti
			String annoMeseRif = parametriIn.size() > 46 ? (String) this.getRRuleParameterIn(parametriIn, ctx, index++) : null;
			
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_T_F24_VERSAMENTI - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_T_F24_VERSAMENTI - flag_dt_val_dato non valido");
			
			SitTF24Versamenti tab = (SitTF24Versamenti)getTabellaDwhInstance(ctx);
			
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);			
			tab.setIdOrig(chiaveOriginale);
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			tab.setDtFornitura(DwhUtils.getDataDwh(new DataDwh(),dtFornitura));
			tab.setProgFornitura( progFornitura );
			tab.setDtRipartizione(DwhUtils.getDataDwh(new DataDwh(),dtRipartizione));
			tab.setProgRipartizione( new BigDecimal( progRipartizione ) );
			tab.setDtBonifico(DwhUtils.getDataDwh(new DataDwh(),dtBonifico));
			tab.setProgDelega( progDelega!=null?progDelega: new BigDecimal("0") );
			tab.setProgRiga( progRiga!=null?progRiga: new BigDecimal("0") );
			tab.setCodEnteRd(codEnteRd);
			tab.setTipoEnteRd(tipoEnteRd);
			tab.setCab(cab);
			tab.setCf(cf);
			tab.setFlagErrCf( flagErrCf );
			tab.setDtRiscossione(DwhUtils.getDataDwh(new DataDwh(),dtRiscossione));
			tab.setCodEnteCom(codEnteCom);
			tab.setCodTributo(codTributo);
			tab.setFlagErrCt( flagErrCt );
			tab.setRateazione(rateazione);
			tab.setAnnoRif(annoRif);
			tab.setFlagErrAr(flagErrAr );
			tab.setCodValuta(codValuta);
			tab.setImpDebito(impDebito);
			tab.setImpCredito(impCredito);
			tab.setRavvedimento( ravvedimento );
			tab.setVarImmIciImu( varImmIciImu );
			tab.setAcconto( acconto );
			tab.setSaldo( saldo );
			tab.setNumFabbIciImu(numImm);
			tab.setFlagErrIciImu( flagErrIciImu );
			tab.setDetrazione(detrazione);
			tab.setDenominazione(denominazione!=null && denominazione.length()>0 ? denominazione : null);
			tab.setNome(nome!=null && nome.length()>0 ? nome : null);
			tab.setSesso(sesso!=null && sesso.length()>0 ? sesso : null);	
			tab.setDtNascita(dtNascita);
			tab.setComuneStato(comuNasc!=null && comuNasc.length()>0 ? comuNasc : null);
			tab.setProvincia(prov!=null && prov.length()>0 ? prov : null);
			tab.setTipoImposta(tipoImposta);
			tab.setCf2(cf2!=null && cf2.length()>0 ? cf2 : null);
			tab.setCodIdCf2(codIdCf2!=null && codIdCf2.length()>0 ? codIdCf2 : null);
			tab.setIdOperazione(idOperazione!=null && idOperazione.length()>0 ? idOperazione : null);
			
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			
			if (idOrigTestata!=null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(idOrigTestata);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtTestata(ce);
			}
			
			tab.setAnnoMeseRif( annoMeseRif!=null && annoMeseRif.length()>0 ? annoMeseRif : null );
			
			BeanEnteSorgente bes = ctx.getEnteSorgenteById(fk_ente_sorgente);
			
			SitTF24VersamentiDao dao = (SitTF24VersamentiDao) DaoFactory.createDao(conn, tab, bes); 
			
			boolean salvato = dao.save(ctx.getBelfiore());		
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitF24Versamenti",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	
		return(new ApplicationAck("Record SIT_T_F24_VERSAMENTI inserito"));
	
	}

}
