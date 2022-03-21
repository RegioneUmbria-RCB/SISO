package it.webred.utils.type.def;

public abstract class DeclarativeType implements ReType
{

		public DeclarativeType(String name, String type, Object value)
		{
			this.name = name;
			this.type = type;
			this.value = value;
		}
		
		private String name;
		private String type;
		private Object value;
		


		public String getName()
		{
			return name;
		}

		public String getType()
		{
			return type;
		}

		public Object getValue()
		{
			return value;
		}

		public Object getObjectValue()
		{
			
			return value;
		}

}
