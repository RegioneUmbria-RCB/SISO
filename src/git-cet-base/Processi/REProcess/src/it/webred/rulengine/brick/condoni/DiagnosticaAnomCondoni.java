package it.webred.rulengine.brick.condoni;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import it.webred.rulengine.Context;
import it.webred.rulengine.DbCommand;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.brick.condoni.bean.CondoniCoordinateBean;
import it.webred.rulengine.brick.condoni.bean.DatiAnagrafeBean;
import it.webred.rulengine.brick.condoni.logic.CondoniCoordinate;
import it.webred.rulengine.brick.condoni.utils.Utility;

public class DiagnosticaAnomCondoni extends DbCommand implements Rule {

	private static final Logger log = Logger.getLogger(DiagnosticaAnomCondoni.class.getName());
	private final String SELECT_MI_CONDONO = "SELECT * FROM MI_CONDONO_DIA  " +
   	 " ORDER BY CODICECONDONO";											
	private final String TAB_ANAGRAFE = "SIT_D_PERSONA";
	private final String TAB_ANAG_TRIBUTI = "SIT_T_CONTRIBUENTI"; 
	private final String TAB_ANAG_CATASTO = "ANAG_SOGGETTI";
    private final String TAB_CONDONO_DIA = "MI_CONDONO_DIA";
    private final String SQL_MI_CONDONO_STRALCI= "SELECT TIPOABUSO, COUNT(*) AS NUM FROM MI_CONDONO_STRALCI WHERE CODICECONDONO=?"
    						+ " AND TIPOABUSO IN ('8','12','9','10','11','13') GROUP BY TIPOABUSO";
    private final String TAB_CONDONO_COOR_DIA = "MI_CONDONO_COOR_DIA";
	private final String SQL_COORD_CATASTO_PARZ ="SELECT * FROM SITIUIU "+ 
												 " WHERE FOGLIO=? AND PARTICELLA=? ";									 
												 //" AND DATA_INIZIO_VAL <=? AND DATA_FINE_VAL >=?";
										 
	private final String SQL_COORD_CATASTO= SQL_COORD_CATASTO_PARZ+ " AND UNIMM=? ";
		
	private final String SQL_CIVICIARIO_VIE ="SELECT * FROM SITIDSTR WHERE PKID_STRA =? AND DATA_FINE_VAL = TO_DATE('31/12/9999', 'DD/MM/YYYY')";
	private final String SQL_CIVICIARIO_NUMCIV ="SELECT * FROM SITICIVI WHERE PKID_STRA =? AND CIVICO=? AND DATA_FINE_VAL = TO_DATE('31/12/9999', 'DD/MM/YYYY')";
	
	private final String SQL_COERENZA_COORD ="SELECT * FROM SITICIVI C, SITICIVI_UIU CU " 
										+ " WHERE C.PKID_CIVI = CU.PKID_CIVI AND PKID_UIU = ?"
										+ " AND C.DATA_FINE_VAL = TO_DATE('31/12/9999', 'DD/MM/YYYY')"
										+ "AND CU.DATA_FINE_VAL = TO_DATE('31/12/9999', 'DD/MM/YYYY')";
	
	private final String SQL_TITOLAR_IMM_PARZ ="SELECT * FROM SITICONDUZ_IMM_ALL " 
								       + " WHERE COD_NAZIONALE = 'F205' AND DATA_INIZIO <=? AND DATA_FINE >=?"
								      + 	" AND FOGLIO=? AND PARTICELLA=? ";
	private final String SQL_TITOLAR_IMM = SQL_TITOLAR_IMM_PARZ + " AND UNIMM=? ";
	private final String SQL_TIT_ULT = "SELECT * FROM ANAG_SOGGETTI WHERE COD_SOGGETTO =? ";
	private final String SQL_VARDOCFA = "SELECT * FROM DOCFA_UIU WHERE FOGLIO=? AND NUMERO =? AND SUBALTERNO=?";
    //ici
	private final String SQL_ICI_FPS ="SELECT * FROM SIT_T_OGGETTI_ICI " 
	                               + " WHERE FOGLIO =? AND PARTICELLA=? AND SUBALTERNO =?";
	private final String SQL_ICI_FPS_CIV = "SELECT * FROM SIT_T_CIVICI C, SIT_t_VIE  V "
										+ " WHERE V.PK_VIACOD= C.FK_VI_COD AND C.PK_CIVICO=?";
	private final String SQL_ICI_CIV = "SELECT * FROM SIT_T_OGGETTI_ICI I, SIT_T_CIVICI C, SIT_t_VIE  V " 
						+ " WHERE I.FK_T_CIVICI= PK_CIVICO AND   V.PK_VIACOD= C.FK_VI_COD "
						+ " AND FK_VI_COD= ? AND NUMERO = ?";
    private final String SQL_ICI_CIV_BARRA= SQL_ICI_CIV + " AND LETTERA =?";
	private final String SQL_ICI_ESIB ="SELECT * FROM SIT_T_CONTRIBUENTI WHERE NINC =? AND PROVENIENZA = ? ";
	//tarsu
	private final String SQL_TARSU_FPS ="SELECT * FROM SIT_T_OGGETTI_TARSU" 
									+ " WHERE FOGLIO =? AND PARTICELLA=? AND SUBALTERNO =?";
									
	private final String SQL_TARSU_FPS_CIV = SQL_ICI_FPS_CIV;
	//private final String SQL_TARSU_CIV = "SELECT * FROM SIT_T_OGGETTI_TARSU I, SIT_T_CIVICI C, SIT_t_VIE  V " 
	private final String SQL_TARSU_CIV  ="SELECT DISTINCT NINC, DATA_INI_OGGE, DATA_FINE_OGGE, NINC, PROVENIENZA " 	
										+ " FROM SIT_T_OGGETTI_TARSU I, SIT_T_CIVICI C, SIT_t_VIE  V "
										+ " WHERE I.FK_T_CIVICI= PK_CIVICO AND   V.PK_VIACOD= C.FK_VI_COD "
									    + " AND FK_VI_COD= ? AND NUMERO = ?";
	private final String SQL_TARSU_CIV_BARRA = SQL_TARSU_CIV+ " AND LETTERA =?"; 
	private final String SQL_TARSU_ESIB = SQL_ICI_ESIB;
	String valTrovato="1";
	String valNonTrovato="0";
	//NOMI DELLE COLONNE DA AGGIORNARE A FRONTE DEI RISULTATI OTTENUTI 	
	private final String nomeColAna="ESIB_ANA_CF", nomeColCat="ESIB_CATASTO_CFPI" , nomeColTrib="ESIB_TRIB_CFPI";
	private final String nomeColAltAna="ESIB_ANA_DENOM", nomeColAltCat="ESIB_CATASTO_DENOM" , nomeColAltTrib="ESIB_TRIB_DENOM";
	private final String nomeColVia ="TOPO_VIA", nomeColCivico = "TOPO_CIVICO";
	private final String colTipoAbuso1="AMPL_1_2004", colTipoAbuso2="AMPL_2_2004", colTipoAbuso3="RIST_2004", colTipoAbuso4="REST_2004", colTipoAbuso5="RISAN_2004", colTipoAbuso6="MAN_ST_2004";
    private final String colCat="CAT", colCatData ="CAT_ALLA_DATA",colCoerTopoCat = "COER_TOPO_CAT";
	private final String colEsibTit ="ESIB_TIT", colVarCat ="VAR_CAT", colVarDocfa="VAR_DOCFA";
	private final String colIciFps="ICI_FPS", colIciCiv="ICI_CIV",colIciAnno="ICI_ANNO",colIciEsib="ICI_ESIB";
	private final String colTarsuFps="TARSU_FPS", colTarsuCiv="TARSU_CIV",colTarsuData="TARSU_DATA",colTarsuEsib="TARSU_ESIB";
	
