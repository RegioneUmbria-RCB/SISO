package eu.smartpeg.rilevazionepresenze.web.converter;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import eu.smartpeg.rilevazionepresenze.ejb.helpers.NazioniCacheHelperRemote;
import eu.smartpeg.utility.ejb.EjbClientUtility;
import it.webred.ct.config.model.AmTabNazioni;

@ManagedBean
@ApplicationScoped
public class AmTabNazioniConverter implements Converter{
	//TODO:  @Inject e @EJB non vengono eseguite - registrare Converter ForClass
	//@EJB private NazioniCacheHelperRemote nazioniCacheHelperRemote;;
	private NazioniCacheHelperRemote nazioniCacheHelperRemote = 
			(NazioniCacheHelperRemote) EjbClientUtility.getEjb("RilevazionePresenze", "RilevazionePresenze_EJB", "NazioniCacheHelper", 
			NazioniCacheHelperRemote.class.getCanonicalName());
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return nazioniCacheHelperRemote.getNazioneByCodiceAnagrafe(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value!=null) {
			return ((AmTabNazioni)value).getCodNazioneAnagrafe();
		}
		return null;
	}
}
