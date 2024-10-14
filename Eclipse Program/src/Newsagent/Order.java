package Newsagent;



	public class Order {
	    private int customerId; //needs customer id to create order
	    private long customerPhoneNumber; // need customer phone number to create order
	    private double publicationPrice; // price of each publication on the order
	    private boolean canSendOrder;

	    // bill will be a method

	 public Order(int  customerId, long customerPhoneNumber, double publicationPrice, boolean canSendOrder )
	 {
	     this.customerId = customerId;
	     this.customerPhoneNumber = customerPhoneNumber;
	     this.publicationPrice = customerId;
	     this.canSendOrder = canSendOrder;

	 }

	    public int getCustomerId() {
	        return customerId;
	    }

	    public void setCustomerId(int customerId) {
	        this.customerId = customerId;
	    }

	    public long getCustomerPhoneNumber() {
	        return customerPhoneNumber;
	    }

	    public void setCustomerPhoneNumber(long customerPhoneNumber) {
	        this.customerPhoneNumber = customerPhoneNumber;
	    }

	    public double getPublicationPrice() {
	        return publicationPrice;
	    }

	    public void setPublicationPrice(double publicationPrice) {
	        this.publicationPrice = publicationPrice;
	    }

	    public boolean isCanSendOrder() {
	        return canSendOrder;
	    }

	    public void setCanSendOrder(boolean canSendOrder) {
	        this.canSendOrder = canSendOrder;
	    }
	}


