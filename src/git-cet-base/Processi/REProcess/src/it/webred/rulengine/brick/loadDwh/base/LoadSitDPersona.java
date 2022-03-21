package it.webred.rulengine.brick.loadDwh.base;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
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
import it.webred.rulengine.dwh.Dao.SitComuneDao;
import it.webred.rulengine.dwh.Dao.SitDCivicoDao;
import it.webred.rulengine.dwh.Dao.SitDPersonaDao;
import it.webred.rulengine.dwh.Dao.SitDUnioneDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.def.TipoXml;
import it.webred.rulengine.dwh.table.SitDCivico;
import it.webred.rulengine.dwh.table.SitDPersona;
import it.webred.rulengine.dwh.table.SitDUnione;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;

public class LoadSitDPersona extends AbstractLoaderCommand implements Rule
{
	
	private static final int IDX_CTRL_NUOVO_TRACCIATO = 31;
	//private static final String FLAG_INS_CONIUGE = "0";
	private static final String MASCHIO = "M";
	private static final String FEMMINA = "F";
	
	public LoadSitDPersona(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadSitDPersona.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = SitDPersona.class;
	}


	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
		throws CommandException
	{
		boolean insConiuge = false;
		try {
			List parametriIn = this.getParametersIn(_jrulecfg);
			String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
			String cognome = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
			String nome = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
			String sesso = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
			String codfisc = (String) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
			String posiz_ana = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
			String stato_civile = (String) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
			String des_persona = (String) ctx.get(((RRuleParamIn) parametriIn.get(8)).getDescr());
			Timestamp data_inizio_residenza = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(9)).getDescr());
			
			String id_orig_stato = (String) ctx.get(((RRuleParamIn) parametriIn.get(10)).getDescr());

