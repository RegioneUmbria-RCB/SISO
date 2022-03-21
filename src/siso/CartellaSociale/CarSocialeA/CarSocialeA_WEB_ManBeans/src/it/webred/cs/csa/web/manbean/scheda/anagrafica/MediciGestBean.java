package it.webred.cs.csa.web.manbean.scheda.anagrafica;

import it.webred.cs.csa.ejb.client.AccessTableMediciSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.data.model.CsCMedico;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.interfaces.IDatiValiditaGestione;
import it.webred.cs.jsf.manbean.DatiValGestioneMan;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.dto.utility.KeyValuePairBean;
import it.webred.ejb.utility.ClientUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.naming.NamingException;

@ManagedBean
@NoneScoped
public class MediciGestBean extends DatiValGestioneMan implements IDatiValiditaGestione {
	protected static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List<KeyValueDTO> loadListItems() {
		try {
			AccessTableMediciSessionBeanRemote bean = (AccessTableMediciSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableMediciSessionBean");
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			return bean.getMedici(bo);

		} catch (NamingException e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
			return null;
		}
	}


	@Override
	public List<KeyValuePairBean> getDettaglioSelezione(Long id) {
		List<KeyValuePairBean>  lstInfo = new ArrayList<KeyValuePairBean>();
		try {
			AccessTableMediciSessionBeanRemote bean = (AccessTableMediciSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableMediciSessionBean");
			BaseDTO bo = new BaseDTO();
			fillEnte(bo);
			bo.setObj(id);
			CsCMedico medico = bean.findMedicoById(bo);
			if (medico != null) {
				if(medico.getDistretto()!=null && !medico.getDistretto().isEmpty())
					lstInfo.add(new KeyValuePairBean("Distretto", medico.getDistretto()));
				
				String indirizzo="";
				if(medico.getIndirizzo()!=null && !medico.getIndirizzo().isEmpty())
					indirizzo+=medico.getIndirizzo()+", ";
				if(medico.getCitta()!=null && !medico.getCitta().isEmpty())
					indirizzo +=medico.getCitta();
				if(!indirizzo.trim().isEmpty())
					lstInfo.add(new KeyValuePairBean("Indirizzo", indirizzo));
				
				String telefono = medico.getTel()!=null && !medico.getTel().isEmpty() ? medico.getTel() : "";
				telefono+= !telefono.isEmpty() && (medico.getCell()!=null && !medico.getCell().isEmpty()) ? "/" : "";
				telefono+= (medico.getCell()!=null && !medico.getCell().isEmpty()) ? medico.getCell() : "";
				if(!telefono.isEmpty())
					lstInfo.add(new KeyValuePairBean("Tel.", telefono));
			}
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		
		return lstInfo;
	}

	//SISO-1127 Inizio
		protected static long nullDateTime;
		static {
			try {
				nullDateTime = SDF.parse("31/12/9999").getTime();
			} catch (Exception e) {}		
		}
		
		public long getNullDateTime() {
			return nullDateTime;
		}
		public ValiditaCompBaseBean getComponenteAttivo() {
			
			
			List<ValiditaCompBaseBean>  listComponents = super.getLstComponents();
			
			if (listComponents == null || listComponents.size() == 0) {
				return null;
			}
			 
			for (ValiditaCompBaseBean comp : listComponents) {
				if(comp.getDataFine() == null || (comp.getDataFine().getTime() == getNullDateTime())) {
					 return comp;
				}
			}
			 return null;
		}
	   public int getIndexComponenteAttivo() {
			
			
			List<ValiditaCompBaseBean>  listComponents = super.getLstComponents();
			
			if (listComponents == null || listComponents.size() == 0) {
				return -1;
			}
			int index = 0; 
			for (ValiditaCompBaseBean comp : listComponents) {
				if(comp.getDataFine() == null || (comp.getDataFine().getTime() == getNullDateTime())) {
					 return index;
				}
				index++;
			}
			 return -1;
		}
		
	   //SISO-1127 Fine
}
