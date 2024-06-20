package org.vaadin.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;

@Service
public class GreetService implements Serializable {

    API requests;
    ArrayList<Spaceship> spaceshipArrayList = new ArrayList<>();

    public GreetService(@Autowired API requests) {
        this.requests = requests;
    }

    public ArrayList<Spaceship> getShips(int page) throws URISyntaxException, IOException, InterruptedException {
        this.spaceshipArrayList = requests.getShips(page);
        return this.spaceshipArrayList;
    }

    public ArrayList<Spaceship> getShips() {
        try {
            this.spaceshipArrayList = requests.getShips();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this.spaceshipArrayList;
    }

    public ArrayList<Spaceship> deleteEntries (ArrayList<Spaceship> shipsList) {
        return new ArrayList<>();
    }
    public void saveShips(ArrayList<Spaceship> shipsList) {
        requests.saveData(shipsList);
    }

    public void saveShips2(ArrayList<Spaceship> shipsList) {
        requests.saveData2(shipsList);
    }

}
