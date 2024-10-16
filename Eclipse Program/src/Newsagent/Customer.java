package Newsagent;

public class Customer
{
	private int id;
	private String name;
	private String address;
	private int phoneNumber;
	private String subscriptionStatus;
	private boolean pauseActive;
	
	public Customer(String custName, String custAddress, int custPhone, String subscriptionStatus, boolean pauseActive)
	{
		id = 0;
		
		this.name = custName;
		this.address = custAddress;
		this.phoneNumber = custPhone;
		this.subscriptionStatus = subscriptionStatus;
		this.pauseActive = pauseActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSubscriptionStatus() {
		return subscriptionStatus;
	}

	public void setSubscriptionStatus(String subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}

	public boolean isPauseActive() {
		return pauseActive;
	}

	public void setPauseActive(boolean pauseActive) {
		this.pauseActive = pauseActive;
	}
	
	public static void validateName()
	{
		
	}
	
	public static void validateAddress()
	{
		
	}
	
	public static void validatePhoneNumber()
	{
		
	}
}