package it.webred.rulengine.brick.elab.bonificaCivici;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

/**
 * 
 * @author M.B.
 * Classe Creata per essere esguita una tantum per creare tabella di associazione tra viario CAT e viario SIT
 * in cui vengono riportati i vari livelli di associazione.
 * Viene creata qui per coerenza con l'argomento dei civici, 
 * in quanto l'associazione del viario risulta preliminare alla verifica della corretta 
 * associazione dei civici alle varie UIU. Ad oggi non è pensata per essere una regola che possa essere lanciata 
 * periodicamente in quanto a posteriori di questa prima associazione, deve essere effettuato un ulteriore passaggio
 * "a mano" per rifinire e correggere ulteriori associazioni che l'algoritmo non riesce ad individuare.
 */

public class AlgoritmoAssociaViarioCatSit {

	public AlgoritmoAssociaViarioCatSit() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final Logger log = Logger.getLogger(AlgoritmoAssociaViarioCatSit.class.getName());
		
		String SQL_DROP_TABLE_TEMP = "DROP TABLE JOIN_VIE_CAT_SIT";
		
		//String SQL_CREATE_TABLE_JOIN_VIE_CAT_SIT = "CREATE TABLE JOIN_VIE_CAT_SIT AS SELECT DISTINCT ind FROM LOAD_CAT_UIU_IND_MILANO";
		String SQL_CREATE_TABLE_JOIN_VIE_CAT_SIT = "CREATE TABLE JOIN_VIE_CAT_SIT AS SELECT DISTINCT ind.ind FROM LOAD_CAT_UIU_IND IND WHERE IND.TODELETE ='0' "
				+ "AND IND.SEQ = (SELECT MAX(SEQ) FROM load_cat_uiu_ind IND21 "
				+ 					"WHERE IND21.ID_IMM = IND.ID_IMM "
				+ 					"AND IND21.CODI_FISC_LUNA = IND.CODI_FISC_LUNA "
				+ 					"AND IND21.PROGRESSIVO = IND.PROGRESSIVO "
				+ 					"AND ind21.SEZIONE = ind.SEZIONE "
				+ 					"AND ind21.todelete ='0') "
				+ "AND IND.PROGRESSIVO = (SELECT MAX(PROGRESSIVO) FROM load_cat_uiu_ind IND22 "
				+ 							"WHERE IND22.ID_IMM = IND.ID_IMM "
				+ 						  	"AND IND22.CODI_FISC_LUNA = IND.CODI_FISC_LUNA "
				+ 							"AND ind22.SEZIONE = ind.SEZIONE "
				+ 							"AND ind22.todelete ='0' ) "
				+ "AND IND.IND IS NOT NULL";   
		
		
		
		String SQL_ALTER_TABLE_JOIN_001 = "ALTER TABLE JOIN_VIE_CAT_SIT ADD IND_VIARIO VARCHAR2(255) ";
		
