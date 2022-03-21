package it.webred.rulengine.brick.loadDwh.base;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.swing.text.TabSet;

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
import it.webred.rulengine.dwh.Dao.SitRttSoggBolletteDao;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.SitRttSoggBollette;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class LoadSitRttSoggBollette extends AbstractLoaderCommand implements Rule
{
	
	public LoadSitRttSoggBollette(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadSitRttSoggBollette.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitRttSoggBollette.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
		throws CommandException
	{
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
			
			String cod_soggetto = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
			String codice_fiscale = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
			String provenienza = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
			String cognome = (String) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
			String nome = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			String sesso = (String) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			String data_nascita = (String) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
			String partita_iva = (String) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
			String comune_nascita = (String) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());
			String localita_nascita = (String) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());

			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(13)).getDescr());
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(14)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(15)).getDescr());
			
			
			if(cod_soggetto==null || provenienza==null || cognome==null)
				return new RejectAck("SIT_RTT_SOGG_BOLLETTE - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_RTT_SOGG_BOLLETTE - flag_dt_val_dato non valido");
			SitRttSoggBollette tab = (SitRttSoggBollette)getTabellaDwhInstance(ctx);
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
			
			tab.setCodSoggetto(cod_soggetto);
			tab.setCodiceFiscale(codice_fiscale);
			tab.setProvenienza(new BigDecimal(provenienza));
			tab.setCognome(cognome);
			tab.setNome(nome);
			if(sesso != null)
				tab.setSesso(new BigDecimal(sesso));
			if(data_nascita != null){
				Timestamp ts = new Timestamp(sdf.parse(data_nascita).getTime());
				tab.setDataNascita(DwhUtils.getDataDwh(new DataDwh(), ts));
			}
			tab.setPartitaIva(partita_iva);
			tab.setComuneNascita(comune_nascita);
			tab.setLocalitaNascita(localita_nascita);
			
			SitRttSoggBolletteDao dao = (SitRttSoggBolletteDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			
			boolean salvato = dao.save(ctx.getBelfiore());		
			log.info(salvato?"Salvataggio avvenuto correttamente": "Salvataggio non avvenuto");
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitRttSoggBollette",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		String msg = "Record SIT_RTT_SOGG_BOLLETTE inserito";
		return(new ApplicationAck(msg));

	}

}
