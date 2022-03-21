package it.webred.rulengine.diagnostics.superclass;

import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.data.access.basic.fonti.FontiDataIn;
import it.webred.ct.data.access.basic.fonti.FontiService;
import it.webred.ct.data.access.basic.fonti.dto.FontiDTO;
import it.webred.rulengine.Context;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.diagnostics.bean.DiagnosticConfigBean;
import it.webred.rulengine.diagnostics.bean.ListDiagnostics;
import it.webred.rulengine.diagnostics.bean.ParamInValue;
import it.webred.rulengine.diagnostics.utils.ChkDiagnostics;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.def.TypeFactory;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

/**
 * La classe elabora la diagnostica ed inserisce i risultati nelle tabelle di testata e dettaglio
 * specificate nel file xml di configurazione
 * @author Luca
 *
 */
public abstract class ElaboraDiagnostics {
	
	private Connection conn = null;
	private Context ctx = null;
	private List<RRuleParamIn> params = null;
	private List<ParamInValue> paramsInValue = null;
	private Date   dataRiferimento = null;
	private String processId = "";
	private String codBelfioreEnte = "";
	private String codCommand = "";
	
	private List<RRuleParamIn> paramsChain = null;
	private List<ParamInValue> paramsChainInValue = null;

	protected static org.apache.log4j.Logger log = Logger.getLogger(ElaboraDiagnostics.class.getName());
	protected HashMap<String, Object> datiDiaTestata4Upd;
		
	public String NUM_REC = "NUM_REC";
	public String DES_PARAM = "DES_PARAM";	
	public String DES_SQL = "DES_SQL";	
		
	public String getCodCommand() {
		return codCommand;
	}

	public String getCodBelfioreEnte() {
		return codBelfioreEnte;
	}

	public Context getCtx() {
		return ctx;
	}

	public void setCtx(Context ctx) {
		this.ctx = ctx;
	}

	public List<RRuleParamIn> getParams() {
		return params;
	}

	public void setParams(List<RRuleParamIn> params) {
		this.params = params;
	}

	public List<ParamInValue> getParamsInValue() {
		return paramsInValue;
	}

