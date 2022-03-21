package it.webred.utils;
import java.io.File;

import org.apache.log4j.Logger;
/**
 * Utility per cancellare file sul FS. <br>
 * Da utilizzare per cancellare file che potrebbero non essere subito disponibili alla cancellazione perche ancora occupati da un altro processo. <br>
 * Questo thread prova a cancellare per N (default 10) volte ogni N (default 5) secondi il file.
 * @author tux
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:15:20 $
 */
public class DeleteFileThread implements Runnable
{
	String					file	= null;
	int quanteVolte=10;
	int ogniQuantiSecondi=5;
	private static final Logger	log		= Logger.getLogger(DeleteFileThread.class.getName());

	public DeleteFileThread(String file)
	{
		this.file = file;
	}

	public void run()
	{
		try
		{
			int trynumber = 0;
			while (trynumber <= quanteVolte)
			{
				if (new File(file).delete())
					break;
				trynumber++;
				synchronized (this)
				{
					wait(ogniQuantiSecondi * 1000);
				}

			}
			
		}
		catch (Exception e)
		{
			log.error("Erroe in cancellazione file", e);
		}
	}

	public int getOgniQuantiSecondi()
	{
		return ogniQuantiSecondi;
	}

	public void setOgniQuantiSecondi(int ogniQuantiSecondi)
	{
		this.ogniQuantiSecondi = ogniQuantiSecondi;
	}

	public int getQuanteVolte()
	{
		return quanteVolte;
	}

	public void setQuanteVolte(int quanteVolte)
	{
		this.quanteVolte = quanteVolte;
	}
	
	

}
