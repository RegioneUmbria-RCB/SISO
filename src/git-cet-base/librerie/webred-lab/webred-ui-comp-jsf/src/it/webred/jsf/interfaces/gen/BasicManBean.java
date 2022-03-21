package it.webred.jsf.interfaces.gen;

import org.jboss.logging.Logger;

/**
 * fornisce alcuni metodi di base per i managed bean che vanno passati ai composite component
 * @author Utente
 *
 */
public abstract class BasicManBean implements IManBeanForComponent {
	
	protected Logger logger = Logger.getLogger("carsociale.log");

	public abstract String getValidatorMessage();

	/*
	 * nome mnemonico da dare al componente che potra essere utilizzato in javascript, il metodo può essere sovrascritto
	 */
	public abstract String getMemoWidgetName();
	
	/*
	 * (non-Javadoc)
	 * @see it.webred.jsf.interfaces.gen.IManBeanForComponent#getWidgetVar()
	 */
	public String getWidgetVar() {
		return getMemoWidgetName()+this.hashCode();
	}

}
