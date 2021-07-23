/**
 @author       Marco Martinez
 @fileName     Slot.java
 @version      2.0
 @description  This is a record of Slot.
 @date         2/1/2019

 Program Change Log
 ==========================
 Name       Date      Description
 Marco      2/1       Create baseline for Slot.java
 Marco      2/7       Finalize Slot.java
 Marco      3/7       Redesign for reuse
 */

public class StringSlot extends GenericSlot{
    // CONSTANT DEFINITIONS
    public final int MAXBUCKETS = 20;

    // INSTANCE VARIABLE DECLARATIONS
    private String  key,
            data;

    // CLASS CONSTRUCTORS
    // (+) StringSlot()
    public StringSlot() {
        this.key = null;
        this.data = null;
    }

    // (+) StringSlot(String newData)
    public StringSlot(String newData) {
        if (newData != null) {
            this.key = newData.substring(0,9);
            this.data = newData.substring(9);
        } else {
            this.key = null;
            this.data = null;
        }
    }

    // (+) StringSlot(StringSlot newSlot)
    public StringSlot(StringSlot newSlot) {
        this.key = newSlot.key;
        this.data = newSlot.data;
    }

    // CHANGE STATE SERVICES
    // (+) void setKey(String newKey)
    public void setKey(String newKey) {
        if (newKey != null)
            this.key = newKey.substring(0,9);
        else
            this.key = null;
    }

    // (+) void setData(String newData)
    public void setData(String newData) {
        if (newData != null)
            this.data = newData;
        else
            this.data = null;
    }

    // READ STATE SERVICES
    // (+) boolean isLess(GenericItemType git)
    public boolean isLess(GenericItemType git) { return ( this.key.compareTo(((StringSlot) git).getKey()) < 0); }

    // (+) boolean isEqual(GenericItemType git)
    public boolean isEqual(GenericItemType git) { return ( this.key.compareTo(((StringSlot) git).getKey()) == 0); }

    // (+) boolean isGreater(GenericItemType git)
    public boolean isGreater(GenericItemType git) {
        return ( this.key.compareTo(((StringSlot) git).getKey()) > 0);
    }

    // (+) int determineIndex()
    public int determineIndex() {
        byte[] temp = key.getBytes();
        return ((int)temp[1] + (int)temp[3] + (int)temp[5]) % MAXBUCKETS;
    }

    // (+) String getKey()
    public String getKey() { return this.key; }

    // (+) String getData()
    public String getData() { return this.data; }

    // (+) String toString()
    public String toString()
    {
        return this.key + this.data;
    }
}
