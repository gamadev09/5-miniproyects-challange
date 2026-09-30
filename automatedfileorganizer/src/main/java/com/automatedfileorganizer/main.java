package com.automatedfileorganizer;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Logger logger = new Logger();

        try {
            ConfigLoader loader = new ConfigLoader();
            Map<String, List<String>> categories = loader.loadCategories();
            ExtensionMapper mapper = new ExtensionMapper(categories);
            FileOrganizer organizer = new FileOrganizer(logger);
            FileScanner scanner = new FileScanner(mapper, organizer, logger);
            runOrganizer(input, scanner, logger);
        } catch (Exception exception) 
        {
            reportError
            (
                "Unexpected application error. / Error inesperado de la aplicación.",
                exception,
                logger
            );
        } finally 
        {
            input.close();
        }
    }

    private static void runOrganizer(Scanner input, FileScanner scanner, Logger logger) {
        boolean running = true;

        while (running && input.hasNextLine()) 
            {
            try {

                String directory = input.nextLine().trim();
                if (directory.isEmpty()) 
                {
                    System.out.println("The path cannot be empty. / La ruta no puede estar vacía.");
                    continue;
                }

                scanner.scan(directory);
                System.out.println("We finished orginizing your files. Press Enter to organize another path or type exit.");

                if (!input.hasNextLine()) 
                {
                    break;
                }

                running = !"exit".equalsIgnoreCase(input.nextLine().trim());
            } catch (Exception exception) 
            {
                reportError(
                    "The action failed, but the program is still running. / "
                        + "La acción falló, pero el programa continúa ejecutándose.",
                    exception,
                    logger
                );
            }
        }
    }

    private static void reportError(String message, Exception exception, Logger logger) 
    {
        System.out.println(message);
        String details = exception.getMessage();
        logger.write(message + (details == null ? "" : " " + details));
    }
}
