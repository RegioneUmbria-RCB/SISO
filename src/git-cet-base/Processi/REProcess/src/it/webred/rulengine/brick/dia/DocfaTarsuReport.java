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
import it.webred.rulengine.brick.dia.bean.tributidocfa.DocfaTarReportBean;
import it.webred.rulengine.brick.dia.bean.tributidocfa.DocfaTarReportSoggBean;
import it.webred.rulengine.brick.superc.dia.AbstractDiagnosticsExport;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.def.TypeFactory;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

/**
 * Valuta i docfa rilevati dalle tabelle DOCFA_ICI_REPORT e DOCFA_UIU per la
 * ricerca di agganci ed eventuali altre informazioni
 * 
 * 
 */
public class DocfaTarsuReport extends AbstractDiagnosticsExport implements Rule {

	private static final org.apache.log4j.Logger log = Logger.getLogger(DocfaTarsuReport.class.getName());
	private final Boolean NON_ELABORATO = null;
	private final String SEPARATORE = "@";
	private final String PIPE = "|";
	private final String SOPPRESSA = "S";
	private final String[] codIndiceDemografia = {"1","1"};
	private final String[] codIndiceCatastoSoggetti= {"4","3"};
	private final String[] codIndiceTarsuSoggetti = {"2","4"};

	public static Hashtable<String, String> htSuperficiMedie = new Hashtable<String, String>();
	
	private PreparedStatement psCiviciCatasto = null;
	private PreparedStatement psCiviciIndiceCD = null;
	private PreparedStatement psTitUiuAnnoDocfa = null;
	private PreparedStatement psTitIndiceCA = null;
	private PreparedStatement psResidenzaTitolare = null;
	private PreparedStatement psResidenzaCivicoDocfa = null;
	private PreparedStatement psResidenzaCivicoCatasto = null;
	private PreparedStatement psDocfaInAnno = null;
	private PreparedStatement psDocfaContemporanei = null;
	private PreparedStatement psTarSuccessivo = null;
/*	private PreparedStatement psTarSuccessivo20 = null;
	private PreparedStatement psTarSuccessivoDS = null;*/
	private PreparedStatement psDocfaSuccessivo = null;
	private PreparedStatement psTarPrecedente = null;
	private PreparedStatement psCatasto = null;
	private PreparedStatement psSuperficiPerVano = null;
	private PreparedStatement psTitTarSuccessivo20 = null;
	private PreparedStatement psTitTarSuccessivoDS = null;
	//private PreparedStatement psTitTarPrecedente = null;
	private PreparedStatement psDocfaDatiGenerali = null;
	private PreparedStatement psDocfaDatiCensuari = null;
	private PreparedStatement psDocfaDataregistrazione = null;
	private PreparedStatement psFamiliariDataDocfa = null;
	private PreparedStatement psDocfaDichiarante = null;
	private PreparedStatement psDocfaInParteDueH = null;
	
	private PreparedStatement psTitTarSuccessivo = null;
	private PreparedStatement psTitTarPrecedente = null;
	private PreparedStatement psTitTarSuccessivoUiu = null;
	private PreparedStatement psTitTarPrecedenteUiu = null;
	private PreparedStatement psTitTarSuccessivoCiv = null;
	private PreparedStatement psTitTarPrecedenteCiv = null;

	private final String DOCFA_NULL = "-";
	private final String TAR_NULL = "0000";
	private final String CAT_NULL = "0";

	// Query DOCFA
	private String SQL_DOCFA = null;
	private String SQL_DOCFA_DATI_CENSUARI = null;
	private String SQL_DOCFA_DATI_GENERALI = null;
	protected String SQL_DOCFA_DICHIARANTE = null;
	private String SQL_DOCFA_SUCCESSIVO = null;
	private String SQL_DOCFA_IN_ANNO = null;
	private String SQL_DOCFA_CONTEMPORANEI = null;
	private String SQL_SUPERFICI_PER_VANO = null;
	private String SQL_DOCFA_DATA_REGISTRAZIONE = null;
	private String SQL_DOCFA_IN_PARTE_DUE_H = null;

	// Query Catasto
	private String SQL_UIU_CATASTO = null;
	private String SQL_TITOLARI_UIU_ANNO_DOCFA = null;

	// Query Tarsu
	private String SQL_TARSU_PRECEDENTE = null;
	private String SQL_TARSU_SUCCESSIVO = null;
	/*private String SQL_TARSU_SUCCESSIVO_20 = null;
	private String SQL_TARSU_SUCCESSIVO_DS = null;*/
	private String SQL_TIT_TARSU_PRECEDENTE = null;
	private String SQL_TIT_TARSU_SUCCESSIVO_20 = null;
	private String SQL_TIT_TARSU_SUCCESSIVO_DS = null;

	private String SQL_CIVICI_UIU_CATASTO = null;
	private String SQL_INDICE_CIVICO_CatDocfa = null;
	private String SQL_INDICE_TITOLARE_CatAnagrafe = null;
	private String SQL_RESIDENZA_TITOLARE = null;
	private String SQL_INDICE_RESIDENZA_CIVICO_CATASTO = null;
	private String SQL_INDICE_RESIDENZA_CIVICO_DOCFA = null;
	
	protected String SQL_FAMILIARI_DATA_DOCFA = null;	
	private String SQL_SOGG_TAR_PRECEDENTE_UIU = null;
	private String SQL_SOGG_TAR_SUCCESSIVO_UIU = null;
	private String SQL_SOGG_TAR_PRECEDENTE_CIV = null;
	private String SQL_SOGG_TAR_SUCCESSIVO_CIV = null;
	private String SQL_SOGG_TAR_PRECEDENTE_UND = null;
	private String SQL_SOGG_TAR_SUCCESSIVO_UND = null;

	private String DATA_SCADENZA_TARSU = "0120";
	private final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");


	public DocfaTarsuReport(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);

		htSuperficiMedie.put("A01", "24");
		htSuperficiMedie.put("A02", "19");
		htSuperficiMedie.put("A03", "18");
		htSuperficiMedie.put("A04", "19");
		htSuperficiMedie.put("A05", "19");
		// htSuperficiMedie.put("A06", "0");
		htSuperficiMedie.put("A07", "19");
		htSuperficiMedie.put("A08", "24");
		// htSuperficiMedie.put("A09", "0");
		htSuperficiMedie.put("A10", "22");
		// htSuperficiMedie.put("C06", "0");
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		String idElaborazione = null;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		ResultSet rsTarSuccessivo = null;
		ResultSet rsDocfaSuccessivo = null;
		ResultSet rsDocfaDatiCensuari = null;
		log.info("DocfaTarsuReport in esecuzione...");

