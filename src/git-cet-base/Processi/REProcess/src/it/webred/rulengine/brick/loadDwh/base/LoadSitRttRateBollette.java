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
import it.webred.rulengine.dwh.Dao.SitRttRateBolletteDao;
import it.webred.rulengine.dwh.Dao.SitTrffMulteDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.def.TipoXml;
import it.webred.rulengine.dwh.table.SitRttRateBollette;
import it.webred.rulengine.dwh.table.SitDPersona;
import it.webred.rulengine.dwh.table.SitDUnione;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;

public class LoadSitRttRateBollette extends AbstractLoaderCommand implements Rule
{
	
	public LoadSitRttRateBollette(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadSitRttRateBollette.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitRttRateBollette.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
		throws CommandException
	{
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
			
			String cod_bolletta = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
			String cod_servizio = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
			String num_rata = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
			String dt_scadenza_rata = (String) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
			String importo_rata = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			String importo_pagato = (String) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			String dt_pagamento = (String) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
			String dt_reg_pagamento = (String) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
			String des_distinta = (String) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());
			String dt_distinta = (String) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
			String id_servizio = (String) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
			String id_pratica = (String) ctx.get(((RRuleParamIn) parametriIn.get(13)).getDescr());
			String des_canale = (String) ctx.get(((RRuleParamIn) parametriIn.get(14)).getDescr());
			String des_pagamento = (String) ctx.get(((RRuleParamIn) parametriIn.get(15)).getDescr());
			String note = (String) ctx.get(((RRuleParamIn) parametriIn.get(16)).getDescr());

			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(17)).getDescr());
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(18)).getDescr());
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(19)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(20)).getDescr());
			
			
			if(cod_bolletta==null || num_rata==null || dt_scadenza_rata==null || importo_rata==null)
				return new RejectAck("SIT_RTT_RATE_BOLLETTE - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_RTT_RATE_BOLLETTE - flag_dt_val_dato non valido");
			SitRttRateBollette tab = (SitRttRateBollette)getTabellaDwhInstance(ctx);
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			tab.setIdOrig(chiaveOriginale);
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			
			tab.setCodBolletta(cod_bolletta);
			tab.setCodServizio(cod_servizio);
			tab.setNumRata(new BigDecimal(num_rata));
			Timestamp ts = new Timestamp(sdf.parse(dt_scadenza_rata).getTime());
			tab.setDtScadenzaRata(DwhUtils.getDataDwh(new DataDwh(), ts));
			tab.setImportoRata(new BigDecimal(importo_rata));
			if(importo_pagato != null){
				tab.setImportoPagato(new BigDecimal(importo_pagato));
			}
			if(dt_pagamento != null){
				ts = new Timestamp(sdf.parse(dt_pagamento).getTime());
				tab.setDtPagamento(DwhUtils.getDataDwh(new DataDwh(), ts));
			}
			if(dt_reg_pagamento != null){
				ts = new Timestamp(sdf.parse(dt_reg_pagamento).getTime());
				tab.setDtRegPagamento(DwhUtils.getDataDwh(new DataDwh(), ts));
			}
			tab.setDesDistinta(des_distinta);
			if(dt_distinta != null){
				ts = new Timestamp(sdf.parse(dt_distinta).getTime());
				tab.setDtDistinta(DwhUtils.getDataDwh(new DataDwh(), ts));
			}
			if(id_servizio != null)
				tab.setIdServizio(new BigDecimal(id_servizio));
			tab.setIdPratica(id_pratica);
			tab.setDesCanale(des_canale);
			tab.setDesPagamento(des_pagamento);
			tab.setNote(note);
			
			SitRttRateBolletteDao dao = (SitRttRateBolletteDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			
			boolean salvato = dao.save(ctx.getBelfiore());		
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitRttRateBollette",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		String msg = "Record SIT_RTT_RATE_BOLLETTE inserito";
		return(new ApplicationAck(msg));

	}

}
