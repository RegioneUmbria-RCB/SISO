package it.webred.web.validator;

import static it.webred.web.validator.ValidationType.MASK;

import java.text.MessageFormat;

public class MaskValidation extends Validation
{
	// Esempi di Mask
	// Solo Caratteri /^[a-zA-Z]*$/
	// Cap /^\\d{5}(-\\d{4})?$/
	String	mask	= "";

	public String getMask()
	{
		return mask;
	}

	public void setMask(String datePattern)
	{
		this.mask = datePattern;
	}

	private MaskValidation(String formName, String fieldName, String fieldDescription)
	{
		super(formName, fieldName, fieldDescription);
	}

	public MaskValidation(String formName, String fieldName, String fieldDescription, String mask)
	{
		this(formName, fieldName, fieldDescription);
		this.mask = mask;
	}

	@Override
	public String getElementFunction()
	{

		String messaggio = MessageFormat.format(getBundle().getString("errors.invalid"), getFieldDescrition());
		String function = "new Array(\"" + getFieldName() + "\", replaceCharCode(\"" + messaggio + "\"), new Function (\"varName\", \"this.mask=" + getMask() + "; return this[varName];\"));";
		return function;
	}

	@Override
	public String getValidationType()
	{
		return MASK;
	}

	@Override
	public String getGroupFunction()
	{
		// Ritorna il nome e la Firma della Funzione compreso il carattere "{"
		return "function " + getFormName() + "_mask () {";
	}

	@Override
	public String getFormValidationFunction()
	{
		// ritorna la funzione da chiamare all'interno della funzione
		// validate<NomeForm>
		return "validateMask(form)";
	}

}
