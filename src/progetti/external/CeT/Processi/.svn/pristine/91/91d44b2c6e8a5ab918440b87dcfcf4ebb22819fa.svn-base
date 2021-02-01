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
import it.webred.rulengine.dwh.Dao.SitRuoloTarsuStSgDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.SitRuoloTarsuStSg;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadSitRuoloTarsuStSg extends AbstractLoaderCommand implements Rule
{
	
	
	public LoadSitRuoloTarsuStSg(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}


	private static final Logger log = Logger.getLogger(LoadSitRuoloTarsuStSg.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitRuoloTarsuStSg.class;
	}

	
	
	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
	throws CommandException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			int index = 0;
			String id_orig = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Integer fk_ente_sorgente = (Integer) this.getRRuleParameterIn(parametriIn, ctx, index++);
			
			String anno = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			//String tipo = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			//Integer cu = (Integer)this.getRRuleParameterIn(parametriIn, ctx, index++);
			Integer prog = (Integer)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String indirizzo = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal importo = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String codTributo = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String numFattura = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String dtFattura = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String numNota = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String dtNota = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			
			Timestamp dt_exp_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_ini_val_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_fine_val_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Integer flag_dt_val_dato = (Integer) this.getRRuleParameterIn(parametriIn, ctx, index++);
	
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_RUOLO_TARSU_IMM - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_RUOLO_TARSU_IMM - flag_dt_val_dato non valido");
			
			SitRuoloTarsuStSg tab = (SitRuoloTarsuStSg)getTabellaDwhInstance(ctx);
			
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);			
			tab.setIdOrig(chiaveOriginale);
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			
			tab.setAnno(anno);
			tab.setProg(prog);
			tab.setImporto(importo);
			tab.setIndirizzo(indirizzo);
		    tab.setNumFattura(numFattura);
			if(dtFattura != null){
				try{
					  tab.setDataFattura(DwhUtils.getDataDwh(new DataDwh(),new Timestamp(sdf.parse(dtFattura).getTime())));
				} catch (ParseException e) {}
			}
			tab.setImporto(importo);
			tab.setCodTributo(codTributo);
			tab.setNumNota(numNota);
			if(dtNota != null){
				try{
					  tab.setDataNota(DwhUtils.getDataDwh(new DataDwh(),new Timestamp(sdf.parse(dtNota).getTime())));
				} catch (ParseException e) {}
			}
			
			String idOrigRuolo = id_orig.substring(0,id_orig.lastIndexOf("|"));
			
			ChiaveOriginale co = new ChiaveOriginale();
			co.setValore(idOrigRuolo);
			ChiaveEsterna ce = new ChiaveEsterna();
			ce.setValore(co, DwhUtils.getIdentificativo(fk_ente_sorgente));
			tab.setIdExtRuolo(ce);
			
			//tab.setProvenienza(provenienza);		
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			
			SitRuoloTarsuStSgDao dao = (SitRuoloTarsuStSgDao) DaoFactory.createDao(conn, tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());		
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitRuoloTarsuStSg",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	
		return(new ApplicationAck("Record SIT_RUOLO_TARSU_ST_SG inserito"));
	
	}

}
