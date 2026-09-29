package com.automatedfileorganizer;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {

    private final String logFile = "logs/organizer.log";

    public Logger(){
        createLogDirectory();
    }

    private void createLogDirectory(){

        java.io.File folder = new java.io.File("logs");

        if(!folder.exists()){
            folder.mkdirs();
        }
    }


    public void write(String message){

        try(FileWriter writer = new FileWriter(logFile, true)){

            writer.write(
                "[" + LocalDateTime.now() + "] "
                + message
                + System.lineSeparator()
            );

        }catch(IOException | RuntimeException e){

            System.out.println(
                "Cannot write log."
            );

        }
    }
}