		try {
			log.debug("Recupero parametro da contesto");
			conn = ctx.getConnection((String) ctx.get("connessione"));

			// recupero parametri catena jelly
			this.getJellyParam(ctx);

			// recupero eventuali parametri di ingresso
			// PreparedStatement pstmt = null;
			List<RAbNormal> abnormals = new ArrayList<RAbNormal>();

			try {
				psDocfaDichiarante = conn.prepareCall(this.SQL_DOCFA_DICHIARANTE ,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				
				psDocfaDatiCensuari = conn.prepareCall(this.SQL_DOCFA_DATI_CENSUARI ,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				psDocfaDatiGenerali = conn.prepareCall(this.SQL_DOCFA_DATI_GENERALI,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				psDocfaInAnno = conn.prepareCall(this.SQL_DOCFA_IN_ANNO,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				psTarPrecedente = conn.prepareCall(this.SQL_TARSU_PRECEDENTE,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				psTarSuccessivo = conn.prepareCall(this.SQL_TARSU_SUCCESSIVO,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				//psTarSuccessivo20 = conn.prepareCall(this.SQL_TARSU_SUCCESSIVO_20,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				psDocfaSuccessivo = conn.prepareCall(this.SQL_DOCFA_SUCCESSIVO,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				//psTarSuccessivoDS = conn.prepareCall(this.SQL_TARSU_SUCCESSIVO_DS,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				psCatasto = conn.prepareCall(this.SQL_UIU_CATASTO,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				psCiviciCatasto = conn.prepareCall(this.SQL_CIVICI_UIU_CATASTO,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				psCiviciIndiceCD = conn.prepareCall(this.SQL_INDICE_CIVICO_CatDocfa,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

				psTitUiuAnnoDocfa = conn.prepareCall(this.SQL_TITOLARI_UIU_ANNO_DOCFA,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				psTitIndiceCA = conn.prepareCall(this.SQL_INDICE_TITOLARE_CatAnagrafe,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				psResidenzaTitolare = conn.prepareCall(this.SQL_RESIDENZA_TITOLARE,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

				psResidenzaCivicoCatasto = conn.prepareCall(this.SQL_INDICE_RESIDENZA_CIVICO_CATASTO,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				psResidenzaCivicoDocfa = conn.prepareCall(this.SQL_INDICE_RESIDENZA_CIVICO_DOCFA,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

				psDocfaContemporanei = conn.prepareCall(this.SQL_DOCFA_CONTEMPORANEI,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				psSuperficiPerVano = conn.prepareCall(this.SQL_SUPERFICI_PER_VANO,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

			//	psTitTarPrecedente = conn.prepareCall(this.SQL_TIT_TARSU_PRECEDENTE,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
		    //  psTitTarSuccessivo20 = conn.prepareCall(this.SQL_TIT_TARSU_SUCCESSIVO_20,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			//	psTitTarSuccessivoDS = conn.prepareCall(this.SQL_TIT_TARSU_SUCCESSIVO_DS,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				
				psTitTarPrecedenteUiu = conn.prepareCall(this.SQL_SOGG_TAR_PRECEDENTE_UIU, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
				psTitTarSuccessivoUiu = conn.prepareCall(this.SQL_SOGG_TAR_SUCCESSIVO_UIU, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
				psTitTarPrecedente = conn.prepareCall(this.SQL_SOGG_TAR_PRECEDENTE_UND, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
				psTitTarSuccessivo = conn.prepareCall(this.SQL_SOGG_TAR_SUCCESSIVO_UND, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
				psTitTarPrecedenteCiv = conn.prepareCall(this.SQL_SOGG_TAR_PRECEDENTE_CIV, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
				psTitTarSuccessivoCiv = conn.prepareCall(this.SQL_SOGG_TAR_SUCCESSIVO_CIV, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
				psFamiliariDataDocfa = conn.prepareCall(this.SQL_FAMILIARI_DATA_DOCFA, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
				psDocfaInParteDueH = conn.prepareCall(this.SQL_DOCFA_IN_PARTE_DUE_H, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
				
				
				psDocfaDataregistrazione = conn.prepareCall(this.SQL_DOCFA_DATA_REGISTRAZIONE,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				
				// Seleziono i Docfa da elaborare
				log.debug("Selezione dei DOCFA da elaborare");
				cs = conn.prepareCall(this.SQL_DOCFA,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				rs = cs.executeQuery();

				boolean primorecord = true;
				Boolean tab = null;
				Boolean tabSoggetti = null;

				int itot = 0;
				while (rs.next()) {
					
					DocfaTarReportBean rep = new DocfaTarReportBean();
					
					String civici[] = null;
					String viaDocfa = null;
					
					// Dichiarazione TARSU presentata a seguito di aggiornamento DOCFA
					Boolean presenzaTarSuccessivo20 = this.NON_ELABORATO;
					Boolean presenzaTarSuccessivoDS = this.NON_ELABORATO;
					// Boolean varSupDocfaATarsu = this.NON_ELABORATO;
					
					rep.setFlgAnomalie(false);
					rep.setFlgElaborato(false);
					
					ArrayList<DocfaTarReportSoggBean> titolari = new ArrayList<DocfaTarReportSoggBean>();
					
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
					rsDocfaDatiCensuari = psDocfaDatiCensuari.executeQuery();
					
					int countDC = 0;
					int campiValidi = 0;
					int campiValidiOpt = 0;
					
					DocfaDatiCensuariBean ddcOpt = new DocfaDatiCensuariBean();
					while(rsDocfaDatiCensuari.next()){
						countDC++;
						DocfaDatiCensuariBean ddc = new DocfaDatiCensuariBean();
						ddc.setDataDocfa(rsDocfaDatiCensuari.getString("DATA_REGISTRAZIONE"));
						ddc.setIdUiu(rsDocfaDatiCensuari.getString("IDENTIFICATIVO_IMMOBILE"));
						ddc.setPrefissoViaDocfa(rsDocfaDatiCensuari.getString("TOPONIMO"));
						ddc.setViaDocfa(rsDocfaDatiCensuari.getString("INDIRIZZO"));
						String [] civiciDC = new String[3];
						civiciDC[0] = cleanLeftPad(rsDocfaDatiCensuari.getString("CIVICO_1"),'0');
						civiciDC[1] = cleanLeftPad(rsDocfaDatiCensuari.getString("CIVICO_2"),'0');
						civiciDC[2] = cleanLeftPad(rsDocfaDatiCensuari.getString("CIVICO_3"),'0');					
						ddc.setCiviciDocfa(civiciDC);
						
						if(!this.SOPPRESSA.equalsIgnoreCase(rep.getTipoOperDocfa())){
							ddc.setCategoriaDocfa(rsDocfaDatiCensuari.getString("CATEGORIA"));
							ddc.setSupDocfaCens(rsDocfaDatiCensuari.getString("SUPERFICIE"));
							ddc.setConsistenzaDocfa(rsDocfaDatiCensuari.getString("CONSISTENZA"));
							
							campiValidi = checkCategoriaValida(ddc.getCategoriaDocfa()) ? campiValidi+1 : campiValidi;
							campiValidi = checkSuperficieValida(ddc.getSupDocfaCens()) ?  campiValidi+1 : campiValidi;
						}
						
						if(campiValidi >= campiValidiOpt){
							ddcOpt = ddc;
							campiValidiOpt = campiValidi;
						}
					}
					
					String idUiu = ddcOpt.getIdUiu();
					
					rep.setDataDocfa(ddcOpt.getDataDocfa());
					rep.setCategoriaDocfa(ddcOpt.getCategoriaDocfa());
					rep.setSupDocfaCens(ddcOpt.getSupDocfaCens());
					rep.setConsistenzaDocfa(ddcOpt.getConsistenzaDocfa());
/*					
 *                  rep.setPrefissoViaDocfa(ddcOpt.getPrefissoViaDocfa());
					
					viaDocfa = (countDC > 0  && ddcOpt.getViaDocfa()!=null) ? ddcOpt.getViaDocfa() : viaDocfaUiu;
					viaDocfa = viaDocfa!= null ? viaDocfa.toUpperCase() : viaDocfa;
					rep.setViaDocfa(viaDocfa);
					
					civici =   (countDC > 0 && ddcOpt.getViaDocfa()!=null) ? ddcOpt.getCiviciDocfa() : civiciUiu;
					rep.setCiviciDocfa(this.concatCivici(civici));
					*/
					
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
					
					controllaConsistenza(rep);
					
					
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
						
					if (this.checkFoglioValido(rep.getFoglio()) && rep.getParticella() != null) {
						if (progUiu != null ) {
							// Verifico la presenza dell'indirizzo dichiarato nel DOCFA, all'interno della TOPONOMASTICA
							this.verificaIndirizzoDocfa(rep,idDocfaIndice);
						}

						if (rep.getDataDocfa() != null) {
							boolean catDocfaVal = this.checkCategoriaValida(rep.getCategoriaDocfa());
							if(!this.SOPPRESSA.equalsIgnoreCase(rep.getTipoOperDocfa())){
								if(!catDocfaVal)
									addAnomalia(rep, DocfaAnomalie.getCodCategoriaDocfaErrata());
							}
							
							//String dataScadenzaTarsu = getDataScadenzaTarsu(dataDocfa);
							this.calcolaSuperficieC340(conn, rep, idUiu);
							
							// Estrazione del numero di docfa per la uiu nello stesso anno
							calcolaNumDocfaUiuInAnno(rep);

							// Estrazione della data del docfa successivo a quello corrente (non dello stesso anno)
							getDataDocfaSuccessivo(rep);

							getDatiCatastoAllaData(rep);

							//Recupero le titolarità della UIU nell'anno del DOCF
							titolari = this.getTitolariUiuAnnoDocfa(rep,idDocfaIndice);

							if (progUiu != null)
								getNumeroDocfaContemporanei(rep);
							

							//if (!this.COSTITUITA.equalsIgnoreCase(tipoOperDocfa))
							log.debug(rep.getFoglio()+" | "+rep.getParticella()+" | "+rep.getSubalterno()+" | "+rep.getDataDocfa());
							getTarsuPrecedente(rep);
							
							if (!this.SOPPRESSA.equalsIgnoreCase(rep.getTipoOperDocfa())) 
								getTarsuSuccessiva(rep);
							
							rep.setFlgElaborato(true);
						} 
					} else {
						
						rep.setFlgElaborato(false);
						if(rep.getFoglio() == null)
							addAnomalia(rep, DocfaAnomalie.getCodFoglioNull());
							//annotazioni += "Foglio Docfa NULL"+ this.SEPARATORE;
						else if(!this.checkFoglioValido(rep.getFoglio()))
							addAnomalia(rep,DocfaAnomalie.getCodFoglioInvalido());
							//annotazioni += "Formato Foglio Docfa NON VALIDO"+ this.SEPARATORE;
						
						if(rep.getParticella() == null)
							addAnomalia(rep, DocfaAnomalie.getCodParticellaNull());
					}
				
					LinkedHashMap<String,Object> dati = rep.getDati();
					
					if (primorecord) {
						tab = creaTabella(conn, DocfaTarReportBean.tabReport,DocfaTarReportBean.SQL_CREATE, DocfaTarReportBean.SQL_INDICI);
						tabSoggetti = creaTabella(conn, DocfaTarReportSoggBean.tabReport,DocfaTarReportSoggBean.SQL_CREATE, DocfaTarReportSoggBean.SQL_INDICI);
						
						//Creo tabella anomalie
						creaTabella(conn, DocfaAnomalie.getTabAnomalie(), DocfaAnomalie.getSQL_CREATE(), null);
						ArrayList<LinkedHashMap<String, Object>> datiAnomalie = DocfaAnomalie.getDatiAnomalie();
						for(int a = 0; a< datiAnomalie.size(); a++){
							insert(DocfaAnomalie.getTabAnomalie(), datiAnomalie.get(a), conn);
						}
						conn.commit();
						primorecord = false;
					}

					if (tab != null && tab && !dati.isEmpty()) {
						
						if(itot%10 == 0)
							log.info("Inserimento riga"+ itot);
						
						insert(DocfaTarReportBean.tabReport, dati, conn);
					}

					if (tabSoggetti != null && tabSoggetti) {
						// Inseririsce i titolari delle UIU con la corrispondente elaborazione
						for (int i = 0; i < titolari.size(); i++) {
							DocfaTarReportSoggBean titolare = titolari.get(i);
							LinkedHashMap<String, Object> datiSogg = titolare.getDatiReportSogg();
 
							if (!datiSogg.isEmpty())
								insert(DocfaTarReportSoggBean.tabReport, datiSogg, conn);
						}
					}

					itot++;
				}

			} finally {

				DbUtils.close(rsTarSuccessivo);
				DbUtils.close(rsDocfaSuccessivo);

				/*DbUtils.close(psTarSuccessivo20);
				DbUtils.close(psTarSuccessivoDS);*/
				DbUtils.close(psDocfaSuccessivo);
				DbUtils.close(psTarPrecedente);
				DbUtils.close(psDocfaInAnno);
				DbUtils.close(psCatasto);
				DbUtils.close(psCiviciCatasto);
				DbUtils.close(psCiviciIndiceCD);
				DbUtils.close(psTitUiuAnnoDocfa);
				DbUtils.close(psTitIndiceCA);
				DbUtils.close(psResidenzaTitolare);
				DbUtils.close(psResidenzaCivicoCatasto);
				DbUtils.close(psResidenzaCivicoDocfa);
				DbUtils.close(psDocfaContemporanei);
				DbUtils.close(psSuperficiPerVano);
				DbUtils.close(psTitTarSuccessivo20);
				DbUtils.close(psTitTarSuccessivoDS);
				DbUtils.close(psTitTarPrecedente);
				DbUtils.close(psDocfaDatiGenerali);
				DbUtils.close(psDocfaDatiCensuari);
				DbUtils.close(psDocfaDichiarante);
				DbUtils.close(psDocfaDataregistrazione);
				DbUtils.close(psDocfaInParteDueH);
				
				DbUtils.close(rs);
				DbUtils.close(cs);

				// DbUtils.close(conn);
			}

			log.info("DocfaTarsuReport eseguito...");
			ApplicationAck ack = new ApplicationAck(abnormals,
					"Report Tarsu-Docfa eseguito");
			return ack;

		} catch (Exception e) {
			log.error("Errore nell'esecuzione di Report Tarsu-Docfa ("+idElaborazione+")",e);
			
			ErrorAck ea = new ErrorAck(e);
			return ea;
		}

	}

	private void insert(String tab, LinkedHashMap<String, Object> dati,
			Connection conn) throws Exception {

		if (dati.isEmpty())
			return;

		java.sql.PreparedStatement stmt = null;
		// Connection conn =null;

		String sqlParams = "INSERT INTO " + tab + " (";
		String sqlIns = " VALUES (";

		try {
			// conn = RulesConnection.getConnection("DIOGENE_"+belfiore);
			Set s = dati.keySet();
			Iterator i = s.iterator();
			int c = 1;
			while (i.hasNext()) {
				Object key = i.next();
				String virgola = null;
				if (c != 1)
					virgola = ",";
				else {
					virgola = " ";
				}
				sqlIns += virgola + "?";
				sqlParams += virgola + (String) key;
				c++;
			}
			sqlIns += ")";
			sqlParams += ") ";
			sqlIns = sqlParams + sqlIns;
			stmt = conn.prepareStatement(sqlIns);
			i = dati.keySet().iterator();
			c = 1;
			while (i.hasNext()) {
				Object key = i.next();
				Object value = dati.get(key);
				String virgola = null;
				if (c != 1)
					virgola = ",";
				else {
					virgola = " ";
				}

				String skey = (String) key;
				//log.debug(skey + ":* " + value);

				if (value != null) {
					if (value instanceof Date)
						stmt.setDate(c, (Date) value);
					else if (value instanceof BigDecimal)
						stmt.setBigDecimal(c, (BigDecimal) value);
					else if (value instanceof Integer)
						stmt.setInt(c, (Integer) value);
					else
						stmt.setString(c, (String) value);
				} else {

					if (skey.equals("FORNITURA") || skey.equals("DATA_NASC") || skey.equals("DATA_VARIAZIONE") 
							|| skey.equals("DATA_MORTE")
							|| skey.equals("DATA_INIZIO_POSSESSO")
							|| skey.equals("DATA_FINE_POSSESSO"))
						stmt.setNull(c, java.sql.Types.DATE);
					else if (skey.equals("PK_CUAA") || skey.equals("PERC_POSS")
							|| skey.equals("SUP_CATASTO"))
						stmt.setNull(c, java.sql.Types.NUMERIC);
					else if (skey.equals("UIU_SOPPRESSE")
							|| skey.equals("UIU_COSTITUITE")
							|| skey.equals("UIU_VARIATE"))
						stmt.setNull(c, java.sql.Types.INTEGER);
					else
						stmt.setNull(c, java.sql.Types.VARCHAR);
				}
				c++;

			}
			stmt.execute();
			stmt.close();
		} catch (Exception e) {
			log.error("Exception: " + e.getMessage(), e);
			log.error(sqlIns, e);
			throw e;
		}

	}
	
	//Dati da Docfa_Dati_Generali
	private void getDichiaranteDocfa(DocfaTarReportBean rep )throws Exception{
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
	
	private String getDataRegistrazione(Connection conn, DocfaTarReportBean rep) throws Exception {
		
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
	
	
	public void getTarsuPrecedente(DocfaTarReportBean rep)throws Exception {
		
		ResultSet rs = null;
		BigDecimal minSup = null;
		BigDecimal maxSup = null;
		
		Boolean presenzaTarPrecedente = this.NON_ELABORATO;
		String foglioTar = cleanLeftPad(rep.getFoglio(),'0');
		String particellaTar = cleanLeftPad(rep.getParticella(),'0');
		String sub = rep.getSubalterno();
		
		try {

			psTarPrecedente.setString(1, foglioTar);
			psTarPrecedente.setString(2, particellaTar);
			psTarPrecedente.setString(3, sub != null ? sub: this.TAR_NULL);
			psTarPrecedente.setString(4, foglioTar);
			psTarPrecedente.setString(5, particellaTar);
			psTarPrecedente.setString(6, sub != null ? sub: this.TAR_NULL);
			psTarPrecedente.setString(7, rep.getDataDocfa());
			rs = psTarPrecedente.executeQuery();

			presenzaTarPrecedente = false;
			
			int i = 0;
			while(rs.next()){
				BigDecimal sup = null;				
				try {
					sup = rs.getBigDecimal("SUP_TOT");
				} catch (Exception e) {
					sup = rs.getString("SUP_TOT") == null ? null : new BigDecimal(rs.getString("SUP_TOT").replace(",", "."));
				}
				sup = (sup == null) ? BigDecimal.ZERO : sup; 
				
				if(i==0){ //Inizializzo con il primo valore trovato
					presenzaTarPrecedente = true;
					minSup = sup;
					maxSup = sup;
					
				}else{   //Compare
				
					minSup = sup.compareTo(minSup) < 0 ? sup : minSup;
					maxSup = sup.compareTo(maxSup) > 0 ? sup : maxSup;
				}
				
				i++;
			}

		} catch (Exception e) {
			throw e;

		} finally {
			DbUtils.close(rs);
			rep.setFlgTarAnteDocfa(presenzaTarPrecedente);
			rep.setMaxSupTarsuAnte(maxSup);
			rep.setMinSupTarsuAnte(minSup);

		}
		
	}
	
	/**
	 * Estrazione della dichiarazione TARSU (se esiste)con data INIZIO VALIDITA' compresa tra: 
	 * - data del DOCFA corrente e 
	 * - 20 Gennaio dell'anno successivo (scadenza termine presentazione dichiarazioni TARSU e data di
	 * FINE VALIDITA' - superiore al 20 gennaio dell'anno successivo (scadenza dichiarazione tarsu)
	 */
	private void getTarsuSuccessiva(DocfaTarReportBean rep) throws Exception {
		String dataScadenzaTarsu = this.getDataScadenzaTarsu(rep.getDataDocfa());
		
		ResultSet rs = null;
		String presenzaTarSuccessivo = null;
		String foglioT = cleanLeftPad(rep.getFoglio(),'0');
		String particellaT = cleanLeftPad(rep.getParticella(),'0');
		String sub = rep.getSubalterno();
		String dataDocfaSuccessivo = rep.getDataDocfaSuccessivo();
		
		BigDecimal maxSupTarsuAnte = rep.getMaxSupTarsuAnte();
		BigDecimal minSupTarsuAnte = rep.getMinSupTarsuAnte();
		
		BigDecimal minSupTarsuPost = null;
		BigDecimal maxSupTarsuPost = null;
		
		Boolean flgSupTarPostMagPre = null;
		Boolean flgSupTarPostMinPre = null;
		Boolean flgSupTarPostMagC340 = null;
		Boolean flgSupTarPostMinC340 = null;
		
		try{
		
			psTarSuccessivo.setString(1,foglioT);
			psTarSuccessivo.setString(2,particellaT);
			psTarSuccessivo.setString(3,sub != null ? sub: this.TAR_NULL);
			psTarSuccessivo.setString(4,foglioT);
			psTarSuccessivo.setString(5,particellaT);
			psTarSuccessivo.setString(6,sub != null ? sub: this.TAR_NULL);
			psTarSuccessivo.setString(7,rep.getDataDocfa());
			psTarSuccessivo.setString(8,"99991231" );
			//psTarSuccessivo.setString(5,dataDocfaSuccessivo!=null ? dataDocfaSuccessivo : "99991231" );
			
/*			psTarSuccessivo.setString(5,dataScadenzaTarsu);
			psTarSuccessivo.setString(6,dataScadenzaTarsu);
			psTarSuccessivo.setString(7, foglioT);
			psTarSuccessivo.setString(8, particellaT);
			psTarSuccessivo.setString(9, sub != null ? sub: this.TAR_NULL);
			psTarSuccessivo.setString(10, rep.getDataDocfa());*/
			
			rs = psTarSuccessivo.executeQuery();
			log.debug("DataDocfa: "+rep.getDataDocfa());

			presenzaTarSuccessivo = "0";
			int i = 0;
			while (rs.next()){
				
				//Verifico presenza rispetto a data
				java.util.Date datIni = (java.util.Date)rs.getDate("DAT_INI");
				java.util.Date datFin = (java.util.Date)rs.getDate("DAT_FIN");
				
				datFin = datFin!=null ? datFin : yyyyMMdd.parse("99991231");
				
				String tipo = getTipoDichTarsu(datIni,datFin,dataScadenzaTarsu);
				
				if(presenzaTarSuccessivo.equals("0") || presenzaTarSuccessivo.equals("2"))
					presenzaTarSuccessivo = tipo;	
				
				//Verifico superficie
				BigDecimal sup = null;
				try {
					sup = rs.getBigDecimal("SUP_TOT");
				} catch (Exception e) {
					sup = rs.getString("SUP_TOT") == null ? null : new BigDecimal(rs.getString("SUP_TOT").replace(",", "."));
				}
				sup = sup!=null ? sup : BigDecimal.ZERO;
			
				if(i==0){ //Inizializzo con il primo valore trovato
					minSupTarsuPost = sup;
					maxSupTarsuPost = sup;
					
				}else{   //Compare
					minSupTarsuPost = sup.compareTo(minSupTarsuPost) < 0 ? sup : minSupTarsuPost;
					maxSupTarsuPost = sup.compareTo(maxSupTarsuPost) > 0 ? sup : maxSupTarsuPost;
				}
				i++;
			}

			if (!presenzaTarSuccessivo.equals("0")) {

				if (!rep.getFlgTarAnteDocfa().equals("0")) {
					// Se ESISTE una superficie Tarsu successiva al docfa,
					// maggiore di tutte le altre precedenti
					if(maxSupTarsuAnte!=null && maxSupTarsuPost!=null)
						flgSupTarPostMagPre = maxSupTarsuPost.compareTo(maxSupTarsuAnte) > 0;
						

					// Se ESISTE una superficie Tarsu successiva al docfa,
					// minore di tutte le altre precedenti
					if(minSupTarsuAnte!=null && minSupTarsuPost!=null)
						flgSupTarPostMinPre = minSupTarsuPost.compareTo(minSupTarsuAnte) < 0;
				}

				// Se ESISTE una superficie Tarsu successiva al docfa, maggiore
				// della sup. calcolata via C340
				BigDecimal supC340 =  rep.getSupDocfaTarsu()!= null ? rep.getSupDocfaTarsu() : BigDecimal.ZERO;
				flgSupTarPostMagC340 = maxSupTarsuPost.compareTo(supC340) > 0;
				flgSupTarPostMinC340 = minSupTarsuPost.compareTo(supC340) < 0;

			}
				
		}catch(Exception e){
			throw e;
		}finally{
			DbUtils.close(rs);
			rep.setFlgTarPostDocfa(presenzaTarSuccessivo);
			rep.setFlgSupTarPostMagPre(flgSupTarPostMagPre);
			rep.setFlgSupTarPostMinPre(flgSupTarPostMinPre);
			rep.setFlgSupTarPostMagC340(flgSupTarPostMagC340);
			rep.setFlgSupTarPostMinC340(flgSupTarPostMinC340);
		}
	}
		
	private String	getTipoDichTarsu(java.util.Date datIni,java.util.Date datFin, String dataScadenzaTarsu)throws Exception{
		String tipo = "0";
		java.util.Date dataScadTarsu = yyyyMMdd.parse(dataScadenzaTarsu);
		// 1:Dichiarazione Tarsu successiva al Docfa, e precedente alla data di Scadenza Tarsu
		if(datIni.compareTo(dataScadTarsu)<= 0 && datFin.compareTo(dataScadTarsu)> 0)
			tipo = "1";
		else if(datIni.compareTo(dataScadTarsu) > 0)
			tipo = "2";
			
		log.debug("DataIni: "+datIni+" DatFin: "+datFin+" DatScadenzaTarsu: "+dataScadTarsu);
		
		return tipo;
	}
	
	private void getDatiCatastoAllaData(DocfaTarReportBean rep)throws Exception {

		ResultSet rsCatasto = null;

		// Valori a catasto
		Boolean presenzaACatasto = this.NON_ELABORATO;
		String categoriaCatasto = null;
		BigDecimal superficieCatasto = null;
		
		String sub = rep.getSubalterno();

		try {
			/**
			 * Estrazione informazioni a catasto per la UIU alla data
			 * antecedente al DOCFA
			 */
			//log.debug("Parametri Query: SQL_UIU_CATASTO");
			psCatasto.setString(1, this.cleanLeftPad(rep.getFoglio(), '0'));
			psCatasto.setString(2, rep.getParticella());
			psCatasto.setString(3, sub != null ? cleanLeftPad(sub,'0'): this.CAT_NULL);
			psCatasto.setString(4, rep.getDataDocfa());
			psCatasto.setString(5, rep.getDataDocfa());
			rsCatasto = psCatasto.executeQuery();

			if (rsCatasto.next()) {
				presenzaACatasto = true;
				categoriaCatasto = rsCatasto.getString("CATEGORIA");				
				try {
					superficieCatasto = rsCatasto.getBigDecimal("SUP_CAT");
				} catch (Exception e) {
					superficieCatasto = rsCatasto.getString("SUP_CAT") == null ? null : new BigDecimal(rsCatasto.getString("SUP_CAT").replace(",", "."));
				}
			} else
				presenzaACatasto = false;
		} catch (Exception e) {
			throw e;
		} finally {

			rep.setFlgUiuCatasto(presenzaACatasto);
			rep.setCategoriaCatasto(categoriaCatasto);
			rep.setSupCatasto(superficieCatasto);
			
			DbUtils.close(rsCatasto);
		}

	}

	/** Conteggio del numero di docfa presentati nell'anno per la stessa UIU */
	private void calcolaNumDocfaUiuInAnno(DocfaTarReportBean rep)throws Exception {

		String docfaInAnno = null;
		ResultSet rsDocfaInAnno = null;
		
		String sub = rep.getSubalterno();
		
		try {

			psDocfaInAnno.setString(1, rep.getFoglio());
			psDocfaInAnno.setString(2, rep.getParticella());
			psDocfaInAnno.setString(3, sub != null ? sub: this.DOCFA_NULL);
			psDocfaInAnno.setString(4, rep.getDataDocfa());

			rsDocfaInAnno = psDocfaInAnno.executeQuery();

			if (rsDocfaInAnno.next())
				docfaInAnno = rsDocfaInAnno.getString("docfa_in_anno");

		} catch (Exception e) {

		} finally {

			DbUtils.close(rsDocfaInAnno);
			rep.setDocfaInAnno(docfaInAnno);
		}
	}

	/**
	 * Conteggio del numero di docfa presentati alla stessa data per la UIU,
	 * dello stesso tipo
	 */
	private void getNumeroDocfaContemporanei(DocfaTarReportBean rep) throws Exception {

		String docfaContemp = "0";
		ResultSet rsDocfaContemp = null;
		String sub = rep.getSubalterno();
		
		try {
			
			 //log.debug("Parametri Query: SQL_DOCFA_CONTEMPORANEI");
			psDocfaContemporanei.setString(1, rep.getFoglio());
			psDocfaContemporanei.setString(2, rep.getParticella());
			psDocfaContemporanei.setString(3, sub != null ? sub: this.DOCFA_NULL);
			psDocfaContemporanei.setString(4, rep.getDataDocfa());
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

		} catch (Exception e) {
			throw e;
		} finally {

			DbUtils.close(rsDocfaContemp);
			rep.setDocfaContemporanei(docfaContemp);
		}
		
	}

	/**
	 * Estrazione della data della prima dichiarazione DOCFA, dell'anno
	 * successivo a quello del DOCFA corrente
	 */
	private void getDataDocfaSuccessivo(DocfaTarReportBean rep) throws Exception {
		String dataDocfaSuccessivo = null;
		ResultSet rs = null;
		String sub = rep.getSubalterno();

		try {
			psDocfaSuccessivo.setDate(1, rep.getFornitura());
			psDocfaSuccessivo.setString(2, rep.getProtocolloDocfa());
			psDocfaSuccessivo.setString(3, rep.getDataDocfa());
			psDocfaSuccessivo.setString(4, rep.getFoglio());
			psDocfaSuccessivo.setString(5, rep.getParticella());
			psDocfaSuccessivo.setString(6, sub != null ? sub: this.DOCFA_NULL);

			rs = psDocfaSuccessivo.executeQuery();
			if (rs.next()) {
				dataDocfaSuccessivo = rs.getString("DATA_DOCFA");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			DbUtils.close(rs);
			rep.setDataDocfaSuccessivo(dataDocfaSuccessivo);
		}

	}
	
	private void calcolaSuperficieC340(Connection conn,DocfaTarReportBean rep, String idUiu) {
		try {
			
			this.calcolaSuperficieC340_ABC(conn, rep, idUiu);
			
			if(rep.getCategoriaDocfa()!=null && rep.getCategoriaDocfa().trim().length()==3)
				this.calcolaSuperficieC340_QC1(conn, rep, idUiu);
		
		} catch (Exception e) {
			log.error(e);
		}
	}

	private void calcolaSuperficieC340_QC1(Connection conn, DocfaTarReportBean rep, String idUiu) throws Exception {
		
		BigDecimal supTarsu = BigDecimal.ZERO;
		BigDecimal supDipendenzeEscl = BigDecimal.ZERO;
		
		ResultSet rs = null;
		
		String cat = rep.getCategoriaDocfa();
		BigDecimal perc = new BigDecimal(0.8);
		String tipo = cat.trim().substring(0,1);
		
		log.debug("Tipo["+tipo+"]");
		
		try{
			
			psDocfaInParteDueH.setString(1, rep.getProtocolloDocfa());
			psDocfaInParteDueH.setDate(2, rep.getFornitura());
			
			psDocfaInParteDueH.setString(3, rep.getFoglio());
			psDocfaInParteDueH.setString(4, rep.getParticella());
			psDocfaInParteDueH.setString(5, rep.getSubalterno());
			
			psDocfaInParteDueH.setString(6, rep.getFoglio());
			psDocfaInParteDueH.setString(7, rep.getParticella());
			psDocfaInParteDueH.setString(8, rep.getSubalterno());
			
			psDocfaInParteDueH.setString(9,  rep.getFoglio());
			psDocfaInParteDueH.setString(10, rep.getParticella());
			psDocfaInParteDueH.setString(11, rep.getSubalterno());
			
			psDocfaInParteDueH.setString(12, rep.getFoglio());
			psDocfaInParteDueH.setString(13, rep.getParticella());
			psDocfaInParteDueH.setString(14, rep.getSubalterno());
			
			rs = psDocfaInParteDueH.executeQuery();
			
			if(rs.next()){
				
				log.debug("DocfaInParteDue Trovato");
				
				//Calcola superficie ai fini tarsu
				if("A".equalsIgnoreCase(tipo) || "B".equalsIgnoreCase(tipo)){
				
					BigDecimal punto1 = null;
					try {
						punto1 = rs.getBigDecimal("AB_VANI_PRINC_SUPERF_UTI");
					} catch (Exception e) {
						punto1 = rs.getString("AB_VANI_PRINC_SUPERF_UTI") == null ? null : new BigDecimal(rs.getString("AB_VANI_PRINC_SUPERF_UTI").replace(",", "."));
					}
					
					BigDecimal[] punto2 = null;
					try {
						punto2 = new BigDecimal[] {rs.getBigDecimal("AB_ACCESSORI_DIR_01_SUPERF_UTI"), 
													rs.getBigDecimal("AB_ACCESSORI_DIR_02_SUPERF_UTI")};
					} catch (Exception e) {
						punto2 = new BigDecimal[] {rs.getString("AB_ACCESSORI_DIR_01_SUPERF_UTI") == null ? null : new BigDecimal(rs.getString("AB_ACCESSORI_DIR_01_SUPERF_UTI").replace(",", ".")),
													rs.getString("AB_ACCESSORI_DIR_02_SUPERF_UTI") == null ? null : new BigDecimal(rs.getString("AB_ACCESSORI_DIR_02_SUPERF_UTI").replace(",", "."))};
					}					
					
					BigDecimal punto3 = null;
					try {
						punto3 = rs.getBigDecimal("AB_ACCESSORI_INDIR_SUPERF_LOR");
					} catch (Exception e) {
						punto3 = rs.getString("AB_ACCESSORI_INDIR_SUPERF_LOR") == null ? null : new BigDecimal(rs.getString("AB_ACCESSORI_INDIR_SUPERF_LOR").replace(",", "."));
					}
					
					BigDecimal[] punto4 = null;
					try {
						punto4 = new BigDecimal[] {rs.getBigDecimal("AB_DIP_USO_ESCL_01_SUPERF_LOR"), 
													rs.getBigDecimal("AB_DIP_USO_ESCL_02_SUPERF_LOR")};
					} catch (Exception e) {
						punto4 = new BigDecimal[] {rs.getString("AB_DIP_USO_ESCL_01_SUPERF_LOR") == null ? null : new BigDecimal(rs.getString("AB_DIP_USO_ESCL_01_SUPERF_LOR").replace(",", ".")),
													rs.getString("AB_DIP_USO_ESCL_02_SUPERF_LOR") == null ? null : new BigDecimal(rs.getString("AB_DIP_USO_ESCL_02_SUPERF_LOR").replace(",", "."))};
					}
					
					punto1 = punto1!=null ? punto1 : BigDecimal.ZERO;
					punto2[0] = punto2[0]!=null ? punto2[0] : BigDecimal.ZERO;
					punto2[1] = punto2[1]!=null ? punto2[1] : BigDecimal.ZERO;
					
					punto3 = punto3!=null ? punto3 : BigDecimal.ZERO;
					
					punto4[0] = punto4[0]!=null ? punto4[0] : BigDecimal.ZERO;
					punto4[1] = punto4[1]!=null ? punto4[1] : BigDecimal.ZERO;
					
					BigDecimal punto2Sum = punto2[0].add(punto2[1]);
					supTarsu = punto1.add(punto2Sum);
					
					//Per accessori indiretti (cantine, soffitte, lavanderie...) la superficie lorda viene abbattuta del 20%
					BigDecimal sup3Abb = punto3.multiply(perc);
					supTarsu = supTarsu.add(sup3Abb);
					
					supDipendenzeEscl = punto4[0].add(punto4[1]);
					
					log.info("SupTarsu: " + supTarsu);
					log.info("supDipEscl: " + supDipendenzeEscl);
					
					
				}else if("C01".equalsIgnoreCase(cat)||"C02".equalsIgnoreCase(cat)||"C03".equalsIgnoreCase(cat)){
					
					BigDecimal[] punto1 = null;
					try {
						punto1 = new BigDecimal[] {rs.getBigDecimal("C_LOC_PRINC_SUP_UTI_01"), 
													rs.getBigDecimal("C_LOC_PRINC_SUP_UTI_02")};
					} catch (Exception e) {
						punto1 = new BigDecimal[] {rs.getString("C_LOC_PRINC_SUP_UTI_01") == null ? null : new BigDecimal(rs.getString("C_LOC_PRINC_SUP_UTI_01").replace(",", ".")),
													rs.getString("C_LOC_PRINC_SUP_UTI_02") == null ? null : new BigDecimal(rs.getString("C_LOC_PRINC_SUP_UTI_02").replace(",", "."))};
					}
					
					BigDecimal[] punto2 = null;
					try {
						punto2 = new BigDecimal[] {rs.getBigDecimal("C_LOC_ACC_DIR_SUP_UTI_01"), 
													rs.getBigDecimal("C_LOC_ACC_DIR_SUP_UTI_02")};
					} catch (Exception e) {
						punto2 = new BigDecimal[] {rs.getString("C_LOC_ACC_DIR_SUP_UTI_01") == null ? null : new BigDecimal(rs.getString("C_LOC_ACC_DIR_SUP_UTI_01").replace(",", ".")),
													rs.getString("C_LOC_ACC_DIR_SUP_UTI_02") == null ? null : new BigDecimal(rs.getString("C_LOC_ACC_DIR_SUP_UTI_02").replace(",", "."))};
					}
					
					BigDecimal[] punto3 = null;
					try {
						punto3 = new BigDecimal[] {rs.getBigDecimal("C_LOC_ACC_INDIR_SUP_UTI_01"), 
													rs.getBigDecimal("C_LOC_ACC_INDIR_SUP_UTI_02")};
					} catch (Exception e) {
						punto3 = new BigDecimal[] {rs.getString("C_LOC_ACC_INDIR_SUP_UTI_01") == null ? null : new BigDecimal(rs.getString("C_LOC_ACC_INDIR_SUP_UTI_01").replace(",", ".")),
													rs.getString("C_LOC_ACC_INDIR_SUP_UTI_02") == null ? null : new BigDecimal(rs.getString("C_LOC_ACC_INDIR_SUP_UTI_02").replace(",", "."))};
					}
					
					BigDecimal[] punto4 = null;
					try {
						punto4 = new BigDecimal[] {rs.getBigDecimal("C_DIPEND_USO_ESCL_SUP_LOR_01"), 
													rs.getBigDecimal("C_DIPEND_USO_ESCL_SUP_LOR_02")};
					} catch (Exception e) {
						punto4 = new BigDecimal[] {rs.getString("C_DIPEND_USO_ESCL_SUP_LOR_01") == null ? null : new BigDecimal(rs.getString("C_DIPEND_USO_ESCL_SUP_LOR_01").replace(",", ".")),
													rs.getString("C_DIPEND_USO_ESCL_SUP_LOR_02") == null ? null : new BigDecimal(rs.getString("C_DIPEND_USO_ESCL_SUP_LOR_02").replace(",", "."))};
					}
					
					punto1[0] = punto1[0]!=null ? punto1[0] : BigDecimal.ZERO;
					punto1[1] = punto1[1]!=null ? punto1[1] : BigDecimal.ZERO;
					
					punto2[0] = punto2[0]!=null ? punto2[0] : BigDecimal.ZERO;
					punto2[1] = punto2[1]!=null ? punto2[1] : BigDecimal.ZERO;
					
					punto3[0] = punto3[0]!=null ? punto3[0] : BigDecimal.ZERO;
					punto3[1] = punto3[1]!=null ? punto3[1] : BigDecimal.ZERO;
					
					punto4[0] = punto4[0]!=null ? punto4[0] : BigDecimal.ZERO;
					punto4[1] = punto4[1]!=null ? punto4[1] : BigDecimal.ZERO;
					
					BigDecimal punto1Sum = punto1[0].add(punto1[1]);
					BigDecimal punto2Sum = punto2[0].add(punto2[1]);
					BigDecimal punto3Sum = punto3[0].add(punto3[1]);
					
					supTarsu = punto1Sum.add(punto2Sum).add(punto3Sum);
					
					supDipendenzeEscl = punto4[0].add(punto4[1]);
					
					log.info("SupTarsu: " + supTarsu);
					log.info("supDipEscl: " + supDipendenzeEscl);
					
				}
			}
			
		}catch(Exception e){
			throw e;
			
		} finally {
			DbUtils.close(rs);
		}
		
		rep.setSupDocfaTarsu(supTarsu);
		rep.setSupDipendenzeEscl(supDipendenzeEscl);
	}

	private void calcolaSuperficieC340_ABC(Connection conn, DocfaTarReportBean rep, String idUiu) throws Exception {

		String ambiente = null;
		BigDecimal sup = null;
		BigDecimal supTarsu = new BigDecimal(0);
		BigDecimal perc = new BigDecimal(0.8);
		ResultSet rs = null;

		try {

			psSuperficiPerVano.setDate(1, rep.getFornitura());
			psSuperficiPerVano.setString(2, rep.getProtocolloDocfa());
			psSuperficiPerVano.setString(3, rep.getDataDocfa());
			psSuperficiPerVano.setString(4, idUiu);

			rs = psSuperficiPerVano.executeQuery();

			while (rs.next()) {

				ambiente = rs.getString("ambiente");
				sup = new BigDecimal(rs.getString("superficie"));

				ambiente = ambiente != null ? ambiente.trim() : null;
				if ("A".equalsIgnoreCase(ambiente)|| "B".equalsIgnoreCase(ambiente)|| "C".equalsIgnoreCase(ambiente))
					supTarsu = supTarsu.add(sup);
				// log.debug(ambiente+": "+sup +"("+rs.getString("superficie")+")");
			}
			supTarsu = supTarsu.multiply(perc);
			supTarsu = supTarsu.setScale(2, BigDecimal.ROUND_DOWN);

		} catch (Exception e) {
			throw e;
		} finally {
			DbUtils.close(rs);
		}

		rep.setSupC340Abc(supTarsu);
	}

	private void verificaIndirizzoDocfa(DocfaTarReportBean rep , String idDocfaIndice) throws Exception {
		Boolean flgIndirizzoACatasto = false;

		log.debug("Verifica della presenza dell'indirizzo dichiarato nel DOCFA, all'interno della TOPONOMASTICA");

		// Seleziono i Docfa da elaborare
		ResultSet rs = null;
		ResultSet rsIndice = null;
		
		String sub = rep.getSubalterno();

		try {

			 //log.debug("Parametri Query: SQL_CIVICI_UIU_CATASTO");
		
			psCiviciCatasto.setString(1, rep.getCodEnte());
			psCiviciCatasto.setString(2, this.cleanLeftPad(rep.getFoglio(), '0'));
			psCiviciCatasto.setString(3, rep.getParticella());
			psCiviciCatasto.setString(4, sub != null ? cleanLeftPad(sub, '0') : this.CAT_NULL);
			rs = psCiviciCatasto.executeQuery();

			while (rs.next() && !flgIndirizzoACatasto) {
				String indirCatImmobile = rs.getString("INDIR_CAT_IMMOBILE");

				// Verifico, per ogni civico trovato associato alla UIU che
				// corrisponda a quello dichiarato nel DOCFA
				
				//log.debug("Parametri Query: SQL_INDICE_CIVICO_CatDocfa");
				 
				psCiviciIndiceCD.setString(1, idDocfaIndice);
				psCiviciIndiceCD.setString(2, indirCatImmobile);
				rsIndice = psCiviciIndiceCD.executeQuery();

				// se non ho mai risultati , per nessun INDIR_CAT_IMMOBILE --->
				// indirizzo a catasto <> indirizzo docfa
				if (rsIndice.next()) {
					flgIndirizzoACatasto = true;
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DbUtils.close(rs);
			DbUtils.close(rsIndice);
			rep.setFlgIndirizzoInToponomastica(flgIndirizzoACatasto);
		}

		
	}

	private ArrayList<DocfaTarReportSoggBean> getTitolariUiuAnnoDocfa(DocfaTarReportBean rep, String idDocfaIndice)throws Exception {
		
		ArrayList<DocfaTarReportSoggBean> titolari = new ArrayList<DocfaTarReportSoggBean>();

		ResultSet rs = null;
		ResultSet rsTitIndiceCA = null;
		ResultSet rsResidenzaTitolare = null;
		ResultSet rsRes = null;
		
		String subDcf = rep.getSubalterno();
		
		String foglioCat = cleanLeftPad(rep.getFoglio(), '0');
		String subCat = subDcf!=null ? cleanLeftPad(subDcf, '0') : this.CAT_NULL;
		String dataDocfa = rep.getDataDocfa();

		try {
			// Seleziono i Docfa da elaborare
			// log.debug("Parametri Query: SQL_TITOLARI_UIU_ANNO_DOCFA");

			psTitUiuAnnoDocfa.setString(1, rep.getCodEnte());
			psTitUiuAnnoDocfa.setString(2, foglioCat);
			psTitUiuAnnoDocfa.setString(3, rep.getParticella());
			psTitUiuAnnoDocfa.setString(4, subCat);
			psTitUiuAnnoDocfa.setString(5, dataDocfa);
			psTitUiuAnnoDocfa.setString(6, dataDocfa);
			rs = psTitUiuAnnoDocfa.executeQuery();

			String tab = null;
			java.util.Date dateDocfa = (dataDocfa != null) ? yyyyMMdd.parse(dataDocfa) : null;
			java.util.Date date3112 = yyyyMMdd.parse(dataDocfa.substring(0, 4)+ "1231");
			//log.debug("Data 31-12 (Date): " + date3112);
			//log.debug("Data Docfa (Date): " + dateDocfa);

			while (rs.next()) {

				DocfaTarReportSoggBean titolare = new DocfaTarReportSoggBean();
				// Serve per cercare il titolare all'interno dell'indice di correlazione
				titolare.setFkIdRep(rep.getIdRep());
				String pkId = rs.getString("pkid");
				titolare.setCatPkid(pkId); 
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

				//this.verificaPresenzaTitolareInTarsu(titolare,rep);
				
				 Object[] flagUiu = verificaSoggettoInTarPerUiu(titolare.getCatPkid(),rep,this.codIndiceCatastoSoggetti);
				 titolare.setFlgTitTarUiuAnteDcf((Boolean)flagUiu[0]);
				 titolare.setFlgTitTarUiuPostDcf((String)flagUiu[1]);
				 
				 Object[] flagCiv = verificaSoggettoInTarPerCiv(titolare.getCatPkid(),rep,idDocfaIndice,this.codIndiceCatastoSoggetti);
				 titolare.setFlgTitTarCivAnteDcf((Boolean)flagCiv[0]);
				 titolare.setFlgTitTarCivPostDcf((String)flagCiv[1]);
				 
				 Object[] flagUnd = verificaSoggettoInTarPerUiuIndefinita(titolare.getCatPkid(),rep,this.codIndiceCatastoSoggetti);
				 titolare.setFlgTitTarAnteDcf((Boolean)flagUnd[0]);
				 titolare.setFlgTitTarPostDcf((String)flagUnd[1]);
				 
				Boolean titolareAllaData = false;
				Boolean titolareAl_3112 = false;
				if(dataInizioPossesso!=null && dataFinePossesso!=null){
					titolareAllaData = dataInizioPossesso.compareTo(dateDocfa)<=0 && dataFinePossesso.compareTo(dateDocfa)>=0;
					titolareAl_3112 = dataInizioPossesso.compareTo(date3112)<=0 && dataFinePossesso.compareTo(date3112)>=0;
				}
				
				Boolean residenteIndDocfa = false;
				Boolean residenteIndCatasto = false;
				Boolean trovati = false;
				titolare.setFlgAnomalia(false); 
				
				if (titolare.getFlgPersFisica().equalsIgnoreCase("P")) {

					 this.verificaFamiliariDataDocfa(rep, titolare, titolare.getCatPkid(), idDocfaIndice);
						

					// Ricerca del titolare della UIU all'interno dell'Anagrafe,mediante l'indice
					
					//log.debug("Parametri Query: SQL_INDICE_TITOLARE_CatAnagrafe");
					
					 
					psTitIndiceCA.setString(1, pkId); // CONS_SOGG_TAB.PKID
					rsTitIndiceCA = psTitIndiceCA.executeQuery();
					int iSogg = 0;
					while (rsTitIndiceCA.next()) {

						String idExtPersona = rsTitIndiceCA.getString("ID_EXT_PERSONA");
						String idAnaPersona = rsTitIndiceCA.getString("ID_ANA_PERS");
						
						//Verifico presenza familiari in tarsu
						/* this.verificaFamiliariDataDocfa(rep, titolare, idExtPersona, idDocfaIndice);
						 iSogg++;
						 if(iSogg >1)
							 titolare.addAnnotazioni("più corrispondenze in anagrafe");
*/
						/* Selezione dell'indirizzo anagrafico associato al soggetto
						INDIRIZZO IN ANAGRAFE ALLA DATA DEL DOCFA (LISTA_INDIRIZZI_ANAGRAFE_DATA_DOCFA)
						*/
						//log.debug("Parametri Query: SQL_RESIDENZA_TITOLARE");
						
						psResidenzaTitolare.setString(1, idExtPersona);
						psResidenzaTitolare.setString(2, dataDocfa.substring(0, 4)+ "1231");
						rsResidenzaTitolare = psResidenzaTitolare.executeQuery();

						while (rsResidenzaTitolare.next() && !trovati) {

							String anaCivicoResidenza = rsResidenzaTitolare.getString("ANA_CIVICO_RESIDENZA");

							if (!residenteIndCatasto) {
								// PER OGNI INDIRIZZO DI RESIDENZA DEL TITOLARE
								// TROVATO VERIFICO SE = A INDIRIZZO A CATASTO
								// DELL'IMMOBILE (UNA VOLTA TRUE NON ESEGUO PIU'
								// IL CONTROLLO)
								
								//log.debug("Parametri Query: SQL_INDICE_RESIDENZA_CIVICO_CATASTO"
							
								psResidenzaCivicoCatasto.setString(1,anaCivicoResidenza);
								psResidenzaCivicoCatasto.setString(2, rep.getCodEnte());
								psResidenzaCivicoCatasto.setString(3, foglioCat);
								psResidenzaCivicoCatasto.setString(4, rep.getParticella());
								psResidenzaCivicoCatasto.setString(5, subCat);

								rsRes = psResidenzaCivicoCatasto.executeQuery();
								if (rsRes.next()) 
									residenteIndCatasto = true;
							}

							if (!residenteIndDocfa) {
								// PER OGNI NDIRIZZO DI RESIDENZA DEL TITOLARE
								// TROVATO VERIFICO SE = A INDIRIZZO DEL DOCFA
								// (UNA VOLTA TRUE NON ESEGUO PIU' IL CONTROLLO)
								
								//log.debug("Parametri Query: SQL_INDICE_RESIDENZA_CIVICO_DOCFA); 
								 
								psResidenzaCivicoDocfa.setString(1,anaCivicoResidenza);
								psResidenzaCivicoDocfa.setString(2,idDocfaIndice);

								rsRes = psResidenzaCivicoDocfa.executeQuery();
								if (rsRes.next()) 
									residenteIndDocfa = true;
							}
							trovati = residenteIndDocfa && residenteIndCatasto;
						}
					}

					if (idDocfaIndice.split(this.PIPE)[2].equalsIgnoreCase("")) {
						addAnomalia(titolare, DocfaAnomalie.getCodNoDocfaUiu());
						//annotazioni = "Corrispondenza DOCFA_UIU mancante (NR_PROG == NULL)";
					}
					
					if(this.SOPPRESSA.equalsIgnoreCase(rep.getTipoOperDocfa())){
						titolare.addAnnotazioni("Docfa Soppressione");
					}

				} else {
					// anomalie = true;
					titolare.addAnnotazioni("Soggetto Giuridico");
				}

				titolare.setFlgTitolareDataDocfa(titolareAllaData);
				titolare.setFlgTitolare3112(titolareAl_3112);

				titolare.setFlgResidIndCat3112(residenteIndCatasto);
				titolare.setFlgResidIndDcf3112(residenteIndDocfa);
				
				
				//titolare.putAll(presenzaTitTarsu);

				// titolare.put("TIPO_TITOLO",tipoTitolo);

				titolari.add(titolare);
			}
		} catch (Exception e) {
			throw e;
		} finally {

			DbUtils.close(rs);
			DbUtils.close(rsTitIndiceCA);
			DbUtils.close(rsResidenzaTitolare);
			DbUtils.close(rsRes);
		}

		return titolari;
	}
		
		

	private Object[] verificaSoggettoInTarPerCiv(String catPkIdSoggetto, DocfaTarReportBean rep, String idDocfaIndice, String[] codIndiceOriSogg) throws Exception{
		
		Object[] flag = new Object[2];
		ResultSet rsTarPrecedente = null;
		ResultSet rsTarSuccessivo = null;
		
		Boolean presenzaTarPrecedente = this.NON_ELABORATO;
		String presenzaTarSuccessivo = null;
		
		String dataDocfaSuccessivo = rep.getDataDocfaSuccessivo();
		String dataScadenzaTarsu = this.getDataScadenzaTarsu(rep.getDataDocfa());
		try{
			psTitTarPrecedenteCiv.setString(1,codIndiceOriSogg[0]);
			psTitTarPrecedenteCiv.setString(2,codIndiceOriSogg[1]);
			psTitTarPrecedenteCiv.setString(3,catPkIdSoggetto);
			psTitTarPrecedenteCiv.setString(4,rep.getDataDocfa());
			psTitTarPrecedenteCiv.setString(5,idDocfaIndice);
			
			rsTarPrecedente = psTitTarPrecedenteCiv.executeQuery();
				
			presenzaTarPrecedente = false;
			if(rsTarPrecedente.next())
				presenzaTarPrecedente = true;
			
			psTitTarSuccessivoCiv.setString(1,codIndiceOriSogg[0]);
			psTitTarSuccessivoCiv.setString(2,codIndiceOriSogg[1]);
			psTitTarSuccessivoCiv.setString(3,catPkIdSoggetto);
			psTitTarSuccessivoCiv.setString(4,rep.getDataDocfa());
			psTitTarSuccessivoCiv.setString(5,"99991231");
			//psTitTarSuccessivoCiv.setString(5,dataDocfaSuccessivo!= null ? dataDocfaSuccessivo : "99991231");
			psTitTarSuccessivoCiv.setString(6,idDocfaIndice);
			
			rsTarSuccessivo = psTitTarSuccessivoCiv.executeQuery();
				
			presenzaTarSuccessivo = presenzaTarsuFamiliarePost(rsTarSuccessivo,dataScadenzaTarsu);
		
		}catch(Exception e){
			throw e;
			
		}finally{
			DbUtils.close(rsTarPrecedente);
			DbUtils.close(rsTarSuccessivo);
			
			flag[0] = presenzaTarPrecedente;
			flag[1] = presenzaTarSuccessivo;
			
		}
		return flag;
	}
	
	private String presenzaTarsuFamiliarePost(ResultSet rsTarSuccessivo, String dataScadenzaTarsu) throws Exception{
		
		String presenzaTarSuccessivo = "0";
		ArrayList temp = new ArrayList();
		while (rsTarSuccessivo.next() && !temp.contains("1")){
			java.util.Date datIni = (java.util.Date)rsTarSuccessivo.getDate("DAT_INI");
			java.util.Date datFin = (java.util.Date)rsTarSuccessivo.getDate("DAT_FIN");
			datFin = datFin!=null ? datFin : yyyyMMdd.parse("99991231");
			
			String tipo = getTipoDichTarsu(datIni,datFin,dataScadenzaTarsu);
			temp.add(tipo);
		}
		
		if(temp.contains("1"))
			presenzaTarSuccessivo = "1";
		else if(!temp.contains("1") && temp.contains("2"))
			presenzaTarSuccessivo = "2";
		
		return presenzaTarSuccessivo;
	}
	
	private Object[] verificaSoggettoInTarPerUiu(String catPkIdSoggetto, DocfaTarReportBean rep,String[] codIndiceOriSogg) throws Exception{
		
		Object[] flag = new Object[2];
		ResultSet rsTarPrecedente = null;
		ResultSet rsTarSuccessivo = null;
		
		Boolean presenzaTarPrecedente = this.NON_ELABORATO;
		String presenzaTarSuccessivo = null;
		
		String foglioTar = this.cleanLeftPad(rep.getFoglio(), '0');
		String particellaTar = this.cleanLeftPad(rep.getParticella(), '0');
		String subalternoTar = rep.getSubalterno()!=null ? rep.getSubalterno() : this.TAR_NULL;
		String dataDocfaSuccessivo = rep.getDataDocfaSuccessivo();
		String dataScadenzaTarsu = this.getDataScadenzaTarsu(rep.getDataDocfa());
		
		try{
			psTitTarPrecedenteUiu.setString(1,codIndiceOriSogg[0]);
			psTitTarPrecedenteUiu.setString(2,codIndiceOriSogg[1]);
			psTitTarPrecedenteUiu.setString(3,catPkIdSoggetto);
			psTitTarPrecedenteUiu.setString(4,rep.getDataDocfa());
			psTitTarPrecedenteUiu.setString(5,foglioTar);
			psTitTarPrecedenteUiu.setString(6,particellaTar);
			psTitTarPrecedenteUiu.setString(7,subalternoTar);
			rsTarPrecedente = psTitTarPrecedenteUiu.executeQuery();
				
			presenzaTarPrecedente = false;
			if(rsTarPrecedente.next())
				presenzaTarPrecedente = true;
			
			if(!this.SOPPRESSA.equalsIgnoreCase(rep.getTipoOperDocfa())){
			
				psTitTarSuccessivoUiu.setString(1,codIndiceOriSogg[0] );
				psTitTarSuccessivoUiu.setString(2,codIndiceOriSogg[1] );
				psTitTarSuccessivoUiu.setString(3,catPkIdSoggetto );
				psTitTarSuccessivoUiu.setString(4,rep.getDataDocfa());
				psTitTarSuccessivoUiu.setString(5,"99991231" );
				//psTitTarSuccessivoUiu.setString(5,dataDocfaSuccessivo!=null ? dataDocfaSuccessivo : "99991231" );
				psTitTarSuccessivoUiu.setString(6,foglioTar);
				psTitTarSuccessivoUiu.setString(7,particellaTar);
				psTitTarSuccessivoUiu.setString(8,subalternoTar);
	
				rsTarSuccessivo = psTitTarSuccessivoUiu.executeQuery();
					
				presenzaTarSuccessivo = presenzaTarsuFamiliarePost(rsTarSuccessivo,dataScadenzaTarsu);
			}
				
		}catch(Exception e){
			throw e;
			
		}finally{
			DbUtils.close(rsTarPrecedente);
			DbUtils.close(rsTarSuccessivo);
			
			flag[0] = presenzaTarPrecedente;
			flag[1] = presenzaTarSuccessivo;
			
		}
		return flag;
	}
	
	private Object[] verificaSoggettoInTarPerUiuIndefinita(String pkIdSoggetto, DocfaTarReportBean rep,String[] codIndiceOriSogg) throws Exception{
		
		Object[] flag = new Object[2];
		ResultSet rsTarPrecedente = null;
		ResultSet rsTarSuccessivo = null;
		
		Boolean presenzaTarPrecedente = this.NON_ELABORATO;
		String presenzaTarSuccessivo = null;
		
		String foglioTar = this.cleanLeftPad(rep.getFoglio(), '0');
		String particellaTar = this.cleanLeftPad(rep.getParticella(), '0');
		String subalternoTar = rep.getSubalterno()!=null ? rep.getSubalterno() : this.TAR_NULL;
		String dataDocfaSuccessivo = rep.getDataDocfaSuccessivo();
		String dataScadenzaTarsu = this.getDataScadenzaTarsu(rep.getDataDocfa());
		
		try{
		
			psTitTarPrecedente.setString(1,foglioTar);
			psTitTarPrecedente.setString(2,particellaTar);
			psTitTarPrecedente.setString(3,subalternoTar);
			psTitTarPrecedente.setString(4,foglioTar);
			psTitTarPrecedente.setString(5,particellaTar);
			psTitTarPrecedente.setString(6,subalternoTar);
			psTitTarPrecedente.setString(7,codIndiceOriSogg[0]);
			psTitTarPrecedente.setString(8,codIndiceOriSogg[1]);
			psTitTarPrecedente.setString(9,pkIdSoggetto);
			psTitTarPrecedente.setString(10,rep.getDataDocfa());
			rsTarPrecedente = psTitTarPrecedente.executeQuery();
				
			presenzaTarPrecedente = false;
			if(rsTarPrecedente.next())
				presenzaTarPrecedente = true;
			
			if(!this.SOPPRESSA.equalsIgnoreCase(rep.getTipoOperDocfa())){
				psTitTarSuccessivo.setString(1,foglioTar );
				psTitTarSuccessivo.setString(2,particellaTar );
				psTitTarSuccessivo.setString(3,subalternoTar );
				psTitTarSuccessivo.setString(4,foglioTar );
				psTitTarSuccessivo.setString(5,particellaTar );
				psTitTarSuccessivo.setString(6,subalternoTar );
				psTitTarSuccessivo.setString(7,codIndiceOriSogg[0]);
				psTitTarSuccessivo.setString(8,codIndiceOriSogg[1]);
				psTitTarSuccessivo.setString(9,pkIdSoggetto);
				psTitTarSuccessivo.setString(10,rep.getDataDocfa());
				psTitTarSuccessivo.setString(11,"99991231");
				//psTitTarSuccessivo.setString(11,dataDocfaSuccessivo!= null ? dataDocfaSuccessivo : "99991231");
				
				rsTarSuccessivo = psTitTarSuccessivo.executeQuery();
					
				presenzaTarSuccessivo = presenzaTarsuFamiliarePost(rsTarSuccessivo,dataScadenzaTarsu);
			}
		
		}catch(Exception e){
			throw e;
			
		}finally{
			DbUtils.close(rsTarPrecedente);
			DbUtils.close(rsTarSuccessivo);
			
			flag[0] = presenzaTarPrecedente;
			flag[1] = presenzaTarSuccessivo;
		}
		return flag;
	}
	
	
	protected void verificaFamiliariDataDocfa(DocfaTarReportBean rep, DocfaTarReportSoggBean titolare, String idExtPersona, String idDocfaIndice)throws Exception{
		ResultSet rs = null;
		List<String> idsExt = new ArrayList<String>();
		Boolean flgFamTarUiuAnteDcf = null;
		Boolean flgFamTarCivAnteDcf = null;
		Boolean flgFamTarAnteDcf = null;
		
		String flgFamTarCivPostDcf = null;
		String flgFamTarUiuPostDcf = null;
		String flgFamTarPostDcf = null;
		
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
			
			int i = 0;
			while(rs.next()){
				String idExt = rs.getString("ID_EXT");
				this.addCodice(idsExt, idExt);
				i ++;
				
				if(i == 1){
					flgFamTarUiuAnteDcf = false;
					flgFamTarCivAnteDcf = false;
					flgFamTarAnteDcf = false;
				}
				
				String demoId = rs.getString("ID");
				 Object[] flagUiu = verificaSoggettoInTarPerUiu(demoId,rep,this.codIndiceDemografia);
				 Object[] flagCiv = verificaSoggettoInTarPerCiv(demoId,rep,idDocfaIndice,this.codIndiceDemografia);
				 Object[] flagUnd = verificaSoggettoInTarPerUiuIndefinita(demoId,rep,this.codIndiceDemografia);
				
				 flgFamTarUiuAnteDcf = flgFamTarUiuAnteDcf ||(Boolean)flagUiu[0];
				 flgFamTarCivAnteDcf = flgFamTarCivAnteDcf ||(Boolean)flagCiv[0];
				 flgFamTarAnteDcf =    flgFamTarAnteDcf ||(Boolean)flagUnd[0];
				
				 if(!this.SOPPRESSA.equalsIgnoreCase(rep.getTipoOperDocfa())){
					 
					/* 
					 * if(numFamiliari == 1){
						flgFamTarCivPostDcf = "0";
						flgFamTarUiuPostDcf = "0";
						flgFamTarPostDcf = "0";
					}
					 */
					flgFamTarUiuPostDcf = getCurrentValuePost(flgFamTarUiuPostDcf,(String)flagUiu[1]);
					flgFamTarCivPostDcf = getCurrentValuePost(flgFamTarCivPostDcf,(String)flagCiv[1]);
					flgFamTarPostDcf = getCurrentValuePost(flgFamTarPostDcf,(String)flagUnd[1]);
				 }
			}
			
			numFamiliari = idsExt.size();
			
		}catch(Exception e){
			throw e;
		}finally{
			DbUtils.close(rs);
			titolare.setNumFamiliari(new BigDecimal(numFamiliari));
			
			titolare.setFlgFamTarUiuAnteDcf(flgFamTarUiuAnteDcf);
			titolare.setFlgFamTarUiuPostDcf(flgFamTarUiuPostDcf);
			titolare.setFlgFamTarCivAnteDcf(flgFamTarCivAnteDcf);
			titolare.setFlgFamTarCivPostDcf(flgFamTarCivPostDcf);
			titolare.setFlgFamTarAnteDcf(flgFamTarAnteDcf);
			titolare.setFlgFamTarPostDcf(flgFamTarPostDcf);
			
		}
	}
	
	
	private String getCurrentValuePost(String vecchio, String temp){
		String nuovo = null;
				
		if("1".equalsIgnoreCase(temp)){
			nuovo = temp;
		}else if("2".equalsIgnoreCase(temp) && !"1".equalsIgnoreCase(vecchio)){
			nuovo = temp;	
		}else if("0".equalsIgnoreCase(temp) && vecchio==null)
			nuovo = temp;
		else nuovo = vecchio;
		
		return nuovo;
	}

/*	private void verificaPresenzaTitolareInTarsu(DocfaTarReportSoggBean tit, DocfaTarReportBean rep ) throws Exception {
		
		ResultSet rsTarPrecedente = null;
		ResultSet rsTarSuccessivo = null;

		Boolean presenzaTarPrecedente = this.NON_ELABORATO;
		String presenzaTarSuccessivo = null;
		
		String foglioT = cleanLeftPad(rep.getFoglio(), '0');
		String particellaT = cleanLeftPad(rep.getParticella(), '0');
		String sub = rep.getSubalterno();
		String dataDocfa = rep.getDataDocfa();
		String dataDocfaSuccessivo = rep.getDataDocfaSuccessivo();
		
		String dataScadenzaTarsu = this.getDataScadenzaTarsu(dataDocfa);

		try {

			psTitTarPrecedente.setString(1, tit.getCatPkid());
			psTitTarPrecedente.setString(2, dataDocfa);
			psTitTarPrecedente.setString(3, foglioT);
			psTitTarPrecedente.setString(4, particellaT);
			psTitTarPrecedente.setString(5, sub != null ? sub: this.TAR_NULL);
			rsTarPrecedente = psTitTarPrecedente.executeQuery();

			presenzaTarPrecedente = false;
			if (rsTarPrecedente.next())
				presenzaTarPrecedente = true;

			psTitTarSuccessivo20.setString(1, tit.getCatPkid());
			psTitTarSuccessivo20.setString(2, dataDocfa);
			psTitTarSuccessivo20.setString(3, dataScadenzaTarsu);
			psTitTarSuccessivo20.setString(4, dataScadenzaTarsu);
			psTitTarSuccessivo20.setString(5, foglioT);
			psTitTarSuccessivo20.setString(6, particellaT);
			psTitTarSuccessivo20.setString(7, sub != null ? sub: this.TAR_NULL);

			rsTarSuccessivo = psTitTarSuccessivo20.executeQuery();

			if (rsTarSuccessivo.next())
				presenzaTarSuccessivo = "1";
			else {

				psTitTarSuccessivoDS.setString(1, tit.getCatPkid());
				psTitTarSuccessivoDS.setString(2, dataDocfa);
				psTitTarSuccessivoDS.setString(3, dataDocfaSuccessivo != null ? dataDocfaSuccessivo: "99991231");
				psTitTarSuccessivoDS.setString(4, foglioT);
				psTitTarSuccessivoDS.setString(5, particellaT);
				psTitTarSuccessivoDS.setString(6, sub != null ? sub : this.TAR_NULL);

				rsTarSuccessivo = psTitTarSuccessivoDS.executeQuery();
				if (rsTarSuccessivo.next())
					presenzaTarSuccessivo = "2";
				else
					presenzaTarSuccessivo = "0";

			}

		} catch (Exception e) {
			throw e;

		} finally {
			DbUtils.close(rsTarPrecedente);
			DbUtils.close(rsTarSuccessivo);
			
			tit.setFlgTarAnteDocfa(presenzaTarPrecedente);
			tit.setFlgTarPostDocfa(presenzaTarSuccessivo);
		}
	}*/

	
	/**
	 * @param dati
	 * @return il nome della tabella creata
	 * @throws Exception
	 * @throws Exception
	 */
	private Boolean creaTabella(Connection conn, String tabella, String sqlCreate, String[] indici) throws Exception {
		java.sql.Statement stmt = null;
		// Connection conn =null;
		Boolean creata = false;
		log.debug("Creazione della tabella " + tabella);
		try {
			// conn = RulesConnection.getConnection("DIOGENE_"+belfiore);

			// drop prima di creare
			String dropSql = "DROP TABLE " + tabella;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate(dropSql);
				stmt.close();
			} catch (Exception e) {}
			
			stmt = conn.createStatement();
			log.debug("SQL_CREATE: " + sqlCreate);
			stmt.executeUpdate(sqlCreate);
			if(indici!=null){
				for(int i=0; i<indici.length; i++)
				stmt.executeUpdate(indici[i]);
			}
			stmt.close();
			creata = true;

		} catch (SQLException ex) {
			log.error("SQLException: " + ex.getMessage(), ex);
			throw ex;
		} catch (Exception e) {
			log.error("Exception: " + e.getMessage(), e);
			throw e;
		}finally{
			stmt.close();
		}

		return creata;

	}

	private Boolean checkCampoValido(String s) {

		Boolean valida = true;

		valida = (s != null) && (s.trim().length() > 0);

		return valida;

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

	private Boolean checkCategoriaValida(String cat) {
		Boolean valida = true;
		valida = this.checkCampoValido(cat) && !"00".equalsIgnoreCase(cat.trim());
		return valida;
	}

	private Boolean checkSuperficieValida(String superficie) {
		Boolean valida = true;
		valida = this.checkCampoValido(superficie) && !"0".equalsIgnoreCase(superficie.trim());
		return valida;
	}

	private Boolean checkIsVariato(Boolean varPrecedente, String s1, String s2) {

		Boolean variato = varPrecedente;
		if (s1 != null && s2 != null)
			variato = variato || !(s1.trim().equalsIgnoreCase(s2.trim()));
		else if (s1 == null && s2 == null)
			variato = variato || false;
		else
			variato = true;

		return variato;
	}

	private String cleanLeftPad(String s, char pad) {
		if (s != null) {
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

	private void controllaConsistenza(DocfaTarReportBean rep) {

		Double supMediaMin = null;
		Double consistSuSuperfCatastale = null;
		Double supMediaMax = null;
		Boolean anomaliaCons = this.NON_ELABORATO;
		Boolean classamentoCompatibile = null;
		Double dSuperficie = parsStringToDouble(rep.getSupDocfaCens());
		Double dConsistenza = parsStringToDouble(rep.getConsistenzaDocfa());
		String categoria = rep.getCategoriaDocfa();

		if (categoria != null && !categoria.trim().equalsIgnoreCase("")) {

			categoria = categoria.trim().toUpperCase();
			/*
			 * Calcolo la tolleranza (= 95% <= x <= 105%) per determinare le
			 * anomalie di consistenza delle categorie diverse da C01, C02, C03
			 * alle quali invece è riservato un trattamento diverso
			 */
			if (categoria.equalsIgnoreCase("C01")|| categoria.equalsIgnoreCase("C02")|| categoria.equalsIgnoreCase("C03")) {
				/*
				 * L'anomalia di consistenza si verifica quando il rapporto tra
				 * consistenza e superficie catastale è inferiore a 0,85
				 */
				supMediaMin = 0d;
				supMediaMax = 0.85d;
				consistSuSuperfCatastale = dConsistenza / dSuperficie;
				if (!(supMediaMin <= consistSuSuperfCatastale && consistSuSuperfCatastale >= supMediaMax))
					addAnomalia(rep, DocfaAnomalie.getCodAnomaliaConsistenza());
			

			} else if (htSuperficiMedie.containsKey(categoria)) {
				if (!categoria.equalsIgnoreCase("A10")) 
					classamentoCompatibile = true;
				

				Double consistenzaMin = dSuperficie/ parsStringToDouble(htSuperficiMedie.get(categoria))* 0.95;
				supMediaMin = consistenzaMin;
				Double consistenzaMax = dSuperficie/ parsStringToDouble(htSuperficiMedie.get(categoria))* 1.05;
				supMediaMax = consistenzaMax;
				consistSuSuperfCatastale = dConsistenza;
				if (!(consistSuSuperfCatastale >= supMediaMin && consistSuSuperfCatastale <= supMediaMax))
					addAnomalia(rep, DocfaAnomalie.getCodAnomaliaConsistenza());
			}
		}

		String supAvgMin = supMediaMin != null ? (new BigDecimal(supMediaMin)).setScale(2, BigDecimal.ROUND_DOWN).toString(): null;
		String supAvgMax = supMediaMax != null ? (new BigDecimal(supMediaMax)).setScale(2, BigDecimal.ROUND_DOWN).toString(): null;
		String consCalc = consistSuSuperfCatastale != null ? (new BigDecimal(consistSuSuperfCatastale)).setScale(2,BigDecimal.ROUND_DOWN).toString() : null;
		rep.setSupAvgMin(supAvgMin);
		rep.setSupAvgMax(supAvgMax);
		rep.setConsCalc(consCalc);
		//rep.setFlgAnomaliaCons(anomaliaCons);
	}

	private static double parsStringToDouble(String s) {
		double d = -1;

		if (s != null && !s.trim().equalsIgnoreCase("")) {
			s = s.replace(',', '.');
			d = Double.parseDouble(s.trim());
		}
		return d;
	}

	private String getDataScadenzaTarsu(String dataDocfa) {
		String dataTarsu = null;
		if (dataDocfa != null) {
			int annoTarsu = Integer.parseInt(dataDocfa.substring(0, 4)) + 1;
			dataTarsu = Integer.toString(annoTarsu) + this.DATA_SCADENZA_TARSU;
		}
		return dataTarsu;
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

	
	
	//Dati da Docfa_Dati_Generali
	private DocfaTarReportBean getDocfaDatiGenerali(DocfaTarReportBean rep) throws Exception{
		ResultSet rs = null;
		String causale = null;
		Date dataVariazione = null;
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
			rep.setDataVariazione(dataVariazione);
			rep.setUiuSoppresse(uiuSoppresse);
			rep.setUiuCostituite(uiuCostituite);
			rep.setUiuVariate(uiuVariate);
			rep.setCausaleDocfa(causale);
			rep.setFlgDocfaSNoC(docfaCessazionePura);
			
		}
		
		return rep;
	}
	
	public void addAnomalia(DocfaTarReportBean rep, String codAnomalia){
		rep.setFlgAnomalie(true);
		rep.addcodAnomalie(codAnomalia);
		
	}
	
	public void addAnomalia(DocfaTarReportSoggBean tit, String codAnomalia){
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
			this.SQL_DOCFA= getJellyParam(ctx, index++, "SQL_DOCFA");
			this.SQL_TARSU_PRECEDENTE = getJellyParam(ctx, index++, "SQL_TARSU_PRECEDENTE");
			this.SQL_DOCFA_SUCCESSIVO = getJellyParam(ctx, index++, "SQL_DOCFA_SUCCESSIVO");
			this.SQL_TARSU_SUCCESSIVO = getJellyParam(ctx, index++, "SQL_TARSU_SUCCESSIVO");
			//this.SQL_TARSU_SUCCESSIVO_20 = getJellyParam(ctx, index++, "SQL_TARSU_SUCCESSIVO_20");
			//this.SQL_TARSU_SUCCESSIVO_DS  = getJellyParam(ctx, index++, "SQL_TARSU_SUCCESSIVO_DS");
			this.SQL_DOCFA_IN_ANNO = getJellyParam(ctx, index++, "SQL_DOCFA_IN_ANNO");
			this.SQL_CIVICI_UIU_CATASTO = getJellyParam(ctx, index++, "SQL_CIVICI_UIU_CATASTO");
			this.SQL_INDICE_CIVICO_CatDocfa = getJellyParam(ctx, index++, "SQL_INDICE_CIVICO_CatDocfa");
			this.SQL_UIU_CATASTO  = getJellyParam(ctx, index++, "SQL_UIU_CATASTO");
			this.SQL_TITOLARI_UIU_ANNO_DOCFA = getJellyParam(ctx, index++, "SQL_TITOLARI_UIU_ANNO_DOCFA");
			this.SQL_INDICE_TITOLARE_CatAnagrafe = getJellyParam(ctx, index++, "SQL_INDICE_TITOLARE_CatAnagrafe");
			this.SQL_RESIDENZA_TITOLARE = getJellyParam(ctx, index++, "SQL_RESIDENZA_TITOLARE");
			this.SQL_INDICE_RESIDENZA_CIVICO_CATASTO = getJellyParam(ctx, index++, "SQL_INDICE_RESIDENZA_CIVICO_CATASTO");
			this.SQL_INDICE_RESIDENZA_CIVICO_DOCFA = getJellyParam(ctx, index++, "SQL_INDICE_RESIDENZA_CIVICO_DOCFA");
			this.SQL_DOCFA_CONTEMPORANEI = getJellyParam(ctx, index++, "SQL_DOCFA_CONTEMPORANEI");
			this.SQL_SUPERFICI_PER_VANO  = getJellyParam(ctx, index++, "SQL_SUPERFICI_PER_VANO");
			//this.SQL_TIT_TARSU_PRECEDENTE = getJellyParam(ctx, index++, "SQL_TIT_TARSU_PRECEDENTE");
			//this.SQL_TIT_TARSU_SUCCESSIVO_20 = getJellyParam(ctx, index++, "SQL_TIT_TARSU_SUCCESSIVO_20");
			//this.SQL_TIT_TARSU_SUCCESSIVO_DS = getJellyParam(ctx, index++, "SQL_TIT_TARSU_SUCCESSIVO_DS");
			this.SQL_DOCFA_DATI_CENSUARI = getJellyParam(ctx, index++, "SQL_DOCFA_DATI_CENSUARI");
			this.SQL_DOCFA_DATI_GENERALI = getJellyParam(ctx, index++, "SQL_DOCFA_DATI_GENERALI");
			this.SQL_DOCFA_DATA_REGISTRAZIONE = getJellyParam(ctx, index++, "SQL_DOCFA_DATA_REGISTRAZIONE");
			this.SQL_DOCFA_DICHIARANTE = getJellyParam(ctx, index++, "SQL_DOCFA_DICHIARANTE");
			this.SQL_SOGG_TAR_PRECEDENTE_UIU = getJellyParam(ctx, index++, "SQL_SOGG_TAR_PRECEDENTE_UIU");
			this.SQL_SOGG_TAR_SUCCESSIVO_UIU = getJellyParam(ctx, index++, "SQL_SOGG_TAR_SUCCESSIVO_UIU");
			this.SQL_SOGG_TAR_PRECEDENTE_UND = getJellyParam(ctx, index++, "SQL_SOGG_TAR_PRECEDENTE_UND");
		    this.SQL_SOGG_TAR_SUCCESSIVO_UND = getJellyParam(ctx, index++, "SQL_SOGG_TAR_SUCCESSIVO_CIV");
			this.SQL_SOGG_TAR_PRECEDENTE_CIV = getJellyParam(ctx, index++, "SQL_SOGG_TAR_SUCCESSIVO_CIV");
			this.SQL_SOGG_TAR_SUCCESSIVO_CIV = getJellyParam(ctx, index++, "SQL_SOGG_TAR_SUCCESSIVO_CIV");
			this.SQL_FAMILIARI_DATA_DOCFA = getJellyParam(ctx, index++, "SQL_FAMILIARI_DATA_DOCFA");
			this.SQL_DOCFA_IN_PARTE_DUE_H = getJellyParam(ctx, index++, "SQL_DOCFA_IN_PARTE_DUE_H");

		} catch (Exception e) {
			log.error("Exception: " + e.getMessage());
			throw e;
		}
	}
	
	private String getJellyParam(Context ctx, int index, String desc) throws Exception{
		String variabile = null;
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