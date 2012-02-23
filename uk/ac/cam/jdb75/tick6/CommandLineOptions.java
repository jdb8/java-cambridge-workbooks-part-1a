package uk.ac.cam.jdb75.tick6;

public class CommandLineOptions {

    public static String WORLD_TYPE_LONG = "--long";
    public static String WORLD_TYPE_AGING = "--aging";
    public static String WORLD_TYPE_ARRAY = "--array";
    private String worldType = null;
    private Integer index = null;
    private String source = null;
 
    public CommandLineOptions(String[] args) throws CommandLineException {
        int argsLength = args.length;
        if (1 == argsLength) {
            source = args[0];
        } else if (2 == argsLength) {
            source = args[0];
            index = Integer.parseInt(args[1]);
            worldType = WORLD_TYPE_ARRAY;
        } else if (3 == argsLength) {
            source = args[1];
            index = Integer.parseInt(args[2]);
            String suppliedType = args[0];
            if (WORLD_TYPE_LONG == suppliedType) { worldType = WORLD_TYPE_LONG; }
            else if (WORLD_TYPE_AGING == suppliedType) { worldType = WORLD_TYPE_AGING; }
            else if (WORLD_TYPE_ARRAY == suppliedType) { worldType = WORLD_TYPE_ARRAY; }
            else { throw new CommandLineException("Error: first argument must be either --array, --long, or --aging"); }
        } else {
            throw new CommandLineException("Error: Number of arguments must equal either 1, 2, or 3");
        }
     
    }
    public String getWorldType() {return worldType;}
    public Integer getIndex() {return index;}
    public String getSource() {return source;}
}
