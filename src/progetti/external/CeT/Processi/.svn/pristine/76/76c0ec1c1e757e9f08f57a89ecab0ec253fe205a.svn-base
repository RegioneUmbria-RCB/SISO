package it.webred.rulengine.brick.elab.toponomastica;

import it.webred.WsSitClient.WsSITClient;
import it.webred.WsSitClient.dto.CivicoDTO;
import it.webred.WsSitClient.dto.RequestDTO;
import it.webred.WsSitClient.dto.ResponseDTO;
import it.webred.WsSitClient.dto.ViaDTO;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.def.TypeFactory;
import it.webred.utils.gis.Coordinates;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import com.ibm.icu.util.Calendar;


public class ImportWSToponomastica extends Command implements Rule  {
	
	private static final Logger log = Logger.getLogger(ImportWSToponomastica.class.getName());
	
	//nome della proprietà di configurazione
	public static final String WS_TOPONOMASTICA_TOKEN = "ws.toponomastica.token";
	//public static final String WS_TOPONOMASTICA_DT_INI = "ws.toponomastica.dataIniRif";
	
	private SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	
	private Connection conn=null; 
	private String enteID;
	
	private String SQL_CLEAN_RE_WS_CIVICO="DELETE FROM RE_WS_CIVICO WHERE RE_FLAG_ELABORATO='0' and cod_stato in (2,99)";
	private String SQL_CLEAN_RE_WS_STRADA="DELETE FROM RE_WS_STRADA WHERE RE_FLAG_ELABORATO='0'";
	
	private String SQL_DATA_INI_RIF;
	private String SQL_CREATE_RE_WS_CIVICO;
	private String SQL_CREATE_RE_WS_STRADA;
	
	public ImportWSToponomastica(BeanCommand bc) {
		super(bc);
	}

	public ImportWSToponomastica(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		log.debug("ImportWSToponomastica run()");
		CommandAck retAck = null; 
		enteID= ctx.getBelfiore();
		log.debug("ENTE IN ELABORAZIONE (DA CTX): " + enteID);
		conn = ctx.getConnection((String)ctx.get("connessione"));
		
		//recupero del path locale dell ente/fd
		ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
		
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setApplication("Controller");
		criteria.setInstance("Controller");
		criteria.setComune(enteID);
		criteria.setKey(WS_TOPONOMASTICA_TOKEN);
		
		AmKeyValueExt param = cdm.getAmKeyValueExt(criteria);
		String token = param!=null ? param.getValueConf() : null;
		log.info("Token WS: "+token);
		
	
		PreparedStatement pst =null; ResultSet rs =null; 
		
		try {
			this.getJellyParam(ctx);
			
			this.createTabelleRe(conn);
			
			log.info("Database Username: " + conn.getMetaData().getUserName());
			
			log.info("Query SQL_DATA_INI_RIF da eseguire:\n"+ SQL_DATA_INI_RIF);
			pst = conn.prepareStatement(SQL_DATA_INI_RIF);
			rs = pst.executeQuery();
			
			Date dataIniRif = null;
			if(rs.next())
			   dataIniRif = rs.getDate("DATA_INI_RIF");
				
			RequestDTO request = new RequestDTO();
			request.setToken(token);
			
			
			this.popolaTabTempVie(ctx, request);
			
			 if(dataIniRif!=null){
				 log.debug("Carico i civici variati dal "+SDF.format(dataIniRif));
				 request.setDataIniRif(dataIniRif);
				 request.setDataFinRif(new Date());
				 
				 this.popolaTabTempCiviciVariati(ctx, request);
			 }else{
				 log.debug("Carico TUTTI i civici");
				 this.popolaTabTempCiviciAll(ctx, request);
			 }
			
		
			
		}catch (SQLException e) {
			log.error("ImportWSToponomastica - ERRORE SQL " + e, e);
			ErrorAck ea = new ErrorAck(e);
			return ea;
		}catch(Exception eg){
			log.error("ImportWSToponomastica - ERRORE Generico " + eg, eg);
			ErrorAck ea = new ErrorAck(eg);
			return ea;
		}finally {
			try {
				DbUtils.close(rs);
				DbUtils.close(pst);
			}
			catch (SQLException sqle) {
				log.error("ImportWSToponomastica - ERRORE CHIUSURA OGGETTI SQL", sqle);
			}
		}
		
		
		retAck = new ApplicationAck("ImportWSToponomastica - ESECUZIONE OK");
		return retAck;
	}
	
