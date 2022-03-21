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
import it.webred.rulengine.dwh.Dao.SitTCosapContribDao;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.SitTCosapContrib;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadSitTCosapContrib extends AbstractLoaderCommand implements Rule
{


	public LoadSitTCosapContrib(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}




	private static final Logger log = Logger.getLogger(LoadSitTCosapContrib.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitTCosapContrib.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
	throws CommandException
	{
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			
			String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
			String tipoPersona = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
			String nome = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
			String cogDenom = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
			String codiceFiscale = (String) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
			String partitaIva = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			Timestamp dtNascita = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			String codBelfioreNascita = (String) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
			String descComuneNascita = (String) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
			String codBelfioreResidenza = (String) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());
			String descComuneResidenza = (String) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
			String capResidenza = (String) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
			String codiceVia = (String) ctx.get(((RRuleParamIn) parametriIn.get(13)).getDescr());
			String sedime = (String) ctx.get(((RRuleParamIn) parametriIn.get(14)).getDescr());
			String indirizzo = (String) ctx.get(((RRuleParamIn) parametriIn.get(15)).getDescr());
			String civico = (String) ctx.get(((RRuleParamIn) parametriIn.get(16)).getDescr());
			Timestamp dtIscrArchivio = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(17)).getDescr());
			Timestamp dtCostitSoggetto = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(18)).getDescr());
			String provenienza = (String) ctx.get(((RRuleParamIn) parametriIn.get(19)).getDescr());		
			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(20)).getDescr());
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(21)).getDescr());
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(22)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(23)).getDescr());
	
			if(id_orig==null || fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_T_COSAP_CONTRIB - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_T_COSAP_CONTRIB - flag_dt_val_dato non valido");
			
			SitTCosapContrib tab = (SitTCosapContrib)getTabellaDwhInstance(ctx);
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);			
			tab.setIdOrig(chiaveOriginale);
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			tab.setTipoPersona(tipoPersona);
			tab.setNome(nome);
			tab.setCogDenom(cogDenom);
			tab.setCodiceFiscale(codiceFiscale);
			tab.setPartitaIva(partitaIva);
			tab.setDtNascita(DwhUtils.getDataDwh(new DataDwh(), dtNascita));
			tab.setCodBelfioreNascita(codBelfioreNascita);
			tab.setDescComuneNascita(descComuneNascita);
			tab.setCodBelfioreResidenza(codBelfioreResidenza);
			tab.setDescComuneResidenza(descComuneResidenza);
			tab.setCapResidenza(capResidenza);
			tab.setCodiceVia(codiceVia);
			tab.setSedime(sedime);
			tab.setIndirizzo(indirizzo);
			tab.setCivico(civico);
			tab.setDtIscrArchivio(DwhUtils.getDataDwh(new DataDwh(), dtIscrArchivio));
			tab.setDtCostitSoggetto(DwhUtils.getDataDwh(new DataDwh(), dtCostitSoggetto));
			tab.setProvenienza(provenienza);		
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			
			SitTCosapContribDao dao = (SitTCosapContribDao) DaoFactory.createDao(conn, tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());		
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitTCosapContrib",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	
		return(new ApplicationAck("Record SIT_T_COSAP_CONTRIB inserito"));
	
	}

}

