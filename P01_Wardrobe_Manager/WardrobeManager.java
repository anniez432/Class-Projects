////////////////FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
//Title:    Wardrobe Manager
//Course:   CS 300 Spring 2024
//
//Author:   Annie Zhao
//Email:    azhao37@wisc.edu
//Lecturer: Hobbes LeGault
//
////////////////////PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
//Partner Name:    N/A
//Partner Email:   N/A
//Partner Lecturer's Name: N/A
//
//VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//___ Write-up states that pair programming is allowed for this assignment.
//___ We have both read and understand the course Pair Programming Policy.
//___ We have registered our team prior to the team registration deadline.
//
////////////////////////ASSISTANCE/HELP CITATIONS ////////////////////////////
//
//Persons:         None
//Online Sources:  The project specifications document attached to the 
//					assignment in Canvas:
//					https://canvas.wisc.edu/courses/398763/assignments/2161027 
//				   
//
///////////////////////////////////////////////////////////////////////////////
/**
 * This class allows the user to receive information regarding the contents
 * of a wardrobe and edit them.
 */
public class WardrobeManager {

	/**
	* Determines if the wardrobe contains the described item.
	*
	* @param description a general description of the clothing item
	* @param brand the brand of the clothing item
	* @param wardrobe a two-dimensional array of Strings, which stores wardrobe entries.
	* wardrobe[i][0] contains a description of item i,
	* wardrobe[i][1] contains its brand name, and
	* wardrobe[i][2] contains its last-worn date formatted as
	* "YYYY-MM-DD", or "never"
	* @param wardrobeSize number of items currently stored in the wardrobe
	* @return containsClothing true if the item described was found in the wardrobe; 
	* 	false if not. 
	*/
	public static boolean containsClothing(String description, String brand, 
			String[][] wardrobe, int wardrobeSize) {
		boolean containsClothing = false;
	
		for(int i = 0; i < wardrobeSize; ++i) {
			
			//if the element matches the description and the brand, 
			// containsClothing is evaluated as true.
			if(wardrobe[i][0].equalsIgnoreCase(description) && 
					wardrobe[i][1].equalsIgnoreCase(brand)) {
				containsClothing = true;
			}
			
		}
		
		return containsClothing;
	}
	
	/**
	* Adds the specified item to the wardrobe.
	*
	* @param description a general description of the clothing item
	* @param brand the brand of the clothing item
	* @param wardrobe a two-dimensional array of Strings, which stores wardrobe entries.
	* wardrobe[i][0] contains a description of item i,
	* wardrobe[i][1] contains its brand name, and
	* wardrobe[i][2] contains its last-worn date formatted as
	* "YYYY-MM-DD", or "never"
	* @param wardrobeSize number of items currently stored in the wardrobe
	* @return wardrobeSize the integer value of the number of items stored in the wardrobe
	*/
	public static int addClothing(String description, String brand, 
			String[][] wardrobe, int wardrobeSize) {
		
		//if the wardrobe already contains the item, wardrobeSize doesn't 
		//change and is returned.
		if(containsClothing(description, brand, wardrobe, wardrobeSize)) {
			return wardrobeSize;
		} else {
			
			//if the wardrobe is not full, the item is added and wardrobeSize is incremented
			if(wardrobeSize < wardrobe.length) {
				wardrobe[wardrobeSize] = new String[]{description, brand, "never"};
				wardrobeSize++;
			} else {
				return wardrobeSize;
			}
		}
		
		return wardrobeSize;
	}
	
	/**
	* Determines the index of a given item.
	*
	* @param description a general description of the clothing item
	* @param brand the brand of the clothing item
	* @param wardrobe a two-dimensional array of Strings, which stores wardrobe entries.
	* wardrobe[i][0] contains a description of item i,
	* wardrobe[i][1] contains its brand name, and
	* wardrobe[i][2] contains its last-worn date formatted as
	* "YYYY-MM-DD", or "never"
	* @param wardrobeSize number of items currently stored in the wardrobe
	* @return indexOfClothing the index of the specified item
	*/
	public static int indexOfClothing(String description, String brand, String[][] wardrobe, int wardrobeSize) {
		int indexOfClothing = -1;
		
		for(int i = 0; i < wardrobeSize; ++i){
			if(wardrobe[i][0].equalsIgnoreCase(description) && wardrobe[i][1].equals(brand)) {
				indexOfClothing = i;
			}
		}
		
		return indexOfClothing;
	}
	
	/**
	* Updates the date worn of the specified item in the wardrobe.
	*
	* @param description a general description of the clothing item
	* @param brand the brand of the clothing item
	* @param wardrobe a two-dimensional array of Strings, which stores wardrobe entries.
	* wardrobe[i][0] contains a description of item i,
	* wardrobe[i][1] contains its brand name, and
	* wardrobe[i][2] contains its last-worn date formatted as
	* "YYYY-MM-DD", or "never"
	* @param wardrobeSize number of items currently stored in the wardrobe
	* @param date the last-worn date of the item, formatted as "YYYY-MM-DD", or "never"
	* @return wearClothing true if the date is successfully updated; false if not
	*/
	public static boolean wearClothing(String description, String brand, String date, String[][] wardrobe, int wardrobeSize) {
		boolean wearClothing = false;
		
		for(int i = 0; i < wardrobeSize; ++i) {
			if(wardrobe[i][0].equalsIgnoreCase(description) &&  wardrobe[i][1].equalsIgnoreCase(brand)) {
				wardrobe[i][2] = date;
				wearClothing = true;
			}
		}
		
		return wearClothing;
	}
	