	public void setParamsInValue(List<ParamInValue> paramsInValue) {
		this.paramsInValue = paramsInValue;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
	public Date getDataRiferimento() {
		if (dataRiferimento == null)
			return new Date();
		else
			return dataRiferimento;
	}

	public void setDataRiferimento(Date dataRiferimento) {
		this.dataRiferimento = dataRiferimento;
	}
	
	public ElaboraDiagnostics(){
		
	}
	
	public ElaboraDiagnostics(Connection connPar, Context ctxPar, List<RRuleParamIn> paramsPar){
		//
		log.debug("[ElaboraDiagnostics()]");
		conn = connPar;
		ctx = ctxPar;
		params = paramsPar;
		processId = ctxPar.getProcessID();
		codBelfioreEnte = ctxPar.getBelfiore();
		codCommand = (String)ctx.getDeclarativeType("RULENGINE.COD_COMMAND").getValue();
		datiDiaTestata4Upd = new HashMap<String, Object>();				
	}
	/*
	public ElaboraDiagnostics(Connection connPar, Context ctxPar, List<RRuleParamIn> paramsPar, List<RRuleParamIn> paramsChainPar){
		this(connPar, ctxPar, paramsPar);
		paramsChain = paramsChainPar; 
		if (paramsChain == null)
			log.debug("[ElaboraDiagnostics()] paramsChain null");
		else
			log.debug("[ElaboraDiagnostics()] paramsChain.size() " + paramsChain.size());
	}
	*/
	public void ExecuteDiagnostic(String codCommand) throws Exception {
		try {
			log.info("[EXECUTEDIAGNOSTIC] - START");
			ListDiagnostics listaDia = ChkDiagnostics.VerificaEsecuzione(conn,codCommand);
	
			GetValueParamInFromContext(); 
			if (listaDia== null || listaDia.getDiagnostics() ==null ) {
				log.info("[EXECUTEDIAGNOSTIC] - END-NESSUNA DIAGNOSTICA DEFINITA NEL GRUPPO ");
				return;
			}
			for (DiagnosticConfigBean diaConfig : listaDia.getDiagnostics()) {
				
				if (!diaConfig.isExecute()){
					log.info("[EXECUTEDIAGNOSTIC] - DIAGNOSTICA NON ESEGUITA : " + diaConfig.toString());
					continue;
				}
				long idDiaTestata = SalvaTestata(diaConfig);
				
				Object result = getResult(diaConfig);
				if (result instanceof ResultSet)
					CaricaFromResultSet((ResultSet)result,diaConfig,idDiaTestata);
				else if (result instanceof HashMap<?, ?>){		
					ElaborazioneNonStandard(diaConfig,idDiaTestata);
				}
				else
					log.info(" Oggetto " + result.getClass().getName() + " non gestito!");
				
				UpdateTestata(diaConfig, idDiaTestata);
				
				GestioneAltreEsecuzioni(diaConfig, idDiaTestata);
			}
			log.info("[EXECUTEDIAGNOSTIC] - END ");
						
		} catch (Exception e) {
			log.error("[EXECUTEDIAGNOSTIC] " + e.getMessage() );	
			throw e;
		}
	}
	
	protected long SalvaTestata(DiagnosticConfigBean diaConf) throws Exception{
		log.info("[SALVATESTATA] - START : " + diaConf.getIdCatalogoDia());
		long idTestata = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO DIA_TESTATA VALUES (SEQ_DIA_TESTATA.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
									
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, diaConf.getIdCatalogoDia());
			pstmt.setString(2, getProcessId());
			pstmt.setTimestamp(3, new java.sql.Timestamp((new Date()).getTime()));			
			pstmt.setString(4, getDesDataRiferimento(diaConf));
			pstmt.setNull(5, Types.NUMERIC);
			pstmt.setNull(6, Types.CHAR);
			pstmt.setNull(7, Types.CHAR);
			pstmt.setString(8, diaConf.getTableClass());
			pstmt.setString(9, diaConf.getStandard());
			pstmt.setString(10, diaConf.getFieldTableDettaglioForFK());
			pstmt.setInt(11, diaConf.getNumTipoGestione());
			pstmt.setString(12, diaConf.getNumTipoGestioneValue());
			
			pstmt.executeUpdate();
			log.info("[SALVATESTATA] - Execute eseguito ");
			
			//recupero l'id della testata					
			rs = conn.prepareStatement("SELECT SEQ_DIA_TESTATA.CURRVAL FROM DUAL").executeQuery();
			if (rs.next()) {
				idTestata = rs.getLong(1);				
			}
			log.debug("[SALVATESTATA] - Id Testata : " + idTestata);			
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (rs != null)
				rs.close();
		}
		log.info("[SALVATESTATA] - END");
		return idTestata;
	}
	
	protected int getRecordsCount(DiagnosticConfigBean diaConf, long idTestata) throws Exception {
		return 0;
	}
	
