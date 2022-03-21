package it.webred.web.validator;

import static it.webred.utils.StringUtils.isEmpty;
import it.webred.utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;

public class ValidatorsHolder
{
	private String										submittingVariableName		= "";

	private Hashtable<String, ArrayList<Validation>>	forms						= new Hashtable<String, ArrayList<Validation>>();

	private final static  String						VALIDATOR_JAVASCRIPT_PATH	= "validator.js";

	public String getSubmittingVariableName()
	{
		return submittingVariableName;
	}

	public void setSubmittingVariableName(String submittingVariableName)
	{
		this.submittingVariableName = submittingVariableName;
	}

	public void addValidation(Validation validation)
		throws Exception
	{
		if (validation == null || StringUtils.isEmpty(validation.getFormName()))
			throw new Exception("Impossibile Aggiungere una validazione = null o che ha formName==null");
		if (null == forms.get(validation.getFormName()))
			forms.put(validation.getFormName(), new ArrayList<Validation>());
		getValidations(validation.getFormName()).add(validation);
	}

	public ArrayList<Validation> getValidations(String formName)
	{
		return forms.get(formName);
	}

	protected void setValidations(ArrayList<Validation> validations)
	{
		for (Validation validation : validations)
		{
			if (null == forms.get(validation.getFormName()))
				forms.put(validation.getFormName(), new ArrayList<Validation>());
			forms.get(validation.getFormName()).add(validation);
		}
	}

	public String getValidateScript()
	{
		return buildFunctionsAllForm();
	}

	public String buildFunctionsAllForm()
	{
		String theFunction = "";
		// ciclo tutte le form presenti e ne genero le funzioni.
		for (Map.Entry<String, ArrayList<Validation>> form : forms.entrySet())
		{
			theFunction += buildFunctionsForm(form.getKey());
		}

		return theFunction;
	}

	public String buildFunctionsForm(String formName)
	{
		// Costruisco tutte le funzioni per il form.

		StringBuffer sbValidateForm = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		// Ordinamento delle Validation per tipologia
		// ObjectComparator comp = new ObjectComparator("validationType");
		ArrayList<Validation> validations = forms.get(formName);
		Collections.sort(validations, new Comparator<Validation>() {

			public int compare(Validation o1, Validation o2)
			{
				if (o1.getValidationType() == null)
				{
					if (o2.getValidationType() == null)
						return 0;
					else
						return -1;
				}
				else if (o2.getValidationType() == null)
				{
					return 1;
				}
				else
					return o1.getValidationType().compareTo(o2.getValidationType());
			}

		});

		if (validations.size() > 0)
		{
			sbValidateForm.append("<script>");
			// costruisco la funzione di validazione del form
			String firstLetter = formName.substring(0, 1);
			sbValidateForm.append(" function validate" + firstLetter.toUpperCase() + formName.substring(1) + "(form) { \n");
			if (!isEmpty(getSubmittingVariableName()))
			{
				sbValidateForm.append("\t\t if (" + getSubmittingVariableName() + ") \n");
				sbValidateForm.append("\t\t\t return true; \n");
				sbValidateForm.append("\t\t else \n");
			}

			String validationType = "", currentFunction = "", validationFunctions = "";
			char firstChar = 'a', secondChar = 'a';

			for (Validation validation : validations)
			{
				// per ogni tipo di validazione azzero i caratteri del vettore
				// delle funzioni
				// Chiudo la funzione precedente se l'ho aperta ed inizializzo
				// la funzione corrente
				if (!validationType.equals(validation.getValidationType()))
				{
					firstChar = 'a';
					secondChar = 'a';
					if ("".equals(validationFunctions))
						validationFunctions += validation.getFormValidationFunction();
					else
						validationFunctions += " && " + validation.getFormValidationFunction();
					if (!"".equalsIgnoreCase(currentFunction))
					{
						// chiudo la precedente
						sb.append("}");
						sb.append("\n\n");
					}
					sb.append(validation.getGroupFunction());
					sb.append("\n");
					validationType = validation.getValidationType();
					currentFunction = validation.getGroupFunction();
				}

				sb.append("\tthis." + firstChar + secondChar + " =");
				sb.append(validation.getElementFunction());
				sb.append("\n");
				// Incremento i caratteri del vettore delle funzioni
				if (secondChar == 'z')
				{
					firstChar += 1;
					secondChar = 'a';
				}
				else
				{
					secondChar += 1;
				}

			}
			// chiudo l'ultima funzione ed il tag <script>
			sb.append("}\n");

			sbValidateForm.append("\t\t\t return ");
			sbValidateForm.append(validationFunctions);
			sbValidateForm.append(";\n}\n\n");
			// chiudo l'ultima funzione ed il tag <script>
			sb.append("</script>");
		}
		return sbValidateForm.toString() + sb.toString();
	}
	public static String getGlobalScript()
	{
		return getGlobalScript(true);
	}
	public static String getGlobalScript(boolean  incScript)
	{
		Class componente = ValidatorsHolder.class;
		StringBuffer theScript = new StringBuffer();
		InputStream is = componente.getResourceAsStream(VALIDATOR_JAVASCRIPT_PATH);
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		try
		{
			String linea = br.readLine();
			if(incScript) theScript.append("<script type=\"text/javascript\" language=\"Javascript1.1\"> "+"\n");
			while (linea != null)
			{
				theScript.append(linea + "\n");
				linea = br.readLine();
			}
			if(incScript) theScript.append("</script> "+"\n");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return theScript.toString();

	}

	@Override
	public String toString()
	{
		return getValidateScript() + getGlobalScript(true);
	}
}
