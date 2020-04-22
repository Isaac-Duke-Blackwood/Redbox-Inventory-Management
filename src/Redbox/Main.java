//Name: Isaac Blackwood
//Net ID: idb170030
package Redbox;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main 
{

	public static void main(String[] args) 
	{
		final String INVENTORY_FILE_NAME = "inventory.dat", TRANSACTION_FILE_NAME = "transaction.log", ERROR_FILE_NAME = "error.log", REPORT_FILE_NAME = "redbox_kiosk.txt";
	
		//create the inventory
		try
		{
			Inventory inventory = new Inventory(INVENTORY_FILE_NAME, ERROR_FILE_NAME, REPORT_FILE_NAME);
			Transactions transactions = new Transactions(TRANSACTION_FILE_NAME);
			inventory.processTransactions(transactions);
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("One or more of the specified files could not be found.");
		} 
		catch (IOException e) 
		{
			System.out.println("There was a problem writing to the file.");
		}
		
	}

}
