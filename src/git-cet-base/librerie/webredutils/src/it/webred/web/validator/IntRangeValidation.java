package it.webred.web.validator;

import static it.webred.web.validator.ValidationType.INT_RANGE;

import java.text.MessageFormat;

;
public class IntRangeValidation extends Validation
{
	Integer	min;
	Integer	max;

	private IntRangeValidation(String formName, String fieldName, String fieldDescription)
	{
		super(formName, fieldName, fieldDescription);
	}

	public IntRangeValidation(String formName, String fieldName, String fieldDescription, Integer min, Integer max)
	{
		this(formName, fieldName, fieldDescription);
		this.max = max;
		this.min = min;
	}

	@Override
	public String getElementFunction()
	{

		String messaggio = MessageFormat.format(getBundle().getString("errors.range"), getFieldDescrition(), getMin(), getMax());
		String function = "new Array(\"" + getFieldName() + "\", replaceCharCode(\"" + messaggio + "\"), new Function (\"varName\", \"this.min='" + getMin() + "'; this.max='" + getMax() + "'; return this[varName];\"));";
		return function;
	}

	@Override
	public String getValidationType()
	{
		return INT_RANGE;
	}

	@Override
	public String getGroupFunction()
	{
		// Ritorna il nome e la Firma della Funzione compreso il carattere "{"
		return "function " + getFormName() + "_intRange () {";
	}

	@Override
	public String getFormValidationFunction()
	{
		// ritorna la funzione da chiamare all'interno della funzione
		// validate<NomeForm>
		return "validateIntRange(form)";
	}

	public Integer getMax()
	{
		return max;
	}

	public void setMax(Integer max)
	{
		this.max = max;
	}

	public Integer getMin()
	{
		return min;
	}

	public void setMin(Integer min)
	{
		this.min = min;
	}

}
