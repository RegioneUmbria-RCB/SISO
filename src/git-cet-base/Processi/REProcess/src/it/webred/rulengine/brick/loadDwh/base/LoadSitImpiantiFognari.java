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
import it.webred.rulengine.dwh.Dao.SitImpiantiFognariDao;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtFineVal;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.DtIniVal;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.table.SitImpiantiFognari;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadSitImpiantiFognari extends AbstractLoaderCommand implements Rule {
	
	public LoadSitImpiantiFognari(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	private static final Logger log = Logger.getLogger(LoadSitImpiantiFognari.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitImpiantiFognari.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn) throws CommandException {
		
		try {			
			String id_orig = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.1.descr"));
			BigDecimal fk_ente_sorgente = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.2.descr"));
			
			String ds_serie_documentale = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.3.descr"));
			String tipo_documento = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.4.descr"));
			String descrizione_tipo_documento = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.5.descr"));
			String id = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.6.descr"));
			String numero_busta = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.7.descr"));
			String tipo_busta = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.8.descr"));
			String tipo_via = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.9.descr"));
			String via = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.10.descr"));
			String civico = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.11.descr"));
			String barrato = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.12.descr"));
			String codice_via = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.13.descr"));
			String zona = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.14.descr"));
			String annotazioni = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.15.descr"));
			String nm_faldone = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.16.descr"));
			String nm_pratica = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.17.descr"));
			String nome_file = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.18.descr"));

			String provenienza = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.19.descr"));
			Timestamp dt_inizio_val = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.20.descr"));
			Timestamp dt_fine_val = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.21.descr"));
			Timestamp dt_exp_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.22.descr"));
			Timestamp dt_inizio_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.23.descr"));
			Timestamp dt_fine_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.24.descr"));
			BigDecimal flag_dt_val_dato = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.25.descr"));
			
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("IMPIANTI FOGNARI IMF - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("IMPIANTI FOGNARI IMF - flag_dt_val_dato non valido");
			
			SitImpiantiFognari imf = new SitImpiantiFognari();
			
			imf.setProcessid(new ProcessId(ctx.getProcessID()));
			
			ChiaveOriginale co =  new ChiaveOriginale();
			co.setValore(id_orig);			
			imf.setIdOrig(co);
			
			imf.setDs_serie_documentale(ds_serie_documentale);
			imf.setTipo_documento(tipo_documento);
			imf.setDescrizione_tipo_documento(descrizione_tipo_documento);
			//imf.setId(id);
			imf.setNumero_busta( numero_busta );
			imf.setTipo_busta( tipo_busta );
			imf.setTipo_via(tipo_via);
			imf.setVia(via);
			imf.setCivico(civico);
			imf.setBarrato(barrato);
			imf.setCodice_via(codice_via);
			imf.setZona(zona);
			imf.setAnnotazioni(annotazioni);
			imf.setNm_faldone(nm_faldone);
			imf.setNm_pratica(nm_pratica);
			imf.setNome_file(nome_file);
			
			imf.setProvenienza( provenienza );
			imf.setDtInizioVal((DtIniVal)DwhUtils.getDataDwh(new DtIniVal(), dt_inizio_val));
			imf.setDtFineVal((DtFineVal)DwhUtils.getDataDwh(new DtFineVal(), dt_fine_val));
			imf.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			imf.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_inizio_dato));
			imf.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_dato));
			imf.setFlagDtValDato(flag_dt_val_dato);
			imf.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente.intValue()), ctx.getBelfiore());
			
			SitImpiantiFognariDao dao = (SitImpiantiFognariDao) DaoFactory.createDao(conn, imf, ctx.getEnteSorgenteById(fk_ente_sorgente.intValue()));
			
			@SuppressWarnings("unused")			
			boolean salvato = dao.save(ctx.getBelfiore());
			
		} catch (DaoException e) {
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e) {
			log.error("Clazz: " + LoadSitImpiantiFognari.class.getName() ,e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		return(new ApplicationAck("Record SIT_IMPIANTI_SFOGNARI inserito"));
	}

}
