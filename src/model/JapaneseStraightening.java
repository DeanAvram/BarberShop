package model;

public class JapaneseStraightening  implements IService{

	private int length;
	private int price;
	
	
	public JapaneseStraightening() {
		this.length = 120;
		this.price = 800;
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
		return "Japanese Straightening";
	}

	@Override
	public String printService() {
		return "Japanese Straightening\t"  + "Price: " + this.price + "\tLength: " + this.length + "\n";
	}

}
