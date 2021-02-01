package it.roma.comune.servizi.test;

import it.roma.comune.servizi.verificheanagrafiche.LogHeader;
import it.roma.comune.servizi.verificheanagrafiche.NVASoap;
import it.roma.comune.servizi.verificheanagrafiche.VerificaStatoFamigliaConv;
import it.roma.comune.servizi.verificheanagrafiche.VerificaStatoFamigliaConvResponse;
import it.roma.comune.servizi.verificheanagrafiche.VerificaStatoFamigliaConvResponseVerificaStatoFamigliaConvResult;

import org.apache.axis.types.UnsignedByte;

public class TestVerificaStatoFamiglia {
	
	NVASoap soap;
	
	public TestVerificaStatoFamiglia(NVASoap soap){
		this.soap=soap;
	}
	
	public void doTest(String codiceIndividuale, String codiceFiscale, String cognome, String nome){
		VerificaStatoFamigliaConv va= new VerificaStatoFamigliaConv();
		UnsignedByte tipo= null;
		
		if(codiceIndividuale != null && !codiceIndividuale.equals("-")){
			va.setCodiceIndividuale(codiceIndividuale);
			tipo= new UnsignedByte(1);
		}
		else if(codiceFiscale != null && !codiceFiscale.equals("-")){
			va.setCodiceFiscale(codiceFiscale);
			tipo= new UnsignedByte(2);
		}
		else{
			tipo= new UnsignedByte(3);
			
			if(cognome != null && !cognome.equals("-"))
				va.setCognome(cognome);
			 if(nome != null && !nome.equals("-"))
				va.setNome(nome);
		}
		
		va.setTipoInterr(tipo);
		LogHeader header= new LogHeader();
		
		header.setLogGuid(TestMain.getUID());
		VerificaStatoFamigliaConvResponse resp=null;
		try {
			resp=soap.verificaStatoFamigliaConv(va, header);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		VerificaStatoFamigliaConvResponseVerificaStatoFamigliaConvResult r=  resp.getVerificaStatoFamigliaConvResult();
		try{
//		System.out.println(r.get_any()[0].getAsString());
//		System.out.println("******************************");
//		System.out.println(r.get_any()[0].getValue());
//		System.out.println("*******************************");
//		System.out.println(r.get_any()[0].getAsDocument().getTextContent());
			for(int i=0; i< r.get_any().length; i++ ){
				 
				 System.out.println(r.get_any()[i].toString());
				 System.out.println("-----------------------------------");
			}
		}
		catch(Exception ex){
			System.out.println("Eccezione .. " + ex.getStackTrace());
		}
	}
	
	public void doTest(){
		VerificaStatoFamigliaConv va= new VerificaStatoFamigliaConv();
		//va.setCodiceIndividuale(11);
		va.setCognome("ROSSI");
 		va.setAnnoNascita((short)1973);
 		va.setSesso("F");
		UnsignedByte tipo= new UnsignedByte(1);
		va.setTipoInterr(tipo);
		
		LogHeader header= new LogHeader();
		
		header.setLogGuid(TestMain.getUID());
		VerificaStatoFamigliaConvResponse resp=null;
		try {
			resp=soap.verificaStatoFamigliaConv(va, header);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		VerificaStatoFamigliaConvResponseVerificaStatoFamigliaConvResult r=  resp.getVerificaStatoFamigliaConvResult();
		try{
		System.out.println(r.get_any()[0].getAsString());
		System.out.println("******************************");
		System.out.println(r.get_any()[0].getValue());
		System.out.println("*******************************");
		System.out.println(r.get_any()[0].getAsDocument().getTextContent());
		}
		catch(Exception ex){
			System.out.println("Eccezione .. " + ex.getStackTrace());
		}
	}
}

