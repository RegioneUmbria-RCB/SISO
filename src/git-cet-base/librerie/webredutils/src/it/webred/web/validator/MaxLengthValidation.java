package it.webred.web.validator;

import static it.webred.web.validator.ValidationType.MAXLENGTH;

import java.text.MessageFormat;

public class MaxLengthValidation extends Validation
{
	String	maxLength	= "10";

	public String getMaxLength()
	{
		return maxLength;
	}

	public void setMaxLength(String maxLength)
	{
		this.maxLength = maxLength;
	}

	private MaxLengthValidation(String formName, String fieldName, String fieldDescription)
	{
		super(formName, fieldName, fieldDescription);
	}

	public MaxLengthValidation(String formName, String fieldName, String fieldDescription, String maxLength)
	{
		this(formName, fieldName, fieldDescription);
		this.maxLength = maxLength;
	}

	@Override
	public String getElementFunction()
	{

		String messaggio = MessageFormat.format(getBundle().getString("errors.maxlength"), getFieldDescrition(), getMaxLength());
		String function = "new Array(\"" + getFieldName() + "\", replaceCharCode(\"" + messaggio + "\"), new Function (\"varName\", \"this.maxlength='" + getMaxLength() + "'; return this[varName];\"));";
		return function;
	}

	@Override
	public String getValidationType()
	{
		return MAXLENGTH;
	}

	@Override
	public String getGroupFunction()
	{
		// Ritorna il nome e la Firma della Funzione compreso il carattere "{"
		return "function " + getFormName() + "_maxlength () {";
	}

	@Override
	public String getFormValidationFunction()
	{
		// ritorna la funzione da chiamare all'interno della funzione
		// validate<NomeForm>
		return "validateMaxLength(form)";
	}

}
