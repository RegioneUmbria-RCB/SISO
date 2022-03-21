package it.webred.rulengine.brick.loadDwh.base;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
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
import it.webred.rulengine.dwh.Dao.SitAcquaUtenteDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.SitAcquaUtente;

import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class LoadSitAcquaUtente extends AbstractLoaderCommand implements Rule
{

	private static final Logger log = Logger.getLogger(LoadSitAcquaUtente.class.getName());

	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitAcquaUtente.class;
	}

	public LoadSitAcquaUtente(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
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

		String cognome = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
		String nome = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
		String sesso = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
		String dtNascita = (String) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
		String comuneNascita = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
		String prNascita = (String) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
		String codFiscale = (String) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
		String denominazione = (String) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
		String partIva = (String) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());
		String viaResidenza = (String) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
		String civicoResidenza = (String) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
		String capResidenza = (String) ctx.get(((RRuleParamIn) parametriIn.get(13)).getDescr());
		String comuneResidenza = (String) ctx.get(((RRuleParamIn) parametriIn.get(14)).getDescr());
		String prResidenza = (String) ctx.get(((RRuleParamIn) parametriIn.get(15)).getDescr());
		String telefono = (String) ctx.get(((RRuleParamIn) parametriIn.get(16)).getDescr());
		String faxEmail = (String) ctx.get(((RRuleParamIn) parametriIn.get(17)).getDescr());

		Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(18)).getDescr());
		Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(19)).getDescr());
		Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(20)).getDescr());
		Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(21)).getDescr());
		
		if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
			return new RejectAck("SIT_ACQUA_UTENTE - Dati obbligatori non forniti");
		if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
			return new RejectAck("SIT_ACQUA_UTENTE - flag_dt_val_dato non valido");
		
		SitAcquaUtente tab = (SitAcquaUtente)getTabellaDwhInstance(ctx);
		ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
		chiaveOriginale.setValore(id_orig);
		
		tab.setIdOrig(chiaveOriginale);
		
		tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
		tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
		tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
		tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
		tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente), ctx.getBelfiore());
		
		tab.setCognome(cognome);
		tab.setNome(nome);
		tab.setSesso(sesso);
		if(dtNascita != null){
			try{
			tab.setDtNascita(DwhUtils.getDataDwh(new DataDwh(),new Timestamp(sdf.parse(dtNascita).getTime())));
			} catch (ParseException e) {	
				tab.setDtNascita(DwhUtils.getDataDwh(new DataDwh(),new Timestamp(sdf2.parse(dtNascita).getTime())));
			}
		}
		tab.setComuneNascita(comuneNascita);
		tab.setPrNascita(prNascita);
		tab.setCodFiscale(codFiscale);
		tab.setDenominazione(denominazione);
		tab.setPartIva(partIva);
		tab.setViaResidenza(viaResidenza);
		tab.setCivicoResidenza(civicoResidenza);
		tab.setCapResidenza(capResidenza);
		tab.setComuneResidenza(comuneResidenza);
		tab.setPrResidenza(prResidenza);
		tab.setTelefono(telefono);
		tab.setFaxEmail(faxEmail);

		SitAcquaUtenteDao dao = (SitAcquaUtenteDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
		
		boolean salvato = dao.save(ctx.getBelfiore());
		
		
	} catch (DaoException e)
	{
		log.error("Errore di inserimento nella classe Dao",e);
		ErrorAck ea = new ErrorAck(e.getMessage());
		return (ea);
	} catch (Exception e)
	{
		log.error("LoadSitAcquaUtente",e);
		ErrorAck ea = new ErrorAck(e.getMessage());
		return (ea);
	}

	return(new ApplicationAck("Record SIT_ACQUA_UTENTE inserito"));

}

}
