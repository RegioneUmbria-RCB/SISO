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
import it.webred.rulengine.dwh.Dao.SitRuoloTaresDao;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.SitRuoloTares;
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

public class LoadSitRuoloTares  extends AbstractLoaderCommand implements Rule
{
	
	
	public LoadSitRuoloTares(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}


	private static final Logger log = Logger.getLogger(LoadSitRuoloTares.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitRuoloTares.class;
	}

	
	
	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
	throws CommandException
	{	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyy");
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			int index = 0;
			String id_orig = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Integer fk_ente_sorgente = (Integer) this.getRRuleParameterIn(parametriIn, ctx, index++);
			
			BigDecimal cu = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String tipo = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String anno = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			
			String cognome = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String nome = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String cf = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String sesso = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String dataNascita = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String comNascita = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String provNascita = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String indirizzo = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String cap = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String comune = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String prov = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String estero = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			Integer nimm = (Integer)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal sanzione = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal interessi = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String dtNot = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String numProvv = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String dtAcc = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String flgAdesione = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String numFatt = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String dataFatt = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal impNotifica = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal totNetto = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal percIva = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal addIva = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal percTribProv = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal tribProv = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal totale = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal totEnte = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal totStato = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal tribNettoComune = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal impNettoAcconto = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal differenzaNetto = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal tribProvinciale = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal differenzaLordo = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal attPro = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal attComune = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal oldPro = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal oldTot = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String trQuotaTia = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String supAnnoPrec = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String supAnno = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String sgrAnno = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String tipoDoc = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String nomeCompleto = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			
			Timestamp dt_exp_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_ini_val_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_fine_val_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Integer flag_dt_val_dato = (Integer) this.getRRuleParameterIn(parametriIn, ctx, index++);
	
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_RUOLO_TARES - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_RUOLO_TARES - flag_dt_val_dato non valido");
			
			SitRuoloTares tab = (SitRuoloTares)getTabellaDwhInstance(ctx);
			
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);			
			tab.setIdOrig(chiaveOriginale);
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			
			tab.setTipo(tipo);
			tab.setAnno(anno);
			tab.setCu(cu);
			
			DataDwh dtnasc=null;
			if(dataNascita != null){
				try{
					dtnasc = DwhUtils.getDataDwh(new DataDwh(),new Timestamp(sdf1.parse(dataNascita).getTime()));
				} catch (ParseException e) {}
			}
			tab.setDatiContribuente(nomeCompleto, cognome, nome, cf, sesso, comNascita, dtnasc, provNascita, indirizzo, cap, comune, prov, estero);
			tab.setNimm(nimm);
			tab.setSanzione(sanzione);
			tab.setInteressi(interessi);
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
			tab.setFlagAdesione(flgAdesione);
			tab.setNumFatt(numFatt);
			if(dataFatt != null){
				try{
					tab.setDataFatt(DwhUtils.getDataDwh(new DataDwh(),new Timestamp(sdf.parse(dataFatt).getTime())));
				} catch (ParseException e) {}
			}
			tab.setImportoNotifica(impNotifica);
			tab.setTotNetto(totNetto);
			tab.setPercIva(percIva);
			tab.setAddIva(addIva);
			tab.setPercTribProv(percTribProv);
			tab.setTribProv(tribProv);
			tab.setTotale(totale);
			tab.setTotaleEnte(totEnte);
			tab.setTotaleStato(totStato);
			tab.setTribNettoComune(tribNettoComune);
			tab.setImpNettoAcconto(impNettoAcconto);
			tab.setDifferenzaNetto(differenzaNetto);
			tab.setDifferenzaLordo(differenzaLordo);
			tab.setTribProvinciale(tribProvinciale);
			
			tab.setAttComune(attComune);
			tab.setAttPro(attPro);
			tab.setOldPro(oldPro);
			tab.setOldTot(oldTot);
			tab.setTrQuotaTia(trQuotaTia);
			tab.setSupAnnoPrec(supAnnoPrec);
			tab.setSupAnno(supAnno);
			tab.setSgrAnno(sgrAnno);
			tab.setTipoDoc(tipoDoc);
			
			//tab.setProvenienza(provenienza);		
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			
			SitRuoloTaresDao dao = (SitRuoloTaresDao) DaoFactory.createDao(conn, tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());		
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitRuoloTares",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	
		return(new ApplicationAck("Record SIT_RUOLO_TARES inserito"));
	
	}

}
