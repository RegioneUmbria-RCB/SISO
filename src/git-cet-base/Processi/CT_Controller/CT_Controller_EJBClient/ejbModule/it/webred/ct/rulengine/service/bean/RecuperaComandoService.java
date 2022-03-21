package it.webred.ct.rulengine.service.bean;


import java.math.BigDecimal;
import java.util.List;

import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.controller.model.RCommandAck;
import it.webred.ct.rulengine.controller.model.RCommandLaunch;
import it.webred.ct.rulengine.controller.model.RFontedatiCommand;
import it.webred.ct.rulengine.dto.LogDTO;
import it.webred.ct.rulengine.dto.Task;



import javax.ejb.Remote;

@Remote
public interface RecuperaComandoService {
	
	public RCommand getRCommand(Task task); 
	
	public RCommand getRCommand(String codCommand);
	
	public RCommand getRCommand(Long idCommand);
	
	public RCommand getRCommandDummy(Long idCommandType,Long idFonte);
	
	//public RCommand controlloEsclusione(Long idCommand,String belfiore); 
	public RCommand getON(Long idCommand,String belfiore);
	
	public RCommand getOFF(Long idCommand,String belfiore);
	
	public List<RFontedatiCommand> getRCommandFDs(RCommand rCommand);
	
	public List<RCommand> getRCommandsByFontiAndType(String belfiore,List<Long> fonti, Long idType);
	
	public Long getRCommandsByFonteTypesCount(String belfiore, Long fonte, List<Long> types);
	
	public List<RCommand> getRCommandsByTypeWithoutFonti(String belfiore, Long idType);
	
	/**
	 * Override del metodo per la gestione della paginazione su Servizio Diagnostiche
	 * 
	 * @param start
	 * @param maxrows
	 * @param belfiore
	 * @param fonti
	 * @param idType
	 * @return
	 */
	public List<RCommand> getRCommandsByFontiAndType(int start, int maxrows, String belfiore,List<Long> fonti, Long idType);
	
	public Long getRCommandsByFontiAndTypeCount(String belfiore,List<Long> fonti, Long idType);
	
	public List<RCommand> getRCommandsByType(String belfiore, Long idType);
	
	public List<RCommand> getAllRCommands();
	
	public List<RCommandLaunch> getRCommandLaunchByRange(List<String> enti, Integer start, Integer maxResult);
	
	public Long getRCommandLaunchCount(List<String> enti);
	
	public List<RCommandLaunch> getRCommandLaunchByBelfiore(String belfiore, Integer start, Integer maxResult);
	
	public Long getRCommandLaunchCountByBelfiore(String belfiore);
	
	public List<RCommandLaunch> getRCommandLaunch(Long idCommand, String belfiore);
	
	public List<RCommandLaunch> getRCommandLaunchByScheduler(Long idScheduler);
	
	public RCommandLaunch getLastRCommandLaunch(Long idCommand,String belfiore);

	public List<RCommandAck> getRCommandAck(Long idCommandLaunch);
	
	public List<Object[]> getRCommandLaunchJoinStatiAck(Long idCommand, String belfiore, String mese, String anno);
}
