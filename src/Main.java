/**   
   @author       Marco Martinez
   @fileName     Main.java
   @version      1.0
   @description  First assignment testing.
   @date         2/1/2019

   Program Change Log 
   ==========================
   Name     Date     Description
   Marco    2/1      Test for ability to read in and write out text files.
 */
 
// LIBRARIES
import java.io.*;

public class Main
{
    public static void main(String[] args)
    {
        try {
            HashTable ht = new HashTable();
            InputStream in = new FileInputStream("DATA.dat");
            InputStream isIn = new FileInputStream("SEARCH.dat");
            InputStream load = new FileInputStream("SAVE.dat");
            OutputStream save = new FileOutputStream("SAVE.dat");
            OutputStream preRestore = new FileOutputStream("PRERESTORE.txt");
            OutputStream postRestore = new FileOutputStream("POSTRESTORE.txt");
            OutputStream searchResults = new FileOutputStream("SEARCHRESULTS.txt");
            OutputStream efficiencyResults = new FileOutputStream("EFFICIENCYRESULTS.txt");

            ht.initialize();
            readData(ht,in);
            in.close();
            reportContentsOfHT(ht,preRestore);
            saveState(ht,save);
            save.close();
            loadState(ht,load);
            load.close();
            reportContentsOfHT(ht,postRestore);
            outputSearchResults(ht,isIn,searchResults);
            isIn.close();
            outputEfficiency(ht,efficiencyResults);
            efficiencyResults.close();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
   
    // METHODS
    // (+) static void reportContentsOfHT(HashTable ht,OutputStream out)
    public static void reportContentsOfHT(HashTable ht,OutputStream out) {
        try {
            int i = 1;
            String file = new String("");
            byte[] buffer = new byte[4096];
            ht.Iterator_initialize();
            while (ht.Iterator_hasNext()) {
                Bucket temp = ht.Iterator_iterate();
                file += "   Bucket " + Integer.toString(i) + ":\n";
                file += "   ----------------------------------\n";
                temp.Iterator_initialize();
                while (temp.Iterator_hasNext()) {
                    file += "   " + ((StringSlot)temp.Iterator_iterate()).toString() + "\n";
                }
                file += "\n";
                i++;
            }
            buffer = file.getBytes();
            out.write(buffer);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // (+) static String[] convertInToString(InputStream in)
    public static String[] convertInToString(InputStream in) {
        try {
            byte[] data = new byte[4096];
            String file;

            in.read(data);
            file = new String(data, "UTF-8");
            return file.split("\\r?\\n");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            return new String[0];
        }
    }

    // (+) static void outputSearchResults(HashTable ht,InputStream isIn, OutputStream searchResults)
    public static void outputSearchResults(HashTable ht,InputStream isIn, OutputStream searchResults) {
        try {
            String[] search = convertInToString(isIn);
            String file = new String("");
            byte[] buffer = new byte[4096];

            file += "   Search Key          Bucket/Position         Record\n";
            file += "   -------------------------------------------------------\n";
            for (int i = 0; i < search.length - 1; i++) {
                if (ht.searchHT(new StringSlot(search[i])) != null) {
                    file += String.format("%-28s","   " + search[i]) + String.format("%-19s",new StringSlot(search[i]).determineIndex()+1 + "/" + (ht.findLocation(new StringSlot(search[i]))+1)) + String.format("%-10s",ht.searchHT(new StringSlot(search[i])).toString() + "\n");
                } else {
                    file += "   " + search[i] + "                                  was not found.\n";
                }
            }

            buffer = file.getBytes();
            searchResults.write(buffer);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // (+) static void outputEfficiency(HashTable ht,OutputStream efficiencyResults)
    public static void outputEfficiency(HashTable ht,OutputStream efficiencyResults) {
        try {
            String file = new String("");
            byte[] buffer = new byte[4096];
            int totalCollisions = 0;
            int totalCollidedBuckets = 0;

            file += "   Program Collision Report\n" +
                    "   ----------------------------\n";
            ht.Iterator_initialize();
            while (ht.Iterator_hasNext()) {
                Bucket buc = ht.Iterator_iterate();
                file += "   Bucket " + ht.getIndex() + " has a length of " + buc.getCount() + ".\n";
                if (buc.getCount() > 1) {
                    totalCollisions += buc.getCount();
                    totalCollisions--;
                    totalCollidedBuckets++;
                }
            }
            file += "\n";
            file += "   There was a total of " + totalCollisions + " collisions within this hashing algorithm.\n";
            file += "   The average length per collision was " + ((float)totalCollisions/totalCollidedBuckets) + ".\n";

            buffer = file.getBytes();
            efficiencyResults.write(buffer);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // (+) static void saveState(HashTable ht, OutputStream out)
    public static void saveState(HashTable ht, OutputStream out) {
        try {
            String file = new String();
            Bucket listTemp;
            byte[] buffer = new byte[4096];
            ht.Iterator_initialize();
            StringSlot temp;
            while (ht.Iterator_hasNext()) {
                listTemp = ht.Iterator_iterate();
                listTemp.Iterator_initialize();
                while (listTemp.Iterator_hasNext()) {
                    temp = (StringSlot) listTemp.Iterator_iterate();
                    file += temp.getKey() + temp.getData() + "\n";
                }
                file += "EOBUCKET\n";
            }
            buffer = file.getBytes();
            out.write(buffer);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // (+) static void readData(HashTable ht, InputStream in)
    public static void readData(HashTable ht, InputStream in) {
        try {
            byte[] data = new byte[4096];
            String file;
            String[] lines;
            in.read(data);
            file = new String(data, "UTF-8");
            lines = file.split("\\r?\\n");

            for (int i = 0; i < lines.length-1; i++) {
                ht.insertIntoHT(new StringSlot(lines[i]));
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // (+) static void loadState(HashTable ht, InputStream in)
    public static void loadState(HashTable ht, InputStream in) {
        try {
            byte[] data = new byte[4096];
            String file;
            String[] lines;
            in.read(data);
            int count = 0;
            file = new String(data, "UTF-8");
            lines = file.split("\\r?\\n");

            for (int i = 0; i < ht.getMax(); i++) {
                ht = new HashTable();
                while (!lines[count].equals("EOBUCKET")) {
                    if (!lines[count].substring(0,3).equals("null")) {
                        ht.getHashTable(i).add_fromTail(new StringSlot(lines[count]));
                    } else {
                        ht.getHashTable(i).add_fromTail(new StringSlot());
                    }
                    count++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}