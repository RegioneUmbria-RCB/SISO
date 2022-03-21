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
import it.webred.rulengine.dwh.Dao.SitTF24IdAccreditoDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.SitTF24IdAccredito;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;


public class LoadSitTF24IdAccredito extends AbstractLoaderCommand implements Rule
{
	
	
	public LoadSitTF24IdAccredito(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}


	private static final Logger log = Logger.getLogger(LoadSitTF24IdAccredito.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitTF24IdAccredito.class;
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
			String codEnteCom = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String codValuta = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal impAccredito = (BigDecimal) this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal cro = (BigDecimal) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dtAccredito = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dtRipartOrig = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String progRipartOrig = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dtBonificoOrig = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String tipoImposta = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String iban = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String sezContoTu = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal numContoTu = (BigDecimal) this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal codMovimento = (BigDecimal) this.getRRuleParameterIn(parametriIn, ctx, index++);
			
			Timestamp dt_exp_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_ini_val_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_fine_val_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Integer flag_dt_val_dato = (Integer) this.getRRuleParameterIn(parametriIn, ctx, index++);
	
			 String idOrigTestata = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_T_F24_ID_ACCREDITO - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_T_F24_ID_ACCREDITO - flag_dt_val_dato non valido");
			
			SitTF24IdAccredito tab = (SitTF24IdAccredito)getTabellaDwhInstance(ctx);
			
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);			
			tab.setIdOrig(chiaveOriginale);
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			tab.setDtFornitura((DataDwh)DwhUtils.getDataDwh(new DataDwh(),dtFornitura));
			tab.setProgFornitura(new BigDecimal(progFornitura));
			tab.setCodEnteCom(codEnteCom);
			tab.setCodValuta(codValuta);
			tab.setImpAccredito(impAccredito);
			tab.setCro(cro);
			tab.setDtAccredito((DataDwh)DwhUtils.getDataDwh(new DataDwh(),dtAccredito));
			tab.setDtRipartOrig((DataDwh)DwhUtils.getDataDwh(new DataDwh(),dtRipartOrig));
			tab.setProgRipartOrig(new BigDecimal(progRipartOrig));
			tab.setDtBonificoOrig((DataDwh)DwhUtils.getDataDwh(new DataDwh(),dtBonificoOrig));
			tab.setTipoImposta(tipoImposta);
			tab.setIban(iban);
			tab.setSezContoTu(sezContoTu);
			tab.setNumContoTu(numContoTu);
			tab.setCodMovimento(codMovimento);
			
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
			
			SitTF24IdAccreditoDao dao = (SitTF24IdAccreditoDao) DaoFactory.createDao(conn, tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());		
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitF24IdAccredito",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	
		return(new ApplicationAck("Record SIT_T_F24_ID_ACCREDITO inserito"));
	
	}

}
