package eu.smartpeg.rilevazionipresenze.helper;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import it.webred.cet.permission.CeTUser;
import it.webred.ct.support.datarouter.CeTBaseObject;

@Named(value = "cetHelper")
public class CeTHelper {

	public static void fillEnte(CeTBaseObject ro) {
		CeTUser user = (CeTUser) getSession().getAttribute("user");
		if (user != null) {
			ro.setEnteId(user.getCurrentEnte());
			ro.setUserId(user.getUsername());
			ro.setSessionId(user.getSessionId());
		}
	}
	
	
	private static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
}
