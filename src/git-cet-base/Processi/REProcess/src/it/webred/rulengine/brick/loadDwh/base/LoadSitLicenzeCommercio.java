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
import it.webred.rulengine.dwh.Dao.SitLicenzeCommercioDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.table.SitLicenzeCommercio;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class LoadSitLicenzeCommercio extends AbstractLoaderCommand implements Rule
{
	


	public LoadSitLicenzeCommercio(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}



	private static final Logger log = Logger.getLogger(LoadSitLicenzeCommercio.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitLicenzeCommercio.class;
	}

	
	
	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
	throws CommandException
	{
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			
			String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
			String numero = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
			String numeroProtocollo = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
			String annoProtocollo = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
			String tipologia = (String) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
			String carattere = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			String stato = (String) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			Timestamp dataInizioSospensione = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
			Timestamp dataFineSospensione = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
			String id_orig_vie = (String) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());
			String civico = (String) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
			String civico2 = (String) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
			String civico3 = (String) ctx.get(((RRuleParamIn) parametriIn.get(13)).getDescr());
			BigDecimal superficieMinuto = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(14)).getDescr());
			BigDecimal superficieTotale = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(15)).getDescr());
			String sezioneCatastale = (String) ctx.get(((RRuleParamIn) parametriIn.get(16)).getDescr());
			String foglio = (String) ctx.get(((RRuleParamIn) parametriIn.get(17)).getDescr());
			String particella = (String) ctx.get(((RRuleParamIn) parametriIn.get(18)).getDescr());
			String subalterno = (String) ctx.get(((RRuleParamIn) parametriIn.get(19)).getDescr());
			String codiceFabbricato = (String) ctx.get(((RRuleParamIn) parametriIn.get(20)).getDescr());
			Timestamp dataValidita = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(21)).getDescr());
			Timestamp dataRilascio = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(22)).getDescr());
			String zona = (String) ctx.get(((RRuleParamIn) parametriIn.get(23)).getDescr());
			String ragSoc = (String) ctx.get(((RRuleParamIn) parametriIn.get(24)).getDescr());
			String note = (String) ctx.get(((RRuleParamIn) parametriIn.get(25)).getDescr());
			String riferimAtto = (String) ctx.get(((RRuleParamIn) parametriIn.get(26)).getDescr());
			String provenienza = (String) ctx.get(((RRuleParamIn) parametriIn.get(27)).getDescr());		
			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(28)).getDescr());
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(29)).getDescr());
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(30)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(31)).getDescr());
	
			if(id_orig==null || fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_LICENZE_COMMERCIO - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_LICENZE_COMMERCIO - flag_dt_val_dato non valido");
			
			SitLicenzeCommercio tab = new SitLicenzeCommercio();
			tab.setProcessid(new ProcessId(ctx.getProcessID()));
			
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);			
			tab.setIdOrig(chiaveOriginale);
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());		
			tab.setNumero(numero);
			tab.setNumeroProtocollo(numeroProtocollo);
			tab.setAnnoProtocollo(annoProtocollo);
			tab.setTipologia(tipologia);
			tab.setCarattere(carattere);
			tab.setStato(stato);
			tab.setDataInizioSospensione(DwhUtils.getDataDwh(new DataDwh(), dataInizioSospensione));
			tab.setDataFineSospensione(DwhUtils.getDataDwh(new DataDwh(), dataFineSospensione));			
			if (id_orig_vie != null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_vie);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(provenienza, co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtVie(ce);
			}
			tab.setCivico(civico);
			tab.setCivico2(civico2);
			tab.setCivico3(civico3);
			tab.setSuperficieMinuto(superficieMinuto);
			tab.setSuperficieTotale(superficieTotale);
			tab.setSezioneCatastale(sezioneCatastale);
			tab.setFoglio(foglio);
			tab.setParticella(particella);
			tab.setSubalterno(subalterno);
			tab.setCodiceFabbricato(codiceFabbricato);
			tab.setDataValidita(DwhUtils.getDataDwh(new DataDwh(), dataValidita));
			tab.setDataRilascio(DwhUtils.getDataDwh(new DataDwh(), dataRilascio));
			tab.setZona(zona);
			tab.setRagSoc(ragSoc);
			tab.setNote(note);
			tab.setRiferimAtto(riferimAtto);
			tab.setProvenienza(provenienza);		
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			
			SitLicenzeCommercioDao dao = (SitLicenzeCommercioDao) DaoFactory.createDao(conn, tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());		
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitLicenzeCommercio",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	
		return(new ApplicationAck("Record SIT_LICENZE_COMMERCIO inserito"));
	
	}

}
