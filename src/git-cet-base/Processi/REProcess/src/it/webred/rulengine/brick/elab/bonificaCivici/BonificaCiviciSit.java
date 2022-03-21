package it.webred.rulengine.brick.elab.bonificaCivici;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

public class BonificaCiviciSit extends Command implements Rule {
	
	private static final Logger log = Logger.getLogger(BonificaCiviciSit.class.getName());
	
	
//	private static final String DEF_ANA_SAN_DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
//	private static final String DEF_ANA_SAN_CONN_STRING = "jdbc:oracle:thin:@172.29.0.2:1521:MDBWEB";
//	private static final String DEF_ANA_SAN_USER_NAME = "mdbweb";
//	private static final String DEF_ANA_SAN_PASSWORD = "mdbweb";
	
	
	private static final String SQL_ESTRAZIONE_CIVICI_CAT = "SELECT DISTINCT SITICOMU.cod_nazionale,LID.foglio ,LID.mappale as particella,LID.SUB, "
			+ "TRIM(t.descr) as SEDIME,TRIM(ind.ind) as indirizzo, remove_lead_zero (ind.civ1) AS civico , ind.id_imm, ind.progressivo, ind.seq, ind.codi_fisc_luna, ind.sezione "
			+ "FROM SITICOMU,CAT_D_TOPONIMI T,LOAD_CAT_UIU_ID LID INNER JOIN LOAD_CAT_UIU_IND IND "
			+ "ON (IND.SEZIONE = LID.SEZIONE AND IND.ID_IMM = LID.ID_IMM "
			+ "AND IND.PROGRESSIVO = LID.PROGRESSIVO AND IND.SEQ = LID.SEQ) "
			+ "WHERE LID.CODI_FISC_LUNA = siticomu.CODI_FISC_LUNA "
			+ "AND IND.CODI_FISC_LUNA = siticomu.CODI_FISC_LUNA "
			+ "AND LID.SEZ = SITICOMU.SEZIONE_CENSUARIA "
			+ "AND T.PK_ID = IND.TOPONIMO "
			+ "AND IND.TODELETE ='0' "
			+ "AND IND.SEQ = (SELECT MAX(SEQ) FROM load_cat_uiu_ind IND21 "
			+ "               WHERE IND21.ID_IMM = IND.ID_IMM "
			+ "               AND IND21.CODI_FISC_LUNA = IND.CODI_FISC_LUNA "
			+ "               AND IND21.PROGRESSIVO = IND.PROGRESSIVO "
			+ "               AND IND21.SEZIONE = ind.SEZIONE "
			+ "               AND IND21.todelete ='0') "
			+ "AND LID.SEQ = (SELECT MAX(SEQ) FROM load_cat_uiu_id LID2 "
			+ "               WHERE LID2.ID_IMM = LID.ID_IMM "
			+ "               AND LID2.CODI_FISC_LUNA = LID.CODI_FISC_LUNA "
			+ "               AND LID2.PROGRESSIVO = LID.PROGRESSIVO "
			+ "               AND LID2.SEZIONE = LID.SEZIONE "
			+ "               AND LID2.foglio = LID.foglio "
			+ "               AND LID2.mappale = LID.mappale "
			+ "               AND LID2.sub = LID.sub) "
			+ "AND IND.PROGRESSIVO = (SELECT MAX(PROGRESSIVO) FROM load_cat_uiu_ind IND22 "
			+ "		                  WHERE IND22.ID_IMM = IND.ID_IMM "
			+ "                       AND IND22.CODI_FISC_LUNA = IND.CODI_FISC_LUNA "
			+ "                       AND IND22.SEZIONE = IND.SEZIONE "
			+ "                          AND IND22.todelete ='0' ) "
			+ "AND IND.ID_IMM IN (SELECT DISTINCT IDE_IMMO "
			+ "                 FROM SITIUIU "
			+ "                 WHERE DATA_FINE_VAL = TO_DATE ('99991231', 'yyyymmdd')) "; 
//			+ "AND SITICOMU.cod_nazionale = 'F704' "
//			+ "AND LID.foglio = LPAD('0061', 4, '0') "
//			+ "AND LID.mappale = LPAD('00058', 5, '0') "
//			+ "AND LID.SUB = LPAD('25', 4, '0') ";

