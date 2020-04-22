//Name: Isaac Blackwood
//Net ID: idb170030
package Redbox;

import java.util.Scanner;

public abstract class Transaction 
{
	private String title;
	
	public static Transaction toTransaction(String transationLogLine) throws DataFileFormatException
	{
		try 
		{
			Scanner scanner = new Scanner(transationLogLine);
			TransactionType type = TransactionType.toType(scanner.next());
			scanner.close();
			//remove the part already read and processed from the string
			transationLogLine = transationLogLine.replaceFirst(type.toString() + " ", "");
			//check which type of transaction should be created, and call the appropriate constructor
			switch (type) 
			{
			case ADD:
				return new AddTransaction(transationLogLine);
			case REMOVE:
				return new RemoveTransaction(transationLogLine);
			case RENT:
				return new RentTransaction(transationLogLine);
			case RETURN:
				return new ReturnTransaction(transationLogLine);
			default:
				throw new DataFileFormatException();
			}
			
		}
		catch (IllegalArgumentException e)
		{
			throw new DataFileFormatException();
		}
	}
	
	//constructors
	protected Transaction() {};
	protected Transaction(String title)
	{
		this.title = title;
	}
	
	public String title()
	{
		return title;
	}
	public String title(String title) 
	{
		return this.title = title;
	}
	
	@Override
	public abstract String toString();
	
	public abstract TransactionType transactionType();
}
