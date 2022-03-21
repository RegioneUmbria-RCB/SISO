package it.webred.cs.csa.ejb.queryBuilder;

import java.text.SimpleDateFormat;

import javax.persistence.Query;

import org.apache.log4j.Logger;

public class QueryBuilderBase {

	protected static Logger logger = Logger.getLogger("carsociale.log");
	
	protected static SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
	
	protected String concatLikeParam(String label){
		return "LIKE '%'|| :"+label+" ||'%'";
	}
	
	protected String setParameter(Query q, String key, Object val, String msg){
		try{
			q.setParameter(key, val);
			msg = msg + "["+key+":"+val+"]";
		}catch(Exception qe){
			logger.error("Tentativo di impostazione parametro query: "+qe.getMessage());
		}
		return msg;
	}
	
}