	private static final String SQL_ESTRAZIONE_CIVICI_SIT = ""
			+ "select      siticomu.COD_NAZIONALE, "
			+ "            sitiuiu.FOGLIO, "
			+ "            sitiuiu.PARTICELLA, "
			+ "            decode (NVL(sitiuiu.SUB,'*'),'*','-',' ','-',sitiuiu.SUB) as SUB, "
			+ "            sitiuiu.UNIMM, "
			+ "            decode(siticomu.ID_SEZC,null,'-',siticomu.ID_SEZC) AS SEZIONE, "
			+ "            sitiuiu.PKID_UIU, "
			+ "            siticivi.CIVICO, "
			+ "	           TRIM(sitidstr.PREFISSO) as SEDIME, "
			+ "            TRIM(sitidstr.NOME) AS INDIRIZZO, "
			+ "            siticivi.PKID_CIVI as PK_CIVICO, "
			+ "            sitidstr.PKID_STRA as PK_INDIRIZZO "
			+ "from sitiuiu,siticivi_uiu,siticivi,sitidstr,siticomu "
			+ "where (siticomu.COD_NAZIONALE = ?) "
			+ "and sitiuiu.FOGLIO = ? "
			+ "and sitiuiu.PARTICELLA = ? "
			+ "and decode (NVL(sitiuiu.SUB,'*'),'*','-',' ','-',sitiuiu.SUB) = decode (NVL('','*'),'*','-',' ','-','') "
			+ "and lpad(to_char(sitiuiu.UNIMM),4,'0') = lpad(nvl(trim(?), '0'),4,'0') "
			+ "and to_char(sitiuiu.DATA_FINE_VAL, 'dd/mm/yyyy') = '31/12/9999' "
			+ "AND siticivi_uiu.PKID_UIU (+) = sitiuiu.PKID_UIU "
			+ "and siticivi_uiu.PKID_CIVI = siticivi.PKID_CIVI (+) "
			+ "and sitidstr.PKID_STRA (+) = siticivi.PKID_STRA "
			+ "and siticivi_uiu.DATA_FINE_VAL (+) = TO_DATE('99991231', 'yyyymmdd') "
			+ "and siticivi.DATA_FINE_VAL (+) = TO_DATE('99991231', 'yyyymmdd') "
			+ "and sitidstr.DATA_FINE_VAL (+) = TO_DATE('99991231', 'yyyymmdd') "
			+ "and sitiuiu.COD_NAZIONALE = siticomu.COD_NAZIONALE "
			+ "order by INDIRIZZO, CIVICO"; 
	
//	private static final String SQL_CREATE_APPO_BONIFICA_CIVICO_SIT = "CREATE TABLE APPO_BONIFICA_CIVICO_SIT("
//			+ "DATA_ELABORAZIONE DATE DEFAULT SYSDATE,"			
//			+ "COD_NAZIONALE     VARCHAR2 (10 BYTE),"
//			+ "FOGLIO            NUMBER (4),"
//			+ "PARTICELLA        CHAR (5 BYTE),"
//			+ "SUB               VARCHAR2 (3 BYTE),"
//			+ "UNIMM              NUMBER (5),"
//			+ "SEDIME_CAT        VARCHAR2 (50 Byte),"
//			+ "INDIRIZZO_CAT     VARCHAR2 (50 Byte),"
//			+ "CIVICO_CAT        VARCHAR2 (100 BYTE),"
//			+ "PKID_UIU          VARCHAR2 (100 BYTE),"
//			+ "SEDIME_SIT        VARCHAR2 (30 BYTE),"
//			+ "INDIRIZZO_SIT   	 VARCHAR2 (200 BYTE),"
//			+ "CIVICO_SIT        VARCHAR2 (100 BYTE),"
//			+ "PK_CIVICO         NUMBER (9),"
//			+ "PK_INDIRIZZO		 NUMBER (9) )";
	
//	private static final String SQL_INSERT_APPO_BONIFICA_CIVICO_SIT = "INSERT INTO APPO_BONIFICA_CIVICO_SIT "
//			+ "(DATA_ELABORAZIONE, COD_NAZIONALE, FOGLIO, PARTICELLA, SUB, UNIMM, "
//			+ "SEDIME_CAT, INDIRIZZO_CAT, CIVICO_CAT, PKID_UIU,"
//			+ "SEDIME_SIT, INDIRIZZO_SIT, CIVICO_SIT, PK_CIVICO, PK_INDIRIZZO) "
//			+ "VALUES ( SYSDATE, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,)";
	
