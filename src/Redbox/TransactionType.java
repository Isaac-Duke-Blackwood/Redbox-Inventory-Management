//Name: Isaac Blackwood
//Net ID: idb170030
package Redbox;

public enum TransactionType 
{
	ADD("add"), REMOVE("remove"), RENT("rent"), RETURN("return");
	private String string;
	private TransactionType(String string)
	{
		this.string = string;
	}
	public String toString()
	{
		return string;
	}
	public static TransactionType toType(String string)
	{
		if (string.equals("add"))
		{
			return ADD;
		}
		else if (string.equals("remove")) 
		{
			return REMOVE;
		}
		else if (string.equals("rent")) 
		{
			return RENT;
		}
		else if ( string.equals("return"))
		{
			return RETURN;
		}
		else 
		{
			throw new IllegalArgumentException("Something other than \"add\", \"remove\", \"rent\", or\"return\" was passed to this function.");
		}
	}
}
