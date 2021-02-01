package it.webred.rulengine.brick.loadDwh.base;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.StringUtils;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.persistence.Query;

public class LoadDemanioBeniDocumenti  extends LoadDemanioBeniBase{
	
	//codice definito nella tabella DM_CONF_DOC_DIR
	private static final String PATH_INVENTAMI = "PATH_INVENTAMI";
	
	public LoadDemanioBeniDocumenti(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}//-------------------------------------------------------------------------

	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn) throws CommandException{
		
		try {
			//numero di parametri espresso nel properties -1 xchè qui nel codice parte da zero
			List<Object> params = this.caricaParametri(3, ctx);

			//RDemanioTerreni tab = (RDemanioTerreni)getTabellaDwhInstance(ctx);
			
			String codice = (String)params.get(0);
			String descrizione = (String)params.get(1);
			String percorso = (String)params.get(2);
			Integer abilitato = (Integer)params.get(3);

			
			if ( codice != null && !codice.trim().equalsIgnoreCase("") && codice.trim().equalsIgnoreCase(PATH_INVENTAMI) ){
				String message = "CODICE: " + codice + " DESCRIZIONE: " + descrizione + " PERCORSO: " + percorso + " ABILITATO: " + abilitato;			
				log.info(message);

				//Imposto flag_rimosso=2 (elaborazione in corso)
				String sql0 = "update DM_D_DOC D SET D.FLG_RIMOSSO=2 WHERE D.DM_CONF_DOC_DIR.CODICE= '"+percorso+"' ";

				//Inserisco nuovi file
				log.debug("Elaborazione documenti percorso: "+percorso);
				
				int numDocNuovi = 0;
				int numDocElaborati = 0;
				int numDocRimossi = 0;
				//Verifica Esistenza percorso base
				File f = new File(percorso);
				if(!f.canRead()){
					log.info("Cartella "+codice+": "+percorso+" non trovata!");
				}else{

					//TODO IMpostare flag_rimoss=1 (file non più presenti in cartella)
					//q = em.createNamedQuery("DmDDoc.updateRimossi");
					String sqlRim = " update DM_D_DOC D "
							+ " SET D.FLG_RIMOSSO = 1 "
							+ " WHERE D.COD_PERCORSO= '?1?' AND D.FLG_RIMOSSO= '2' ";
					
					sqlRim = sqlRim.replace("?1?", codice );

					Statement psRim = conn.createStatement();
					psRim.addBatch(sqlRim);
					int[] risRim = psRim.executeBatch();
					numDocRimossi = risRim[0];
					
					String errLog = this.elaboraCartella(percorso, null, conn);
					
					/*
					 * TODO inserisco il record nella tabella di LOG del caricamento
					 * documenti 
					 */
					errLog = errLog!=null && errLog.length()>0 ? errLog : null;
					String sqlLog = " INSERT INTO DM_CONF_DOC_LOG "
								+ " ( "
								+ " CODICE, DT_ELAB, N_NUOVI, N_RIMOSSI, N_ELABORATI, TXT_LOG "
								+ " )"
								+ " VALUES ("
								+ " '"+codice+"', SYSDATE, '"+numDocNuovi+"', '"+numDocRimossi+"', '"+numDocElaborati+"', TO_CLOB('"+errLog+"') "
								+ " ) ";
					
					Statement psLog = conn.createStatement();
					psLog.addBatch(sqlLog);
					int[] risLog = psLog.executeBatch();
					
					log.debug("Info Elaborazione:"+errLog);
					log.debug("Num. Documenti Nuovi:"+numDocNuovi);
					log.debug("Num. Documenti Rimossi:"+numDocRimossi);
					log.debug("Num. Documenti Elaborati:"+numDocElaborati);
					
	       		   conn.commit();	
					
				}
			}else{
				log.info(PATH_INVENTAMI + " non impostato: posizione dei documenti fascicolo bene non inesrita in DM_CONF_DOC_DIR " );
			}
			
			
			//RDemanioTerreniDao dao = (RDemanioTerreniDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			//boolean salvato = dao.save(ctx.getBelfiore());
			
		} 
		catch (Exception e)
		{
			log.error("LoadDemanioBeniDocumenti",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		String msg = "Acquisizione documenti";
		
		return(new ApplicationAck(msg));

	}//-------------------------------------------------------------------------
		
	private String elaboraCartella(String percorsoBase, Object[] cartella, Connection conn) throws Exception{
		String errLog="";
		SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
		String percorso = ( (String)cartella[1]!=null && !((String)cartella[1]).trim().equalsIgnoreCase("")) ? percorsoBase+File.separatorChar+(String)cartella[1] : percorsoBase;
		File fPercorso = new File(percorso);
		File[] listaFiles = fPercorso.listFiles();
		int numDocElaborati = 0;
		//Elaboro nuovi file
		for(File f : listaFiles){
			if(f.isFile()){
			  try{
				numDocElaborati++;
				int num = updateDocEsistenti(percorsoBase, f, (Long)cartella[0] );
				if(num<=0){

					String nomeTot = f.getName();
					int index = nomeTot.lastIndexOf('.');
					String ext = index>=0 ?  nomeTot.substring(index+1,nomeTot.length()) : "";
					String nome = index>=0 ? nomeTot.substring(0,index) : nomeTot;
					
					String[] dati = nome.split("_");
					if(dati.length>=7){
						String chiave = dati[0];
						if (chiave != null) {
							while (chiave.startsWith("0")) {
								chiave = chiave.substring(1);
							}
						}

						String codMacro = dati [1];
						String progCat = StringUtils.trimLeftChar(dati[2], '0');
						String progDoc = StringUtils.trimLeftChar(dati[3], '0');
						Date dataDa = SDF.parse(dati[4]);
						Date dataA = SDF.parse(dati[5]);
						Date dataMod = SDF.parse(dati[6]);
						String estensione = ext.toLowerCase();
						//BigDecimal flgRimosso = new BigDecimal(0); 
						String nomeFile = nome;
						String nomeFileBase = nome.substring(0,nome.lastIndexOf('_'));
						
						Long beneId = 0l;
						String sql9 = "select ID from DM_B_BENE B where B.CHIAVE= ? ";

						PreparedStatement preparedStatement = conn.prepareStatement( sql9 );
						preparedStatement.setString(1, chiave);
						ResultSet rs9 = preparedStatement .executeQuery();
						while(rs9.next()){
							beneId = rs9.getLong("ID");
						}
						preparedStatement.close();
		       		   //conn.commit();	
						
						String sql1 = " INSERT INTO DM_D_DOC "
								+ " ( ID, DM_D_CARTELLA_ID, "
								+ "MACRO_CATEGORIA, PROG_CATEGORIA, PROG_DOCUMENTO, "
								+ "DATA_DA, DATA_A, DATA_MOD, "
								+ "EXT, FLG_RIMOSSO, "
								+ "NOME_FILE, NOME_FILE_BASE, "
								+ "COD_PERCORSO, DM_B_BENE_ID ) "
								+ " VALUES "
								+ " ( SEQ_DEM.NEXTVAL, "+(Long)cartella[0]+", "
								+ " '"+codMacro+"', '"+progCat+"', '"+progDoc+"', "
								+ " '"+dataDa+"', '"+dataA+"', '"+dataMod+"', "
								+ " '"+estensione+"', "+ new BigDecimal(0) +", "
								+ " '"+nomeFile+"', '"+nomeFileBase+"', "
								+ " '"+percorsoBase+"', " + beneId + " ) ";

//						TODO Controllo esistenza classificazione in DM_CONF_CLASSIFICAZIONE						
//						DmConfClassificazione classe = this.getClassificazione(dati[1], progCat);
//						if(classe==null)
//							errLog+= "Classificazione "+codMacro+"-"+progCat+" non configurata ("+f.getPath()+")\n";
//						doc.setDmConfClassificazione(classe);
						
						Statement ps1 = conn.createStatement();
						ps1.addBatch(sql1);
						int[] ris1 = ps1.executeBatch();
		       		   conn.commit();	
						
					}
				}
			  }catch(Throwable e){
				  String s = "ERRORE elaboraCartella per il file "+f.getName()+" "+e.getMessage();
				  log.error(s);
				  throw new Exception(s,e);
			  }
			}else if(f.isDirectory()){
				String nomeDir = ((String)cartella[1]!=null ? (String)cartella[1]+File.separatorChar : "")+f.getName();
				this.salvaCartella(percorso, nomeDir, "", conn);
				this.elaboraCartella(percorsoBase, cartella, conn);
			}
		}
		
		return errLog;
		
	}//-------------------------------------------------------------------------	
	
	private int updateDocEsistenti(String codicePercorso, File f, Long cartellaId) throws Exception{
		int ris = 0;
		String nome = f.getName();
		int index = nome.lastIndexOf('.');
		String nomeCompleto = nome;
		if(index>=0)
		  nomeCompleto = nome.substring(0,index);
		
		Query q;
		String sql0 = "";
		if(cartellaId != null && codicePercorso != null && !codicePercorso.trim().equalsIgnoreCase("") && nomeCompleto != null && !nomeCompleto.trim().equalsIgnoreCase("")){
			//q= em.createNamedQuery("DmDDoc.updateDocPresentiCartella");
			//sql0 = "update DmDDoc d SET d.flgRimosso=0 WHERE d.dmConfDocDir=:conf and d.nomeFile=:nomeFile and d.dmDCartella=:cartella";
			
			sql0 = "update DM_D_DOC D1 set D1.flg_rimosso = 0 where D1.COD_PERCORSO = '" +codicePercorso+ "' AND D1.NOME_FILE = '"+nomeCompleto+"' AND D1.DM_D_CARTELLA_ID = " + cartellaId;
			
		}
		
		return ris;

	}//-------------------------------------------------------------------------
	
	private int[] salvaCartella(String codPercorso, String nomeCartella, String nomeUtente, Connection conn) throws Exception{
		String cartella = this.getCartellaByIdx(codPercorso, nomeCartella, conn);
		int[] ris1 = null;
		if(cartella==null || cartella.trim().equalsIgnoreCase("")){
			try{
				String sql1 = " INSERT INTO DM_D_CARTELLA "
						+ " ( ID, NUM_CARTELLA, "
						+ "USER_INS, DT_INS, "
						+ "USER_MOD, DT_MOD, "
						+ "PROVENIENZA, "
						+ "COD_PERCORSO ) "
						+ " VALUES "
						+ " ( SEQ_DEM.NEXTVAL, '?1?', "
						+ " 'TESTU', SYSDATE, "
						+ " '', NULL, "
						+ " '', '?2?' ) ";

				sql1 = sql1.replace("?1?", nomeCartella );
				sql1 = sql1.replace("?2?", codPercorso );

				Statement ps1 = conn.createStatement();
				ps1.addBatch(sql1);
				ris1 = ps1.executeBatch();
       		   conn.commit();	

			} catch (Throwable t) {
				log.error("Errore salvataggio in Dm_D_Cartella", t);
				throw new Exception(t);
			}
		}
		return ris1;
	}//-------------------------------------------------------------------------
	
	private String getCartellaByIdx(String codicePath, String nomeCartella, Connection conn) throws Exception{
		String cartella = "";
		String sql = "select * from DM_D_CARTELLA where COD_PERCORSO= ? and NUM_CARTELLA= ? ";
		//em.createNamedQuery("DmDCartella.getByIdx");
		ArrayList<String[]> alFolders = new ArrayList<String[]>();
		PreparedStatement preparedStatement = conn.prepareStatement( sql );
		preparedStatement.setString(1, codicePath);
		preparedStatement.setString(1, nomeCartella);
		ResultSet rs9 = preparedStatement .executeQuery();
		while(rs9.next()){
			
			Long cartellaId = rs9.getLong("ID");
			String cartellaNome = rs9.getString("NUM_CARTELLA");
			String provenienza = rs9.getString("PROVENIENZA");
			String codPercorso = rs9.getString("COD_PERCORSO");
			
			String[] folder = new String[4];
			folder[0] = cartellaId.toString();
			folder[1] = cartellaNome;
			folder[2] = provenienza;
			folder[3] = codPercorso;
			
			alFolders.add(folder);
		}
		preparedStatement.close();

		if(alFolders.size()>0)
			cartella = ((String[])alFolders.get(0))[1];

		return cartella;
	}//-------------------------------------------------------------------------

}
