package it.webred.rulengine.brick.dia;


import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.dia.bean.tributidocfa.DocfaAnomalie;
import it.webred.rulengine.brick.dia.bean.tributidocfa.DocfaDatiCensuariBean;
import it.webred.rulengine.brick.dia.bean.tributidocfa.DocfaIciReportBean;
import it.webred.rulengine.brick.dia.bean.tributidocfa.DocfaIciReportSoggBean;
import it.webred.rulengine.brick.superc.dia.AbstractDiagnosticsExport;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.def.TypeFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

/**
 * Valuta i docfa rilevati dalle tabelle DOCFA_ICI_REPORT e DOCFA_UIU
 * per la ricerca di agganci ed eventuali altre informazioni
 */
public class DocfaIciReport  extends AbstractDiagnosticsExport implements Rule {
	
	private static final org.apache.log4j.Logger log = Logger.getLogger(DocfaIciReport.class.getName());
	private final Boolean NON_ELABORATO = null;
	private final String SEPARATORE = "@";
	private final String PIPE = "|";
	private final String SOPPRESSA = "S";
	private final String VARIATA = "V";
	private final String COSTITUITA = "C";
	
	private final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	
	private final String DOCFA_NULL = "-";
	private final String ICI_NULL = "0000";
	private final String CAT_NULL = "0";
	
	protected PreparedStatement psCiviciCatasto = null;
	protected PreparedStatement psCiviciIndiceCD = null;
	protected PreparedStatement psTitUiuAnnoDocfa = null;
	protected PreparedStatement psTitIndiceCA = null;
	protected PreparedStatement psResidenzaTitolare = null;
	protected PreparedStatement psResidenzaCivicoDocfa = null;
	protected PreparedStatement psResidenzaCivicoCatasto = null;
	protected PreparedStatement psDocfaInAnno = null;
	protected PreparedStatement psDocfaSucc = null;
	protected PreparedStatement psDocfaContemporanei = null;
	protected PreparedStatement psDocfaSuccessivo = null;
	protected PreparedStatement psCatasto = null;
	protected PreparedStatement psCatastoPost = null;
	protected PreparedStatement psDocfaDatiGenerali = null;
	protected PreparedStatement psDocfaDichiarante = null;
	protected PreparedStatement psDocfaDatiCensuari = null;
	protected PreparedStatement psDocfaDataregistrazione = null;		
	protected PreparedStatement psFamiliariDataDocfa = null;
	
	private PreparedStatement psIciSuccessivo = null;
	private PreparedStatement psIciPrecedente = null;
	private PreparedStatement psVerificaClassamento = null;

	private PreparedStatement psTitIciSuccessivo = null;
	private PreparedStatement psTitIciPrecedente = null;
	private PreparedStatement psTitIciSuccessivoUiu = null;
	private PreparedStatement psTitIciPrecedenteUiu = null;
	private PreparedStatement psTitIciSuccessivoCiv = null;
	private PreparedStatement psTitIciPrecedenteCiv = null;
	
	private PreparedStatement psDocfaIciReport = null;
	private PreparedStatement psDocfaIciReportSumPre = null;
	private PreparedStatement psDocfaIciReportSumPost = null;
	private PreparedStatement psUpdateSumImponibile = null;

	/*private PreparedStatement psFamIciSuccessivo = null;
	private PreparedStatement psFamIciPrecedente = null;
	private PreparedStatement psFamIciSuccessivoUiu = null;
	private PreparedStatement psFamIciPrecedenteUiu = null;
	private PreparedStatement psFamIciSuccessivoCiv = null;
	private PreparedStatement psFamIciPrecedenteCiv = null;*/
	
	protected String SQL_DOCFA = null;
	protected String SQL_DOCFA_DATI_CENSUARI = null;
	protected String SQL_DOCFA_DATI_GENERALI = null;
	protected String SQL_DOCFA_DICHIARANTE = null;
	protected String SQL_DOCFA_IN_ANNO = null;
	protected String SQL_DOCFA_SUCC = null;
	protected String SQL_DOCFA_SUCCESSIVO = null;
	protected String SQL_CIVICI_UIU_CATASTO = null;
	protected String SQL_INDICE_CIVICO_CatDocfa = null;
	protected String SQL_UIU_CATASTO = null;
	protected String SQL_UIU_CATASTO_POST = null;
	protected String SQL_TITOLARI_UIU_ANNO_DOCFA = null;
	protected String SQL_INDICE_TITOLARE_CatAnagrafe = null;
	protected String SQL_RESIDENZA_TITOLARE = null ;
	protected String SQL_INDICE_RESIDENZA_CIVICO_CATASTO = null;
	protected String SQL_INDICE_RESIDENZA_CIVICO_DOCFA = null;
	protected String SQL_DOCFA_CONTEMPORANEI = null;
	protected String SQL_DOCFA_DATA_REGISTRAZIONE = null;
	
	private String SQL_VERIFICA_CLASSAMENTO = null;
	private String SQL_ICI_PRECEDENTE = null;
	
	private String SQL_ICI_SUCCESSIVO = null;
	
	protected String SQL_FAMILIARI_DATA_DOCFA = null;	
	private String SQL_SOGG_ICI_PRECEDENTE_UIU = null;
	private String SQL_SOGG_ICI_SUCCESSIVO_UIU = null;
	private String SQL_SOGG_ICI_PRECEDENTE_CIV = null;
	private String SQL_SOGG_ICI_SUCCESSIVO_CIV = null;
	private String SQL_SOGG_ICI_PRECEDENTE_UND = null;
	private String SQL_SOGG_ICI_SUCCESSIVO_UND = null;
	
	private String SQL_DOCFA_ICI_REPORT = null;
	private String SQL_DOCFA_ICI_REPORT_SUM_PRE= null;
	private String SQL_DOCFA_ICI_REPORT_SUM_POST= null;
	private String SQL_UPDATE_SUM_IMPONIBILE= null;
	

