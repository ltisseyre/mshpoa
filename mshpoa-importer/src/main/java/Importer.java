/**
 * Created by ltisseyre on 22/01/17.
 */

public class Importer {

    /**
     * main method, it take at least one argument (file path)
     * @param args
     */
    public static void main(String [] args){
        if(args.length < 1){
            throw new IllegalArgumentException("At least one file is required.");
        }

        try{
            //load configuration
            FileImporter fileImporter = new FileImporter();

            //import each file in args
            for(String arg : args){
                fileImporter.importFile(arg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