	private String SQL_DECODIFICA_VIARIO = "SELECT * FROM JOIN_VIE_CAT_SIT WHERE IND = ?";
	
	private String SQL_DELETE_CIVICO_UIU = "DELETE FROM SITILOC_UIU CU WHERE CU.PKID_LOC = ? AND CU.PKID_UIU = ?";
	private String SQL_UPDATE_CIVICO_UIU = "UPDATE SITICIVI_UIU SET UTENTE = 'CLEANER_1' WHERE PKID_CIVI = ? AND PKID_UIU = ?";
	private String SQL_INSERT_CIVICO_UIU = "INSERT INTO SITILOC_UIU (FKID_TYPE,PKID_LOC,PKID_UIU,UTENTE) VALUES (-1, ?, ?,'CLEANER_2')";
	
	private String SQL_VERIFICA_INDIRIZZO_PRESENTE = "SELECT CIV.PKID_CIVI,STR.PREFISSO SEDIME,STR.NOME,CIV.CIVICO FROM SITICIVI CIV,SITIDSTR STR "
			+ " WHERE STR.DATA_FINE_VAL = TO_DATE('99991231', 'yyyymmdd') "
			+ " AND CIV.DATA_FINE_VAL = TO_DATE('99991231', 'yyyymmdd') "
			+ " AND CIV.PKID_STRA = STR.PKID_STRA "
			+ " AND STR.COD_NAZIONALE = ? "
			+ " AND STR.NOME = ? "
			+ " AND CIV.CIVICO = ? ";
	
	private String SQL_PKUIU_FPS = "select   PKID_UIU "
								+ "	from sitiuiu "
								+ "	where COD_NAZIONALE = ? "
								+ "	and FOGLIO = ? "
								+ "	and PARTICELLA = ? "
								+ "	and decode (NVL(SUB,'*'),'*','-',' ','-',SUB) = decode (NVL('','*'),'*','-',' ','-','') "
								+ " and lpad(to_char(UNIMM),4,'0') = lpad(nvl(trim(?), '0'),4,'0') "
								+ " and to_char(DATA_FINE_VAL, 'dd/mm/yyyy') = '31/12/9999'"; 
	
	class CivicoAppo {
		private String codNazionale = null;
		private String foglio = null;
		private String particella = null;
		private String sub = null;
		private String sedime = null;
		private String indirizzo = null;
		private String civico = null;
		private String idImm = null;
		private String pkCivico = null;
		private String pkStrada = null;
		
		CivicoAppo (){}
		public String getCodNazionale() {
			return codNazionale;
		}

		public void setCodNazionale(String codNazionale) {
			this.codNazionale = codNazionale;
		}

		public String getFoglio() {
			return foglio;
		}

		public void setFoglio(String foglio) {
			this.foglio = foglio;
		}

		public String getParticella() {
			return particella;
		}

		public void setParticella(String particella) {
			this.particella = particella;
		}

		public String getSub() {
			return sub;
		}

		public void setSub(String sub) {
			this.sub = sub;
		}

		public String getSedime() {
			return sedime;
		}

		public void setSedime(String sedime) {
			this.sedime = sedime;
		}

		public String getIndirizzo() {
			return indirizzo;
		}
		
