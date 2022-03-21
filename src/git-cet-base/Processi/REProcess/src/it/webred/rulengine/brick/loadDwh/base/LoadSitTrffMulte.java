package it.webred.rulengine.brick.loadDwh.base;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.NumberFormat;
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
import it.webred.rulengine.dwh.Dao.SitTrffMulteDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.def.TipoXml;
import it.webred.rulengine.dwh.table.SitTrffMulte;
import it.webred.rulengine.dwh.table.SitDPersona;
import it.webred.rulengine.dwh.table.SitDUnione;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.utils.StringUtils;

public class LoadSitTrffMulte extends AbstractLoaderCommand implements Rule
{
	
	public LoadSitTrffMulte(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadSitTrffMulte.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitTrffMulte.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
		throws CommandException
	{
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
			
			String stato_verbale = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
			String tipo_verbale = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
			String nr_verbale = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
			String serie_verbale = (String) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
			String anno_verbale = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			String data_multa = (String) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			String importo_multa = (String) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
			String importo_dovuto = (String) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
			String dt_scadenza_pagam = (String) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());
			String luogo_infrazione = (String) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
			String note = (String) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
			String tipo_ente = (String) ctx.get(((RRuleParamIn) parametriIn.get(13)).getDescr());
			String comune_ente = (String) ctx.get(((RRuleParamIn) parametriIn.get(14)).getDescr());
			String targa = (String) ctx.get(((RRuleParamIn) parametriIn.get(15)).getDescr());
			String marca = (String) ctx.get(((RRuleParamIn) parametriIn.get(16)).getDescr());
			String modello = (String) ctx.get(((RRuleParamIn) parametriIn.get(17)).getDescr());
			String codice_persona = (String) ctx.get(((RRuleParamIn) parametriIn.get(18)).getDescr());
			String cognome = (String) ctx.get(((RRuleParamIn) parametriIn.get(19)).getDescr());
			String nome = (String) ctx.get(((RRuleParamIn) parametriIn.get(20)).getDescr());
			String luogo_nascita = (String) ctx.get(((RRuleParamIn) parametriIn.get(21)).getDescr());
			String dt_nascita = (String) ctx.get(((RRuleParamIn) parametriIn.get(22)).getDescr());
			String luogo_residenza = (String) ctx.get(((RRuleParamIn) parametriIn.get(23)).getDescr());
			String indirizzo_residenza = (String) ctx.get(((RRuleParamIn) parametriIn.get(24)).getDescr());
			String nr_patente = (String) ctx.get(((RRuleParamIn) parametriIn.get(25)).getDescr());
			String dt_rilascio_patente = (String) ctx.get(((RRuleParamIn) parametriIn.get(26)).getDescr());
			String prov_rilascio_patente = (String) ctx.get(((RRuleParamIn) parametriIn.get(27)).getDescr());
			String flag_pagamento = (String) ctx.get(((RRuleParamIn) parametriIn.get(28)).getDescr());
			String estremi_pagamento = (String) ctx.get(((RRuleParamIn) parametriIn.get(29)).getDescr());
			String sistema_pagamento = (String) ctx.get(((RRuleParamIn) parametriIn.get(30)).getDescr());
			String dt_pagamento = (String) ctx.get(((RRuleParamIn) parametriIn.get(31)).getDescr());
			String importo_pagato = (String) ctx.get(((RRuleParamIn) parametriIn.get(32)).getDescr());
			String cod_fisc = (String) ctx.get(((RRuleParamIn) parametriIn.get(33)).getDescr());
			String importo_scontato = (String) ctx.get(((RRuleParamIn) parametriIn.get(34)).getDescr());

			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(35)).getDescr());
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(36)).getDescr());
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(37)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(38)).getDescr());
			
			
			if(id_orig==null || fk_ente_sorgente==null || tipo_verbale==null || nr_verbale==null
					|| data_multa==null )
				return new RejectAck("SIT_TRFF_MULTE - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_TRFF_MULTE - flag_dt_val_dato non valido");
			SitTrffMulte tab = (SitTrffMulte)getTabellaDwhInstance(ctx);
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);
						
			tab.setIdOrig(chiaveOriginale);
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			
			tab.setStatoVerbale(stato_verbale);
			tab.setTipoVerbale(tipo_verbale);
			tab.setNrVerbale(nr_verbale);
			tab.setSerieVerbale(serie_verbale);
			tab.setAnnoVerbale(anno_verbale);
			tab.setDataMulta(new BigDecimal(data_multa));
			tab.setImportoMulta(getBigDecimal(importo_multa));
			tab.setImportoDovuto(getBigDecimal(importo_dovuto));
			if(dt_scadenza_pagam != null)
				tab.setDtScadenzaPagam(new BigDecimal(dt_scadenza_pagam));
			tab.setLuogoInfrazione(luogo_infrazione);
			tab.setNote(note);
			tab.setTipoEnte(tipo_ente);
			tab.setComuneEnte(comune_ente);
			tab.setTarga(targa);
			tab.setMarca(marca);
			tab.setModello(modello);
			tab.setCodicePersona(codice_persona);
			tab.setCognome(cognome);
			tab.setNome(nome);
			tab.setLuogoNascita(luogo_nascita);
			tab.setDtNascita(dt_nascita);
			tab.setLuogoResidenza(luogo_residenza);
			tab.setIndirizzoResidenza(indirizzo_residenza);
			tab.setNrPatente(nr_patente);
			if(dt_rilascio_patente != null)
				tab.setDtRilascioPatente(new BigDecimal(dt_rilascio_patente));
			tab.setProvRilascioPatente(prov_rilascio_patente);
			tab.setFlagPagamento(flag_pagamento);
			tab.setEstremiPagamento(estremi_pagamento);
			tab.setSistemaPagamento(sistema_pagamento);
			if(dt_pagamento != null)
				tab.setDtPagamento(new BigDecimal(dt_pagamento));
			if(importo_pagato != null){
				tab.setImportoPagato(getBigDecimal(importo_pagato));
			}
			tab.setCodFisc(cod_fisc);
			if(importo_scontato != null){
				tab.setImportoScontato(getBigDecimal(importo_scontato));
			}
			
			SitTrffMulteDao dao = (SitTrffMulteDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			
			boolean salvato = dao.save(ctx.getBelfiore());			
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitTrffMulte",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		String msg = "Record SIT_TRFF_MULTE inserito";
		return(new ApplicationAck(msg));

	}
	
	private BigDecimal getBigDecimal(String numero) {
		String trim = StringUtils.trimLeftChar(numero,'0');

		String stingVal = "".equalsIgnoreCase(trim)?"0":trim;

		return new BigDecimal(stingVal);
	}

}
