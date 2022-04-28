package model;

public class FemaleHaircut implements IService{

	private int length;
	private int price;
	
	
	public FemaleHaircut() {
		this.length = 60;
		this.price = 150;
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
		return "Female Hair Cut";
	}


	@Override
	public String printService() {
		return "Female Hair Cut\t"  + "Price: " + this.price + "\tLength: " + this.length + "\n";
	}

}
