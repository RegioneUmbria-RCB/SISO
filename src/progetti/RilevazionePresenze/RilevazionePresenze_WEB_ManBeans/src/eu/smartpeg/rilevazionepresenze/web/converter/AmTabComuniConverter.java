package eu.smartpeg.rilevazionepresenze.web.converter;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


import eu.smartpeg.rilevazionepresenze.ejb.helpers.ComuniCacheHelperRemote;
import eu.smartpeg.utility.ejb.EjbClientUtility;
import it.webred.ct.config.model.AmTabComuni;

@ManagedBean
@ApplicationScoped
public class AmTabComuniConverter implements Converter  {
	
	//TODO:  @Inject e @EJB non vengono eseguite - registrare Converter ForClass
	private ComuniCacheHelperRemote comuniCacheHelperRemote = 
			(ComuniCacheHelperRemote) EjbClientUtility.getEjb("RilevazionePresenze", "RilevazionePresenze_EJB", "ComuniCacheHelper", 
			ComuniCacheHelperRemote.class.getCanonicalName());
	
	//@EJB
	//@EJB(lookup="java:global/RilevazionePresenze/RilevazionePresenze_EJB/ComuniCacheHelper!eu.smartpeg.rilevazionepresenze.ejb.helpers.ComuniCacheHelperRemote")
	//private  ComuniCacheHelperRemote comuniCacheHelperRemote;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return comuniCacheHelperRemote.getComuneByCodiceIstat(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value!=null) {
			return ((AmTabComuni)value).getCodIstatComune();
		}
		return null;
	}

}