	protected void UpdateTestata(DiagnosticConfigBean diaConf, long idTestata) throws Exception{
		log.info("[UPDATETESTATA] - START - ID TESTATA: " + idTestata );
		if (datiDiaTestata4Upd == null){
			log.info("[UPDATETESTATA] - Update dati di testata non avvenuto HashMap null ");
			return;
		}
		
		if (datiDiaTestata4Upd.get(this.NUM_REC) == null){
			datiDiaTestata4Upd.put(this.NUM_REC, getRecordsCount(diaConf,idTestata));
		}
		
		PreparedStatement pstmt = null;		
		String sql = "UPDATE DIA_TESTATA SET NUM_REC=?, DES_PARAM=?, DES_SQL=? WHERE ID=? ";
		try {
									
			pstmt = conn.prepareStatement(sql);
			
			log.debug("[UPDATETESTATA] - Numero Record trovati:"+ ((Integer)datiDiaTestata4Upd.get(this.NUM_REC)).intValue());
			log.debug("[UPDATETESTATA] - Descrizione Parametri:"+ WriteDesParametri((List<ParamInValue>)datiDiaTestata4Upd.get(this.DES_PARAM)));
			log.debug("[UPDATETESTATA] - Descrizione Sql:"+ ((String)datiDiaTestata4Upd.get(this.DES_SQL)));
			
			pstmt.setInt(1, ((Integer)datiDiaTestata4Upd.get(this.NUM_REC)).intValue());
			
			if (datiDiaTestata4Upd.get(this.DES_PARAM) == null)
				pstmt.setNull(2, Types.CHAR);
			else
				pstmt.setString(2, WriteDesParametri((List<ParamInValue>)datiDiaTestata4Upd.get(this.DES_PARAM)));
			
			if (datiDiaTestata4Upd.get(this.DES_SQL) == null)
				pstmt.setNull(3, Types.CHAR);
			else
				pstmt.setString(3, ((String)datiDiaTestata4Upd.get(this.DES_SQL)));
				
			pstmt.setLong(4, idTestata);
			log.info("[UPDATETESTATA] - Execute SQL: " + sql);
			pstmt.executeUpdate();
			log.info("[UPDATETESTATA] - Execute eseguito ");
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();			
		}
		log.info("[UPDATETESTATA] - END");
	}
	
	/**
	 * Metodo usato per la gestione delle esecuzioni (Tab. Dia_Testata) nelle D. non standard.
	 * Se la D. non gestisce lo storico (quindi getFieldTableDettaglioForFK() == "") le altre testate diverse da idTestata saranno eliminate.
	 * @param diaConf
	 * @param idTestata
	 * @throws Exception
	 */	
	protected void GestioneAltreEsecuzioni(DiagnosticConfigBean diaConf, long idTestata) throws Exception{
		log.info("[GestioneAltreEsecuzioni] - START ");
		
		if (diaConf != null && diaConf.getStandard().equals("S")) return;
		if (diaConf != null && diaConf.getStandard().equals("N") && diaConf.getFieldTableDettaglioForFK().length() > 0) return;
		
		PreparedStatement pstmt = null;				
		try {	
			String sql = "DELETE FROM DIA_LOG_ACCESSI WHERE FK_DIA_TESTATA IN (SELECT ID FROM DIA_TESTATA WHERE IDCATALOGODIA=? AND ID<>?) ";
			pstmt = conn.prepareStatement(sql);		
			pstmt.setLong(1, diaConf.getIdCatalogoDia());						
			pstmt.setLong(2, idTestata);
			pstmt.executeUpdate();
			log.info("[GestioneAltreEsecuzioni] - Execute delete DIA_LOG_ACCESSI ");
			
			sql = "DELETE FROM DIA_TESTATA WHERE IDCATALOGODIA=? AND ID<>? ";
			pstmt = conn.prepareStatement(sql);	
			pstmt.clearParameters();
			pstmt.setLong(1, diaConf.getIdCatalogoDia());						
			pstmt.setLong(2, idTestata);
			pstmt.executeUpdate();
			log.info("[GestioneAltreEsecuzioni] - Execute delete DIA_TESTATA ");
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();			
		}
		log.info("[GestioneAltreEsecuzioni] - END");
	}
	
	protected abstract Object getResult(DiagnosticConfigBean diaConf) throws Exception;
	
	protected void ElaborazioneNonStandard(DiagnosticConfigBean diaConfig, long idTestata) throws Exception {}
	
