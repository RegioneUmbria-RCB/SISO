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
import it.webred.rulengine.dwh.Dao.SitTF24AnnullamentoDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.SitTF24Annullamento;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;


public class LoadSitTF24Annullamento extends AbstractLoaderCommand implements Rule
{
	
	
	public LoadSitTF24Annullamento(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}


	private static final Logger log = Logger.getLogger(LoadSitTF24Annullamento.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitTF24Annullamento.class;
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
			String progFornitura = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dtRipartizione = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String progRipartizione = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dtBonifico = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String codEnteRd = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String cf = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dtRiscossione = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String codEnteCom = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String codTributo = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal annoRif = (BigDecimal) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String codValuta = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    BigDecimal impCredito = (BigDecimal) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    BigDecimal impDebito = (BigDecimal) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    String tipoOperazione = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    Timestamp dtOperazione = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    String tipoImposta = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    
			Timestamp dt_exp_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_ini_val_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_fine_val_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Integer flag_dt_val_dato = (Integer) this.getRRuleParameterIn(parametriIn, ctx, index++);
	
			 String idOrigTestata = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_T_F24_ANNULLAMENTO - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_T_F24_ANNULLAMENTO - flag_dt_val_dato non valido");
			
			SitTF24Annullamento tab = (SitTF24Annullamento)getTabellaDwhInstance(ctx);
			
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);			
			tab.setIdOrig(chiaveOriginale);
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			tab.setDtFornitura((DataDwh)DwhUtils.getDataDwh(new DataDwh(),dtFornitura));
			tab.setProgFornitura(new BigDecimal(progFornitura));
			tab.setDtRipartOrig(DwhUtils.getDataDwh(new DataDwh(),dtRipartizione));
			tab.setProgRipartOrig(new BigDecimal(progRipartizione));
			tab.setDtBonificoOrig(DwhUtils.getDataDwh(new DataDwh(),dtBonifico));
			tab.setCodEnteRd(codEnteRd);
			tab.setCf(cf);
			tab.setDtRiscossione(DwhUtils.getDataDwh(new DataDwh(),dtRiscossione));
			tab.setCodEnteCom(codEnteCom);
			tab.setCodTributo(codTributo);
			tab.setAnnoRif(annoRif);
			tab.setCodValuta(codValuta);
			tab.setImpDebito(impDebito);
			tab.setImpCredito(impCredito);
			tab.setTipoOperazione(tipoOperazione);
			tab.setDtOperazione(DwhUtils.getDataDwh(new DataDwh(),dtOperazione));
			tab.setTipoImposta(tipoImposta);
					
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
			
			SitTF24AnnullamentoDao dao = (SitTF24AnnullamentoDao) DaoFactory.createDao(conn, tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());		
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitF24Annullamento",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	
		return(new ApplicationAck("Record SIT_T_F24_ANNULLAMENTO inserito"));
	
	}

}
