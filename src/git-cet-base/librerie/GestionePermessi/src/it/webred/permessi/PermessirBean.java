package it.webred.permessi;
import java.util.ArrayList;
import java.util.HashMap;

class PermessirBean
{
	private String user;
	private String role;
	private NameValuePair[] listaProperties;
	private ArrayList<String> applications = new ArrayList<String>();
	private ArrayList<String> items = new ArrayList<String>();
	private HashMap<String, HashMap<String, HashMap<String, String>>> permissions = new HashMap<String, HashMap<String, HashMap<String, String>>>();
	private ArrayList<String> permissionsList;
	
	public PermessirBean()
	{
	}

	public ArrayList<String> getApplications()
	{
		return applications;
	}

	protected void setPermissions(String application, String item, String permesso)
	{
		if(!this.applications.contains(application))
			this.applications.add(application);
		if(!this.items.contains(item))
			this.items.add(item);

		HashMap<String, String> perm = new HashMap<String, String>();

		HashMap<String, HashMap<String, String>> items = this.permissions.get(application);
		if (items != null)
		{
			if (items.get(item) != null)
			{
				perm = items.get(item);
				perm.put(permesso, permesso);
			}
			else
			{
				perm.put(permesso, permesso);
				items.put(item, perm);
			}
		}
		else
		{
			items = new HashMap<String, HashMap<String, String>>();

			perm.put(permesso, permesso);
			items.put(item, perm);
			this.permissions.put(application, items);
		}

	}

	protected boolean getPermissions(String application, String item, String permesso)
	{
		HashMap<String, HashMap<String, String>> items = this.permissions.get(application);
		HashMap<String, String> perm = null;

		if (items != null)
		{
			//	System.out.println("Applicazione TROVATA --------- "+application);
			if (items.get(item) != null)
			{
				//	System.out.println("Item TROVATO --------- " + item);
				perm = items.get(item);
				if (perm.get(permesso) != null)
				{
				//	System.out.println("Permesso TROVATO --------- " + permesso);
					return true;
				}
				else
				{
					//System.out.println("Permesso non trovato --------- " + permesso);
					return false;
				}
			}
			else
			{
				//System.out.println("Item non trovato --------- " + item);
				return false;
			}
		}
		else
		{
			//System.out.println("Applicazione non trovata --------- " + application);
			return false;
		}
	}
	

	public HashMap<String, HashMap<String, HashMap<String, String>>> getPermissions()
	{
		return permissions;
	}

	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;
	}

	public String getUser()
	{
		return user;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public ArrayList<String> getPermissionsList()
	{
		return permissionsList;
	}

	public void setPermissionsList(ArrayList<String> permissionsList)
	{
		this.permissionsList = permissionsList;
	}

	public ArrayList<String> getItems()
	{
		return items;
	}

	public void setItems(ArrayList<String> items)
	{
		this.items = items;
	}

	public void setApplications(ArrayList<String> applications)
	{
		this.applications = applications;
	}

	/**
	 * Ricava una lista di valori relativi ad una certa proprietà
	 * @param nomeProperty Il nome della proprietà 
	 * @return La lista dei valori trovati, può essere vuota
	 */
	public ArrayList<String> getListaPropertiesValues(String nomeProperty )
	{
		ArrayList<String> listaValues= new ArrayList<String>();
		if (listaProperties.length>0)
		{
			for (NameValuePair nameValue: listaProperties)
			{
				if(nameValue.getName().equals(nomeProperty))
					listaValues.add(nameValue.getValue());
			} 	
		}
		return listaValues;
	}

	public NameValuePair[] getListaProperties()
	{
		return listaProperties;
	}

	public void setListaProperties(NameValuePair[] listaProperties)
	{
		this.listaProperties = listaProperties;
	}
	
	
	
	
	

}