		String SQL_ALTER_TABLE_JOIN_002 = "ALTER TABLE JOIN_VIE_CAT_SIT ADD TIPO_MATCH VARCHAR2(50) ";

//		String SQL_SELECT_IND_UGUALI = "SELECT DISTINCT STR.NOME, CAV.IND FROM SITIDSTR_MILANO STR,JOIN_VIE_CAT_SIT CAV "
//		+ "WHERE TRIM(STR.NOME) = TRIM(CAV.IND) ";
		String SQL_SELECT_IND_UGUALI = "SELECT DISTINCT STR.NOME, CAV.IND FROM SITIDSTR STR,JOIN_VIE_CAT_SIT CAV "
										+ "WHERE TRIM(STR.NOME) = TRIM(CAV.IND) "
										+ "AND STR.DATA_FINE_VAL = TO_DATE('99991231', 'yyyymmdd')";

		
		String SQL_INSERT_MOBE_001_IND_UGUALI = "UPDATE JOIN_VIE_CAT_SIT SET IND_VIARIO = ?,TIPO_MATCH = ?  WHERE IND = ? ";
		
//		String SQL_SELECT_IND_NO_UGUALI ="SELECT DISTINCT TRIM(NOME) AS NOME FROM SITIDSTR STR WHERE STR.NOME NOT IN ("
//											+ "SELECT DISTINCT STR1.NOME FROM SITIDSTR STR1, JOIN_VIE_CAT_SIT MB "
//											+ "WHERE TRIM(STR1.NOME) = TRIM(MB.IND) "
//											+ "AND STR1.DATA_FINE_VAL = TO_DATE('99991231', 'yyyymmdd')) "
//											+ "AND STR.DATA_FINE_VAL = TO_DATE('99991231', 'yyyymmdd')"
//										+ "ORDER BY NOME";
		String SQL_SELECT_IND_NO_UGUALI ="SELECT DISTINCT IND FROM JOIN_VIE_CAT_SIT WHERE IND_VIARIO IS NULL ORDER BY IND ASC";
		
//		String SQL_SELECT_DISTINCT_NAME_VIE = "SELECT DISTINCT TRIM(NOME) as NOME FROM SITIDSTR_MILANO WHERE NOME IS NOT NULL "
//		+ "ORDER BY NOME ASC";		
		String SQL_SELECT_DISTINCT_NAME_VIE = "SELECT DISTINCT TRIM(STR.NOME) as NOME FROM SITIDSTR STR "
				+ "WHERE STR.DATA_FINE_VAL = TO_DATE('99991231', 'yyyymmdd')";

		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Statement stm = null;
		
		ArrayList<String> elencoVieRiferimento = new ArrayList<String>();
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//MILANO: "jdbc:oracle:thin:@exadbe01-scan:1521:dbcat", "DIOGENE_2", "higit01w2"
			conn = DriverManager.getConnection("jdbc:oracle:thin:@exadbe01-scan:1521:dbcat", "DIOGENE_2", "higit01w2");
			
			//SVILUPPO: "jdbc:oracle:thin:@HIWEB-SVIL-DB01:1521:DBCAT", "DIOGENE_F704", "DIOGENE_F704"
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@HIWEB-SVIL-DB01:1521:dbcat", "DIOGENE_F704", "DIOGENE_F704");
			
			stm = conn.createStatement();
			try{
				stm.execute(SQL_DROP_TABLE_TEMP);
			}catch(Exception ee ){
				//System.out.println("Eccezione SQL_DROP_TABLE_TEMP:"+ee);
				log.error("Eccezione SQL_DROP_TABLE_TEMP:"+ee);
			}
			
			stm.execute(SQL_CREATE_TABLE_JOIN_VIE_CAT_SIT);
			stm.execute(SQL_ALTER_TABLE_JOIN_001);//COLONNA PER IND VIARIO
			stm.execute(SQL_ALTER_TABLE_JOIN_002);//COLONNA PER TIPOLOGIA MATCH
			
			//System.out.println("RECUPERO VIE PERFETTAMENTE UGUALI="+SQL_SELECT_IND_UGUALI);
			
			rs = stm.executeQuery(SQL_SELECT_IND_UGUALI);
			
			while (rs.next()) {
				String via_sit = rs.getString("NOME");
				String via_cat = rs.getString("IND");
				
				pstmt = conn.prepareStatement(SQL_INSERT_MOBE_001_IND_UGUALI);
				pstmt.setString(1, via_sit);
				pstmt.setString(2, "UGUALI");
				pstmt.setString(3, via_cat);
				
				int eseguito = pstmt.executeUpdate();
				
				pstmt.close();
			}
			
			rs.close();
			stm.close();
			
			//PREPARO ELENCO DEL VIARIO CHE UTILIZZERO' PER I CONFRONTI SENZA ACCEDERE NUOVAMENTE AL DB
			Statement stmElencoVie = conn.createStatement();
			ResultSet rsElencoVie = stmElencoVie.executeQuery(SQL_SELECT_DISTINCT_NAME_VIE);
			while (rsElencoVie.next()) {
				elencoVieRiferimento.add(rsElencoVie.getString("NOME"));
			}
			rsElencoVie.close();
			stmElencoVie.close();
			
