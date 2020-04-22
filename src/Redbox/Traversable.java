//Name: Isaac Blackwood
//Net ID: idb170030
package Redbox;

public interface Traversable<T> extends Comparable<T>
{
	public abstract T left(); //should return the node that this points to on the left
	public abstract T right(); //should return the node that this points to on the right
	public abstract T left(T left); //should set the passed node as the new left
	public abstract T right(T right); //should set the passed node as the new right
}
