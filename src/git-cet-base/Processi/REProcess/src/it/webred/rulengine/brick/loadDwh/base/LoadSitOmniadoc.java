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
import it.webred.rulengine.dwh.Dao.SitOmniadocPraEdiDao;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtFineVal;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.DtIniVal;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.table.SitOmniadocPraEdi;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadSitOmniadoc extends AbstractLoaderCommand implements Rule {
	
	public LoadSitOmniadoc(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	private static final Logger log = Logger.getLogger(LoadSitOmniadoc.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitOmniadocPraEdi.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn) throws CommandException {
		
		try {			
			String id_orig = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.1.descr"));
			BigDecimal fk_ente_sorgente = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.2.descr"));
			
			String tipoProtCapofila = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.3.descr"));
			String nrProtCapofila = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.4.descr"));
			String annoProtCapofila = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.5.descr"));
			String registroProtDiSettore = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.6.descr"));
			String subNrProt = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.7.descr"));
			String annoPratOnlyone = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.8.descr"));
			String nrPratOnlyone = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.9.descr"));
			String codVia = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.10.descr"));
			String numCivico = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.11.descr"));
			String appendiceCivico = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.12.descr"));
			String tipoProtDocumento = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.13.descr"));
			String numProtDocumento = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.14.descr"));
			String annoProtDocumento = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.15.descr"));
			String registroProtSetDoc = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.16.descr"));
			String subNumProtDocumento = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.17.descr"));
			String richiedenteIstanza = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.18.descr"));
			String faldone = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.19.descr"));
			String pdfFile = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.20.descr"));
			String f = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.21.descr"));
			String m = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.22.descr"));
			String s = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.23.descr"));
			String oggetto = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.24.descr"));
			String segnalazioni = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.25.descr"));
			String descrizioneVia = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.26.descr"));
			String pagine = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.27.descr"));
			
			String provenienza = (String) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.28.descr"));
			Timestamp dt_inizio_val = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.29.descr"));
			Timestamp dt_fine_val = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.30.descr"));
			Timestamp dt_exp_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.31.descr"));
			Timestamp dt_inizio_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.32.descr"));
			Timestamp dt_fine_dato = (Timestamp) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.33.descr"));
			BigDecimal flag_dt_val_dato = (BigDecimal) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.34.descr"));
			
			if(fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("OMNIADOC PRATICHE EDILIZIE - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("OMNIADOC PRATICHE EDILIZIE - flag_dt_val_dato non valido");
			
			SitOmniadocPraEdi omniaPreEdi = new SitOmniadocPraEdi();
			
			omniaPreEdi.setProcessid(new ProcessId(ctx.getProcessID()));
			
			ChiaveOriginale co =  new ChiaveOriginale();
			co.setValore(id_orig);			
			omniaPreEdi.setIdOrig(co);
			
			omniaPreEdi.setTipo_prot_capofila(tipoProtCapofila);			
			omniaPreEdi.setNr_prot_capofila(nrProtCapofila);
			omniaPreEdi.setAnno_prot_capofila(annoProtCapofila);
			omniaPreEdi.setRegistro_prot_di_settore( registroProtDiSettore );
			omniaPreEdi.setSub_nr_prot( subNrProt );
			omniaPreEdi.setAnno_prat_onlyone( annoPratOnlyone );
			omniaPreEdi.setNr_prat_onlyone( nrPratOnlyone );
			omniaPreEdi.setCod_via( codVia );
			omniaPreEdi.setNum_civico( numCivico );
			omniaPreEdi.setAppendice_civico( appendiceCivico );
			omniaPreEdi.setTipo_prot_documento( tipoProtDocumento );
			omniaPreEdi.setNum_prot_documento( numProtDocumento );
			omniaPreEdi.setAnno_prot_documento( annoProtDocumento );
			omniaPreEdi.setRegistro_prot_set_doc( registroProtSetDoc );
			omniaPreEdi.setSub_num_prot_documento( subNumProtDocumento );
			omniaPreEdi.setRichiedente_istanza( richiedenteIstanza );
			omniaPreEdi.setFaldone( faldone );
			omniaPreEdi.setPdf_file(pdfFile);
			omniaPreEdi.setF( f );
			omniaPreEdi.setM( m );
			omniaPreEdi.setS( s );
			omniaPreEdi.setOggetto( oggetto );
			omniaPreEdi.setSegnalazioni( segnalazioni );
			omniaPreEdi.setDescrizione_via( descrizioneVia );
			omniaPreEdi.setPagine( pagine );
			omniaPreEdi.setProvenienza( provenienza );
			omniaPreEdi.setDtInizioVal((DtIniVal)DwhUtils.getDataDwh(new DtIniVal(), dt_inizio_val));
			omniaPreEdi.setDtFineVal((DtFineVal)DwhUtils.getDataDwh(new DtFineVal(), dt_fine_val));
			omniaPreEdi.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			omniaPreEdi.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_inizio_dato));
			omniaPreEdi.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_dato));
			omniaPreEdi.setFlagDtValDato(flag_dt_val_dato);
			omniaPreEdi.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente.intValue()), ctx.getBelfiore());
			
			SitOmniadocPraEdiDao dao = (SitOmniadocPraEdiDao) DaoFactory.createDao(conn, omniaPreEdi, ctx.getEnteSorgenteById(fk_ente_sorgente.intValue()));
			
			@SuppressWarnings("unused")			
			boolean salvato = dao.save(ctx.getBelfiore());
			
		} catch (DaoException e) {
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e) {
			log.error("LoadSitOmniadoc",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		return(new ApplicationAck("Record SIT_OMNIADOC_PRA_EDI inserito"));
	}

}
