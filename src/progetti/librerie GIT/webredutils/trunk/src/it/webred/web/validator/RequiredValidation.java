package it.webred.web.validator;

import static it.webred.web.validator.ValidationType.REQUIRED;

import java.text.MessageFormat;

public class RequiredValidation extends Validation
{
	public RequiredValidation(String formName, String fieldName, String fieldDescription)
	{
		super(formName, fieldName, fieldDescription);
	}

	@Override
	public String getElementFunction()
	{

		String messaggio = MessageFormat.format(getBundle().getString("errors.required"), getFieldDescrition());
		String function = "new Array(\"" + getFieldName() + "\", replaceCharCode(\"" + messaggio + "\"), new Function (\"varName\", \" return this[varName];\"));";
		return function;
	}

	@Override
	public String getValidationType()
	{
		return REQUIRED;
	}

	@Override
	public String getGroupFunction()
	{
		// Ritorna il nome e la Firma della Funzione compreso il carattere "{"
		return "function " + getFormName() + "_required () {";
	}

	@Override
	public String getFormValidationFunction()
	{
		// ritorna la funzione da chiamare all'interno della funzione
		// validate<NomeForm>
		return "validateRequired(form)";
	}

}
