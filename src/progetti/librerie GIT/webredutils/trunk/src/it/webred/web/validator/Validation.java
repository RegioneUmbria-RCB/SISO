package it.webred.web.validator;

import java.util.ResourceBundle;

/**
 * Classe astratta che tutte le classi di Validazione JavaScript devono
 * implementare
 * 
 * @author Pietro Bartoccioni
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:15:20 $
 */
public abstract class Validation
{
	private String			formName;

	private String			fieldName;

	private String			fieldDescrition;

	private static String	RESOURCE_FULL_QUALIFIED_NAME_MESSAGE	= "it.webred.web.validator.validations";

	public abstract String getElementFunction();

	public abstract String getValidationType();

	public abstract String getGroupFunction();

	public abstract String getFormValidationFunction();

	private Validation()
	{

	}

	public Validation(String formName, String fieldName, String fieldDescription)
	{
		this.formName = formName;
		this.fieldName = fieldName;
		this.fieldDescrition = fieldDescription;
	}

	public String toString()
	{
		return getElementFunction();
	}

	public String getFormName()
	{
		return formName;
	}

	public void setFormName(String formName)
	{
		this.formName = formName;
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}

	public String getFieldDescrition()
	{
		return fieldDescrition;
	}

	public void setFieldDescrition(String fieldDescrition)
	{
		this.fieldDescrition = fieldDescrition;
	}

	protected ResourceBundle getBundle()
	{
		return ResourceBundle.getBundle(RESOURCE_FULL_QUALIFIED_NAME_MESSAGE);

	}
}