	private void popolaTabTempVie(Context ctx, RequestDTO request) throws Exception{
		PreparedStatement pst=null;
		try{
			
			//Cancello i dati dalla tabella temporanea delle strade, per ricaricarle
			String delete = "TRUNCATE TABLE RE_WS_STRADA";
			pst = conn.prepareStatement(delete);
			pst.execute();
			
			log.debug("Ricerca In Corso di Vie...");
			ResponseDTO res = WsSITClient.sitGetVieFtopo(request);
				
			List<ViaDTO> listaVie = res.getListaVie();
			log.debug("Individuate "+listaVie.size()+" vie!");
			boolean procedi = !res.getFlgErrore() ;
			
			if(procedi){
				
				String processId =ctx.getProcessID();
				String flgElaborato = "0";
				Date dtExpDato = new Date();
				
				String sqlReWsIns = "INSERT INTO RE_WS_STRADA VALUES(";
				
				for(int i=1; i<12; i++){
					sqlReWsIns += " ?,";
				}
				sqlReWsIns += " ? )";
				
			
				pst = conn.prepareStatement(sqlReWsIns);
				
				for(ViaDTO civ : listaVie){
					int index = 1;
					
					pst.setBigDecimal(index++, new BigDecimal(civ.getCodVia()));
					pst.setString(index++, civ.getToponimo());
					pst.setString(index++, civ.getTopoVia());
					pst.setString(index++, civ.getTopo1());
					pst.setString(index++, civ.getTopo2());
					pst.setString(index++, civ.getTopo3());
					pst.setString(index++, civ.getTopo4());
					pst.setString(index++, civ.getTopo5());
					pst.setString(index++, ctx.getBelfiore());
					pst.setString(index++, flgElaborato);
					pst.setString(index++, processId);
					pst.setDate(index++, new java.sql.Date(dtExpDato.getTime()));
					
					pst.executeUpdate();
					
				}
				
				log.info("La tabella RE_WS_STRADA è stata caricata con i dati provenienti dal Web Service");
			
		}else{
			throw new Exception("RE_WS_STRADA - Errore generato dal Web Service: "+res.getErrCode()+" - "+res.getErrDescrizione());
		}
	
		}catch(Exception e){
			throw e;
		}finally{
			DbUtils.close(pst);
		}
		
	}
	
