package it.webred.jsf.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(DateValidator.VALIDATOR_ID)
public class DateValidator implements Validator {
	
	public static final String VALIDATOR_ID = "dateValidator";
	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null) {
			return;
		}
		try {
			String dateStr = ((UIInput)component).getSubmittedValue().toString();
			Date d = SDF.parse(dateStr);
			if (!SDF.format(d).equals(dateStr)) {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato della data non valido", ""));
			}
		} catch (Exception e) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato della data non valido", ""));
		}
	}

}
