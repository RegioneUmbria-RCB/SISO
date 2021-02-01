package it.webred.ct.data.access.basic.pgt.dao;

import it.webred.ct.data.access.basic.pgt.PGTService;
import it.webred.ct.data.access.basic.pgt.dto.DatoPgtDTO;
import it.webred.ct.data.access.basic.pgt.dto.RicercaPgtDTO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class Test {	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String driver="oracle.jdbc.driver.OracleDriver";
		String nomeUtente="DIOGENE_F704";
		String pwdUtente="DIOGENE_F704";
		String url="dbc:oracle:thin:@roma:1521:dbcat";
		PreparedStatement ps = null;
	    Connection conn = null;
	    ResultSet rs = null;
		try {
			Class.forName(driver);
			conn= DriverManager.getConnection(url, nomeUtente, pwdUtente);
			if (conn == null) {
				System.out.print("connessione ko ");
			}else {
				String sql = "SELECT FOGLIO, CAST (PARTICELLA AS VARCHAR2 (5)) AS particella FROM SITIPART WHERE FOGLIO=1"; 
		        //sql = "select * from sit_ente";
				ps = conn.prepareStatement(sql);
				System.out.print("sql da eseguire:  " + sql);
		        rs = ps.executeQuery();
		        if (rs != null) {
		        	 while (rs.next()) {
		        		 System.out.println("foglio:" + rs.getInt("foglio"));
		        		 System.out.println("particella:" + rs.getString("particella"));
		        	 }
		        }
			}
		}catch(SQLException e){
			System.out.print("errore sql: " + e.getMessage());
		}catch(ClassNotFoundException cnf){
			System.out.print("errore CNF");
		}	
		finally
	    {
	    	try { if (rs != null) rs.close(); } catch (Exception e) {}
	    	try { if (ps != null) ps.close(); } catch (Exception e) {}
	    	try { if (conn != null) conn.close(); } catch (Exception e) {}
	    }    
	}	
	
	public static void mainDAO(String[] args) {
		// TODO Auto-generated method stub
		String driver="oracle.jdbc.driver.OracleDriver";
		String nomeUtente="DIOGENE_F704";
		String pwdUtente="DIOGENE_F704";
		String url="dbc:oracle:thin:@roma:1521:dbcat";
		PreparedStatement ps = null;
	    Connection conn = null;
	    ResultSet rs = null;
		try {
			Class.forName(driver);
			conn= DriverManager.getConnection(url, nomeUtente, pwdUtente);
			if (conn == null) {
				System.out.print("connessione ko ");
			}else {
				String sql = "SELECT L.* FROM  (SELECT CODICE, DESCRIZIONE, NOME, CLASSE, FUNZIONI, CIRCOSCRIZ, AREA, SHAPE  FROM siti_cat_pgT_amb_strA) L, SITIPART S " +
						"	 WHERE SDO_RELATE (L.SHAPE, S.SHAPE,'MASK=ANYINTERACT') = 'TRUE' "+
						"	AND FOGLIO=8 AND PARTICELLA='00044'";
		        //sql = "select * from sit_ente";
				ps = conn.prepareStatement(sql);
				System.out.print("sql da eseguire:  " + sql);
		        rs = ps.executeQuery();
		        Hashtable<String,Object> ht = new Hashtable<String,Object>();
				List<DatoPgtDTO> listaDati = new ArrayList<DatoPgtDTO> ();
				List listaValori=new ArrayList();
		        if (rs!=null)  {
		        	ResultSetMetaData rsmd = rs.getMetaData();
			        int numTotCols = rsmd.getColumnCount();
			       	DatoPgtDTO ele=null;
		        	for (int i=1; i<=numTotCols ; i++ ) {
		      		   ele = new DatoPgtDTO();
			    	   ele.setNomeDato(rsmd.getColumnName(i));
			    	   ele.setTipoOracleDato(rsmd.getColumnTypeName(i));
			    	   ele.setTipoJavaDato(rsmd.getColumnClassName(i));
			    	   listaDati.add(ele);
			    	}
		        	Object valore = null;
		        	List<Object> rigaValori=null;
		 	        while (rs.next()) {
			           	rigaValori= new ArrayList<Object>();
			        	for (int i=1; i<=numTotCols ; i++ ) {
			        		valore = new Object();
			        		if (rs.getObject(i) != null)
			        			valore = rs.getObject(i) ;
			        		rigaValori.add(valore);
			        	}
			        	listaValori.add(rigaValori);
			        }
		 	        ht.put(PGTService.KEY_HASH_COLONNE, listaDati);
		 	        ht.put(PGTService.KEY_HASH_VALORI, listaValori);
			        stampaHT(ht);
		        }else
		        	System.out.print("rs null" );
			}
		}catch(SQLException e){
			System.out.print("errore sql: " + e.getMessage());
		}catch(ClassNotFoundException cnf){
			System.out.print("errore CNF");
		}	
		finally
	    {
	    	try { if (rs != null) rs.close(); } catch (Exception e) {}
	    	try { if (ps != null) ps.close(); } catch (Exception e) {}
	    	try { if (conn != null) conn.close(); } catch (Exception e) {}
	    }    

	}
	
	public static void mainXXX(String[] args) {
		String data= "01/12/2006";
		System.out.println(data.substring(6, 10)+data.substring(3, 5));
		
		/*
		Integer integ = new Integer(3);
		BigDecimal bigD= new BigDecimal(integ);
		float flP =3.5f; 
		Float fl = new Float(integ);
		Number numInt = (Number)integ; 
		Number numBigD = (Number)bigD; 
		Number numFl = (Number)fl; 
		Float flO = new Float(flP);
		System.out.print("Integer " + numInt.doubleValue());
		System.out.print("BigDecimal " + numBigD.doubleValue());
		System.out.print("Float " + numFl.doubleValue());
		System.out.print("Float p " + ((Number)(flO)).doubleValue());
		
		System.out.print("Integer str" + numInt.toString());
		System.out.print("BigDecimal str" + numBigD.toString());
		System.out.print("Float str" + numFl.toString());
		System.out.print("Float p str" + ((Number)(flO)).toString());
		
		/*
		String str = "44";
	    String mask = "00000";		    
	    String z = mask.substring(0 , mask.length() - str.length()) + str;
	    System.out.println(z);
	   */
		/*
		BigDecimal num= new BigDecimal(41);
		System.out.println("num: " + num);
		float f = 0.8f;
		BigDecimal fatt = new BigDecimal(f);
		System.out.println("fatt: " + fatt);
		BigDecimal num1 = num.multiply(fatt);
		System.out.println("res: " + num1);
		 */
	}
	/**
	 * @param args
	 */
	public static void mainOLD(String[] args) {
		// TODO Auto-generated method stub
		String driver="oracle.jdbc.driver.OracleDriver";
		String nomeUtente="DIOGENE_F704";
		String pwdUtente="DIOGENE_F704";
		String url="dbc:oracle:thin:@roma:1521:dbcat";
		PreparedStatement ps = null;
	    Connection conn = null;
	    ResultSet rs = null;
		try {
			Class.forName(driver);
			conn= DriverManager.getConnection(url, nomeUtente, pwdUtente);
			if (conn == null) {
				System.out.print("connessione ko ");
			}else {
				String sql = "SELECT L.* FROM  (SELECT CODICE, DESCRIZIONE, NOME, CLASSE, FUNZIONI, CIRCOSCRIZ, AREA, SHAPE  FROM siti_cat_pgT_amb_strA) L, SITIPART S " +
						"	 WHERE SDO_RELATE (L.SHAPE, S.SHAPE,'MASK=ANYINTERACT') = 'TRUE' "+
						"	AND FOGLIO=8 AND PARTICELLA='00044'";
		        //sql = "select * from sit_ente";
				ps = conn.prepareStatement(sql);
		        rs = ps.executeQuery();
		        if (rs!=null)  {
			        ResultSetMetaData rsmd = rs.getMetaData();
			        int numTotCols = rsmd.getColumnCount();
			        System.out.print("NUMERO COLONNE: " + numTotCols);
			        int numCols=-1;
		        	for (int i=1; i<=numTotCols ; i++ ) {
		        		System.out.println("COLONNA: " + rsmd.getColumnName(i) + "( " + i + ")");
		        		System.out.println("TIPO ORACLE: " + rsmd.getColumnTypeName(i));
		        		System.out.println("CLASSE JAVA: " + rsmd.getColumnClassName(i));
		    	    }
		        	int j =1;
		        	while(rs.next()) {
		        		System.out.println("--->RECORD  N. " +j);
		        		for (int i=1; i<=numTotCols ; i++ ) {
		        			if (rs.getObject(i) != null)	{
		        				System.out.println("VALORE COLONNA(" + i+"): " + rs.getObject(i).toString());	
		        				//if (rsmd.getColumnClassName(i).equals("java.math.BigDecimal")) {
		        				if (rsmd.getColumnTypeName(i).toLowerCase().contains("number")) {
		        					BigDecimal num = (BigDecimal)rs.getObject(i);
		        					String valFormatted= DFEURO.format(num);
		        					System.out.println("--FORMATTATA: " + valFormatted); 
		        					
		        				}
		        			}
		        				 
		        		}
		        	j++;	
		        	}
			       
		        }else
		        	System.out.print("rs null" );
			}
		}catch(SQLException e){
			System.out.print("errore sql: " + e.getMessage());
		}catch(ClassNotFoundException cnf){
			System.out.print("errore CNF");
		}	
		finally
	    {
	    	try { if (rs != null) rs.close(); } catch (Exception e) {}
	    	try { if (ps != null) ps.close(); } catch (Exception e) {}
	    	try { if (conn != null) conn.close(); } catch (Exception e) {}
	    }    

	}
	public static final DecimalFormat DFEURO = new DecimalFormat();
	static {
			DFEURO.setGroupingUsed(true);
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator(',');
			dfs.setGroupingSeparator('.');
			DFEURO.setDecimalFormatSymbols(dfs);
			DFEURO.setMinimumFractionDigits(2);
	}	
	public static void stampaHT(Hashtable<String,Object> ht ) {
		List<DatoPgtDTO> listaDati =(List<DatoPgtDTO>) ht.get(PGTService.KEY_HASH_COLONNE);
		List<List<Object>> listaValori = (List<List<Object>>)ht.get(PGTService.KEY_HASH_VALORI);
		System.out.println("-- COLONNE --");
		int i =1;
		for (DatoPgtDTO dato: listaDati) {
			System.out.println("COL-" + i + ": " +dato.getNomeDato());
			i++;
		}
		i =1;
		System.out.println("-- VALORI --");
		for(List<Object> riga: listaValori) {
			System.out.println("RIGA NUM: " + i );
			for (int j=0; j<riga.size(); j++) {
				System.out.println("VAL-" + j + "-" + riga.get(j));
			}
		}
	}
}
