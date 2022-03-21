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
import it.webred.rulengine.dwh.Dao.SitTTarOggUltsoggDao;
import it.webred.rulengine.dwh.Dao.SitTTarSoggDao;
import it.webred.rulengine.dwh.Dao.SitTTarUltSoggDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.table.SitTTarContrib;
import it.webred.rulengine.dwh.table.SitTTarDich;
import it.webred.rulengine.dwh.table.SitTTarOggUltsogg;
import it.webred.rulengine.dwh.table.SitTTarSogg;
import it.webred.rulengine.dwh.table.SitTTarUltSogg;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadSitTTarUltSogg extends AbstractLoaderCommand implements Rule
{
	public LoadSitTTarUltSogg(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadSitTTarUltSogg.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitTTarUltSogg.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
	throws CommandException
	{
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);		
			
			String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());			
			String id_orig_ogg_rsu = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
			String id_orig_sogg = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
			String tit_sogg = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
			String flag_contribuente = (String) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
			String flag_dichiarante = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			String cod_fisc = (String) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			String part_iva = (String) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
			String cog_denom = (String) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());			
			String nome = (String) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());
			String sesso = (String) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
			String tip_sogg = (String) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
			Timestamp dt_nsc = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(13)).getDescr());
			String cod_ist_cmn_nsc = (String) ctx.get(((RRuleParamIn) parametriIn.get(14)).getDescr());
			String cod_blfr_cmn_nsc = (String) ctx.get(((RRuleParamIn) parametriIn.get(15)).getDescr());
			String cod_cmn_nsc = (String) ctx.get(((RRuleParamIn) parametriIn.get(16)).getDescr());
			String des_com_nsc = (String) ctx.get(((RRuleParamIn) parametriIn.get(17)).getDescr());
			String cap_com_nsc = (String) ctx.get(((RRuleParamIn) parametriIn.get(18)).getDescr());
			String sigla_prov_nsc = (String) ctx.get(((RRuleParamIn) parametriIn.get(19)).getDescr());
			String des_prov_nsc = (String) ctx.get(((RRuleParamIn) parametriIn.get(20)).getDescr());
			String cod_stato_nsc = (String) ctx.get(((RRuleParamIn) parametriIn.get(21)).getDescr());
			String des_stato_nsc = (String) ctx.get(((RRuleParamIn) parametriIn.get(22)).getDescr());			
			String cod_ist_cmn_res = (String) ctx.get(((RRuleParamIn) parametriIn.get(23)).getDescr());
			String cod_blfr_cmn_res = (String) ctx.get(((RRuleParamIn) parametriIn.get(24)).getDescr());
			String cod_cmn_res = (String) ctx.get(((RRuleParamIn) parametriIn.get(25)).getDescr());
			String des_com_res = (String) ctx.get(((RRuleParamIn) parametriIn.get(26)).getDescr());
			String cap_com_res = (String) ctx.get(((RRuleParamIn) parametriIn.get(27)).getDescr());
			String sigla_prov_res = (String) ctx.get(((RRuleParamIn) parametriIn.get(28)).getDescr());
			String des_prov_res = (String) ctx.get(((RRuleParamIn) parametriIn.get(29)).getDescr());
			String cod_stato_res = (String) ctx.get(((RRuleParamIn) parametriIn.get(30)).getDescr());
			String des_stato_res = (String) ctx.get(((RRuleParamIn) parametriIn.get(31)).getDescr());			
			String des_ind = (String) ctx.get(((RRuleParamIn) parametriIn.get(32)).getDescr());
			String id_orig_via = (String) ctx.get(((RRuleParamIn) parametriIn.get(33)).getDescr());
			String num_civ = (String) ctx.get(((RRuleParamIn) parametriIn.get(34)).getDescr());
			String esp_civ = (String) ctx.get(((RRuleParamIn) parametriIn.get(35)).getDescr());
			String scala = (String) ctx.get(((RRuleParamIn) parametriIn.get(36)).getDescr());
			String piano = (String) ctx.get(((RRuleParamIn) parametriIn.get(37)).getDescr());
			String interno = (String) ctx.get(((RRuleParamIn) parametriIn.get(38)).getDescr());
			String ind_res_ext = (String) ctx.get(((RRuleParamIn) parametriIn.get(39)).getDescr());
			String num_civ_ext = (String) ctx.get(((RRuleParamIn) parametriIn.get(40)).getDescr());
			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(41)).getDescr());
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(42)).getDescr());
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(43)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(44)).getDescr());
			String provenienza = (String) ctx.get(((RRuleParamIn) parametriIn.get(45)).getDescr());

			//n.b. in questo caso id_orig è null, ed è gestito in seguito
			if(/*id_orig==null || */fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_T_TAR_ULT_SOGG - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_T_TAR_ULT_SOGG - flag_dt_val_dato non valido");
			
			//inserimento dei dati del soggetto
			SitTTarSogg sogg = new SitTTarSogg();			
			sogg.setProcessid(new ProcessId(ctx.getProcessID()));
			
			ChiaveOriginale chiaveOriginale = new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig_sogg);			
			sogg.setIdOrig(chiaveOriginale);
			sogg.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			sogg.setIdOrigSoggU(null);
			sogg.setCodFisc(cod_fisc);
			sogg.setPartIva(part_iva);
			sogg.setCogDenom(cog_denom);
			sogg.setNome(nome);
			sogg.setSesso(sesso);
			sogg.setTipSogg(tip_sogg);
			sogg.setDtNsc(DwhUtils.getDataDwh(new DataDwh(), dt_nsc));
			sogg.setCodIstCmnNsc(cod_ist_cmn_nsc);
			sogg.setCodBlfrCmnNsc(cod_blfr_cmn_nsc);
			sogg.setCodCmnNsc(cod_cmn_nsc);
			sogg.setDesComNsc(des_com_nsc);
			sogg.setCapComNsc(cap_com_nsc);
			sogg.setSiglaProvNsc(sigla_prov_nsc);
			sogg.setDesProvNsc(des_prov_nsc);
			sogg.setCodStatoNsc(cod_stato_nsc);
			sogg.setDesStatoNsc(des_stato_nsc);			
			sogg.setCodIstCmnRes(cod_ist_cmn_res);
			sogg.setCodBlfrCmnRes(cod_blfr_cmn_res);
			sogg.setCodCmnRes(cod_cmn_res);
			sogg.setDesComRes(des_com_res);
			sogg.setCapComRes(cap_com_res);
			sogg.setSiglaProvRes(sigla_prov_res);
			sogg.setDesProvRes(des_prov_res);
			sogg.setCodStatoRes(cod_stato_res);
			sogg.setDesStatoRes(des_stato_res);
			sogg.setDesIndRes(des_ind);
			if (id_orig_via != null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_via);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(provenienza, co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				sogg.setIdExtViaRes(ce);
			}
			sogg.setNumCivRes(num_civ);
			sogg.setEspCivRes(esp_civ);
			sogg.setScalaRes(scala);
			sogg.setPianoRes(piano);
			sogg.setInternoRes(interno);			
			sogg.setIndResExt(ind_res_ext);
			sogg.setNumCivExt(num_civ_ext);			
			sogg.setTmsAgg(DwhUtils.getDataDwh(new DataDwh(), null));
			sogg.setFlgTrf(null);
			sogg.setTmsBon(DwhUtils.getDataDwh(new DataDwh(), null));
			sogg.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			sogg.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			sogg.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			sogg.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			sogg.setProvenienza(provenienza);
			
			SitTTarSoggDao soggDao = (SitTTarSoggDao) DaoFactory.createDao(conn, sogg, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = soggDao.save(ctx.getBelfiore());
			
			if (flag_contribuente != null && flag_contribuente.equalsIgnoreCase("S")) {
				
				//INSERIMENTO IN SIT_T_TAR_CONTRIB
				SitTTarContrib cnt = (SitTTarContrib)getTabellaDwhInstanceSitTTarContrib(ctx);
				
				cnt.setIdOrig(null);
				cnt.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente), ctx.getBelfiore());
				cnt.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(), dt_exp_dato));
				cnt.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
				cnt.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
				cnt.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
				cnt.setProvenienza(provenienza);
				
				ChiaveOriginale co1 = new ChiaveOriginale();
				co1.setValore(id_orig_ogg_rsu);
				
				ChiaveEsterna ce1 = new ChiaveEsterna();
				ce1.setValore(provenienza, co1, DwhUtils.getIdentificativo(fk_ente_sorgente));
				cnt.setIdExtOggRsu(ce1);
				
				ChiaveOriginale co2 = new ChiaveOriginale();
				co2.setValore(id_orig_sogg);
				
				ChiaveEsterna ce2 = new ChiaveEsterna();
				ce2.setValore(provenienza, co2, DwhUtils.getIdentificativo(fk_ente_sorgente));
				cnt.setIdExtSogg(ce2);
				
				SitTTarContribDao cntDao = (SitTTarContribDao) DaoFactory.createDao(conn, cnt, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
				
				salvato = cntDao.save(ctx.getBelfiore());				
			} else if (flag_dichiarante != null && flag_dichiarante.equalsIgnoreCase("S")) {
				
				//INSERIMENTO IN SIT_T_TAR_DICH
				SitTTarDich dich = (SitTTarDich)getTabellaDwhInstanceSitTTarDich(ctx);
				
				dich.setIdOrig(null);
				dich.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente), ctx.getBelfiore());
				dich.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(), dt_exp_dato));
				dich.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
				dich.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
				dich.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
				dich.setProvenienza(provenienza);
				
				ChiaveOriginale co1 = new ChiaveOriginale();
				co1.setValore(id_orig_ogg_rsu);
				
				ChiaveEsterna ce1 = new ChiaveEsterna();
				ce1.setValore(provenienza, co1, DwhUtils.getIdentificativo(fk_ente_sorgente));
				dich.setIdExtOggRsu(ce1);
				
				ChiaveOriginale co2 = new ChiaveOriginale();
				co2.setValore(id_orig_sogg);
				
				ChiaveEsterna ce2 = new ChiaveEsterna();
				ce2.setValore(provenienza, co2, DwhUtils.getIdentificativo(fk_ente_sorgente));
				dich.setIdExtSogg(ce2);
				
				SitTTarDichDao dichDao = (SitTTarDichDao) DaoFactory.createDao(conn, dich, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
				
				salvato = dichDao.save(ctx.getBelfiore());				
			} else {
				
				//INSERIMENTO IN SIT_T_TAR_ULT_SOGG
				SitTTarUltSogg tab = (SitTTarUltSogg)getTabellaDwhInstance(ctx);
				
				tab.setIdOrig(null);
				tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente), ctx.getBelfiore());
				tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(), dt_exp_dato));
				tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
				tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
				tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
				tab.setProvenienza(provenienza);
				
				ChiaveOriginale co1 = new ChiaveOriginale();
				co1.setValore(id_orig_ogg_rsu);
				
				ChiaveEsterna ce1 = new ChiaveEsterna();
				ce1.setValore(provenienza, co1, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtOggRsu(ce1);
				
				ChiaveOriginale co2 = new ChiaveOriginale();
				co2.setValore(id_orig_sogg);
				
				ChiaveEsterna ce2 = new ChiaveEsterna();
				ce2.setValore(provenienza, co2, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtSogg(ce2);
				
				SitTTarUltSoggDao dao = (SitTTarUltSoggDao) DaoFactory.createDao(conn, tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
				
				salvato = dao.save(ctx.getBelfiore());
				
				//INSERIMENTO IN SIT_T_TAR_OGG_ULTSOGG
				SitTTarOggUltsogg ou = (SitTTarOggUltsogg)getTabellaDwhInstanceSitTTarOggUltsogg(ctx);
				
				ou.setIdOrig(null);
				ou.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente), ctx.getBelfiore());
				ou.setIdExtTarUltSogg(tab.getIdExt());
				ou.setTitSogg(tit_sogg);
				ou.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(), dt_exp_dato));
				ou.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
				ou.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
				ou.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
				ou.setProvenienza(provenienza);
				
				SitTTarOggUltsoggDao ouDao = (SitTTarOggUltsoggDao) DaoFactory.createDao(conn, ou, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
				
				salvato = ouDao.save(ctx.getBelfiore());
			}
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitTTarUltSogg",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	
		return(new ApplicationAck("Record SIT_T_TAR_ULT_SOGG inserito"));
	
	}
		
	private TabellaDwh getTabellaDwhInstanceSitTTarSogg(Context ctx) throws Exception {
		TabellaDwh retVal = SitTTarSogg.class.newInstance();
		ProcessId processId = new ProcessId(ctx.getProcessID());
		retVal.setProcessid(processId);
		return retVal;
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

	private TabellaDwh getTabellaDwhInstanceSitTTarOggUltsogg(Context ctx) throws Exception {
		TabellaDwh retVal = SitTTarOggUltsogg.class.newInstance();
		ProcessId processId = new ProcessId(ctx.getProcessID());
		retVal.setProcessid(processId);
		return retVal;
	}

}
