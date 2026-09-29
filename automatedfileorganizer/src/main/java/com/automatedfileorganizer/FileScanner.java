package com.automatedfileorganizer;

import java.io.File;
import java.util.Objects;

public class FileScanner {

    private final ExtensionMapper mapper;
    private final FileOrganizer organizer;
    private final Logger logger;

    public FileScanner(
        ExtensionMapper mapper,
        FileOrganizer organizer,
        Logger logger
    ){
        this.mapper = Objects.requireNonNull(mapper);
        this.organizer = Objects.requireNonNull(organizer);
        this.logger = Objects.requireNonNull(logger);
    }

    public void scan(String path){

        if(path == null || path.isBlank()){
            report("Path cannot be empty. / La ruta no puede estar vacía.");
            return;
        }

        File directory = new File(path);

        try {
            if(!directory.exists()){

                report("Directory does not exist. / El directorio no existe.");
                return;
            }

            if(!directory.isDirectory()){

                report("Path is not a directory. / La ruta no es un directorio.");
                return;
            }

            scanDirectory(directory);

        } catch (RuntimeException e) {

            report("Cannot access directory. / No se puede acceder al directorio.", e);
        }
    }


    private void scanDirectory(File directory){

        File[] files;

        try {
            files = directory.listFiles();
        } catch (RuntimeException e) {
            report("Cannot read directory. / No se puede leer el directorio.", e);
            return;
        }

        if(files == null){

            String message =
                "Cannot read directory. / No se puede leer el directorio.";

            System.out.println(message);
            logger.write(message);

            return;
        }


        for(File file : files){

            try {
                if(file.isDirectory()){

                    if(file.getName().equals("logs")){
                        continue;
                    }

                    scanDirectory(file);

                }
                else if(file.isFile()){

                    String category = mapper.getCategory(file);

                    String message =
                        file.getName() + " -> " + category;

                    System.out.println(message);
                    logger.write(message);

                    organizer.organize(file, category);
                }
            } catch (RuntimeException e) {
                report("Cannot process file. / No se puede procesar el archivo.", e);
            }
        }
    }

    private void report(String message){
        report(message, null);
    }

    private void report(String message, RuntimeException exception){
        System.out.println(message);
        logger.write(
            exception == null
                ? message
                : message + " " + exception.getMessage()
        );
    }
}