package it.webred.web.validator;

import static it.webred.web.validator.ValidationType.MINLENGTH;

import java.text.MessageFormat;

public class MinLengthValidation extends Validation
{
	String	minLength	= "10";

	public String getMinLength()
	{
		return minLength;
	}

	public void setMinLength(String maxLength)
	{
		this.minLength = maxLength;
	}

	private MinLengthValidation(String formName, String fieldName, String fieldDescription)
	{
		super(formName, fieldName, fieldDescription);
	}

	public MinLengthValidation(String formName, String fieldName, String fieldDescription, String minLength)
	{
		this(formName, fieldName, fieldDescription);
		this.minLength = minLength;
	}

	@Override
	public String getElementFunction()
	{

		String messaggio = MessageFormat.format(getBundle().getString("errors.minlength"), getFieldDescrition(), getMinLength());
		String function = "new Array(\"" + getFieldName() + "\", replaceCharCode(\"" + messaggio + "\"), new Function (\"varName\", \"this.minlength='" + getMinLength() + "'; return this[varName];\"));";
		return function;
	}

	@Override
	public String getValidationType()
	{
		return MINLENGTH;
	}

	@Override
	public String getGroupFunction()
	{
		// Ritorna il nome e la Firma della Funzione compreso il carattere "{"
		return "function " + getFormName() + "_minlength () {";
	}

	@Override
	public String getFormValidationFunction()
	{
		// ritorna la funzione da chiamare all'interno della funzione
		// validate<NomeForm>
		return "validateMinLength(form)";
	}

}
