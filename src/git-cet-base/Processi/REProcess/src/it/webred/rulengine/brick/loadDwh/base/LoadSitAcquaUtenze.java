package it.webred.rulengine.brick.loadDwh.base;

import java.math.BigDecimal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;


import it.webred.rulengine.Context;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.Dao.DaoException;
import it.webred.rulengine.dwh.Dao.DaoFactory;
import it.webred.rulengine.dwh.Dao.SitAcquaUtenzeDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.SitAcquaUtenze;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.StringUtils;

public class LoadSitAcquaUtenze extends AbstractLoaderCommand
{
	public LoadSitAcquaUtenze(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadSitAcquaUtenze.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitAcquaUtenze.class;
	}



	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
		throws CommandException
	{
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMyyyy");
		
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
			
			String codServizio = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
			String descrCategoria = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
			String qualificaTitolare = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
			String tipologia = (String) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
			String tipoContratto = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			String dtUtenza = (String) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			String ragSocUbicazione = (String) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
			String viaUbicazione = (String) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
			String civicoUbicazione = (String) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());
			String capUbicazione = (String) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
			String comuneUbicazione = (String) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
			String tipologiaUi = (String) ctx.get(((RRuleParamIn) parametriIn.get(13)).getDescr());
			BigDecimal mesiFatturazione = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(14)).getDescr());
			BigDecimal consumoMedio = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(15)).getDescr());
			BigDecimal stacco = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(16)).getDescr());
			BigDecimal giro = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(17)).getDescr());
			BigDecimal fatturato = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(18)).getDescr());
			
			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(19)).getDescr());
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(20)).getDescr());
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(21)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(22)).getDescr());

			//campi per hash id esterna
			String cognome = (String) ctx.get(((RRuleParamIn) parametriIn.get(23)).getDescr());
			String nome = (String) ctx.get(((RRuleParamIn) parametriIn.get(24)).getDescr());
			String codFiscale = (String) ctx.get(((RRuleParamIn) parametriIn.get(25)).getDescr());
			String denominazione = (String) ctx.get(((RRuleParamIn) parametriIn.get(26)).getDescr());
			String partIva = (String) ctx.get(((RRuleParamIn) parametriIn.get(27)).getDescr());
			String viaResidenza = (String) ctx.get(((RRuleParamIn) parametriIn.get(28)).getDescr());
			String civicoResidenza = (String) ctx.get(((RRuleParamIn) parametriIn.get(29)).getDescr());
			
			if(id_orig==null || fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_ACQUA_UTENZE - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_ACQUA_UTENZE - flag_dt_val_dato non valido");
			SitAcquaUtenze tab = (SitAcquaUtenze)getTabellaDwhInstance(ctx);
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);
			
			tab.setIdOrig(chiaveOriginale);
				
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente), ctx.getBelfiore());
			
			tab.setCodServizio(codServizio);
			tab.setDescrCategoria(descrCategoria);
			tab.setQualificaTitolare(qualificaTitolare);
			tab.setTipologia(tipologia);
			tab.setTipoContratto(tipoContratto);
			if(dtUtenza != null){
				try{
				tab.setDtUtenza(DwhUtils.getDataDwh(new DataDwh(),new Timestamp(sdf.parse(dtUtenza).getTime())));
				} catch (ParseException e) {
					tab.setDtUtenza(DwhUtils.getDataDwh(new DataDwh(),new Timestamp(sdf2.parse(dtUtenza).getTime())));
				}
			}
			tab.setRagSocUbicazione(ragSocUbicazione);
			tab.setViaUbicazione(viaUbicazione);
			tab.setCivicoUbicazione(civicoUbicazione);
			tab.setCapUbicazione(capUbicazione);
			tab.setComuneUbicazione(comuneUbicazione);
			tab.setTipologiaUi(tipologiaUi);
			if(mesiFatturazione != null);
				tab.setMesiFatturazione(mesiFatturazione);
			if(consumoMedio != null);
				tab.setConsumoMedio(consumoMedio);
			if(stacco != null);
				tab.setStacco(stacco);
			if(giro != null);
				tab.setGiro(giro);
			if(fatturato != null);
				tab.setFatturato(fatturato);
			
			//valore idutente: creazione hash
			String valueForHash = (cognome!=null?cognome:"") +
					(nome!=null?nome:"")+
					 (codFiscale!=null?codFiscale:"")+
					 (denominazione!=null?denominazione:"")+
					 (partIva!=null?partIva:"")+
					 (viaResidenza!=null?viaResidenza:"")+
					 (civicoResidenza!=null?civicoResidenza:"");
			
			if (valueForHash!=null) {
				MessageDigest md = null;
				try
				{md = MessageDigest.getInstance("SHA");}
				catch (NoSuchAlgorithmException e){
					log.error(e);
				}
				
					md.update(valueForHash.getBytes());
					byte[] b = md.digest();
					String hash = new String(StringUtils.toHexString(b));
				
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(hash);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtUtente(ce);
			}
			
			SitAcquaUtenzeDao dao = (SitAcquaUtenzeDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			
			boolean salvato = dao.save(ctx.getBelfiore());
			
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitAcquaUtenze",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		return(new ApplicationAck("Record SIT_ACQUA_UTENZE inserito"));

	}



}
