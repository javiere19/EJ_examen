package org.vaadin.example;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;
/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 * <p>
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */
@Route
public class MainView extends VerticalLayout {

    // Use TextField for standard text input
    ArrayList<Spaceship> spaceshipArrayList = new ArrayList<Spaceship>();

    public MainView(@Autowired GreetService service) {

        // Use TextField for standard text input
        TextField textField = new TextField("Page");
        textField.addThemeName("bordered");

        Grid<Spaceship> grid = new Grid<>();
        grid.setItems(spaceshipArrayList);
        grid.addColumn(Spaceship::getName).setHeader("Name");
        grid.addColumn(Spaceship::getModel)
                .setHeader("Model");
        grid.addColumn(Spaceship::getManufacturer).setHeader("Manufacturer");
        grid.addColumn(Spaceship::getLength)
                .setHeader("Length");
        grid.addColumn(Spaceship::getCrew)
                .setHeader("Crew");
        grid.addColumn(
                new NativeButtonRenderer<>("Remove item",
                        clickedItem -> {
                            // Llamada al método de eliminar
                            ArrayList<Spaceship> listWithoutDeleted = deleteItems(spaceshipArrayList, clickedItem);
                            spaceshipArrayList = listWithoutDeleted;
                            grid.setItems(listWithoutDeleted);

                        })
        );

        Button getSpaceships = new Button("Obtener naves",
                e -> {
                    if (textField.getValue().isEmpty() || textField.getValue() == null) {
                        spaceshipArrayList.addAll(service.getShips());
                        grid.getDataProvider().refreshAll();

                    } else {
                        int number = Integer.parseInt(textField.getValue());
                        if (number > 4 || number < 1) {
                            Notification.show("Número de página no está en rango establecido");
                            return;
                        }
                        int page = Integer.parseInt(textField.getValue());
                        try {
                            ArrayList<Spaceship> ships = service.getShips(page);
                            spaceshipArrayList.addAll(ships);
                            grid.getDataProvider().refreshAll();
                        } catch (URISyntaxException ex) {
                            throw new RuntimeException(ex);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });

        Button saveData = new Button("Guardar",
                e -> {
                    service.saveShips(spaceshipArrayList);
                });

        Button saveData2 = new Button("PDF Completo",
                e -> {
                    service.saveShips2(spaceshipArrayList);
                });

        // Create dialog for adding a new spaceship
        Dialog addSpaceshipDialog = new Dialog();
        TextField nameField = new TextField("Name");
        TextField modelField = new TextField("Model");
        TextField manufacturerField = new TextField("Manufacturer");
        TextField lengthField = new TextField("Length");
        TextField crewField = new TextField("Crew");

        Button confirmAddSpaceship = new Button("Confirmar", e -> {
            try {
                String name = nameField.getValue();
                String model = modelField.getValue();
                String manufacturer = manufacturerField.getValue();
                String length = lengthField.getValue();
                String crew = crewField.getValue();

                Spaceship newSpaceship = new Spaceship(name, model, manufacturer, length, crew);
                spaceshipArrayList.add(newSpaceship);
                grid.getDataProvider().refreshAll();

                nameField.clear();
                modelField.clear();
                manufacturerField.clear();
                lengthField.clear();
                crewField.clear();

                addSpaceshipDialog.close();
            } catch (NumberFormatException ex) {
                Notification.show("Please enter valid numbers for Length and Crew");
            }
        });

        Button cancelAddSpaceship = new Button("Cancelar", e -> addSpaceshipDialog.close());

        HorizontalLayout dialogButtons = new HorizontalLayout(confirmAddSpaceship, cancelAddSpaceship);
        VerticalLayout dialogLayout = new VerticalLayout(nameField, modelField, manufacturerField, lengthField, crewField, dialogButtons);
        addSpaceshipDialog.add(dialogLayout);

        Button openAddSpaceshipDialog = new Button("Añadir nave", e -> addSpaceshipDialog.open());

        add(textField, getSpaceships, grid, saveData, saveData2, openAddSpaceshipDialog);
    }

    public static ArrayList<Spaceship> deleteItems(ArrayList<Spaceship> spaceshipArrayList, Spaceship item) {
        ArrayList<Spaceship> clean = new ArrayList<>();
        for (Spaceship spaceship : spaceshipArrayList) {
            if (!Objects.equals(spaceship.getName(), item.getName())) {
                clean.add(spaceship);
            }
        }
        return clean;
    }

}
