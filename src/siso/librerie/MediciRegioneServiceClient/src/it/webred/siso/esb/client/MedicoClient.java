package it.webred.siso.esb.client;

import java.net.URL;

import org.jboss.logging.Logger;

import it.webred.siso.esb.Medico;
import it.webred.siso.esb.MedicoService;
import it.webred.siso.esb.MedicoServicePortType;


public class MedicoClient {
	 private MedicoService medicoService;
     private MedicoServicePortType port ;
     private URL urlWSDL;
     
     protected static Logger logger = Logger.getLogger("ud-rest-lib");
     
     public MedicoClient(){
    	 
     }
     
     public MedicoClient(URL urlWSDL){
 		this.urlWSDL=urlWSDL;
 	}

 	private void initClientContext() {
 		if (medicoService==null || port==null) {			
 				if(urlWSDL!=null){
 					medicoService = new MedicoService(urlWSDL);
 				}else{
 					medicoService = new MedicoService();
 				}
 				port= medicoService.getMedicoServiceHttpSoap12Endpoint();
 		}
 	}
 	public Medico getMedicoByCodiceRegionale(String codReg){
 		initClientContext();
 		Medico medico=port.getMedico(codReg);
 		String medicoDesc = medico!=null ? (medico.getCognome()+" "+medico.getNome()+" "+medico.getCodiceFiscale()) : "Non trovato";
 		logger.debug("getMedicoByCodiceRegionale ["+codReg+"] ["+ medicoDesc + "]");
 		if(medico!=null && medico.getCodiceFiscale()!=null && !medico.getCodiceFiscale().trim().isEmpty()){
 			return medico;
 		}
 		return null;
 	}

}
