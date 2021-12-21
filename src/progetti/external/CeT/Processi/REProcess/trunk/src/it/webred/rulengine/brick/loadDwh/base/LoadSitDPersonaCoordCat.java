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
import it.webred.rulengine.dwh.Dao.SitDPersonaCoordCatDao;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.SitDPersonaCoordCat;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadSitDPersonaCoordCat extends AbstractLoaderCommand implements Rule
{

	private static final Logger log = Logger.getLogger(LoadSitDPersonaCoordCat.class.getName());

	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitDPersonaCoordCat.class;
	}

	public LoadSitDPersonaCoordCat(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

	public CommandAck runWithConnectionSpec(Context ctx, Connection conn) throws CommandException {
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			
			String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());			
			String descrizioneVia = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
			String tipoCivico = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
			String lotto = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
			String isolato = (String) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
			String scala = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			String numInt = (String) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			String piano = (String) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
			String cap = (String) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
			String cittadinanza = (String) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());
			String titoloStudio = (String) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
			String statoCivile = (String) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
			String flgIntestatarioScheda = (String) ctx.get(((RRuleParamIn) parametriIn.get(13)).getDescr());
			String codEventoVariazione = (String) ctx.get(((RRuleParamIn) parametriIn.get(14)).getDescr());
			String foglio = (String) ctx.get(((RRuleParamIn) parametriIn.get(15)).getDescr());
			String mappale = (String) ctx.get(((RRuleParamIn) parametriIn.get(16)).getDescr());
			String subalterno = (String) ctx.get(((RRuleParamIn) parametriIn.get(17)).getDescr());
			String nomeFile = (String) ctx.get(((RRuleParamIn) parametriIn.get(18)).getDescr());
			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(19)).getDescr());
			Timestamp dt_inizio_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(20)).getDescr());
			Timestamp dt_fine_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(21)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(22)).getDescr());
			
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_D_PERSONA_COORD_CAT - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_D_PERSONA_COORD_CAT - flag_dt_val_dato non valido");
			if (id_orig==null)
				return new RejectAck("SIT_D_PERSONA_COORD_CAT - id persona obbligatorio");
			
			SitDPersonaCoordCat tab = (SitDPersonaCoordCat)getTabellaDwhInstance(ctx);
			
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);			
			tab.setIdOrig(chiaveOriginale);
			
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			
			tab.setDescrizioneVia(descrizioneVia);
			tab.setTipoCivico(tipoCivico);
			tab.setLotto(lotto);
			tab.setIsolato(isolato);
			tab.setScala(scala);
			tab.setNumInt(numInt);
			tab.setPiano(piano);
			tab.setCap(cap);
			tab.setCittadinanza(cittadinanza);
			tab.setTitoloStudio(titoloStudio);
			tab.setStatoCivile(statoCivile);
			tab.setFlgIntestatarioScheda(flgIntestatarioScheda);
			tab.setCodEventoVariazione(codEventoVariazione);
			tab.setFoglio(foglio);
			tab.setMappale(mappale);
			tab.setSubalterno(subalterno);
			tab.setNomeFile(nomeFile);
			
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_inizio_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
	
			SitDPersonaCoordCatDao dao = (SitDPersonaCoordCatDao) DaoFactory.createDao(conn, tab, ctx.getEnteSorgenteById(fk_ente_sorgente));
			
			boolean salvato = dao.save(ctx.getBelfiore());
			
		} catch (DaoException e) {
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e) {
			log.error("LoadSitDPersonaCoordCat",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	
		return(new ApplicationAck("Record SIT_D_PERSONA_COORD_CAT inserito"));
	
	}

}

