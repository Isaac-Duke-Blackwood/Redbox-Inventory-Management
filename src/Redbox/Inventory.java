//Name: Isaac Blackwood
//Net ID: idb170030
package Redbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Inventory 
{
	private final String TITLE = "Title ", AVAILABLE = "Available ", RENTED = "Rented ";
	private BinarySearchTree<Node> binarySearchTree;
	private String errorLogFileName, reportFileName;
	
	protected Inventory(){}
	public Inventory(String inventoryFileName, String errorLogFileName, String reportFileName) throws FileNotFoundException
	{
		binarySearchTree = new BinarySearchTree<Node>();
		errorLogFileName(errorLogFileName);
		reportFileName(reportFileName);
		Scanner inventoryScanner = new Scanner(new File(inventoryFileName));
		
		//populate the bst with an inventory
		while(inventoryScanner.hasNext())
		{
			try
			{
				InventoryElement inventoryElement = new InventoryElement(inventoryScanner.nextLine());
				binarySearchTree.insert(inventoryElement.node());
			}
			catch (DataFileFormatException e)
			{
				//This line of the inventory file was not in the proper format
				continue;
			}
		}
		inventoryScanner.close();
	}
	public String reportFileName(String reportFileName) 
	{
		return this.reportFileName = reportFileName;
	}
	public String reportFileName() 
	{
		return reportFileName;
	}
	public String errorLogFileName(String errorLogFileName) 
	{
		return this.errorLogFileName = errorLogFileName;
	}
	public String errorLogFileName() 
	{
		return errorLogFileName;
	}
	
	//process the transactions
	public String processTransactions(Transactions transactions) throws IOException //returns a formated report of the new inventory
	{
		//iterate through all of the transactions and process each individually, keep track of orders that couldn't be processed correctly (including the error list from Transactions) and output them to the error.log at the end if there were 1 or more. Return a string created from the ending inventory
		for (Transaction transaction : transactions) 
		{
			if(!process(transaction))
			{
				//if the order wasn't processed correctly, add it to the transactions error list
				transactions.addToErrorList(transaction.toString());
			}
		}
		if (null != transactions.errorList())
		{
			StringBuilder errorLines = new StringBuilder();
			for (String errorLine : transactions.errorList())
			{
				//add each bad line to a big string
				errorLines.append(errorLine + "\n");
			}
			//write all the bad lines to the file
			FileWriter errorLog = new FileWriter(errorLogFileName());
			errorLog.write(errorLines.toString()); 
			errorLog.close();
		}
		//create a string from the inventory and write it to the report file
		String report = toString();
		FileWriter reportFile = new FileWriter(reportFileName());
		reportFile.write(report);
		reportFile.close();
		return report;
	}
	
	public boolean process(Transaction transaction) //attempts to process the transaction and returns true if successful
	{
		//Check which type of transaction it is
		switch (transaction.transactionType())
		{
		case ADD:
			AddTransaction addTransaction = (AddTransaction) transaction;
			return process(addTransaction);
			
		case REMOVE:
			RemoveTransaction removeTransaction = (RemoveTransaction) transaction;
			return process(removeTransaction);
			
		case RENT:
			RentTransaction rentTransaction = (RentTransaction) transaction;
			return process(rentTransaction);
			
		case RETURN:
			ReturnTransaction returnTransaction = (ReturnTransaction) transaction;
			return process(returnTransaction);
			
		default:
			return false;
		}
	}
	private boolean process(AddTransaction addTransaction)
	{
		//get the node for the movie the transaction applies to
		Node nodeToFind = new Node(addTransaction.title());
		if (binarySearchTree.contains(nodeToFind))
		{
			//the node was in the tree
			Node node = binarySearchTree.search(new Node(addTransaction.title()));
			try 
			{
				//try to add disks to available
				node.changeAvailable(addTransaction.numberToAdd());
				return true;
			}
			catch (IllegalArgumentException e) 
			{
				//the transaction could not be completed because there are not enough disks.
				return false;
			}
		}
		else 
		{
			try 
			{
			//the node was not found, so add a new title
			binarySearchTree.insert(new Node(addTransaction.title(), addTransaction.numberToAdd()));
			return true;
			}
			catch (IllegalArgumentException e) 
			{
				return false;
			}
		}
	}
	private boolean process(RemoveTransaction removeTransaction)
	{
		//get the node for the movie the transaction applies to
		Node nodeToFind = new Node(removeTransaction.title());
		if (binarySearchTree.contains(nodeToFind))
		{
			//the node was in the tree
			Node node = binarySearchTree.search(new Node(removeTransaction.title()));
			try 
			{
				//try to remove disks from available
				node.changeAvailable(-removeTransaction.numberToRemove());
				

				//if there are no copies available, and no copies currently being rented, then remove the title from the tree
				if (0 == node.available() && 0 == node.rented())
				{
					binarySearchTree.delete(node);
				}
				return true;
			}
			catch (IllegalArgumentException e) 
			{
				//the transaction could not be completed because there are not enough disks.
				return false;
			}
		}
		else 
		{
			//the node was not found
			return false;
		}
	}
	private boolean process(RentTransaction rentTransaction)
	{
		//get the node for the movie the transaction applies to
		Node nodeToFind  = new Node(rentTransaction.title());
		if (binarySearchTree.contains(nodeToFind))
		{
			//the node was in the tree
			Node node = binarySearchTree.search(new Node(rentTransaction.title()));
			try 
			{
				//try to remove a disk from available and add to rented
				node.changeAvailable(-1);
				node.changeRented(1);
				return true;
			}
			catch (IllegalArgumentException e) 
			{
				//the transaction could not be completed because there are not enough disks.
				return false;
			}
		}
		else 
		{
			//the node was not found
			return false;
		}
	}
	private boolean process(ReturnTransaction returnTransaction)
	{
		//get the node for the movie the transaction applies to
		Node nodeToFind  = new Node(returnTransaction.title());
		if (binarySearchTree.contains(nodeToFind))
		{
			//the node was in the tree
			Node node = binarySearchTree.search(new Node(returnTransaction.title()));
			try 
			{
				//try to remove a disk from rented and add to available
				node.changeRented(-1);
				node.changeAvailable(1);
				return true;
			}
			catch (IllegalArgumentException e) 
			{
				//the transaction could not be completed because there are not enough disks.
				return false;
			}
		}
		else 
		{
			//the node was not found
			return false;
		}
	}
	
	@Override
	public String toString() 
	{
		//build a string with all of the titles, then available, then rented
		StringBuilder report = new StringBuilder();
		int largestTitleLength = TITLE.length(); //number of characters in "Title "
		int largestAvailableLength = AVAILABLE.length(); //number of characters in "Available "
		int largestRentedLength = RENTED.length(); //number of characters in "Rented "
		
		//step through the binary tree and find the largest number of spaces required to fit each value
		Node current = binarySearchTree.leftmost();
		while (null != current)
		{
			int currentLength = current.title().length() + 1;
			if (currentLength > largestTitleLength)
			{
				//new largest length
				largestTitleLength = currentLength;
			}
			currentLength = (current.available() / 10) + 1; //get number of digits then add a space
			if (currentLength > largestAvailableLength)
			{
				largestAvailableLength = currentLength;
			}
			currentLength = (current.rented() / 10) + 1; //get number of digits then add a space
			if (currentLength > largestRentedLength)
			{
				largestRentedLength = currentLength;
			}
			current = binarySearchTree.nextFrom(current);
		}
		//create the header and append it
		StringBuilder temp = new StringBuilder(TITLE);
		temp.setLength(largestTitleLength);
		report.append(temp);
		temp = new StringBuilder(AVAILABLE);
		temp.setLength(largestAvailableLength);
		report.append(temp);
		temp = new StringBuilder(RENTED);
		temp.setLength(largestRentedLength);
		report.append(temp);
		report.append("\n");
		
		//step through the tree and append the each of data members with the correct length so the output is in columns
		current = binarySearchTree.leftmost();
		while (null != current)
		{
			temp = new StringBuilder(current.title());
			temp.setLength(largestTitleLength);
			report.append(temp);
			temp = new StringBuilder(Integer.toString(current.available()));
			temp.setLength(largestAvailableLength);
			report.append(temp);
			temp = new StringBuilder(Integer.toString(current.rented()));
			temp.setLength(largestRentedLength);
			report.append(temp);
			report.append("\n");
			current = binarySearchTree.nextFrom(current);
		}
		
		return report.toString();
	}
}
