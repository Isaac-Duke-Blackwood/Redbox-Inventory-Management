//Name: Isaac Blackwood
//Net ID: idb170030
package Redbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Transactions extends ArrayList<Transaction> 
{
	private static final long serialVersionUID = -6853916538925547171L;
	private ArrayList<String> errorList = null;
	public Transactions(String transactionLogFileName) throws FileNotFoundException
	{
		Scanner transactionScanner = new Scanner(new File(transactionLogFileName));
		
		//step through and create transactions with from the file
		while (transactionScanner.hasNext()) 
		{
			String currentLine = transactionScanner.nextLine();
			try 
			{
				this.add(Transaction.toTransaction(currentLine));
			}
			catch (DataFileFormatException e) 
			{
				//The current line caused an error
				addToErrorList(currentLine);
				continue;
			}
		}
		transactionScanner.close();
	}
	public boolean addToErrorList(String errorCausingLine)
	{
		if (null == errorList)
		{
			errorList = new ArrayList<String>();
		}
		errorList.add(errorCausingLine);
		return true;
	}
	public ArrayList<String> errorList()
	{
		return errorList;
	}
}
