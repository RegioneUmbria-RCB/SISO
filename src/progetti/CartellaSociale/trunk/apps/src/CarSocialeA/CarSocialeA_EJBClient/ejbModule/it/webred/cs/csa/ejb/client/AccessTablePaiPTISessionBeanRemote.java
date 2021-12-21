package it.webred.cs.csa.ejb.client;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.ArCsPaiPTIDocumentoDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.CsPaiPTIDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.CsPaiPtiRevisioniDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.InserimentoConsuntivazioneDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.RichiestaDisponibilitaPaiPtiDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.StrutturaDisponibilitaDTO;
import it.webred.cs.data.model.pti.CsPaiPTI;
import it.webred.cs.data.model.pti.CsPaiPtiDocumento;
import it.webred.cs.data.model.pti.InserimentoMinoreDaStruttura;
import it.webred.ct.support.datarouter.CeTBaseObject;

public interface AccessTablePaiPTISessionBeanRemote {
	
	public CsPaiPTIDTO findPTIByDiarioPaiId(BaseDTO dto) throws Exception;
	
	public CsPaiPTIDTO savePTI(BaseDTO dto) throws Exception;
	
	public CsPaiPTI saveNuovoPTIFromBatch(BaseDTO dto) throws Exception;
	
	public CsPaiPTI findPTIByDiarioPaiIdFromBatch(BaseDTO dto) throws Exception;
	
	public CsPaiPTI savePTIFromBatch(BaseDTO dto) throws Exception;
	
	public CsPaiPtiDocumento saveDocumentoFromBatch(BaseDTO dto) throws Exception;
	
	public List<StrutturaDisponibilitaDTO> findElencoStruttureDisponibilita (BaseDTO dto )throws Exception;
	
	public List<StrutturaDisponibilitaDTO> findElencoStrutture(BaseDTO dto) throws Exception;
	
	public RichiestaDisponibilitaPaiPtiDTO findDisponibilitaById(BaseDTO dto )throws Exception;
	
	public List<InserimentoMinoreDaStruttura> findInsMinoriInStruttura(CeTBaseObject cet) throws Exception;
	
	public InserimentoMinoreDaStruttura saveInsMinoreStruttura(BaseDTO dto) throws Exception;
	
	public List<InserimentoConsuntivazioneDTO> findConsuntivazioneDaStruttura(BaseDTO dto) throws Exception;
	
	public void salvaConsuntivazione(InserimentoConsuntivazioneDTO consuntivazione)  throws Exception;
	
	public CsPaiPtiRevisioniDTO savePtiRevisione(BaseDTO dto) throws Exception;
	
	public List<CsPaiPtiRevisioniDTO> findRevisioniPTI(BaseDTO dto) throws Exception;
	
	public List<ArCsPaiPTIDocumentoDTO> findDocumentiRichiestaSelezionata(BaseDTO dto);
	
	public void saveDocumento(BaseDTO dto)  throws IllegalAccessException, InvocationTargetException, Exception;
	
	public List<RichiestaDisponibilitaPaiPtiDTO> findProgettiAltriEnti(BaseDTO dto)throws Exception;
	
	public StrutturaDisponibilitaDTO findStrutturaById(BaseDTO dto) throws Exception;
	
	public List<ArCsPaiPTIDocumentoDTO> findDocumentiDaProcessare(BaseDTO dto) throws Exception;
	
	public CsPaiPTI findPTIById(BaseDTO dto) throws Exception;
	
	public List<InserimentoConsuntivazioneDTO> findConsuntiDaProcessare(BaseDTO dto) throws Exception;
	
	public List<InserimentoConsuntivazioneDTO> findConsuntivazioneErogate(BaseDTO dto) throws Exception;
}