	private void insertCivici(Context ctx, ResponseDTO res) throws Exception{
		
		PreparedStatement pst = null;
		
		try{
		
		List<CivicoDTO> listaCivici = res.getListaCivici();
		log.debug("Individuati "+listaCivici.size()+" civici");
		boolean procedi = !res.getFlgErrore();
		
		if(procedi){
			
			String processId =ctx.getProcessID();
			String flgElaborato = "0";
			Date dtExpDato = new Date();
			
			String sqlReWsIns = "INSERT INTO RE_WS_CIVICO VALUES(";
			
			for(int i=1; i<28; i++){
				sqlReWsIns += " ?,";
			}
			sqlReWsIns += " ? )";
			
		
			pst = conn.prepareStatement(sqlReWsIns);
			
			for(CivicoDTO civ : listaCivici){
				String codStato = civ.getCodStato();
				
				//Inserisco solo i record con stato valido o cessato
				if("2".equals(codStato)||"99".equals(codStato)){
					int index = 1;
					
					Double dx= civ.getCoordx()!=null ? Double.parseDouble(civ.getCoordx().replace(',', '.')): null;
					Double dy= civ.getCoordy()!=null ? Double.parseDouble(civ.getCoordy().replace(',', '.')): null;
					
				/*	String srid = null;
					if(dx!=null && dy!=null)
						srid = Coordinates.getCoordSystem(dx, dy);*/
				
					pst.setBigDecimal(index++, new BigDecimal(civ.getIdc()));
					//pst.setString(index++, srid);
					
					if(dx!=null) pst.setDouble(index++, dx);
					else pst.setNull(index++, java.sql.Types.DECIMAL);
					
					if(dy!=null) pst.setDouble(index++, dy);
					else pst.setNull(index++, java.sql.Types.DECIMAL);
					
					pst.setString(index++, civ.getNumCompleto());
					pst.setString(index++, civ.getToponimo());
					pst.setBigDecimal(index++, civ.getCodVia()!=null ? new BigDecimal(civ.getCodVia()) : null);
					pst.setString(index++, civ.getTopoVia());
					pst.setString(index++, civ.getTopo1());
					pst.setString(index++, civ.getTopo2());
					pst.setString(index++, civ.getTopo3());
					pst.setString(index++, civ.getTopo4());
					pst.setString(index++, civ.getTopo5());
					pst.setBigDecimal(index++, civ.getNumero()!=null ? new BigDecimal(civ.getNumero()) : null);
					pst.setString(index++, civ.getLettera());
					pst.setString(index++, civ.getBarra());
					pst.setString(index++, civ.getCodStato());
					pst.setString(index++, civ.getDescStato());
					pst.setString(index++, ctx.getBelfiore());
					pst.setDate(index++, civ.getDataApplicazione()!=null ? new java.sql.Date(civ.getDataApplicazione().getTime()) : null);
					pst.setDate(index++, civ.getDataSoppressione()!=null ? new java.sql.Date(civ.getDataSoppressione().getTime()) : null);
					pst.setString(index++, civ.getResidenziale());
					pst.setString(index++, civ.getZdn());
					pst.setString(index++, civ.getCap());
					pst.setString(index++, null);
					pst.setString(index++, civ.getUrl());
					pst.setString(index++, flgElaborato);
					pst.setString(index++, processId);
					pst.setDate(index++, new java.sql.Date(dtExpDato.getTime()));
					
					pst.executeUpdate();
				}
			}
			
		}else{
			throw new Exception("RE_WS_CIVICO - Errore generato dal Web Service: "+res.getErrCode()+" - "+res.getErrDescrizione());
		}
		}catch(Exception e){
			throw e;
		}finally{
			DbUtils.close(pst);
		}
	}
	
	private void popolaTabTempCiviciAll(Context ctx, RequestDTO request) throws Exception{
		PreparedStatement pst=null;
		ResultSet rs=null;
		try{
			
			//Cancello i dati dalla tabella temporanea dei civici, per ricericarli
			String delete = "TRUNCATE TABLE RE_WS_CIVICO";
			pst = conn.prepareStatement(delete);
			pst.execute();

			String sql = "select count(distinct cod_via) conteggio from re_ws_strada";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery(sql);
			
			int conteggio = 0;
			if(rs.next())
				conteggio = rs.getInt("CONTEGGIO");
			
			sql = "SELECT DISTINCT COD_VIA FROM RE_WS_STRADA ORDER BY COD_VIA";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery(sql);
			
			log.debug("Ricerca civici associati a num."+conteggio+" strade...");
			
			while(rs.next()){
				BigDecimal pkidStra = rs.getBigDecimal("COD_VIA");
				
				request.setCodiceVia(pkidStra.toString());
				request.setCivico(WsSITClient.CIVICI_ALL);
				request.setState(WsSITClient.STATO_APPLICATO);
				
				log.debug("Ricerca in corso civici APPLICATI per via: "+pkidStra);
				ResponseDTO res = WsSITClient.sitGetCiviciVia(request);
				this.insertCivici(ctx, res);
				
				//Ricerco anche i civici SOPPRESSI 
				log.debug("Ricerca in corso civici SOPPRESSI per via: "+pkidStra);
				request.setState(WsSITClient.STATO_SOPPRESSO);
				ResponseDTO resSoppressi = WsSITClient.sitGetCiviciVia(request);
				this.insertCivici(ctx, resSoppressi);
				
				conteggio--;
				
				if(conteggio % 50==0)
					log.debug("Elaborazione di "+conteggio+" vie rimanenti...");
				
			}
			
			log.info("La tabella RE_WS_CIVICO è stata caricata con i dati provenienti dal Web Service");
			
		}catch(Exception e){
			throw e;
		}finally{
			DbUtils.close(pst);
		}
		
	}
	
