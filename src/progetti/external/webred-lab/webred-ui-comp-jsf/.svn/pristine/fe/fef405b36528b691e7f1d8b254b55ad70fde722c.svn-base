package it.webred.jsf.utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator(CodFiscValidator.VALIDATOR_ID)
public class CodFiscValidator implements Validator {
	
	public static final String VALIDATOR_ID = "codFiscValidator";
	public static final String REGEX = "(^[A-Za-z]{6}[A-Za-z0-9]{2}[ABCDEHLMPRSTabcdehlmprst]{1}[A-Za-z0-9]{2}[A-Za-z]{1}[A-Za-z0-9]{3}[A-Za-z]{1}$)?";
	
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (context.getRenderResponse()) {
			if (value == null || value.equals("")) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ((UIInput)component).getRequiredMessage(), null);
		        context.addMessage(null, message);
			} else {
				try {				
					if (!value.toString().matches(REGEX)) {
						throw new ValidatorException(new FacesMessage("Codice fiscale non valido"));
					}
				} catch (Exception e) {
					throw new ValidatorException(new FacesMessage("Codice fiscale non valido"));
				}
			}
		}
	}

}