			String id_orig_provincia_imm = (String) ctx.get(((RRuleParamIn) parametriIn.get(11)).getDescr());
			String id_orig_comune_imm = (String) ctx.get(((RRuleParamIn) parametriIn.get(12)).getDescr());
			Timestamp data_imm = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(13)).getDescr());
			String id_orig_provincia_emi = (String) ctx.get(((RRuleParamIn) parametriIn.get(14)).getDescr());
			String id_orig_comune_emi = (String) ctx.get(((RRuleParamIn) parametriIn.get(15)).getDescr());
			Timestamp data_emi = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(16)).getDescr());
			String id_orig_provincia_mor = (String) ctx.get(((RRuleParamIn) parametriIn.get(17)).getDescr());
			String id_orig_comune_mor = (String) ctx.get(((RRuleParamIn) parametriIn.get(18)).getDescr());
			Timestamp data_mor = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(19)).getDescr());

			String id_orig_provincia_nascita = (String) ctx.get(((RRuleParamIn) parametriIn.get(20)).getDescr());
			String id_orig_comune_nascita = (String) ctx.get(((RRuleParamIn) parametriIn.get(21)).getDescr());
			Timestamp data_nascita = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(22)).getDescr());
			
			String id_orig_d_persona_madre = (String) ctx.get(((RRuleParamIn) parametriIn.get(23)).getDescr());
			String id_orig_d_persona_padre = (String) ctx.get(((RRuleParamIn) parametriIn.get(24)).getDescr());
			
			
			Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(25)).getDescr());
			Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(26)).getDescr());
			Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(27)).getDescr());
			Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(28)).getDescr());
			String flagCodiceFiscale = (String) ctx.get(((RRuleParamIn) parametriIn.get(29)).getDescr());
						
			/*
			String matricolaConiuge = (String) ctx.get(((RRuleParamIn) parametriIn.get(30)).getDescr());
			String flagConiuge = (String) ctx.get(((RRuleParamIn) parametriIn.get(31)).getDescr());
			String codFisConiuge = (String) ctx.get(((RRuleParamIn) parametriIn.get(32)).getDescr());
			String cognomeConiuge = (String) ctx.get(((RRuleParamIn) parametriIn.get(33)).getDescr());
			String nomeConiuge = (String) ctx.get(((RRuleParamIn) parametriIn.get(34)).getDescr());
			Timestamp dataNascitaConiuge = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(35)).getDescr());
			*/

			String indirizzoEmigrazione = null;
			try{
				indirizzoEmigrazione = (String) ctx.get(((RRuleParamIn) parametriIn.get(36)).getDescr());
			}catch(Exception e){
				log.info("____INDIRZZO EMIGRAZIONE NON MAPPATO PER QUESTA REGOLA...CONTINUO...");
			}
			
			String motivo_cancellazione_apr = null;
			String motivo_iscrizione_apr = null;
			Timestamp data_cancellazione_apr = null;
			Timestamp data_iscrizione_apr = null;
			try{
				motivo_cancellazione_apr = (String) ctx.get(((RRuleParamIn) parametriIn.get(37)).getDescr());
				data_cancellazione_apr = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(38)).getDescr());
				motivo_iscrizione_apr = (String) ctx.get(((RRuleParamIn) parametriIn.get(39)).getDescr());
				data_iscrizione_apr = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(40)).getDescr());
			}catch(Exception e){
				log.info("____DATI APR NON MAPPATI PER QUESTA REGOLA...CONTINUO...");
			}
			
			if(id_orig==null || fk_ente_sorgente==null || dt_exp_dato==null || flag_dt_val_dato==null)
				return new RejectAck("SIT_D_PERSONA - Dati obbligatori non forniti");
			if(flag_dt_val_dato.intValue() != 0 && flag_dt_val_dato.intValue() != 1)
				return new RejectAck("SIT_D_PERSONA - flag_dt_val_dato non valido");
			SitDPersona tab = (SitDPersona)getTabellaDwhInstance(ctx);
			ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
			chiaveOriginale.setValore(id_orig);
			
			tab.setIdOrig(chiaveOriginale);
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
			tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			
			
			tab.setCodfisc(codfisc);
			tab.setFlagCodiceFiscale(flagCodiceFiscale);
			tab.setCognome(cognome);
			tab.setDataEmi(DwhUtils.getDataDwh(new DataDwh(),data_emi));
			tab.setDataImm(DwhUtils.getDataDwh(new DataDwh(),data_imm));
			tab.setDataInizioResidenza(DwhUtils.getDataDwh(new DataDwh(),data_inizio_residenza));
			tab.setDataMor(DwhUtils.getDataDwh(new DataDwh(),data_mor));
			tab.setDataNascita(DwhUtils.getDataDwh(new DataDwh(),data_nascita));
			tab.setDesPersona(des_persona);
			tab.setNome(nome);
			tab.setSesso(sesso);
			tab.setPosizAna(posiz_ana);
			tab.setStatoCivile(stato_civile);
			tab.setIndirizzoEmi(indirizzoEmigrazione);
			if(motivo_cancellazione_apr != null){
				tab.setMotivoCancellazioneApr(motivo_cancellazione_apr);
				tab.setDataCancellazioneApr(DwhUtils.getDataDwh(new DataDwh(),data_cancellazione_apr));
				//se presente azzero data emigrazione
				tab.setDataEmi(DwhUtils.getDataDwh(new DataDwh(),null));
			}
			tab.setMotivoIscrizioneApr(motivo_iscrizione_apr);
			tab.setDataIscrizioneApr(DwhUtils.getDataDwh(new DataDwh(),data_iscrizione_apr));
			
			if (id_orig_stato!=null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_stato);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtStato(ce);
			}
			if (id_orig_comune_emi!=null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_comune_emi);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtComuneEmi(ce);
			}
			if (id_orig_comune_imm!=null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_comune_imm);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtComuneImm(ce);
			}			
			if (id_orig_comune_mor!=null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_comune_mor);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtComuneMor(ce);
			}	
			if (id_orig_comune_nascita!=null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_comune_nascita);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtComuneNascita(ce);
			}	
			if (id_orig_provincia_emi!=null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_provincia_emi);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtProvinciaEmi(ce);
			}	
			if (id_orig_provincia_imm!=null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_provincia_imm);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtProvinciaImm(ce);
			}
			if (id_orig_provincia_mor!=null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_provincia_mor);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtProvinciaMor(ce);
			}
			if (id_orig_provincia_nascita!=null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_provincia_nascita);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtProvinciaNascita(ce);
			}
			if (id_orig_d_persona_madre!=null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_d_persona_madre);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtDPersonaMadre(ce);
			}			
			if (id_orig_d_persona_padre!=null) {
				ChiaveOriginale co = new ChiaveOriginale();
				co.setValore(id_orig_d_persona_padre);
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtDPersonaPadre(ce);
			}				
			SitDPersonaDao dao = (SitDPersonaDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			
			boolean salvato = dao.save(ctx.getBelfiore());
			insConiuge = verificaInsConiuge(ctx, conn);
			
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadSitDPersona",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		String msg = "Record SIT_D_PERSONA inserito";
		if (insConiuge) {
			msg += " - presenti e inseriti correttamente anche i dati del coniuge (SIT_D_PERSONA, solo se i dati del coniuge non sono forniti anche con riga autonoma, e SIT_D_UNIONE)";
		}
		return(new ApplicationAck(msg));

	}
	
	private boolean verificaInsConiuge(Context ctx, Connection conn) throws Exception {
		List parametriIn = this.getParametersIn(_jrulecfg);
		try {
			 if (parametriIn != null) {
				 if (parametriIn.get(IDX_CTRL_NUOVO_TRACCIATO) != null && ctx.get(((RRuleParamIn) parametriIn.get(31)).getDescr()) != null) {
					 boolean insConiuge;
					 //String flagConiuge = (String) ctx.get(((RRuleParamIn) parametriIn.get(31)).getDescr());
					 //insConiuge = flagConiuge.equals(FLAG_INS_CONIUGE);					 
					 String id_orig = (String) ctx.get(((RRuleParamIn) parametriIn.get(30)).getDescr());
					 Integer fk_ente_sorgente = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
					 String codfisc = (String) ctx.get(((RRuleParamIn) parametriIn.get(32)).getDescr());
					 String cognome = (String) ctx.get(((RRuleParamIn) parametriIn.get(33)).getDescr());
					 String nome = (String) ctx.get(((RRuleParamIn) parametriIn.get(34)).getDescr());
					 String des_persona = cognome + "/" + nome;
					 String sessoConiuge = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
					 //si presume che il sesso sia l'opposto del sesso del coniuge e lo stato civile lo stesso del coniuge...
					 String sesso = sessoConiuge == null ? null : 
						 			(sessoConiuge.equals(MASCHIO) ? FEMMINA : (sessoConiuge.equals(FEMMINA) ? MASCHIO : null));
					 String stato_civile = (String) ctx.get(((RRuleParamIn) parametriIn.get(7)).getDescr());
					 Timestamp data_nascita = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(35)).getDescr());
					 Timestamp dt_exp_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(25)).getDescr());
					 Timestamp dt_ini_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(26)).getDescr());
					 Timestamp dt_fine_val_dato = (Timestamp) ctx.get(((RRuleParamIn) parametriIn.get(27)).getDescr());
					 Integer flag_dt_val_dato = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(28)).getDescr());					 
					 insConiuge = id_orig != null && !id_orig.equals("0000000000");
					 
					 if (insConiuge) {
						 boolean salvato = true;						 
						 boolean insPersona = true;
						 
						 //si inserisce il coniuge solo se non è fornito con riga autonoma e non è già presente in SIT_D_PERSONA
						 PreparedStatement ps = null;
						 ResultSet rs = null;
						 try {
							String sql = "SELECT COUNT(1) AS CONTA FROM RE_DEMOG_ANAGRAFE_2_0 WHERE MATRICOLA = ? AND PROCESSID = ?";
							ps = conn.prepareStatement(sql);
							ps.setString(1, id_orig);
							ps.setString(2, ctx.getProcessID());
							rs = ps.executeQuery();
							while (rs.next()) {
								insPersona = rs.getObject("CONTA") == null || rs.getInt("CONTA") == 0;
							}
							
							if (insPersona) {
								if (rs != null) {
									rs.close();
								}
								if (ps != null) {
									ps.close();
								}
								sql = "SELECT COUNT(1) AS CONTA FROM SIT_D_PERSONA WHERE ID_ORIG = ? " +
								"AND (DT_FINE_VAL IS NULL OR TO_CHAR(DT_FINE_VAL, 'DD/MM/YYYY') = '31/12/9999')";
								ps = conn.prepareStatement(sql);
								ps.setString(1, id_orig);
								rs = ps.executeQuery();
								while (rs.next()) {
									insPersona = rs.getObject("CONTA") == null || rs.getInt("CONTA") == 0;
								}								
							}
						 } catch (Exception e) {
							 insPersona = false;
						 } finally {
							 if (rs != null) {
								 rs.close();
							 }
							 if (ps != null) {
								 ps.close();
							 }
						 }						 
						 
						 if (insPersona) {
							 SitDPersona tab = (SitDPersona)getTabellaDwhInstance(ctx);
							 ChiaveOriginale chiaveOriginale =  new ChiaveOriginale();
							 chiaveOriginale.setValore(id_orig);				
							 tab.setIdOrig(chiaveOriginale);
							 tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
								tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
								tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
							 tab.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
							 tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente), ctx.getBelfiore());
							 tab.setCodfisc(codfisc);				
							 tab.setCognome(cognome);
							 tab.setDataNascita(DwhUtils.getDataDwh(new DataDwh(),data_nascita));
							 tab.setDesPersona(des_persona);
							 tab.setNome(nome);
							 tab.setSesso(sesso);
							 tab.setStatoCivile(stato_civile);	
							 //trattamento date null
							 tab.setDataEmi(DwhUtils.getDataDwh(new DataDwh(), null));
							 tab.setDataImm(DwhUtils.getDataDwh(new DataDwh(), null));
							 tab.setDataInizioResidenza(DwhUtils.getDataDwh(new DataDwh(), null));
							 tab.setDataMor(DwhUtils.getDataDwh(new DataDwh(), null));						 
								
							 // almeno il codfisc o il cognome, per inserire 
							 if (codfisc==null && cognome==null)
								 return false;

							 SitDPersonaDao dao = (SitDPersonaDao) DaoFactory.createDao(conn, tab, ctx.getEnteSorgenteById(fk_ente_sorgente));						
									
							 salvato = dao.save(ctx.getBelfiore());
						 }						 
						 
						 if (salvato) { 
							 //inserimento in SIT_D_UNIONE
							 
							 /* 
							  * inserire un controllo in SIT_D_UNIONE per verificare se la stessa unione è stata già inserita?
							  * esempio:
							  * SELECT COUNT(1) AS CONTA FROM SIT_D_UNIONE WHERE ID_EXT_D_PERSONA1 = ? OR ID_EXT_D_PERSONA2 = ? 
							  * AND (DT_FINE_VAL IS NULL OR TO_CHAR(DT_FINE_VAL, 'DD/MM/YYYY') = '31/12/9999')
							  */
							 
							 //id_orig qui è la matricola del primo dei due coniugi
							 String id_origUnione = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
							 
							 if (id_origUnione != null && id_orig != null) {
								 SitDUnione tabUnione = (SitDUnione)getTabellaDwhInstanceSitDUnione(ctx);
								 ChiaveOriginale chiaveOriginaleUnione = new ChiaveOriginale();
								 chiaveOriginaleUnione.setValore(id_origUnione);								
								 tabUnione.setIdOrig(chiaveOriginaleUnione);
								 tabUnione.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),dt_exp_dato));
								 tabUnione.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),dt_ini_val_dato));
								 tabUnione.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),dt_fine_val_dato));
								 tabUnione.setFlagDtValDato(new BigDecimal(flag_dt_val_dato));
								 tabUnione.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente), ctx.getBelfiore());
								 ChiaveOriginale co1 = new ChiaveOriginale();
								 co1.setValore(id_origUnione);								
								 ChiaveEsterna ce1 = new ChiaveEsterna();
								 ce1.setValore(co1, DwhUtils.getIdentificativo(fk_ente_sorgente));
								 tabUnione.setIdExtDPersona1(ce1);
								 ChiaveOriginale co2 = new ChiaveOriginale();
								 co2.setValore(id_orig);								
								 ChiaveEsterna ce2 = new ChiaveEsterna();
								 ce2.setValore(co2, DwhUtils.getIdentificativo(fk_ente_sorgente));
								 tabUnione.setIdExtDPersona2(ce2);
								 //trattamento date null
								 tabUnione.setDataUnione(DwhUtils.getDataDwh(new DataDwh(), null));
								 
								 SitDUnioneDao daoUnione = (SitDUnioneDao) DaoFactory.createDao(conn, tabUnione, ctx.getEnteSorgenteById(fk_ente_sorgente));						
								 
								 salvato = daoUnione.save(ctx.getBelfiore());							 
								 
								 return salvato;
							 }
						 }						 
					 }					 
				 }
			 }
			 return false;	
		} catch (Exception e) {			
			throw e;
		}
	}
	
	private TabellaDwh getTabellaDwhInstanceSitDUnione(Context ctx) throws Exception {
		TabellaDwh retVal = SitDUnione.class.newInstance();
		ProcessId processId = new ProcessId(ctx.getProcessID());
		retVal.setProcessid(processId);
		return retVal;
	}

}
