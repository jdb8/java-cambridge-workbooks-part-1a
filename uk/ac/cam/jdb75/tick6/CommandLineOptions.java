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
            if (args[0].startsWith("--")) {
                source = args[1];
            } else {
                source = args[0];
                try {
                    index = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    throw new CommandLineException("Error: second argument must be an integer");
                }
                worldType = WORLD_TYPE_ARRAY;
            }
            
        } else if (3 == argsLength) {
            source = args[1];
            try {
                index = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                throw new CommandLineException("Error: third argument must be an integer");
            }
            String suppliedType = args[0];
            if (suppliedType.equals(WORLD_TYPE_LONG)) { worldType = WORLD_TYPE_LONG; }
            else if (suppliedType.equals(WORLD_TYPE_AGING)) { worldType = WORLD_TYPE_AGING; }
            else if (suppliedType.equals(WORLD_TYPE_ARRAY)) { worldType = WORLD_TYPE_ARRAY; }
            else { throw new CommandLineException("Error: first argument must be either --array, --long, or --aging"); }
        } else {
            throw new CommandLineException("Error: Number of arguments must equal either 1, 2, or 3");
        }
     
    }
    public String getWorldType() {return worldType;}
    public Integer getIndex() {return index;}
    public String getSource() {return source;}
}
