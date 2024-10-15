package Newsagent;

public class Deliveries
{
	private int driverID;
	private int orderId;
	private float deliveryDate;
	private String publicationID;  //publicationsdelivered
	private String customerName; 
	private String deliveryAddress; //deliveryarea
	private String activeDays;  //deliveryDate
	private boolean deliveryStatus;
	private boolean deliveriesPaused;
	
	public Deliveries(int driverID, int orderId, double deliveryDate, String publicationID, String customerName, String deliveryAddress,
			String activeDays, boolean deliveryStatus, boolean deliveriesPaused) {
		this.driverID = driverID;
		this.orderId = orderId;
		this.deliveryDate = deliveryDate;
		this.publicationID = publicationID;
		this.customerName = customerName;
		this.deliveryAddress = deliveryAddress;
		this.activeDays = activeDays;
		this.deliveryStatus = deliveryStatus;
		this.deliveriesPaused = deliveriesPaused;
	}
	
	public int getDriverID() {
		return driverID;
	}
	public void setDriverID(int driverID) {
		this.driverID = driverID;
	}

	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(float deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	public String getPublicationID() {
		return publicationID;
	}
	public void setPublicationID(String publicationID) {
		this.publicationID = publicationID;
	}

	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getActiveDays() {
		return activeDays;
	}
	public void setActiveDays(String activeDays) {
		this.activeDays = activeDays;
	}

	public boolean isDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(boolean deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public boolean isDeliveriesPaused() {
		return deliveriesPaused;
	}
	public void setDeliveriesPaused(boolean deliveriesPaused) {
		this.deliveriesPaused = deliveriesPaused;
	}

	public static void main(String args[]) {		
	}
}
