/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package perfil;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
/**
 * FXML Controller class
 *
 * @author yach
 */
public class PerfilController implements Initializable {


    @FXML
    private Label lblNombre;
    @FXML
    private Label lblEmail;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Button btnModificarPerfil;
    @FXML
    private Button btnInicio;
    @FXML
    private Label lblFechaNacimiento;
    @FXML
    private TableView<?> tablaHistorial;
    @FXML
    private TableColumn<?, ?> colInicio;
    @FXML
    private TableColumn<?, ?> colFin;
    @FXML
    private TableColumn<?, ?> colDuracion;
    @FXML
    private TableColumn<?, ?> colImportadas;
    @FXML
    private TableColumn<?, ?> colVistas;
    @FXML
    private TableColumn<?, ?> colAnotaciones;
    @FXML
    private Label lblTotalSesiones;
    @FXML
    private Label lblTotalDuracion;
    @FXML
    private Label lblTotalImportadas;
    @FXML
    private Label lblTotalVistas;
    @FXML
    private Label lblTotalAnotaciones;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onCerrarSesion(ActionEvent event) {
    }

    @FXML
    private void onModificarPerfil(ActionEvent event) {
    }

    @FXML
    private void onVolverInicio(ActionEvent event) {
    }
    
}
