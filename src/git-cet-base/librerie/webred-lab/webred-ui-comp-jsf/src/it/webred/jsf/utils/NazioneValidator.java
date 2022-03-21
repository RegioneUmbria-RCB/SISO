package it.webred.jsf.utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(NazioneValidator.VALIDATOR_ID)
public class NazioneValidator implements Validator {
	
	public static final String VALIDATOR_ID = "nazioneValidator";
	
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null && context.getRenderResponse() && ((UIInput)component).getValidatorMessage() != null && !((UIInput)component).getValidatorMessage().equals("")) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ((UIInput)component).getValidatorMessage(), null);
	        context.addMessage(null, message);
		}
	}

}