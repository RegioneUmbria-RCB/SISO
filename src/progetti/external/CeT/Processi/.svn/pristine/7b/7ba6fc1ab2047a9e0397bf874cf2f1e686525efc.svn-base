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
import it.webred.rulengine.dwh.Dao.SitRuoloTarsuDao;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.SitRuoloTarsu;
import it.webred.rulengine.dwh.table.SitRuoloTarsuImm;
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

public class LoadSitRuoloTarsu extends AbstractLoaderCommand implements Rule
{
	
	
	public LoadSitRuoloTarsu(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}


	private static final Logger log = Logger.getLogger(LoadSitRuoloTarsu.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitRuoloTarsu.class;
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
			
			Integer cu = (Integer)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String tipo = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String anno = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			
			String nominativo = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String indirizzo = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String cap = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String comune = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String prov = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String cf = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String estero = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String iban = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String dom = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			
			BigDecimal totNetto = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal percEca = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal addEca = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal percMaggEca = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal maggEca = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal percTribProv = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal tribProv = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal sanzione = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal interessi = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal totale = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String dtNot = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String numProvv = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String dtAcc = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal impNotifica = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String supAnno = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String supAnnoSucc = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String sgrAnnoSucc = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal accTarsuAnno = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String tipoDoc = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			
			Timestamp dt_exp_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_ini_val_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_fine_val_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Integer flag_dt_val_dato = (Integer) this.getRRuleParameterIn(parametriIn, ctx, index++);
	
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_RUOLO_TARSU - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_RUOLO_TARSU - flag_dt_val_dato non valido");
			
			SitRuoloTarsu tab = (SitRuoloTarsu)getTabellaDwhInstance(ctx);
			
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);			
			tab.setIdOrig(chiaveOriginale);
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			
			tab.setTipo(tipo);
			tab.setAnno(anno);
			tab.setCu(cu);
			tab.setNominativoContrib(nominativo);
			tab.setIndirizzo(indirizzo);
			tab.setCap(cap);
			tab.setComune(comune);
			tab.setProv(prov);
			tab.setCodfisc(cf);
			tab.setEstero(estero);
			tab.setIban(iban);
			tab.setDom(dom);
			tab.setTotNetto(totNetto);
			tab.setPercEca(percEca);
			tab.setAddEca(addEca);
			tab.setPercMaggEca(percMaggEca);
			tab.setMaggEca(maggEca);
			tab.setPercTribProv(percTribProv);
			tab.setTribProv(tribProv);
			tab.setSanzione(sanzione);
			tab.setInteressi(interessi);
			tab.setTotale(totale);
			if(dtNot != null){
				try{
					tab.setDataNotifica(DwhUtils.getDataDwh(new DataDwh(),new Timestamp(sdf.parse(dtNot).getTime())));
				} catch (ParseException e) {}
			}
			tab.setNumProvv(numProvv);
			if(dtAcc != null){
				try{
					tab.setDataAcc(DwhUtils.getDataDwh(new DataDwh(),new Timestamp(sdf.parse(dtAcc).getTime())));
				} catch (ParseException e) {}
			}
			tab.setImportoNotifica(impNotifica);
			tab.setSupAnno(supAnno);
			tab.setSupAnnoSucc(supAnnoSucc);
			tab.setSgrAnnoSucc(sgrAnnoSucc);
			tab.setAccontoTarsuAnno(accTarsuAnno);
			tab.setTipoDoc(tipoDoc);
			
			//tab.setProvenienza(provenienza);		
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			
			SitRuoloTarsuDao dao = (SitRuoloTarsuDao) DaoFactory.createDao(conn, tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());		
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitRuoloTarsu",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	
		return(new ApplicationAck("Record SIT_RUOLO_TARSU inserito"));
	
	}

}