	public DocfaIciReport(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	@Override
	public CommandAck run(Context ctx) throws CommandException {
		String idElaborazione = null;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		ResultSet rsIciSuccessivo = null;
		ResultSet rsDocfaSuccessivo = null;
		
		log.info("DocfaIciReport in esecuzione...");
		
		try {
			log.debug("Recupero parametro da contesto");
			conn = ctx.getConnection((String)ctx.get("connessione"));
			
			//recupero parametri catena jelly
			this.getJellyParam(ctx);
			
			//recupero eventuali parametri di ingresso
			//PreparedStatement pstmt = null;
			List<RAbNormal> abnormals = new ArrayList<RAbNormal>();
			
		try { 
			psDocfaDichiarante = conn.prepareCall(this.SQL_DOCFA_DICHIARANTE ,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			psDocfaDatiCensuari = conn.prepareCall(this.SQL_DOCFA_DATI_CENSUARI ,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			psDocfaDatiGenerali = conn.prepareCall(this.SQL_DOCFA_DATI_GENERALI,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			psDocfaInAnno = conn.prepareCall(this.SQL_DOCFA_IN_ANNO, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			psDocfaSucc = conn.prepareCall(this.SQL_DOCFA_SUCC, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			psIciSuccessivo = conn.prepareCall(this.SQL_ICI_SUCCESSIVO, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			psDocfaSuccessivo = conn.prepareCall(this.SQL_DOCFA_SUCCESSIVO, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			psIciPrecedente = conn.prepareCall(this.SQL_ICI_PRECEDENTE, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			psCatasto = conn.prepareCall(this.SQL_UIU_CATASTO, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			psCatastoPost = conn.prepareCall(this.SQL_UIU_CATASTO_POST, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			psVerificaClassamento = conn.prepareCall(this.SQL_VERIFICA_CLASSAMENTO, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			
			psCiviciCatasto = conn.prepareCall(this.SQL_CIVICI_UIU_CATASTO, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			psCiviciIndiceCD = conn.prepareCall(this.SQL_INDICE_CIVICO_CatDocfa, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			
			psTitUiuAnnoDocfa = conn.prepareCall(this.SQL_TITOLARI_UIU_ANNO_DOCFA, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			psTitIndiceCA = conn.prepareCall(this.SQL_INDICE_TITOLARE_CatAnagrafe, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			psResidenzaTitolare = conn.prepareCall(this.SQL_RESIDENZA_TITOLARE, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
		
			psResidenzaCivicoCatasto = conn.prepareCall(this.SQL_INDICE_RESIDENZA_CIVICO_CATASTO, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			psResidenzaCivicoDocfa = conn.prepareCall(this.SQL_INDICE_RESIDENZA_CIVICO_DOCFA, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			
			psDocfaContemporanei = conn.prepareCall(this.SQL_DOCFA_CONTEMPORANEI, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			
			psTitIciPrecedenteUiu = conn.prepareCall(this.SQL_SOGG_ICI_PRECEDENTE_UIU, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			psTitIciSuccessivoUiu = conn.prepareCall(this.SQL_SOGG_ICI_SUCCESSIVO_UIU, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			psTitIciPrecedente = conn.prepareCall(this.SQL_SOGG_ICI_PRECEDENTE_UND, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			psTitIciSuccessivo = conn.prepareCall(this.SQL_SOGG_ICI_SUCCESSIVO_UND, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			psTitIciPrecedenteCiv = conn.prepareCall(this.SQL_SOGG_ICI_PRECEDENTE_CIV, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			psTitIciSuccessivoCiv = conn.prepareCall(this.SQL_SOGG_ICI_SUCCESSIVO_CIV, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			psFamiliariDataDocfa = conn.prepareCall(this.SQL_FAMILIARI_DATA_DOCFA, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			
			psDocfaDataregistrazione = conn.prepareCall(this.SQL_DOCFA_DATA_REGISTRAZIONE,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			
			//Seleziono i Docfa da elaborare
			log.debug("Selezione dei DOCFA da elaborare");
			cs = conn.prepareCall(this.SQL_DOCFA, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			rs = cs.executeQuery();

			boolean primorecord=true;
			Boolean tab = null;
			Boolean tabSoggetti = null;
			
			Boolean misureDocfaValide = null;
			Boolean misureIciSuccValide = null;
		  
			int itot = 0;
			while (rs.next()) {
				
				DocfaIciReportBean rep = new DocfaIciReportBean();
				//String annotazioni = "";
				String viaDocfa = null;
				String civici[] = null;
				
				//Dichiarazione ICI presentata a seguito di aggiornamento DOCFA
				Boolean presenzaIciSuccessivo = this.NON_ELABORATO;
				Boolean varRenditaDocfaAIci = this.NON_ELABORATO;
				Boolean varClsDocfaAIci = this.NON_ELABORATO;
				Boolean varCatDocfaAIci = this.NON_ELABORATO;
				BigDecimal[] maxVarRenditaDocfaAIci = {null,null};
				BigDecimal[] maxVarPercRenditaDocfaAIci = {null,null};
				
				rep.setFlgAnomalie(false);
				rep.setFlgElaborato(false);
				ArrayList<DocfaIciReportSoggBean> titolari = new ArrayList<DocfaIciReportSoggBean>();
				
				//Dati da Tabella Unione
				rep.setFornitura(rs.getDate("FORNITURA"));
				rep.setProtocolloDocfa(rs.getString("PROTOCOLLO_REGISTRAZIONE"));
				rep.setCodEnte(ctx.getBelfiore());
				rep.setSezione(rs.getString("SEZIONE"));
				rep.setFoglio(rs.getString("FOGLIO"));
				rep.setParticella(rs.getString("NUMERO"));
				
				String subalterno = rs.getString("SUBALTERNO").trim();
				subalterno = subalterno.equalsIgnoreCase("-") ? null : subalterno;
				rep.setSubalterno(subalterno);
				
				//Dati da Docfa_Uiu
				rep.setTipoOperDocfa(rs.getString("TIPO_OPERAZIONE"));
				String progUiu = rs.getString("NR_PROG");
				String flgIndirizzoUiu = rs.getString("INDIR_FLAG");
				String codIndirizzoUiu = rs.getString("INDIR_CODICE");
				String viaDocfaUiu = rs.getString("INDIR_TOPONIMO");
				String[]civiciUiu = new String[3];
				civiciUiu[0] = cleanLeftPad(rs.getString("INDIR_NCIV_UNO"),'0');
				civiciUiu[1] = cleanLeftPad(rs.getString("INDIR_NCIV_DUE"),'0');
				civiciUiu[2] = cleanLeftPad(rs.getString("INDIR_NCIV_TRE"),'0');
				
				String idDocfaIndice = rep.getProtocolloDocfa() 
					+ this.PIPE + yyyyMMdd.format(rep.getFornitura()) 
					+ this.PIPE + (progUiu == null ? "" : progUiu);
				log.info("Protocollo Docfa" + idDocfaIndice);

				idElaborazione = yyyyMMdd.format(rep.getFornitura()) 
					+ this.SEPARATORE + rep.getProtocolloDocfa()  
					+ this.SEPARATORE + rep.getFoglio() 
					+ this.SEPARATORE + rep.getParticella() 
					+ this.SEPARATORE + (subalterno == null ? "" : subalterno) 
					+ this.SEPARATORE + (progUiu == null ? "" : progUiu);
		
				rep.setIdRep(idElaborazione);
				
				//Dati da Docfa Dati Generali
				getDocfaDatiGenerali(rep);
				
				//Docfa Dichiarante
				getDichiaranteDocfa(rep);
				
				psDocfaDatiCensuari.setDate  (1,rep.getFornitura());
				psDocfaDatiCensuari.setString(2,rep.getProtocolloDocfa());
				psDocfaDatiCensuari.setString(3,rep.getFoglio());
				psDocfaDatiCensuari.setString(4,rep.getParticella());
				psDocfaDatiCensuari.setString(5,subalterno!=null ? subalterno : this.DOCFA_NULL);
				ResultSet rsDocfaDatiCensuari = psDocfaDatiCensuari.executeQuery();
				
				int countDC = 0;
				int campiValidi = 0;
				int campiValidiOpt = 0;
				
				DocfaDatiCensuariBean ddcOpt = new DocfaDatiCensuariBean();
				while(rsDocfaDatiCensuari.next()){
					countDC++;
					DocfaDatiCensuariBean ddc = new DocfaDatiCensuariBean();
					ddc.setDataDocfa(rsDocfaDatiCensuari.getString("DATA_REGISTRAZIONE"));
					ddc.setIdUiu(rsDocfaDatiCensuari.getString("IDENTIFICATIVO_IMMOBILE"));
					ddc.setZona(rsDocfaDatiCensuari.getString("ZONA"));
					ddc.setPrefissoViaDocfa(rsDocfaDatiCensuari.getString("TOPONIMO"));
					ddc.setViaDocfa(rsDocfaDatiCensuari.getString("INDIRIZZO"));
					String [] civiciDC = new String[3];
					civiciDC[0] = cleanLeftPad(rsDocfaDatiCensuari.getString("CIVICO_1"),'0');
					civiciDC[1] = cleanLeftPad(rsDocfaDatiCensuari.getString("CIVICO_2"),'0');
					civiciDC[2] = cleanLeftPad(rsDocfaDatiCensuari.getString("CIVICO_3"),'0');					
					ddc.setCiviciDocfa(civiciDC);
					
					if(!this.SOPPRESSA.equalsIgnoreCase(rep.getTipoOperDocfa())){
						ddc.setCategoriaDocfa(rsDocfaDatiCensuari.getString("CATEGORIA"));
						ddc.setClasseDocfa(rsDocfaDatiCensuari.getString("CLASSE"));
						ddc.setRenditaDocfa(rsDocfaDatiCensuari.getString("RENDITA_EURO"));
						ddc.setConsistenzaDocfa(rsDocfaDatiCensuari.getString("CONSISTENZA"));
						
						campiValidi = checkCategoriaValida(ddc.getCategoriaDocfa()) ? campiValidi+1 : campiValidi;
						campiValidi = checkClasseValida(ddc.getClasseDocfa()) ?  campiValidi+1 : campiValidi;	
						campiValidi = checkRenditaValida(convertStringToBigDecimal(ddc.getRenditaDocfa())) ?  campiValidi+1 : campiValidi;
						
					}
					
					if(campiValidi >= campiValidiOpt){
						ddcOpt = ddc;
						campiValidiOpt = campiValidi;
					}
				}	
				
				String idUiu = ddcOpt.getIdUiu();
				
				rep.setDataDocfa(ddcOpt.getDataDocfa());
				rep.setCategoriaDocfa(ddcOpt.getCategoriaDocfa());
				rep.setClasseDocfa(ddcOpt.getClasseDocfa());
				rep.setRenditaDocfa(convertStringToBigDecimal(ddcOpt.getRenditaDocfa()));
				
				rep.setConsistenzaDocfa(ddcOpt.getConsistenzaDocfa());
				
			
				//Prendo l'indirizzo in DOCFA_UIU (utilizzato per correlazione)
				viaDocfa = viaDocfaUiu!=null ? viaDocfaUiu.toUpperCase() : viaDocfaUiu;
				civici = civiciUiu;
				
				if(viaDocfa==null && countDC > 0  && ddcOpt.getViaDocfa()!=null){
					rep.setPrefissoViaDocfa(ddcOpt.getPrefissoViaDocfa());
					viaDocfa = ddcOpt.getViaDocfa();
					civici = ddcOpt.getCiviciDocfa();
				}
				rep.setViaDocfa(viaDocfa);
				rep.setCiviciDocfa(concatCivici(civici));
					
			
				controllaClassamento(ddcOpt.getZona(), rep);
				
				
				if (subalterno == null) {
					addAnomalia(rep,DocfaAnomalie.getCodSubNull());
					//annotazioni += "Subalterno Docfa NULL"+ this.SEPARATORE;
				}
				
				if(progUiu == null){
					addAnomalia(rep, DocfaAnomalie.getCodNoDocfaUiu());
					//rep.addAnnotazioni("Corrispondenza record in DOCFA_UIU mancante");
				}
				
				if(countDC == 0 ){
					if(!this.SOPPRESSA.equalsIgnoreCase(rep.getTipoOperDocfa())){
						addAnomalia(rep, DocfaAnomalie.getCodNoDocfaDatiCensuari());
						//annotazioni += "Dati censuari NON PRESENTI"+ this.SEPARATORE;
					}else{
						rep.setDataDocfa(this.getDataRegistrazione(conn, rep));
						
						if (rep.getDataDocfa() == null){
							addAnomalia(rep, DocfaAnomalie.getCodDataDocfaNull());
							//annotazioni += "Data Registrazione docfa NULL"+ this.SEPARATORE;
						}
					}
				}else{
					if(rep.getDataDocfa() == null){
						addAnomalia(rep, DocfaAnomalie.getCodDataDocfaNull());
						//annotazioni += "Data Registrazione docfa NULL"+ this.SEPARATORE;
					}else{
						//Verifica coerenza data registrazione con fornitura
						String sFornitura = yyyyMMdd.format(rep.getFornitura());
						String subData = rep.getDataDocfa().substring(0, 6);
						String subFornitura = sFornitura.substring(0, 6);
						if(!subFornitura.equalsIgnoreCase(subData))
							addAnomalia(rep,DocfaAnomalie.getCodDataDocfaDiversaFornitura());
					}
					if(countDC > 1 && !this.SOPPRESSA.equalsIgnoreCase(rep.getTipoOperDocfa())){
						addAnomalia(rep, DocfaAnomalie.getCodDatiCensuariRipetuti());
						rep.addAnnotazioni("Dati Censuari RIPETUTI ("+ countDC +")");
					}
				}
				
				misureDocfaValide = false;
				misureIciSuccValide = false;
				boolean catDocfaVal = false;
				boolean clsDocfaVal = false;
				boolean renDocfaVal = false;
				
								
				int docfaSuccessivi = 0;
				
				if(this.checkFoglioValido(rep.getFoglio()) && rep.getParticella() != null){
					
					if(progUiu != null)
						this.verificaIndirizzoDocfa(rep, idDocfaIndice);
					
					//Estrazione Dati a Catasto (pre-post) la data di registrazione del DOCFA
					//Se la data è null, prendo la data di variazione
					if(rep.getDataDocfa()!=null || rep.getDataVariazione()!= null)
						this.getDatiCatastoAllaData(rep, ctx);
					
					if(rep.getDataDocfa()!=null){
						catDocfaVal = this.checkCategoriaValida(rep.getCategoriaDocfa());
						clsDocfaVal = this.checkClasseValida(rep.getClasseDocfa());
						renDocfaVal = this.checkRenditaValida(rep.getRenditaDocfa());
						
						misureDocfaValide = catDocfaVal && clsDocfaVal && renDocfaVal;	
						
						if(!this.SOPPRESSA.equalsIgnoreCase(rep.getTipoOperDocfa())){
							if(!catDocfaVal)
								addAnomalia(rep, DocfaAnomalie.getCodCategoriaDocfaErrata());
							if(!clsDocfaVal)
								addAnomalia(rep, DocfaAnomalie.getCodClasseDocfaErrata());
							if(!renDocfaVal)
								addAnomalia(rep, DocfaAnomalie.getCodRenditaDocfaErrata());
						}
												
						//Estrazione del numero di docfa per la uiu nello stesso anno
						this.getNumeroDocfaInAnno(rep);
						
						//Estrazione della data del docfa successivo a quello corrente (non dello stesso anno)
						this.getDataDocfaSuccessivo(rep);
						
						//Recupero le titolarità della UIU nell'anno del DOCFA
						titolari = this.getTitolariUiuAnnoDocfa(rep, idDocfaIndice);
						
						//TODO:Recupero eventuali UIU graffate					
	
						if(progUiu != null)
							this.getNumeroDocfaContemporanei(rep);
						
						//if(!this.COSTITUITA.equalsIgnoreCase(tipoOperDocfa))
						this.getIciPrecedente(rep);
	
						
						if(!this.SOPPRESSA.equalsIgnoreCase(rep.getTipoOperDocfa())){
							
							/**Estrazione della dichiarazione ICI con anno di riferimento compreso tra:
							 * la data del DOCFA corrente e
							 * la data DOCFA successivo (con anno diverso a quello del docfa corrente)- se esiste*/
						
							psIciSuccessivo.setString(1, this.cleanLeftPad(rep.getFoglio(),'0'));
							psIciSuccessivo.setString(2, this.cleanLeftPad(rep.getParticella(),'0'));
							psIciSuccessivo.setString(3, subalterno!= null ? subalterno : this.ICI_NULL );
							psIciSuccessivo.setString(4, rep.getDataDocfa());
							
							//Imposto il limite superiore (data)
							if(rep.getDataDocfaSuccessivo()!=null){
								psIciSuccessivo.setString(5, rep.getDataDocfaSuccessivo());
							}else
								psIciSuccessivo.setString(5,"99991231");
							
							rsIciSuccessivo = psIciSuccessivo.executeQuery();
							
							/*Se ci sono più dichiarazioni ICI precedenti (dello stesso anno) verifico su tutte:
							 * se il DOCFA varia un parametro rispetto ad una dichiarazione ici, la si considera come variazione globale
							 */
							String annoIciSucc = null;
							presenzaIciSuccessivo = false;
							while(rsIciSuccessivo.next()){
								
								if(!presenzaIciSuccessivo){ 
									//Prima Iterazione: Inizializzo i flag a false
									varClsDocfaAIci = false;
									varCatDocfaAIci = false;
									varRenditaDocfaAIci = false;
									maxVarRenditaDocfaAIci[0] = BigDecimal.ZERO;
									maxVarRenditaDocfaAIci[1] = BigDecimal.ZERO;
									maxVarPercRenditaDocfaAIci[0] = BigDecimal.ZERO;
									maxVarPercRenditaDocfaAIci[1] = BigDecimal.ZERO;
									presenzaIciSuccessivo = true;	
								}	
								
								annoIciSucc = rsIciSuccessivo.getString("YEA_DEN");
								String classeIciSucc = rsIciSuccessivo.getString("CLS");
								String categoriaIciSucc = rsIciSuccessivo.getString("CAT");
								BigDecimal renditaIciSucc = null;
								try {
									renditaIciSucc = rsIciSuccessivo.getBigDecimal("VAL_IMM");
								} catch (Exception e) {
									renditaIciSucc = rsIciSuccessivo.getString("VAL_IMM") == null ? null : new BigDecimal(rsIciSuccessivo.getString("VAL_IMM").replace(",", "."));
								}									
								boolean catIciVal = this.checkCategoriaValida(categoriaIciSucc);
								boolean clsIciVal = this.checkClasseValida(classeIciSucc);
								boolean renIciVal = this.checkRenditaValida(renditaIciSucc);
								
								
								if(!catIciVal)
									addAnomalia(rep, DocfaAnomalie.getCodCategoriaIciPostErrata());
								else if(catDocfaVal)
									varCatDocfaAIci = this.checkIsVariato(varCatDocfaAIci, categoriaIciSucc, rep.getCategoriaDocfa());
								
									
								if(!clsIciVal)
									addAnomalia(rep, DocfaAnomalie.getCodClasseIciPostErrata());
								else if(clsDocfaVal)
									varClsDocfaAIci = this.checkIsVariato(varClsDocfaAIci, classeIciSucc, rep.getClasseDocfa());
									
								if(!renIciVal)
									addAnomalia(rep, DocfaAnomalie.getCodRenditaIciPostErrata());
								else if(renDocfaVal)
									maxVarRenditaDocfaAIci = this.getMaxVarRendita(maxVarRenditaDocfaAIci, renditaIciSucc, rep.getRenditaDocfa());
									//varRenditaDocfaAIci = this.checkRenditaIsVariata(varRenditaDocfaAIci, renditaIciSucc, rep.getRenditaDocfa());	
								
									
							}
							
							if(varRenditaDocfaAIci!=null){
								BigDecimal soglia = new BigDecimal(0);
								varRenditaDocfaAIci =    soglia.compareTo(maxVarRenditaDocfaAIci[0])!=0 
												      || soglia.compareTo(maxVarRenditaDocfaAIci[1])!=0;
								
								maxVarPercRenditaDocfaAIci[0] = this.calcolaPercentuale(maxVarRenditaDocfaAIci[0], rep.getRenditaDocfa());
								maxVarPercRenditaDocfaAIci[1] = this.calcolaPercentuale(maxVarRenditaDocfaAIci[1], rep.getRenditaDocfa());
								
							}
					
							if(presenzaIciSuccessivo && annoIciSucc!= null){
								String dataSup = Integer.toString(Integer.parseInt(annoIciSucc)-1)+"1231";
								docfaSuccessivi = this.getNumeroDocfaSuccessivi(rep, dataSup);
								if(docfaSuccessivi > 0){
									addAnomalia(rep, DocfaAnomalie.getCodAltriDocfaBeforeIci());
									rep.addAnnotazioni("Presenza di " + docfaSuccessivi 
											+ " dichiarazioni tra la data del DOCFA corrente e " 
											+ dataSup.substring(6, 8) + "-"
											+ dataSup.substring(4, 6) + "-" 
											+ dataSup.substring(0, 4));
								}
							}	
						}
						rep.setFlgElaborato(true);
				  }
				}else{
					rep.setFlgElaborato(false);
					if(rep.getFoglio() == null)
						addAnomalia(rep, DocfaAnomalie.getCodFoglioNull());
					else if(!this.checkFoglioValido(rep.getFoglio()))
						addAnomalia(rep, DocfaAnomalie.getCodFoglioInvalido());
					if(rep.getParticella() == null)
						addAnomalia(rep, DocfaAnomalie.getCodParticellaNull());
				}
			
				rep.setFlgIciPostDocfa(presenzaIciSuccessivo);
				rep.setFlgVarPostCategoria(varCatDocfaAIci);
				rep.setFlgVarPostClasse(varClsDocfaAIci);
				rep.setFlgVarPostRendita(varRenditaDocfaAIci);
				rep.setMaxDimRenDcf2Ici(maxVarRenditaDocfaAIci[1]);
				rep.setMaxAugRenDcf2Ici(maxVarRenditaDocfaAIci[0]);
				rep.setMaxDimPercRenDcf2Ici(maxVarPercRenditaDocfaAIci[1]);
				rep.setMaxAugPercRenDcf2Ici(maxVarPercRenditaDocfaAIci[0]);
        	   
				LinkedHashMap<String, Object> dati = rep.getDati();
        	   
        	   if(primorecord){
	              tab = creaTabella(conn,DocfaIciReportBean.tabReport, DocfaIciReportBean.SQL_CREATE,DocfaIciReportBean.SQL_INDICI );	
	              tabSoggetti = creaTabella(conn, DocfaIciReportSoggBean.tabReport, DocfaIciReportSoggBean.SQL_CREATE,DocfaIciReportSoggBean.SQL_INDICI);
	        	  
		            //Creo tabella anomalie
					creaTabella(conn, DocfaAnomalie.getTabAnomalie(), DocfaAnomalie.getSQL_CREATE(),null);
					ArrayList<LinkedHashMap<String, Object>> datiAnomalie = DocfaAnomalie.getDatiAnomalie();
					for(int a = 0; a< datiAnomalie.size(); a++){
						insert(DocfaAnomalie.getTabAnomalie(), datiAnomalie.get(a), conn);
					}
					conn.commit();
					primorecord = false;
	             
        	   }
				
				if (tab!=null && tab && !dati.isEmpty()) {
					if(itot%10 == 0)
						log.info("Inserimento riga"+ itot);
					
					insert(DocfaIciReportBean.tabReport, dati,conn);
				}
				
				if(tabSoggetti!=null && tabSoggetti){
					//Inseririsce i titolari delle UIU con la corrispondente elaborazione
					for(int i=0; i<titolari.size(); i++){
						DocfaIciReportSoggBean titolare = titolari.get(i);
						LinkedHashMap<String, Object> datiSogg = titolare.getDatiReportSogg();

						if(!datiSogg.isEmpty())
							insert(DocfaIciReportSoggBean.tabReport, datiSogg,conn);
					}
				}

				itot++;
			}
			
			conn.commit();
			
			updateValImponibileDocfa(conn);
							
		}finally {
        				
			DbUtils.close(rsIciSuccessivo);        	
			
			DbUtils.close(psIciSuccessivo); 
			DbUtils.close(psDocfaSuccessivo); 
			DbUtils.close(psIciPrecedente); 
			DbUtils.close(psDocfaInAnno);
			DbUtils.close(psDocfaSucc);
			DbUtils.close(psCatasto);
			DbUtils.close(psCatastoPost);
			DbUtils.close(psCiviciCatasto);
			DbUtils.close(psCiviciIndiceCD);
			DbUtils.close(psTitUiuAnnoDocfa);
			DbUtils.close(psTitIndiceCA);
			DbUtils.close(psResidenzaTitolare);
			DbUtils.close(psResidenzaCivicoCatasto);
			DbUtils.close(psResidenzaCivicoDocfa);
			DbUtils.close(psDocfaContemporanei);
			DbUtils.close(psVerificaClassamento);
			DbUtils.close(psTitIciSuccessivo);
			DbUtils.close(psTitIciPrecedente);
			DbUtils.close(psDocfaDatiGenerali);
			DbUtils.close(psDocfaDatiCensuari);
			DbUtils.close(psDocfaDataregistrazione);
			DbUtils.close(psDocfaDichiarante);
			DbUtils.close(psFamiliariDataDocfa);
			
        	DbUtils.close(rs);        	
        	DbUtils.close(cs);
        	
        	//DbUtils.close(conn);  
		}
        
		    log.info("DocfaIciReport eseguito...");
			ApplicationAck ack = new ApplicationAck(abnormals, "Report Ici-Docfa eseguito");
			return ack;
			
		}catch (Exception e)
		{
			log.error("Errore nell'esecuzione di Report Ici-Docfa ("+idElaborazione+")",e);
			
			ErrorAck ea = new ErrorAck(e);
			return ea;
		}
	}
	
	private void updateValImponibileDocfa(Connection conn) throws Exception{
		ResultSet rs = null;
		ResultSet rsSumPre = null;
		ResultSet rsSumPost = null;
			
		try{
			psDocfaIciReport = conn.prepareCall(this.SQL_DOCFA_ICI_REPORT, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			psDocfaIciReportSumPre = conn.prepareCall(this.SQL_DOCFA_ICI_REPORT_SUM_PRE, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			psDocfaIciReportSumPost = conn.prepareCall(this.SQL_DOCFA_ICI_REPORT_SUM_POST, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			psUpdateSumImponibile = conn.prepareStatement(this.SQL_UPDATE_SUM_IMPONIBILE);
			rs = psDocfaIciReport.executeQuery();
		
			while(rs.next()){
				
				Date fornitura = rs.getDate("FORNITURA");
				String protocollo = rs.getString("PROTOCOLLO_DOCFA");
				//String dataReg = rs.getString("DATA_DOCFA");
				
			//	dataReg = dataReg!= null ? dataReg : "";
				
			/*	log.info("Calcolo Sum Pre: Fornitura["+fornitura+"];" +
						"Protocollo["+protocollo+"]; " +
						"Data Registrazione["+dataReg+"]");
				*/
				psDocfaIciReportSumPre.setDate(1, fornitura);
				psDocfaIciReportSumPre.setString(2, protocollo);
			//	psDocfaIciReportSumPre.setString(3, dataReg);
				rsSumPre = psDocfaIciReportSumPre.executeQuery();
				BigDecimal sumPre = null;
				if(rsSumPre.next()) {					
					try {
						sumPre = rsSumPre.getBigDecimal("SUM_IMPONIBILE_PRE");
					} catch (Exception e) {
						sumPre = rsSumPre.getString("SUM_IMPONIBILE_PRE") == null ? null : new BigDecimal(rsSumPre.getString("SUM_IMPONIBILE_PRE").replace(",", "."));
					}
				}					
				sumPre  = sumPre != null ? sumPre  : BigDecimal.ZERO;
				
			//	if(dataReg == null)
				log.info("Calcolo Sum Pre["+sumPre+"]");
				
			/*	log.info("Calcolo Sum Post: Fornitura["+fornitura+"];" +
						"Protocollo["+protocollo+"]; " +
						"Data Registrazione["+dataReg+"]");*/
			
				psDocfaIciReportSumPost.setDate(1, fornitura);
				psDocfaIciReportSumPost.setString(2, protocollo);
				//psDocfaIciReportSumPost.setString(3, dataReg);
				rsSumPost = psDocfaIciReportSumPost.executeQuery();
				BigDecimal sumPost = null;
				if(rsSumPost.next()) {
					try {
						sumPost = rsSumPost.getBigDecimal("SUM_IMPONIBILE_POST");
					} catch (Exception e) {
						sumPost = rsSumPost.getString("SUM_IMPONIBILE_POST") == null ? null : new BigDecimal(rsSumPost.getString("SUM_IMPONIBILE_POST").replace(",", "."));
					}
				}
				sumPost = sumPost!= null ? sumPost : BigDecimal.ZERO;
			
				//if(dataReg == null)
					log.debug("Calcolo Sum Post["+sumPost+"]");
				
				BigDecimal varSum = sumPost.subtract(sumPre);
				BigDecimal varSumPerc = this.calcolaPercentuale(varSum, sumPre);
				
				psUpdateSumImponibile.setBigDecimal(1,sumPre);
				psUpdateSumImponibile.setBigDecimal(2,sumPost);
				psUpdateSumImponibile.setBigDecimal(3,varSumPerc);
				psUpdateSumImponibile.setDate(4,fornitura);
				psUpdateSumImponibile.setString(5,protocollo);
				//update.setString(6,dataReg);
				psUpdateSumImponibile.executeUpdate();	
			}
		} catch (Exception e) {
			log.error("Exception: " + e.getMessage()  ,e);
			log.error(SQL_UPDATE_SUM_IMPONIBILE,e);
			throw e;
		}finally {
			
			DbUtils.close(rs); 
			DbUtils.close(rsSumPre); 
			DbUtils.close(rsSumPost); 
			
			DbUtils.close(psUpdateSumImponibile);
			DbUtils.close(psDocfaIciReport);    
			DbUtils.close(psDocfaIciReportSumPre);    
			DbUtils.close(psDocfaIciReportSumPost);    
		}
	}
	

	private void insert(String tab, LinkedHashMap<String, Object> dati,Connection conn) throws Exception {
		
		if (dati.isEmpty())
			return;

		ArrayList<String> date = new DocfaIciReportBean().getGetDateFields();
		ArrayList<String> numerici = new DocfaIciReportBean().getGetNumberFields();
		
		java.sql.PreparedStatement stmt =null;
		//Connection conn  =null;
		
		String sqlParams = "INSERT INTO " + tab +" (";
		String sqlIns = " VALUES (";
			
		try {
			//conn = RulesConnection.getConnection("DIOGENE_"+belfiore);
			Set s = dati.keySet();
			Iterator i = s.iterator();
			int c=1;
			while(i.hasNext())
			{
				Object key = i.next();
				String virgola= null;
				if (c!=1)
					virgola = ",";
				else { 
					virgola = " ";
				}
				sqlIns+=virgola + "?";
				sqlParams+=virgola + (String)key;
				c++;
			}
			sqlIns += ")";
			sqlParams+=") ";
			sqlIns = sqlParams + sqlIns;
			stmt = conn.prepareStatement(sqlIns);
			i = dati.keySet().iterator();
			c=1;
			while(i.hasNext())
			{
				Object key = i.next();
				Object value = dati.get(key);
				String virgola= null;
				if (c!=1)
					virgola = ",";
				else { 
					virgola = " ";
				}
				
				String skey = (String)key;
			//	log.debug(skey + ":* "+ value);
				
				
				if (value!=null){
					if(value instanceof Date)
						stmt.setDate(c, (Date)value);
					else if (value instanceof BigDecimal)
						stmt.setBigDecimal(c, (BigDecimal)value);
					else if (value instanceof Integer)
						stmt.setInt(c, (Integer)value);
					else
						stmt.setString(c, (String)value);
				}else{
					
					/*if(skey.equals("FORNITURA")||skey.equals("DATA_VARIAZIONE")
							|| skey.equals("DATA_NASC")||skey.equals("DATA_MORTE") 
							||skey.equals("DATA_INIZIO_POSSESSO")||skey.equals("DATA_FINE_POSSESSO"))
						stmt.setNull(c,java.sql.Types.DATE);
					else if(skey.equals("PK_CUAA")||skey.equals("PERC_POSS")||skey.equals("NUM_FAMILIARI"))
						stmt.setNull(c,java.sql.Types.NUMERIC);
					else if (skey.equals("UIU_SOPPRESSE")||skey.equals("UIU_COSTITUITE")||skey.equals("UIU_VARIATE"))
						stmt.setNull(c,java.sql.Types.INTEGER);
					else
						stmt.setNull(c,java.sql.Types.VARCHAR);*/
					
					if(date.contains(skey))
						stmt.setNull(c,java.sql.Types.DATE);
					else if(numerici.contains(skey))
						stmt.setNull(c,java.sql.Types.NUMERIC);
					else
						stmt.setNull(c,java.sql.Types.VARCHAR);
					
				}
				c++;
								
				
			}
			stmt.execute();
			stmt.close();
		} catch (Exception e) {
			log.error("Exception: " + e.getMessage()  ,e);
			log.error( sqlIns,e);
			throw e;
		} 

	}
	
	private BigDecimal convertStringToBigDecimal(String s){
		BigDecimal val = BigDecimal.ZERO;
		if(this.checkCampoValido(s)){
			s = s.replace(',', '.');
			val = new BigDecimal(s);
		}
		return val;
	}
	
	private String getDataRegistrazione(Connection conn, DocfaIciReportBean rep) throws Exception {
		
		String data = null;
		ResultSet rsDataRegistrazione = null;
		
		try{
	
			psDocfaDataregistrazione.setDate(1,rep.getFornitura());
			psDocfaDataregistrazione.setString(2,rep.getProtocolloDocfa());
				
			rsDataRegistrazione = psDocfaDataregistrazione.executeQuery();
			
			if(rsDataRegistrazione.next())
				data = rsDataRegistrazione.getString("data_registrazione");
			if(rsDataRegistrazione.next())
				data = null;
			
		   }catch(Exception e){
			   
		   }finally{
			   
			   DbUtils.close(rsDataRegistrazione);
		   }
		return data;
	}
	

	private void getIciPrecedente(DocfaIciReportBean rep) throws Exception {
		
		ResultSet rsIciPrecedente = null;
		
		Boolean presenzaIciPrecedente = this.NON_ELABORATO;
		Boolean varClsIciADocfa = this.NON_ELABORATO;
		Boolean varCatIciADocfa = this.NON_ELABORATO;
		Boolean varRenditaIciADocfa = this.NON_ELABORATO;
		BigDecimal[] maxVarRenditaIciADocfa = {null,null};
		BigDecimal[] maxVarPercRenditaIciADocfa = {null,null};

		Boolean catIciVal = this.NON_ELABORATO;
		Boolean clsIciVal = this.NON_ELABORATO;
		Boolean renIciVal = this.NON_ELABORATO;
		String sub = rep.getSubalterno();
		
		try{
		
		/**Estrazione della(delle) più recenti dichiarazione ICI associata alla UIU, precedente al DOCFA*/
			psIciPrecedente.setString(1, this.cleanLeftPad(rep.getFoglio(),'0'));
			psIciPrecedente.setString(2, this.cleanLeftPad(rep.getParticella(),'0'));
			psIciPrecedente.setString(3, sub!=null ? sub : this.ICI_NULL );
			psIciPrecedente.setString(4, rep.getDataDocfa());
			rsIciPrecedente = psIciPrecedente.executeQuery();
				
			/*Se ci sono più dichiarazioni ICI precedenti (dello stesso anno) verifico su tutte:
			 * se il DOCFA varia un parametro rispetto ad una dichiarazione ici, la si considera come variazione globale
			 */
			presenzaIciPrecedente = false;
			while(rsIciPrecedente.next()){
				
				if(!presenzaIciPrecedente){ 
					//Prima Iterazione: Inizializzo i flag a false
					varClsIciADocfa = false;
					varCatIciADocfa = false;
					maxVarRenditaIciADocfa[0] = BigDecimal.ZERO;
					maxVarRenditaIciADocfa[1] = BigDecimal.ZERO;
					maxVarPercRenditaIciADocfa[0] = BigDecimal.ZERO;
					maxVarPercRenditaIciADocfa[1] = BigDecimal.ZERO;
					varRenditaIciADocfa = false;
					
					presenzaIciPrecedente = true;
				}
			
				if(!this.SOPPRESSA.equalsIgnoreCase(rep.getTipoOperDocfa())){
					//Se la UIU è soppressa non ci sono valori metrici da confrontare nel DOCFA
					String annoIciPrec = rsIciPrecedente.getString("YEA_DEN");
					String classeIciPrec = rsIciPrecedente.getString("CLS");
					String categoriaIciPrec = rsIciPrecedente.getString("CAT");
					BigDecimal renditaIciPrec = null;
					try {
						renditaIciPrec = rsIciPrecedente.getBigDecimal("VAL_IMM");
					} catch (Exception e) {
						renditaIciPrec = rsIciPrecedente.getString("VAL_IMM") == null ? null : new BigDecimal(rsIciPrecedente.getString("VAL_IMM").replace(",", "."));
					}					
					catIciVal = this.checkCategoriaValida(rep.getCategoriaDocfa());
					clsIciVal = this.checkClasseValida(rep.getClasseDocfa());
					System.out.println("Rendita Dcf riga913:" + rep.getRenditaDocfa());
					renIciVal = this.checkRenditaValida(rep.getRenditaDocfa());
					
					
					if(!catIciVal)
						addAnomalia(rep, DocfaAnomalie.getCodCategoriaIciAnteErrata());
					if(!clsIciVal)
						addAnomalia(rep, DocfaAnomalie.getCodClasseIciAnteErrata());
					if(!renIciVal)
						addAnomalia(rep, DocfaAnomalie.getCodRenditaIciAnteErrata());
				
					varClsIciADocfa = this.checkIsVariato(varClsIciADocfa, classeIciPrec, rep.getClasseDocfa());
					varCatIciADocfa = this.checkIsVariato(varCatIciADocfa, categoriaIciPrec, rep.getCategoriaDocfa());
					maxVarRenditaIciADocfa = this.getMaxVarRendita(maxVarRenditaIciADocfa, renditaIciPrec, rep.getRenditaDocfa());
					//varRenditaIciADocfa = this.checkRenditaIsVariata(varRenditaIciADocfa, renditaIciPrec, rep.getRenditaDocfa());	
					
				}
			}
			
			if(varRenditaIciADocfa!=null){
				BigDecimal soglia = new BigDecimal(0);
				varRenditaIciADocfa =    soglia.compareTo(maxVarRenditaIciADocfa[0])!=0 
								      || soglia.compareTo(maxVarRenditaIciADocfa[1])!=0;
				
				
				
				maxVarPercRenditaIciADocfa[0] = this.calcolaPercentuale(maxVarRenditaIciADocfa[0], rep.getRenditaDocfa());
				maxVarPercRenditaIciADocfa[1] = this.calcolaPercentuale(maxVarRenditaIciADocfa[1], rep.getRenditaDocfa());
				
			}
				
		
		}catch(Exception e){
			throw e;
			
		}finally{
			DbUtils.close(rsIciPrecedente);
			
			rep.setFlgIciAnteDocfa(presenzaIciPrecedente);
			rep.setFlgVarAnteCategoria(varCatIciADocfa);
			rep.setFlgVarAnteClasse(varClsIciADocfa);
			rep.setFlgVarAnteRendita(varRenditaIciADocfa);
			rep.setMaxDimRenIci2Dcf(maxVarRenditaIciADocfa[0]);
			rep.setMaxAugRenIci2Dcf(maxVarRenditaIciADocfa[1]);
			
			rep.setMaxDimPercRenIci2Dcf(maxVarPercRenditaIciADocfa[0]);
			rep.setMaxAugPercRenIci2Dcf(maxVarPercRenditaIciADocfa[1]);
			
		}
	}
	
	private BigDecimal calcolaPercentuale(BigDecimal delta, BigDecimal totale){
		
		BigDecimal cento = new BigDecimal(100);
		MathContext mc = new MathContext(3, RoundingMode.HALF_UP);
		BigDecimal result = null;
		
		if(delta.equals(BigDecimal.ZERO))
			result = BigDecimal.ZERO;
		else{
			//Esiste una variazione
			if(totale.compareTo(BigDecimal.ZERO)!=0)
				result = (cento.multiply(delta)).divide(totale,mc).setScale(2, RoundingMode.HALF_DOWN);
			else if(totale.compareTo(BigDecimal.ZERO)==0)
				result = null;
		}
		
		log.info("Delta: "+ delta+", Totale: "+totale+", Result: "+result);
		return result;
	}
	
	private void getDatiCatastoAllaData(DocfaIciReportBean rep, Context ctx) throws Exception{
		
		ResultSet rsCatasto = null;
		ResultSet rsCatastoPost = null;
		
		//Valori a catasto 
		Boolean presenzaACatasto = this.NON_ELABORATO;
		String categoriaCatasto = null;
		String classeCatasto = null;
		BigDecimal renditaCatasto = null;
		String sub = rep.getSubalterno();
		BigDecimal valImponibilePre = null;
		BigDecimal valImponibilePost = null;
		BigDecimal varImponibileUiu = null;
		
		String dataRif = null;
		if(rep.getDataDocfa()!= null){
			dataRif = rep.getDataDocfa();
		}else if(rep.getDataVariazione()!=null)
			dataRif = yyyyMMdd.format(rep.getDataVariazione());
		
		
		try{
			
			/**Estrazione informazioni a catasto per la UIU alla data antecedente al DOCFA*/
			
			psCatasto.setString(1, cleanLeftPad(rep.getFoglio(), '0'));
			psCatasto.setString(2, rep.getParticella() );
			psCatasto.setString(3, sub!=null ? cleanLeftPad(sub,'0') : this.CAT_NULL );
			psCatasto.setString(4, dataRif );
			psCatasto.setString(5, dataRif );
			rsCatasto = psCatasto.executeQuery();
			
			if(rsCatasto.next()){
				presenzaACatasto = true;
				categoriaCatasto = rsCatasto.getString("CATEGORIA");
				classeCatasto = rsCatasto.getString("CLASSE");
				try {
					renditaCatasto = rsCatasto.getBigDecimal("RENDITA");
				} catch (Exception e) {
					renditaCatasto = rsCatasto.getString("RENDITA") == null ? null : new BigDecimal(rsCatasto.getString("RENDITA").replace(",", "."));
				}
				valImponibilePre = this.calcolaValImponibile(renditaCatasto, categoriaCatasto);
			}else
				presenzaACatasto = false;
			
			//Ricerca presenza a catasto successiva al docfa
			psCatastoPost.setString(1, cleanLeftPad(rep.getFoglio(), '0'));
			psCatastoPost.setString(2, rep.getParticella() );
			psCatastoPost.setString(3, sub!=null ? cleanLeftPad(sub,'0') : this.CAT_NULL );
			psCatastoPost.setString(4, dataRif );
			psCatastoPost.setString(5, dataRif );
			rsCatastoPost = psCatastoPost.executeQuery();
			
			if(rsCatastoPost.next()){
				BigDecimal rendita = null;
				try {
					rendita = rsCatastoPost.getBigDecimal("RENDITA");
				} catch (Exception e) {
					rendita = rsCatastoPost.getString("RENDITA") == null ? null : new BigDecimal(rsCatastoPost.getString("RENDITA").replace(",", "."));
				}
				valImponibilePost = this.calcolaValImponibile(
						rendita,
						rsCatastoPost.getString("CATEGORIA"));
			}
			
			if(valImponibilePost!=null && valImponibilePre!=null)
				varImponibileUiu = valImponibilePost.subtract(valImponibilePre);
			else if (valImponibilePost!=null)
				varImponibileUiu = valImponibilePost;
			else if (valImponibilePre!=null)
				varImponibileUiu = BigDecimal.ZERO.subtract(valImponibilePre);
				
					
		}catch(Exception e){
			throw e;
		}finally{
			
			DbUtils.close(rsCatasto);
			DbUtils.close(rsCatastoPost);
			
			rep.setFlgUiuCatasto(presenzaACatasto);
			rep.setCategoriaCatasto(categoriaCatasto);
			rep.setClasseCatasto(classeCatasto);
			rep.setRenditaCatasto(renditaCatasto);
			rep.setImponibileUiuPre(valImponibilePre);
			rep.setImponibileUiuPost(valImponibilePost);
			rep.setVarImponibileUiu(varImponibileUiu);
			
		}
	}
	
	private BigDecimal calcolaValImponibile(BigDecimal renditaBD, String categoria){
		Double result = null;
		Double rendita = renditaBD.doubleValue();
		log.info( "Calcola val.imponibile: " +
							"rendita["+rendita+"];" +
							"categoria["+categoria+"];");
		
		String tipo = categoria!=null ? categoria.substring(0,1) : null;
		if(tipo!=null && rendita !=null){
			result = rendita*105/100; 
			if(("A".equals(tipo) && !categoria.equals("A10")) || ("C".equals(tipo) && !categoria.equals("C01")))
				result = result * 100;
			else if("B".equals(tipo))
				result = result * 140;
			else if ("D".equals(tipo) || categoria.equals("A10")){
				result = result * 50; 
				//TODO: Va fatta eccezione per fabbricati classificabili nella categoria D, interamente posseduti 
				//da imprese e distintamente contabilizzati, sforniti di rendita catastale (per i quali si utilizzano costi contabili)
			}else if (categoria.equals("C01"))
				result = result * 34;
		}
		
		log.info( "Val.imponibile["+result+"]"); 
		
		if(result!=null)
			return BigDecimal.valueOf(result);
		else return BigDecimal.ZERO;
	}

	
	/**Estrazione della data della prima dichiarazione DOCFA, dell'anno successivo a quello del DOCFA corrente*/
	private void getDataDocfaSuccessivo( DocfaIciReportBean rep )throws Exception{
		String dataDocfaSuccessivo = null;
		ResultSet rs = null; 
		String sub = rep.getSubalterno();
		
		try{
			psDocfaSuccessivo.setDate(1,rep.getFornitura() );
			psDocfaSuccessivo.setString(2, rep.getProtocolloDocfa() );
			psDocfaSuccessivo.setString(3, rep.getDataDocfa() );
			psDocfaSuccessivo.setString(4, rep.getFoglio() );
			psDocfaSuccessivo.setString(5, rep.getParticella() );
			psDocfaSuccessivo.setString(6, sub!= null ? sub : this.DOCFA_NULL );
			
			rs = psDocfaSuccessivo.executeQuery();
			if(rs.next()){
				dataDocfaSuccessivo = rs.getString("DATA_DOCFA");
			}
			
		}catch(Exception e){
			throw e;
		}finally{
			DbUtils.close(rs);
			rep.setDataDocfaSuccessivo(dataDocfaSuccessivo);
		}
	}
	
	/**Conteggio del numero di docfa presentati nell'anno per la stessa UIU*/
	private void getNumeroDocfaInAnno(DocfaIciReportBean rep) throws Exception {
		
		String docfaInAnno = null;
		ResultSet rsDocfaInAnno = null;
		String sub = rep.getSubalterno();
		
		try{
	
			psDocfaInAnno.setString(1,rep.getFoglio());
			psDocfaInAnno.setString(2,rep.getParticella() );
			psDocfaInAnno.setString(3,sub!=null ? sub : this.DOCFA_NULL  );
			psDocfaInAnno.setString(4,rep.getDataDocfa() );
			
			rsDocfaInAnno = psDocfaInAnno.executeQuery();
			
			if(rsDocfaInAnno.next())
				docfaInAnno = rsDocfaInAnno.getString("docfa_in_anno");
			
		   }catch(Exception e){
			   
		   }finally{
			   
			   DbUtils.close(rsDocfaInAnno);
			   rep.setDocfaInAnno(docfaInAnno);
		   }
	}
	
	/**Conteggio del numero di docfa presentati nell'anno per la stessa UIU*/
	private int getNumeroDocfaSuccessivi(DocfaIciReportBean rep, String dataSup) throws Exception {
		
		int docfaInAnnoSucc = 0;
		ResultSet rs = null;
		String sub = rep.getSubalterno();
		
		try{
			psDocfaSucc.setString(1,rep.getFoglio());
			psDocfaSucc.setString(2,rep.getParticella() );
			psDocfaSucc.setString(3,sub!=null ? sub : this.DOCFA_NULL  );
			psDocfaSucc.setString(4,rep.getDataDocfa());
			psDocfaSucc.setString(5,dataSup);
			
			rs = psDocfaSucc.executeQuery();
			
			if(rs.next())
				docfaInAnnoSucc = rs.getInt("docfa_succ")-1;
			
		   }catch(Exception e){
			   
		   }finally{
			   DbUtils.close(rs);
		   }
		return docfaInAnnoSucc;
	}
	
	/**Conteggio del numero di docfa presentati alla stessa data per la UIU, dello stesso tipo*/
	private void getNumeroDocfaContemporanei(DocfaIciReportBean rep) throws Exception {
		
		String docfaContemp = null;
		ResultSet rsDocfaContemp = null;
		String sub = rep.getSubalterno();
		
		try{
		/*	log.debug("Parametri Query: SQL_DOCFA_CONTEMPORANEI");
			
			*/
			psDocfaContemporanei.setString(1, rep.getFoglio());
			psDocfaContemporanei.setString(2, rep.getParticella() );
			psDocfaContemporanei.setString(3, sub!=null ? sub : this.DOCFA_NULL);
			psDocfaContemporanei.setString(4, rep.getDataDocfa() );
			psDocfaContemporanei.setString(5, rep.getTipoOperDocfa());
			
			rsDocfaContemp = psDocfaContemporanei.executeQuery();
			
			if(rsDocfaContemp.next()){
				int num = rsDocfaContemp.getInt("docfa_contemporanei");
				if(num == 0)
					docfaContemp = null;
				else{
					if(num > 1){
						addAnomalia(rep,DocfaAnomalie.getCodDocfaContemporanei());
						rep.addAnnotazioni(" Presenza di " + num + " docfa, per la UIU alla stessa data");
					}
					docfaContemp = Integer.toString(num - 1);
				}
			}
			
		   }catch(Exception e){
			   throw e;
		   }finally{
			   DbUtils.close(rsDocfaContemp);
			   rep.setDocfaContemporanei(docfaContemp);
			   //log.debug("Docfa Contemporanei: " + docfaContemp);
		   }	
	}
	
	private void verificaIndirizzoDocfa(DocfaIciReportBean rep, String idDocfaIndice) throws Exception{
		Boolean flgIndirizzoACatasto = false;
		
		//Seleziono i Docfa da elaborare
		ResultSet rs = null;
		ResultSet rsIndice = null;
		String sub = rep.getSubalterno();
		
		BigDecimal codViaCatasto = null;
		String prefissoViaCatasto = null;
		String viaCatasto = null;
		String civicoCatasto = null;
		int i = 0;
		
		try{
				
			psCiviciCatasto.setString(1, rep.getCodEnte());
			psCiviciCatasto.setString(2, this.cleanLeftPad(rep.getFoglio(), '0'));
			psCiviciCatasto.setString(3, rep.getParticella());
			psCiviciCatasto.setString(4, sub != null ? cleanLeftPad(sub, '0') : this.CAT_NULL);
			rs = psCiviciCatasto.executeQuery();
			
			while (rs.next() && !flgIndirizzoACatasto) {
				String indirCatImmobile = rs.getString("INDIR_CAT_IMMOBILE");		
				try {
					codViaCatasto = rs.getBigDecimal("PKID_STRA");
				} catch (Exception e) {
					codViaCatasto = rs.getString("PKID_STRA") == null ? null : new BigDecimal(rs.getString("PKID_STRA").replace(",", "."));
				}
				prefissoViaCatasto = rs.getString("PREFISSO");
				viaCatasto = rs.getString("NOME");
				civicoCatasto = rs.getString("CIVICO");
				
				//Verifico, per ogni civico trovato associato alla UIU che corrisponda a quello dichiarato nel DOCFA
				psCiviciIndiceCD.setString(1,idDocfaIndice);
				psCiviciIndiceCD.setString(2,indirCatImmobile);
				rsIndice = psCiviciIndiceCD.executeQuery();
				
				//se non ho mai risultati , per nessun INDIR_CAT_IMMOBILE ---> indirizzo a catasto <> indirizzo docfa
				if(rsIndice.next()){
					flgIndirizzoACatasto = true;
				}
				
				i++;
			}
		}catch(Exception e){	
			throw e;
		}finally{
			DbUtils.close(rs);
			DbUtils.close(rsIndice);
			rep.setFlgIndirizzoInToponomastica(flgIndirizzoACatasto);
			
			if(i==1){
				rep.setCodViaCatasto(codViaCatasto);
				rep.setPrefissoViaCatasto(prefissoViaCatasto);
				rep.setDescViaCatasto(viaCatasto);
				rep.setCivicoCatasto(civicoCatasto);
			}
		}
	}
	
	private ArrayList<DocfaIciReportSoggBean> getTitolariUiuAnnoDocfa(DocfaIciReportBean rep, String idDocfaIndice ) throws Exception{
		ArrayList<DocfaIciReportSoggBean> titolari = new ArrayList<DocfaIciReportSoggBean>();
		ResultSet rs = null;
		ResultSet rsTitIndiceCA = null;
		ResultSet rsResidenzaTitolare = null;
		ResultSet rsRes = null;
		
		String foglioCat = cleanLeftPad(rep.getFoglio(), '0');
		String subCat = rep.getSubalterno()!=null ? cleanLeftPad(rep.getSubalterno(), '0') : this.CAT_NULL;
		String dataDocfa = rep.getDataDocfa();
		try{
			//Seleziono i Docfa da elaborare
	
			psTitUiuAnnoDocfa.setString(1, rep.getCodEnte());
			psTitUiuAnnoDocfa.setString(2, foglioCat);
			psTitUiuAnnoDocfa.setString(3, rep.getParticella());
			psTitUiuAnnoDocfa.setString(4, subCat);
			psTitUiuAnnoDocfa.setString(5, dataDocfa);
			psTitUiuAnnoDocfa.setString(6, dataDocfa);
			rs = psTitUiuAnnoDocfa.executeQuery();
			
			String tab = null;
			java.util.Date dateDocfa = (dataDocfa!=null) ? yyyyMMdd.parse(dataDocfa): null;
			java.util.Date date3112 = yyyyMMdd.parse(dataDocfa.substring(0, 4)+ "1231");
			//log.debug("Data 31-12 (Date): " + date3112);
			//log.debug("Data Docfa (Date): " + dateDocfa);
			
			while (rs.next()) {
				
				DocfaIciReportSoggBean titolare = new DocfaIciReportSoggBean();
				titolare.setFkIdRep(rep.getIdRep());
				String pkId = rs.getString("pkid");
				titolare.setCatPkid(pkId);         //Serve per cercare il titolare all'interno dell'indice di correlazione
				try {
					titolare.setPkCuaa(rs.getBigDecimal("pk_cuaa"));
				} catch (Exception e) {
					titolare.setPkCuaa(rs.getString("pk_cuaa") == null ? null : new BigDecimal(rs.getString("pk_cuaa").replace(",", ".")));
				}
				titolare.setTitolo(rs.getString("tipo_titolo"));
				try {
					titolare.setPercPoss(rs.getBigDecimal("perc_poss"));
				} catch (Exception e) {
					titolare.setPercPoss(rs.getString("perc_poss") == null ? null : new BigDecimal(rs.getString("perc_poss").replace(",", ".")));
				}				
				Date dataInizioPossesso = rs.getDate("data_inizio_possesso");
				titolare.setDataInizioPossesso(dataInizioPossesso);
				Date dataFinePossesso = rs.getDate("data_fine_possesso");
				titolare.setDataFinePossesso(dataFinePossesso);
				titolare.setCodiFisc(rs.getString("cod_fiscale"));
				titolare.setCodiPiva(rs.getString("piva"));
				titolare.setRagiSoci(rs.getString("ragi_soci")) ;
				titolare.setNome(rs.getString("nome"));
				titolare.setSesso(rs.getString("sesso"));
				titolare.setDataNasc(rs.getDate("data_nasc"));
				titolare.setDataMorte(rs.getDate("data_morte"));
				titolare.setComuNasc(rs.getString("comu_nasc"));
				titolare.setFlgPersFisica(rs.getString("flag_pers_fisica"));
				
				this.verificaTitolareInIciPerUiu(titolare, rep);
				this.verificaTitolareInIciPerCivico(titolare, rep, idDocfaIndice);
				this.verificaTitolareInIciPerUiuIndefinita(titolare, rep);
			
				
				Boolean titolareAllaData = false;
				Boolean titolareAl_3112 = false;
				if(dataInizioPossesso!=null && dataFinePossesso!=null){
					titolareAllaData = dataInizioPossesso.compareTo(dateDocfa)<=0 && dataFinePossesso.compareTo(dateDocfa)>=0;
					titolareAl_3112 = dataInizioPossesso.compareTo(date3112)<=0 && dataFinePossesso.compareTo(date3112)>=0;
				}
				
				Boolean residenteIndDocfa = false;
				Boolean residenteIndCatasto = false;
				Boolean trovati = false;
				
				Boolean anomalie = false;
				String annotazioni = "";
				if(titolare.getFlgPersFisica().equalsIgnoreCase("P")){
				
					this.verificaFamiliariDataDocfa(rep, titolare, titolare.getCatPkid());
					
					
					//Ricerca del titolare della UIU all'interno dell'Anagrafe, mediante l'indice
				
					psTitIndiceCA.setString(1, pkId);         //CONS_SOGG_TAB.PKID     
					rsTitIndiceCA = psTitIndiceCA.executeQuery();
					while(rsTitIndiceCA.next()){
						
						String idExtPersona = rsTitIndiceCA.getString("ID_EXT_PERSONA");
						String idAnaPersona = rsTitIndiceCA.getString("ID_ANA_PERS");
						
						//Selezione dell'indirizzo anagrafico associato al soggetto
						//INDIRIZZO IN ANAGRAFE AL 31-12 dell'anno del docfa (LISTA_INDIRIZZI_ANAGRAFE_DATA_DOCFA)
						psResidenzaTitolare.setString(1, idExtPersona);
						psResidenzaTitolare.setString(2, dataDocfa.substring(0, 4)+ "1231");
						rsResidenzaTitolare = psResidenzaTitolare.executeQuery();
						
						
						while(rsResidenzaTitolare.next() && !trovati){
						
							String anaCivicoResidenza = rsResidenzaTitolare.getString("ANA_CIVICO_RESIDENZA");
							
							
							if(!residenteIndCatasto){
								// PER OGNI INDIRIZZO DI RESIDENZA DEL TITOLARE TROVATO  VERIFICO SE = A INDIRIZZO A CATASTO DELL'IMMOBILE  (UNA VOLTA TRUE NON ESEGUO PIU' IL CONTROLLO)
							
								psResidenzaCivicoCatasto.setString(1, anaCivicoResidenza);
		                        psResidenzaCivicoCatasto.setString(2, rep.getCodEnte());
		                        psResidenzaCivicoCatasto.setString(3, foglioCat);
								psResidenzaCivicoCatasto.setString(4, rep.getParticella());
								psResidenzaCivicoCatasto.setString(5, subCat);
		                        
		                        rsRes = psResidenzaCivicoCatasto.executeQuery();
								if(rsRes.next()){
									residenteIndCatasto = true;
								}
							}
							
							if(!residenteIndDocfa){
								//PER OGNI NDIRIZZO DI RESIDENZA DEL TITOLARE TROVATO  VERIFICO SE = A INDIRIZZO DEL DOCFA  (UNA VOLTA TRUE NON ESEGUO PIU' IL CONTROLLO)
					
								psResidenzaCivicoDocfa.setString(1,anaCivicoResidenza); 
								psResidenzaCivicoDocfa.setString(2,idDocfaIndice); 
								
								rsRes = psResidenzaCivicoDocfa.executeQuery();
								if(rsRes.next()){
									residenteIndDocfa = true;
								}	
							}
							trovati = residenteIndDocfa && residenteIndCatasto;	
						}
					}
					
					if(idDocfaIndice.split(this.PIPE)[2].equalsIgnoreCase("")){
						addAnomalia(titolare, DocfaAnomalie.getCodNoDocfaUiu());
						//annotazioni = "Corrispondenza DOCFA_UIU mancante (NR_PROG == NULL)";
					}
					
					if(this.SOPPRESSA.equalsIgnoreCase(rep.getTipoOperDocfa())){
						titolare.addAnnotazioni("Docfa Soppressione");
					}
					
				}else{
					
					titolare.addAnnotazioni("Soggetto Giuridico");
					
				}
					        	   
				titolare.setFlgTitolareDataDocfa(titolareAllaData);
				titolare.setFlgTitolare3112(titolareAl_3112);
				
				titolare.setFlgResidIndCat3112(residenteIndCatasto);
				titolare.setFlgResidIndDcf3112(residenteIndDocfa);
			
				

				titolari.add(titolare);
			}
		}catch(Exception e){ 
			throw e; 
		}finally{
			
			DbUtils.close(rs);
			DbUtils.close(rsTitIndiceCA);
			DbUtils.close(rsResidenzaTitolare);
			DbUtils.close(rsRes);
			
		}

		return titolari;
	}
	
	protected void verificaFamiliariDataDocfa(DocfaIciReportBean rep, DocfaIciReportSoggBean titolare, String idExtPersona)throws Exception{
		ResultSet rs = null;
		List<String> ids = new ArrayList<String>();
		int numFamiliari = 0;
		try{	
			psFamiliariDataDocfa.setString(1, idExtPersona);
			psFamiliariDataDocfa.setString(2, rep.getDataDocfa());
			psFamiliariDataDocfa.setString(3, rep.getDataDocfa());
			psFamiliariDataDocfa.setString(4, idExtPersona);
			psFamiliariDataDocfa.setString(5, rep.getDataDocfa());
			psFamiliariDataDocfa.setString(6, rep.getDataDocfa());
			psFamiliariDataDocfa.setString(7, rep.getDataDocfa());
			psFamiliariDataDocfa.setString(8, rep.getDataDocfa());
			psFamiliariDataDocfa.setString(9, rep.getDataDocfa());
			rs = psFamiliariDataDocfa.executeQuery();
			while(rs.next()){
				String id = rs.getString("ID_EXT");
				this.addCodice(ids, id);
			}
			
			numFamiliari = ids.size();
			
		}catch(Exception e){throw e;
		}finally{
			DbUtils.close(rs);
			titolare.setNumFamiliari(new BigDecimal(numFamiliari));
		}
	}
	
	private void verificaTitolareInIciPerUiu(DocfaIciReportSoggBean tit, DocfaIciReportBean rep) throws Exception{
		
		ResultSet rsIciPrecedente = null;
		ResultSet rsIciSuccessivo = null;
		
		Boolean presenzaIciSuccessivo = this.NON_ELABORATO;
		Boolean presenzaIciPrecedente = this.NON_ELABORATO;
		String foglioIci = this.cleanLeftPad(rep.getFoglio(), '0');
		String particellaIci = this.cleanLeftPad(rep.getParticella(), '0');
		String subalternoIci = rep.getSubalterno()!=null ? rep.getSubalterno() : this.ICI_NULL;
		String dataDocfaSuccessivo = rep.getDataDocfaSuccessivo();
		try{
			
			psTitIciPrecedenteUiu.setString(1,tit.getCatPkid());
			psTitIciPrecedenteUiu.setString(2,rep.getDataDocfa());
			psTitIciPrecedenteUiu.setString(3,foglioIci);
			psTitIciPrecedenteUiu.setString(4,particellaIci );
			psTitIciPrecedenteUiu.setString(5,subalternoIci );
			rsIciPrecedente = psTitIciPrecedenteUiu.executeQuery();
				
			presenzaIciPrecedente = false;
			if(rsIciPrecedente.next())
				presenzaIciPrecedente = true;
			
			if(!this.SOPPRESSA.equalsIgnoreCase(rep.getTipoOperDocfa())){
			
				psTitIciSuccessivoUiu.setString(1,tit.getCatPkid());
				psTitIciSuccessivoUiu.setString(2,rep.getDataDocfa());
				psTitIciSuccessivoUiu.setString(3,dataDocfaSuccessivo!= null ? dataDocfaSuccessivo : "99991231");
				psTitIciSuccessivoUiu.setString(4,foglioIci );
				psTitIciSuccessivoUiu.setString(5,particellaIci );
				psTitIciSuccessivoUiu.setString(6,subalternoIci );
				
				rsIciSuccessivo = psTitIciSuccessivoUiu.executeQuery();
				
			/*Se ci sono più dichiarazioni ICI precedenti (dello stesso anno) verifico su tutte:
			 * se il DOCFA varia un parametro rispetto ad una dichiarazione ici, la si considera come variazione globale
			 */
			presenzaIciSuccessivo = false;
			if(rsIciSuccessivo.next())
				presenzaIciSuccessivo = true;
			}
			
		}catch(Exception e){
			throw e;
			
		}finally{
			DbUtils.close(rsIciPrecedente);
			DbUtils.close(rsIciSuccessivo);
			
			tit.setFlgTitIciUiuAnteDcf(presenzaIciPrecedente);
			tit.setFlgTitIciUiuPostDcf(presenzaIciSuccessivo);
				   
		}
	}
	
		
	private void verificaTitolareInIciPerCivico(DocfaIciReportSoggBean tit, DocfaIciReportBean rep, String idDocfaIndice) throws Exception{
		
		ResultSet rsIciPrecedente = null;
		ResultSet rsIciSuccessivo = null;
		
		Boolean presenzaIciPrecedente = this.NON_ELABORATO;
		Boolean presenzaIciSuccessivo = this.NON_ELABORATO;
		
		String dataDocfaSuccessivo = rep.getDataDocfaSuccessivo();
		try{
		
			psTitIciPrecedenteCiv.setString(1,tit.getCatPkid());
			psTitIciPrecedenteCiv.setString(2,rep.getDataDocfa());
			psTitIciPrecedenteCiv.setString(3,idDocfaIndice);
			
			rsIciPrecedente = psTitIciPrecedenteCiv.executeQuery();
				
			presenzaIciPrecedente = false;
			if(rsIciPrecedente.next())
				presenzaIciPrecedente = true;
			
			if(!this.SOPPRESSA.equalsIgnoreCase(rep.getTipoOperDocfa())){
				
				psTitIciSuccessivoCiv.setString(1,tit.getCatPkid());
				psTitIciSuccessivoCiv.setString(2,rep.getDataDocfa());
				psTitIciSuccessivoCiv.setString(3,dataDocfaSuccessivo!= null ? dataDocfaSuccessivo : "99991231");
				psTitIciSuccessivoCiv.setString(4,idDocfaIndice );
				
				rsIciSuccessivo = psTitIciSuccessivoCiv.executeQuery();
					 
				presenzaIciSuccessivo = false;
				if(rsIciSuccessivo.next())
					presenzaIciSuccessivo = true;
			}
		}catch(Exception e){
			throw e;
			
		}finally{
			DbUtils.close(rsIciPrecedente);
			DbUtils.close(rsIciSuccessivo);
			
			tit.setFlgTitIciCivAnteDcf(presenzaIciPrecedente);
			tit.setFlgTitIciCivPostDcf(presenzaIciSuccessivo);
		}
	}

	
	private void verificaTitolareInIciPerUiuIndefinita(DocfaIciReportSoggBean tit, DocfaIciReportBean rep) throws Exception{
		
		ResultSet rsIciPrecedente = null;
		ResultSet rsIciSuccessivo = null;
		
		Boolean presenzaIciPrecedente = this.NON_ELABORATO;
		Boolean presenzaIciSuccessivo = this.NON_ELABORATO;
		
		String foglioIci = this.cleanLeftPad(rep.getFoglio(), '0');
		String particellaIci = this.cleanLeftPad(rep.getParticella(), '0');
		String subalternoIci = rep.getSubalterno()!=null ? rep.getSubalterno() : this.ICI_NULL;
		String dataDocfaSuccessivo = rep.getDataDocfaSuccessivo();
		try{
		
			psTitIciPrecedente.setString(1,tit.getCatPkid());
			psTitIciPrecedente.setString(2,rep.getDataDocfa());
			psTitIciPrecedente.setString(3,foglioIci);
			psTitIciPrecedente.setString(4,particellaIci );
			psTitIciPrecedente.setString(5,subalternoIci );
			psTitIciPrecedente.setString(6,foglioIci);
			psTitIciPrecedente.setString(7,particellaIci );
			psTitIciPrecedente.setString(8,subalternoIci );
			rsIciPrecedente = psTitIciPrecedente.executeQuery();
				
			presenzaIciPrecedente = false;
			if(rsIciPrecedente.next())
				presenzaIciPrecedente = true;
			
			if(!this.SOPPRESSA.equalsIgnoreCase(rep.getTipoOperDocfa())){
				psTitIciSuccessivo.setString(1,tit.getCatPkid());
				psTitIciSuccessivo.setString(2,rep.getDataDocfa());
				psTitIciSuccessivo.setString(3,dataDocfaSuccessivo!= null ? dataDocfaSuccessivo : "99991231");
				psTitIciSuccessivo.setString(4,foglioIci );
				psTitIciSuccessivo.setString(5,particellaIci );
				psTitIciSuccessivo.setString(6,subalternoIci );
				psTitIciSuccessivo.setString(7,foglioIci );
				psTitIciSuccessivo.setString(8,particellaIci );
				psTitIciSuccessivo.setString(9,subalternoIci );
				rsIciSuccessivo = psTitIciSuccessivo.executeQuery();
					
				presenzaIciSuccessivo = false;
				if(rsIciSuccessivo.next())
					presenzaIciSuccessivo = true;
			}
		}catch(Exception e){
			throw e;
			
		}finally{
			DbUtils.close(rsIciPrecedente);
			DbUtils.close(rsIciSuccessivo);
			
			tit.setFlgTitIciAnteDcf(presenzaIciPrecedente);
			tit.setFlgTitIciPostDcf(presenzaIciSuccessivo);
		}
	}
	
	/**
	 * @param dati
	 * @return il nome della tabella creata
	 * @throws Exception 
	 * @throws Exception 
	 */
	private Boolean creaTabella(Connection conn, String tabella, String sqlCreate,String[] indici) throws Exception {
		java.sql.Statement stmt =null;
		//Connection conn  =null;	
		Boolean creata = false;
		log.debug("Creazione della tabella " + tabella);	
		try {
			//conn = RulesConnection.getConnection("DIOGENE_"+belfiore);
			
			// drop prima di creare
			String dropSql = "DROP TABLE " + tabella ;
			try {
				stmt = conn.createStatement();
		   		stmt.executeUpdate(dropSql);
				stmt.close();
			} catch (Exception e) {
				// non faccio niente
			}
			stmt = conn.createStatement();
			log.debug("SQL_CREATE: " + sqlCreate);	
	   		stmt.executeUpdate(sqlCreate);
	   		if(indici!=null){
				for(int i=0; i<indici.length; i++)
				stmt.executeUpdate(indici[i]);
			}
			stmt.close();
			creata = true;

		} catch(SQLException ex) {
			log.error("SQLException: " + ex.getMessage(),ex);
			throw ex;
		} catch (Exception e) {
			log.error("Exception: " + e.getMessage(),e);
			throw e;
		} 

		return creata;
		
		
	}
	
	private Boolean checkCampoValido (String s){
		
		Boolean valida = true;
		
		valida = (s != null) && (s.trim().length() > 0);
		
		return valida;
		
	}
	
	private Boolean checkClasseValida(String classe){
		Boolean valida = true;
		
		valida = this.checkCampoValido(classe);
		
		return valida;
	}
	
	private Boolean checkCategoriaValida(String cat){
		Boolean valida = true;
		valida = this.checkCampoValido(cat) &&  !"00".equalsIgnoreCase(cat.trim());
		return valida;
	}
	
	private Boolean checkRenditaValida(BigDecimal rendita){
		Boolean valida = false;
		if(rendita!=null)
			valida = BigDecimal.ZERO.compareTo(rendita)!= 0;
		return valida;
	}
	
	private Boolean checkIsVariato(Boolean varPrecedente, String s1, String s2){
		
		Boolean variato = varPrecedente;
		if(s1!= null && s2!=null){
			s1 = s1.trim();
			s1 = s1.length()<2 ? "0" + s1 : s1;
			variato = variato || !(s1.equalsIgnoreCase(s2.trim()));
		}else if (s1 == null && s2 == null)
			variato = variato || false;
		else
			variato = true;
			
		return variato;
	}
	
/*	private Boolean checkRenditaIsVariata(Boolean varPrecedente, BigDecimal bd1, String s2 ){
		
		
		Boolean variato = varPrecedente;
		if(bd1!= null && s2!=null){
			s2 = s2.replace(',', '.');
			
			BigDecimal bd2 = new BigDecimal(s2);
			
			variato = variato || bd1.compareTo(bd2)!= 0;
			
			if(variato)
				log.debug(bd1.toString() +"--------"+ bd2.toString());
		}else if (bd1 == null && s2 == null)
			variato = variato || false;
		else
			variato = true;
		
		
			
		return variato;
	}*/
	
	private BigDecimal[] getMaxVarRendita(BigDecimal[] maxVarRendita, BigDecimal bd1, BigDecimal bd2){
		
		BigDecimal dimRen = new BigDecimal(0);
		BigDecimal augRen = new BigDecimal(0);
		
	/*	if(bd1!= null && s2!=null){
			s2 = s2.replace(',', '.');
			
			BigDecimal bd2 = new BigDecimal(s2);
			if(bd1.compareTo(bd2)> 0)
				dimRen = bd1.subtract(bd2);
			
			else if(bd1.compareTo(bd2) < 0)
				augRen = bd2.subtract(bd1);
			
		}else if (bd1 == null && s2 != null)
			augRen = new BigDecimal(s2);
		else if (bd1 != null && s2 == null)
			dimRen = bd1;*/
		
		if(bd1!= null && bd2!=null){
			
			if(bd1.compareTo(bd2)> 0)
				dimRen = bd1.subtract(bd2);
			
			else if(bd1.compareTo(bd2) < 0)
				augRen = bd2.subtract(bd1);
			
		}else if (bd1 == null && bd2 != null)
			augRen = bd2;
		else if (bd1 != null && bd2 == null)
			dimRen = bd1;
			
		maxVarRendita[0] = maxVarRendita[0].compareTo(dimRen) > 0 ? maxVarRendita[0] : dimRen;
		maxVarRendita[1] = maxVarRendita[1].compareTo(augRen) > 0 ? maxVarRendita[1] : augRen;
			
		return maxVarRendita;
	}
	
	private BigDecimal getMaxVarRenditaPerc(BigDecimal maxVarRendita, String s2){
		
		//TODO
		
		return maxVarRendita;
	}
	
	
	private String cleanLeftPad(String s, char pad){
		if(s!= null){
			//s = s.trim();
			while (s.length() > 1 && s.charAt(0) == pad)
				s = s.substring(1);
		}
		return s;
	}
	
	
	private int compareNullableValue(Comparable c1, Comparable c2) {
		int res;
		res = (c1 != null ? (c2 != null ? c1.compareTo(c2) : 1)
				: (c2 == null ? 0 : -1));
		return res;
	}
	
	
	private String evalToNumeroCivico(String numeroCivico) {
		String newNumeroCivico = null;
		if (numeroCivico != null) {
			try {
				newNumeroCivico = String.valueOf(Integer.valueOf(numeroCivico));
			} catch (Exception e) {
				newNumeroCivico = numeroCivico.trim().toUpperCase();
			}
		}
		return newNumeroCivico;
	}
	
	
	private void controllaClassamento(String zona, DocfaIciReportBean rep) throws Exception{
		Integer classeMin = null;
		String classeAnomala = null;
		ResultSet rs = null;
		HashMap<String, Object> verifica = new HashMap<String, Object>();
		String categoria = rep.getCategoriaDocfa();
		String classe = rep.getClasseDocfa();

		try{
		if (categoria != null && !categoria.trim().equalsIgnoreCase("")){
			
			categoria = categoria.trim().toUpperCase();
			/*
			 * Logica per anomalie di classe: preve il recupero della classe
			 * minima e nel caso di C06 devo diminuire di 3 unità
			 */
			if (categoria.startsWith("C")|| categoria.equalsIgnoreCase("A10")) {
				
				// Mostro la Classe Maggiormente Frequente
				psVerificaClassamento.setString(1, checkNullString(zona));
				psVerificaClassamento.setString(2, checkNullString(rep.getFoglio()));
				psVerificaClassamento.setString(3, checkNullString(categoria));
				rs = psVerificaClassamento.executeQuery();
				if(rs.next()){
					String minClasse = (String)rs.getString("classe_min");
					classeMin = parsStringToInt(minClasse);
					if (categoria.equalsIgnoreCase("C06"))
						classeMin -= 3;		
					
					classeAnomala = (parsStringToInt(classe) >= classeMin )? "0" : "1" ;
				}
			}else
				classeAnomala = "0";			
		}
		}catch(NumberFormatException nfe){
			//Classe U parificata alla prima(?)
			if(classe.trim().toUpperCase().equalsIgnoreCase("U"))
				classeAnomala = classeMin == 1 ? "0" : "1" ;
			else
				classeAnomala = "1";
		}catch(Exception e){
			throw e;
		}finally{
			
			rep.setClasseMin(classeMin!= null ? String.valueOf(classeMin) : null);
			rep.setFlgAnomaliaClasse(classeAnomala);
			if("1".equalsIgnoreCase(classeAnomala))
				addAnomalia(rep, DocfaAnomalie.getCodAnomaliaClassamentoDocfa());
			DbUtils.close(rs);
		}
	}
	
	public static String checkNullString(String s){		
		if (s != null && !s.trim().equalsIgnoreCase(""))
			return s.trim();
		else
			return "";
	}
	
	public static int parsStringToInt(String s){
		int i = -1;

		if (s != null && !s.trim().equalsIgnoreCase("")){
			i = Integer.parseInt(s.trim());
		}
		return i;
	}
	
	private String concatCivici(String[] civici){
		String sCivici = null;
		if(civici[0]!=null && civici[0].trim().length()>0)
			sCivici = civici[0];
		if(civici[1]!=null && civici[1].trim().length()>0)
			sCivici += this.SEPARATORE + civici[1];
		if(civici[2]!=null && civici[2].trim().length()>0)
			sCivici += this.SEPARATORE + civici[2];
		
		return sCivici;
	}
	
	private Boolean checkFoglioValido(String foglio){
		Boolean valida = true;
		try{
			if(this.checkCampoValido(foglio))
				Integer.parseInt(foglio);
		}catch(NumberFormatException nfe){
			valida = false;
		}
		return valida;
	}
	
	
	//Dati da Docfa_Dati_Generali
	private void getDocfaDatiGenerali(DocfaIciReportBean rep )throws Exception{
		
		ResultSet rs = null;
		Date dataVariazione = null;
		String causale = null;
		Integer uiuCostituite = null;
		Integer uiuSoppresse = null;
		Integer uiuVariate = null;
		String docfaCessazionePura = null;
		
		try{
			psDocfaDatiGenerali.setDate(1,rep.getFornitura());
			psDocfaDatiGenerali.setString(2,rep.getProtocolloDocfa());
			rs = psDocfaDatiGenerali.executeQuery();
			
			if(rs.next()){
				 dataVariazione = rs.getDate("DATA_VARIAZIONE");
				 causale = rs.getString("CAUSALE_NOTA_VAX");
				 uiuCostituite = rs.getInt("UIU_IN_COSTITUZIONE");
				 uiuSoppresse = rs.getInt("UIU_IN_SOPPRESSIONE");
				 uiuVariate = rs.getInt("UIU_IN_VARIAZIONE");
				 docfaCessazionePura = (uiuCostituite == 0 && uiuSoppresse > 0) ? "1": "0";
			}
		
		} catch (Exception e) {
			throw e;
	
		} finally {
			DbUtils.close(rs);
			
			rep.setUiuSoppresse(uiuSoppresse);
			rep.setUiuCostituite(uiuCostituite);
			rep.setUiuVariate(uiuVariate);
			rep.setCausaleDocfa(causale);
			rep.setFlgDocfaSNoC(docfaCessazionePura);
			rep.setDataVariazione(dataVariazione);
	
		}
	}
	
	//Dati Dichiarante
	private void getDichiaranteDocfa(DocfaIciReportBean rep )throws Exception{
		ResultSet rs = null;
		
		try{
			
			psDocfaDichiarante.setDate(1,rep.getFornitura());
			psDocfaDichiarante.setString(2,rep.getProtocolloDocfa());
			rs = psDocfaDichiarante.executeQuery();
			
			if(rs.next()){
				rep.setNomeDichiarante(rs.getString("DIC_NOME"));
				rep.setCognomeDichiarante(rs.getString("DIC_COGNOME"));
				rep.setViaResDichiarante(rs.getString("DIC_RES_INDIR"));
				rep.setCivicoResDichiarante(rs.getString("DIC_RES_NUMCIV"));
				rep.setComuneResDichiarante(rs.getString("DIC_RES_COM"));
				rep.setCapResDichiarante(rs.getString("DIC_RES_CAP"));
				rep.setProvResDichiarante(rs.getString("DIC_RES_PROV"));
			}
		
		} catch (Exception e) {
			throw e;
	
		} finally {
			DbUtils.close(rs);
		}
	}
	
	protected void addAnomalia(DocfaIciReportBean rep, String codAnomalia){
		rep.setFlgAnomalie(true);
		rep.addcodAnomalie(codAnomalia);
		
	}
	
	protected void addAnomalia(DocfaIciReportSoggBean tit, String codAnomalia){
		tit.setFlgAnomalia(true);
		tit.addcodAnomalie(codAnomalia);
		
	}
	
	private boolean addCodice(List<String> ids, String nuovoId){
		boolean esiste = false;
		Iterator i = ids.iterator();
		while(i.hasNext() && !esiste){
			String id = (String) i.next();
			if(id.equals(nuovoId))
				esiste = true;
		}

		if(!esiste)
			ids.add(nuovoId);
		
		return !esiste;
	}
	
	private void getJellyParam(Context ctx) throws Exception {
		try {
			
			int index = 1;
			log.debug("**************************************************************rengine.rule.param.in."+index+".descr");
			this.SQL_DOCFA = getJellyParam(ctx, index++, "SQL_DOCFA");
			this.SQL_ICI_PRECEDENTE = getJellyParam(ctx, index++, "SQL_ICI_PRECEDENTE");
			this.SQL_DOCFA_SUCCESSIVO = getJellyParam(ctx, index++, "SQL_DOCFA_SUCCESSIVO");
			this.SQL_ICI_SUCCESSIVO = getJellyParam(ctx, index++, "SQL_ICI_SUCCESSIVO");
			this.SQL_DOCFA_IN_ANNO  = getJellyParam(ctx, index++, "SQL_DOCFA_IN_ANNO");
			this.SQL_CIVICI_UIU_CATASTO  = getJellyParam(ctx, index++, "SQL_CIVICI_UIU_CATASTO");
			this.SQL_INDICE_CIVICO_CatDocfa = getJellyParam(ctx, index++, "SQL_INDICE_CIVICO_CatDocfa");
			this.SQL_UIU_CATASTO = getJellyParam(ctx, index++, "SQL_UIU_CATASTO");
			this.SQL_TITOLARI_UIU_ANNO_DOCFA = getJellyParam(ctx, index++, "SQL_TITOLARI_UIU_ANNO_DOCFA");
			this.SQL_INDICE_TITOLARE_CatAnagrafe  = getJellyParam(ctx, index++, "SQL_INDICE_TITOLARE_CatAnagrafe");
			this.SQL_RESIDENZA_TITOLARE = getJellyParam(ctx, index++, "SQL_RESIDENZA_TITOLARE");
			this.SQL_INDICE_RESIDENZA_CIVICO_CATASTO = getJellyParam(ctx, index++, "SQL_INDICE_RESIDENZA_CIVICO_CATASTO");
			this.SQL_INDICE_RESIDENZA_CIVICO_DOCFA  = getJellyParam(ctx, index++, "SQL_INDICE_RESIDENZA_CIVICO_DOCFA");
			this.SQL_DOCFA_CONTEMPORANEI = getJellyParam(ctx, index++, "SQL_DOCFA_CONTEMPORANEI");
			this.SQL_VERIFICA_CLASSAMENTO  = getJellyParam(ctx, index++, "SQL_VERIFICA_CLASSAMENTO");
			this.SQL_DOCFA_SUCC = getJellyParam(ctx, index++, "SQL_DOCFA_SUCC");
			this.SQL_DOCFA_DATI_CENSUARI = getJellyParam(ctx, index++, "SQL_DOCFA_DATI_CENSUARI");
			this.SQL_DOCFA_DATI_GENERALI = getJellyParam(ctx, index++, "SQL_DOCFA_DATI_GENERALI");
			this.SQL_DOCFA_DATA_REGISTRAZIONE = getJellyParam(ctx, index++, "SQL_DOCFA_DATA_REGISTRAZIONE");
			this.SQL_DOCFA_DICHIARANTE = getJellyParam(ctx, index++, "SQL_DOCFA_DICHIARANTE");
			this.SQL_SOGG_ICI_PRECEDENTE_UIU = getJellyParam(ctx, index++, "SQL_SOGG_ICI_PRECEDENTE_UIU");
			this.SQL_SOGG_ICI_SUCCESSIVO_UIU = getJellyParam(ctx, index++, "SQL_SOGG_ICI_SUCCESSIVO_UIU");
			this.SQL_SOGG_ICI_PRECEDENTE_UND = getJellyParam(ctx, index++, "SQL_SOGG_ICI_PRECEDENTE_UND");
		    this.SQL_SOGG_ICI_SUCCESSIVO_UND = getJellyParam(ctx, index++, "SQL_SOGG_ICI_SUCCESSIVO_UND");
			this.SQL_SOGG_ICI_PRECEDENTE_CIV = getJellyParam(ctx, index++, "SQL_SOGG_ICI_PRECEDENTE_CIV");
			this.SQL_SOGG_ICI_SUCCESSIVO_CIV = getJellyParam(ctx, index++, "SQL_SOGG_ICI_SUCCESSIVO_CIV");
			this.SQL_FAMILIARI_DATA_DOCFA = getJellyParam(ctx, index++, "SQL_FAMILIARI_DATA_DOCFA");
			this.SQL_UIU_CATASTO_POST = getJellyParam(ctx, index++, "SQL_UIU_CATASTO_POST");
			this.SQL_DOCFA_ICI_REPORT= getJellyParam(ctx, index++, "SQL_DOCFA_ICI_REPORT");
			this.SQL_DOCFA_ICI_REPORT_SUM_PRE= getJellyParam(ctx, index++, "SQL_DOCFA_ICI_REPORT_SUM_PRE");
			this.SQL_DOCFA_ICI_REPORT_SUM_POST= getJellyParam(ctx, index++, "SQL_DOCFA_ICI_REPORT_SUM_POST");
			this.SQL_UPDATE_SUM_IMPONIBILE= getJellyParam(ctx, index++, "SQL_UPDATE_SUM_IMPONIBILE");
			
		}catch (Exception e) {
			log.error("Exception: " + e.getMessage());
			throw e;
		}
	}
	
	private String getJellyParam(Context ctx, int index, String desc) throws Exception{
		
		String variabile = "";
		
		log.debug("rengine.rule.param.in."+index+".descr");
		
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
		
		log.debug("Query "+desc+" da eseguire:\n"+ variabile);
		
		return variabile;
	}
	
}