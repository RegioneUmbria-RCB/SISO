package it.webred.rulengine.brick.loadDwh.base;

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
import it.webred.rulengine.dwh.Dao.SitPubPratDettaglioDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.SitPubPratDettaglio;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadSitPubblicitaPratDettaglio extends AbstractLoaderCommand implements Rule
{
	
	
	public LoadSitPubblicitaPratDettaglio(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}


	private static final Logger log = Logger.getLogger(LoadSitPubblicitaPratDettaglio.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitPubPratDettaglio.class;
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
			
			String tipoPratica = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			Integer numPratica = (Integer)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String annoPratica = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String codCls = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String descCls = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String codOggetto = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String descOggetto = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String annotazioni = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String indirizzo = (String)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String via = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String civico = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dtInizio = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dtFine = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Integer giorniOccupazione = (Integer) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String codZona = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String descZona = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal base = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal altezza = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal supImponibile = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal supEsistente = (BigDecimal)this.getRRuleParameterIn(parametriIn, ctx, index++);
			String codCar = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String descCar = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Integer numEsemplari = (Integer)this.getRRuleParameterIn(parametriIn, ctx, index++);
			Integer numFacce = (Integer)this.getRRuleParameterIn(parametriIn, ctx, index++);
			
			String provenienza = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_exp_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_ini_val_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_fine_val_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Integer flag_dt_val_dato = (Integer) this.getRRuleParameterIn(parametriIn, ctx, index++);
	
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_PUBBLICITA_PRAT_DETTAGLIO - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_PUBBLICITA_PRAT_DETTAGLIO - flag_dt_val_dato non valido");
			
			SitPubPratDettaglio tab = (SitPubPratDettaglio)getTabellaDwhInstance(ctx);
			
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);			
			tab.setIdOrig(chiaveOriginale);
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			
			tab.setTipoPratica(tipoPratica);
			tab.setNumPratica(new BigDecimal(numPratica));
			tab.setAnnoPratica(annoPratica);
			tab.setCodCls(codCls);
			tab.setDescCls(descCls);
			tab.setCodOggetto(codOggetto);
			tab.setDescOggetto(descOggetto);
			tab.setAnnotazioni(annotazioni);
			tab.setIndirizzo(indirizzo);
			tab.setVia(via);
			tab.setCivico(civico);
			tab.setDtInizio(DwhUtils.getDataDwh(new DataDwh(), dtInizio));
			tab.setDtFine(DwhUtils.getDataDwh(new DataDwh(), dtFine));
			tab.setGiorniOccupazione(new BigDecimal(giorniOccupazione));
			tab.setCodZonaCespite(codZona);
			tab.setDescZonaCespite(descZona);
			tab.setBase(base);
			tab.setAltezza(altezza);
			tab.setSupImponibile(supImponibile);
			tab.setSupEsistente(supEsistente);
			tab.setCodCaratteristica(codCar);
			tab.setDescCaratteristica(descCar);
			tab.setNumEsemplari(new BigDecimal(numEsemplari));
			tab.setNumFacce(new BigDecimal(numFacce));
		
			
			String idOrigTestata = tab.getTipoPratica()+"|"+tab.getNumPratica()+"|"+tab.getAnnoPratica();
			
			ChiaveOriginale co = new ChiaveOriginale();
			co.setValore(idOrigTestata);
			ChiaveEsterna ce = new ChiaveEsterna();
			ce.setValore(provenienza, co, DwhUtils.getIdentificativo(fk_ente_sorgente));
			tab.setIdExtTestata(ce);
			
			
			
			tab.setProvenienza(provenienza);		
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			
			SitPubPratDettaglioDao dao = (SitPubPratDettaglioDao) DaoFactory.createDao(conn, tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());		
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitPubblicitaPratDettaglio",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	
		return(new ApplicationAck("Record SIT_PUBBLICITA_PRAT_DETTAGLIO inserito"));
	
	}

}
