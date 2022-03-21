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
import it.webred.rulengine.dwh.Dao.SitTF24AnticipoDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.SitTF24Anticipo;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;


public class LoadSitTF24Anticipo extends AbstractLoaderCommand implements Rule
{
	
	
	public LoadSitTF24Anticipo(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}


	private static final Logger log = Logger.getLogger(LoadSitTF24Anticipo.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitTF24Anticipo.class;
	
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
			//String progFornitura = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);			
			Object param = this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal progFornitura = param instanceof BigDecimal ? (BigDecimal) param : (param == null ? null : new BigDecimal(param.toString()));
			Timestamp dtRipartizione = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String progRipartizione = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dtBonifico = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String codEnteCom = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    String codValuta = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    BigDecimal impAnticipo = (BigDecimal) this.getRRuleParameterIn(parametriIn, ctx, index++);
		    String tipoImposta = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_exp_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_ini_val_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_fine_val_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Integer flag_dt_val_dato = (Integer) this.getRRuleParameterIn(parametriIn, ctx, index++);
	
			String idOrigTestata = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			 
			//campi aggiunti
			String annoMeseRif = parametriIn.size() > 16 ? (String) this.getRRuleParameterIn(parametriIn, ctx, index++) : null;
			String campoUsoInterno = parametriIn.size() > 16 ? (String) this.getRRuleParameterIn(parametriIn, ctx, index++) : null;
			
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_T_F24_ANTICIPO - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_T_F24_ANTICIPO - flag_dt_val_dato non valido");
			
			SitTF24Anticipo tab = (SitTF24Anticipo)getTabellaDwhInstance(ctx);

			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);			
			tab.setIdOrig(chiaveOriginale);
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			tab.setDtFornitura(DwhUtils.getDataDwh(new DataDwh(),dtFornitura));
			tab.setProgFornitura( progFornitura );
			tab.setDtRipartizione(DwhUtils.getDataDwh(new DataDwh(),dtRipartizione));
			tab.setProgRipartizione(new BigDecimal(progRipartizione));
			// Il campo DT_BONIFICO nel database è stato modificato al fine di far accettare anche valori NULL
			tab.setDtBonifico( DwhUtils.getDataDwh(new DataDwh(), dtBonifico) );
			tab.setCodEnteCom(codEnteCom);
			tab.setCodValuta(codValuta);
			tab.setImpAnticipo(impAnticipo);
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
			
			tab.setAnnoMeseRif(annoMeseRif);
			tab.setCampoUsoInterno(campoUsoInterno);
			
			SitTF24AnticipoDao dao = (SitTF24AnticipoDao) DaoFactory.createDao(conn, tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());		
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitF24Anticipo",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	
		return(new ApplicationAck("Record SIT_T_F24_ANTICIPO inserito"));
	
	}

}
