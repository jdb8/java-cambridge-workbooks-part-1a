package uk.ac.cam.jdb75.tick4star;

public class Repeat {
    public static void main(String[] args) {
        System.out.println(parseAndRep(args));
    }

    /*
    * Return the first string repeated by the number of times
    * specified by the integer in the second string, for example 
    *   
    *    parseAndRep(new String[]{"one","3"}) 
    *
    * should return "one one one". Adjacent copies of the repeated 
    * string should be separated by a single space.
    *
    * Return a suitable error message in a string when there are 
    * not enough arguments in "args" or the second argument is 
    * not a valid positive integer. For example:
    *
    *  - parseAndRep(new String[]{"one"}) should return 
    *    "Error: insufficient arguments"
    *
    *  - parseAndRep(new String[]{"one","five"}) should return 
    *    "Error: second argument is not a positive integer"
    */
    public static String parseAndRep(String[] args) {
        try {
            int rep = Integer.parseInt(args[1]);
            if (rep <= 0) {throw new NumberFormatException();}
            String store = "";
            for (int i = 0; i < rep - 1; i++) { // minus one in order to get rid of trailing space
                store += args[0] + " "; 
            }
            store += args[0]; // final word is appended here
            return store;        
        }
        catch (ArrayIndexOutOfBoundsException a) {
            return "Error: insufficient arguments";
        }
        catch (NumberFormatException b) {
            return "Error: second argument is not a positive integer";
        }
    }  
}