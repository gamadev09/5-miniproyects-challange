package com.automatedfileorganizer;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class main{
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        ConfigLoader loader = new ConfigLoader();
        Map<String, List<String>> categories = loader.loadCategories();

        ExtensionMapper mapper = new ExtensionMapper(categories);

        Logger logger = new Logger();

        FileOrganizer organizer = new FileOrganizer(logger);

        FileScanner scanner = new FileScanner(
            mapper,
            organizer,
            logger
        );

        String directory;
        
        System.out.println("Automated File Organization");
        System.out.println("Please select:");
        System.out.println("1. English");
        System.out.println("2. Spanish");

        int language = input.nextInt();
        input.nextLine();
        
        switch(language){

            case 1:
                System.out.println("Please write your path to order");
                directory = input.nextLine();
                scanner.scan(directory);
            break;

            case 2:
                System.out.println("Porfavor ingresa el directorio a ordenar");
                directory = input.nextLine();
                scanner.scan(directory);
            break;

            default:
                System.out.println("Invalid option/Opción Inválida");
            break;
        }
            
        input.close();
    }
}