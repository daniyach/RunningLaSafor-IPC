/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package inicio;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import upv.ipc.sportlib.Activity;
import upv.ipc.sportlib.SportActivityApp;
import upv.ipc.sportlib.User;

/**
 * FXML Controller class
 *
 * @author yach
 */
public class InicioController implements Initializable {

    @FXML
    private VBox sideBarB2;
    @FXML
    private ImageView imgAvatarSidebar;
    @FXML
    private Label lblUsuario;
    @FXML
    private Button btnMapa;
    @FXML
    private Button btnActividades;
    @FXML
    private Button btnPerfil;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblSaludo;
    @FXML
    private Button btnPerfilTarjeta;
    @FXML
    private Button btnVerTodas;
    @FXML
    private ListView<Activity> listUltimasActividades;

    private SportActivityApp app = SportActivityApp.getInstance();
    private User usuario = app.getCurrentUser();
    private Button btnActivo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        app.login("testuser", "Password1!");

        usuario = app.getCurrentUser();

        cargarDatosUsuario();
        cargarUltimasActividades();
        activarBoton(btnMapa);

    }

    private void cargarDatosUsuario() {
        lblUsuario.setText(usuario.getNickName());
        if (usuario.getAvatar() != null) {
            imgAvatarSidebar.setImage(usuario.getAvatar());
        }
        // Uso de IA
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "EEE dd MMM yyyy", new Locale("es")
        );
        lblFecha.setText("Inicio: " + LocalDate.now().format(formatter).toUpperCase());
        lblSaludo.setText("Hola, " + usuario.getNickName());
    }

    private void cargarUltimasActividades() {
        List<Activity> actividades = app.getUserActivities();
        if (actividades != null && !actividades.isEmpty()) {
            // Cargamos las últimas 4 actividades del usuario.
            int total = Math.min(4, actividades.size());
            listUltimasActividades.getItems().setAll(
                    actividades.subList(0, total)
            );
        }
    }

    // Idea de IA
    private void activarBoton(Button boton) {
        if (btnActivo != null) {
            btnActivo.getStyleClass().remove("nav-btn-active");
        }
        boton.getStyleClass().add("nav-btn-active");
        btnActivo = boton;
    }

    @FXML
    private void onMostrarMapa(ActionEvent event) throws IOException {
        activarBoton(btnMapa);
        Parent root = FXMLLoader.load(getClass().getResource("../mapademo/FXMLDocument.fxml"));
        Stage stage = (Stage) btnMapa.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void onMostrarActividades(ActionEvent event) {
        // TEMPORAL - hasta que esté listo Actividad
        activarBoton(btnActividades);

    }

    @FXML
    private void onMostrarPerfil(ActionEvent event) throws IOException {
        activarBoton(btnPerfil);
        Parent root = FXMLLoader.load(
                getClass().getResource("../perfil/Perfil.fxml")
        );
        Stage stage = (Stage) btnPerfil.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void onCerrarSesion(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar sesión");
        alert.setHeaderText("¿Estás seguro?");
        alert.setContentText("Se cerrará la sesión actual.");

        Optional<ButtonType> resultado = alert.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            app.logout();
            // TEMPORAL - hasta que esté listo login
            Stage stage = (Stage) btnPerfil.getScene().getWindow();
            stage.close();
        }
    }

}
