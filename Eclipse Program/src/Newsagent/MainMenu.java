package Newsagent;

import java.util.Scanner;

public class MainMenu {

	
	public static void main(String[] args) throws DeliveryExceptionHandler, InvoiceExceptionHandler, PublicationExceptionHandler
	{
		Scanner in = new Scanner(System.in);
		while (true)
		{
			System.out.println("Choose an Option:");
	        System.out.println("1.	Customer");
	       	System.out.println("2.	Deliveries");
	       	System.out.println("3.	Invoice");
	       	System.out.println("4.	Order ");
	       	System.out.println("5.	Publications");
	       	System.out.println("6.	Exit");
	       	System.out.println(":	");
	       	
	       	int choice = in.nextInt();
	       	in.nextLine();

	        switch (choice) 
	       	{
	       		case 1:
	       			Customer.main(null);
	       			in.close();
	       			break;

	       		case 2:
	       			Deliveries.main(null);
	       			in.close();
	       			break;
	       			
	       		case 3:
	       			Invoice.main(null);
	       			in.close();
	       			break;
	       			
	       		case 4:
	       			Order.main(null);
	       			in.close();
	       			break;
	       			
	       		case 5:
	       			Publications.main(null);
	       			in.close();
	       			break;

	       		case 6:
	       			System.out.println("Exiting.....");
	       			in.close();
	       			return;
	        				
	       		default:
	       			System.out.println("Error: Invalid Choice");
	       			System.out.println("Press Enter to Retry.....");
                    in.nextLine();
	        }
		}
		}
		
	}

