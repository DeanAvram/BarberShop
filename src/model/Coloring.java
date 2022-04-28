package model;

public class Coloring implements IService {
	
	private int length;
	private int price;
	
	public Coloring () {
		this.length = 90;
		this.price = 450;
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
		return "Coloring";
	}


	@Override
	public String printService() {
		return "Coloring\t"  + "Price: " + this.price + "\tLength: " + this.length + "\n";
	}

}
