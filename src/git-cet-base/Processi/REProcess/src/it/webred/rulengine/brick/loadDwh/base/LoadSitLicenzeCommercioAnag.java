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
import it.webred.rulengine.dwh.Dao.SitLicenzeCommercioAnagDao;
import it.webred.rulengine.dwh.Dao.SitLicenzeCommercioTitDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.table.SitLicenzeCommercioAnag;
import it.webred.rulengine.dwh.table.SitLicenzeCommercioTit;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadSitLicenzeCommercioAnag extends AbstractLoaderCommand implements Rule
{
	public LoadSitLicenzeCommercioAnag(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadSitLicenzeCommercioAnag.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitLicenzeCommercioAnag.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
	throws CommandException
	{
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			
			String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
			String id_orig_autorizzazione = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
			String numero = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
			String codiceFiscale = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
			String cognome = (String) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
			String nome = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			String denominazione = (String) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			String formaGiuridica = (String) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
			String titolo = (String) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
			Timestamp dataNascita = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());
			String comuneNascita = (String) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
			String provinciaNascita = (String) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
			String indirizzoResidenza = (String) ctx.get(((RRuleParamIn) parametriIn.get(13)).getDescr());
			String civicoResidenza = (String) ctx.get(((RRuleParamIn) parametriIn.get(14)).getDescr());
			String capResidenza = (String) ctx.get(((RRuleParamIn) parametriIn.get(15)).getDescr());
			String comuneResidenza = (String) ctx.get(((RRuleParamIn) parametriIn.get(16)).getDescr());
			String provinciaResidenza = (String) ctx.get(((RRuleParamIn) parametriIn.get(17)).getDescr());
			Timestamp dataInizioResidenza = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(18)).getDescr());
			String tel = (String) ctx.get(((RRuleParamIn) parametriIn.get(19)).getDescr());
			String fax = (String) ctx.get(((RRuleParamIn) parametriIn.get(20)).getDescr());
			String email = (String) ctx.get(((RRuleParamIn) parametriIn.get(21)).getDescr());
			String piva = (String) ctx.get(((RRuleParamIn) parametriIn.get(22)).getDescr());
			String provenienza = (String) ctx.get(((RRuleParamIn) parametriIn.get(23)).getDescr());		
			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(24)).getDescr());
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(25)).getDescr());
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(26)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(27)).getDescr());
	
			if(id_orig==null || fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_LICENZE_COMMERCIO_ANAG - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_LICENZE_COMMERCIO_ANAG - flag_dt_val_dato non valido");
			
			SitLicenzeCommercioAnag tab = new SitLicenzeCommercioAnag();
			tab.setProcessid(new ProcessId(ctx.getProcessID()));
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);			
			tab.setIdOrig(chiaveOriginale);
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());			
			tab.setNumero(numero);
			tab.setCodiceFiscale(codiceFiscale);
			tab.setCognome(cognome);
			tab.setNome(nome);
			tab.setDenominazione(denominazione);
			tab.setFormaGiuridica(formaGiuridica);			
			tab.setDataNascita(DwhUtils.getDataDwh(new DataDwh(), dataNascita));
			tab.setComuneNascita(comuneNascita);
			tab.setProvinciaNascita(provinciaNascita);
			tab.setIndirizzoResidenza(indirizzoResidenza);
			tab.setCivicoResidenza(civicoResidenza);
			tab.setCapResidenza(capResidenza);
			tab.setComuneResidenza(comuneResidenza);
			tab.setProvinciaResidenza(provinciaResidenza);
			tab.setDataInizioResidenza(DwhUtils.getDataDwh(new DataDwh(), dataInizioResidenza));
			tab.setTel(tel);
			tab.setFax(fax);
			tab.setEmail(email);
			tab.setPiva(piva);
			tab.setProvenienza(provenienza);		
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			
			SitLicenzeCommercioAnagDao dao = (SitLicenzeCommercioAnagDao) DaoFactory.createDao(conn, tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());
			
			//INSERIMENTO IN SIT_LICENZE_COMMERCIO_TIT
			/* n.b.: non si utilizza il booleano "salvato", perché qui l'inserimento deve avvenire anche se esso vale false,
			nei casi di più record con dati anagrafici uguali (stesso hash) ma con titolo e/o chiave autorizzazione diversi*/
			SitLicenzeCommercioTit tit = new SitLicenzeCommercioTit();
			tit.setProcessid(new ProcessId(ctx.getProcessID()));
			
			ChiaveOriginale chiaveOriginaleTit = new ChiaveOriginale();
			chiaveOriginaleTit.setValore(id_orig_autorizzazione + " " + id_orig);								
			tit.setIdOrig(chiaveOriginaleTit);
			tit.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(), dt_exp_dato));
			tit.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			tit.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente), ctx.getBelfiore());
			tit.setProvenienza(provenienza);
			tit.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tit.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			if (id_orig_autorizzazione != null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_autorizzazione);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(provenienza, co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tit.setIdExtAutorizzazione(ce);
			}
			if (id_orig != null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(provenienza, co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tit.setIdExtAnagrafica(ce);
			}
			tit.setTitolo(titolo);
			
			SitLicenzeCommercioTitDao titDao = (SitLicenzeCommercioTitDao) DaoFactory.createDao(conn, tit, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			salvato = titDao.save(ctx.getBelfiore());			
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitLicenzeCommercioAnag",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	
		return(new ApplicationAck("Record SIT_LICENZE_COMMERCIO_ANAG inserito"));
	
	}
	

}
