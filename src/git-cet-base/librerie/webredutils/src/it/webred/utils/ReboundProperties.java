package it.webred.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class ReboundProperties
{

	public ReboundProperties()
	{
	}

	public static void main(String args[])
			throws Exception
	{
		if (args.length < 3)
			throw new Exception("Usage agrs: filein fileout property=value property2=value2 ecc.");
		File file = new File(args[0]);
		if (!file.exists())
			throw new Exception("Invalid file name.");
		Properties p = new Properties();
		p.load(new FileInputStream(file));
		for (int i = 2; i < args.length; i++)
		{
			int idx = -1;
			if ((idx = args[i].indexOf("=")) < 1)
				throw new Exception("Invalid property.");
			p.setProperty(args[i].substring(0, idx), args[i].substring(idx + 1, args[i].length()));
		}

		p.store(new FileOutputStream(new File(args[1])), "Rebounded");
	}
}