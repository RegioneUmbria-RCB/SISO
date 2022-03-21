package it.webred.rulengine.brick.loadDwh.base;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.swing.text.TabSet;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import it.webred.rulengine.Context;
import it.webred.rulengine.DbCommand;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.Dao.DaoException;
import it.webred.rulengine.dwh.Dao.DaoFactory;
import it.webred.rulengine.dwh.Dao.SitComuneDao;
import it.webred.rulengine.dwh.Dao.SitDCivicoDao;
import it.webred.rulengine.dwh.Dao.SitDPersonaDao;
import it.webred.rulengine.dwh.Dao.SitDUnioneDao;
import it.webred.rulengine.dwh.Dao.SitRttBolletteDao;
import it.webred.rulengine.dwh.Dao.SitTrffMulteDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.def.TipoXml;
import it.webred.rulengine.dwh.table.SitRttBollette;
import it.webred.rulengine.dwh.table.SitDPersona;
import it.webred.rulengine.dwh.table.SitDUnione;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;

public class LoadSitRttBollette extends AbstractLoaderCommand implements Rule
{
	
	public LoadSitRttBollette(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadSitRttBollette.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitRttBollette.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
		throws CommandException
	{
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
			
			String cod_soggetto = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
			String des_intestatario = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
			String codice_fiscale = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
			String indirizzo = (String) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
			String recapito = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			String cod_bolletta = (String) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			String anno = (String) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
			String cod_servizio = (String) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
			String id_servizio = (String) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());
			String num_bolletta = (String) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
			String num_rate = (String) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
			String data_bolletta = (String) ctx.get(((RRuleParamIn) parametriIn.get(13)).getDescr());
			String oggetto = (String) ctx.get(((RRuleParamIn) parametriIn.get(14)).getDescr());
			String spese_spedizione = (String) ctx.get(((RRuleParamIn) parametriIn.get(15)).getDescr());
			String tot_esente_iva = (String) ctx.get(((RRuleParamIn) parametriIn.get(16)).getDescr());
			String tot_imponibile_iva = (String) ctx.get(((RRuleParamIn) parametriIn.get(17)).getDescr());
			String tot_iva = (String) ctx.get(((RRuleParamIn) parametriIn.get(18)).getDescr());
			String arrotondamento_prec = (String) ctx.get(((RRuleParamIn) parametriIn.get(19)).getDescr());
			String arrotondamento_att = (String) ctx.get(((RRuleParamIn) parametriIn.get(20)).getDescr());
			String importo_bolletta_prec = (String) ctx.get(((RRuleParamIn) parametriIn.get(21)).getDescr());
			String tot_bolletta = (String) ctx.get(((RRuleParamIn) parametriIn.get(22)).getDescr());
			String tot_pagato = (String) ctx.get(((RRuleParamIn) parametriIn.get(23)).getDescr());
			String fl_non_pagare = (String) ctx.get(((RRuleParamIn) parametriIn.get(24)).getDescr());
			String mot_non_pagare = (String) ctx.get(((RRuleParamIn) parametriIn.get(25)).getDescr());
			String note = (String) ctx.get(((RRuleParamIn) parametriIn.get(26)).getDescr());

			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(27)).getDescr());
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(28)).getDescr());
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(29)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(30)).getDescr());
			
			
			if(cod_soggetto==null || des_intestatario==null || recapito==null || cod_bolletta==null
					|| anno==null || cod_servizio==null || id_servizio==null || num_bolletta==null
					|| data_bolletta==null || oggetto==null || tot_bolletta==null)
				return new RejectAck("SIT_RTT_BOLLETTE - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_RTT_BOLLETTE - flag_dt_val_dato non valido");
			SitRttBollette tab = (SitRttBollette)getTabellaDwhInstance(ctx);
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			tab.setIdOrig(chiaveOriginale);
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			
			tab.setCodSoggetto(cod_soggetto);
			tab.setDesIntestatario(des_intestatario);
			tab.setCodiceFiscale(codice_fiscale);
			tab.setIndirizzo(indirizzo);
			tab.setRecapito(recapito);
			tab.setCodBolletta(cod_bolletta);
			tab.setAnno(new BigDecimal(anno));
			tab.setCodServizio(cod_servizio);
			tab.setIdServizio(new BigDecimal(id_servizio));
			tab.setNumBolletta(num_bolletta);
			if(num_rate != null)
				tab.setNumRate(new BigDecimal(num_rate));
			Timestamp ts = new Timestamp(sdf.parse(data_bolletta).getTime());
			tab.setDataBolletta(DwhUtils.getDataDwh(new DataDwh(), ts));
			tab.setOggetto(oggetto);
			if(spese_spedizione != null)
				tab.setSpeseSpedizione(new BigDecimal(spese_spedizione));
			if(tot_esente_iva != null)
				tab.setTotEsenteIva(new BigDecimal(tot_esente_iva));
			if(tot_imponibile_iva != null)
				tab.setTotImponibileIva(new BigDecimal(tot_imponibile_iva));
			if(tot_iva != null)
				tab.setTotIva(new BigDecimal(tot_iva));
			if(arrotondamento_prec != null)
				tab.setArrotondamentoPrec(new BigDecimal(arrotondamento_prec));
			if(arrotondamento_att != null)
				tab.setArrotondamentoAtt(new BigDecimal(arrotondamento_att));
			if(importo_bolletta_prec != null)
				tab.setImportoBollettaPrec(new BigDecimal(importo_bolletta_prec));
			tab.setTotBolletta(new BigDecimal(tot_bolletta));
			if(tot_pagato != null){
				tab.setTotPagato(new BigDecimal(tot_pagato));
			}
			if(fl_non_pagare != null)
				tab.setFlNonPagare(new BigDecimal(fl_non_pagare));
			tab.setMotNonPagare(mot_non_pagare);
			tab.setNote(note);
			
			SitRttBolletteDao dao = (SitRttBolletteDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			
			boolean salvato = dao.save(ctx.getBelfiore());			
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitRttBollette",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		String msg = "Record SIT_RTT_BOLLETTE inserito";
		return(new ApplicationAck(msg));

	}

}
