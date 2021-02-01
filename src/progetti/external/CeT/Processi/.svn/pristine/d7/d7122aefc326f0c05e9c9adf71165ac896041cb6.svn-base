package it.webred.rulengine.brick.loadDwh.base;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

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
import it.webred.rulengine.dwh.Dao.SitUGasDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.table.SitComune;
import it.webred.rulengine.dwh.table.SitUGas;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class LoadSitUGas extends AbstractLoaderCommand implements Rule
{
	

	public LoadSitUGas(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	private static final Logger log = Logger.getLogger(LoadSitUGas.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitUGas.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
		throws CommandException
	{
		try {
			//List parametriIn = this.getParametersIn();
			int paramCount = Integer.parseInt(super._jrulecfg.getProperty("rengine.rule.number.params.in"));
			
			String id_orig = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.1.descr"));
			Integer fk_ente_sorgente = (Integer) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.2.descr"));
			String tipologiaFornitura = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.3.descr"));
			String annoRiferimento = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.4.descr"));

			String codiceCatastaleUtenza = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.5.descr"));
			String cfTitolareUtenza = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.6.descr"));
			String tipoSoggetto = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.7.descr"));
			String cognomeUtente = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.8.descr"));
			String nomeUtente = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.9.descr"));
			String sesso = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.10.descr"));
			String dataNascita = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.11.descr"));
			String descComuneNascita = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.12.descr"));
			String siglaProvNascita = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.13.descr"));
			String ragioneSociale = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.14.descr"));
			String identificativoUtenza = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.15.descr"));
			String tipoUtenza = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.16.descr"));
			String indirizzoUtenza = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.17.descr"));

			String capUtenza = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.18.descr"));
			String spesaConsumoNettoIva = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.19.descr"));
			String nMesiFatturazione = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.20.descr"));

			Timestamp dt_exp_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.21.descr"));
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.22.descr"));
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.23.descr"));
			Integer flag_dt_val_dato = (Integer) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.24.descr"));
			String provenienza = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.25.descr"));
			String cfErogante = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.26.descr"));
			String segnoAmmontFatturato = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.27.descr"));
			Integer ammontFatturato = (Integer) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.28.descr"));
			Integer consumoFatturato = (Integer) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.29.descr"));
			String esitoCtrlFormale = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.30.descr"));
			String esitoCtrlFormaleQual = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.31.descr"));
	
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("GAS - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("GAS - flag_dt_val_dato non valido");
			
			SitUGas gas = new SitUGas();
			gas.setProcessid(new ProcessId(ctx.getProcessID()));
			ChiaveOriginale co =  new ChiaveOriginale();
			co.setValore(id_orig);
			
			gas.setIdOrig(co);
			gas.setTipologiaFornitura(tipologiaFornitura);
			gas.setAnnoRiferimento(annoRiferimento);
			gas.setCodiceCatastaleUtenza(codiceCatastaleUtenza);
			gas.setCfTitolareUtenza(cfTitolareUtenza);
			gas.setTipoSoggetto(tipoSoggetto);
			gas.setCognomeUtente(cognomeUtente);
			gas.setNomeUtente(nomeUtente);
			gas.setSesso(sesso);
			gas.setDataNascita(dataNascita);
			gas.setDescComuneNascita(descComuneNascita);
			gas.setSiglaProvNascita(siglaProvNascita);
			gas.setRagioneSociale(ragioneSociale);
			gas.setIdentificativoUtenza(identificativoUtenza);
			gas.setTipoUtenza(tipoUtenza);
			gas.setIndirizzoUtenza(indirizzoUtenza);
			gas.setCapUtenza(capUtenza);
			gas.setSpesaConsumoNettoIva(spesaConsumoNettoIva);
			gas.setNMesiFatturazione(nMesiFatturazione);
			gas.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			gas.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			gas.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			gas.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			gas.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente), ctx.getBelfiore());
			gas.setProvenienza(provenienza);
			gas.setCfErogante(cfErogante);
			gas.setSegnoAmmontFatturato(segnoAmmontFatturato);
			gas.setAmmontFatturato(ammontFatturato != null && ammontFatturato.intValue() != -1 ? new BigDecimal(ammontFatturato) : null);
			gas.setConsumoFatturato(consumoFatturato != null && consumoFatturato.intValue() != -1 ? new BigDecimal(consumoFatturato) : null);
			gas.setEsitoCtrlFormale(esitoCtrlFormale);
			gas.setEsitoCtrlFormaleQual(esitoCtrlFormaleQual);
		
			SitUGasDao dao = (SitUGasDao) DaoFactory.createDao(conn,gas, ctx.getEnteSorgenteById(fk_ente_sorgente)); 

			@SuppressWarnings("unused")
			boolean salvato = dao.save(ctx.getBelfiore());
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitUGas",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		return(new ApplicationAck("Record SIT_U_GAS inserito"));

	}



}
