/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package register;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import upv.ipc.sportlib.SportActivityApp;

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
    private void VerificarCrearCuenta(ActionEvent event) throws IOException {
        String nick = nickname_register.getText();
        String correo = correo_register.getText();
        String contra = contraseña_register.getText();
        String repcontra = repcontraseña_register.getText();
        String fecha = fecha_register.getText(); 
        boolean error = false;
        
        
        nickname_register.setPromptText("UsuarioIPC");
        nickname_register.setStyle("-fx-border-color: black");
        correo_register.setPromptText("UsuarioIPC@gmail.com");
        correo_register.setStyle("-fx-border-color: black");
        contraseña_register.setPromptText("Contraseña1@A");
        contraseña_register.setStyle("-fx-border-color: black");
        repcontraseña_register.setPromptText("Contraseña1@A");
        repcontraseña_register.setStyle("-fx-border-color: black");
        fecha_register.setPromptText("XX / XX / XXXX");
        fecha_register.setStyle("-fx-border-color: black");
        error_register.setText("");
        
        
       

        String regexContraseña = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._\\-\\/])[A-Za-z\\d@$!%*?&._\\-\\/]{8,20}$";
        String regexGmail = "^[A-Za-z0-9._%+-]+@gmail\\.com$";
        
        if (contra.isEmpty() || !contra.matches(regexContraseña)) {
            contraseña_register.setPromptText("La contraseña no es lo suficientemente segura.");
            contraseña_register.setStyle("-fx-border-color: red");
            error = true;
        } 
        
        if (!repcontra.equals(contra)){
            repcontraseña_register.setPromptText("Las contraseñas no coinciden");
            repcontraseña_register.setStyle("-fx-border-color: red");
            error = true;
        }
        
       
        if (!condicionesyterminos.isSelected()){
            error_register.setText("Debes aceptar los terminos y condiciones.");
            error = true;
        }
        if (!correo.matches(regexGmail)){
            correo_register.setPromptText("Debes poner un dominio válido (@gmail.com)");
            correo_register.setStyle("-fx-border-color: red");
            error = true;
        }
        
        if (nick.isEmpty()){
            nickname_register.setPromptText("Introduzca un Nickname");
            nickname_register.setStyle("-fx-border-color: red");
            error = true;
        }
        LocalDate fechaNacimiento = null;
        DateTimeFormatter fechaDMA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (!fecha.isEmpty()){
            try {
                fechaNacimiento = LocalDate.parse(fecha, fechaDMA);
                long edad = ChronoUnit.YEARS.between(fechaNacimiento, LocalDate.now());
            
            if (edad < 12) {
                fecha_register.setPromptText("Debes ser mayor de 12 años para poder registrarte.");
                fecha_register.setStyle("-fx-border-color: red");
                error = true;
            }
            
            } catch (Exception e) {
            fecha_register.setPromptText("Formato de la fecha invalid, prueba xx/xx/xxxx");
            fecha_register.setStyle("-fx-border-color: red");
            error = true;
            }
        }else{
            fecha_register.setPromptText("Introduzca una fecha.");
        }
        
        if (!error){
            NickContraseña datos = NickContraseña.getGuardar();
            datos.setNickname(nick);
            datos.setContraseña(contra);
            
            Parent root = FXMLLoader.load(getClass().getResource("/login/FXML.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) crear_register.getScene().getWindow();
              
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
            stage.setTitle("Running la Safor - Folelé - IPC");
            stage.setScene(scene);
            stage.show();
        }
            
            
    }

    @FXML
    private void AñadirAvatar(ActionEvent event) {
    FileChooser selectorArchivos = new FileChooser();
    // Crear un filtro para mostrar SOLAMENTE imágenes
    FileChooser.ExtensionFilter filtroImagenes = new FileChooser.ExtensionFilter( "Archivos de Imagen (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
    selectorArchivos.getExtensionFilters().add(filtroImagenes);

    // Obtener la ventana actual para bloquearla de fondo mientras el explorador está abierto
    Stage ventanaActual = (Stage) avatar.getScene().getWindow();

    // Abrir la ventana del explorador de archivos
    File archivoSeleccionado = selectorArchivos.showOpenDialog(ventanaActual);

    // Procesar el archivo si el usuario ha seleccionado uno
        if (archivoSeleccionado != null) {
        // Guardamos la ruta absoluta de la imagen elegida
        String rutaImagen = archivoSeleccionado.getAbsolutePath();
        System.out.println("El usuario ha seleccionado la foto en: " + rutaImagen);
        }
    }
    @FXML
    private void OnActionVolver(ActionEvent event) throws IOException {
        nickname_register.setPromptText("UsuarioIPC");
        nickname_register.setStyle("-fx-border-color: black");
        correo_register.setPromptText("UsuarioIPC@gmail.com");
        correo_register.setStyle("-fx-border-color: black");
        contraseña_register.setPromptText("Contraseña1@A");
        contraseña_register.setStyle("-fx-border-color: black");
        repcontraseña_register.setPromptText("Contraseña1@A");
        repcontraseña_register.setStyle("-fx-border-color: black");
        fecha_register.setPromptText("XX / XX / XXXX");
        fecha_register.setStyle("-fx-border-color: black");
        error_register.setText("");
        
        SportActivityApp app = SportActivityApp.getInstance();

                
            Parent root = FXMLLoader.load(getClass().getResource("/login/FXML.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) volver_register.getScene().getWindow();
              
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
            stage.setTitle("Running la Safor - Folelé - IPC");
            stage.setScene(scene);
            stage.show();
    }
    
}


