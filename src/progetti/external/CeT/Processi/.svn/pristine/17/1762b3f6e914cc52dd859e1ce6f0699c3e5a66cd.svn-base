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
import it.webred.rulengine.dwh.Dao.SitTIciContitDao;
import it.webred.rulengine.dwh.Dao.SitTIciContribDao;
import it.webred.rulengine.dwh.Dao.SitTIciDichDao;
import it.webred.rulengine.dwh.Dao.SitTIciOggettoDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.table.SitTIciContit;
import it.webred.rulengine.dwh.table.SitTIciContrib;
import it.webred.rulengine.dwh.table.SitTIciDich;
import it.webred.rulengine.dwh.table.SitTIciOggetto;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadSitTIciOggetto extends AbstractLoaderCommand implements Rule
{
	public LoadSitTIciOggetto(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadSitTIciOggetto.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitTIciOggetto.class;
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
			String yea_den = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
			String num_den = (String) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
			String yea_rif = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			String tip_den = (String) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			String desc_tip_den = (String) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
			String id_orig_sogg_cc = (String) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
			String par_cts = (String) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());
			String sez = (String) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
			String foglio = (String) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
			String numero = (String) ctx.get(((RRuleParamIn) parametriIn.get(13)).getDescr());
			String sub = (String) ctx.get(((RRuleParamIn) parametriIn.get(14)).getDescr());
			String cat = (String) ctx.get(((RRuleParamIn) parametriIn.get(15)).getDescr());
			String cls = (String) ctx.get(((RRuleParamIn) parametriIn.get(16)).getDescr());
			String tip_val = (String) ctx.get(((RRuleParamIn) parametriIn.get(17)).getDescr());
			String desc_tip_val = (String) ctx.get(((RRuleParamIn) parametriIn.get(18)).getDescr());
			BigDecimal val_imm = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(19)).getDescr());
			BigDecimal prc_poss = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(20)).getDescr());
			String car_imm = (String) ctx.get(((RRuleParamIn) parametriIn.get(21)).getDescr());
			BigDecimal dtr_abi_pri = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(22)).getDescr());
			BigDecimal num_mod = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(23)).getDescr());
			BigDecimal num_riga = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(24)).getDescr());
			BigDecimal suf_riga = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(25)).getDescr());
			String flg_imm_sto = (String) ctx.get(((RRuleParamIn) parametriIn.get(26)).getDescr());
			BigDecimal mesi_pos = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(27)).getDescr());
			BigDecimal mesi_ese = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(28)).getDescr());
			BigDecimal mesi_rid = (BigDecimal) ctx.get(((RRuleParamIn) parametriIn.get(29)).getDescr());			
			String flg_pos3112 = (String) ctx.get(((RRuleParamIn) parametriIn.get(30)).getDescr());
			String flg_ese3112 = (String) ctx.get(((RRuleParamIn) parametriIn.get(31)).getDescr());
			String flg_rid3112 = (String) ctx.get(((RRuleParamIn) parametriIn.get(32)).getDescr());
			String flg_abi_pri3112 = (String) ctx.get(((RRuleParamIn) parametriIn.get(33)).getDescr());
			String flg_acq = (String) ctx.get(((RRuleParamIn) parametriIn.get(34)).getDescr());
			String flg_css = (String) ctx.get(((RRuleParamIn) parametriIn.get(35)).getDescr());			
			String yea_pro = (String) ctx.get(((RRuleParamIn) parametriIn.get(36)).getDescr());
			String num_pro = (String) ctx.get(((RRuleParamIn) parametriIn.get(37)).getDescr());
			String flg_trf = (String) ctx.get(((RRuleParamIn) parametriIn.get(38)).getDescr());
			String des_ind = (String) ctx.get(((RRuleParamIn) parametriIn.get(39)).getDescr());
			String id_orig_via = (String) ctx.get(((RRuleParamIn) parametriIn.get(40)).getDescr());
			String num_civ = (String) ctx.get(((RRuleParamIn) parametriIn.get(41)).getDescr());
			String esp_civ = (String) ctx.get(((RRuleParamIn) parametriIn.get(42)).getDescr());
			String scala = (String) ctx.get(((RRuleParamIn) parametriIn.get(43)).getDescr());
			String piano = (String) ctx.get(((RRuleParamIn) parametriIn.get(44)).getDescr());
			String interno = (String) ctx.get(((RRuleParamIn) parametriIn.get(45)).getDescr());
			Timestamp tms_agg = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(46)).getDescr());
			Timestamp tms_bon = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(47)).getDescr());
			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(48)).getDescr());
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(49)).getDescr());
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(50)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(51)).getDescr());
			String provenienza = (String) ctx.get(((RRuleParamIn) parametriIn.get(52)).getDescr());
	
			//if(id_orig==null || fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_T_ICI_OGGETTO - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_T_ICI_OGGETTO - flag_dt_val_dato non valido");
			
			SitTIciOggetto tab = new SitTIciOggetto();
			tab.setProcessid(new ProcessId(ctx.getProcessID()));
			
			ChiaveOriginale chiaveOriginale = new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);			
			tab.setIdOrig(chiaveOriginale);
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			tab.setYeaDen(yea_den);
			tab.setNumDen(num_den);
			tab.setYeaRif(yea_rif);
			tab.setTipDen(tip_den);
			tab.setDescTipDen(desc_tip_den);
			tab.setParCts(par_cts);
			tab.setSez(sez);
			tab.setFoglio(foglio);
			tab.setNumero(numero);
			tab.setSub(sub);
			tab.setCat(cat);
			tab.setCls(cls);
			tab.setTipVal(tip_val);
			tab.setDescTipVal(desc_tip_val);
			tab.setValImm(val_imm);
			tab.setPrcPoss(prc_poss);
			tab.setCarImm(car_imm);
			tab.setDtrAbiPri(dtr_abi_pri);
			tab.setNumMod(num_mod);
			tab.setNumRiga(num_riga);
			tab.setSufRiga(suf_riga);
			tab.setFlgImmSto(flg_imm_sto);
			tab.setMesiPos(mesi_pos);
			tab.setMesiEse(mesi_ese);
			tab.setMesiRid(mesi_rid);
			tab.setFlgPos3112(flg_pos3112);
			tab.setFlgEse3112(flg_ese3112);
			tab.setFlgRid3112(flg_rid3112);
			tab.setFlgAbiPri3112(flg_abi_pri3112);
			tab.setFlgAcq(flg_acq);
			tab.setFlgCss(flg_css);					
			tab.setYeaPro(yea_pro);
			tab.setNumPro(num_pro);
			tab.setFlgTrf(flg_trf);
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
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			tab.setProvenienza(provenienza);
			
			SitTIciOggettoDao dao = (SitTIciOggettoDao) DaoFactory.createDao(conn, tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());
			
			if (id_orig_sogg_cnt != null && !id_orig_sogg_cnt.trim().equals("")) {
				
				//INSERIMENTO IN SIT_T_ICI_CONTRIB
				SitTIciContrib cnt = (SitTIciContrib)getTabellaDwhInstanceSitTIciContrib(ctx.getProcessID());
				
				cnt.setIdOrig(null);
				cnt.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente), ctx.getBelfiore());
				cnt.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(), dt_exp_dato));
				cnt.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
				cnt.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
				cnt.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
				cnt.setProvenienza(provenienza);
				
				/*ChiaveOriginale co1 = new ChiaveOriginale();
				co1.setValore(tab.getCtrHash().getValore());
				
				ChiaveEsterna ce1 = new ChiaveEsterna();
				ce1.setValore(provenienza, co1, DwhUtils.getIdentificativo(fk_ente_sorgente));
				cnt.setIdExtOggIci(ce1);*/
				cnt.setIdExtOggIci(tab.getIdExt());
				
				ChiaveOriginale co2 = new ChiaveOriginale();
				co2.setValore(id_orig_sogg_cnt);
				
				ChiaveEsterna ce2 = new ChiaveEsterna();
				ce2.setValore(provenienza, co2, DwhUtils.getIdentificativo(fk_ente_sorgente));
				cnt.setIdExtSogg(ce2);
				
				SitTIciContribDao cntDao = (SitTIciContribDao) DaoFactory.createDao(conn, cnt, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
				
				salvato = cntDao.save(ctx.getBelfiore());
			}
			
			if (id_orig_sogg_dich != null && !id_orig_sogg_dich.trim().equals("")) {
				
				//INSERIMENTO IN SIT_T_ICI_DICH
				SitTIciDich dich = (SitTIciDich)getTabellaDwhInstanceSitTIciDich(ctx.getProcessID());
				
				dich.setIdOrig(null);
				dich.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente), ctx.getBelfiore());
				dich.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(), dt_exp_dato));
				dich.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
				dich.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
				dich.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
				dich.setProvenienza(provenienza);
				
				/*ChiaveOriginale co1 = new ChiaveOriginale();
				co1.setValore(tab.getCtrHash().getValore());
				
				ChiaveEsterna ce1 = new ChiaveEsterna();
				ce1.setValore(provenienza, co1, DwhUtils.getIdentificativo(fk_ente_sorgente));
				dich.setIdExtOggIci(ce1);*/
				dich.setIdExtOggIci(tab.getIdExt());
				
				ChiaveOriginale co2 = new ChiaveOriginale();
				co2.setValore(id_orig_sogg_dich);
				
				ChiaveEsterna ce2 = new ChiaveEsterna();
				ce2.setValore(provenienza, co2, DwhUtils.getIdentificativo(fk_ente_sorgente));
				dich.setIdExtSogg(ce2);
				
				SitTIciDichDao dichDao = (SitTIciDichDao) DaoFactory.createDao(conn, dich, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
				
				salvato = dichDao.save(ctx.getBelfiore());
			}
			
			if (id_orig_sogg_cc != null && !id_orig_sogg_cc.trim().equals("")) {
				
				//INSERIMENTO IN SIT_T_ICI_CONTIT
				SitTIciContit cc = (SitTIciContit)getTabellaDwhInstanceSitTIciContit(ctx.getProcessID());
				
				cc.setIdOrig(null);
				cc.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente), ctx.getBelfiore());
				cc.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(), dt_exp_dato));
				cc.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
				cc.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
				cc.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
				cc.setProvenienza(provenienza);
				
				/*ChiaveOriginale co1 = new ChiaveOriginale();
				co1.setValore(tab.getCtrHash().getValore());
				
				ChiaveEsterna ce1 = new ChiaveEsterna();
				ce1.setValore(provenienza, co1, DwhUtils.getIdentificativo(fk_ente_sorgente));
				cc.setIdExtOggIci(ce1);*/
				cc.setIdExtOggIci(tab.getIdExt());
				
				ChiaveOriginale co2 = new ChiaveOriginale();
				co2.setValore(id_orig_sogg_cc);
				
				ChiaveEsterna ce2 = new ChiaveEsterna();
				ce2.setValore(provenienza, co2, DwhUtils.getIdentificativo(fk_ente_sorgente));
				cc.setIdExtSogg(ce2);
				
				SitTIciContitDao ccDao = (SitTIciContitDao) DaoFactory.createDao(conn, cc, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
				
				salvato = ccDao.save(ctx.getBelfiore());
			}
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitTIciOggetto",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	
		return(new ApplicationAck("Record SIT_T_ICI_OGGETTO inserito"));
	
	}
	
	private TabellaDwh getTabellaDwhInstanceSitTIciContrib(String processId) throws Exception {
		TabellaDwh retVal = new SitTIciContrib();
		retVal.setProcessid(new ProcessId(processId));
		return retVal;
	}
	
	private TabellaDwh getTabellaDwhInstanceSitTIciDich(String processId) throws Exception {
		TabellaDwh retVal = new SitTIciDich();
		retVal.setProcessid(new ProcessId(processId));
		return retVal;
	}
	
	private TabellaDwh getTabellaDwhInstanceSitTIciContit(String processId) throws Exception {
		TabellaDwh retVal = new SitTIciContit();
		retVal.setProcessid(new ProcessId(processId));
		return retVal;
	}

}