	private String getDesDataRiferimento(DiagnosticConfigBean diaConf){		
		log.info("[getDesDataRiferimento] - START");
		StringBuilder sb = new StringBuilder();
		StringBuilder sbDatRif;	
		StringBuilder sbDatAgg;
		ResultSet rsFonti = null;
		ResultSet rsd= null;
		ResultSet rsSql= null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		try {
			
			FontiService fntService = (FontiService) ServiceLocator.getInstance()
			.getService("CT_Service", "CT_Service_Data_Access", "FontiServiceBean");
			FontiDataIn fontiDataIn = new FontiDataIn();
			fontiDataIn.setEnteId(codBelfioreEnte);
			
			//Recupero la lista delle fonti dal codCommand
			String sql =  "select rc.id, rfc.id_fonte from r_command rc ";
			sql +=" inner join r_fontedati_command rfc on rc.id=rfc.fk_command ";
			sql +=" where rc.cod_command = '"+codCommand+"' ";				
			
			log.info("[getDesDataRiferimento] - Recupero id fonte:" + sql);
			rsFonti = conn.prepareStatement(sql).executeQuery();
			while (rsFonti.next()) {
				sbDatRif = new StringBuilder();	
				sbDatAgg = new StringBuilder();
				
				boolean findDatRif = false;
				boolean findDatAgg = false;
				int idFonte = rsFonti.getInt(2);
				
				// Recupero la data di riferimento
				fontiDataIn.setIdFonte(String.valueOf(idFonte));
				
				try{
					FontiDTO fontiDTO = fntService.getDateRiferimentoFonte(fontiDataIn);
					if (fontiDTO != null && fontiDTO.getDataRifInizio() != null){		
						log.info("[getDesDataRiferimento] - trovata. ");
						sbDatRif.append(" data rifer. da:[");							
						sbDatRif.append(sdf.format(fontiDTO.getDataRifInizio()));																
						sbDatRif.append("] ");
						if(fontiDTO.getDataRifAggiornamento() != null){
							sbDatRif.append(" data rifer. a:[");							
							sbDatRif.append(sdf.format(fontiDTO.getDataRifAggiornamento()));											
							sbDatRif.append("] ");
						}
						findDatRif = true;
					}else {
						log.info("[getDesDataRiferimento] - NON trovata: ");
					}						
				}catch (Exception e) {
					log.error("[getDesDataRiferimento] - Errore reperimento data di riferimento: "+e.getMessage());
				}				
				
				// Recupero la data di aggiornamento
				sql = "select rts.istante from R_Traccia_Stati rts where rts.belfiore = '"+codBelfioreEnte+"' and rts.id_fonte ="+idFonte;
				sql+= " order by rts.istante desc";
				log.info("[getDesDataRiferimento] - Recupero data aggiornamento:" + sql);
				rsd = conn.prepareStatement(sql).executeQuery();
				if (rsd.next()){
					findDatAgg = true;
					sbDatAgg.append(" data agg.:[");
					sbDatAgg.append(sdf.format(new Date(rsd.getLong(1))));					
					sbDatAgg.append("] ");
				}
				rsd.close();
				
				if (findDatAgg || findDatRif){
					sb.append(" Fonte:[");
					sb.append(idFonte);
					sb.append("] ");
					if (findDatRif) sb.append(sbDatRif);
					if (findDatAgg)	sb.append(sbDatAgg);
					
				}						
			}
								
		} catch (Exception e) {
			log.error("[getDesDataRiferimento] - Errore recupero date fonti dati: "+e.getMessage());
			return "";
		}finally{
			try{
				if (rsSql != null) rsSql.close();						
				if (rsd != null) rsd.close();
				if (rsFonti != null) rsFonti.close();
			} catch (Exception e) {
				
			}
		}	
		log.debug("[getDesDataRiferimento] - StringBuilder data riferimento:" + sb.toString());
		return sb.toString();
		
	}
	
