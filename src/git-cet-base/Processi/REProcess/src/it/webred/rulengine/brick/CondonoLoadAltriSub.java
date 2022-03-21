package it.webred.rulengine.brick;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import it.webred.rulengine.Context;
import it.webred.rulengine.DbCommand;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.cataloghi.ValoreMedioRenditaVano;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class CondonoLoadAltriSub extends DbCommand implements Rule {
	private static final Logger log = Logger.getLogger(CondonoLoadAltriSub.class.getName());
	
	private final String TAB_COORDINATE_DENORMALIZZATE = "MI_CONDONO_NORMALIZZAZIONE_ALE";
	private final String TAB_COORDINATE  = "MI_CONDONO_COORDINATE";
	
	public CondonoLoadAltriSub(BeanCommand bc){
		super(bc);
	}
	@Override
	public CommandAck runWithConnection(Context ctx, Connection conn)
			throws CommandException {
		// TODO Auto-generated method stub
		
		PreparedStatement pst = null;
		PreparedStatement pstIns = null;
		ResultSet rs = null;
		String sql = null;
		sql = "SELECT * FROM " + TAB_COORDINATE_DENORMALIZZATE + " WHERE ALTRISUB IS NOT NULL"; 
		long numRigheIns=0;
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs != null){
				while(rs.next()){
					String altriSub = rs.getString("ALTRISUB");
					if (altriSub.indexOf("_") > -1)
						altriSub.replace("_","-");
					String[] arrAltriSub = altriSub.split("-");
					for(int i = 0; i<arrAltriSub.length; i++ ){
						String sub = arrAltriSub[i].trim();
						Integer subNum = null;
						boolean flSubNum = false;
						try {
							subNum = Integer.parseInt(sub);
							flSubNum= true;
						}
						catch(Exception e) {
							log.debug("CONDONO/SUB: " + rs.getString("CODCONDONO") + "/" + sub +" - Formato sub errato. ALTRISUB: " + altriSub  );
							flSubNum = false;
						}
						if (sub.length() == 4 || flSubNum) {
							//inserisco una riga 
							String annota ="null";
							String notaCond = "null";
							String esibente = "null";
							String cfpi = "null";
							String barraCivico = "null";
							String catastoTestuale = "null";
				
							String sqlIns = "INSERT INTO " + TAB_COORDINATE + " VALUES ('" + 
			                  rs.getString("CODCONDONO") + "','" + rs.getString("DATAINSERIMENTO") + "','" + 
			                 rs.getString("NUMPROG") + "','" + rs.getString("PROGPROV") + "','"  +
			                 rs.getString("PROGPROV2") + "','" + rs.getString("DATAPROV") + "',";
							if (rs.getString("NOTACOND") != null) {
								notaCond =  rs.getString("NOTACOND").replace("'", "''");
								sqlIns +=  "'" + notaCond + "',";
							}
							else 
								sqlIns +=   " null," ;
							sqlIns += "'" + rs.getString("TIPOCOND") + "',";
							if (rs.getString("ESIBENTE") != null) {
								esibente =  rs.getString("ESIBENTE").replace("'", "''");
								sqlIns += "'" + esibente + "',";
							}
							else
								sqlIns +=   " null," ;
							if (rs.getString("CFPI") != null) {
								cfpi =  rs.getString("CFPI").replace("'", "''");
								sqlIns +=  "'" + cfpi + "',";
							}
							else 
								sqlIns +=   " null," ;
							sqlIns += "'" + rs.getString("CODVIA") + "','" + rs.getString("PROVIA_CND") + "','" + 
			                          rs.getString("CIVICO") + "',";
							if (rs.getString("BARRACIVICO") != null) {
								barraCivico =  rs.getString("BARRACIVICO").replace("'", "''");
								sqlIns +=  "'" + barraCivico + "',";
							}
							else 
								sqlIns +=   " null," ;
							sqlIns += "'" + rs.getString("ZONA") + "',";
							if (rs.getString("CATASTOTESTUALE") != null) {
								catastoTestuale =  rs.getString("CATASTOTESTUALE").replace("'", "''");
								sqlIns +=  "'" + catastoTestuale + "',";
							}
							else 
								sqlIns +=   " null," ;
							if (rs.getString("ANNOTA") != null) {
								annota =  rs.getString("ANNOTA").replace("'", "''");
								sqlIns +=  "'" + annota + "',";
							}
							else 
								sqlIns +=   " null," ;
							sqlIns += "'"+ rs.getString("TIPOSTRINGA") + "','" +
			                               rs.getString("SEZIONE") + "','" + rs.getString("FOGLIO") + "','" +
			                               rs.getString("MAPPALE") + "','" + sub+ "')" ; 
							
							//log.debug("inserisco: " + sqlIns);
							pstIns = conn.prepareStatement(sqlIns);
							numRigheIns++;
							pstIns.execute();
							pstIns.close();
						}
					}
				}
				
			}
			log.debug("Fine caricamento. Righe inserite: " + numRigheIns);
			return (new ApplicationAck("CARICAMENTO ALTRI SUB TERMINATO CON SUCCESSO - RIGHE INSERITE: " + numRigheIns));
		}catch (Exception e)
		{
			log.error("Errore esecuzione sql:" + sql, e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} finally {
			try
			{
				if (pstIns != null)
					pstIns.close();
				if (pst != null)
					pst.close();
				if (rs != null)
					rs.close();
			}
			catch (Exception exc)
			{
			}

		}
				
	}

}
