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
import it.webred.rulengine.dwh.Dao.SitBucapAfmDao;
import it.webred.rulengine.dwh.Dao.SitBucapPraEdiDao;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtFineVal;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.DtIniVal;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.table.SitBucapAfm;
import it.webred.rulengine.dwh.table.SitBucapPraEdi;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadSitBucapAfm extends AbstractLoaderCommand implements Rule {
	
	public LoadSitBucapAfm(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	private static final Logger log = Logger.getLogger(LoadSitBucapAfm.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitBucapAfm.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn) throws CommandException {
		
		try {			
			String id_orig = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.1.descr"));
			BigDecimal fk_ente_sorgente = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.2.descr"));
			
			String numero_work_flow__verifica = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.3.descr"));
			String via = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.4.descr"));
			String sede = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.5.descr"));
			String anno_workflow = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.6.descr"));
			String descrizione_sede = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.7.descr"));
			String numero_pg_documento = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.8.descr"));
			String esibente = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.9.descr"));
			String codice_classificazione = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.10.descr"));
			String anno_work_flow__verifica = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.11.descr"));
			String ds_codice_classificazione = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.12.descr"));
			String numero_civico = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.13.descr"));
			String id_scatola = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.14.descr"));
			String piano_di_intervento = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.15.descr"));
			String numero_workflow = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.16.descr"));
			String anno_pg_documento = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.17.descr"));
			String dati_catastali = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.18.descr"));
			String periodo_archivio = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.19.descr"));
			String descrizione_periodo_archivio = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.20.descr"));
			String codice_settore = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.21.descr"));
			String descrizione_codice_settore = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.22.descr"));
			String oggetto_visura = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.23.descr"));
			String nm_faldone = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.24.descr"));
			String nm_pratica = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.25.descr"));
			String magazzino = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.26.descr"));
			String scaffale = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.27.descr"));
			String ripiano = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.28.descr"));
			String buca = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.29.descr"));
			String data_inserimento = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.30.descr"));
			String fila = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.31.descr"));
			String immagine_presente = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.32.descr"));
			String data_ultimo_rientro = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.33.descr"));
			String tipo_pratica = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.34.descr"));
			String richiedente = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.35.descr"));
			String descrizione_richiedente = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.36.descr"));
			String cod_via = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.37.descr"));
			
			String provenienza = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.38.descr"));
			Timestamp dt_inizio_val = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.39.descr"));
			Timestamp dt_fine_val = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.40.descr"));
			Timestamp dt_exp_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.41.descr"));
			Timestamp dt_inizio_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.42.descr"));
			Timestamp dt_fine_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.43.descr"));
			BigDecimal flag_dt_val_dato = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.44.descr"));
			
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("BUCAP PRATICHE EDILIZIE - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("BUCAP PRATICHE EDILIZIE - flag_dt_val_dato non valido");
			
			SitBucapAfm bucapAfm = new SitBucapAfm();
			
			bucapAfm.setProcessid(new ProcessId(ctx.getProcessID()));
			
			ChiaveOriginale co =  new ChiaveOriginale();
			co.setValore(id_orig);			
			bucapAfm.setIdOrig(co);
			
			bucapAfm.setNumero_work_flow__verifica(numero_work_flow__verifica);
			bucapAfm.setVia(via);	
			bucapAfm.setSede(sede);
			bucapAfm.setAnno_workflow(anno_workflow);
			bucapAfm.setDescrizione_sede(descrizione_sede);
			bucapAfm.setNumero_pg_documento(numero_pg_documento);
			bucapAfm.setEsibente(esibente);
			bucapAfm.setCodice_classificazione(codice_classificazione);
			bucapAfm.setAnno_work_flow__verifica(anno_work_flow__verifica);
			bucapAfm.setDs_codice_classificazione(ds_codice_classificazione);
			bucapAfm.setNumero_civico(numero_civico);
			bucapAfm.setId_scatola(id_scatola);
			bucapAfm.setPiano_di_intervento(piano_di_intervento);
			bucapAfm.setNumero_workflow(numero_workflow);
			bucapAfm.setAnno_pg_documento( anno_pg_documento );
			bucapAfm.setDati_catastali( dati_catastali );
			bucapAfm.setPeriodo_archivio(periodo_archivio);
			bucapAfm.setDescrizione_periodo_archivio(descrizione_periodo_archivio);
			bucapAfm.setCodice_settore(codice_settore);
			bucapAfm.setDescrizione_codice_settore(descrizione_codice_settore);
			bucapAfm.setOggetto_visura(oggetto_visura);
			bucapAfm.setNm_faldone(nm_faldone);
			bucapAfm.setNm_pratica(nm_pratica);
			bucapAfm.setMagazzino(magazzino);
			bucapAfm.setScaffale(scaffale);
			bucapAfm.setRipiano(ripiano);
			bucapAfm.setBuca(buca);
			bucapAfm.setData_inserimento(data_inserimento);
			bucapAfm.setFila(fila);
			bucapAfm.setImmagine_presente(immagine_presente);
			bucapAfm.setData_ultimo_rientro(data_ultimo_rientro);
			bucapAfm.setTipo_pratica(tipo_pratica);
			bucapAfm.setRichiedente(richiedente);
			bucapAfm.setDescrizione_richiedente(descrizione_richiedente);
			bucapAfm.setCod_via(cod_via);
			
			bucapAfm.setProvenienza( provenienza );
			bucapAfm.setDtInizioVal((DtIniVal)DwhUtils.getDataDwh(new DtIniVal(), dt_inizio_val));
			bucapAfm.setDtFineVal((DtFineVal)DwhUtils.getDataDwh(new DtFineVal(), dt_fine_val));
			bucapAfm.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			bucapAfm.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_inizio_dato));
			bucapAfm.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_dato));
			bucapAfm.setFlagDtValDato(flag_dt_val_dato);
			bucapAfm.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente.intValue()), ctx.getBelfiore());
			
			SitBucapAfmDao dao = (SitBucapAfmDao) DaoFactory.createDao(conn, bucapAfm, ctx.getEnteSorgenteById(fk_ente_sorgente.intValue()));
			
			@SuppressWarnings("unused")			
			boolean salvato = dao.save(ctx.getBelfiore());
			
		} catch (DaoException e) {
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e) {
			log.error("LoadSitBucap",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		return(new ApplicationAck("Record SIT_BUCAP_AFM inserito"));
	}

}
