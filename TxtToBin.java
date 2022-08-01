// P.S: Please make sure to change the directories of "<dir>" correspondingly
import java.io.*;
import java.util.Scanner;

public class TxtToBin {
	// Only the needed methods have been added for the dynamic array
	private int length;
	private Country[] tab;
	private int eltsNbr = 0;
	
	// Class constant – default length
	public static final int DEFAULT_LENGTH = 1; // = 1 because I want the dynamic array to grow once every time we add an element not more.
	// Class constant
	public static final int NOT_FOUND = -1;
	
	// Constructor with argument length
	public TxtToBin(int length) {
		super();
		this.tab = new Country[length];
		this.eltsNbr = 0;
		this.length = length;
	}
	
	// Constructor with no arguments
	public TxtToBin() {
		this(DEFAULT_LENGTH);
	}
	
	// Copy Constructor
	public TxtToBin(TxtToBin a) {
		this(a.length);
		for (int i = 0; i < a.eltsNbr; i++)
			this.tab[i] = a.tab[i];
		this.eltsNbr = a.eltsNbr;
	}
	
	// Returns « true » if the ADT is full
	public boolean isFull() {
		return this.eltsNbr == this.length;
	}
		
	// Extends the size of the dynamic array
	public void extend(int additionalLength) {
		Country [] tab1 = new Country[this.length + additionalLength];
				
		for (int i = 0; i < this.eltsNbr; i++)
			tab1[i] = this.tab[i];
			
		this.tab = tab1;
		this.length = this.length + additionalLength;
	}
		
	// Returns true if « value » is successfully inserted at position « pos », and « false » otherwise
	public boolean insertAtPosition(Country value, int pos) {
		if (pos > this.eltsNbr || pos < 0)
			return false;
			
		if (this.isFull())
			this.extend(DEFAULT_LENGTH);
			
		for (int i = this.eltsNbr; i > pos; i--)
			this.tab[i] = this.tab[i - 1];
			
		this.tab[pos] = value;
		this.eltsNbr++;
			
		return true;
	}
			
	// Returns « true » if « value » is successfully inserted at the first « empty » element, and « false » otherwise
	public boolean insertAtRear(Country value) { // We only need insertAtRear so that we can keep the elements sorted alphabetically
		return this.insertAtPosition(value, this.eltsNbr);
	}
		
	public String toString() {
		String s = "Name \t\t\t Code \t\t\t Region \t\t\t Population\n"; // It does not look the cleanest 
		// due to some countries having bigger or smaller names than average
		// but since we are not supposed to use it in the first place (other than for testing usages), it will do.
			
		for(int i = 0; i < eltsNbr; i++) {	// We can put the following 8 lines into 1 line only
											// but for the clarity of the code, it was done that way
			s += this.tab[i].name;
			s += " \t\t ";
			s += this.tab[i].code;
			s += " \t\t ";
			s += this.tab[i].region;
			s += " \t\t ";
			s += this.tab[i].population;
			s += " \n";
		}
			
		return s;
	}
	// Class method to present all the regions available with their numbers without worrying about null elements since it will be limited by "counter" and not the length of the array.
	public static void regionList(String[] region, int counter) { 	
		for(int i = 0; i < counter; i++)
			System.out.println((i+1) + " " + region[i]);
	}
	
