package model;

public class KidHaircut implements IService{
	
	private int length;
	private int price;
	
	public KidHaircut () {
		this.length = 30;
		this.price = 35;
	}
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	public int getPrice () {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getName() {
		return "Kid Hair Cut";
	}

	@Override
	public String printService() {
		return "Kid Hair Cut\t"  + "Price: " + this.price + "\tLength: " + this.length + "\n";
	}
	
	

}
