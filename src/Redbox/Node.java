//Name: Isaac Blackwood
//Net ID: idb170030
package Redbox;

public class Node implements Traversable<Node>
{
	private String title; //this should be a unique identifier for use with my BinarySearchTree class
	private int available;
	private int rented;
	private Node left;
	private Node right;
	
	//constructors
	public Node() 
	{
		this(null, 0, 0, null, null);
		//empty for expansion
	}
	public Node(String title, int available, int rented, Node left, Node right)
	{
		title(title);
		available(available);
		rented(rented);
		left(left);
		right(right);
	}
	public Node(String title, int available, int rented)
	{
		this(title, available, rented, null, null);
	}
	public Node(String title, int available)
	{
		this(title, available, 0, null, null);
	}
	public Node(String title)
	{
		this(title, 0, 0, null, null);
	}

	//getters and setters
	public String title() 
	{
		return title;
	}
	public String title(String title) 
	{
		return this.title = title;
	}
	public int available() 
	{
		return available;
	}
	public int available(int available) 
	{
		int potentialAmount = available;
		if (potentialAmount < 0)
		{
			throw new IllegalArgumentException("There must be at least 0 available.");
		}
		return this.available = available;
	}
	public int rented() 
	{
		return rented;
	}
	public int rented(int rented) 
	{
		int potentialAmount = rented;
		if (potentialAmount < 0)
		{
			throw new IllegalArgumentException("There must be at least 0 rented.");
		}
		return this.rented = rented;
	}
	@Override
	public Node left() 
	{
		return left;
	}
	@Override
	public Node left(Node left) 
	{
		return this.left = left;
	}
	@Override
	public Node right() 
	{
		return right;
	}
	@Override
	public Node right(Node right) 
	{
		return this.right = right;
	}
	
	//Object methods
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		Node other = (Node) obj;
		if (title == null) 
		{
			if (other.title != null)
			{
				return false;
			}
		} 
		else if (!title.equals(other.title))
		{
			return false;
		}
		return true;
	}
	@Override
	public String toString()
	{
		return title;
	}
	
	//comparable
	@Override
	public int compareTo(Node node) 
	{
		return title().compareTo(node.title());
	}
	
	public int changeAvailable(int amount)
	{
		return available(available() + amount);
	}
	
	public int changeRented(int amount)
	{
		return rented(rented() + amount);
	}
}
