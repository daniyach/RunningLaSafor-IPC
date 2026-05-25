/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import register.NickContraseña;
import upv.ipc.sportlib.SportActivityApp;

/**
 * FXML Controller class
 *
 * @author fabio
 */


public class FXMLController implements Initializable {

    @FXML
    private ImageView foto_iniciarsesion;
    @FXML
    private TextField nickname_login;
    @FXML
    private PasswordField contraseña_login;
    @FXML
    private Text crear_cuenta;
    @FXML
    private Button iniciar_login;

    /**
     * Initializes the controller class.
     */
     NickContraseña datos = NickContraseña.getGuardar();
        
        String nick = datos.getNickname();
        String contra = datos.getContraseña();
    @FXML
    private Text error_login;
            
  
        
        
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Image imagenLocal = new Image(getClass().getResourceAsStream("/resources/logorun.png"));
        foto_iniciarsesion.setImage(imagenLocal);
    }    
   

    @FXML
    private void clickCrear(MouseEvent event) throws IOException {
        SportActivityApp app = SportActivityApp.getInstance();

                
            Parent root = FXMLLoader.load(getClass().getResource("/register/FXML.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) iniciar_login.getScene().getWindow();
              
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
            stage.setTitle("Running la Safor - Folelé - IPC");
            stage.setScene(scene);
            stage.show();
    }

    @FXML
    private void iniciarCuenta(ActionEvent event) throws IOException {
       
        String nickLogin = nickname_login.getText().trim();
        String contraseñaLogin = contraseña_login.getText();
    
        
        nickname_login.setStyle("-fx-border-color: black");
        contraseña_login.setStyle("-fx-border-color: black");
        error_login.setText("");

    SportActivityApp app = SportActivityApp.getInstance();
    boolean correcto = false;
    
    try{
        correcto = app.login(nickLogin, contraseñaLogin); 
    } catch (Exception e) {
        correcto = false;
    }

    if (correcto) {
       
        Parent root = FXMLLoader.load(getClass().getResource("/inicio/Inicio.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) iniciar_login.getScene().getWindow();
          
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
        stage.setTitle("Running la Safor - Folelé - IPC");
        stage.setScene(scene);
        stage.show();
       
        } else {
            nickname_login.setStyle("-fx-border-color: red");
            contraseña_login.setStyle("-fx-border-color: red");
            error_login.setText("Contraseña o Usuario incorrectos.");
        }
        
    }
}



    