	private String FormatData(ResultSet rs){
		try{
			if (rs == null || rs.isClosed()) return "";
			ResultSetMetaData metadata = rs.getMetaData();
			int columnType = metadata.getColumnType(1);
			if(columnType == Types.CHAR || columnType == Types.VARCHAR || columnType == Types.LONGVARCHAR)
            {
				if (rs.getObject(1) != null){
					String dt = rs.getString(1);
					return dt.substring(0,2)+"/"+dt.substring(2, 4)+"/"+dt.substring(4,8);
				}
            }else if(columnType == Types.INTEGER || columnType == Types.BIGINT || columnType == Types.SMALLINT || columnType == Types.NUMERIC)
            {
            	if (rs.getObject(1) != null){
            		String dt = String.valueOf(rs.getLong(1));
            		return dt.substring(0,2)+"/"+dt.substring(2, 4)+"/"+dt.substring(4,8);
				}
            }else if(columnType == Types.TIME || columnType == Types.TIMESTAMP || columnType == Types.DATE)
            {
            	if (rs.getObject(1) != null){
            		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            		return sdf.format(new Date(rs.getTimestamp(1).getTime()));
				}
            }
		}catch (Exception e){
			
		}
		
		return "";
	}
	
	private String WriteDesParametri(List<ParamInValue> param){
		if (param == null) return "";
		StringBuilder sb = new StringBuilder("|");
		for (ParamInValue parIn : param) {
			sb.append( parIn.getParamValue().toString() + "|");
		}
		return sb.toString();
	}
	
	private void CaricaFromResultSet(ResultSet rs,DiagnosticConfigBean diaConf, long idDiaTestata) throws Exception{
		log.info("[CARICAFROMRESULTSET] - START ");
		PreparedStatement pstmt = null;				
		ResultSetMetaData metadata = null;
		int columnType;
		int cnt = 0;
		try {		
			
			//Costruisco la insert per il prepareStatement
			StringBuilder sql = new StringBuilder("INSERT INTO "+ diaConf.getTableNameDettaglio() );
			sql.append(" VALUES ("+diaConf.getSeqForDettaglio()+".NEXTVAL,?,");			
			metadata = rs.getMetaData();
			log.debug("[CARICAFROMRESULTSET] - Né colonne da metadati:" + metadata.getColumnCount());
			for (int i = 1; i <= metadata.getColumnCount(); i++) {													
				if (i != metadata.getColumnCount())
					sql.append("?,");
				else
					sql.append("?");
			}		
			sql.append(" )");
			log.debug("[CARICAFROMRESULTSET] - Insert dati dettaglio :" + sql.toString());
			pstmt = conn.prepareStatement(sql.toString());			
			while (rs.next()){
				
				pstmt.clearParameters();
				pstmt.setLong(1, idDiaTestata);	
				
				for (int i = 1; i <= metadata.getColumnCount(); i++) {
					
					columnType = metadata.getColumnType(i);
					
					log.debug(columnType+" "+rs.getObject(i));
					
					if(columnType == Types.CHAR || columnType == Types.VARCHAR || columnType == Types.LONGVARCHAR)
                    {
						if (rs.getObject(i) != null)
							pstmt.setString((i+1), rs.getString(i));
						else
							pstmt.setNull((i+1), java.sql.Types.VARCHAR);
                    }else if(columnType == Types.INTEGER || columnType == Types.BIGINT || columnType == Types.SMALLINT || columnType == Types.NUMERIC)
                    {
                    	if (rs.getObject(i) != null)
							pstmt.setLong((i+1), rs.getLong(i));
						else
							pstmt.setNull((i+1), java.sql.Types.NUMERIC);
                    }
                    else if(columnType == Types.DECIMAL || columnType == Types.DOUBLE || columnType == Types.FLOAT || columnType == Types.REAL)
                    {
                    	if (rs.getObject(i) != null)
							pstmt.setDouble((i+1), rs.getDouble(i));
						else
							pstmt.setNull((i+1), java.sql.Types.DOUBLE);
                    }else if(columnType == Types.TIME || columnType == Types.TIMESTAMP || columnType == Types.DATE)
                    {
                    	if (rs.getObject(i) != null)
							pstmt.setDate((i+1), rs.getDate(i));
						else
							pstmt.setNull((i+1), java.sql.Types.DATE);
                    }		
				}
				
				cnt++;
				pstmt.executeUpdate();
				
				//TODO: Commentare il produzione 
				//if (cnt > 100) break;				
				
			}
			datiDiaTestata4Upd.put(this.NUM_REC, new Integer(cnt));
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (rs != null)
				rs.close();
		}			
		log.info("[CARICAFROMRESULTSET] - END ");
	}
	
