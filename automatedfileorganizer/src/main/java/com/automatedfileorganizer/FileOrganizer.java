package com.automatedfileorganizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class FileOrganizer {

    private final Logger logger;

    public FileOrganizer(Logger logger){
        this.logger = Objects.requireNonNull(logger);
    }

    public void organize(File file, String category){

        if(file == null || category == null || category.isBlank()){
            report("Cannot organize an invalid file or category.");
            return;
        }

        if("unknown".equals(category)){

            String message =
                "Skipped unknown file. / Archivo desconocido omitido: "
                + file.getName();

            System.out.println(message);
            logger.write(message);

            return;
        }

        File parentFolder = file.getParentFile();

        if(parentFolder == null){

            String message =
                "Cannot find parent folder: "
                + file.getName();

            System.out.println(message);
            logger.write(message);

            return;
        }


        File categoryFolder = new File(
            parentFolder,
            category
        );


        try {
            if(!categoryFolder.exists() && !categoryFolder.mkdirs()){
                report("Cannot create category folder: " + category);
                return;
            }

            if(!categoryFolder.isDirectory()){
                report("Category path is not a directory: " + category);
                return;
            }
        } catch (RuntimeException e) {
            report("Cannot access category folder: " + category, e);
            return;
        }


        File destination = new File(
            categoryFolder,
            file.getName()
        );


        if(destination.exists()){

            String message =
                "File already exists. / El archivo ya existe: "
                + file.getName();

            System.out.println(message);
            logger.write(message);

            return;
        }


        try {

            Files.move(
                file.toPath(),
                destination.toPath()
            );


            String message =
                file.getName()
                + " moved to / se movio a "
                + category;


            System.out.println(message);
            logger.write(message);


        } catch (IOException | RuntimeException e){

            String message =
                "Cannot move / No se puede mover: "
                + file.getName();


            System.out.println(message);
            logger.write(message);

            logger.write(e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage());
        }
    }

    private void report(String message){
        System.out.println(message);
        logger.write(message);
    }

    private void report(String message, RuntimeException exception){
        System.out.println(message);
        logger.write(message + " " + exception.getMessage());
    }
}