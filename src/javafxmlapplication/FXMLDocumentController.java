/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import upv.ipc.sportlib.Activity;
import upv.ipc.sportlib.SportActivityApp;

/**
 * FXML Controller class
 *
 * @author derimansantossantana
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button registrar_actividad;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void subirActividad(ActionEvent event) {
    
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Seleccionar fichero de actividad (GPX)");

    
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("Ficheros GPX", "*.gpx")
    );

    
    File selectedFile = fileChooser.showOpenDialog(
        ((Node) event.getSource()).getScene().getWindow()
    );

   
    if (selectedFile != null) {
        try {
           
            SportActivityApp app = SportActivityApp.getInstance();
            Activity nuevaActividad = app.importActivity(selectedFile);

           
            if (nuevaActividad != null) {
                System.out.println("Éxito: Actividad '" + nuevaActividad.getName() + "' registrada.");
               
                
            } else {
                
                System.err.println("Error: El fichero no se ha podido procesar.");
              
            }

        } catch (Exception e) {
            
            System.err.println("Error crítico de sistema: " + e.getMessage());
        }
    } else {
        System.out.println("Operación cancelada por el usuario.");
    }
    
}
} 
