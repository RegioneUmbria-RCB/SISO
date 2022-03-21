package it.webred.utils;
import java.util.Date;
	public class StatusMonitorBean
	{
		private String	msg;
		private Date	dataInizio;
		private Date	dataAggiornamento;

		public StatusMonitorBean(String msg, Date dataInizio, Date dataAggiornamento)
		{
			this.msg = msg;
			this.dataInizio = dataInizio;
			this.dataAggiornamento = dataAggiornamento;
		}

		public Date getDataAggiornamento()
		{
		return dataAggiornamento;
		}

		public void setDataAggiornamento(Date dataAggiornamento)
		{
		this.dataAggiornamento = dataAggiornamento;
		}

		public Date getDataInizio()
		{
		return dataInizio;
		}

		public void setDataInizio(Date dataInizio)
		{
		this.dataInizio = dataInizio;
		}

		public String getMsg()
		{
		return msg;
		}

		public void setMsg(String msg)
		{
		this.msg = msg;
		}

	}