package eu.smartpeg.rilevazionepresenze.web;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;

@ManagedBean
@RequestScoped
public class HelloControler extends RilevazionePresenzeBaseController {

	public HelloControler() {
		logger.info("Hello Controller COSTRUTTORE");
		// TODO Auto-generated constructor stub
	}
	
	  public String getMessage() {
	      return "Telelavoratori .... PRRRRRR :-)";
	  }

	  public Date getTime() {
	      return GregorianCalendar.getInstance().getTime();
	  }

}