			//LEGGO GLI INDIRIZZI CHE ANCORA NON HANNO TROVATO MATCH
			stm = conn.createStatement();
			System.out.println("RECUPERO INDIRIZZI CHE NON SONO PERFETTAMENTE UGUALI:"+SQL_SELECT_IND_NO_UGUALI);
			rs = stm.executeQuery(SQL_SELECT_IND_NO_UGUALI);
			while (rs.next()) {
				String nomeDaAssociareCat = rs.getString("IND");
				String nomeDaAssociareCatNorm = AlgoritmoAssociaViarioCatSit.normalizzaStringa(nomeDaAssociareCat); 
	
				String nomeTrovatoSit = AlgoritmoAssociaViarioCatSit.controllaParolePresenti(nomeDaAssociareCatNorm, elencoVieRiferimento);
				
				if (nomeTrovatoSit != null && nomeTrovatoSit.trim().length()>0){
					pstmt = conn.prepareStatement(SQL_INSERT_MOBE_001_IND_UGUALI);
					pstmt.setString(1, nomeTrovatoSit);
					pstmt.setString(2, "PAROLE CAT TROVATE TUTTE E LUNG MAX <> 1");
					pstmt.setString(3, nomeDaAssociareCat);
					
					int eseguito = pstmt.executeUpdate();
					
					pstmt.close();
				}
				
			}
			
			rs.close();
			stm.close();
			
			
			//leggo gli indirizzi che ancora non sono stati associati
			stm = conn.createStatement();
			//System.out.println("SQL_SELECT_IND_NO_UGUALI - SECONDO GIRO:"+SQL_SELECT_IND_NO_UGUALI);
			rs = stm.executeQuery(SQL_SELECT_IND_NO_UGUALI);
			while (rs.next()) {
				String nomeDaAssociareCat = rs.getString("IND");
				String nomeDaAssociareCatNorm = AlgoritmoAssociaViarioCatSit.normalizzaStringa(nomeDaAssociareCat); 

				String nomeTrovatoSit = AlgoritmoAssociaViarioCatSit.controllaParolePresentiMenoUno(nomeDaAssociareCatNorm, elencoVieRiferimento);
				
				if (nomeTrovatoSit != null && nomeTrovatoSit.trim().length()>0){
					pstmt = conn.prepareStatement(SQL_INSERT_MOBE_001_IND_UGUALI);
					pstmt.setString(1, nomeTrovatoSit);
					pstmt.setString(2, "PAROLE CAT TROVATE TUTTE MENO UNA - ALMENO 2");
					pstmt.setString(3, nomeDaAssociareCat);
					
					int eseguito = pstmt.executeUpdate();
					
					pstmt.close();
				}
				
			}
			
			rs.close();
			stm.close();
			
			
			//leggo gli indirizzi che ancora non sono stati associati e analizzo quelli conposti da 2 parole
			stm = conn.createStatement();
			//System.out.println("SQL_SELECT_IND_NO_UGUALI - TERZO GIRO:"+SQL_SELECT_IND_NO_UGUALI);
			rs = stm.executeQuery(SQL_SELECT_IND_NO_UGUALI);
			while (rs.next()) {
				String nomeDaAssociareCat = rs.getString("IND");
				String nomeDaAssociareCatNorm = AlgoritmoAssociaViarioCatSit.normalizzaStringa(nomeDaAssociareCat); 

				String nomeTrovatoSit = AlgoritmoAssociaViarioCatSit.controllaParolePresentiSeDue(nomeDaAssociareCatNorm, elencoVieRiferimento);
				
				if (nomeTrovatoSit != null && nomeTrovatoSit.trim().length()>0){
					pstmt = conn.prepareStatement(SQL_INSERT_MOBE_001_IND_UGUALI);
					pstmt.setString(1, nomeTrovatoSit);
					pstmt.setString(2, "PAROLE CAT 2:TROVATA UNA E INIZIALE SECONDA");
					pstmt.setString(3, nomeDaAssociareCat);
					
					int eseguito = pstmt.executeUpdate();
					
					pstmt.close();
				}
				
			}
			
			rs.close();
			stm.close();
			