		public String getIndirizzoNormalizzato()
		{
			if(indirizzo != null)
			{
				if (indirizzo.startsWith("S")) {
					if (indirizzo.contains("SANTA "))
						indirizzo = indirizzo.replace("SANTA ", "S.");
					else if (indirizzo.contains("SANTO "))
						indirizzo = indirizzo.replace("SANTO ", "S.");	
					else if (indirizzo.contains("SANT "))
						indirizzo = indirizzo.replace("SANT ", "S.");
					else if (indirizzo.contains("SANT'"))
						indirizzo = indirizzo.replace("SANT'", "S.");
					else if (indirizzo.contains("SAN "))
						indirizzo = indirizzo.replace("SAN ", "S.");
				}
				
				// ' e spazio con apostofo senza spazio
				indirizzo = indirizzo.replaceAll("[\\'][\\s]","'");
				indirizzo =  indirizzo.replaceAll("[\\.][\\s]",".");			
			}
			return indirizzo;
		}
		
		public String getCivicoNormalizzato(){
			if(civico != null){
				civico = civico.replaceAll("\\\\", "");
				civico = civico.replaceAll("-", "");	
				civico = civico.replaceAll("/", "");
			}
			
			return civico;
		}

		public void setIndirizzo(String indirizzo) {
			this.indirizzo = indirizzo;
		}

		public String getCivico() {
			return civico;
		}

		public void setCivico(String civico) {
			this.civico = civico;
		}

		public String getIdImm() {
			return idImm;
		}

		public void setIdImm(String idImm) {
			this.idImm = idImm;
		}
		public String getPkCivico() {
			return pkCivico;
		}
		public void setPkCivico(String pkCivico) {
			this.pkCivico = pkCivico;
		}
		public String getPkStrada() {
			return pkStrada;
		}
		public void setPkStrada(String pkStrada) {
			this.pkStrada = pkStrada;
		}
		
	}
	
	/**
	 * @param bc
	 */
	public BonificaCiviciSit(BeanCommand bc){
		super(bc);
	}
	
	public BonificaCiviciSit(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}
	
	@Override
	public CommandAck run(Context ctx) throws CommandException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		PreparedStatement pstmtSit = null;
		ResultSet rsSit = null;

