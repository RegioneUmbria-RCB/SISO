package it.webred.web.validator;

import static it.webred.web.validator.ValidationType.EMAIL;

import java.text.MessageFormat;

public class EmailValidation extends Validation
{
	public EmailValidation(String formName, String fieldName, String fieldDescription)
	{
		super(formName, fieldName, fieldDescription);
	}

	@Override
	public String getElementFunction()
	{

		String messaggio = MessageFormat.format(getBundle().getString("errors.email"), getFieldDescrition());
		String function = "new Array(\"" + getFieldName() + "\", replaceCharCode(\"" + messaggio + "\"), new Function (\"varName\", \" return this[varName];\"));";
		return function;
	}

	@Override
	public String getValidationType()
	{
		return EMAIL;
	}

	@Override
	public String getGroupFunction()
	{
		// Ritorna il nome e la Firma della Funzione compreso il carattere "{"
		return "function " + getFormName() + "_email () {";
	}

	@Override
	public String getFormValidationFunction()
	{
		// ritorna la funzione da chiamare all'interno della funzione
		// validate<NomeForm>
		return "validateEmail(form)";
	}

}
