package Newsagent;

public class Deliveries
{
	private int driverID;
	private int deliveryDArea; //1-24
	private float deliveryDDate;
	private int orderId;
	private String deliveryAddress;
	private String publicationId;  //publications delivered
	
	public Deliveries(int driverID, int deliveryDArea, float DeliveryDDate, int orderId, String deliveryAddress, String publicationId) {
		this.driverID = driverID;
		this.deliveryDArea = deliveryDArea;
		this.deliveryDDate = deliveryDDate;
		this.orderId = orderId;
		this.deliveryAddress = deliveryAddress;
		this.publicationID = publicationID;
	}
	
	public int getDriverID() {
		return driverID;
	}
	public void setDriverID(int driverID) {
		this.driverID = driverID;
	}

	public int getDeliveryDArea() {
		return deliveryDArea;
	}
	public void setDeliveryDArea(int deliveryDArea) {
		this.deliveryDArea = deliveryDArea;
	}

	public float getDeliveryDDate() {
		return deliveryDDate;
	}
	public void setDeliveryDDate(int deliveryDDate) {
		this.deliveryDDate = deliveryDDate;
	}

	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	public String getPublicationId() {
		return publicationId;
	}
	public void setPublicationId(String publicationId) {
		this.publicationId = publicationId;
	}

	public static void main(String args[]) {		
	}
}
