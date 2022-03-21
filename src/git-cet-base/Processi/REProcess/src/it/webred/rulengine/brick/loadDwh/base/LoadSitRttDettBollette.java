package it.webred.rulengine.brick.loadDwh.base;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.log4j.Logger;

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
import it.webred.rulengine.dwh.Dao.SitRttDettBolletteDao;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.SitRttDettBollette;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class LoadSitRttDettBollette extends AbstractLoaderCommand implements Rule
{
	
	public LoadSitRttDettBollette(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadSitRttDettBollette.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitRttDettBollette.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
		throws CommandException
	{
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
			
			String cod_bolletta = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
			String dt_ini_servizio = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
			String dt_fin_servizio = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
			String des_oggetto = (String) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
			String ubicazione = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			String categoria = (String) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			String cod_voce = (String) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
			String des_voce = (String) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
			String valore = (String) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());

			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(13)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(14)).getDescr());
			
			
			if(cod_bolletta==null || des_voce==null || "".equals(des_voce))
				return new RejectAck("SIT_RTT_DETT_BOLLETTE - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_RTT_DETT_BOLLETTE - flag_dt_val_dato non valido");
			SitRttDettBollette tab = (SitRttDettBollette)getTabellaDwhInstance(ctx);
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);
			
			NumberFormat n = NumberFormat.getNumberInstance(Locale.ITALY);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			tab.setIdOrig(chiaveOriginale);
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			
			tab.setCodBolletta(cod_bolletta);
			if(dt_ini_servizio != null){
				Timestamp ts = new Timestamp(sdf.parse(dt_ini_servizio).getTime());
				tab.setDtIniServizio(DwhUtils.getDataDwh(new DataDwh(), ts));
			}
			if(dt_fin_servizio != null){
				Timestamp ts = new Timestamp(sdf.parse(dt_fin_servizio).getTime());
				tab.setDtFinServizio(DwhUtils.getDataDwh(new DataDwh(), ts));
			}
			tab.setDesOggetto(des_oggetto);
			tab.setUbicazione(ubicazione);
			tab.setCategoria(categoria);
			tab.setCodVoce(cod_voce);
			tab.setDesVoce(des_voce);
			tab.setValore(valore);
			
			SitRttDettBolletteDao dao = (SitRttDettBolletteDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			
			boolean salvato = dao.save(ctx.getBelfiore());			
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitRttDettBollette",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		String msg = "Record SIT_RTT_BOLLETTE inserito";
		return(new ApplicationAck(msg));

	}

}
