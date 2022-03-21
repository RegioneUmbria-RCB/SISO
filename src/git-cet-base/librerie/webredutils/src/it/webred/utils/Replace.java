package it.webred.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.StringCharacterIterator;

public class Replace
{
	public static String secureString(String aRegexFragment)
	{
		if (aRegexFragment == null)
			return null;
		final StringBuffer result = new StringBuffer();

		final StringCharacterIterator iterator = new StringCharacterIterator(aRegexFragment);
		char character = iterator.current();
		while (character != StringCharacterIterator.DONE)
		{
			if (character == '\\' || character == '?' || character == '&' || character == '{' || character == '}' || character == '$' || character == '+' || character == '^')
			{
				result.append("");
			}
			else if (character == '\'' || character == '"')
				result.append("\'\'");
			else
			{
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}

	public static String forHTMLTag(String aTagFragment)
	{
		if (aTagFragment == null)
			return null;

		final StringBuffer result = new StringBuffer();

		final StringCharacterIterator iterator = new StringCharacterIterator(aTagFragment);
		char character = iterator.current();
		while (character != StringCharacterIterator.DONE)
		{
			if (character == '<')
			{
				result.append("&lt;");
			}
			else if (character == '>')
			{
				result.append("&gt;");
			}
			else if (character == '\"')
			{
				result.append("&quot;");
			}
			else if (character == '\'')
			{
				result.append("&#039;");
			}
			else if (character == '\\')
			{
				result.append("&#092;");
			}
			else if (character == '&')
			{
				result.append("&amp;");
			}
			else
			{
				// the char is not a special one
				// add it to the result as is
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}

	public static String forURL(String aURLFragment)
	{
		if (aURLFragment == null)
			return null;
		String result = null;
		try
		{
			result = URLEncoder.encode(aURLFragment, "UTF-8");
		}
		catch (UnsupportedEncodingException ex)
		{
			throw new RuntimeException("UTF-8 not supported", ex);
		}
		return result;
	}

}
