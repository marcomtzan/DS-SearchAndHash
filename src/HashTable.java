/**
    @author       Marco Martinez
    @fileName     HashTable.java
    @version      2.0
    @description  Complete redesign with JList reuse.
    @date         2/1/2019

    Program Change Log
    ==========================
    Name     Date     Description
    Marco    2/1      Create baseline for HashTable.java
 */

public class HashTable {
    // CONSTANT DEFINITIONS
    public final int MAXBUCKETS = 20;

    // INSTANCE VARIABLE DECLARATIONS
    private Bucket[] ht = new Bucket[MAXBUCKETS];
    private int index;
   
    // CLASS CONSTRUCTORS
    // (+) HashTable()
    public HashTable() {
        for (int i = 0; i < MAXBUCKETS; i++)
            this.ht[i] = new Bucket();
    }

    // (+) HashTable(GenericSlot gs)
    public HashTable(GenericSlot gs) {
        for (int i = 0; i < MAXBUCKETS; i++)
            this.ht[i] = new Bucket();
        this.ht[gs.determineIndex()] = new Bucket(gs);
    }

    // (+) HashTable(HashTable ht)
    public HashTable(HashTable ht) {
        this.ht = ht.getHashTable();
    }
   
    // CHANGE STATE SERVICES
    // (+) void initialize()
    public void initialize() {
        for (int i = 0; i < MAXBUCKETS; i++)
            this.ht[i] = new Bucket();
        this.index = 0;
    }

    // (+) void insertIntoHT(GenericSlot data)
    public void insertIntoHT(GenericSlot data) {
        int hashIndex = data.determineIndex();
        if (hashIndex < MAXBUCKETS)
            this.ht[hashIndex].add_fromTail(data);
    }

    // (+) GenericItemType searchHT(GenericSlot data)
    public GenericItemType searchHT(GenericSlot data) {
        int hashIndex = data.determineIndex();
        if (hashIndex < MAXBUCKETS)
            return ht[hashIndex].linearSearch(data);
        return null;
    }

    // (+) void deleteFromHT(GenericSlot data)
    public void deleteFromHT(GenericSlot data) {
        int hashIndex = data.determineIndex();
        if (hashIndex < MAXBUCKETS)
            this.ht[hashIndex].remove(data);
    }

    // READ STATE SERVICES
    // (+) void Iterator_initialize()
    public void Iterator_initialize() {
      this.index = 0;
   }
   
    // (+) boolean Iterator_hasNext()
    public boolean Iterator_hasNext() {
      return this.index < MAXBUCKETS;
   }

    // (+) Bucket Iterator_getNext()
    public Bucket Iterator_iterate() {
      return new Bucket(this.ht[this.index++]);
   }

    // (+) int findLocation(GenericSlot key)
    public int findLocation(GenericSlot key) {
        if (ht[key.determineIndex()].linearSearch(key) != null)
            return this.ht[key.determineIndex()].searchLocation(key);
        return -1;
    }

    // (+) int getIndex()
    public int getIndex() {
      return this.index;
   }

    // (+) Bucket[] getHashTable()
    public Bucket[] getHashTable() {
      return this.ht;
   }

    // (+) Bucket getHashTable(int index)
    public Bucket getHashTable(int index) {
        if (index < MAXBUCKETS)
            return this.ht[index];
        return new Bucket();
    }
   
    // (+) int getMax()
    public int getMax() {
      return this.MAXBUCKETS;
   }
}