	/**
	* Determines the number of times a brand is present in the wardrobe.
	*
	* @param brand the brand of the clothing item
	* @param wardrobe a two-dimensional array of Strings, which stores wardrobe entries.
	* wardrobe[i][0] contains a description of item i,
	* wardrobe[i][1] contains its brand name, and
	* wardrobe[i][2] contains its last-worn date formatted as
	* "YYYY-MM-DD", or "never"
	* @param wardrobeSize number of items currently stored in the wardrobe
	* @return brandCount the number of times the brand is found
	*/
	public static int getBrandCount(String brand, String[][] wardrobe, int wardrobeSize) {
		int brandCount = 0;
		
		for(int i = 0; i < wardrobeSize; ++i) {
			if(wardrobe[i][1].equalsIgnoreCase(brand)) brandCount++;
		}
		
		return brandCount;
	}
	
	/**
	* Determines the number of unworn clothes in the wardrobe
	*
	* @param wardrobe a two-dimensional array of Strings, which stores wardrobe entries.
	* wardrobe[i][0] contains a description of item i,
	* wardrobe[i][1] contains its brand name, and
	* wardrobe[i][2] contains its last-worn date formatted as
	* "YYYY-MM-DD", or "never"
	* @param wardrobeSize number of items currently stored in the wardrobe
	* @return numUnwornClothes the number of clothes whose last-worn date is "never"
	*/
	public static int getNumUnwornClothes(String[][] wardrobe, int wardrobeSize) {
		int numUnwornClothes = 0;
		
		for(int i = 0; i < wardrobeSize; ++i) {
			if(wardrobe[i][2].equalsIgnoreCase("never")) numUnwornClothes++;
		}
		
		return numUnwornClothes;
	}
	
	/**
	* Determines the index of the item in the wardrobe with the most recent 
	* last-worn date.
	*
	* @param wardrobe a two-dimensional array of Strings, which stores wardrobe entries.
	* wardrobe[i][0] contains a description of item i,
	* wardrobe[i][1] contains its brand name, and
	* wardrobe[i][2] contains its last-worn date formatted as
	* "YYYY-MM-DD", or "never"
	* @param wardrobeSize number of items currently stored in the wardrobe
	* @return mostRecentlyWorn the index of the item with the most recent 
	* 	last-worn date
	*/
	public static int getMostRecentlyWorn(String[][] wardrobe, int wardrobeSize) {
		int mostRecentlyWorn = 0;
		int mostRecentYear = 0;
		int mostRecentMonth = 0;
		int mostRecentDay = 0;
	
		
		for(int i = 0; i < wardrobeSize; ++i) {
			
			if(wardrobe[i][2].equalsIgnoreCase("never")) {
				continue;
			} 
			
			int year = Integer.parseInt(wardrobe[i][2].substring(0, 4));
			int month = Integer.parseInt(wardrobe[i][2].substring(5, 7));
			int day = Integer.parseInt(wardrobe[i][2].substring(8, 10));;
			
			
			if(year > mostRecentYear) {
				
				mostRecentYear = year;
				mostRecentMonth = month;
				mostRecentDay = day;
				mostRecentlyWorn = i;
				
			} else if(year == mostRecentYear) {
					
					if(month > mostRecentMonth) {
						
						mostRecentMonth = month;
						mostRecentDay = day;
						mostRecentlyWorn = i;
	
					} 
					else if (month == mostRecentMonth) {
						
						if(day > mostRecentDay) {
							mostRecentDay = day;
							mostRecentlyWorn = i;
						}
					}
				}
		}
		return mostRecentlyWorn;
	}
	
	/**
	* Removes the item from the wardrobe that is located at a given index.
	*
	* @param index the specified index of the item to be removed
	* @param wardrobe a two-dimensional array of Strings, which stores wardrobe entries.
	* wardrobe[i][0] contains a description of item i,
	* wardrobe[i][1] contains its brand name, and
	* wardrobe[i][2] contains its last-worn date formatted as
	* "YYYY-MM-DD", or "never"
	* @param wardrobeSize number of items currently stored in the wardrobe
	* @return wardrobeSize the number of items stored in the wardrobe
	*/
	public static int removeClothingAtIndex(int index, String[][] wardrobe, int wardrobeSize) {
		
		if(index < wardrobeSize) {
			for(int i = index; i< wardrobeSize; ++i) {
				//shifts each of the elements
				wardrobe[i] = wardrobe[i + 1];
			}
			wardrobeSize--;
		} 
		return wardrobeSize;
	}
	
	/**
	* Remove all of the items that have a last-worn date of "never"
	* 
	* @param wardrobe a two-dimensional array of Strings, which stores wardrobe entries.
	* wardrobe[i][0] contains a description of item i,
	* wardrobe[i][1] contains its brand name, and
	* wardrobe[i][2] contains its last-worn date formatted as
	* "YYYY-MM-DD", or "never"
	* @param wardrobeSize number of items currently stored in the wardrobe
	* @return wardrobeSize the number of items stored in the wardrobe
	*/
	public static int removeAllUnworn(String[][] wardrobe, int wardrobeSize) {

		for(int i = 0; i < wardrobeSize; ++i) {
			if(wardrobe[i][2].equalsIgnoreCase("never")) {
				
				for(int j = i; j < wardrobeSize; ++j) {
					wardrobe[j] = wardrobe[j + 1];
				}
				wardrobeSize--;
				i--;
			} 
		}
		
		return wardrobeSize;
	}
}
