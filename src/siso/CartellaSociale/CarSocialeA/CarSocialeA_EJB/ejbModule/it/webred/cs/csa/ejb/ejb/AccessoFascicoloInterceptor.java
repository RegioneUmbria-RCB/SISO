package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.RelazioneDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneMasterDTO;
import it.webred.cs.csa.ejb.dto.fascicolo.DatiInterceptorDTO;
import it.webred.cs.data.model.CsDColloquioBASIC;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDDocIndividuale;
import it.webred.cs.data.model.CsDIsee;
import it.webred.cs.data.model.CsDPai;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsIIntervento;

import java.util.Iterator;
import java.util.List;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

@SuppressWarnings("serial")
public class AccessoFascicoloInterceptor extends CarSocialeBaseSessionBean {

	@AroundInvoke
	public <T> Object intercetta(InvocationContext context){
		Object result = null;
		Object input = context.getParameters()[0];
		Long idSettore = null;
		Boolean nascondiInfo = null;
	    if(input instanceof BaseDTO){
	    	BaseDTO b = (BaseDTO)input ;
	    	idSettore = b.getSettoreId();
	    	nascondiInfo = b.getNascondiInfoPerSettore()!=null ? b.getNascondiInfoPerSettore() : false;
	    }
			
		try {
			result = context.proceed();
			
			List<T> listaRisultati = (List<T>) result;	
			
			if(idSettore != null && listaRisultati != null && listaRisultati.size() > 0){
				T tipoObj = listaRisultati.get(0);
				
				//interventi programmati
				if(tipoObj instanceof CsIIntervento){
					List<CsIIntervento> lip = (List<CsIIntervento> ) result;
					filtraInterventiProgrammati(lip, idSettore, nascondiInfo);
				}
				
				//EROGAZIONI
				if(tipoObj instanceof ErogazioneMasterDTO){
					List<ErogazioneMasterDTO> le = (List<ErogazioneMasterDTO> ) result;
					//le eorgazioni vengono gia filtrate per settore.
					//vanno filtrate solo eventualmente per secondo livello
					filtraErogazioni(le, idSettore);
				}
				
				//DIARIOINTERCEPTOR (diario) --> DOC.INDIVIDUALI, COLLOQUI, PAI
				if(tipoObj instanceof DatiInterceptorDTO){
					List<DatiInterceptorDTO> lcb = (List<DatiInterceptorDTO> ) result;
					filtraLista(lcb, idSettore, nascondiInfo);
				}
				
				//ATTIVITA' PROFESSIONALI
				if(tipoObj instanceof RelazioneDTO){
					List<RelazioneDTO> lr = (List<RelazioneDTO> ) result;
					filtraRelazioni(lr, idSettore, nascondiInfo);
				}
				
				//ISEE (Gestito con interfaccia JSON)
				if(tipoObj instanceof CsDIsee){
					List<CsDIsee> li = (List<CsDIsee> ) result;
					filtraIsee(li, idSettore, nascondiInfo);
				}
	
				//VAL.SINBA and PROVVEDIMENTI-TRIBUNALE (JSON)
				if(tipoObj instanceof CsDValutazione){
					List<CsDValutazione> lv = (List<CsDValutazione> ) result;
					filtraValutazioni(lv, idSettore, nascondiInfo);
				}
			}
		} catch (Exception e) {
			logger.error("Errore fascicolo interceptor: "+e.getMessage(), e);
		}
			
		return result;
	}
	
	private boolean filtraSecondoLivello(Long idSett2Liv, Long idSettDiario, Long idSettore, Boolean nascondiInfo){
		boolean rimuovi = false;
		if(idSettDiario!=null){ //Può essere NULL se i dati da mostrare provengono da UDC (es.: doc.individuali) in tal caso non vanno filtrati
			Boolean isSecondoLivelloInfo = idSett2Liv == null ? false : true;
			rimuovi = (nascondiInfo || isSecondoLivelloInfo) && !idSettore.equals(idSettDiario);
		}
		return rimuovi;
	}
	
	private void filtraLista(List<DatiInterceptorDTO> lcb, Long idSettore, Boolean nascondiInfo){
		Iterator<DatiInterceptorDTO> it = lcb.iterator();
		while(it.hasNext()){
			DatiInterceptorDTO c = it.next();
			if(filtraSecondoLivello(c.getSettSecondoLivello(), c.getIdSettoreDiario(),idSettore, nascondiInfo))
			   it.remove();
		}
		
	}
	
	private void filtraIsee(List<CsDIsee> li, Long idSettore, Boolean nascondiInfo){
		Iterator<CsDIsee> it = li.iterator();
		while(it.hasNext()){
			CsDIsee c = it.next();
			Long idSett2Liv = c.getCsDDiario().getVisSecondoLivello();
			Long idSettDiario = c.getCsDDiario().getCsOOperatoreSettore().getCsOSettore().getId();
			if(filtraSecondoLivello(idSett2Liv,idSettDiario,idSettore, nascondiInfo))
			   it.remove();
		}	
	}
	
	private void filtraRelazioni(List<RelazioneDTO> lr, Long idSettore, Boolean nascondiInfo){
		Iterator<RelazioneDTO> it = lr.iterator();
		while(it.hasNext()){
			RelazioneDTO cs = it.next();
			CsDDiario c = cs.getRelazione().getCsDDiario();
			Long idSett2Liv = c.getVisSecondoLivello();
			Long idSettDiario = c.getCsOOperatoreSettore().getCsOSettore().getId();
			if(filtraSecondoLivello(idSett2Liv,idSettDiario,idSettore, nascondiInfo))
			   it.remove();
		}	
	}
	
	private void filtraValutazioni(List<CsDValutazione> lv, Long idSettore, Boolean nascondiInfo){
		Iterator<CsDValutazione> it = lv.iterator();
		while(it.hasNext()){
			CsDValutazione c = it.next();
			Long idSett2Liv = c. getCsDDiario().getVisSecondoLivello();
			Long idSettDiario = c.getCsDDiario().getCsOOperatoreSettore().getCsOSettore().getId();
			if(filtraSecondoLivello(idSett2Liv,idSettDiario,idSettore, nascondiInfo))
			   it.remove();
		}	
	}
	
    private void filtraInterventiProgrammati(List<CsIIntervento> lip, Long idSettore, Boolean nascondiInfo){
		Iterator<CsIIntervento> it = lip.iterator();
		while(it.hasNext()){
			CsIIntervento cs = it.next();
			CsDDiario c = cs.getCsFlgInterventos().iterator().next().getCsDDiario();
			Long idSett2Liv = c.getVisSecondoLivello();
			Long idSettDiario = c.getCsOOperatoreSettore().getCsOSettore().getId();
			if(filtraSecondoLivello(idSett2Liv,idSettDiario,idSettore, nascondiInfo))
			   it.remove();
		}	
	}
    
    private void filtraErogazioni(List<ErogazioneMasterDTO> lem, Long idSettore){
    	Iterator<ErogazioneMasterDTO> it = lem.iterator();
    	while(it.hasNext()){
    		ErogazioneMasterDTO em = it.next();
    		if(em.getSettoreSecondoLivello() != null && !em.getSettoreSecondoLivello().equals(idSettore)){
    			it.remove();
    		}
    	}
    }
	
	
}
