package it.webred.rulengine.brick.loadDwh.base;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;


import it.webred.rulengine.Context;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.Dao.DaoException;
import it.webred.rulengine.dwh.Dao.DaoFactory;
import it.webred.rulengine.dwh.Dao.SitCConcessioniDao;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.SitCConcessioni;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class LoadSitCConcessioni extends AbstractLoaderCommand
{
	public LoadSitCConcessioni(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadSitCConcessioni.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitCConcessioni.class;
	}



	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
		throws CommandException
	{
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
			String concessioneNumero = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
			String progressivoNumero = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
			String progressivoAnno = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
			Timestamp protocolloData = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());

			String protocolloNumero = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			String tipoIntervento = (String) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			String oggetto = (String) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
			String procedimento = (String) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
			String zona = (String) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());
			Timestamp dataRilascio = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
			Timestamp dataInizioLavori = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
			Timestamp dataFineLavori = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(13)).getDescr());
			Timestamp dataProrogaLavori = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(14)).getDescr());
			String provenienza = (String) ctx.get(((RRuleParamIn) parametriIn.get(15)).getDescr());

			
			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(16)).getDescr());
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(17)).getDescr());
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(18)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(19)).getDescr());
			String esito = (String) ctx.get(((RRuleParamIn) parametriIn.get(20)).getDescr());

			String posizioneCodice = (String) ctx.get(((RRuleParamIn) parametriIn.get(21)).getDescr());
			String posizioneDescrizione = (String) ctx.get(((RRuleParamIn) parametriIn.get(22)).getDescr());
			Timestamp posizioneData = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(23)).getDescr());




			if(id_orig==null || fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_C_CONCESSIONI - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_C_CONCESSIONI - flag_dt_val_dato non valido");
			SitCConcessioni tab = (SitCConcessioni)getTabellaDwhInstance(ctx);
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);
			
			tab.setIdOrig(chiaveOriginale);

				
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente), ctx.getBelfiore());
			
			tab.setConcessioneNumero(concessioneNumero);
			tab.setProgressivoAnno(progressivoAnno);
			tab.setProgressivoNumero(progressivoNumero);
			tab.setProtocolloData(DwhUtils.getDataDwh(new DataDwh(),protocolloData));
			tab.setProtocolloNumero(protocolloNumero);
			tab.setTipoIntervento(tipoIntervento);
			tab.setOggetto(oggetto);
			tab.setProcedimento(procedimento);
			tab.setZona(zona);
			tab.setDataRilascio(DwhUtils.getDataDwh(new DataDwh(),dataRilascio));
			tab.setDataInizioLavori(DwhUtils.getDataDwh(new DataDwh(),dataInizioLavori));
			tab.setDataFineLavori(DwhUtils.getDataDwh(new DataDwh(),dataFineLavori));
			tab.setDataProrogaLavori(DwhUtils.getDataDwh(new DataDwh(),dataProrogaLavori));
			tab.setProvenienza(provenienza);
			tab.setEsito(esito);
			tab.setPosizioneCodice(posizioneCodice);
			tab.setPosizioneData(DwhUtils.getDataDwh(new DataDwh(),posizioneData) );
			tab.setPosizioneDescrizione(posizioneDescrizione);
			
			SitCConcessioniDao dao = (SitCConcessioniDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			
			boolean salvato = dao.save(ctx.getBelfiore());
			
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitCConcessioni",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		return(new ApplicationAck("Record SIT_C_CONCESSIONI inserito"));

	}



}