	private void popolaTabTempCiviciVariati(Context ctx, RequestDTO request) throws Exception{
		log.debug("Ricerca In Corso di Civici...");
		ResponseDTO res = WsSITClient.sitGetCivicoChangeDati(request);
		this.insertCivici(ctx, res);
		log.info("La tabella RE_WS_CIVICO è stata caricata con i dati provenienti dal Web Service");
	}
	
	
	private void createTabelleRe(Connection con){
		Statement st = null;
		try {
			st = con.createStatement();
			String sql = this.SQL_CREATE_RE_WS_CIVICO;
			log.debug(this.SQL_CREATE_RE_WS_CIVICO);
			st.execute(sql);
		} catch (SQLException e1) {
			log.warn("Tabella RE_WS_CIVICO esiste già. Rimuovo dati sporchi di precedenti caricamenti. ");
			try{
				st.execute(this.SQL_CLEAN_RE_WS_CIVICO);
			}catch(SQLException e11){}
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		
		try {
			st = con.createStatement();
			String sql = this.SQL_CREATE_RE_WS_STRADA;
			log.debug(this.SQL_CREATE_RE_WS_STRADA);
			st.execute(sql);
		} catch (SQLException e1) {
			log.warn("Tabella RE_WS_STRADA esiste già. Rimuovo dati sporchi di precedenti caricamenti.");
			try{
				st.execute(this.SQL_CREATE_RE_WS_STRADA);
			}catch(SQLException e11){}
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		
	}
	
	
	private void getJellyParam(Context ctx) throws Exception {
		try {
			
			int index = 1;
			log.debug("*******************************INIZIO CARICAMENTO PARAMETRI****************************************");
			
			this.SQL_DATA_INI_RIF = getJellyParam(ctx, index++, "SQL_DATA_INI_RIF");
			this.SQL_CREATE_RE_WS_STRADA = getJellyParam(ctx, index++, "SQL_CREATE_RE_WS_STRADA");
			this.SQL_CREATE_RE_WS_CIVICO = getJellyParam(ctx, index++, "SQL_CREATE_RE_WS_CIVICO");
			
			log.debug("*******************************FINE CARICAMENTO PARAMETRI****************************************");
			
		}catch (Exception e) {
			log.error("Exception: " + e.getMessage());
			throw e;
		}
	}
	
	
	private String getJellyParam(Context ctx, int index, String desc) throws Exception{
		
		String variabile = "";
		
		log.info("rengine.rule.param.in."+index+".descr");
		
		ComplexParam paramSql= (ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in."+index+".descr"));
		
		HashMap<String,ComplexParamP> p = paramSql.getParams();
		Set set = p.entrySet();
		Iterator it = set.iterator();
		int i = 1;
		while (it.hasNext()) {
			Entry entry = (Entry)it.next();
			ComplexParamP cpp = (ComplexParamP)entry.getValue();
			Object o = TypeFactory.getType(cpp.getValore(),cpp.getType());
			variabile = o.toString();
		}
		
		log.info("Query "+desc+" da eseguire:"+ variabile);
		
		return variabile;
	}

}
