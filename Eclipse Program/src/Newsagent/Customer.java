package Newsagent;

public class Customer
{
	public String name;
	public String address;
	public int phoneNumber;
	public String subscriptionStatus;
	public boolean pauseActive;
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public int getPhoneNumber()
	{
		return phoneNumber;
	}
	
	public void setPhoneNumber(int phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	
	public String getSubscriptionStatus()
	{
		return subscriptionStatus;
	}
	
	public void setSubscriptionStatus(String subscriptionStatus)
	{
		this.subscriptionStatus = subscriptionStatus;
	}
	
	public boolean isPauseActive()
	{
		return pauseActive;
	}
	
	public void setPauseActive(boolean pauseActive)
	{
		this.pauseActive = pauseActive;
	}
	
}
