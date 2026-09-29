package com.automatedfileorganizer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigLoader {

    public Map<String, List<String>> loadCategories() {

        ObjectMapper mapper = new ObjectMapper();

        File file = findCategoriesFile();

        if(file == null){
            System.out.println(
                "The .json config file is not available."
            );
            return Map.of();
        }

        try {
            return mapper.readValue(
                file,
                new TypeReference<Map<String, List<String>>>() {}
            );

        } catch (IOException | RuntimeException e) {
            System.out.println("The .json config file is not available.");
        }

        return Map.of();
    }

    private File findCategoriesFile(){
        File[] candidates = {
            new File("config/categories.json"),
            new File("automatedfileorganizer/config/categories.json")
        };

        for(File candidate : candidates){
            try {
                if(candidate.isFile()){
                    return candidate;
                }
            } catch (RuntimeException e) {
                // Try the next known project location.
            }
        }

        return null;
    }
}