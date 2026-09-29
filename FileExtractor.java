import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class FileExtractor {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Directory to extract:");
        String path = input.nextLine();

        File mainFolder = new File(path);

        extract(mainFolder, mainFolder);

        input.close();

        System.out.println("Finished.");
    }


    public static void extract(File mainFolder, File currentFolder){

        File[] files = currentFolder.listFiles();

        if(files == null){
            return;
        }


        for(File file : files){

            if(file.isDirectory()){

                // ignore the main folder itself
                extract(mainFolder, file);

            }
            else if(file.isFile()){

                // don't move files already in main folder
                if(file.getParentFile().equals(mainFolder)){
                    continue;
                }


                File destination = new File(
                    mainFolder,
                    file.getName()
                );


                // avoid overwriting
                if(destination.exists()){

                    System.out.println(
                        "Already exists: " + file.getName()
                    );

                    continue;
                }


                try{

                    Files.move(
                        file.toPath(),
                        destination.toPath()
                    );


                    System.out.println(
                        "Moved: " + file.getName()
                    );


                }catch(Exception e){

                    System.out.println(
                        "Cannot move: " + file.getName()
                    );
                }
            }
        }
    }
}