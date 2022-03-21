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
import it.webred.rulengine.dwh.Dao.SitCuritDao;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtFineVal;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.DtIniVal;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.table.SitCurit;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadSitCurit extends AbstractLoaderCommand implements Rule {
	
	public LoadSitCurit(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	private static final Logger log = Logger.getLogger(LoadSitCurit.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitCurit.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn) throws CommandException {
		
		try {			
			String id_orig = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.1.descr"));
			BigDecimal fk_ente_sorgente = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.2.descr"));
			String identificativoImpianto = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.3.descr"));
			BigDecimal generatoriNumero = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.4.descr"));
			BigDecimal potenzaImpiantoRisc = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.5.descr"));
			BigDecimal potenzaImpiantoAcs = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.6.descr"));
			BigDecimal potenzaImpiantoRaff = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.7.descr"));
			String ubicazioneToponimo = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.8.descr"));
			String ubicazioneIndirizzo = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.9.descr"));
			String ubicazioneCivico = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.10.descr"));
			String ubicazioneComune = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.11.descr"));
			String ubicazioneProvincia = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.12.descr"));
			String ubicazioneCap = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.13.descr"));
			String ubicazioneCodiceIstat = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.14.descr"));
			String catastoSezione = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.15.descr"));
			String catastoFoglio = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.16.descr"));
			String catastoParticella = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.17.descr"));
			String catastoSubalterno = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.18.descr"));
			String edificioCategoria = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.19.descr"));
			BigDecimal volumetriaRisc = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.20.descr"));
			BigDecimal volumetriaRaff = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.21.descr"));
			String regolazione = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.22.descr"));
			String contabilizzazione = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.23.descr"));
			String emissione = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.24.descr"));
			String apePresenza = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.25.descr"));
			String apeCodice = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.26.descr"));
			String generatoreCategoria = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.27.descr"));
			String generatoreProgressivo = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.28.descr"));
			BigDecimal generatorePotenza = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.29.descr"));
			String generatoreServizi = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.30.descr"));
			String generatoreFabbricante = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.31.descr"));
			String generatoreTipologia = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.32.descr"));
			String generatoreCombustibile = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.33.descr"));
			Timestamp generatoreDataInst = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.34.descr"));
			BigDecimal generatoreAnnoInst = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.35.descr"));
			String generatoreTecnologia = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.36.descr"));
			BigDecimal generatoreRendNom = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.37.descr"));
			Timestamp rapportoDiControlloData = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.38.descr"));
			BigDecimal rapportoDiControlloAnno = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.39.descr"));
			String rapDiControlloEsito = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.40.descr"));
			BigDecimal rapDiControlloRendimento = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.41.descr"));
			String rapDiControlloBacharach = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.42.descr"));
			String ispezioneAutComp = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.43.descr"));
			Timestamp ispezioneData = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.44.descr"));
			String ispezioneEsito = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.45.descr"));
			Timestamp dtFileCsv = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.46.descr"));
			String provenienza = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.47.descr"));
			Timestamp dt_inizio_val = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.48.descr"));
			Timestamp dt_fine_val = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.49.descr"));
			Timestamp dt_exp_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.50.descr"));
			Timestamp dt_inizio_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.51.descr"));
			Timestamp dt_fine_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.52.descr"));
			BigDecimal flag_dt_val_dato = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.53.descr"));
			
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("CURIT - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("CURIT - flag_dt_val_dato non valido");
			
			SitCurit curit = new SitCurit();
			
			curit.setProcessid(new ProcessId(ctx.getProcessID()));
			
			ChiaveOriginale co =  new ChiaveOriginale();
			co.setValore(id_orig);			
			curit.setIdOrig(co);
			
			curit.setIdentificativoImpianto(identificativoImpianto);			
			curit.setGeneratoriNumero(generatoriNumero);
			curit.setPotenzaImpiantoRisc(potenzaImpiantoRisc);
			curit.setPotenzaImpiantoAcs(potenzaImpiantoAcs);
			curit.setPotenzaImpiantoRaff(potenzaImpiantoRaff);
			curit.setUbicazioneToponimo(ubicazioneToponimo);
			curit.setUbicazioneIndirizzo(ubicazioneIndirizzo);
			curit.setUbicazioneCivico(ubicazioneCivico);
			curit.setUbicazioneComune(ubicazioneComune);
			curit.setUbicazioneProvincia(ubicazioneProvincia);
			curit.setUbicazioneCap(ubicazioneCap);
			curit.setUbicazioneCodiceIstat(ubicazioneCodiceIstat);
			curit.setCatastoSezione(catastoSezione);
			curit.setCatastoFoglio(catastoFoglio);
			curit.setCatastoParticella(catastoParticella);
			curit.setCatastoSubalterno(catastoSubalterno);
			curit.setEdificioCategoria(edificioCategoria);
			curit.setVolumetriaRisc(volumetriaRisc);
			curit.setVolumetriaRaff(volumetriaRaff);
			curit.setRegolazione(regolazione);
			curit.setContabilizzazione(contabilizzazione);
			curit.setEmissione(emissione);
			curit.setApePresenza(apePresenza);
			curit.setApeCodice(apeCodice);
			curit.setGeneratoreCategoria(generatoreCategoria);
			curit.setGeneratoreProgressivo(generatoreProgressivo);
			curit.setGeneratorePotenza(generatorePotenza);
			curit.setGeneratoreServizi(generatoreServizi);
			curit.setGeneratoreFabbricante(generatoreFabbricante);
			curit.setGeneratoreTipologia(generatoreTipologia);
			curit.setGeneratoreCombustibile(generatoreCombustibile);			
			curit.setGeneratoreDataInst(DwhUtils.getDataDwh(new DataDwh(), generatoreDataInst));
			curit.setGeneratoreAnnoInst(generatoreAnnoInst);
			curit.setGeneratoreTecnologia(generatoreTecnologia);
			curit.setGeneratoreRendNom(generatoreRendNom);
			curit.setRapportoDiControlloData(DwhUtils.getDataDwh(new DataDwh(), rapportoDiControlloData));
			curit.setRapportoDiControlloAnno(rapportoDiControlloAnno);
			curit.setRapDiControlloEsito(rapDiControlloEsito);
			curit.setRapDiControlloRendimento(rapDiControlloRendimento);
			curit.setRapDiControlloBacharach(rapDiControlloBacharach);
			curit.setIspezioneAutComp(ispezioneAutComp);
			curit.setIspezioneData(DwhUtils.getDataDwh(new DataDwh(), ispezioneData));
			curit.setIspezioneEsito(ispezioneEsito);
			curit.setDtFileCsv(DwhUtils.getDataDwh(new DataDwh(), dtFileCsv));
			curit.setProvenienza(provenienza);
			curit.setDtInizioVal((DtIniVal)DwhUtils.getDataDwh(new DtIniVal(), dt_inizio_val));
			curit.setDtFineVal((DtFineVal)DwhUtils.getDataDwh(new DtFineVal(), dt_fine_val));
			curit.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			curit.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_inizio_dato));
			curit.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_dato));
			curit.setFlagDtValDato(flag_dt_val_dato);
			curit.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente.intValue()), ctx.getBelfiore());
			
			SitCuritDao dao = (SitCuritDao) DaoFactory.createDao(conn, curit, ctx.getEnteSorgenteById(fk_ente_sorgente.intValue()));
			
			@SuppressWarnings("unused")			
			boolean salvato = dao.save(ctx.getBelfiore());
			
		} catch (DaoException e) {
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e) {
			log.error("LoadSitCurit",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		return(new ApplicationAck("Record SIT_CURIT inserito"));
	}

}
