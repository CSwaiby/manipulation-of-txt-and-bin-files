// The main purpose of this class is to create Country objects
public class Country { 

	String name, code, region;
	int population; // Cannot have the attributes as private since they will be used in the other public classes.

	// We only need this constructor (Empty and copy constructors are not needed)
	public Country(String name, String code, String region, int population) {
		this.name = name;
		this.code = code;
		this.region = region;
		this.population = population;
	}
}