		try {

			log.debug("CTL-BONCIV Inizio!:"+new Date().getTime() );
			
			conn = ctx.getConnection("DWH_" + ctx.getBelfiore());
			
			//recupero tutti i civici Localizzazione CAT
			pstmt = conn.prepareStatement(SQL_ESTRAZIONE_CIVICI_CAT);
			
			rs = pstmt.executeQuery();
			ArrayList<CivicoAppo> civiciCat = new ArrayList<CivicoAppo>();
			int contaRecordTrovati = 0;
			while (rs.next()) {
				CivicoAppo civ = new CivicoAppo();
				civ.setCodNazionale(rs.getString("cod_nazionale"));
				civ.setFoglio(rs.getString("foglio"));
				civ.setParticella(rs.getString("particella"));
				civ.setSub(rs.getString("SUB"));
				civ.setSedime(rs.getString("sedime"));
				civ.setIndirizzo(rs.getString("indirizzo"));
				civ.setCivico(rs.getString("civico"));
				civ.setIdImm("id_imm");
				civiciCat.add(civ);
				contaRecordTrovati++;
				if (contaRecordTrovati%10000==0 )
					log.debug("Record Aggiunti ="+contaRecordTrovati);
				
			}
			
			DbUtils.close(rs);
			DbUtils.close(pstmt);
			
			log.debug("Record Trovati ="+contaRecordTrovati);
			
			int contaRecordElaborati = 0;
			for (CivicoAppo civ : civiciCat) {
				contaRecordElaborati++;
				if (contaRecordElaborati%10000==0 )
					log.debug("Record Elaborati ="+contaRecordElaborati);
				//recupero decodifica della via dalla tabella di raccordo
				String nomeViaSit = null;
				PreparedStatement pstmtDecVia = conn.prepareStatement(SQL_DECODIFICA_VIARIO);
				pstmtDecVia.setString(1, civ.getIndirizzo());
				ResultSet rsDecVia = pstmtDecVia.executeQuery();
				if (rsDecVia.next()){
					nomeViaSit = rsDecVia.getString("IND_VIARIO");
				}
				
				DbUtils.close(rsDecVia);
				DbUtils.close(pstmtDecVia);
				
				//se trovo una corrispondenza allora indago se ci sono vie associate all'UIU da rimuovere
				if (nomeViaSit!=null && !nomeViaSit.trim().equalsIgnoreCase("")){
				
					//recupero i civici SIT per FG-PART-SUB
					pstmtSit = conn.prepareStatement(SQL_ESTRAZIONE_CIVICI_SIT);
					pstmtSit.setString(1, civ.getCodNazionale());
					pstmtSit.setString(2, civ.getFoglio());
					pstmtSit.setString(3, civ.getParticella());
					pstmtSit.setString(4, civ.getSub());
					rsSit = pstmtSit.executeQuery();
				
					CivicoAppo civicoGiusto = null;
					ArrayList<CivicoAppo> chiaviCivici = new ArrayList<CivicoAppo>();
					while (rsSit.next()) {
		
						CivicoAppo civSIT = new CivicoAppo();
						civSIT.setCodNazionale(rsSit.getString("COD_NAZIONALE"));
						civSIT.setFoglio(rsSit.getString("FOGLIO"));
						civSIT.setParticella(rsSit.getString("PARTICELLA"));
						civSIT.setSub(rsSit.getString("SUB"));
						civSIT.setSedime(rsSit.getString("SEDIME"));
						civSIT.setIndirizzo(rsSit.getString("INDIRIZZO"));
						civSIT.setCivico(rsSit.getString("CIVICO"));
						civSIT.setIdImm(rsSit.getString("PKID_UIU"));
						civSIT.setPkCivico(rsSit.getString("PK_CIVICO"));
						civSIT.setPkStrada(rsSit.getString("PK_INDIRIZZO"));
					
						//controllo se è civico da considerare
						if (civSIT.getPkCivico()!=null && !civSIT.getPkCivico().equalsIgnoreCase("")) {
							
							//confronto la descrizione della via	
							if (civSIT.getIndirizzo()!=null && civSIT.getIndirizzo().length()>0){
								
								//stessa via
								if (civSIT.getIndirizzo().trim().equalsIgnoreCase(nomeViaSit)){
									//controllo se è stesso civico
									if (civ.getCivicoNormalizzato()!= null && civ.getCivicoNormalizzato().equalsIgnoreCase(civSIT.getCivicoNormalizzato())){
										//questa è l'associazione da mantenere
										civicoGiusto = civSIT;
									}else{
										chiaviCivici.add(civSIT);
									}
									
								}else{
									chiaviCivici.add(civSIT);
								}
								
							}else{
								chiaviCivici.add(civSIT);
							}
						}
						
					}//fine while rsSit
					
					DbUtils.close(rsSit);
					DbUtils.close(pstmtSit);
					
					boolean trovatoIndSIT = false;
					boolean riuscitoInsSIT = false;
					if (civicoGiusto!= null){
						
						//aggiorno il record del civico che viene mantenuto per riconoscere che è stato processato
						PreparedStatement psUpdCU = conn.prepareStatement(SQL_UPDATE_CIVICO_UIU);
						psUpdCU.setBigDecimal(1, new BigDecimal(civicoGiusto.getPkCivico()));
						psUpdCU.setBigDecimal(2, new BigDecimal(civicoGiusto.getIdImm()));
						
						psUpdCU.execute();
						
						DbUtils.close(psUpdCU);
						//System.out.println("Aggiorno Associazione che rimane: PK_CIVI="+civicoGiusto.getPkCivico()+ " e PK_UIU="+civicoGiusto.getIdImm());
					}
					else{
						//verifico se in SITICIVI esiste la coppia via + civico e l'associo io
						PreparedStatement psFndInd = conn.prepareStatement(SQL_VERIFICA_INDIRIZZO_PRESENTE);
						psFndInd.setString(1, civ.getCodNazionale());
						psFndInd.setString(2, nomeViaSit);
						psFndInd.setString(3, civ.getCivicoNormalizzato());
						
						ResultSet rsFndInd = psFndInd.executeQuery();
						
						Integer pkCivico = null;
						String sedime = null;
						String nome = null;
						String civico = null;
						if (rsFndInd.next()){ //CIV.PKID_CIVI,STR.PREFISSO SEDIME,STR.NOME,CIV.CIVICO
							pkCivico = rsFndInd.getInt("PKID_CIVI");
							sedime = rsFndInd.getString("SEDIME");
							nome = rsFndInd.getString("NOME");
							civico = rsFndInd.getString("CIVICO");
							trovatoIndSIT = true;
							
						}
						DbUtils.close(psFndInd);
						
						if (trovatoIndSIT){
							//recupero pkid_uiu e poi procedo
							PreparedStatement psFndPkUiu = conn.prepareStatement(SQL_PKUIU_FPS);
							psFndPkUiu.setString(1, civ.getCodNazionale());
							psFndPkUiu.setInt(2, civ.getFoglio()!=null&&!civ.getFoglio().trim().equalsIgnoreCase("")?Integer.parseInt(civ.getFoglio()):0 );
							psFndPkUiu.setString(3, civ.getParticella());
							psFndPkUiu.setInt(4, civ.getSub()!=null&&!civ.getSub().trim().equalsIgnoreCase("")?Integer.parseInt(civ.getSub()):0 );
							
							ResultSet rsFndPkUiu = psFndPkUiu.executeQuery();
							Integer pkidUiu = null;
							if (rsFndPkUiu.next()){ //PKID_UIU
								pkidUiu = rsFndPkUiu.getInt("PKID_UIU");
							}
							
							DbUtils.close(psFndPkUiu);
							
							if (pkidUiu != null && pkCivico != null){
								//collego l'indirizzo SIT alla UIU
								PreparedStatement psInsInd = conn.prepareStatement(SQL_INSERT_CIVICO_UIU);
								psInsInd.setInt(1, pkCivico);
								psInsInd.setInt(2, pkidUiu);
								
								try{
									riuscitoInsSIT = psInsInd.execute();
									
								}catch(Exception e){
									log.error("ERRORE IN INSERT SITILOC_UIU PER I SEGUENTI DATI-> foglio:"+civ.getFoglio()+"-part:"+civ.getParticella()+"-sub:"+civ.getSub()+"- pkid_civi:"+pkCivico+"-pkid_uiu:"+pkidUiu);
								}
								
								DbUtils.close(psInsInd);
							}
						}
						
					}
					
					if ((trovatoIndSIT && riuscitoInsSIT) || civicoGiusto != null){
						//rimuovo l'associazione per tutti i civici e indirizzi diversi per quell'UIU
						for (Iterator<CivicoAppo> iterChiavi = chiaviCivici.iterator(); iterChiavi.hasNext();) {
							CivicoAppo civi = (CivicoAppo) iterChiavi.next();
							PreparedStatement psDelCU = conn.prepareStatement(SQL_DELETE_CIVICO_UIU);
							psDelCU.setBigDecimal(1, new BigDecimal(civi.getPkCivico()));
							psDelCU.setBigDecimal(2, new BigDecimal(civi.getIdImm()));
							
							psDelCU.execute();
							
							DbUtils.close(psDelCU);
							//System.out.println("Elimino Associazione: PK_CIVI="+civi.getPkCivico()+ " e PK_UIU="+civi.getIdImm());
						}
					}
				}
			}
			
			log.debug("CTL-BONCIV Finito!:"+new Date().getTime() );
			conn.commit();
			
			
		
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error(e1);
			}
			
