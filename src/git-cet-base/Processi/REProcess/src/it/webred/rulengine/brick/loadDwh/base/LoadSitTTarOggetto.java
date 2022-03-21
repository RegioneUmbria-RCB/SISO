package it.webred.rulengine.brick.loadDwh.base;

import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.Dao.DaoException;
import it.webred.rulengine.dwh.Dao.DaoFactory;
import it.webred.rulengine.dwh.Dao.SitTTarContribDao;
import it.webred.rulengine.dwh.Dao.SitTTarDichDao;
import it.webred.rulengine.dwh.Dao.SitTTarOggettoDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.table.SitTTarContrib;
import it.webred.rulengine.dwh.table.SitTTarDich;
import it.webred.rulengine.dwh.table.SitTTarOggetto;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadSitTTarOggetto extends AbstractLoaderCommand implements Rule
{
	public LoadSitTTarOggetto(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadSitTTarOggetto.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitTTarOggetto.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
	throws CommandException
	{
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			
			String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
			String id_orig_sogg_cnt = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
			String id_orig_sogg_dich = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
			String des_cls_rsu = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
			String sez = (String) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
			String foglio = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			String numero = (String) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			String sub = (String) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());			
			BigDecimal sup_tot = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
			Timestamp dat_ini = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());
			Timestamp dat_fin = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
			String tip_ogg = (String) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
			String des_tip_ogg = (String) ctx.get(((RRuleParamIn) parametriIn.get(13)).getDescr());
			String des_ind = (String) ctx.get(((RRuleParamIn) parametriIn.get(14)).getDescr());
			String id_orig_via = (String) ctx.get(((RRuleParamIn) parametriIn.get(15)).getDescr());
			String num_civ = (String) ctx.get(((RRuleParamIn) parametriIn.get(16)).getDescr());
			String esp_civ = (String) ctx.get(((RRuleParamIn) parametriIn.get(17)).getDescr());
			String scala = (String) ctx.get(((RRuleParamIn) parametriIn.get(18)).getDescr());
			String piano = (String) ctx.get(((RRuleParamIn) parametriIn.get(19)).getDescr());
			String interno = (String) ctx.get(((RRuleParamIn) parametriIn.get(20)).getDescr());	
			Timestamp tms_agg = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(21)).getDescr());
			Timestamp tms_bon = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(22)).getDescr());
			BigDecimal n_comp_fam = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(23)).getDescr());
			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(24)).getDescr());
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(25)).getDescr());
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(26)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(27)).getDescr());
			String provenienza = (String) ctx.get(((RRuleParamIn) parametriIn.get(28)).getDescr());
	
			//if(id_orig==null || fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_T_TAR_OGGETTO - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_T_TAR_OGGETTO - flag_dt_val_dato non valido");
			
			SitTTarOggetto tab = new SitTTarOggetto();
			tab.putHashValue(SitTTarOggetto.HASH_ID_ORIG_KEY, id_orig);
			id_orig = null;
			tab.setProcessid(new ProcessId(ctx.getProcessID()));
			
			ChiaveOriginale chiaveOriginale = new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);			
			tab.setIdOrig(chiaveOriginale);
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());			
			tab.setDesClsRsu(des_cls_rsu);
			tab.setSez(sez);
			tab.setFoglio(foglio);
			tab.setNumero(numero);
			tab.setSub(sub);		
			tab.setSupTot(sup_tot);
			tab.setDatIni(DwhUtils.getDataDwh(new DataDwh(), dat_ini));
			tab.setDatFin(DwhUtils.getDataDwh(new DataDwh(), dat_fin));
			tab.setTipOgg(tip_ogg);
			tab.setDesTipOgg(des_tip_ogg);
			tab.setDesInd(des_ind);
			if (id_orig_via != null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_via);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(provenienza, co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtVia(ce);
			}			
			tab.setNumCiv(num_civ);
			tab.setEspCiv(esp_civ);
			tab.setScala(scala);
			tab.setPiano(piano);
			tab.setInterno(interno);
			tab.setTmsAgg(DwhUtils.getDataDwh(new DataDwh(), tms_agg));
			tab.setTmsBon(DwhUtils.getDataDwh(new DataDwh(), tms_bon));	
			tab.setNCompFam(n_comp_fam);
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			tab.setProvenienza(provenienza);

			SitTTarOggettoDao dao = (SitTTarOggettoDao) DaoFactory.createDao(conn, tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());
			
			if (id_orig_sogg_cnt != null && !id_orig_sogg_cnt.trim().equals("")) {
				
				//INSERIMENTO IN SIT_T_TAR_CONTRIB
				SitTTarContrib cnt = (SitTTarContrib)getTabellaDwhInstanceSitTTarContrib(ctx);
				
				cnt.setIdOrig(null);
				cnt.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
				cnt.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(), dt_exp_dato));
				cnt.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
				cnt.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
				cnt.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
				cnt.setProvenienza(provenienza);
				
				/*ChiaveOriginale co1 = new ChiaveOriginale();
				co1.setValore(id_orig);
				
				ChiaveEsterna ce1 = new ChiaveEsterna();
				ce1.setValore(provenienza, co1, DwhUtils.getIdentificativo(fk_ente_sorgente));
				cnt.setIdExtOggRsu(ce1);*/
				cnt.setIdExtOggRsu(tab.getIdExt());
				
				ChiaveOriginale co2 = new ChiaveOriginale();
				co2.setValore(id_orig_sogg_cnt);
				
				ChiaveEsterna ce2 = new ChiaveEsterna();
				ce2.setValore(provenienza, co2, DwhUtils.getIdentificativo(fk_ente_sorgente));
				cnt.setIdExtSogg(ce2);
				
				SitTTarContribDao cntDao = (SitTTarContribDao) DaoFactory.createDao(conn, cnt, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
				
				salvato = cntDao.save(ctx.getBelfiore());
			}
			
			if (id_orig_sogg_dich != null && !id_orig_sogg_dich.trim().equals("")) {
				
				//INSERIMENTO IN SIT_T_TAR_DICH
				SitTTarDich dich = (SitTTarDich)getTabellaDwhInstanceSitTTarDich(ctx);
				
				dich.setIdOrig(null);
				dich.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
				dich.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(), dt_exp_dato));
				dich.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
				dich.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
				dich.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
				dich.setProvenienza(provenienza);
				
				/*ChiaveOriginale co1 = new ChiaveOriginale();
				co1.setValore(id_orig);
				
				ChiaveEsterna ce1 = new ChiaveEsterna();
				ce1.setValore(provenienza, co1, DwhUtils.getIdentificativo(fk_ente_sorgente));
				dich.setIdExtOggRsu(ce1);*/
				dich.setIdExtOggRsu(tab.getIdExt());
				
				ChiaveOriginale co2 = new ChiaveOriginale();
				co2.setValore(id_orig_sogg_dich);
				
				ChiaveEsterna ce2 = new ChiaveEsterna();
				ce2.setValore(provenienza, co2, DwhUtils.getIdentificativo(fk_ente_sorgente));
				dich.setIdExtSogg(ce2);
				
				SitTTarDichDao dichDao = (SitTTarDichDao) DaoFactory.createDao(conn, dich, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
				
				salvato = dichDao.save(ctx.getBelfiore());
			}
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitTTarOggetto",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	
		return(new ApplicationAck("Record SIT_T_TAR_OGGETTO inserito"));
	
	}
	
	private TabellaDwh getTabellaDwhInstanceSitTTarContrib(Context ctx) throws Exception {
		TabellaDwh retVal = SitTTarContrib.class.newInstance();
		ProcessId processId = new ProcessId(ctx.getProcessID());
		retVal.setProcessid(processId);
		return retVal;
	}
	
	private TabellaDwh getTabellaDwhInstanceSitTTarDich(Context ctx) throws Exception {
		TabellaDwh retVal = SitTTarDich.class.newInstance();
		ProcessId processId = new ProcessId(ctx.getProcessID());
		retVal.setProcessid(processId);
		return retVal;
	}

}