	private void GetValueParamInFromContext()
	{
		if (params == null || params.size() == 0) {
			log.info("[GETVALUEPARAMINFROMCONTEXT] - Parametri in input non presenti");
			return;
		}
		
		try {
			
			paramsInValue = new ArrayList<ParamInValue>();
			for (RRuleParamIn parIn : params) {
				ParamInValue obj = new ParamInValue(parIn);
				
				ComplexParam cp = (ComplexParam)ctx.get(obj.getDescr());
				HashMap<String,ComplexParamP> p = cp.getParams();
				Set set = p.entrySet();
				Iterator it = set.iterator();
				while (it.hasNext()) {
					Entry entry = (Entry)it.next();
					ComplexParamP cpp = (ComplexParamP)entry.getValue();
					Object o = TypeFactory.getType(cpp.getValore(),cpp.getType());
					obj.setParamValue(o.toString());
				}
				
				log.debug("[GETVALUEPARAMINFROMCONTEXT] - Par ctx : " + obj.getDescr() + " Value:" + obj.getParamValue());
				
				paramsInValue.add(obj);
			}
		} catch (Exception e) {
			log.error("Eccezione recupero valori da contesto: "+e.getMessage(),e);
			paramsInValue = null;
		}
		
	}
		
	public int getNumParam(String query){
		int numParam = 0;		
		String searchFor = "?";		 
		int len = searchFor.length();		
		if (len > 0) {  
			int start = query.indexOf(searchFor);
			while (start != -1) {
				numParam++;
				start = query.indexOf(searchFor, start+len);
			}
		}
		
		return numParam;
	}
	
	protected void callPreparedStatementSetMethod(PreparedStatement st, ParamInValue parIn, int column)
	throws IllegalArgumentException, SQLException {
		if (parIn.getType().equalsIgnoreCase(java.lang.String.class.getName())) {
			if (parIn.getParamValue() == null)
				st.setNull(column, Types.CHAR);
			else
				st.setString(column, String.valueOf(parIn.getParamValue()));			
		}else if (parIn.getType().equalsIgnoreCase(java.lang.Integer.class.getName())) {
			if (parIn.getParamValue() == null)
				st.setNull(column, Types.INTEGER);
			else
				st.setInt(column, Integer.parseInt(parIn.getParamValue().toString()));	
		}else if (parIn.getType().equalsIgnoreCase(java.lang.Double.class.getName())) {
			if (parIn.getParamValue() == null)
				st.setNull(column, Types.NUMERIC);
			else
				st.setBigDecimal(column, new BigDecimal(parIn.getParamValue().toString()));	
		}else if (parIn.getType().equalsIgnoreCase(java.util.Date.class.getName())) {
			try {
			if (parIn.getParamValue() == null)
				st.setNull(column, Types.DATE);
			else{				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				st.setDate(column, new java.sql.Date(sdf.parse(parIn.getParamValue().toString().trim()).getTime()));			
			}
				st.setBigDecimal(column, new BigDecimal(parIn.getParamValue().toString()));	
			} catch (Exception nfe) {
				throw new IllegalArgumentException(parIn.getParamValue().toString() + " non è una data valida.");
			}
		}else
			throw new IllegalArgumentException("Tipo " + parIn.getType() + " sconosciuto.");	
	}
		
	public String getDiaProperty(String propName) {
        String fileName =  "dia.properties";
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream is = cl.getResourceAsStream(fileName);
        Properties props = new Properties();        
        try {
        	props.load(is);
        } catch (Exception e) {
			return null;
		}        
        String p = props.getProperty(propName);
        return p;
	}
			
}
