package it.webred.rulengine.brick.bsh;

import it.webred.rulengine.brick.bean.CommandAck;

public class Result
{
	private CommandAck ack;
	private Object result;
	
	public Result(CommandAck ca, Object res) {
		this.ack = ca;
		this.result = res;
	}

	public CommandAck getAck()
	{
		return ack;
	}

	public void setAck(CommandAck ca)
	{
		this.ack = ca;
	}

	public Object getResult()
	{
		return result;
	}

	public void setResult(Object result)
	{
		this.result = result;
	}

}