			//se viene lanciata come jar eseguibile per sapere che l'elaborazione è terminata
			//JOptionPane.showMessageDialog(null, "ELABORAZIONE TERMINATA CORRETTAMENTE");
			
			
			
		}
		catch (Exception e) {
			//System.out.println("errore 1:"+e);
			//JOptionPane.showMessageDialog(null, "errore 1:"+e);
			log.error("errore 1:"+e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				//System.out.println("errore 1b:"+e1);
				//JOptionPane.showMessageDialog(null, "errore 1b:"+e1);
				log.error("errore 1b:"+e1);
			}
				
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				//System.out.println("errore 2");
				//JOptionPane.showMessageDialog(null, "errore 2:"+e);
				log.error("errore 2:"+e);
			}
		}			
	}
	
	public static String controllaParolePresenti(String nomeViaCat, ArrayList<String> elencoVieRiferimento) 
	{
		
		String[] splitViaCat = null;
		int numParoleViaCat = 0;
		String nomeViaRitorno = null;
		
		try{
			splitViaCat = nomeViaCat.split(" ");
			numParoleViaCat = splitViaCat.length;
			
			for (Iterator<String> itVieRif = elencoVieRiferimento.iterator(); itVieRif.hasNext();) {
				String appoViaRif = (String) itVieRif.next();
				
				String appoViaRifNorm  = AlgoritmoAssociaViarioCatSit.normalizzaStringa(appoViaRif);
				
				String[] splitViaRif = appoViaRifNorm.split(" ");	
				int numParoleViaRif = splitViaRif.length;
				int contaMatch = 0 ;
				for (int i = 0; i < splitViaCat.length; i++) {
					String app_Cat = splitViaCat[i];
					for (int j = 0; j < splitViaRif.length; j++) {
						if (app_Cat.trim().equalsIgnoreCase(splitViaRif[j])){
							contaMatch ++;
							j= splitViaRif.length;
						}
					}
				}
				
				if (contaMatch >0 && contaMatch == numParoleViaCat && numParoleViaCat>= (numParoleViaRif-1) ){
					//tutte le parole hanno trovato corrispondenza (erano in ordine diverso e magari meno della via di riferimento (diff -1 ))
					nomeViaRitorno = appoViaRif;
					break;
				}
				
			}
			
		}catch(Exception e ){
			System.out.println("Errore in Confronto Parole Presenti "+e.getMessage());
		}
		
		return nomeViaRitorno;
		
	}
	
	public static String normalizzaStringa(String stringa){
		
		String ritorno = null;
		
		if (stringa!= null && stringa.startsWith("S")) {
			if (stringa.startsWith("SANTA "))
				ritorno = stringa.replace("SANTA ", "S.");
			else if (stringa.startsWith("SANTO "))
				ritorno = stringa.replace("SANTO ", "S.");	
			else if (stringa.startsWith("SANT "))
				ritorno = stringa.replace("SANT ", "S.");
			else if (stringa.startsWith("SANT'"))
				ritorno = stringa.replace("SANT'", "S.");
			else if (stringa.startsWith("SAN "))
				ritorno = stringa.replace("SAN ", "S.");
			else
				ritorno = stringa;
		}else
			ritorno = stringa;
		
		if (ritorno != null){
			ritorno = ritorno.replace((char)39,(char)32);//'
			ritorno = ritorno.replace((char)96,(char)32);//`
			ritorno = ritorno.replace((char)40,(char)32); //(
			ritorno = ritorno.replace((char)41,(char)32); //)
			ritorno = ritorno.replace((char)46,(char)32); //.
		}

		return ritorno;
	}
	
	public static String controllaParolePresentiMenoUno(String nomeViaCat, ArrayList<String> elencoVieRiferimento) 
	{
		
		String[] splitViaCat = null;
		int numParoleViaCat = 0;
		String nomeViaRitorno = null;
		
		try{
			splitViaCat = nomeViaCat.split(" ");
			numParoleViaCat = splitViaCat.length;
			
			for (Iterator<String> itVieRif = elencoVieRiferimento.iterator(); itVieRif.hasNext();) {
				String appoViaRif = (String) itVieRif.next();
				
				String appoViaRifNorm  = AlgoritmoAssociaViarioCatSit.normalizzaStringa(appoViaRif);
				
				String[] splitViaRif = appoViaRifNorm.split(" ");	
				int numParoleViaRif = splitViaRif.length;
				int contaMatch = 0 ;
				int contaSpazi = 0;
				for (int i = 0; i < splitViaCat.length; i++) {
					String app_Cat = splitViaCat[i];
					if (!app_Cat.trim().equalsIgnoreCase("")){
						for (int j = 0; j < splitViaRif.length; j++) {
							if (app_Cat.trim().equalsIgnoreCase(splitViaRif[j])){
								contaMatch ++;
								j= splitViaRif.length;
							}
						}
					}else{
						contaSpazi++;
					}
				}
				
				//if ( contaMatch >0 && contaMatch >= (numParoleViaCat-1) && numParoleViaCat>2 ){
				if (contaMatch >1 && contaMatch >= (numParoleViaCat-contaSpazi-1) && numParoleViaCat>2 ){
					//tutte le parole hanno trovato corrispondenza tranne una)
					nomeViaRitorno = appoViaRif;
					break;
				}
				
			}
			
		}catch(Exception e ){
			System.out.println("Errore in controllaParolePresentiMenoUno: "+e.getMessage());
		}
		
		return nomeViaRitorno;
		
	}
	
	public static String controllaParolePresentiSeDue(String nomeViaCat, ArrayList<String> elencoVieRiferimento) 
	{
		
		String[] splitViaCat = null;
		int numParoleViaCat = 0;
		String nomeViaRitorno = null;
		
		try{
			splitViaCat = nomeViaCat.split(" ");
			numParoleViaCat = splitViaCat.length;
			
			int contaSpazi = 0;
			for (int i = 0; i < splitViaCat.length; i++) {
				String app_Cat = splitViaCat[i];
				if (app_Cat.trim().equalsIgnoreCase(""))
					contaSpazi++;
			}
			
			//if (numParoleViaCat == 2){
			if (numParoleViaCat-contaSpazi == 2){
			
				for (Iterator<String> itVieRif = elencoVieRiferimento.iterator(); itVieRif.hasNext();) {
					String appoViaRif = (String) itVieRif.next();
					
					String appoViaRifNorm  = AlgoritmoAssociaViarioCatSit.normalizzaStringa(appoViaRif);
					
					String[] splitViaRif = appoViaRifNorm.split(" ");	
					int contaSpaziRif = 0;
					for (int i = 0; i < splitViaRif.length; i++) {
						String app_Rif = splitViaRif[i];
						if (app_Rif.trim().equalsIgnoreCase(""))
							contaSpaziRif++;
					}
					
					int numParoleViaRif = splitViaRif.length;
					
					if (numParoleViaRif-contaSpaziRif == 2){
						boolean contaMatchC1 = false;
						boolean contaFirstC1 = false;
						
						boolean contaMatchC2 = false;
						boolean contaFirstC2 = false;
						
						for (int i = 0; i < splitViaCat.length; i++) {
							String app_Cat = splitViaCat[i];
							if (!app_Cat.trim().equalsIgnoreCase("")){
								for (int j = 0; j < splitViaRif.length; j++) {
									if(!splitViaRif[j].trim().equalsIgnoreCase("")){
										if (app_Cat.trim().equalsIgnoreCase(splitViaRif[j])){
											if (i==0)
												contaMatchC1 =true;
											else
												contaMatchC2 =true;
										}else if (app_Cat.trim().substring(0,1).equalsIgnoreCase(splitViaRif[j].trim().substring(0,1)) ){
											if (i==0)
												contaFirstC1 =true;
											else
												contaFirstC2 =true;
										}
									}
								}
							}
						}
						
						if ((contaMatchC1 && contaFirstC2) ||(contaFirstC1 && contaMatchC2)){
							//una corrisponde e l'altra inizia uguale)
							nomeViaRitorno = appoViaRif;
							break;
						}
					}
				}
			}	
			
		}catch(Exception e ){
			System.out.println("Errore in controllaParolePresentiSeDue: "+e.getMessage());
		}
		
		return nomeViaRitorno;
		
	}

}
