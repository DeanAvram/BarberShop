package model;

public class ServiceFactory {
	
	public IService getService (String name) {
		switch (name) {
		case ("Male Hair Cut"):
			return new MaleHaircut();
		case ("Female Hair Cut"):
			return new FemaleHaircut();
		case ("Japanese Straightening"):
			return new JapaneseStraightening();
		case ("Coloring"):
			return new Coloring();
		case ("Kid Hair Cut"):
			return new KidHaircut();
		default:
			return null;
		
		}
	}

}
