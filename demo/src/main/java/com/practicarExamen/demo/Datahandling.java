package com.practicarExamen.demo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.reflect.Type;
public class Datahandling {
    public static void saveFile (ArrayList<Spaceship> spaceshipList) {
        Gson gson = new Gson();

        try {
            FileWriter writer = new FileWriter("ships.json");
            gson.toJson(spaceshipList, writer);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static ArrayList<Spaceship> getShips () {
        Gson gson = new Gson();
        Type spaceshipsListType = new TypeToken<ArrayList<Spaceship>>(){}.getType();
        try {
            return gson.fromJson(new FileReader("ships.json"), spaceshipsListType);
        } catch (FileNotFoundException e) {
            System.out.println("Fichero no encontrado. Se devuelve un array vacío");
            return new ArrayList<>();
        }
    }
}