			log.error("Errore: ", e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} 
		finally {
			try {
				
				DbUtils.close(conn);
			} catch (SQLException e) {
				log.error(e);
			}
		}	
		
		//System.out.println("Regola Bonifica CIvici SIT");
		
		return (new ApplicationAck("BONIFICA CIVICI SIT - ESECUZIONE OK"));
	}
	

//	private void createTabellaAppoBonificaCivicoSIT(Connection con){
//		Statement st = null;
//		try {
//			st = con.createStatement();
//			String sql = SQL_CREATE_APPO_BONIFICA_CIVICO_SIT;
//			log.debug(SQL_CREATE_APPO_BONIFICA_CIVICO_SIT);
//			st.execute(sql);
//		} catch (SQLException e1) {
//			log.warn("Tabella APPO_BONIFICA_CIVICO_SIT esiste già. ");
//		}
//		finally {
//			try {
//				if (st!=null)
//					st.close();
//			} catch (SQLException e1) {
//			}			
//		}
//		
//	}
	
//	private boolean insertAppoBonificaCivicoSIT(CivicoAppo civicoSit,CivicoAppo civicoCat, Connection conn){
//		PreparedStatement pst=null;
//		boolean insertOK = false;
//		try {
//			String sql = SQL_INSERT_APPO_BONIFICA_CIVICO_SIT;
//			log.debug(SQL_INSERT_APPO_BONIFICA_CIVICO_SIT);
//			
//			pst = conn.prepareStatement(sql);
//			
//			/*"COD_NAZIONALE     VARCHAR2 (10 BYTE)," 1
//			+ "FOGLIO            NUMBER (4)," 2
//			+ "PARTICELLA        CHAR (5 BYTE)," 3
//			+ "SUB               VARCHAR2 (3 BYTE)," 4
//			+ "UNIMM              NUMBER (5)," 5
//			+ "SEDIME_CAT        VARCHAR2 (50 Byte)," 6
//			+ "INDIRIZZO_CAT     VARCHAR2 (50 Byte)," 7
//			+ "CIVICO_CAT        VARCHAR2 (100 BYTE)," 8
//			+ "PKID_UIU          VARCHAR2 (100 BYTE)," 9
//			+ "SEDIME_SIT        VARCHAR2 (30 BYTE)," 10
//			+ "INDIRIZZO_SIT   	 VARCHAR2 (200 BYTE)," 11
//			+ "CIVICO_SIT        VARCHAR2 (100 BYTE)," 12
//			+ "PK_CIVICO         NUMBER (9)," 13
//			+ "PK_INDIRIZZO		 NUMBER (9)  14  */
//			
//			pst.setString(1, civicoCat.getCodNazionale());
//			pst.setInt(2, 	 civicoCat.getFoglio()!=null&&!civicoCat.getFoglio().trim().equalsIgnoreCase("")?Integer.parseInt(civicoCat.getFoglio().trim()):null);
//			pst.setString(3, civicoCat.getParticella()!=null?civicoCat.getParticella().trim():null);
//			pst.setString(4, "-");
//			pst.setInt(5, civicoCat.getSub()!=null&&!civicoCat.getSub().trim().equalsIgnoreCase("")?Integer.parseInt(civicoCat.getSub().trim()):null);
//			pst.setString(6, civicoCat.getSedime());
//			pst.setString(7, civicoCat.getIndirizzo());
//			pst.setString(8, civicoCat.getCivico());
//			pst.setString(9, civicoSit.getIdImm());
//			pst.setString(10, civicoSit.getSedime());
//			pst.setString(11, civicoSit.getIndirizzo());
//			pst.setString(12, civicoSit.getCivico());
//			pst.setBigDecimal(13, new BigDecimal(civicoSit.getPkCivico().trim()));
//			pst.setBigDecimal(14, new BigDecimal(civicoSit.getPkStrada().trim()));
//			
//			pst.executeUpdate();
//			
//			insertOK = true;
//			
//		} catch (SQLException e1) {
//			log.warn("Eccezione in INSERT_APPO_BONIFICA_CIVICO_SIT. ");
//		}
//		finally {
//			try {
//				if (pst!=null)
//					pst.close();
//			} catch (SQLException e1) {
//			}			
//		}
//		
//		return insertOK;
//		
//	}
	

	
}

