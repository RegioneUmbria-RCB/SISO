package it.webred.cs.csa.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(DateRangeValidator.VALIDATOR_ID)
public class DateRangeValidator implements Validator {
	
	public static final String VALIDATOR_ID = "dateRangeValidator";
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null) {
            return;
        }
         
        //Leave the null handling of startDate to required="true"
        Object startDateValue = component.getAttributes().get("startDate");
        if (startDateValue==null) {
            return;
        }
         
        Date startDate = (Date)startDateValue;
        Date endDate = (Date)value;
        
        //azzero orario
        String startStr = sdf.format(startDate);
        String endStr = sdf.format(endDate);
        try {
        	startDate = sdf.parse(startStr);
			endDate = sdf.parse(endStr);
			
			if (endDate.before(startDate))
	            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "La data inizio deve essere precedente alla data fine", ""));
		} catch (ParseException e) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore durante conversione delle date", ""));
		}
	}

}
