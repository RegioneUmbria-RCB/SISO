package it.webred.rulengine.brick.loadDwh.base;

import java.math.BigDecimal;


import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Properties;

import org.apache.log4j.Logger;

import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.Dao.DaoException;
import it.webred.rulengine.dwh.Dao.DaoFactory;
import it.webred.rulengine.dwh.Dao.SitBonBanDao;
import it.webred.rulengine.dwh.Dao.SitUGasDao;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtFineVal;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.DtIniVal;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.table.SitBonBan;
import it.webred.rulengine.dwh.table.SitUGas;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class LoadSitBonBan extends AbstractLoaderCommand implements Rule
{
	

	public LoadSitBonBan(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	private static final Logger log = Logger.getLogger(LoadSitBonBan.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitBonBan.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
		throws CommandException
	{
		try {
			//List parametriIn = this.getParametersIn();
			int paramCount = Integer.parseInt(super._jrulecfg.getProperty("rengine.rule.number.params.in"));
			
			String id_orig = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.1.descr"));
			BigDecimal fk_ente_sorgente = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.2.descr"));
			BigDecimal tipoRecord = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.3.descr"));
			String tipologiaFornitura = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.4.descr"));
			BigDecimal annoRiferimento = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.5.descr"));

			String codiceCatastaleBeneficiario = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.6.descr"));
			String codiceFiscaleBeneficiario = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.7.descr"));
			String esitoValidazioneCf = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.8.descr"));
			String abi = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.9.descr"));
			String cab = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.10.descr"));
			String numeroBonifico = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.11.descr"));
			Timestamp dataDisposizione = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.12.descr"));
			BigDecimal numeroSoggettiOrdinanti = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.13.descr"));
			String codiceFiscaleOrdinante = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.14.descr"));
			String codiceFiscaleAmministratore = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.15.descr"));
			String valuta = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.16.descr"));
			BigDecimal importoComplessivo = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.17.descr"));

			String normativaRiferimento = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.18.descr"));
			String fineRiga = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.19.descr"));
			
			String provenienza = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.20.descr"));
			Timestamp dt_inizio_val = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.21.descr"));
			Timestamp dt_fine_val = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.22.descr"));
			Timestamp dt_exp_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.23.descr"));
			Timestamp dt_inizio_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.24.descr"));
			Timestamp dt_fine_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.25.descr"));
			BigDecimal flag_dt_val_dato = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.26.descr"));
			
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("BONIFICI BANCARI - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("BONIFICI BANCARI - flag_dt_val_dato non valido");
			
			SitBonBan bonBan = new SitBonBan();
			bonBan.setProcessid(new ProcessId(ctx.getProcessID()));
			ChiaveOriginale co =  new ChiaveOriginale();
			co.setValore(id_orig);
			
			bonBan.setIdOrig(co);
			bonBan.setTipoRecord(tipoRecord != null && !tipoRecord.equals(-1) ? tipoRecord : null);
			bonBan.setTipologiaFornitura(tipologiaFornitura);
			bonBan.setAnnoRiferimento(annoRiferimento != null && !annoRiferimento.equals(-1) ? annoRiferimento : null);
			bonBan.setCodiceCatastaleBeneficiario(codiceCatastaleBeneficiario);
			bonBan.setCodiceFiscaleBeneficiario(codiceFiscaleBeneficiario);
			bonBan.setEsitoValidazioneCf(esitoValidazioneCf);
			bonBan.setAbi(abi);
			bonBan.setCab(cab);
			bonBan.setNumeroBonifico(numeroBonifico);
			bonBan.setDataDisposizione( DwhUtils.getDataDwh(new DataDwh(), dataDisposizione) );
			bonBan.setNumeroSoggettiOrdinanti(numeroSoggettiOrdinanti != null && !numeroSoggettiOrdinanti.equals(-1) ? numeroSoggettiOrdinanti : null);
			bonBan.setCodiceFiscaleOrdinante(codiceFiscaleOrdinante);
			bonBan.setCodiceFiscaleAmministratore(codiceFiscaleAmministratore);
			bonBan.setValuta(valuta);
			bonBan.setImportoComplessivo(importoComplessivo != null && !importoComplessivo.equals(-1) ? importoComplessivo : null);
			bonBan.setNormativaRiferimento(normativaRiferimento);
			bonBan.setFineRiga(fineRiga);

			bonBan.setProvenienza(provenienza);
			bonBan.setDtInizioVal( (DtIniVal)DwhUtils.getDataDwh(new DtIniVal(), dt_inizio_val));
			bonBan.setDtFineVal( (DtFineVal)DwhUtils.getDataDwh(new DtFineVal(), dt_fine_val));
			bonBan.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			bonBan.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_inizio_dato));
			bonBan.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_dato));
			bonBan.setFlagDtValDato( flag_dt_val_dato );
			bonBan.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente.intValue()), ctx.getBelfiore());

			SitBonBanDao dao = (SitBonBanDao) DaoFactory.createDao(conn, bonBan, ctx.getEnteSorgenteById(fk_ente_sorgente.intValue())); 

			@SuppressWarnings("unused")
			boolean salvato = dao.save(ctx.getBelfiore());
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitBonBan",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		return(new ApplicationAck("Record SIT_BON_BAN inserito"));

	}



}
