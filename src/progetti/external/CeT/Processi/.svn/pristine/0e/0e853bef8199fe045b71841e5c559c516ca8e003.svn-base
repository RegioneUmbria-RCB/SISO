package it.webred.rulengine.diagnostics;

import it.webred.rulengine.Context;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.diagnostics.bean.DiagnosticConfigBean;
import it.webred.utils.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class DiaAccessiIsoleEcologiche extends ElaboraDiagnosticsNonStandard {

	public DiaAccessiIsoleEcologiche(Connection connPar, Context ctxPar, List<RRuleParamIn> paramsPar) {
		super(connPar, ctxPar, paramsPar);
		log = Logger.getLogger(DiaAccessiIsoleEcologiche.class.getName());		
	}
	
	@Override
	protected void ElaborazioneNonStandard(DiagnosticConfigBean diaConfig, long idTestata) throws Exception {

		log.info("[DiaAccessiIsoleEcologiche] - Invoke class DiaAccessiIsoleEcologiche ");
		super.ElaborazioneNonStandard(diaConfig, idTestata);
				
		String query = "{call DIA_ACCESSI_ISOLA_ECOLOGICA(?)}";
		CallableStatement cstmt = getConn().prepareCall(query);		
		cstmt.setLong("idTestata", idTestata);     // Set idTestata.
		cstmt.execute();
		
		DiaAccessiIsoleEcologicheEnv env = new DiaAccessiIsoleEcologicheEnv( (String)super.getCtx().get("connessione") , super.getCtx());
		String diaDirExpFiles = env.getPercorsoFiles();
		String diaFtpUrl = env.getDiaFtpUrl();
		log.info("[DiaAccessiIsoleEcologiche] - Parametri Recuperati dalla AM_KEY_VALUE_EXT: dia.exp.dir.files = " + diaDirExpFiles + "; dia.ftp.url = " + diaFtpUrl);
		
		if (diaFtpUrl != null && !diaFtpUrl.trim().equalsIgnoreCase("")){
			//Eseguo esportazione e invio FTP verso il server delcliente
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmm");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	        Date resultdate = new Date(System.currentTimeMillis());
	        //System.out.println(sdf.format(resultdate));
	        
			String fileName = sdf.format(resultdate) + "_" + super.getCodBelfioreEnte() + "_ecostazioni.csv";
			String toPath = diaDirExpFiles + fileName;

			log.info("[DiaAccessiIsoleEcologiche] - Inizio Esportazione CSV");
			elabora(getConn(), toPath);
			log.info("[DiaAccessiIsoleEcologiche] - Fine Esportazione CSV");
			
			log.info("[DiaAccessiIsoleEcologiche] - Inizio Invio FTP");
			inviaFtp(diaFtpUrl, toPath, fileName);
			log.info("[DiaAccessiIsoleEcologiche] - Fine Invio FTP");
		}
		
		
		log.info("[DiaAccessiIsoleEcologiche] - Termine");
		
	}//-------------------------------------------------------------------------
	
	private static void inviaFtp(String diaFtpUrl, String toPath, String fileName) {
		//new ftp client
        FTPClient ftp = new FTPClient();
	    try {
	    	 //user:pass@127.0.0.1:21/path
	    	
            String serverAddress = diaFtpUrl.substring(diaFtpUrl.indexOf('@')+1, diaFtpUrl.lastIndexOf(':'));
            log.info("FTP Server Address: " + serverAddress.trim());
            String userId = diaFtpUrl.substring(0, diaFtpUrl.indexOf(':'));
            log.info("FTP Usr: " + userId);
            String password = diaFtpUrl.substring(diaFtpUrl.indexOf(':')+1, diaFtpUrl.lastIndexOf('@'));
            log.info("FTP Pwd: " + password);
            String port = diaFtpUrl.substring(diaFtpUrl.lastIndexOf(':')+1, diaFtpUrl.indexOf('/'));
            log.info("FTP Port: " + port);
            String remoteDirectory = diaFtpUrl.substring(diaFtpUrl.indexOf('/')+1, diaFtpUrl.length());
            log.info("FTP Subdir: " + remoteDirectory);
            
//            String serverAddress = "catapp03.webred.it";
//            String userId = "hiweb";
//            String password = "hired001";

            //try to connect
            ftp.connect(serverAddress.trim());
            ftp.setFileType(FTP.ASCII_FILE_TYPE);
            //login to server
            if(!ftp.login(userId, password)){
                ftp.logout();
            }else{
                int reply = ftp.getReplyCode();
                //FTPReply stores a set of constants for FTP reply codes. 
                if (!FTPReply.isPositiveCompletion(reply)){
                    ftp.disconnect();
                    log.info("Connection to FTP refused: " + diaFtpUrl);
                }else{
                    //enter passive mode
                    ftp.enterLocalPassiveMode();
                    //get system name
                    //System.out.println("Remote system is " + ftp.getSystemType());
                    //change current directory
                    //ftp.changeWorkingDirectory("/" + remoteDirectory);
                    log.info("Current directory is " + ftp.printWorkingDirectory());
                    
                    //get input stream
                    //InputStream input = new FileInputStream("C:/Dati_Diogene/F704/diagnostiche/1403773025955_diaAccessiIsoleEcologiche.csv");
                    InputStream input = new FileInputStream(toPath);
                    //store the file in the remote server
                    ftp.storeFile("/"+remoteDirectory+"/" + fileName, input);
                    //close the stream
                    input.close();
         
                    ftp.logout();
                    ftp.disconnect();

                }

            }
        }
        catch (Exception ex){
        	log.error(ex);
        }finally {
            if(ftp.isConnected()) {
                try {
                  ftp.disconnect();
                } catch(IOException ioe) {
                	log.error(ioe);
                }
              }
        }
        
	}//-------------------------------------------------------------------------
	
	private static void elabora(Connection conn, String toPath) {
		
		
		//BufferedReader fileIn = null;
		PrintWriter fileOut = null;
		
		try{
			fileOut = new PrintWriter(new File(toPath));
			String line = "ID|CODICE|COGNOME_DENOM|NOME|CODICE_FISCALE|BELFIORE|COMUNE|PROV|INDIRIZZO|CIVICO|COD_FAM|INTESTATARIO|TIPO_UTENZA|NUTENZE|";
			
			fileOut.println(line);

			log.info("File separator: " + System.getProperty("file.separator"));
			
			PreparedStatement pst = null;
			ResultSet rs = null;
			//ArrayList<Object[]> alAccIsoEco = new ArrayList<Object[]>();
			try {
				pst = conn.prepareStatement("SELECT ID, CODICE, COGNOME_DENOM, NOME, CODICE_FISCALE, BELFIORE, COMUNE, PROV, INDIRIZZO, CIVICO, COD_FAM, INTESTATARIO, TIPO_UTENZA, NUTENZE FROM DIA_ACCESSI_ISOLE_ECOLOGICHE ");
				//pst.setString(1, codFisc);
				rs = pst.executeQuery();					
				while(rs.next()) {
					String id = !StringUtils.isEmpty(rs.getString("ID"))?rs.getString("ID"):"";
					String codice = !StringUtils.isEmpty(rs.getString("CODICE"))?rs.getString("CODICE"):"";
					String cognomeDenom = !StringUtils.isEmpty(rs.getString("COGNOME_DENOM"))?rs.getString("COGNOME_DENOM"):"";
					String nome = !StringUtils.isEmpty(rs.getString("NOME"))?rs.getString("NOME"):"";
					String codiceFiscale = !StringUtils.isEmpty(rs.getString("CODICE_FISCALE"))?rs.getString("CODICE_FISCALE"):"";
					String belfiore = !StringUtils.isEmpty(rs.getString("BELFIORE"))?rs.getString("BELFIORE"):"";
					String comune = !StringUtils.isEmpty(rs.getString("COMUNE"))?rs.getString("COMUNE"):"";
					String prov = !StringUtils.isEmpty(rs.getString("PROV"))?rs.getString("PROV"):"";
					String indirizzo = !StringUtils.isEmpty(rs.getString("INDIRIZZO"))?rs.getString("INDIRIZZO"):"";
					String civico = !StringUtils.isEmpty(rs.getString("CIVICO"))?rs.getString("CIVICO"):"";
					String codFam = !StringUtils.isEmpty(rs.getString("COD_FAM"))?rs.getString("COD_FAM"):"";
					String intestatario = !StringUtils.isEmpty(rs.getString("INTESTATARIO"))?rs.getString("INTESTATARIO"):"";
					String tipoUtenza = !StringUtils.isEmpty(rs.getString("TIPO_UTENZA"))?rs.getString("TIPO_UTENZA"):"";
					String nutenze = !StringUtils.isEmpty(rs.getString("NUTENZE"))?rs.getString("NUTENZE"):"";
					
					line = id  + "|" + codice + "|" + cognomeDenom + "|" + nome  + "|" + codiceFiscale + "|" + belfiore + "|" + comune + "|" + prov + "|" + indirizzo + "|" + civico + "|" + codFam + "|" + intestatario + "|" + tipoUtenza + "|" + nutenze + "|"; 
					fileOut.println(line);
					
				}
			} catch (Exception e) {
				log.error(e);
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (pst != null) {
						pst.close();
					}
				} catch (Exception e) {
					log.error(e);
				}			
			}
			
			
			
		}catch (Exception e) {
			log.error(e);
		} finally {
			if (fileOut != null) {
				try {
					fileOut.flush();
					fileOut.close();
				} catch (Exception e) {
					log.error(e);
				}				
			}
//			if (fileIn != null) {
//				try {
//					fileIn.close();
//				} catch (Exception e) {
//					log.error(e);
//				}
//			}
		}
	
	}//-------------------------------------------------------------------------
}
