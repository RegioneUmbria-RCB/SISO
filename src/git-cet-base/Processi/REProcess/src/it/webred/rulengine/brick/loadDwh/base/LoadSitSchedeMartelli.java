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
import it.webred.rulengine.dwh.Dao.SitSchedeMartelliDao;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtFineVal;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.DtIniVal;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.table.SitSchedeMartelli;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadSitSchedeMartelli extends AbstractLoaderCommand implements Rule {
	
	public LoadSitSchedeMartelli(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	private static final Logger log = Logger.getLogger(LoadSitSchedeMartelli.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitSchedeMartelli.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn) throws CommandException {
		
		try {			
			String id_orig = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.1.descr"));
			BigDecimal fk_ente_sorgente = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.2.descr"));
			
			String ds_serie_documentale = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.3.descr"));
			String fascicolo = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.4.descr"));
			String tipo = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.5.descr"));
			String toponimo = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.6.descr"));
			String civico = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.7.descr"));
			String descrizione = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.8.descr"));
			String classifica = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.9.descr"));
			String sequenziale = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.10.descr"));
			String multiplo = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.11.descr"));
			String nm_faldone = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.12.descr"));
			String nm_pratica = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.13.descr"));
			String nome_file = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.14.descr"));

			String provenienza = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.15.descr"));
			Timestamp dt_inizio_val = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.16.descr"));
			Timestamp dt_fine_val = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.17.descr"));
			Timestamp dt_exp_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.18.descr"));
			Timestamp dt_inizio_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.19.descr"));
			Timestamp dt_fine_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.20.descr"));
			BigDecimal flag_dt_val_dato = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.21.descr"));

			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SCHEDE MARTELLI - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SCHEDE MARTELLI - flag_dt_val_dato non valido");
			
			SitSchedeMartelli schmar = new SitSchedeMartelli();
			
			schmar.setProcessid(new ProcessId(ctx.getProcessID()));
			
			ChiaveOriginale co =  new ChiaveOriginale();
			co.setValore(id_orig);			
			schmar.setIdOrig(co);
			
			schmar.setDs_serie_documentale(ds_serie_documentale);
			schmar.setFascicolo(fascicolo);
			schmar.setTipo(tipo);
			//imf.setId(id);
			schmar.setToponimo(toponimo);
			schmar.setCivico(civico);
			schmar.setDescrizione(descrizione);
			schmar.setClassifica(classifica);
			schmar.setSequenziale(sequenziale);
			schmar.setMultiplo(multiplo);
			schmar.setNm_faldone(nm_faldone);
			schmar.setNm_pratica(nm_pratica);
			schmar.setNome_file(nome_file);
			
			schmar.setProvenienza( provenienza );
			schmar.setDtInizioVal((DtIniVal)DwhUtils.getDataDwh(new DtIniVal(), dt_inizio_val));
			schmar.setDtFineVal((DtFineVal)DwhUtils.getDataDwh(new DtFineVal(), dt_fine_val));
			schmar.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			schmar.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_inizio_dato));
			schmar.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_dato));
			schmar.setFlagDtValDato(flag_dt_val_dato);
			schmar.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente.intValue()), ctx.getBelfiore());
			
			SitSchedeMartelliDao dao = (SitSchedeMartelliDao) DaoFactory.createDao(conn, schmar, ctx.getEnteSorgenteById(fk_ente_sorgente.intValue()));
			
			@SuppressWarnings("unused")			
			boolean salvato = dao.save(ctx.getBelfiore());
			
		} catch (DaoException e) {
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e) {
			log.error("Clazz: " + LoadSitSchedeMartelli.class.getName() ,e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		return(new ApplicationAck("Record SIT_SCHEDE_MARTELLI inserito"));
	}

}
