// P.S: Please make sure to change the directories of "<dir>" correspondingly
import java.io.*;

//Inner Class Element to create the elements of the linked list
class Element {
	Country data; // int type used as example
	Element next; // Reference of the successor
	
	Element(Country value) {
		data = value;
		next = null;
	}
}

public class BinToTxt {

	// Reference of the head of the list
	private Element head = null;
	// Reference of the rear of the list
	private Element rear = null;
	// Number of elements of the list
	private int length = 0;
	// NOT_FOUND as a class constant to be used in the following methods
	public static final int NOT_FOUND = -1;
		
		
	// Constructor with no arguments (Copy constructor not needed)
	public BinToTxt() {
		this.head = null;
		this.rear = null;
		this.length = 0;
	}
	
	// Returns true if the list is empty, false otherwise
	public boolean isEmpty() {
		return this.length == 0;
	}
	
	// We only need insertAtRear so that we can keep the elements sorted alphabetically
	public void insertAtRear(Country value) {
		Element cur, tmp;
		// Create a new node and Initialize it
		tmp = new Element(value);
		
		// Special case of an empty list
		if (this.isEmpty()) {
			this.head = this.rear = tmp;
		}
		
		else {
			// Go to the element at the rear
			cur = rear;
			
			// Insert the new node by adjusting the links
			cur.next = tmp;
			this.rear = tmp;
		}
		this.length++;
	}
	
	public static void main(String[] args) throws IOException {
		
		String binFileDirectory = "<dir>";
		DataInputStream inStream = new DataInputStream(new FileInputStream(binFileDirectory));
		
		int countryNbrs = inStream.readInt();	 // Getting the first line
		String regionName = inStream.readUTF();	 // Getting the second line
		
		BinToTxt arr = new BinToTxt(); // Creating the linked list
		
		for(int i = 0; i < countryNbrs; i++) {
			Country current = new Country(inStream.readUTF(),inStream.readUTF(), regionName, inStream.readInt());
			arr.insertAtRear(current);
		}
		
		inStream.close(); // Closing the stream
		
		String txtFileDirectory = "<dir>";
		FileWriter outStream = new FileWriter(txtFileDirectory);
		
		outStream.write(countryNbrs + "\n");
		outStream.write(regionName + "\n"); // The structure would become different than the one in data_in.txt but at least there won't be any redundancy
		
		Element cur = arr.head; // Creating an element cur to move from one element to another
		for(int i = 0; i < arr.length; i++) {
			outStream.write(cur.data.name + "\n"); // Filling the corresponding data on each line
			outStream.write(cur.data.code + "\n");
			outStream.write(cur.data.population + "\n");
			cur = cur.next; // moving to the next element
		}
		
		outStream.close(); // Closing the stream
		
		System.out.println("Data saved in a text file on your desktop!"); // Confirmation
	}
}