package it.webred.web.validator;

import static it.webred.web.validator.ValidationType.DATE;

import java.text.MessageFormat;

public class DateValidation extends Validation
{
	String	datePattern	= "dd/MM/yyyy";

	public String getDatePattern()
	{
		return datePattern;
	}

	public void setDatePattern(String datePattern)
	{
		this.datePattern = datePattern;
	}

	/**
	 * Costruttore con valore di default per datePattern="dd/MM/yyyy"
	 * 
	 * @param formName
	 *            Nome del form
	 * @param fieldName
	 *            nome della proprietà da validare
	 * @param fieldDescription
	 *            Descrizione della proprietà da validare
	 */
	public DateValidation(String formName, String fieldName, String fieldDescription)
	{
		super(formName, fieldName, fieldDescription);
	}

	/**
	 * Costruttore completo
	 * 
	 * @param formName
	 *            Nome del form
	 * @param fieldName
	 *            nome della proprietà da validare
	 * @param fieldDescription
	 *            Descrizione della proprietà da validare
	 * @param datePattern
	 *            pattern di validazione della data es. "dd/MM/yyyy"
	 */
	public DateValidation(String formName, String fieldName, String fieldDescription, String datePattern)
	{
		super(formName, fieldName, fieldDescription);
		this.datePattern = datePattern;
	}

	@Override
	public String getElementFunction()
	{

		String messaggio = MessageFormat.format(getBundle().getString("errors.date"), getFieldDescrition());
		String function = "new Array(\"" + getFieldName() + "\", replaceCharCode(\"" + messaggio + "\"), new Function (\"varName\", \"this.datePatternStrict='" + getDatePattern() + "'; return this[varName];\"));";
		return function;
	}

	@Override
	public String getValidationType()
	{
		return DATE;
	}

	@Override
	public String getGroupFunction()
	{
		// Ritorna il nome e la Firma della Funzione compreso il carattere "{"
		return "function " + getFormName() + "_DateValidations () {";
	}

	@Override
	public String getFormValidationFunction()
	{
		// ritorna la funzione da chiamare all'interno della funzione
		// validate<NomeForm>
		return "validateDate(form)";
	}

}
