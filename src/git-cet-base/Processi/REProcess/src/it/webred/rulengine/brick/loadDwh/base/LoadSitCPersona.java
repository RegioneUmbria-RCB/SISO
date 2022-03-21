package it.webred.rulengine.brick.loadDwh.base;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
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

import it.webred.rulengine.dwh.Dao.SitCPersonaDao;

import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;

import it.webred.rulengine.dwh.table.SitCPersona;

import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class LoadSitCPersona extends AbstractLoaderCommand implements Rule
{

	private static final Logger log = Logger.getLogger(LoadSitCPersona.class.getName());

	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitCPersona.class;
	}

	public LoadSitCPersona(BeanCommand bc, Properties jrulecfg) {
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

		String tipoSoggetto = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
		String tipoPersona = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
		String codiceFiscale = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
		String cognome = (String) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
		String nome = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
		String denominazione = (String) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());

		String provenienza = (String) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
		String titolo  = (String) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
		Timestamp dataNascita = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());
		String comuneNascita = (String) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
		String provinciaNascita = (String) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
		String indirizzo = (String) ctx.get(((RRuleParamIn) parametriIn.get(13)).getDescr());
		String cap = (String) ctx.get(((RRuleParamIn) parametriIn.get(14)).getDescr());
		String comuneResidenza = (String) ctx.get(((RRuleParamIn) parametriIn.get(15)).getDescr());
		String provinciaResidenza = (String) ctx.get(((RRuleParamIn) parametriIn.get(16)).getDescr());
		String telefono = (String) ctx.get(((RRuleParamIn) parametriIn.get(17)).getDescr());
		String fax = (String) ctx.get(((RRuleParamIn) parametriIn.get(18)).getDescr());
		String email = (String) ctx.get(((RRuleParamIn) parametriIn.get(19)).getDescr());
		String piva = (String) ctx.get(((RRuleParamIn) parametriIn.get(20)).getDescr());
		String indirizzoStudio = (String) ctx.get(((RRuleParamIn) parametriIn.get(21)).getDescr());
		String capStudio = (String) ctx.get(((RRuleParamIn) parametriIn.get(22)).getDescr());
		String comuneStudio = (String) ctx.get(((RRuleParamIn) parametriIn.get(23)).getDescr());
		String provStudio = (String) ctx.get(((RRuleParamIn) parametriIn.get(24)).getDescr());
		String albo = (String) ctx.get(((RRuleParamIn) parametriIn.get(25)).getDescr());
		String ragsocDitta = (String) ctx.get(((RRuleParamIn) parametriIn.get(26)).getDescr());
		String cfDitta = (String) ctx.get(((RRuleParamIn) parametriIn.get(27)).getDescr());
		String piDitta = (String) ctx.get(((RRuleParamIn) parametriIn.get(28)).getDescr());
		String indirizzoDitta = (String) ctx.get(((RRuleParamIn) parametriIn.get(29)).getDescr());
		String capDitta = (String) ctx.get(((RRuleParamIn) parametriIn.get(30)).getDescr());
		String comuneDitta = (String) ctx.get(((RRuleParamIn) parametriIn.get(31)).getDescr());
		String provDitta = (String) ctx.get(((RRuleParamIn) parametriIn.get(32)).getDescr());
		String qualita = (String) ctx.get(((RRuleParamIn) parametriIn.get(33)).getDescr());

		
		Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(34)).getDescr());
		Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(35)).getDescr());
		Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(36)).getDescr());
		Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(37)).getDescr());
		String civico = (String) ctx.get(((RRuleParamIn) parametriIn.get(38)).getDescr());
		Timestamp dataIniRes = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(39)).getDescr());


		if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
			return new RejectAck("SIT_C_PERSONA - Dati obbligatori non forniti");
		if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
			return new RejectAck("SIT_C_PERSONA - flag_dt_val_dato non valido");
		
		SitCPersona tab = (SitCPersona)getTabellaDwhInstance(ctx);
		ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
		chiaveOriginale.setValore(id_orig);
		
		tab.setIdOrig(chiaveOriginale);

		tab.setTipoSoggetto(tipoSoggetto);
		tab.setTipoPersona(tipoPersona);
		tab.setCodiceFiscale(codiceFiscale);
		tab.setCognome(cognome);
		tab.setNome(nome);
		tab.setDenominazione( denominazione);

		tab.setProvenienza ( provenienza);
		tab.setTitolo   ( titolo);
		tab.setDataNascita ( DwhUtils.getDataDwh(new DataDwh(),dataNascita));
		tab.setComuneNascita ( comuneNascita);
		tab.setProvNascita  ( provinciaNascita);
		tab.setIndirizzo ( indirizzo);
		tab.setCap ( cap);
		tab.setComuneResidenza (comuneResidenza );
		tab.setProvResidenza (provinciaResidenza );
		tab.setTelefono ( telefono);
		tab.setFax (fax );
		tab.setEmail ( email);
		tab.setPiva ( piva);
		tab.setIndirizzoStudio ( indirizzoStudio);
		tab.setCapStudio ( capStudio);
		tab.setComuneStudio ( comuneStudio);
		tab.setProvStudio ( provStudio);
		tab.setAlbo ( albo);
		tab.setRagsocDitta (ragsocDitta );
		tab.setCfDitta ( cfDitta);
		tab.setPiDitta ( piDitta);
		tab.setIndirizzoDitta ( indirizzoDitta);
		tab.setCapDitta ( capDitta);
		tab.setComuneDitta (comuneDitta );
		tab.setProvDitta (provDitta );
		tab.setQualita( qualita);	
		tab.setCivico(civico);
		tab.setDataIniRes( DwhUtils.getDataDwh(new DataDwh(),dataIniRes));
		
		tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
		tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
		tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
		tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
		tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
		

		SitCPersonaDao dao = (SitCPersonaDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
		
		boolean salvato = dao.save(ctx.getBelfiore());



		
		
	} catch (DaoException e)
	{
		log.error("Errore di inserimento nella classe Dao",e);
		ErrorAck ea = new ErrorAck(e.getMessage());
		return (ea);
	} catch (Exception e)
	{
		log.error("LoadSitCPersona",e);
		ErrorAck ea = new ErrorAck(e.getMessage());
		return (ea);
	}

	return(new ApplicationAck("Record SIT_C_CONC_PERSONA inserito"));

}

	
}
