//Name: Isaac Blackwood
//Net ID: idb170030
package Redbox;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveTransaction extends Transaction 
{
	private final String ADD_REMOVE_FORMAT_PATTERN_STRING = "(\")(.*)(\")(,\\s*)(\\d+)";//this is supposed to represent "anything in the outermost "double quotes" found" and then a ',' and 0 or more whitespace characters before an integer
	private int numberToRemove;

	public RemoveTransaction(String transactionLogLine) throws DataFileFormatException
	{
		super();
		try
		{
			Pattern pattern = Pattern.compile(ADD_REMOVE_FORMAT_PATTERN_STRING); 
			Matcher matcher = pattern.matcher(transactionLogLine);
			matcher.find();
			if (!matcher.group().equals(transactionLogLine))
			{
				//there is extra stuff on the line 
				throw new DataFileFormatException();
			}
			String title = matcher.group(2); //this should get everything from the outermost double quotes
			int numberToRemove = Integer.parseInt(matcher.group(5)); //this should get the number
			//assign the values from the string
			title(title);			
			numberToRemove(numberToRemove);
		}
		catch(IllegalStateException e)
		{
			//this line was bad
			throw new DataFileFormatException();
		}
	}
	
	public int numberToRemove()
	{
		return numberToRemove;
	}
	public int numberToRemove(int numberToRemove)
	{
		return this.numberToRemove = numberToRemove;
	}

	@Override 
	public String toString() 
	{
		return transactionType() +  " \"" + title() + "\"," + numberToRemove();
	}

	@Override
	public TransactionType transactionType() 
	{
		return TransactionType.REMOVE;
	}
}
