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
import it.webred.rulengine.dwh.Dao.SitTCosapTassaDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.SitTCosapTassa;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadSitTCosapTassa extends AbstractLoaderCommand implements Rule
{

	public LoadSitTCosapTassa(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadSitTCosapTassa.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitTCosapTassa.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
	throws CommandException
	{
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			
			String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());			
			String codUnivocoCanone = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
			String id_orig_contrib = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
			String tipoDocumento = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
			String numeroDocumento = (String) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
			String annoDocumento = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			String statoDocumento = (String) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			String numeroProtocollo = (String) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
			String annoContabileDocumento = (String) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
			Timestamp dtProtocollo = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());
			Timestamp dtIniValidita = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
			Timestamp dtFinValidita = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
			Timestamp dtRichiesta = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(13)).getDescr());
			String tipoOccupazione = (String) ctx.get(((RRuleParamIn) parametriIn.get(14)).getDescr());
			String codiceImmobile = (String) ctx.get(((RRuleParamIn) parametriIn.get(15)).getDescr());
			String codiceVia = (String) ctx.get(((RRuleParamIn) parametriIn.get(16)).getDescr());
			String sedime = (String) ctx.get(((RRuleParamIn) parametriIn.get(17)).getDescr());
			String indirizzo = (String) ctx.get(((RRuleParamIn) parametriIn.get(18)).getDescr());
			String civico = (String) ctx.get(((RRuleParamIn) parametriIn.get(19)).getDescr());
			String zona = (String) ctx.get(((RRuleParamIn) parametriIn.get(20)).getDescr());
			BigDecimal foglio = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(21)).getDescr());
			String particella = (String) ctx.get(((RRuleParamIn) parametriIn.get(22)).getDescr());
			BigDecimal subalterno = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(23)).getDescr());
			BigDecimal quantita = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(24)).getDescr());
			String unitaMisuraQuantita = (String) ctx.get(((RRuleParamIn) parametriIn.get(25)).getDescr());
			BigDecimal tariffaPerUnita = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(26)).getDescr());
			BigDecimal importoCanone = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(27)).getDescr());
			Timestamp dtIniValiditaTariffa = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(28)).getDescr());
			Timestamp dtFinValiditaTariffa = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(29)).getDescr());
			String descrizione = (String) ctx.get(((RRuleParamIn) parametriIn.get(30)).getDescr());
			String note = (String) ctx.get(((RRuleParamIn) parametriIn.get(31)).getDescr());			
			String codiceEsenzione = (String) ctx.get(((RRuleParamIn) parametriIn.get(32)).getDescr());
			BigDecimal scontoAssoluto = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(33)).getDescr());
			BigDecimal percentualeSconto = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(34)).getDescr());
			Timestamp dtIniSconto = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(35)).getDescr());
			Timestamp dtFinSconto = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(36)).getDescr());
			String provenienza = (String) ctx.get(((RRuleParamIn) parametriIn.get(37)).getDescr());		
			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(38)).getDescr());
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(39)).getDescr());
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(40)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(41)).getDescr());
	
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_T_COSAP_TASSA - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_T_COSAP_TASSA - flag_dt_val_dato non valido");
			
			SitTCosapTassa tab = (SitTCosapTassa)getTabellaDwhInstance(ctx);
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);			
			tab.setIdOrig(chiaveOriginale);
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			tab.setCodUnivocoCanone(codUnivocoCanone);
			if (id_orig_contrib != null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_contrib);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(provenienza, co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtContrib(ce);
			}
			tab.setTipoDocumento(tipoDocumento);
			tab.setNumeroDocumento(numeroDocumento);
			tab.setAnnoDocumento(annoDocumento);
			tab.setStatoDocumento(statoDocumento);
			tab.setNumeroProtocollo(numeroProtocollo);
			tab.setAnnoContabileDocumento(annoContabileDocumento);
			tab.setDtProtocollo(DwhUtils.getDataDwh(new DataDwh(), dtProtocollo));
			tab.setDtIniValidita(DwhUtils.getDataDwh(new DataDwh(), dtIniValidita));
			tab.setDtFinValidita(DwhUtils.getDataDwh(new DataDwh(), dtFinValidita));
			tab.setDtRichiesta(DwhUtils.getDataDwh(new DataDwh(), dtRichiesta));
			tab.setTipoOccupazione(tipoOccupazione);
			tab.setCodiceImmobile(codiceImmobile);
			tab.setCodiceVia(codiceVia);
			tab.setSedime(sedime);
			tab.setIndirizzo(indirizzo);
			tab.setCivico(civico);
			tab.setZona(zona);
			tab.setFoglio(foglio);
			tab.setParticella(particella);
			tab.setSubalterno(subalterno);
			tab.setQuantita(quantita);
			tab.setUnitaMisuraQuantita(unitaMisuraQuantita);
			tab.setTariffaPerUnita(tariffaPerUnita);
			tab.setImportoCanone(importoCanone);
			tab.setDtIniValiditaTariffa(DwhUtils.getDataDwh(new DataDwh(), dtIniValiditaTariffa));
			tab.setDtFinValiditaTariffa(DwhUtils.getDataDwh(new DataDwh(), dtFinValiditaTariffa));
			tab.setDescrizione(descrizione);
			tab.setNote(note);
			tab.setCodiceEsenzione(codiceEsenzione);
			tab.setScontoAssoluto(scontoAssoluto);
			tab.setPercentualeSconto(percentualeSconto);
			tab.setDtIniSconto(DwhUtils.getDataDwh(new DataDwh(), dtIniSconto));
			tab.setDtFinSconto(DwhUtils.getDataDwh(new DataDwh(), dtFinSconto));	
			tab.setProvenienza(provenienza);		
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			
			SitTCosapTassaDao dao = (SitTCosapTassaDao) DaoFactory.createDao(conn, tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());		
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitTCosapTassa",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	
		return(new ApplicationAck("Record SIT_T_COSAP_TASSA inserito"));
	
	}

}
