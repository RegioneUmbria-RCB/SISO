package it.webred.ct.data.access.basic.docfa.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import it.webred.ct.data.model.docfa.DocfaInParteUno;

public class DocfaInParteUnoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private DocfaInParteUno docfaInParteUno;
	
	private String descrPosizFabb;
	private String descrTipoAcces;
	
	public DocfaInParteUno getDocfaInParteUno() {
		return docfaInParteUno;
	}
	public void setDocfaInParteUno(DocfaInParteUno docfaInParteUno) {
		this.docfaInParteUno = docfaInParteUno;
	}
	public String getDescrPosizFabb() {

		if(docfaInParteUno!=null){
			BigDecimal code = docfaInParteUno.getPosizioneFabb();
			if(code!=null){
				switch(code.intValue()) {
	            case 1: descrPosizFabb = "ISOLATO";
	                     break;
	            case 2: descrPosizFabb = "CONTIGUO";
	            		 break;     
	            case 3: descrPosizFabb = "A SCHIERA"; 
	            		 break;
	            default: descrPosizFabb = code.toString();
	            	     break;
			}
		 }
		}
		return descrPosizFabb;
	}
		
	public String getDescrTipoAcces() {
		
		if(docfaInParteUno!=null){
			BigDecimal code = docfaInParteUno.getTipoAcces();
			if(code!=null){
				switch(code.intValue()) {
	            case 1: descrTipoAcces = "UNICO ESTERNO";
	                     break;
	            case 2: descrTipoAcces = "PLURIMO ESTERNO";
	            		 break;     
	            case 3: descrTipoAcces = "DAL CORTILE INT."; 
	            		 break;
	            default: descrTipoAcces = code.toString();
	            	     break;
			}
		 }
		}
	
		return descrTipoAcces;
	}
}
