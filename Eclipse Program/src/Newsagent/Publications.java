package Newsagent;

public class Publications
{
	private int publicationNo;
	private String publicationName;
	private double publicationStock;
	private double publicationPrice;
	
	public Publications(int publicationNo, String publicationName, double publicationStock, double publicationPrice) {
		super();
		this.publicationNo = publicationNo;
		this.publicationName = publicationName;
		this.publicationStock = publicationStock;
		this.publicationPrice = publicationPrice;
	}

	public int getPublicationNo() {
		return publicationNo;
	}
	public void setPublicationNo(int publicationNo) {
		this.publicationNo = publicationNo;
	}

	public String getPublicationName() {
		return publicationName;
	}
	public void setPublicationName(String publicationName) {
		this.publicationName = publicationName;
	}

	public double getPublicationStock() {
		return publicationStock;
	}
	public void setPublicationStock(double publicationStock) {
		this.publicationStock = publicationStock;
	}

	public double getPublicationPrice() {
		return publicationPrice;
	}
	public void setPublicationPrice(double publicationPrice) {
		this.publicationPrice = publicationPrice;
	}

	public static void main(String[] args) {
		
	}
	
}
