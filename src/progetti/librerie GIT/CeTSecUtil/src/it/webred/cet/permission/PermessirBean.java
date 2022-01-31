package it.webred.cet.permission;

import java.util.ArrayList;
import java.util.HashMap;



 class PermessirBean
{
	private String						user;
	// non implementato
	private String						role;
	//private SSONameValuePair[]		listaProperties;
	private ArrayList		applications	= new ArrayList();
	private ArrayList		items			= new ArrayList();
	private HashMap<String, HashMap>	permissions		= new HashMap();
	private ArrayList<String>	permissionsList;
	

	public PermessirBean()
	{
		// TODO Auto-generated constructor stub
	}

	public ArrayList getApplications()
	{
		return applications;
	}





	protected void setPermissions(String application, String item, String permesso)
	{
		if(!this.applications.contains(application))
			this.applications.add(application);
		if(!this.items.contains(item))
			this.items.add(item);

		HashMap items ;
		HashMap perm =new HashMap();

		if (this.permissions.get(application) != null)
		{
			items = this.permissions.get(application);
			if (items.get(item) != null)
			{
				// perm=(HashMap)items.get(item);
				((HashMap) this.permissions.get(application).get(item)).put(permesso, permesso);
			}
			else
			{
				perm.put(permesso, permesso);
				this.permissions.get(application).put(item, perm);
			}
		}
		else
		{
			items = new HashMap();
			
			perm.put(permesso, permesso);
			items.put(item, perm);
			this.permissions.put(application, items);
		}

	}

	protected boolean getPermissions(String application, String item, String permesso)
	{
		HashMap items = this.permissions.get(application);
		HashMap perm;
		if (items != null)
		{
		//	System.out.println("Applicazione TROVATA --------- "+application);
			if (items.get(item) != null)
			{
			//	System.out.println("Item TROVATO --------- " + item);
				perm = (HashMap) items.get(item);
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
	

	public HashMap<String, HashMap> getPermissions()
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

	public ArrayList getItems()
	{
		return items;
	}

	public void setItems(ArrayList items)
	{
		this.items = items;
	}

	public void setApplications(ArrayList applications)
	{
		this.applications = applications;
	}

	
	
	/**
	 * Ricava una lista di valori relativi ad una certa proprietà
	 * @param nomeProperties Il nome della proprietà 
	 * @return La lista dei valori trovati, può essere vuota
	 */
	/*
	public ArrayList getListaPropertiesValues(String nomeProperties )
	{
		ArrayList listaValues= new ArrayList();
		if (listaProperties.length>0)
		{
			for (SSONameValuePair ssoNameValue: listaProperties)
			{
				if(ssoNameValue.getName().equals(nomeProperties))
					listaValues.add(ssoNameValue.getValue());
			} 	
		}
		return listaValues;
	}

	public SSONameValuePair[] getListaProperties()
	{
		return listaProperties;
	}

	public void setListaProperties(SSONameValuePair[] listaProperties)
	{
		this.listaProperties = listaProperties;
	}
	
	
	*/
	
	

}