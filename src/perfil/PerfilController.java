/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package perfil;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import upv.ipc.sportlib.*;

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
    private TableView<Session> tablaHistorial;
    @FXML
    private TableColumn<Session, String> colInicio;
    @FXML
    private TableColumn<Session, String> colFin;
    @FXML
    private TableColumn<Session, String> colDuracion;
    @FXML
    private TableColumn<Session, Integer> colImportadas;
    @FXML
    private TableColumn<Session, Integer> colVistas;
    @FXML
    private TableColumn<Session, Integer> colAnotaciones;
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
    @FXML
    private ImageView imgAvatar;

    private SportActivityApp app = SportActivityApp.getInstance();
    private User usuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        usuario = app.getCurrentUser();
        cargarDatosUsuario();

    }

    private void cargarDatosUsuario() {
        lblNombre.setText(usuario.getNickName());
        lblEmail.setText(usuario.getEmail());
        lblFechaNacimiento.setText(usuario.getBirthDate().toString());
        if (usuario.getAvatar() != null) {
            imgAvatar.setImage(usuario.getAvatar());
        } else {
            imgAvatar.setImage(new Image(
                    getClass().getResourceAsStream("../resources/icons/userDefault.png")
            ));
        }

        cargarHistorial();
    }

    // Con ayuda de IA
    private void cargarHistorial() {
        List<Session> sesiones = app.getSessionsByUser(usuario);
        tablaHistorial.setItems(FXCollections.observableArrayList(sesiones));

        colInicio.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getStartTime().format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                )
        )
        );
        colFin.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getEndTime().format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                )
        )
        );
        colDuracion.setCellValueFactory(data -> {
            long minutos = data.getValue().getDuration().toMinutes();
            long horas = minutos / 60;
            long mins = minutos % 60;
            return new SimpleStringProperty(horas + "h " + mins + "m");
        });

        colImportadas.setCellValueFactory(data -> new SimpleIntegerProperty(
                data.getValue().getImportedActivities()).asObject()
        );

        colVistas.setCellValueFactory(data -> new SimpleIntegerProperty(
                data.getValue().getViewedActivities()).asObject()
        );

        colAnotaciones.setCellValueFactory(data -> new SimpleIntegerProperty(
                data.getValue().getAnnotationsCreated()).asObject()
        );
        cargarTotales(sesiones);
    }

    // Con ayuda de IA
    private void cargarTotales(List<Session> sesiones) {
        // número de sesiones
        lblTotalSesiones.setText(String.valueOf(sesiones.size()));

        // duración total
        long minutosTotales = sesiones.stream().mapToLong(s -> s.getDuration().toMinutes()).sum();
        long horas = minutosTotales / 60;
        long mins = minutosTotales % 60;
        lblTotalDuracion.setText(horas + "h " + mins + "m");

        // actividades importadas
        int totalImportadas = sesiones.stream().mapToInt(s -> s.getImportedActivities()).sum();
        lblTotalImportadas.setText(String.valueOf(totalImportadas));

        // actividades vistas
        int totalVistas = sesiones.stream().mapToInt(s -> s.getViewedActivities()).sum();
        lblTotalVistas.setText(String.valueOf(totalVistas));

        // anotaciones creadas
        int totalAnotaciones = sesiones.stream().mapToInt(s -> s.getAnnotationsCreated()).sum();
        lblTotalAnotaciones.setText(String.valueOf(totalAnotaciones));
    }

    @FXML
    private void onCerrarSesion(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar sesión");
        alert.setHeaderText("¿Estás seguro?");
        alert.setContentText("Se cerrará la sesión actual.");

        Optional<ButtonType> resultado = alert.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            app.logout();
            Parent root = FXMLLoader.load(getClass().getResource("/login/FXML.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    private void onModificarPerfil(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModificarPerfil.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Modificar perfil");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(btnModificarPerfil.getScene().getWindow());
        stage.showAndWait();

        usuario = app.getCurrentUser();
        cargarDatosUsuario();

    }

    @FXML
    private void onVolverInicio(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../inicio/Inicio.fxml"));
        Stage stage = (Stage) btnInicio.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}
