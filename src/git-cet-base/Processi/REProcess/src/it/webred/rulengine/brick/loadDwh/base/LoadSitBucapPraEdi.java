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
import it.webred.rulengine.dwh.Dao.SitBucapPraEdiDao;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtFineVal;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.DtIniVal;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.table.SitBucapPraEdi;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadSitBucapPraEdi extends AbstractLoaderCommand implements Rule {
	
	public LoadSitBucapPraEdi(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	private static final Logger log = Logger.getLogger(LoadSitBucapPraEdi.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitBucapPraEdi.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn) throws CommandException {
		
		try {			
			String id_orig = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.1.descr"));
			BigDecimal fk_ente_sorgente = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.2.descr"));
			
			String via = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.3.descr"));
			String sede = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.4.descr"));
			String descrizione_sede = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.5.descr"));
			String periodo_archivio = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.6.descr"));
			String descrizione_periodo_archivio = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.7.descr"));
			String anno_protocollo = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.8.descr"));
			String id_scatola = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.9.descr"));
			String protocollo = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.10.descr"));
			String numero_deposito = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.11.descr"));
			String numero_civico = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.12.descr"));
			String committente = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.13.descr"));
			String impresa = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.14.descr"));
			String nm_faldone = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.15.descr"));
			String nm_pratica = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.16.descr"));
			String magazzino = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.17.descr"));
			String scaffale = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.18.descr"));
			String ripiano = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.19.descr"));
			String buca = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.20.descr"));
			String fila = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.21.descr"));
			String immagine_presente = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.22.descr"));
			String data_inserimento = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.23.descr"));
			String data_ultimo_rientro = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.24.descr"));
			String tipo_pratica = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.25.descr"));
			String richiedente = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.26.descr"));
			String descrizione_richiedente = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.27.descr"));
			String cod_via = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.28.descr"));
			String status = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.29.descr"));
			
			String provenienza = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.30.descr"));
			Timestamp dt_inizio_val = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.31.descr"));
			Timestamp dt_fine_val = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.32.descr"));
			Timestamp dt_exp_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.33.descr"));
			Timestamp dt_inizio_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.34.descr"));
			Timestamp dt_fine_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.35.descr"));
			BigDecimal flag_dt_val_dato = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.36.descr"));
			
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("BUCAP PRATICHE EDILIZIE - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("BUCAP PRATICHE EDILIZIE - flag_dt_val_dato non valido");
			
			SitBucapPraEdi bucapPreEdi = new SitBucapPraEdi();
			
			bucapPreEdi.setProcessid(new ProcessId(ctx.getProcessID()));
			
			ChiaveOriginale co =  new ChiaveOriginale();
			co.setValore(id_orig);			
			bucapPreEdi.setIdOrig(co);
			
			bucapPreEdi.setVia(via);	
			bucapPreEdi.setSede(sede);
			bucapPreEdi.setDescrizione_sede(descrizione_sede);
			bucapPreEdi.setPeriodo_archivio(periodo_archivio);
			bucapPreEdi.setDescrizione_periodo_archivio(descrizione_periodo_archivio);
			bucapPreEdi.setAnno_protocollo(anno_protocollo);
			bucapPreEdi.setId_scatola(id_scatola);
			bucapPreEdi.setProtocollo(protocollo);
			bucapPreEdi.setNumero_deposito(numero_deposito);
			bucapPreEdi.setNumero_civico(numero_civico);
			bucapPreEdi.setCommittente(committente);
			bucapPreEdi.setImpresa(impresa);
			bucapPreEdi.setNm_faldone(nm_faldone);
			bucapPreEdi.setNm_pratica(nm_pratica);
			bucapPreEdi.setMagazzino(magazzino);
			bucapPreEdi.setScaffale(scaffale);
			bucapPreEdi.setRipiano(ripiano);
			bucapPreEdi.setBuca(buca);
			bucapPreEdi.setFila(fila);
			bucapPreEdi.setImmagine_presente(immagine_presente);
			bucapPreEdi.setData_inserimento(data_inserimento);
			bucapPreEdi.setData_ultimo_rientro(data_ultimo_rientro);
			bucapPreEdi.setTipo_pratica(tipo_pratica);
			bucapPreEdi.setRichiedente(richiedente);
			bucapPreEdi.setDescrizione_richiedente(descrizione_richiedente);
			bucapPreEdi.setCod_via(cod_via);
			bucapPreEdi.setStatus(status);
			
			bucapPreEdi.setProvenienza( provenienza );
			bucapPreEdi.setDtInizioVal((DtIniVal)DwhUtils.getDataDwh(new DtIniVal(), dt_inizio_val));
			bucapPreEdi.setDtFineVal((DtFineVal)DwhUtils.getDataDwh(new DtFineVal(), dt_fine_val));
			bucapPreEdi.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			bucapPreEdi.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_inizio_dato));
			bucapPreEdi.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_dato));
			bucapPreEdi.setFlagDtValDato(flag_dt_val_dato);
			bucapPreEdi.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente.intValue()), ctx.getBelfiore());
			
			SitBucapPraEdiDao dao = (SitBucapPraEdiDao) DaoFactory.createDao(conn, bucapPreEdi, ctx.getEnteSorgenteById(fk_ente_sorgente.intValue()));
			
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

		return(new ApplicationAck("Record SIT_BUCAP_PRA_EDI inserito"));
	}

}
