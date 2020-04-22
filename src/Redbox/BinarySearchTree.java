//Name: Isaac Blackwood
//Net ID: idb170030
package Redbox;

import java.lang.IllegalArgumentException;

public class BinarySearchTree<T extends Traversable<T>>
{
	private T root;
	
	//constructors
	public BinarySearchTree()
	{
		root = null;
	}
	
	public T root()
	{
		return root;
	}
	public T root(T root)
	{
		return this.root = root;
	}
	public T insert(T newNode) //returns the node inserted or null, will throw an error if the element is in the tree already
	{
		if (null == newNode)
		{
			return null;
		}
		else if (isEmpty())
		{
			return root = newNode;
		}
		return insert(newNode, root);
	}
	private T insert(T newNode, T root) //returns the node inserted (needs to be recursive according to the project instructions)
	{
		if (newNode.compareTo(root) < 0) 
		{
			//the new node should be inserted on the left subtree
			if (null == root.left())
			{
				return root.left(newNode);
			}
			return insert(newNode, root.left());
		}
		else if (newNode.compareTo(root) > 0)
		{
			//the new node should be inserted on the right subtree
			if (null == root.right())
			{
				return root.right(newNode);
			}
			return insert(newNode, root.right());
		}
		else
		{
			//the new node is a duplicate of root
			throw new IllegalArgumentException("The element \"" + newNode.toString() + "\" was already contained in the binary search tree, so it was not added.");
		}
	}
	public boolean isEmpty() 
	{
		if (null != root)
		{
			return false;
		}
		return true;
	}
	public T search(T node) //returns the found element or null if not in the tree
	{
		if (null == node)
		{
			return null;
		}
		return search(node, root);
	}
	private T search(T node, T root) //returns the found element or null (needs to be recursive by project requirements)
	{
		//Base case 1: node not found
		if (null == root)
		{
			return root;
		}
		//Base case 2: found node
		else if (0 == node.compareTo(root))
		{
			return root;
		}
		else if (node.compareTo(root) < 0)
		{
			//the node would be in the left sub tree
			return search(node, root.left());
		}
		else 
		{
			//the node would be in the right sub tree
			return search(node, root.right());
		}
	}
	public T delete(T node) //returns the deleted node, or null if the passed node was null throws IllegalArgument if the node is not in the tree
	{
		if (null == node)
		{
			return null;
		}
		//find the node to delete
		T nodeToDelete = search(node);
		if (null == nodeToDelete)
		{
			//the node doesn't exist
			throw new IllegalArgumentException("The element \"" + node.toString() + "\" could not be found in the tree, so it was not deleted. ");
		}
		
		//find the node that will replace the old node in the tree
		T replacementNode;
		if (null == nodeToDelete.right()) //replace the node with its left subtree
		{
			replacementNode = nodeToDelete.left();
		}
		else if (null == nodeToDelete.left()) //replace the node with its right subtree
		{
			replacementNode = nodeToDelete.right();
		}
		else //the node has both a left and right subtree so replace it with the rightmost node of the left subtree and then replace said node with its left subtree
		{
			replacementNode = rightmost(nodeToDelete.left());
			
			//replace references to replacementNode with references to its left subtree as 
			if (null != replacementNode) //if the replacementNode 
			{
				T parentOfReplacement = parent(replacementNode);
				
				//determine which side the replacementNode was on
				if (replacementNode.compareTo(parentOfReplacement) < 0)
				{
					//node was on the left
					parentOfReplacement.left(replacementNode.left());
				}
				else if (replacementNode.compareTo(parentOfReplacement) > 0)
				{
					//node was on the right
					parentOfReplacement.right(replacementNode.left());
				}
			}
			replacementNode.left(nodeToDelete.left());
			replacementNode.right(nodeToDelete.right());
		}
		
		//replace the old node with the new one
		nodeToDelete.left(null);
		nodeToDelete.right(null);
		if (nodeToDelete == root)
		{
			root = replacementNode;
		}
		else 
		{
			T parentOfDeleted = parent(nodeToDelete);
			//determine which side the replacementNode should be put on
			if (nodeToDelete.compareTo(parentOfDeleted) < 0)
			{
				//put node on left
				parentOfDeleted.left(replacementNode);
			}
			else if (nodeToDelete.compareTo(parentOfDeleted) > 0)
			{
				//put node on right
				parentOfDeleted.right(replacementNode);
			}
		}
		
		
		return node;
	}
	public T rightmost(T root) //returns the rightmost node on the tree
	{
		while(null != root.right())
		{
			root = root.right();
		}
		return root;
	}
	public T rightmost()
	{
		return rightmost(root());
	}
	public T leftmost(T root)
	{
		while(null != root.left())
		{
			root = root.left();
		}
		return root;
	}
	public T leftmost()
	{
		return leftmost(root());
	}

	public T parent(T node) //returns the parent of the passed node, or null if the root
	{
		if (null == node)
		{
			throw new IllegalArgumentException("A null pointer cannot have a parent.");
		}
		else if (0 == node.compareTo(root))
		{
			return null;
		}
		return parent(node, root, null);
	}

	private T parent(T node, T root, T parent) 
	{
		//The same as the search algorithm but keep track of and return the parent, not the found node
		if (0 == node.compareTo(root))
		{
			return parent;
		}
		else if (node.compareTo(root) < 0)
		{
			//the node would be in the left sub tree
			return parent(node, root.left(), root);
		}
		else 
		{
			//the node would be in the right sub tree
			return parent(node, root.right(), root);
		}
	}
	
	public boolean contains(T node)
	{
		if (null != search(node))
		{
			return true;
		}
		return false;
	}
	
	//iteration (these are sort of a workaround because i am not directly implementing the interface)
	public T nextFrom(T current) //return the next node or null
	{
		if (null != current.right())
		{
			return leftmost(current.right());
		}
		try 
		{
			while (parent(current).compareTo(current) < 0)
			{
				//trace back towards the root until the parent is larger
				current = parent(current);
			}
			return parent(current);
		}
		catch (NullPointerException e) 
		{
			//none of the parents were larger, so an exception was thrown when the parent of the root did not exist
			return null;
		}
	}
	public boolean hasNext(T current)
	{
		if (null != nextFrom(current))
		{
			return true;
		}
		return false;
	}
}

