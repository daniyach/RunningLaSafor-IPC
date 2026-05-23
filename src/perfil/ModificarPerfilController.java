/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package perfil;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import upv.ipc.sportlib.SportActivityApp;
import upv.ipc.sportlib.User;

/**
 * FXML Controller class
 *
 * @author yach
 */
public class ModificarPerfilController implements Initializable {

    @FXML
    private Label lblNickname;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private DatePicker dpFechaNacimiento;
    @FXML
    private ImageView imgAvatar;
    @FXML
    private Button btnSeleccionarAvatar;
    @FXML
    private Label lblRutaAvatar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;

    private SportActivityApp app = SportActivityApp.getInstance();
    private User usuario = app.getCurrentUser();
    private String avatarPath = usuario.getAvatarPath();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        lblNickname.setText(usuario.getNickName());
        txtEmail.setText(usuario.getEmail());
        dpFechaNacimiento.setValue(usuario.getBirthDate());

        if (usuario.getAvatar() != null) {
            imgAvatar.setImage(usuario.getAvatar());
            lblRutaAvatar.setText(usuario.getAvatarPath());
        }
    }

    @FXML
    private void onSeleccionarAvatar(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");

        // restingir solo a imágenes por su extensión
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg")
        );

        File fichero = fileChooser.showOpenDialog(
                btnSeleccionarAvatar.getScene().getWindow()
        );

        if (fichero != null) {
            avatarPath = fichero.getAbsolutePath();
            lblRutaAvatar.setText(fichero.getName());
            imgAvatar.setImage(new Image(fichero.toURI().toString()));

        }

    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de validación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();

    }

    @FXML
    private void onCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onGuardar(ActionEvent event) {

        String email = txtEmail.getText();
        String password = txtPassword.getText();
        LocalDate fechaNacimiento = dpFechaNacimiento.getValue();

        // Validad email
        if (!User.checkEmail(email)) { // método estatico
            // mostrar por pantalla que no es valido el email
            mostrarError("El email no tiene un formato válido");
            return;
        }
        // Validar contraseña si no está vacio y cumple con las restricciones
        if (!password.isEmpty() && !User.checkPassword(password)) {
            mostrarError("La contraseña debe tener entre 8 y 20 caracteres, al menos una mayúscula, minúscula, dígito y símbolo.");
            return;
        }
        // validar fecha de nacimiento
        if (fechaNacimiento == null) {
            mostrarError("ebes seleccionar una fecha de nacimiento");
            return;
        }
        // validar mayor de 12
        if (!User.isOlderThan(fechaNacimiento, 12)) {
            mostrarError("Debes tener más de 12 años");
            return;
        }
        if (password.isEmpty()) {
            password = usuario.getPassword();
        }

        // después de verificar guardamos
        app.updateCurrentUser(email, password, fechaNacimiento, avatarPath);

        Stage stage = (Stage) btnGuardar.getScene().getWindow();
        stage.close();

        //System.out.println("Email: " + email);
        //System.out.println("Password: " + password);
        //System.out.println("Fecha: " + fechaNacimiento);
    }

}
