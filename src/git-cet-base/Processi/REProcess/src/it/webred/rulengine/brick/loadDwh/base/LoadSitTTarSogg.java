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
import it.webred.rulengine.dwh.Dao.SitTTarSoggDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.table.SitTTarSogg;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadSitTTarSogg extends AbstractLoaderCommand implements Rule
{
	public LoadSitTTarSogg(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadSitTTarSogg.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitTTarSogg.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
	throws CommandException
	{
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			
			String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
			String id_orig_sogg_u = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
			String cod_fisc = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
			String part_iva = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
			String cog_denom = (String) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
			String nome = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			String sesso = (String) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			String tip_sogg = (String) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
			Timestamp dt_nsc = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
			String cod_ist_cmn_nsc = (String) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());
			String cod_blfr_cmn_nsc = (String) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
			String cod_cmn_nsc = (String) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
			String des_com_nsc = (String) ctx.get(((RRuleParamIn) parametriIn.get(13)).getDescr());
			String cap_com_nsc = (String) ctx.get(((RRuleParamIn) parametriIn.get(14)).getDescr());
			String sigla_prov_nsc = (String) ctx.get(((RRuleParamIn) parametriIn.get(15)).getDescr());
			String des_prov_nsc = (String) ctx.get(((RRuleParamIn) parametriIn.get(16)).getDescr());
			String cod_stato_nsc = (String) ctx.get(((RRuleParamIn) parametriIn.get(17)).getDescr());
			String des_stato_nsc = (String) ctx.get(((RRuleParamIn) parametriIn.get(18)).getDescr());
			String cod_ist_cmn_res = (String) ctx.get(((RRuleParamIn) parametriIn.get(19)).getDescr());
			String cod_blfr_cmn_res = (String) ctx.get(((RRuleParamIn) parametriIn.get(20)).getDescr());
			String cod_cmn_res = (String) ctx.get(((RRuleParamIn) parametriIn.get(21)).getDescr());
			String des_com_res = (String) ctx.get(((RRuleParamIn) parametriIn.get(22)).getDescr());
			String cap_com_res = (String) ctx.get(((RRuleParamIn) parametriIn.get(23)).getDescr());
			String sigla_prov_res = (String) ctx.get(((RRuleParamIn) parametriIn.get(24)).getDescr());
			String des_prov_res = (String) ctx.get(((RRuleParamIn) parametriIn.get(25)).getDescr());
			String cod_stato_res = (String) ctx.get(((RRuleParamIn) parametriIn.get(26)).getDescr());
			String des_stato_res = (String) ctx.get(((RRuleParamIn) parametriIn.get(27)).getDescr());
			String des_ind = (String) ctx.get(((RRuleParamIn) parametriIn.get(28)).getDescr());
			String id_orig_via = (String) ctx.get(((RRuleParamIn) parametriIn.get(29)).getDescr());
			String num_civ = (String) ctx.get(((RRuleParamIn) parametriIn.get(30)).getDescr());
			String esp_civ = (String) ctx.get(((RRuleParamIn) parametriIn.get(31)).getDescr());
			String scala = (String) ctx.get(((RRuleParamIn) parametriIn.get(32)).getDescr());
			String piano = (String) ctx.get(((RRuleParamIn) parametriIn.get(33)).getDescr());
			String interno = (String) ctx.get(((RRuleParamIn) parametriIn.get(34)).getDescr());
			String ind_res_ext = (String) ctx.get(((RRuleParamIn) parametriIn.get(35)).getDescr());
			String num_civ_ext = (String) ctx.get(((RRuleParamIn) parametriIn.get(36)).getDescr());			
			Timestamp tms_agg = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(37)).getDescr());
			String flg_trf = (String) ctx.get(((RRuleParamIn) parametriIn.get(38)).getDescr());
			Timestamp tms_bon = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(39)).getDescr());
			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(40)).getDescr());
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(41)).getDescr());
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(42)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(43)).getDescr());
			String provenienza = (String) ctx.get(((RRuleParamIn) parametriIn.get(44)).getDescr());
	
			if(id_orig==null || fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_T_TAR_SOGG - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_T_TAR_SOGG - flag_dt_val_dato non valido");
			
			SitTTarSogg tab = new SitTTarSogg();			
			tab.setProcessid(new ProcessId(ctx.getProcessID()));
			
			ChiaveOriginale chiaveOriginale = new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);			
			tab.setIdOrig(chiaveOriginale);
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			tab.setIdOrigSoggU(id_orig_sogg_u);
			tab.setCodFisc(cod_fisc);
			tab.setPartIva(part_iva);
			tab.setCogDenom(cog_denom);
			tab.setNome(nome);
			tab.setSesso(sesso);
			tab.setTipSogg(tip_sogg);
			tab.setDtNsc(DwhUtils.getDataDwh(new DataDwh(), dt_nsc));
			tab.setCodIstCmnNsc(cod_ist_cmn_nsc);
			tab.setCodBlfrCmnNsc(cod_blfr_cmn_nsc);
			tab.setCodCmnNsc(cod_cmn_nsc);
			tab.setDesComNsc(des_com_nsc);
			tab.setCapComNsc(cap_com_nsc);
			tab.setSiglaProvNsc(sigla_prov_nsc);
			tab.setDesProvNsc(des_prov_nsc);
			tab.setCodStatoNsc(cod_stato_nsc);
			tab.setDesStatoNsc(des_stato_nsc);			
			tab.setCodIstCmnRes(cod_ist_cmn_res);
			tab.setCodBlfrCmnRes(cod_blfr_cmn_res);
			tab.setCodCmnRes(cod_cmn_res);
			tab.setDesComRes(des_com_res);
			tab.setCapComRes(cap_com_res);
			tab.setSiglaProvRes(sigla_prov_res);
			tab.setDesProvRes(des_prov_res);
			tab.setCodStatoRes(cod_stato_res);
			tab.setDesStatoRes(des_stato_res);
			tab.setDesIndRes(des_ind);
			if (id_orig_via != null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_via);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(provenienza, co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtViaRes(ce);
			}
			tab.setNumCivRes(num_civ);
			tab.setEspCivRes(esp_civ);
			tab.setScalaRes(scala);
			tab.setPianoRes(piano);
			tab.setInternoRes(interno);
			tab.setIndResExt(ind_res_ext);
			tab.setNumCivExt(num_civ_ext);	
			tab.setTmsAgg(DwhUtils.getDataDwh(new DataDwh(), tms_agg));
			tab.setFlgTrf(flg_trf);
			tab.setTmsBon(DwhUtils.getDataDwh(new DataDwh(), tms_bon));			
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			tab.setProvenienza(provenienza);
			
			SitTTarSoggDao dao = (SitTTarSoggDao) DaoFactory.createDao(conn, tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitTTarSogg",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	
		return(new ApplicationAck("Record SIT_T_TAR_SOGG inserito"));
	
	}

}
