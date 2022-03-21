package it.webred.rulengine.brick.loadDwh.base;

import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadDemanioBeniBase  extends AbstractLoaderCommand implements Rule{
	
	protected static final Logger log = Logger.getLogger(LoadDemanioBeniPrincipali.class.getName());
	
	protected SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	
	public LoadDemanioBeniBase(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}//-------------------------------------------------------------------------
	
	protected List<Object> caricaParametri(int maxIndex, Context ctx){
		
		List<Object> lst = new ArrayList<Object>();
		List parametriIn = this.getParametersIn(_jrulecfg);
		for(int i=0; i<= maxIndex; i++){
			Object o = ctx.get(((RRuleParamIn) parametriIn.get(i)).getDescr());
			lst.add(o);
		}
		
		return lst;
		
	}//-------------------------------------------------------------------------

	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn) throws CommandException{
		
		
		return(new ApplicationAck("superclasse"));

	}//-------------------------------------------------------------------------
	
	protected void aggiornaDataElaborazione(String dato, String provenienza, Connection conn) throws Exception{
		SimpleDateFormat sdm = new SimpleDateFormat("ddMMyyyyHHmmss");
		String sql = "select nvl(to_char(max(dt_elab),'ddMMyyyyHH24miss'),'01010001000000') MAX_DATA_ELAB from "+dato+" where provenienza = ? ";
		log.info("LoadDemanioBeniPrincipali.aggiornaDataElaborazione - SQL["+sql+"]");
		PreparedStatement preparedStatement = conn.prepareStatement( sql );
		preparedStatement.setString(1, provenienza);
		ResultSet rs = preparedStatement.executeQuery();
		String dataMax = null;
		while(rs.next()){
			dataMax = rs.getString("MAX_DATA_ELAB");
		}
		sql = "update DM_CONF_CARICAMENTO set DT_ELAB = ? where DATO = ? and PROVENIENZA = ? ";
		log.info("LoadDemanioBeniPrincipali.aggiornaDataElaborazione - SQL["+sql+"]");
		preparedStatement = conn.prepareStatement( sql );
		preparedStatement.setDate(1, new Date(sdm.parse(dataMax).getTime() ) );
		preparedStatement.setString(2, dato);
		preparedStatement.setString(3, provenienza);
		preparedStatement.executeUpdate();
		preparedStatement.close();
	}//-------------------------------------------------------------------------
	
	protected String getDestFields(String tab, Connection conn) throws Exception{
		String clause = "";
		for(String t : this.getListaColonne(tab, conn)){
				clause+=t+",";
		}
		
		return clause = clause.substring(0,clause.length()-1);
	}//-------------------------------------------------------------------------
	
	protected String getOrigFields(String tab, Connection conn) throws Exception{
		String clause = "";
		for(String t : this.getListaColonne(tab, conn)){
				clause+="o."+t+",";
		}
		
		return clause = clause.substring(0,clause.length()-1);
	}//-------------------------------------------------------------------------
	
	protected List<String> getListaColonne(String tab, Connection conn) throws Exception{

			String sql = "select distinct column_name from all_tab_columns where table_name = ? and owner like 'VIRGILIO%' ";
			log.debug("getListaColonne[tabella:"+tab+"] - SQL["+sql+"]");
			List<String> lstColsName = new LinkedList<String>();
			PreparedStatement preparedStatement = conn.prepareStatement( sql );
			preparedStatement.setString(1, tab);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");
				lstColsName.add(columnName);
			}
			return  lstColsName;
				
	}//-------------------------------------------------------------------------

	protected String getMergeClause(String tab, String tipo, String vista, Connection conn) throws Exception{
		String clause = "";
		String[] le = {"ID","CHIAVE","PROVENIENZA","TIPO","DT_INIZIO_VAL","DT_FINE_VAL","DM_B_BENE_ID"};
		List<String> lstEsc = Arrays.asList(le);  
		
		List<String> lstDest = this.getListaColonne(tab, conn);
		List<String> lstOrig = this.getListaColonne(vista, conn);
		
		for(String t : lstDest){
			if(!lstEsc.contains(t)){
				if(lstOrig.contains(t))
					clause+=" d."+t+"=o."+t+",";
				else
					clause+=" d."+t+"=null ";
			}
		}
		
		return clause = clause.substring(0,clause.length()-1);
	}//-------------------------------------------------------------------------


}
