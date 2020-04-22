//Name: Isaac Blackwood
//Net ID: idb170030
package Redbox;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReturnTransaction extends Transaction 
{
private final String RENT_RETURN_FORMAT_PATTERN_STRING = "(\")(.*)(\")";//this is supposed to represent "anything in the outermost "double quotes" found" and then a ',' and 0 or more whitespace characters before an integer
	
	public ReturnTransaction(String transactionLogLine) throws DataFileFormatException
	{
		super();
		try
		{
			Pattern pattern = Pattern.compile(RENT_RETURN_FORMAT_PATTERN_STRING); 
			Matcher matcher = pattern.matcher(transactionLogLine);
			matcher.find();
			if (!matcher.group().equals(transactionLogLine))
			{
				//there is extra stuff on the line 
				throw new DataFileFormatException();
			}
			String title = matcher.group(2); //this should get everything from the outermost double quotes
			//assign the values from the string
			title(title);			
		}
		catch(IllegalStateException e)
		{
			//this line was bad
			throw new DataFileFormatException();
		}
	}
	@Override 
	public String toString() 
	{
		return transactionType() +  " \"" + title() + "\"";
	}

	@Override
	public TransactionType transactionType() 
	{
		return TransactionType.RETURN;
	}
}