	// Creates a new dynamic array with only the selected region 
	public TxtToBin regionChosen(String region, int countryNbr) { // "countryNbr" is the nbr of countries overall (first line in text file)
		TxtToBin newList = new TxtToBin();
		for(int i = 0; i < countryNbr; i++)
			if(this.tab[i].region.equals(region))
				newList.insertAtRear(this.tab[i]);
		return newList;
	}
	
		
	public static void main(String[] args) throws IOException{
		String txtFileDirectory = "<dir>";
		Scanner inStream = new Scanner(new File(txtFileDirectory));
		
		int countryNbr = inStream.nextInt();
		TxtToBin arr = new TxtToBin(countryNbr);// Creating a dynamic array with "countryNbr" as length from the first line in the text file
		
		inStream.nextLine();// Everytime we use inStream.nextInt(), we have to do inStream.nextLine() afterwards to skip that line
		
		for(int i = 0; i < countryNbr; i++) {
			Country tmp = new Country(inStream.nextLine(), inStream.nextLine(), inStream.nextLine(), inStream.nextInt());// using the Country constructor directly to simplify
			arr.insertAtRear(tmp);// Inserting at rear so that the first country in the text file stays the first in the dynamic array
			inStream.nextLine();// Same concept as 5 lines above
		}
		inStream.close(); // Closing the stream.
		
		// System.out.println(arr.toString()); // (For testing purposes (will print all the countries' attributes in a list))
		
		// Dialogue with user:
		// We could write down directly the various regions listed in the text file but in case we need to add more countries with different regions I decided to automate the process.
		String[] region = new String [10]; // the length of the array can be different than 10 as long as it can fit all the regions.
		int counter = 0; // Used to limit, later on, the "region" array without checking the elements at the end of it which would be null
		boolean check = true; // checker to see if we have a new region or not (starts with true since the first country will definitely be new and need to be added to "region" array)
		
		for(int i = 0; i < countryNbr; i++) { // going through all the countries
			for(int j = 0; j < region.length; j++) { // going through all the regions in "region" array
				if(arr.tab[i].region.equals(region[j]))
					check = false; // region not new then check will be false
			}
			if(check == true) { // if check is true then we add it to the array and print it out correspondingly 
				region[counter] = arr.tab[i].region;
				counter++;
			}
			check = true;
		}
		
		// What will be printed out:
		System.out.println("Choose the number for which region you need: "); // Asking the user to write a number corresponding to its region
		regionList(region,counter); // Using the class method "regionList" to list the regions available correspondingly
		
		Scanner in = new Scanner(System.in); // Creating a different input stream since we have a different directory
		
		check = false; // Using the same boolean checker to check if the input is correct (we can also create a new boolean variable)
		int input; 
		
		do {// could also do a normal while loop with the same condition but then we would have to initialize "input" for uses after the loop.
			while(!in.hasNextInt()) {// checking if the input is an integer, if not, it will skip the line and ask for another input.
				in.next();
				System.out.println("Incorrect input, please choose again:");
				regionList(region,counter);
			}
			
			// Checking if the integer inputted is one of the numbers needed.
			input = in.nextInt();
			for(int i = 0; i < counter; i++) 
				if(input == (i+1))
					check = true; // true for valid inputs
			
			if(check == false) {// if check is true it means we have a valid input, else, check would be false we would go through this if condition 
								// get the following dialogue and start a new iteration in the loop
				System.out.println("Incorrect input, please choose again:");
				regionList(region,counter);
			}
		}while(check == false);
		
		in.close(); // Closing the stream.
		
		// System.out.println(input); // (for testing purposes (will show the input if it passes the verification process))
		// System.out.println(arr.regionChosen(region[input-1], countryNbr).toString()); // (for testing purposes (will show the list of countries of the input region))
		
		TxtToBin newArr = new TxtToBin(arr.regionChosen(region[input-1], countryNbr)); // Creating a Dynamic array with new countries list (same region as requested from user)
		
		String binFileDirectory = "<dir>";
		DataOutputStream outStream = new DataOutputStream(new FileOutputStream(binFileDirectory));
		
		outStream.writeInt(newArr.eltsNbr);		// Since all countries here have the same region, for the first line I will designate the number of countries 
		outStream.writeUTF(region[input - 1]); 	// and the second line the region name.
		
		for(int i = 0; i < newArr.eltsNbr; i++) {
			outStream.writeUTF(newArr.tab[i].name);
			outStream.writeUTF(newArr.tab[i].code);
			outStream.writeInt(newArr.tab[i].population);
		}
		
		outStream.close(); // Closing the stream
		
		System.out.println("Data saved in a binary file on your desktop!"); // Confirmation
	}
}