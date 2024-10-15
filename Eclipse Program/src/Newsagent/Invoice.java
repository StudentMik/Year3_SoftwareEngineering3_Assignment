package Newsagent;

public class Invoice
{
	private int customerID;
	private int quantity;
	private double invoiceAmount; //total cost
	private double pricePerItem;  // or publication price
	private String publicationName; //or id
	
	public Invoice(int customerID, int quantity, double invoiceAmount, double pricePerItem, String publicationName) {
		this.customerID = customerID;
		this.quantity = quantity;
		this.invoiceAmount = invoiceAmount;
		this.pricePerItem = pricePerItem;
		this.publicationName = publicationName;
	}

	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public double getPricePerItem() {
		return pricePerItem;
	}
	public void setPricePerItem(double pricePerItem) {
		this.pricePerItem = pricePerItem;
	}

	public String getPublicationName() {
		return publicationName;
	}
	public void setPublicationName(String publicationName) {
		this.publicationName = publicationName;
	}
	
	public static void main(String args[]) {
		
	}
	
}


