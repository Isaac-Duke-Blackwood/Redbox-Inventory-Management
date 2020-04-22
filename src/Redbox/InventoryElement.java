//Name: Isaac Blackwood
//Net ID: idb170030
package Redbox;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InventoryElement 
{
	private final String INVENTORY_ELEMENT_PATTERN_STRING = "(\")(.*)(\")(,\\s*)(\\d+)(,\\s*)(\\d+)";
	private Node node;
	public InventoryElement(String lineOfInventoryFile) throws DataFileFormatException
	{
		try 
		{
		Pattern pattern = Pattern.compile(INVENTORY_ELEMENT_PATTERN_STRING); 
		Matcher matcher = pattern.matcher(lineOfInventoryFile);
		matcher.find();
		String title = matcher.group(2);
		int available = Integer.parseInt(matcher.group(5));
		int rented = Integer.parseInt(matcher.group(7));
		node(new Node(title, available, rented));
		
		}
		catch (IllegalStateException e)
		{
			//this line was bad
			throw new DataFileFormatException();
		}
		
	}
	public Node node()
	{
		return node;
	}
	public Node node(Node node) 
	{
		return this.node = node;
	}
	
	@Override
	public String toString() 
	{
		return "Title: " + node.title() + "\nAvailable: " + node.available() + "\nRented: " + node.rented();
	}
}
