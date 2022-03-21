package it.webred.ct.support.audit.test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import it.webred.ct.support.audit.DumpField;
import it.webred.ct.support.audit.annotation.AuditAnnotedFields;
import it.webred.ct.support.audit.annotation.AuditField;
import it.webred.ct.support.audit.annotation.AuditInherit;
import it.webred.ct.support.datarouter.CeTBaseObject;

public class MainTest {
	  
	    public static void main(String[] args) throws Exception
	    {
	       
			DumpField df = new DumpField();
			String arguments = "";
			
			/*TEST*/
			/*Objtest t = new Objtest();
			
			CeTBaseObject c = new CeTBaseObject();
			c.setSessionId("SDJ756");
			c.setEnteId("F704");
			
			LinkedList ll = new LinkedList();
			CeTBaseObject co = new CeTBaseObject();
			co.setSessionId("DHER65GH6F_666");
			ll.add(co);
			
			t.setLl(ll);
			t.setCet(c);*/
			
			PermessoZtlDataBean t = new PermessoZtlDataBean();
			t.setEnte("F704");
			t.setSessionId("DFH67BS21");
			DatiUtente u = new DatiUtente();
			u.setNomeUtente(null);
			u.setCodfisc("CCDF543T2");
			t.setUtente(u);
			
			PermessoZtlDataBean t2 = new PermessoZtlDataBean();
			t2.setEnte("A234");
			t2.setSessionId("AQAQ121");
			DatiUtente u2 = new DatiUtente();
			u2.setNomeUtente(null);
			u2.setCodfisc("AQWASWAA311");
			t2.setUtente(u2);
			
			List<PermessoZtlDataBean> lista = new ArrayList<PermessoZtlDataBean>();
			lista.add(t);
			lista.add(t2);
			
			df.setMaxProfondita(3);
			arguments = df.dumpFields(lista, "1");
	   	       
			System.out.println(arguments);
			
			//Accesso alla classe
			/*Class<PermessoZtlDataBean> c = PermessoZtlDataBean.class;

			if(c.getAnnotation( InheritedAudit.class ) != null){
				boolean dumpAll = true;
				if(c.getAnnotation( AnnotedFieldsAudit.class ) != null)
					dumpAll = false;
				Vector<Field> vector = df.getAllFields(c);
				
				for(int i = 0; i < vector.size(); i++){
					Field f = vector.get(i);
					f.setAccessible(true);
					if(dumpAll || f.getAnnotation(DumpFieldAudit.class) != null)
						System.out.println(f.getName());
				}
			}*/
			
			/*String input = "VIA&#x20;TOMMASEO&#x20;NICOLO&apos;";
			input = input.replaceAll("&#x20;"," ");
			input = input.replaceAll("&apos;","'");
			System.out.println(input);*/
			
			/*
			//Mostra annotazioni
			System.out.println("Annotazione di classe: ");
			System.out.println(c.getAnnotation( InheritedAudit.class ) != null? "Inherit":"Non Inherit");

			//Mostra annotazioni
			System.out.println("Annotazione di classe: ");
			System.out.println(c.getAnnotation( AnnotedFieldsAudit.class ));
			
			//Campo saluto
			//Field field = c2.getField("ente");
			System.out.println("Annotazione attributo saluto: ");
			//System.out.println(field.getAnnotation(DumpFieldAudit.class));
			 * 
			 */
	    }
	    
	    
	
}




   
	  
