package it.webred.rulengine.brick.loadDwh.base;

import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.Dao.DaoException;
import it.webred.rulengine.dwh.Dao.DaoFactory;
import it.webred.rulengine.dwh.Dao.SitTF24AccreditoSuccessivoDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtFineVal;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.DtIniVal;
import it.webred.rulengine.dwh.table.SitTF24AccreditoSuccessivo;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;


public class LoadSitTF24AccreditoSuccessivo extends AbstractLoaderCommand implements Rule
{
	
	private String tabella = "SIT_T_F24_ACCREDITO_SUCCESSIVO";
	
	public LoadSitTF24AccreditoSuccessivo(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}


	private static final Logger log = Logger.getLogger(LoadSitTF24AccreditoSuccessivo.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitTF24AccreditoSuccessivo.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
	throws CommandException
	{
		
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			
			int index = 0;
			
			String id_orig = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Integer fk_ente_sorgente = (Integer) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String annoMeseRif = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String codEnte = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dtProduzioneMandato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String progMandato = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dtRif = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String progRif = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dtBonifico = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String codValuta = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			BigDecimal impAccredito = (BigDecimal) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String motivoMandato = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String cro = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dtFinalizzazione = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);			
			String esitoAccredito = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			String codiceMovimento = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_inizio_val = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_fine_val = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Timestamp dt_exp_dato = (Timestamp) this.getRRuleParameterIn(parametriIn, ctx, index++);
			Integer flag_dt_val_dato = (Integer) this.getRRuleParameterIn(parametriIn, ctx, index++);

			String idOrigTestata = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);

		    String tipoImposta = (String) this.getRRuleParameterIn(parametriIn, ctx, index++);
			
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck(tabella+" - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck(tabella+" - flag_dt_val_dato non valido");
			
			SitTF24AccreditoSuccessivo tab = (SitTF24AccreditoSuccessivo)getTabellaDwhInstance(ctx);
			
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);			
			tab.setIdOrig(chiaveOriginale);
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			tab.setAnnoMeseRif(annoMeseRif);
			tab.setCodEnte(codEnte);
			tab.setDtProduzioneMandato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(), dtProduzioneMandato));			
			tab.setProgMandato(new BigDecimal(progMandato));			
			tab.setDtRif((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dtRif));
			tab.setProgRif(new BigDecimal(progRif));
			tab.setDtBonifico((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dtBonifico));			
			tab.setCodValuta(codValuta);
			tab.setImpAccredito(impAccredito);
			tab.setMotivoMandato(motivoMandato);
			tab.setCro(cro);
			tab.setDtFinalizzazione( (DtExpDato)DwhUtils.getDataDwh(new DtExpDato(), dtFinalizzazione ) );
			tab.setEsitoAccredito(esitoAccredito);
			tab.setCodiceMovimento(codiceMovimento);
			tab.setDtInizioVal( (DtIniVal)DwhUtils.getDataDwh(new DtIniVal(), dt_inizio_val) );
			tab.setDtFineVal((DtFineVal)DwhUtils.getDataDwh(new DtFineVal(),dt_fine_val));
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));			
			tab.setTipoImposta(tipoImposta);

			if (idOrigTestata!=null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(idOrigTestata);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtTestata(ce);
			}

			SitTF24AccreditoSuccessivoDao dao = (SitTF24AccreditoSuccessivoDao) DaoFactory.createDao(conn, tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());		
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitF24Accredito",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	
		return(new ApplicationAck("Record "+tabella+" inserito"));
	
	}

}
