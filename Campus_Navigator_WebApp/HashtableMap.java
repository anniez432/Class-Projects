// == CS400 Fall 2024 File Header Information ==
// Name: Annie Zhao
// Email: azhao37@wisc.edu
// Group: P2.3703
// Lecturer: Professor Florian Heimerl
// Notes to Grader: N/A

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.List;

/**
 * This class creates a HashtableMap that stores key value pairs
 * in a map that can then be edited and accessed.
 */
public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {
    //one protected single-dimensional array
    protected LinkedList<Pair>[] table = null;

    //private field to keep track of size
    private int size = 0;

    /**
     * This is the given class that creates a key-value pair.
     */
    protected class Pair {
        public KeyType key;
        public ValueType value;

        public Pair(KeyType key, ValueType value) {
            this.key = key;
            this.value = value;
        }

    }

    /**
     * This constructor creates a HashtableMap with the default size
     * of 64 key-value pairs.
     */
    public HashtableMap() {
        this(64);
    }

    /**
     * This constructor creates a Hashtable Map with a given
     * capacity of key-value pairs.
     * @param capacity The maximum amount of key-value pairs to hold
     *                 in this Hashtable map.
     */
    @SuppressWarnings("unchecked")
    public HashtableMap(int capacity){
        //instantiate array with raw type, then cast to complete
        //type with generics
        table = (LinkedList<Pair>[]) new LinkedList[capacity];
    }


    /**
     * Adds a new key,value pair/mapping to this collection.
     *
     * @param key   the key of the key,value pair
     * @param value the value that key maps to
     * @throws IllegalArgumentException if key already maps to a value
     * @throws NullPointerException     if key is null
     */
    @Override
    public void put(KeyType key, ValueType value) throws IllegalArgumentException {
        //throw nullPointerException if key is null
        if(key == null){
            throw new NullPointerException("Key is null.");
        }

        //throw IllegalArgumentException if the table already
        //has the key
        if(this.containsKey(key)){
            throw new IllegalArgumentException("Key already maps to a value.");
        }

        //calculate the new index
        int newIndex = Math.abs(key.hashCode()) % getCapacity();

        //if there's no LinkedList at that index of the table yet
        //create one
        if(this.table[newIndex] == null){
            this.table[newIndex] = new LinkedList<>();
        }

        //add the new pair to the table at the new index
        this.table[newIndex].add(new Pair(key, value));
        this.size++;

        //CALCULATE IF THERE's COLLISIONS/LOAD FACTOR
        //if load factor has met, double capacity and rehash
        if(checkLoadFactor()){
            this.table = this.growHashTable();
        }

    }

    /**
     * This private helper method determines if the table has
     * met the load factor threshold yet.
     * @return true if the threshold has been met, false if not.
     */
    private boolean checkLoadFactor(){
        //calculate load factor based on # of pairs / table capacity
        double loadFactor = (double)this.size / (double)this.getCapacity();
        //return true if load factor is greater than or equal to 80%
        return loadFactor >= 0.8;
    }

    /**
     * This method resizes the table to double the capacity
     * and rehashes all elements in the table beforehand.
     * @return
     */
    private LinkedList<Pair>[] growHashTable(){
        //initialize a new table with double the capacity
        LinkedList<Pair>[] newTable = new LinkedList[this.getCapacity() * 2];

        //go through all the LinkedLists in the table
        for(LinkedList<Pair> pairLists : this.table){
            //ensure pairLists aren't null before proceeding
            if(pairLists != null){
                //generate new index for each pair in each list
                for(Pair pair : pairLists){
                    int newIndex = Math.abs(pair.key.hashCode()) % newTable.length;
                    //if there's not yet a LinkedList in the new table
                    //at that index, add one
                    if(newTable[newIndex] == null){
                        newTable[newIndex] = new LinkedList<>();
                    }
                    //add the pair to the linkedlist
                    newTable[newIndex].add(pair);
                }
            }
        }
        //change the reference of the table to the new one
       return newTable;
    }

    /**
     * Checks whether a key maps to a value in this collection.
     *
     * @param key the key to check
     * @return true if the key maps to a value, and false is the
     * key doesn't map to a value
     */
    @Override
    public boolean containsKey(KeyType key) {
        //find the index to check that would match the key
        int indexOfKey = Math.abs(key.hashCode()) % getCapacity();
        //check every pair located in the LinkedList at this index
        if (this.table[indexOfKey] != null) {
            for (Pair pair : this.table[indexOfKey]){
                //if there's a match return true
                if (pair.key.equals(key)){
                    return true;
                }
            }
        }
        //otherwise return false
        return false;
    }

    /**
     * Retrieves the specific value that a key maps to.
     *
     * @param key the key to look up
     * @return the value that key maps to
     * @throws NoSuchElementException when key is not stored in this
     *                                collection
     */
    @Override
    public ValueType get(KeyType key) throws NoSuchElementException {

        //find the index to check that would match the key
        int indexOfKey = Math.abs(key.hashCode()) % getCapacity();

        //go through every pair in the index of the table
        if(this.table[indexOfKey] != null){
            for(Pair pair : this.table[indexOfKey]){
                //if there's a match, return the value
                if(pair.key.equals(key)){
                    return pair.value;
                }
            }
        }

        //otherwise throw a NoSuchElementException
        throw new NoSuchElementException("Given key is not " +
                "stored in this collection.");
    }

    /**
     * Remove the mapping for a key from this collection.
     *
     * @param key the key whose mapping to remove
     * @return the value that the removed key mapped to
     * @throws NoSuchElementException when key is not stored in this
     *                                collection
     */
    @Override
    public ValueType remove(KeyType key) throws NoSuchElementException {
        //check if the key is null - if so throw the exception
        if(key == null){
            throw new NoSuchElementException("Key is null.");
        }

        //find the index to check that would match the key
        int indexOfKey = Math.abs(key.hashCode()) % getCapacity();

        //go through every pair in the index of the table
        if (this.table[indexOfKey] != null) {
            for(Pair pair : this.table[indexOfKey]){
                //if there's a match, save that value and remove it
                if(pair.key.equals(key)){
                    //save the value
                    ValueType returnValue = pair.value;
                    //remove the pair from this LinkedList
                    table[indexOfKey].remove(pair);
                    //decrease size
                    this.size--;
                    return returnValue;
                }
            }
        }

        //otherwise, throw exception
        throw new NoSuchElementException("The specified key is not " +
                "present in this collection.");
    }

    /**
     * Removes all key,value pairs from this collection.
     */
    @Override
    public void clear() {
        //go through every index of the table and remove
        //all pairs
        for(int i = 0; i < table.length; ++i){
            //if the value at that index isn't null,
            //clear the LinkedList of any pairs
            if(table[i] != null){
                table[i].clear();
            }
        }
        //set size to 0
        this.size = 0;
    }

    /**
     * Retrieves the number of keys stored in this collection.
     *
     * @return the number of keys stored in this collection
     */
    @Override
    public int getSize() {
        return this.size;
    }

    /**
     * Retrieves this collection's capacity.
     *
     * @return the size of te underlying array for this collection
     */
    @Override
    public int getCapacity() {
        return this.table.length;
    }

    /**
     * Returns a LinkedList with all key pairs in the table.
     * @return a list of keys in the underlying array for this collection

    */
    @Override
    public List<KeyType> getKeys(){
	List<KeyType> keysList = new LinkedList<>();
	for(LinkedList<Pair> pairLists : this.table){
	 if(pairLists != null){
                //generate new index for each pair in each list
                for(Pair pair : pairLists){
			keysList.add(pair.key);
		}
	}
	}
	return keysList;

    }
    //JUNIT TESTS

    /**
     * This test determines if the put() and containsKey() methods
     * correctly put values into the map and determine if a key
     * is present in the map.
     */
    @Test
    public void hashtableMap_test1(){
        //default size
        HashtableMap<Integer, String> map = new HashtableMap<>();

        //putting a valid key-value pair shouldn't throw any exceptions
        try{
            map.put(1, "one");
        } catch(Exception e){
            System.out.println(e.getMessage());
            Assertions.fail();
        }

        //mapping to the same key should throw an IllegalArgumentException
        try{
            map.put(1, "oneInvalid");
            Assertions.fail();
        } catch(IllegalArgumentException e){
            //if there is no exception message then the test fails
            if(e.getMessage() == null || e.getMessage().isBlank()){
                Assertions.fail();
            }
            System.out.println(e.getMessage());
        } catch (Exception e){
            //should throw an IllegalArgumentException, so a different
            //exception fails this test
            System.out.println(e.getMessage());
            Assertions.fail();
        }

        //putting a null value for key should throw a NullPointerException
        try{
            map.put(null, "oneNullKey");
            Assertions.fail();
        } catch(NullPointerException e){
            //if there is no exception message then the test fails
            if(e.getMessage() == null || e.getMessage().isBlank()){
                Assertions.fail();
            }
            System.out.println(e.getMessage());
        } catch (Exception e){
            //should throw a NullPointerException, so a different
            //exception fails this test
            System.out.println(e.getMessage());
            Assertions.fail();
        }

        //map should currently just contain the 1, "one"
        Assertions.assertTrue(map.containsKey(1));

    }

    /**
     * This test determines if get() returns the correct value for the
     * specified key or throws a NoSuchElementException if needed.
     */
    @Test
    public void hashtableMap_test2(){
        //test get()
        //specified capacity
        HashtableMap<Integer, String> map = new HashtableMap<>(50);
        //put in a key-value pair
        map.put(2, "two");

        //using get() to find a valid key should work
        //if an exception is caught - test fails
        try{
            Assertions.assertEquals(map.get(2), "two");
        } catch (Exception e){
            System.out.println(e.getMessage());
            Assertions.fail();
        }

        //trying to get a key not in the map should throw a
        //NoSuchElementException
        try{
            map.get(3);
            Assertions.fail();
        } catch (NoSuchElementException e){
            //if there is no exception message then the test fails
            if(e.getMessage() == null || e.getMessage().isBlank()){
                Assertions.fail();
            }
            System.out.println(e.getMessage());
        } catch (Exception e){
            //should throw a NoSuchElementException, so a different
            //exception fails this test
            System.out.println(e.getMessage());
            Assertions.fail();
        }

    }

    /**
     * This test determines if remove() and clear() correctly
     * remove key-value pairs from the Hashtable map.
     */
    @Test
    public void hashtableMap_test3(){
        //test remove() and clear()
        HashtableMap<Integer, String> map = new HashtableMap<>();
        //put values into map
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        //removing a valid value
        try{
            //removing 1 should return its value too - "one"
            Assertions.assertEquals(map.remove(1), "one");
        } catch (Exception e){
            //there shouldn't be an exception - test fails if so
            System.out.println(e.getMessage());
            Assertions.fail();
        }

        //removing an invalid key should throw a NoSuchElementException
        try{
            map.remove(4);
            Assertions.fail();
        } catch (NoSuchElementException e){
            //if there is no exception message then the test fails
            if(e.getMessage() == null || e.getMessage().isBlank()){
                Assertions.fail();
            }
            System.out.println(e.getMessage());
        } catch (Exception e){
            //should throw a NoSuchElementException, so a different
            //exception fails this test
            System.out.println(e.getMessage());
            Assertions.fail();
        }

        //test clear()
        map.clear();

        //check size
        Assertions.assertEquals(map.getSize(), 0);

        //double check if a value is in it
        Assertions.assertFalse(map.containsKey(2));

    }

    /**
     * This test determines if getSize() correctly returns the
     * size of an empty or filled map.
     */
    @Test
    public void hashtableMap_test4(){
        //test getSize()
        HashtableMap<Integer, String> map = new HashtableMap<>();

        //test that an empty map returns 0
        Assertions.assertEquals(map.getSize(), 0);

        //put values into map
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        //check that size is 3
        Assertions.assertEquals(map.getSize(), 3);

    }

    /**
     * This test determines if getCapacity() correctly returns the
     * capacity of the Hashtable map.
     */
    @Test
    public void hashtableMap_test5(){
        //test getCapacity()
        //test that the default capacity is 64
        HashtableMap<Integer, String> map = new HashtableMap<>();

        Assertions.assertEquals(map.getCapacity(), 64);

        //test with a 0 capacity - change if an exception should be thrown
        HashtableMap<Integer, String> mapZero = new HashtableMap<>(0);
        Assertions.assertEquals(mapZero.getCapacity(), 0);

        //test with a specific capacity
        HashtableMap<Integer, String> mapFifty = new HashtableMap<>(50);
        Assertions.assertEquals(mapFifty.getCapacity(), 50);

    }

    
}