	long numAnmIns=0;
	long numRecCondoni =0;//numero condoni
	long numEsibNull=0;//CF-PIVA ESIBENTE null
	long numEsibNotNull=0;//CF-PIVA ESIBENTE VALORIZZATO
	long numPF=0;//CF-PIVA ESIBENTE-->codice fiscale
	long numPIva=0;//CF-PIVA ESIBENTE-->PIVA
	long numVieNonValide=0;//VIE FORMATO NON VALIDO
	long numCooCtNonDisp=0; //coordinate non disp per il condono (solo info e non registrata l'anomalia
	long NumCooCtNonVal =0;// coordinate non valide 
	long numCooCtComp=0; //disponibile chiave coordinate catastali completa per il condono
	long numCooCtParz=0; //disponibile chiave coordinate catastali parziale per il condono
	String fmt = "yyyy-MM-dd";
	SimpleDateFormat sdf = new SimpleDateFormat(fmt);
	Connection conn;
	String processId;
	public DiagnosticaAnomCondoni(BeanCommand bc){
		super(bc);
	}
	@Override
	public CommandAck runWithConnection(Context ctx, Connection conn)
			throws CommandException {
		log.debug("INIZIATO");
		this.conn = conn;	
		PreparedStatement pst=null, pstAna = null, pstTributi = null;
		ResultSet rs=null;
		String sql = null;
		sql =SELECT_MI_CONDONO;
		ApplicationAck aa = null;
		//processId=ctx.getProcessID();
		try {
			//CondoniInfoDiagnostica.cancellaInfo(conn);
			//cancella TUTTE le anomalie già presenti
			//CondoniAnomali.cancellaAnomalie(null,conn);
			resetColsDia(); //resetta a null i valori delle colonne risultato della diagnostica
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				numRecCondoni++;
				long  codCond = rs.getLong("CODICECONDONO");
				if (numRecCondoni%100 == 0 ) {
				//if (numRecCondoni> 0 ) {
					log.debug("NUMERO REC CONDONI IN ELABORAZIONE: " + numRecCondoni);
					log.debug("COD CONDONO in elab: "+ codCond);
				}
				String codFisPIva=null, denomEsib=null;
				codFisPIva = rs.getString("CFPIESIBENTE");
				denomEsib = rs.getString("ESIBENTE");
				DatiAnagrafeBean datiAnag = controllaAnomEsibente(codFisPIva, denomEsib, codCond);
				controllaStralci(codCond);
				String codiceVia= rs.getString("CODICEVIA");
				String numCivico =rs.getString("NCIVICO");
				String barraNumCivico=rs.getString("BARRANUMCIVICO");
				int ctrlIndirizzo = controllaAnomalieIndirizzo(codCond,codiceVia,numCivico, barraNumCivico);
				ArrayList<CondoniCoordinateBean> listaCoord = CondoniCoordinate.getCoordinate(codCond, conn);
				java.sql.Date dataPg = rs.getDate("PGDATA");
				long annoPg = -1;
				// PGANNO NON SEMPRE CORRISPONDE ALL'ANNO DELLA DATA PROTOCOLLO
				//if (rs.getObject("PGANNO") != null) 
				if (dataPg != null) 
					annoPg = Utility.annoData(dataPg.toString(), fmt);
				controllaInCatasto(listaCoord, dataPg, annoPg,codiceVia, numCivico, barraNumCivico, ctrlIndirizzo, denomEsib, datiAnag);
			}
		    //inserisciInfo();
		    rs.close();
		    pst.close();
			log.debug("Fine diagnostica. Numero record condoni analizzati " + numRecCondoni  );
			aa = new ApplicationAck("DIAGNOSTICA ANOMALIE CONDONO TERMINATA CON SUCCESSO" );
			return (aa);
			}catch (SQLException sqle){
				log.error("Errore sql in esecuzione :" + sql, sqle);
				ErrorAck ea = new ErrorAck(sqle.getMessage());
				return (ea);
			}/*catch (Exception e){
				log.error("Errore generico ", e);
				ErrorAck ea = new ErrorAck(e.getMessage());
				return (ea);
			} */finally {
				try	{
					if (pst != null)
						pst.close();
					if (rs != null)
						rs.close();
				}catch (Exception exc){
					log.error("Errore generico in finally:" , exc);
					ErrorAck ea = new ErrorAck(exc.getMessage());
					return (ea);
				}
		}
	}
	
	private DatiAnagrafeBean controllaAnomEsibente(String codice, String denom,long codCond) throws SQLException{
		long pIvaNum=0;
		PreparedStatement pst=null ;
		ResultSet rs=null;;
		String sqlAna=null, sqlTributi=null, sqlCatasto=null, sqlAltAna=null, sqlAltTrib=null,sqlAltTrib1=null,sqlAltCat=null;
		DatiAnagrafeBean datiAnag=null;
		datiAnag= new DatiAnagrafeBean();
		datiAnag.setCodiceCondono(codCond);
		//datiAnag.setPkIdContribuente(-1);
		datiAnag.setCodFis(null);
		datiAnag.setPIva(null);
		boolean isPiva=false;
		try {
			if (isCFPIEsibenteValid(codice)) {
				try {
					pIvaNum = Long.parseLong(codice);
					isPiva=true;
					numPIva++;
					sqlTributi = "SELECT * FROM " + TAB_ANAG_TRIBUTI + " WHERE PARTITAIVA='" + pIvaNum + "'";
					//codAnmTributi=CondoniAnomalieConstants.ESIBENTE_PIVA_NON_IN_TRIBUTI;
					String pIvaStr = Utility.fill("0","sx", String.valueOf(pIvaNum),11);
					//sqlCatasto = "SELECT * FROM " + TAB_ANAG_CATASTO + " WHERE PART_IVA='" + pIvaStr + "' AND DATA_FINE_VAL = TO_DATE('31/12/9999', 'DD/MM/YYYY')";
					sqlCatasto = "SELECT * FROM " + TAB_ANAG_CATASTO + " WHERE PART_IVA='" + pIvaStr + "'"; 
					//codAnmCatasto=CondoniAnomalieConstants.ESIBENTE_PIVA_NON_IN_CATASTO;
					datiAnag.setPIva(pIvaStr);
				}catch(NumberFormatException nfe){
					numPF++;
					datiAnag.setCodFis(codice);
					sqlAna= "SELECT POSIZ_ANA FROM " + TAB_ANAGRAFE + " WHERE CODFISC='" + codice + "' AND DT_FINE_VAL IS NULL";
					sqlTributi = "SELECT * FROM " + TAB_ANAG_TRIBUTI + " WHERE CODFISC ='" + codice + "'";
					//codAnmTributi=CondoniAnomalieConstants.ESIBENTE_PF_NON_IN_TRIBUTI;
					//sqlCatasto = "SELECT * FROM " + TAB_ANAG_CATASTO + " WHERE COD_FISCALE='" + codice + "' AND DATA_FINE_VAL = TO_DATE('31/12/9999', 'DD/MM/YYYY')";
					sqlCatasto = "SELECT * FROM " + TAB_ANAG_CATASTO + " WHERE COD_FISCALE='" + codice + "'";
					//codAnmCatasto=CondoniAnomalieConstants.ESIBENTE_PF_NON_IN_CATASTO;
				}
			}
			denom= denom.replace("'", "''");
			//sqlAltAna= "SELECT * FROM " + TAB_ANAGRAFE + " WHERE REPLACE (DES_PERSONA,'/',' ') = '" + denom + "'";
			sqlAltAna= "SELECT POSIZ_ANA FROM " + TAB_ANAGRAFE + " WHERE COGNOME || ' ' || NOME ='" + denom + "'";
			sqlAltTrib = "SELECT * FROM " + TAB_ANAG_TRIBUTI + " WHERE COGNOME || ' ' || NOME ='" + denom + "'";
			sqlAltTrib1 = "SELECT * FROM " + TAB_ANAG_TRIBUTI + " WHERE DENOMINAZIONE ='" + denom + "'";
			sqlAltCat =  "SELECT * FROM " + TAB_ANAG_CATASTO + " WHERE DENOMINAZIONE ='" + denom + "'";
			boolean esisteAnag=false;
			if (sqlAna != null) {// il codice fiscale viene ricercato in anagrafe 
				pst = conn.prepareStatement(sqlAna);
				rs = pst.executeQuery();
				if (rs.next()){
					esisteAnag =true;
					aggiornaDiagnostica(codCond, nomeColAna, rs.getString("POSIZ_ANA")); 
				}
				else 
					aggiornaDiagnostica(codCond, nomeColAna,valNonTrovato);
				rs.close();
				pst.close();
			}  
			if (!isPiva && !esisteAnag) { //sqlAna=null vuol dire che il campo CFPIEsib non è in cod fisc o non è valorizzato 
				pst = conn.prepareStatement(sqlAltAna);
				//log.debug("sqlAltAna da eseguire: " + sqlAltAna);
				rs = pst.executeQuery();
				//log.debug("sqlAltAna eseguito.");
				if (rs.next()) {
					esisteAnag=true;
					aggiornaDiagnostica(codCond, nomeColAltAna, rs.getString("POSIZ_ANA")); 
				}else
					aggiornaDiagnostica(codCond, nomeColAltAna, valNonTrovato); 
				rs.close();
				pst.close();
			}
    		//ricerca esibente in tributi
			boolean esisteTributi = false;
			boolean primaVolta= true;
			ArrayList<Long> arrFkIDContrib = null;
			if (sqlTributi != null) {
				pst = conn.prepareStatement(sqlTributi );
				rs = pst.executeQuery();
				while (rs.next())  {
					if (primaVolta){
						aggiornaDiagnostica(codCond, nomeColTrib, valTrovato);
						//datiAnag.setPkIdContribuente(rs.getLong("PK_ID_CONTRIBUENTI"));
						esisteTributi=true;
						arrFkIDContrib = new ArrayList<Long>();
						arrFkIDContrib.add(new Long(rs.getLong("PK_ID_CONTRIBUENTI")));
					}else {
						arrFkIDContrib.add(new Long(rs.getLong("PK_ID_CONTRIBUENTI")));
					}
				}
				rs.close();
				pst.close();
				if (!esisteTributi)
					aggiornaDiagnostica(codCond, nomeColTrib, valNonTrovato);
			}
			if (!esisteTributi) {
				pst = conn.prepareStatement(sqlAltTrib);
				//log.debug("sqlAltTrib da eseguire: " + sqlAltTrib);
				rs = pst.executeQuery();
				//log.debug("sqlAltTrib eseguito");
				while (rs.next()) {
					if (primaVolta){
						esisteTributi=true;
						aggiornaDiagnostica(codCond, nomeColAltTrib, valTrovato); 
						arrFkIDContrib = new ArrayList<Long>();
						arrFkIDContrib.add(new Long(rs.getLong("PK_ID_CONTRIBUENTI")));
					}else {
						arrFkIDContrib.add(new Long(rs.getLong("PK_ID_CONTRIBUENTI")));
					}
				}	
				rs.close();
				pst.close();
				if (!esisteTributi) {
					pst = conn.prepareStatement(sqlAltTrib1);
					//log.debug("sqlAltTrib1 da eseguire: " + sqlAltTrib1);
					rs = pst.executeQuery();
					//log.debug("sqlAltTrib1 eseguito");
					while (rs.next()) {
						if (primaVolta){
							esisteTributi=true;
							aggiornaDiagnostica(codCond, nomeColAltTrib, valTrovato); 
							arrFkIDContrib = new ArrayList<Long>();
							arrFkIDContrib.add(new Long(rs.getLong("PK_ID_CONTRIBUENTI")));
						}else {
							arrFkIDContrib.add(new Long(rs.getLong("PK_ID_CONTRIBUENTI")));
						}
					}	
					rs.close();
					pst.close();
					if (!esisteTributi)	
						aggiornaDiagnostica(codCond, nomeColAltTrib, valNonTrovato);
					
				}
			}
			datiAnag.setPkIdsContrib(arrFkIDContrib);
			//ricerca esibente in catasto 
			boolean esisteCatasto = false;
			if (sqlCatasto != null){
				pst = conn.prepareStatement(sqlCatasto );
				rs = pst.executeQuery();
				if (rs.next())  {
					aggiornaDiagnostica(codCond, nomeColCat, valTrovato);
					esisteCatasto=true;
				}else
					aggiornaDiagnostica(codCond, nomeColCat, valNonTrovato);
				rs.close();
				pst.close();
			}
			if (!esisteCatasto){
				pst = conn.prepareStatement(sqlAltCat);
				//log.debug("sqlAltCat da eseguire: " + sqlAltCat);
				rs = pst.executeQuery();
				//log.debug("sqlAltCat eseguito" );
				if (rs.next()) {
					esisteCatasto=true;
					aggiornaDiagnostica(codCond, nomeColAltCat, valTrovato); 
				}else
					aggiornaDiagnostica(codCond, nomeColAltCat, valNonTrovato); 
				rs.close();
				pst.close();
			}
			rs.close();
			pst.close();
			
			return datiAnag;
		}catch(SQLException sqle) {
			log.error("ERRORE SQL", sqle);
			throw sqle;
		}finally {
			if (rs != null)
				rs.close();
			if (pst!= null)
				pst.close();
		}
	}
	
	private boolean isCFPIEsibenteValid(String codice) throws SQLException{
		boolean retVal = true;
		if (codice == null || codice.equals(""))
			return false; 
		if (codice.indexOf("'" )!= -1){
			retVal=false;
		}
		long pIvaNum = -1;
		try {
			pIvaNum = Long.parseLong(codice);
			if (pIvaNum == 0) 
				return false;
		}catch(NumberFormatException nfe){
			
		}
		return retVal;
	}
	
	private void controllaStralci(long codice) throws SQLException{
		PreparedStatement pst=null ;
		ResultSet rs=null;;
		String sql=SQL_MI_CONDONO_STRALCI;
		try {
			pst = conn.prepareStatement(sql);
			pst.setLong(1,codice);
			rs = pst.executeQuery();
			while (rs.next()){
				if(rs.getString("TIPOABUSO").trim().equals("8"))
					aggiornaDiagnostica(codice, colTipoAbuso1, "" + rs.getLong("NUM")); 
				if(rs.getString("TIPOABUSO").trim().equals("12"))
					aggiornaDiagnostica(codice, colTipoAbuso2, "" + rs.getLong("NUM")); 
				if(rs.getString("TIPOABUSO").trim().equals("9"))
					aggiornaDiagnostica(codice, colTipoAbuso3, "" + rs.getLong("NUM")); 
				if(rs.getString("TIPOABUSO").trim().equals("10"))
					aggiornaDiagnostica(codice, colTipoAbuso4, "" + rs.getLong("NUM")); 
				if(rs.getString("TIPOABUSO").trim().equals("11"))
					aggiornaDiagnostica(codice, colTipoAbuso5, "" + rs.getLong("NUM")); 
				if(rs.getString("TIPOABUSO").trim().equals("13"))
					aggiornaDiagnostica(codice, colTipoAbuso6, "" + rs.getLong("NUM")); 
			}
			rs.close();
			pst.close();
		}catch(SQLException sqle) {
			log.error("ERRORE SQL", sqle);
			throw sqle;
		}finally {
			if (rs != null)
				rs.close();
			if (pst!= null)
				pst.close();
		}
	}	
    private void controllaInCatasto(ArrayList<CondoniCoordinateBean> listaCoo, java.sql.Date dataPg,long annoPg, String via, String civico, String barra, int ctrlIndir, String denomEsib, DatiAnagrafeBean datiAnag)  
				throws SQLException{
		//String codAnmCatastoCoor;
      	// valori delle colonne DA AGGIORNARE A FRONTE DEI RISULTATI OTTENUTI 
    	String valPresCat, valAssCat,  valPresCatData, valAssCatData, valCoerCat, valNonCoerCat, valAssDocfa;
    	String valVarCat; // stringa che rappresentza la data della variazione catastale + vicina 
    	String valVarDocfa; // stringa che rappresentza la data del Docfa + vicino
    	String valEsibTit, valEsibNonTit,valIciFPS,valIciFPSNonPres ,valIciCiv, valIciPres, valIciNonPres;
    	String valTarsuFPS, valTarsuFPSNonPres, valTarsuCiv, valTarsuData, valTarsuPres, valTarsuNonPres;
    	int esisteIciAnno=-1; // rimane a -1 se non posso fare il controllo per ASSENZA DI DENUNCE ISI DA VERIFICARE 
		int esisteIciCiv=-1; // rimane a -1 se non posso fare il controllo
		int esisteIciCivFPS=-1; // rimane a -1 se non posso fare il controllo
		int esisteIciEsib=-1; // rimane a -1 se non posso fare il controllo (per assenza in anagrafe tributi del contribuente o assenza di debunce ici)
     	//int esisteTarsuData=-1; // rimane a -1 se non posso fare il controllo  
		int esisteTarsuCivFPS=-1; // rimane a -1 se non posso fare il controllo
		int esisteTarsuCiv=-1; // rimane a -1 se non posso fare il controllo
		int esisteTarsuEsib=-1; // rimane a -1 se non posso fare il controllo (per assenza in anagrafe tributi del contribuente o assenza di debunce ici) 
 		PreparedStatement pst=null, pstCat=null, pstTitUlt=null, pstEsib=null, pstCiv=null;
		ResultSet rs=null, rsCat=null, rsCoerenza=null, rsTitolarita=null, rsTitolaritaUlt=null, rsEsib=null, rsCiv=null;
		String sql=null;
		valIciFPS="1";	
		valIciFPSNonPres="0";
		valIciPres="1"; // valore da asscociare alla presenza assenza per vari dat ICI  
		valIciNonPres="0";
		valTarsuPres="1"; // valore da asscociare alla presenza assenza per vari dat ICI  
		valTarsuNonPres="0";
		valAssDocfa="0";
		valTarsuFPS="1";	
		valTarsuFPSNonPres="0";
		//java.sql.Date dtTarsuProx=null;
		String dtTarsuProxStr=null;
		String tipoDataTarsu="";
		//long deltaDate=999999;// Questa variabile non viene ri-azzerata quando cambio le coordinate ma vale per tutto il condono (a differenza di diffDate che invece viene riazzerata ad ogni coordinata)
		valTarsuData=null;//se rimane a null significa che non è stato trovato nulla in tarsu
		int codVia =0, numCivico=0;
		try {
			codVia = Integer.parseInt(via); //via è la via nella pratica di condono. La rendo numerica
			numCivico=Integer.parseInt(civico); 
		}catch (NumberFormatException nfe) {}//non si dovrebbe verificare...
		for (int i = 0; i < listaCoo.size(); i++ ){
			CondoniCoordinateBean coor = listaCoo.get(i);
			if (coor.getFoglio()== 0 || coor.getNumero()==null) {
				NumCooCtNonVal++;
				continue;
			}
			if (coor.getSub()== null) {
				sql=SQL_COORD_CATASTO_PARZ;
				valPresCat="P1"; // valore associato alla presenza parziale in catasto
				valAssCat="P0"; //valore associato alla ASSENZA con ricerca parz in catasto
				valPresCatData="P1"; // valore associato alla presenza parziale in catasto alla data
				valAssCatData="P0"; //valore associato alla ASSENZA con ricerca parz in catasto alla data
				valCoerCat ="1";
				valNonCoerCat ="0";
				valEsibTit ="P1";
				valEsibNonTit ="P0";
				numCooCtParz++;
			}
			else{
				sql=SQL_COORD_CATASTO;
				valPresCat = "1"; //valore associato alla presenza
				valAssCat="0"; //valore associato alla ASSENZA in catasto
				valPresCatData = "1"; //valore associato alla presenza alla data
				valAssCatData="0"; //valore associato alla ASSENZA in catasto alla data
				valCoerCat ="1";
				valNonCoerCat ="0";
				valEsibTit ="1";
				valEsibNonTit ="0";
				numCooCtComp++;
			}
			valVarCat=null;
			valVarDocfa = null;
	    	valIciCiv=null;// sarà valorizzato di volta in volta con il giusto valore
			valTarsuCiv=null;// sarà valorizzato di volta in volta con il giusto valor
			boolean esisteInCatasto= false;
			boolean esisteInCatastoData=false;
			boolean coerente=false;
			boolean titolare=false;
	    	//boolean esisteDocfa = false;
			int esisteDocfa=-1;
	    	//boolean esisteIciFPS=false;
			int esisteIciFPS=-1;
	    	esisteIciCivFPS=-1;
	    	esisteIciEsib=-1;
	    	esisteIciAnno=-1;
	    	//boolean esisteTarsuFPS=false;
	    	int esisteTarsuFPS=-1;
	    	esisteTarsuCivFPS=-1;
	    	esisteTarsuEsib=-1;
	    	//dtTarsuProx=null;
	    	dtTarsuProxStr=null;
			tipoDataTarsu="";
	    	java.sql.Date dtVarCatProx=null;
			java.sql.Date dtVarDocfaProx=null;
			long diffDate=999999;
			try {
				String comFoglio ="" + coor.getFoglio(); 
				int comPart = -1, comSub =-1;
				try {
					comPart= Integer.parseInt(coor.getNumero());
					if (coor.getSub()!= null)
						comSub =Integer.parseInt(coor.getSub());
				}catch(NumberFormatException nfe) {}
				pstCat = conn.prepareStatement(sql);
				pstCat.setLong(1, coor.getFoglio());
				pstCat.setString(2, coor.getNumero());
				if (coor.getSub()!= null) {
					pstCat.setString(3, coor.getSub());
					//log.debug("esegue SQL_CAT_...: " + sql + " parms: " + coor.getFoglio() +"," + coor.getNumero()+ "," + coor.getSub());
				}else{
					//log.debug("esegue SQL_CAT_...: " + sql + " parms: " + coor.getFoglio() +"," + coor.getNumero());
				}
				rsCat = pstCat.executeQuery();
				//log.debug("eseguito SQL_CAT");
				while(rsCat.next()){// scorrimento righe catasto
					// se esistono in catasto allora verifico alla data, coerenza fra gli indirizzi e titolarità
					esisteInCatasto=true;
					//ESISTENZA IN CATASTO ALLA DATA
					if (dataPg != null &&  rsCat.getDate("DATA_INIZIO_VAL") != null &&  rsCat.getDate("DATA_FINE_VAL") != null ){
						java.sql.Date dtIniValCat = rsCat.getDate("DATA_INIZIO_VAL");
						java.sql.Date dtFinValCat = rsCat.getDate("DATA_FINE_VAL");
						if (dtIniValCat.compareTo(dtFinValCat) != 0) {
							if (dtIniValCat.compareTo(dataPg)<=0 && dtFinValCat.compareTo(dataPg) >= 0) 
								esisteInCatastoData=true;
							//Per cercare la data di variazione più vicina alla PGDATA...
							if (rsCat.getDate("DATA_INIZIO_VAL").compareTo(rsCat.getDate("DATA_FINE_VAL")) != 0 ){
								if (dtVarCatProx == null)
									dtVarCatProx=dtIniValCat;
								String dtIniStr = dtIniValCat.toString();
								String dtFinStr = dtFinValCat.toString();
								String dataPgStr = dataPg.toString();
								long comDiffDate = 999999;
								comDiffDate =Utility.giorniDifferenza(dtIniStr, dataPgStr, fmt,null, true) ; 
								if (comDiffDate < diffDate){
									dtVarCatProx=dtIniValCat;
									diffDate=comDiffDate;
								}
								comDiffDate =Utility.giorniDifferenza(dtFinStr, dataPgStr, fmt,null, true) ;
								if (comDiffDate< diffDate){
									dtVarCatProx=dtFinValCat;
									diffDate=comDiffDate;
								}	
							}
						}
						
					}
					//scorrimento righe catasto: coerenza
					long pkIdUiu= rsCat.getLong("PKID_UIU"); //ID_UIU associato alla ricerca effettuata 
					if (ctrlIndir == 1 && !coerente) {// controllo coerenza solo per le gli indirizzi trovati in topovie, topocivici
						sql=SQL_COERENZA_COORD;
						pst = conn.prepareStatement(sql);
						pst.setLong(1,pkIdUiu);
						//log.debug("esegue sql coerenza: " + sql + " par: " +pkIdUiu);
						rsCoerenza =pst.executeQuery();
						//log.debug("eseguito sql coerenza" );
						while(rsCoerenza.next() && !coerente ){ //scorro tutti gi indirizzi in catasto
							if (codVia !=0 ) {
								if (rsCoerenza.getLong("PKID_STRA") == codVia) {
									String sitiCivico = rsCoerenza.getString("CIVICO");
								    if (sitiCivico != null) {
									   if(sitiCivico.equals(civico))
										  coerente = true;
									   else if (barra != null && (sitiCivico.equals(civico + barra) || sitiCivico.equals(civico +"/" + barra)) )
										   coerente = true; 
								    }
								}
							}
						}
						rsCoerenza.close();
						pst.close();
					}//fine controllo coerenza indirizzo
				} // fine scorrimento righe presenti in catasto per le coordinate
				rsCat.close();
				pstCat.close();
				if(esisteInCatastoData)
					valVarCat =dtVarCatProx.toString();
				//(controlli da fare se le coord - ACNHE SOLO FOGLIO + NUMERO - sono in catasto)
				if (esisteInCatasto && dataPg != null){// la titolarità viene valutata alla data. Togli condizione su dataPg se fare diversamente  
					//titolarità
					String codice=null;
					if (datiAnag.getCodFis() != null)
						codice=datiAnag.getCodFis();
					else if (datiAnag.getPIva()!= null){
						codice=datiAnag.getPIva();
					}
					if (coor.getSub()!= null) 
				    	sql = SQL_TITOLAR_IMM;
					else
						sql= SQL_TITOLAR_IMM_PARZ;
					pst = conn.prepareStatement(sql);
					pst.setDate(1, dataPg);
					pst.setDate(2, dataPg);
					pst.setLong(3, coor.getFoglio());
					pst.setString(4, coor.getNumero());
					if (coor.getSub()!= null) {
						pst.setString(5, coor.getSub());
						//log.debug("esegue query sql titolarità: " + sql + " parms1-2,3,4, 5: " + dataPg.toString() + "," + coor.getFoglio() + "," + coor.getNumero() + "," + coor.getSub());
					}
					else {
						//log.debug("esegue query sql titolarità: " + sql + " parms1-2,3,4: " + dataPg.toString() + "," + coor.getFoglio() + "," + coor.getNumero());
					}
					rsTitolarita = pst.executeQuery();
					//log.debug("eseguito sql titolarità");
					while(rsTitolarita.next() && !titolare){
						String cuaa = rsTitolarita.getString("CUAA");
						if (codice != null && cuaa.equals(codice))
							titolare=true;
					    else {
					    	long pkCuaa = rsTitolarita.getLong("PK_CUAA");
					    	sql = SQL_TIT_ULT;
					    	pstTitUlt=conn.prepareStatement(sql);
					    	pstTitUlt.setLong(1, pkCuaa);
					    	rsTitolaritaUlt=pstTitUlt.executeQuery();
					    	if (rsTitolaritaUlt.next()){
					    		String denom = rsTitolaritaUlt.getString("DENOMINAZIONE");
					    		if (denom.equals(denomEsib))
					    			titolare=true;
					    	}
					    	rsTitolaritaUlt.close();
					    	pstTitUlt.close();
					    }
					}
					rsTitolarita.close();
					pst.close();
					// CONTROLLI DA FARE QUANDO ESISTONO IN CATASTO LE COORDINATE COMPLETE
					if (coor.getSub()!= null) {
						// RICERCA FRA I DOCFA
						sql = SQL_VARDOCFA;
						pst = conn.prepareStatement(sql);
						String foglioDocfa = Utility.fill("0", "sx",comFoglio , 4);
						String subDocfa = Utility.fill("0", "sx",coor.getSub() , 4);
						pst.setString(1, foglioDocfa);
						pst.setString(2, coor.getNumero());
						pst.setString(3, subDocfa);
						//log.debug("esegue sql_vardocfa" + sql);
						rs=pst.executeQuery();
						//log.debug("eseguito  sql_vardocfa");
						diffDate=999999;
						esisteDocfa =0; 
						String prot="";
						dtVarDocfaProx=null;
						while (rs.next()) {
							esisteDocfa= 1;
							if (dataPg != null ){
								if (dtVarDocfaProx == null) {
									dtVarDocfaProx=rs.getDate("FORNITURA");
								}
								String dtDocFaStr = rs.getDate("FORNITURA").toString();
								String dataPgStr = dataPg.toString();
								long comDiffDate = 999999;
								comDiffDate =Utility.giorniDifferenza(dtDocFaStr, dataPgStr, fmt,null, true) ; 
								if (comDiffDate < diffDate){
									dtVarDocfaProx=rs.getDate("FORNITURA");
									prot = rs.getString("PROTOCOLLO_REG");
								}
							}
						}
						pst.close();
						rs.close();
						if (esisteDocfa==1){
							int anno = Utility.annoData(dtVarDocfaProx.toString(), fmt);
							int mese =  Utility.meseData(dtVarDocfaProx.toString(), fmt);
							mese++; 
							String meseData="";
							if (mese <10)
								meseData = "0" + mese;
							else
								meseData = "" +mese;
							valVarDocfa = prot + " - " + meseData + "/" + anno;
						}
					}
				} // fine ricerca fra le coordinate presenti in catasto
				//controlli per le coord complete (presenti o no in catasto)
				if (coor.getSub()!= null ){
					//ricerca denuncia ICI 
					sql = SQL_ICI_FPS;
					pst = conn.prepareStatement(sql);
					String foglioIci = comFoglio; 
					String partIci = "" + comPart;
					String subIci="" + comSub;
					pst.setString(1, foglioIci);
					pst.setString(2, partIci);
					pst.setString(3, subIci);
					//log.debug("SQL_ICI_FPS IN EXEC: " + SQL_ICI_FPS + " PARMS: "+ foglioIci +"," + partIci + "," +subIci);
					rs=pst.executeQuery();
					//log.debug("SQL_ICI_FPS eseguito"); 
					esisteIciFPS=0;
					while (rs.next()) {
						esisteIciFPS=1;
						//verifico far queste denunce il civico
							if( ctrlIndir == 1 && esisteIciCivFPS <= 0 ){
								esisteIciCivFPS=0; // posso verificare la coerenza fra il civico del condonmo equello delle varie ici che sto analizzando 
								valIciCiv="C0";
								sql = SQL_ICI_FPS_CIV; 
								pstCiv = conn.prepareStatement(sql);
								pstCiv.setLong (1, rs.getLong("FK_T_CIVICI"));	
								//log.debug("SQL_ICI_FPS_CIV: " + sql + " parm: " +  rs.getLong("FK_T_CIVICI"));
						    	rsCiv = pstCiv.executeQuery();
								//log.debug("SQL_ICI_FPS_CIV() eseguito");
								if (rsCiv.next() && esisteIciCivFPS==0) {
									if (rsCiv.getInt("FK_VI_COD") == codVia && rsCiv.getInt("NUMERO")== numCivico){
										if (barra==null || barra.equals("") ){
											esisteIciCivFPS=1;
											valIciCiv="C1"; //ESISTE IL CIVICO FRA GLI OGG ICI CON STESSE COORDINATE
										}else{
											if(rsCiv.getString("LETTERA").equals(barra) ) {
												esisteIciCivFPS=1;
												valIciCiv="C1"; //ESISTE
											} 
										}
									}
								}
								rsCiv.close();
								pstCiv.close();
							}
						// ...l'anno ...
						if (esisteIciAnno<=0 && annoPg != -1) {//esisteIciAnno=-1: NON è ANCORA STATO VERIIFICATO, 0: VERIFICATO E NON TROVATO
							 esisteIciAnno = 0;
							 if (rs.getObject("DEN_RIFERIMENTO") != null) {
								 if (rs.getLong("DEN_RIFERIMENTO") ==  annoPg) // fra queste denunce verifico l'anno ...
									 esisteIciAnno=1;
							 }
						}
						// .. e l'esibente ....
							if (esisteIciEsib<=0 && datiAnag.getPkIdsContrib()!= null) {
								esisteIciEsib=0;
								long ninc = rs.getLong("NINC");
								String proven = rs.getString("PROVENIENZA");
								sql = SQL_ICI_ESIB;
								pstEsib = conn.prepareStatement(sql);
								pstEsib.setLong (1, ninc);
								pstEsib.setString(2, proven);
								//log.debug("SQL_ICI_ESIB da eseguire: " + sql + " parm: " + ninc + "," + proven);
								rsEsib = pstEsib.executeQuery();
								//log.debug("SQL_ICI_ESIB eseguito"); 
								while (rsEsib.next() && esisteIciEsib==0) {
									for (int j = 0; j < datiAnag.getPkIdsContrib().size();j++ ){
										if (rsEsib.getLong("PK_ID_CONTRIBUENTI") == datiAnag.getPkIdsContrib().get(j))
											esisteIciEsib=1;
										if(	esisteIciEsib==1)
											break;
									}
								}
								rsEsib.close();
								pstEsib.close();
						}
											
					} // FINE SCORRIMENTO DENUNCE ICI
					pst.close();
					rs.close();
					// fine controlli ici  alivello di FPS 
					// VERIFICHE TARSU SULLE COORDINATE
					sql = SQL_TARSU_FPS;
					pst = conn.prepareStatement(sql);
					foglioIci = comFoglio; // in ici e tarsu hanno lo stesso formato
					partIci = "" + comPart;
					subIci="" + comSub;
					pst.setString(1, foglioIci);
					pst.setString(2, partIci);
					pst.setString(3, subIci);
					//log.debug("sql_tarsu_fps in esec: " + SQL_TARSU_FPS + "PARMS: " + foglioIci + "," + partIci + "," + subIci);
					rs=pst.executeQuery();
					//log.debug("sql_tarsu_fps eseguito");
					long deltaDate=999999;
					esisteTarsuFPS=0;
					while (rs.next()) {
						esisteTarsuFPS=1;
						//verifico far queste denunce il civico
							if( ctrlIndir == 1 && esisteTarsuCivFPS <= 0 ){
								esisteTarsuCivFPS=0;
								valTarsuCiv="C0";
								sql = SQL_TARSU_FPS_CIV; 
								pstCiv = conn.prepareStatement(sql);
								pstCiv.setLong (1, rs.getLong("FK_T_CIVICI"));	
								//log.debug("SQL_TARSU_FPS_CIV: " + sql + " parm: " +  rs.getLong("FK_T_CIVICI"));
					    	    rsCiv = pstCiv.executeQuery();
								//log.debug("SQL_TARSU_FPS_CIV() eseguito");
								if (rsCiv.next() && esisteTarsuCivFPS==0) {
									if (rsCiv.getInt("FK_VI_COD") == codVia && rsCiv.getInt("NUMERO")== numCivico){
										if (barra==null || barra.equals("") ){
											esisteTarsuCivFPS=1;
											valTarsuCiv="C1"; //ESISTE IL CIVICO FRA GLI OGG TARSU CON STESSE COORDINATE
										}else{
											if(rsCiv.getString("LETTERA").equals(barra) ) {
												esisteTarsuCivFPS=1;
												valTarsuCiv="C1"; //ESISTE
											} 
										}
									}
								}
								rsCiv.close();
								pstCiv.close();
							}
						// ...l'anno ...
						if (dataPg != null) {
							String comDt ="";
							//Per cercare la data dell'oggetto tarsu più vicina alla PGDATA...
							if (dtTarsuProxStr == null) {
								comDt = rs.getString("DATA_INI_OGGE");
								dtTarsuProxStr=comDt.substring(6,10 ) + "-"  + comDt.substring(3,5 ) + "-" + comDt.substring(0,2) ;
							}
							comDt = rs.getString("DATA_INI_OGGE");
							String dtIniStr = comDt.substring(6,10 ) + "-"  + comDt.substring(3,5 ) + "-" + comDt.substring(0,2) ;
							comDt =rs.getString("DATA_FINE_OGGE");
							String dtFinStr = comDt.substring(6,10 ) + "-"  + comDt.substring(3,5 ) + "-" + comDt.substring(0,2) ;
							String dataPgStr = dataPg.toString();
							long comDiffDate = 999999;
							comDiffDate =Utility.giorniDifferenza(dtIniStr, dataPgStr, fmt,null, true) ; 
							if (comDiffDate < deltaDate){
								dtTarsuProxStr = dtIniStr;
								deltaDate=comDiffDate;
								tipoDataTarsu="I";
							}
							comDiffDate =Utility.giorniDifferenza(dtFinStr, dataPgStr, fmt,null, true) ; 
							if (comDiffDate < deltaDate){
								dtTarsuProxStr = dtFinStr;
								deltaDate=comDiffDate;
								tipoDataTarsu="F";
							} 
						}
						// .. e l'esibente ....
							if (esisteTarsuEsib<=0 && datiAnag.getPkIdsContrib()!= null) {
								esisteTarsuEsib=0;
								long ninc = rs.getLong("NINC");
								String proven = rs.getString("PROVENIENZA");
								sql = SQL_TARSU_ESIB;
								pstEsib = conn.prepareStatement(sql);
								pstEsib.setLong (1, ninc);
								pstEsib.setString(2, proven);
								//log.debug("SQL_TARSU_ESIB da eseguire: " + sql + " parm: " + ninc + "," + proven);
								rsEsib = pstEsib.executeQuery();
								//log.debug("SQL_TARSU_ESIB eseguito"); 
								while (rsEsib.next() && esisteTarsuEsib==0) {
									for (int j = 0; j < datiAnag.getPkIdsContrib().size();j++ ){
										if (rsEsib.getLong("PK_ID_CONTRIBUENTI") == datiAnag.getPkIdsContrib().get(j))
											esisteTarsuEsib=1;
										if (esisteTarsuEsib== 1)
											break;
									}
								}
								rsEsib.close();
								pstEsib.close();
						}
											
					} // FINE SCORRIMENTO OGGETTI TARSU
					pst.close();
					rs.close();
					
					// fine tarsu
				}
				ResultSet rsIciCiv=null;
				if(esisteIciCivFPS==1)
					esisteIciCiv=1;
				if(esisteTarsuCivFPS==1)
					esisteTarsuCiv=1;
				//Nel caso in cui nessuna fra le coordinate per cui esiste una denuncia ICI abbia lo stesso 
				//indirizzodel condono, si cerca se esiste una denuncia ISI sul solo indirizzo del condono ...
				//if (cercatoIciCiv =false && esisteIciCivFPS<=0 && ctrlIndir == 1 ) {// il controllo si fa una volta sola per ciascun condono e non per ogni coordinata ... 
				//	cercatoIciCiv=true;
				if (esisteIciCivFPS<=0 && ctrlIndir == 1 ) {// se lo faccio una volta sola come nel codice commenatto, mi devo salvare i valori..altrimenti vengono "ricoperti" dall'elaboraizone della coordinata successiva.
					esisteIciCiv =0; // valore per dire: sono in grado di fare al ricerca.
					valIciCiv="0";
					if (barra == null || barra.equals(""))
						sql=SQL_ICI_CIV;
					else
						sql = SQL_ICI_CIV_BARRA;
					pst = conn.prepareStatement(sql);
					pst.setInt(1,codVia); 
					pst.setInt(2,numCivico);
					if (barra != null && !barra.equals("")) {
						pst.setString(3,barra);
						//log.debug("sqlIciCiv DA ESEGUIRE: " + sql + " - PAR1,PAR2, par3 : " + codVia +"," +numCivico + "," + barra);
					}else{
						//log.debug("sqlIciCiv DA ESEGUIRE: " + sql + " - PAR1,PAR2: " + codVia +"," +numCivico);
					}
					rsIciCiv=pst.executeQuery();
					//log.debug("sqlIciCiv ESEGUITO");
					while (rsIciCiv.next()) { // ... e fra queste denunce, verifico l'anno e l'esibente
						 esisteIciCiv = 1;
						 valIciCiv="1";
						 if (esisteIciAnno<=0 && annoPg != -1) {//esisteIciAnno=-1: NON è ANCORA STATO VERIIFICATO, 0= VERIFICATO E NON TROVATO
							 esisteIciAnno = 0;
							 if (rsIciCiv.getObject("DEN_RIFERIMENTO") != null) {
								 if (rsIciCiv.getLong("DEN_RIFERIMENTO") ==  annoPg)
									 esisteIciAnno=1;
							 }
						 }
						// verico se è una denuncia ICI per l'esibente
						if (esisteIciEsib <= 0 && datiAnag.getPkIdsContrib() !=null) {
							esisteIciEsib=0;
 							long ninc = rsIciCiv.getLong("NINC");
 							String proven = rsIciCiv.getString("PROVENIENZA");
							sql = SQL_ICI_ESIB;
							pstEsib = conn.prepareStatement(sql);
							pstEsib.setLong (1, ninc);
							pstEsib.setString(2, proven);
							//log.debug("SQL_ICI_ESIB da eseguire: " + sql + " parm: " + ninc + "," + proven);
							rsEsib = pstEsib.executeQuery();
							//log.debug("SQL_ICI_ESIB eseguito");
							while (rsEsib.next() && esisteIciEsib==0) {
								for (int j = 0; j < datiAnag.getPkIdsContrib().size();j++ ){
									if (rsEsib.getLong("PK_ID_CONTRIBUENTI") == datiAnag.getPkIdsContrib().get(j))
										esisteIciEsib=1;
									if(	esisteIciEsib==1)
										break;
								}
							}
							rsEsib.close();
							pstEsib.close();
						}
					}
					rsIciCiv.close();
					pst.close();
				}//fine controlli ici non legati alle coordinate
				//Nel caso in cui nessuna fra le coordinate per cui esiste un oggetto tarsu abbia lo stesso 
				//indirizzo del condono, si cerca se esiste un oggetto tarsu sul solo indirizzo del condono ...
				//if (cercatoTarsuCiv =false && esisteTarsuCivFPS<=0 && ctrlIndir == 1 ) {// il controllo si fa una volta sola per ciascun condono e non per ogni coordinata ... 
				//	cercatoTarsuCiv=true;
				if (esisteTarsuCivFPS<=0 && ctrlIndir == 1 ) {
					esisteTarsuCiv =0; // valore per dire: sono in grado di fare al ricerca.
					valTarsuCiv="0";
					if (barra == null || barra.equals(""))
						sql=SQL_TARSU_CIV;
					else
						sql = SQL_TARSU_CIV_BARRA;
					pst = conn.prepareStatement(sql);
					pst.setInt(1,codVia); 
					pst.setInt(2,numCivico);
					if (barra != null && !barra.equals("")) {
						pst.setString(3,barra);
						//log.debug("sqlTarsuCiv DA ESEGUIRE: " + sql + " - PAR1,PAR2, par3 : " + codVia +"," +numCivico + "," + barra);
					}else{
						//log.debug("sqlTarsuCiv DA ESEGUIRE: " + sql + " - PAR1,PAR2: " + codVia +"," +numCivico);
					}
					rsIciCiv=pst.executeQuery();
					//log.debug("sqlIciCiv ESEGUITO");
					long deltaDate = 999999;
					//dtTarsuProx=null;
					dtTarsuProxStr=null;
					long comNinc=-1;
					while (rsIciCiv.next()) { // ... e fra queste denunce, verifico l'anno e l'esibente
						 esisteTarsuCiv = 1;
						 valTarsuCiv="1";
						 if (dataPg != null) {
							 String comDt ="";
							//Per cercare la data dell'oggetto tarsu più vicina alla PGDATA...
							 if (dtTarsuProxStr == null) {
									comDt = rsIciCiv.getString("DATA_INI_OGGE");
									dtTarsuProxStr=comDt.substring(6,10 ) + "-"  + comDt.substring(3,5 ) + "-" + comDt.substring(0,2 ) ;
								}
								comDt = rsIciCiv.getString("DATA_INI_OGGE");
								String dtIniStr = comDt.substring(6,10 ) + "-"  + comDt.substring(3,5 ) + "-" + comDt.substring(0,2 ) ;
								comDt =rsIciCiv.getString("DATA_FINE_OGGE");
								String dtFinStr = comDt.substring(6,10 ) + "-"  + comDt.substring(3,5 ) + "-" + comDt.substring(0,2 ) ;
								String dataPgStr = dataPg.toString();
								long comDiffDate = 999999;
								comDiffDate =Utility.giorniDifferenza(dtIniStr, dataPgStr, fmt,null, true) ; 
								if (comDiffDate < deltaDate){
									dtTarsuProxStr = dtIniStr;
									deltaDate=comDiffDate;
									tipoDataTarsu="I";
								}
								comDiffDate =Utility.giorniDifferenza(dtFinStr, dataPgStr, fmt,null, true) ; 
								if (comDiffDate < deltaDate){
									dtTarsuProxStr = dtFinStr;
									deltaDate=comDiffDate;
									tipoDataTarsu="F";
								} 
							}
				    	 // verico se è un oggetto tarsu  per l'esibente
						if (esisteTarsuEsib <= 0 && datiAnag.getPkIdsContrib()!=null) {
							esisteTarsuEsib=0;
							long ninc = rsIciCiv.getLong("NINC");
 							String proven = rsIciCiv.getString("PROVENIENZA");
	 						if (comNinc != ninc) {
	 							sql = SQL_TARSU_ESIB;
								pstEsib = conn.prepareStatement(sql);
								pstEsib.setLong (1, ninc);
								pstEsib.setString(2, proven);
								//log.debug("SQL_TARSU_ESIB da eseguire: " + sql + " parm: " + ninc + "," + proven);
								rsEsib = pstEsib.executeQuery();
								//log.debug("SQL_TARSU_ESIB eseguito"); 
								while (rsEsib.next() && esisteTarsuEsib==0) {
									for (int j = 0; j < datiAnag.getPkIdsContrib().size();j++ ){
										if (rsEsib.getLong("PK_ID_CONTRIBUENTI") == datiAnag.getPkIdsContrib().get(j))
											esisteTarsuEsib=1;
										if (esisteTarsuEsib== 1)
											break;
									}
								}
								rsEsib.close();
								pstEsib.close();
								comNinc = ninc;
 							}
						}
					}
					rsIciCiv.close();
					pst.close();
				}//fine controlli ici non legati alle coordinate
				
				//fine controlli tarsu
			
				if (esisteInCatasto) {
					aggiornaDiagnosticaCoordinate(coor, colCat, valPresCat);
					if (esisteInCatastoData)  {
						aggiornaDiagnosticaCoordinate(coor, colCatData, valPresCatData);
						String comDt = valVarCat.substring(8,10 ) + "/"  + valVarCat.substring(5,7 ) + "/" + valVarCat.substring(0,4) ;
						aggiornaDiagnosticaCoordinate(coor, colVarCat , comDt);
					}
				    else
				    	aggiornaDiagnosticaCoordinate(coor, colCatData, valAssCatData);
					if (coerente) 
						aggiornaDiagnosticaCoordinate(coor, colCoerTopoCat, valCoerCat);
	                else
	                	aggiornaDiagnosticaCoordinate(coor, colCoerTopoCat, valNonCoerCat);
					if (titolare)
						aggiornaDiagnosticaCoordinate(coor, colEsibTit, valEsibTit);
					else
						aggiornaDiagnosticaCoordinate(coor, colEsibTit, valEsibNonTit);
					if (esisteDocfa==1)
						aggiornaDiagnosticaCoordinate(coor, colVarDocfa, valVarDocfa);
					else if (esisteDocfa==0){
						aggiornaDiagnosticaCoordinate(coor, colVarDocfa, valAssDocfa);
					}
			   }else	
				// NON è PRESENTE  in catasto
					aggiornaDiagnosticaCoordinate(coor, colCat, valAssCat);
				//ici
				if (esisteIciFPS==1)
					aggiornaDiagnosticaCoordinate(coor, colIciFps, valIciFPS);
				else if (esisteIciFPS==0) {
					aggiornaDiagnosticaCoordinate(coor, colIciFps, valIciFPSNonPres);
				}
				if (esisteIciEsib==1)
					aggiornaDiagnosticaCoordinate(coor, colIciEsib, valIciPres);
				else if(esisteIciEsib==0)	{
					aggiornaDiagnosticaCoordinate(coor, colIciEsib, valIciNonPres);
				}
				if (esisteIciCiv==1)
					aggiornaDiagnosticaCoordinate(coor, colIciCiv, valIciCiv);
				else if(esisteIciCiv==0) {	
					aggiornaDiagnosticaCoordinate(coor, colIciCiv, valIciCiv);
				}
				if (esisteIciAnno==1)
					aggiornaDiagnosticaCoordinate(coor, colIciAnno, valIciPres);
				else if (esisteIciAnno==0)	{
					aggiornaDiagnosticaCoordinate(coor, colIciAnno, valIciNonPres);
				}
				//tarsu
				if (esisteTarsuFPS ==1)
					aggiornaDiagnosticaCoordinate(coor, colTarsuFps, valTarsuFPS);
				else if (esisteTarsuFPS ==0){
					aggiornaDiagnosticaCoordinate(coor, colTarsuFps, valTarsuFPSNonPres);
				}
				if (esisteTarsuCiv==1)
					aggiornaDiagnosticaCoordinate(coor, colTarsuCiv, valTarsuCiv);
				else if(esisteTarsuCiv==0) {	
					aggiornaDiagnosticaCoordinate(coor, colTarsuCiv, valTarsuCiv);
				}
				if (esisteTarsuEsib==1)
					aggiornaDiagnosticaCoordinate(coor, colTarsuEsib, valTarsuPres);
				else if(esisteTarsuEsib==0)	{
					aggiornaDiagnosticaCoordinate(coor, colTarsuEsib, valTarsuNonPres);
				}
				if (dtTarsuProxStr !=null)  {
					String comDt = dtTarsuProxStr.substring(8,10 ) + "/"  + dtTarsuProxStr.substring(5,7 ) + "/" + dtTarsuProxStr.substring(0,4) ;
					valTarsuData=comDt + " (" + tipoDataTarsu + ")";
					aggiornaDiagnosticaCoordinate(coor, colTarsuData, valTarsuData);
				}
			}catch(SQLException sqle) {
				log.error("ERRORE SQL", sqle);
				throw sqle;
			}finally {
				if (rsCat != null)
					rsCat.close();
				if (rsCoerenza != null)
					rsCoerenza.close();
				if (rsTitolarita != null)
					rsTitolarita.close();
				if (pst!= null)
					pst.close();
				if (pstCat!= null)
					pstCat.close();
				
			}
		}// fine del ciclo di scorrimento delle coordinate
		
	}
	
	private int controllaAnomalieIndirizzo (long codCond, String via, String civico, String barra) 
		throws SQLException{
		//retVal: -1=controllo non effettuato; 
		//0=via non trovata; 1= indirizzo trovato;  2--> via trovata, civico non trovato;  
		int retVal = -1;
		//String codAnmInd;
		PreparedStatement pst=null;
		ResultSet rs=null;
		String sql=null;
		if ( via==null || via.equals("0") ) {
     		//segnalaAnomaliaCondono(codCond, CondoniAnomalieConstants.VIA_NON_DISPONIBILE);
			return retVal;
		}//else if (civico==null || civico.equals("0") )  
			//segnalaAnomaliaCondono(codCond, CondoniAnomalieConstants.CIVICO_NON_DISPONIBILE );
		sql=SQL_CIVICIARIO_VIE;
		//codAnmInd = CondoniAnomalieConstants.VIA_NON_IN_CIVICIARIO;

		try {
			long codVia =0;
			try {
				codVia = Long.parseLong(via);
			}catch (NumberFormatException nfe) {
				numVieNonValide++;
				return retVal;
			}
			pst = conn.prepareStatement(sql);
			pst.setLong(1, codVia);
			//log.debug("sql TOPO_VIA da eseguire " + sql + " PARMS 1 " + codVia);
			rs = pst.executeQuery();
			//log.debug("sql TOPO_VIA eseguito");
			if (!rs.next()){
				// NON è PRESENTE 
				aggiornaDiagnostica(codCond, nomeColVia, valNonTrovato);
			   	retVal =0;
			   	rs.close();
				pst.close();
			   	return retVal;
			}
			//else{//cerco il civico
			aggiornaDiagnostica(codCond, nomeColVia, valTrovato);
			sql = SQL_CIVICIARIO_NUMCIV;
			//if (barra!=null && !barra.equals(""))
			//   civico = civico + barra;
			rs.close();
			pst.close();
			pst = conn.prepareStatement(sql);
			pst.setLong(1, codVia);
			pst.setString(2, civico);
			//log.debug("sql TOPO_CIVICO da eseguire: " + sql + " - parms 1,2: " + codVia +"," + civico);
			rs = pst.executeQuery();
			//log.debug("sql TOPO_CIVICO eseguito"); 
			if (rs.next()) {
				aggiornaDiagnostica(codCond, nomeColCivico, valTrovato);
				retVal=1;
				rs.close();
				pst.close();
			   	return retVal;
			}
			//else {	
			if (barra!=null && !barra.equals("")) {// riprovo con numero/barracivico
				civico = civico + "/" + barra;	
				rs.close();
				pst.close();
				pst = conn.prepareStatement(sql);
				pst.setLong(1, codVia);
				pst.setString(2, civico);
				rs = pst.executeQuery();		
				if (rs.next()) {
					aggiornaDiagnostica(codCond, nomeColCivico, valTrovato);
					retVal=1;
				}
				else {
					rs.close();
					pst.close();
					civico = civico + barra;	
					pst = conn.prepareStatement(sql);
					pst.setLong(1, codVia);
					pst.setString(2, civico);
					rs = pst.executeQuery();		
					if (rs.next()) {
						aggiornaDiagnostica(codCond, nomeColCivico, valTrovato);
						retVal=1;
					}else {
					// CIVICO NON è PRESENTE 
				   	//segnalaAnomaliaCondono(codCond, codAnmInd);
					aggiornaDiagnostica(codCond, nomeColCivico, valNonTrovato);
				    retVal= 2;
					}
					rs.close();
					pst.close();
				}
			}else{
				// civico NON è PRESENTE 
			   	//segnalaAnomaliaCondono(codCond, codAnmInd);
				aggiornaDiagnostica(codCond, nomeColCivico, valNonTrovato);
				retVal = 2;
			}
				rs.close();
				pst.close();
			//}
			//}
			if (rs != null)
				rs.close();
			if (pst!= null)
				pst.close();
			return retVal;
		}catch(SQLException sqle) {
			log.error("ERRORE SQL", sqle);
			throw sqle;
		}finally {
			if (rs != null)
				rs.close();
			if (pst!= null)
				pst.close();
			
		}
	}
	
	private void aggiornaDiagnostica(long codCondono, String col, String valore) throws SQLException {
		String sql = "UPDATE " + TAB_CONDONO_DIA + " SET " + col +" = '" + valore + "' WHERE CODICECONDONO=" +codCondono;
		faiUpdate (sql);
	}
	
	private void aggiornaDiagnosticaCoordinate(CondoniCoordinateBean coor, String col, String valore) throws SQLException {
		String sql = "UPDATE " + TAB_CONDONO_COOR_DIA + " SET " + col +" = '" + valore + "'" +
		            " WHERE CODCONDONO=" +coor.getCodiceCondono() + " AND FOGLIO =" + coor.getFoglio() +
		            " AND MAPPALE ='" +coor.getNumero() + "' AND SUB ='" + coor.getSub() +"'";
		faiUpdate (sql);
	}
	
	private void resetColsDia() throws SQLException{
		String [] arrColsToReset = new String[14];
		arrColsToReset[0] = nomeColAna;
		arrColsToReset[1] = nomeColAltAna;
		arrColsToReset[2] = nomeColCat;
		arrColsToReset[3] = nomeColAltCat;
		arrColsToReset[4] = nomeColTrib;
		arrColsToReset[5] = nomeColAltTrib;
		arrColsToReset[6] = nomeColVia;
		arrColsToReset[7] = nomeColCivico;
		arrColsToReset[8] = colTipoAbuso1;
		arrColsToReset[9] = colTipoAbuso2;
		arrColsToReset[10] = colTipoAbuso3;
		arrColsToReset[11] = colTipoAbuso4;
		arrColsToReset[12] = colTipoAbuso5;
		arrColsToReset[13] = colTipoAbuso6;
		for (int i = 0; i < arrColsToReset.length; i++) {
			String col = arrColsToReset[i];
			String sql ="UPDATE " + TAB_CONDONO_DIA + " SET " + col +" = null WHERE " + col + " IS NOT NULL";
			faiUpdate(sql);
		}
		arrColsToReset = new String[14];
		arrColsToReset[0] = colCat;
		arrColsToReset[1] = colCatData;
		arrColsToReset[2] = colCoerTopoCat;
		arrColsToReset[3] = colEsibTit;
		arrColsToReset[4] = colVarCat;
		arrColsToReset[5] = colVarDocfa;
		arrColsToReset[6] = colIciFps;
		arrColsToReset[7] = colIciCiv;
		arrColsToReset[8] = colIciAnno;
		arrColsToReset[9] = colIciEsib;
		arrColsToReset[10] = colTarsuFps;
		arrColsToReset[11] = colTarsuCiv;
		arrColsToReset[12] = colTarsuData;
		arrColsToReset[13] = colTarsuEsib;
		for (int i = 0; i < arrColsToReset.length; i++) {
			String col = arrColsToReset[i];
			String sql ="UPDATE " + TAB_CONDONO_COOR_DIA + " SET " + col +" = null WHERE " + col + " IS NOT NULL";
			faiUpdate(sql);
		}
		
	}
	
	private void faiUpdate(String sql) throws SQLException{
		PreparedStatement pst=null;
		try{
	       pst = conn.prepareStatement(sql);
	       pst.executeUpdate();
	       pst.close();
	       
		}catch(SQLException sqle) {
			throw sqle;
		}
		finally {
			if (pst != null)
				pst.close();
		}
	}
	
	
	
}

