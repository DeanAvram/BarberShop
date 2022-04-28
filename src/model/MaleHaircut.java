package model;

public class MaleHaircut implements IService{

	private int length;
	private int price;
	
	
	public MaleHaircut() {
		
		this.length = 30;
		this.price = 50;
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
	
	public String getName () {
		return "Male Hair Cut";
	}

	@Override
	public String printService() {
		return "Male Hair Cut\t"  + "Price: " + this.price + "\tLength: " + this.length + "\n";
	}
	
	
	
	
	
}