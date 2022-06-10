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
import it.webred.rulengine.dwh.Dao.SitSciaJpeDao;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtFineVal;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.DtIniVal;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.table.SitSciaJpe;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadSitSciaJpe extends AbstractLoaderCommand implements Rule {
	
	public LoadSitSciaJpe(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	private static final Logger log = Logger.getLogger(LoadSitSciaJpe.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitSciaJpe.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn) throws CommandException {
		
		try {			
			String id_orig = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.1.descr"));
			BigDecimal fk_ente_sorgente = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.2.descr"));
			
			String numero_pratica = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.3.descr"));
			String data_apertura = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.4.descr"));
			String data_chiusura = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.5.descr"));
			String macrotipo = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.6.descr"));
			String procedimento = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.7.descr"));
			String oggetto = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.8.descr"));
			String data_protocollo = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.9.descr"));
			String numero_protocollo = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.10.descr"));
			String referenti = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.11.descr"));
			String ubicazioni = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.12.descr"));
			String fascicolo = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.13.descr"));
			String dati_catastali_terreni = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.14.descr"));
			String dati_catastali_fabbricati = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.15.descr"));
			String tipo_opera = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.16.descr"));
			String responsabili = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.17.descr"));
			String tecnici = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.18.descr"));
			String situazione_pratica = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.19.descr"));
			String gg_istruttoria = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.20.descr"));
			String gg_prop_motiv = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.21.descr"));
			String gg_interruzione = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.22.descr"));
			String gg_sospensione = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.23.descr"));
			
			String provenienza = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.24.descr"));
			Timestamp dt_inizio_val = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.25.descr"));
			Timestamp dt_fine_val = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.26.descr"));
			Timestamp dt_exp_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.27.descr"));
			Timestamp dt_inizio_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.28.descr"));
			Timestamp dt_fine_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.29.descr"));
			BigDecimal flag_dt_val_dato = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.30.descr"));
			
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SCIA JPE PRATICHE EDILIZIE - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SCIA JPE PRATICHE EDILIZIE - flag_dt_val_dato non valido");
			
			SitSciaJpe sciaJpe = new SitSciaJpe();
			
			sciaJpe.setProcessid(new ProcessId(ctx.getProcessID()));
			
			ChiaveOriginale co =  new ChiaveOriginale();
			co.setValore(id_orig);			
			sciaJpe.setIdOrig(co);
			
			sciaJpe.setNumero_pratica(numero_pratica);
			sciaJpe.setData_apertura(data_apertura);
			sciaJpe.setData_chiusura(data_chiusura);
			sciaJpe.setMacrotipo(macrotipo);
			sciaJpe.setProcedimento(procedimento);
			sciaJpe.setOggetto(oggetto);
			sciaJpe.setData_protocollo(data_protocollo);
			sciaJpe.setNumero_protocollo(numero_protocollo);
			sciaJpe.setReferenti(referenti);
			sciaJpe.setUbicazioni(ubicazioni);
			sciaJpe.setFascicolo(fascicolo);
			sciaJpe.setDati_catastali_terreni(dati_catastali_terreni);
			sciaJpe.setDati_catastali_fabbricati(dati_catastali_fabbricati);
			sciaJpe.setTipo_opera(tipo_opera);
			sciaJpe.setResponsabili(responsabili);
			sciaJpe.setTecnici(tecnici);
			sciaJpe.setSituazione_pratica(situazione_pratica);
			sciaJpe.setGg_istruttoria(gg_istruttoria);
			sciaJpe.setGg_prop_motiv(gg_prop_motiv);
			sciaJpe.setGg_interruzione(gg_interruzione);
			sciaJpe.setGg_sospensione(gg_sospensione);
			
			sciaJpe.setProvenienza( provenienza );
			sciaJpe.setDtInizioVal((DtIniVal)DwhUtils.getDataDwh(new DtIniVal(), dt_inizio_val));
			sciaJpe.setDtFineVal((DtFineVal)DwhUtils.getDataDwh(new DtFineVal(), dt_fine_val));
			sciaJpe.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			sciaJpe.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_inizio_dato));
			sciaJpe.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_dato));
			sciaJpe.setFlagDtValDato(flag_dt_val_dato);
			sciaJpe.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente.intValue()), ctx.getBelfiore());
			
			SitSciaJpeDao dao = (SitSciaJpeDao) DaoFactory.createDao(conn, sciaJpe, ctx.getEnteSorgenteById(fk_ente_sorgente.intValue()));
			
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

		return(new ApplicationAck("Record SIT_SCIA_JPE inserito"));
	}

}
