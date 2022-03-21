package it.webred.rulengine.brick.loadDwh.base;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

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
import it.webred.rulengine.dwh.Dao.SitDCivicoDao;
import it.webred.rulengine.dwh.Dao.SitDFamigliaDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.TipoXml;
import it.webred.rulengine.dwh.table.SitDFamiglia;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;

public class LoadSitDFamiglia extends AbstractLoaderCommand implements Rule
{
	private static final Logger log = Logger.getLogger(LoadSitDFamiglia.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitDFamiglia.class;
	}

	public LoadSitDFamiglia(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
	throws CommandException
{
	try {
		List parametriIn = this.getParametersIn(_jrulecfg);
		String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
		Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
		String tipofami = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
		String denominazione = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
		String id_orig_d_civico = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
		Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
		Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
		Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
		Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());

		
		if(id_orig==null || fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
			return new RejectAck("SIT_D_FAMIGLIA - Dati obbligatori non forniti");
		if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
			return new RejectAck("SIT_D_FAMIGLIA - flag_dt_val_dato non valido");
		
		if (id_orig_d_civico==null)
			return new RejectAck("SIT_D_FAMIGLIA - civico obbligatorio - riga non inseribile");
			
		if (tipofami==null)
			return new RejectAck("SIT_D_FAMIGLIA - tipoFami : dato obbligatorio");

		if(id_orig.equals("0000000000"))//convenzione interna dieci 0 significa niente famiglia 
			return(new ApplicationAck("Record SIT_D_FAMIGLIA non inseribile perche 0000000000"));
		
		SitDFamiglia tab = (SitDFamiglia)getTabellaDwhInstance(ctx);
		ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
		chiaveOriginale.setValore(id_orig);
		
		tab.setIdOrig(chiaveOriginale);
		tab.setTipofami(tipofami);
		tab.setDenominazione(denominazione);
			
		tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
		tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
		tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
		tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
		tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
		
		if (id_orig_d_civico!=null) {
			ChiaveOriginale co = new ChiaveOriginale();
			co.setValore(id_orig_d_civico);
			
			ChiaveEsterna ce = new ChiaveEsterna();
			ce.setValore(co, DwhUtils.getIdentificativo(fk_ente_sorgente));
			tab.setIdExtDCivico(ce);
		}
		
		SitDFamigliaDao dao = (SitDFamigliaDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
		
		
		boolean salvato = dao.save(ctx.getBelfiore());
		
		
	} catch (DaoException e)
	{
		log.error("Errore di inserimento nella classe Dao",e);
		ErrorAck ea = new ErrorAck(e.getMessage());
		return (ea);
	} catch (Exception e)
	{
		log.error("LoadSitDFamiglia",e);
		ErrorAck ea = new ErrorAck(e.getMessage());
		return (ea);
	}

	return(new ApplicationAck("Record SIT_D_FAMIGLIA inserito"));

}


}
