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
import it.webred.rulengine.dwh.Dao.SitRuoloTaresImmDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.SitRuoloTaresImm;
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

public class LoadSitRuoloTaresImm extends AbstractLoaderCommand implements Rule
{
	
	
	public LoadSitRuoloTaresImm(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}


	private static final Logger log = Logger.getLogger(LoadSitRuoloTaresImm.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitRuoloTaresImm.class;
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
			Integer prog = (Integer)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String indirizzo = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String desTipo = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String codTipo = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String desCat = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String codCat = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String rifcat = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal mq = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal tariffaQf = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal tariffaQv = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal tariffaQs = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal riduzioneQf = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal riduzioneQv = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal riduzioneQs = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String causale = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal importo = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal importoQf = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal importoQv = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal importoQs = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String codTributo = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String da = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String a = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			
			Timestamp dt_exp_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_ini_val_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_fine_val_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Integer flag_dt_val_dato = (Integer) this.getRRuleParameterIn(parametriIn, ctx, index++);
	
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_RUOLO_TARES_IMM - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_RUOLO_TARES_IMM - flag_dt_val_dato non valido");
			
			SitRuoloTaresImm tab = (SitRuoloTaresImm)getTabellaDwhInstance(ctx);
			
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);			
			tab.setIdOrig(chiaveOriginale);
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			
			tab.setAnno(anno);
			tab.setProg(prog);
			if(indirizzo !=null && indirizzo.startsWith(anno+" "))
				indirizzo = indirizzo.substring(6).trim();

			tab.setIndirizzo(indirizzo);
			tab.setCodTipo(codTipo);
			tab.setDesTipo(desTipo);
			tab.setCat(codCat);
			tab.setDesCat(desCat);
			
			if(rifcat!=null){
				rifcat=rifcat.trim();
				//replace delle sequenze di spazi
				while(rifcat.indexOf("  ")>0)
					rifcat=rifcat.replaceAll("  ", " ");
				
				String[] pc = rifcat.split(" ");
				int i=0;
				while(i<pc.length){
					
					String label = pc[i];
					String value = pc[i+1];
					
					if(label.startsWith("Sez"))
						tab.setSez(value);
					else if(label.startsWith("Fg"))
						tab.setFoglio(value);
					else if(label.startsWith("Num"))
						tab.setParticella(value);
					else if(label.startsWith("Sub"))
						tab.setSub(value);
					
					i = i+2;
				}
			}
			
			tab.setMq(mq);
			tab.setTariffaQf(tariffaQf);
			tab.setTariffaQs(tariffaQs);
			tab.setTariffaQv(tariffaQv);
			tab.setRiduzioneQf(riduzioneQf);
			tab.setRiduzioneQv(riduzioneQv);
			tab.setRiduzioneQs(riduzioneQs);
			tab.setCausale(causale);
			tab.setImporto(importo);
			tab.setImportoQf(importoQf);
			tab.setImportoQv(importoQv);
			tab.setImportoQs(importoQs);
			tab.setCodTributo(codTributo);
			
			if(da != null && a!=null){
				try{
					tab.setPeriodoDa(DwhUtils.getDataDwh(new DataDwh(),new Timestamp(sdf.parse(da).getTime())));
					tab.setPeriodoA(DwhUtils.getDataDwh(new DataDwh(),new Timestamp(sdf.parse(a).getTime())));
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
			
			SitRuoloTaresImmDao dao = (SitRuoloTaresImmDao) DaoFactory.createDao(conn, tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
		
			boolean salvato = dao.save(ctx.getBelfiore());		
		
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitRuoloTaresImm",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	
		return(new ApplicationAck("Record SIT_RUOLO_TARES_IMM inserito"));
	
	}

}
