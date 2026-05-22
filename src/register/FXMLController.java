/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package register;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author fabio
 */
public class FXMLController implements Initializable {

    @FXML
    private ImageView login_foto;
    @FXML
    private TextField nickname_register;
    @FXML
    private Button avatar;
    @FXML
    private TextField correo_register;
    @FXML
    private PasswordField contraseña_register;
    @FXML
    private PasswordField repcontraseña_register;
    @FXML
    private TextField fecha_register;
    @FXML
    private CheckBox condicionesyterminos;
    @FXML
    private Button crear_register;
    @FXML
    private Button volver_register;
    @FXML
    private Text error_register;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Image imagenLocal = new Image(getClass().getResourceAsStream("/resources/logorun.png"));
        login_foto.setImage(imagenLocal);
    }    

    @FXML
    private void VerificarCrearCuenta(ActionEvent event) {
        String nick = nickname_register.getText();
        String correo = correo_register.getText();
        String contra = contraseña_register.getText();
        String repcontra = repcontraseña_register.getText();
        String fecha = fecha_register.getText();
        
        String regexContraseña = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._\\-\\/])[A-Za-z\\d@$!%*?&._\\-\\/]{8,20}$";
        String regexGmail = "^[A-Za-z0-9._%+-]+@gmail\\.com$";
        
        if (contra.isEmpty() || !contra.matches(regexContraseña)) {
            contraseña_register.setText("La contraseña no es lo suficientemente segura.");
            contraseña_register.setStyle("-fx-border-color: red; -fx-fill: red");
        }
        
        if (!repcontra.equals(contra)){
            repcontraseña_register.setText("Las contraseñas no coinciden");
            repcontraseña_register.setStyle("-fx-border-color: red; -fx-fill: red");
        }
        
       
        if (!condicionesyterminos.isSelected()){
            error_register.setText("Debes aceptar los terminos y condiciones.");
        }
        if (!correo.matches(regexGmail)){
            correo_register.setText("Debes poner un dominio válido (@gmail.com)");
            correo_register.setStyle("-fx-border-color: red; -fx-fill: red");
        }
        
        if (nick.isEmpty()){
            nickname_register.setText("Introduzca un Nickname");
            nickname_register.setStyle("-fx-border-color: red; -fx-fill: red");
        }
        LocalDate fechaNacimiento = null;
        DateTimeFormatter fechaDMA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        try {
            fechaNacimiento = LocalDate.parse(fecha, fechaDMA);
            long edad = ChronoUnit.YEARS.between(fechaNacimiento, LocalDate.now());
            
            if (edad < 12) {
                fecha_register.setText("Debes ser mayor de 12 años para poder registrarte.");
                fecha_register.setStyle("-fx-border-color: red; -fx-fill: red");
            }
            
        } catch (Exception e) {
            // Si el formato no es xx/xx/xxxx o meten números imposibles (ej: 35/14/2020), saltará aquí
            fecha_register.setText("Formato de la fecha invalid, prueba xx/xx/xxxx");
            fecha_register.setStyle("-fx-border-color: red; -fx-fill: red");
        }
        
        
    }

    @FXML
    private void AñadirAvatar(ActionEvent event) {
    }

    @FXML
    private void OnActionVolver(ActionEvent event) {
    }
    
}
