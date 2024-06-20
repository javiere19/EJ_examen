package com.practicarExamen.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
public class Datacontroller {



    @RestController
    public class Datacontroller {
        @PostMapping(value = "/ships",
                consumes = MediaType.APPLICATION_JSON_VALUE)
        public HttpStatus saveShips (@RequestBody ArrayList<Spaceship> spaceshipsList) {
            Datahandling.saveFile(spaceshipsList);
            ControlPDF manager = new ControlPDF();
            for (Spaceship ship : spaceshipsList) {
                manager.createPDF(ship);
            }
            return HttpStatus.OK;
        }

        @PostMapping(value = "/shipsCompleto",
                consumes = MediaType.APPLICATION_JSON_VALUE)
        public HttpStatus saveShipsCompleto (@RequestBody ArrayList<Spaceship> spaceshipsList) {
            Datahandling.saveFile(spaceshipsList);
            ControlPDF manager = new ControlPDF();
            manager.createPDF2(spaceshipsList);
            return HttpStatus.OK;
        }


        @GetMapping("/ships")
        public ResponseEntity<ArrayList<Spaceship>> getShips () {
            ArrayList<Spaceship> shipsList = Datahandling.getShips();
            return new ResponseEntity<>(shipsList, HttpStatus.OK);
        }
    }
}
