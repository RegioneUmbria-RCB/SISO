package it.webred.web.validator;

import static it.webred.web.validator.ValidationType.BYTE;
import static it.webred.web.validator.ValidationType.FLOAT;
import static it.webred.web.validator.ValidationType.INTEGER;
import static it.webred.web.validator.ValidationType.SHORT;

import java.text.MessageFormat;

public class NumberValidation extends Validation
{
	private String	validationType	= INTEGER;

	private NumberValidation(String formName, String fieldName, String fieldDescription)
	{
		super(formName, fieldName, fieldDescription);
	}

	public NumberValidation(String formName, String fieldName, String fieldDescription, String validationType) throws Exception
	{
		this(formName, fieldName, fieldDescription);
		this.validationType = validationType;
		if (!BYTE.equals(validationType) && !FLOAT.equals(validationType) && !INTEGER.equals(validationType) && !SHORT.equals(validationType))
		{
			throw new Exception("DataTypeValidation può essere instanziato solo per validazioni di tipo BYTE,DATE,FLOAT,INTEGER,SHORT");
		}
	}

	@Override
	public String getElementFunction()
	{
		String errorString = getErrorString();
		// se il ValidationType non è corretto ritorno stringa vuota
		if ("".equals(errorString))
			return "";
		String messaggio = MessageFormat.format(getBundle().getString(errorString), getFieldDescrition());
		String function = "new Array(\"" + getFieldName() + "\", replaceCharCode(\"" + messaggio + "\"), new Function (\"varName\", \" return this[varName];\"));";
		return function;
	}

	private String getErrorString()
	{
		String theString = "";
		if (BYTE.equals(validationType))
			theString = "errors.byte";
		else if (FLOAT.equals(validationType))
			theString = "errors.float";
		else if (INTEGER.equals(validationType))
			theString = "errors.integer";
		else if (SHORT.equals(validationType))
			theString = "errors.short";
		return theString;
	}

	@Override
	public String getValidationType()
	{
		return validationType;
	}

	@Override
	public String getGroupFunction()
	{
		// Ritorna il nome e la Firma della Funzione compreso il carattere "{"
		String groupFunction = "";
		if (BYTE.equals(validationType))
			groupFunction = "function " + getFormName() + "_ByteValidations () { ";
		else if (FLOAT.equals(validationType))
			groupFunction = "function " + getFormName() + "_FloatValidations () { ";
		else if (INTEGER.equals(validationType))
			groupFunction = "function " + getFormName() + "_IntegerValidations () { ";
		else if (SHORT.equals(validationType))
			groupFunction = "function " + getFormName() + "_ShortValidations () { ";

		return groupFunction;
	}

	@Override
	public String getFormValidationFunction()
	{
		// ritorna la funzione da chiamare all'interno della funzione
		// validate<NomeForm>
		String formFunction = "";
		if (BYTE.equals(validationType))
			formFunction = "validateByte(form)";
		else if (FLOAT.equals(validationType))
			formFunction = "validateFloat(form)";
		else if (INTEGER.equals(validationType))
			formFunction = "validateInteger(form)";
		else if (SHORT.equals(validationType))
			formFunction = "validateShort(form)";
		return formFunction;
	}

}
