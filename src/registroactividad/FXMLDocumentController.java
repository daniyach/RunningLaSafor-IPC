package registroactividad;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import upv.ipc.sportlib.Activity;
import upv.ipc.sportlib.SportActivityApp;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Button registrar_actividad;
    @FXML
    private ListView<Activity> lista_actividad;
    @FXML
    private Button cambiar_nombre;
    @FXML
    private Button borrar;
    @FXML
    private Button inicio;

    private ObservableList<Activity> datos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SportActivityApp app = SportActivityApp.getInstance();

        lista_actividad.setCellFactory(lv -> new ListCell<Activity>() {
            @Override
            protected void updateItem(Activity a, boolean empty) {
                super.updateItem(a, empty);
                if (empty || a == null) {
                    setText(null);
                } else {
                    String km = String.format("%.1f km", a.getTotalDistance() / 1000);
                    setText(a.getName() + " - " + km);
                }
            }
        });

        datos = FXCollections.observableArrayList(app.getUserActivities());
        lista_actividad.setItems(datos);

        lista_actividad.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    mostrarDetallesActividad(newValue);
                }
            }
        );
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
                    TextInputDialog dialog = new TextInputDialog(nuevaActividad.getName());
                    dialog.setTitle("Nombrar Actividad");
                    dialog.setHeaderText("Fichero cargado con éxito.");
                    dialog.setContentText("Introduce un nombre para tu entrenamiento:");

                    Optional<String> result = dialog.showAndWait();

                    result.ifPresent(nuevoNombre -> {
                        if (!nuevoNombre.trim().isEmpty()) {
                            nuevaActividad.setName(nuevoNombre);
                            app.renameActivity(nuevaActividad, nuevoNombre);
                        }
                    });

                    datos.add(0, nuevaActividad);
                    lista_actividad.getSelectionModel().select(nuevaActividad);

                } else {
                    mostrarAlertaError("El fichero no se ha podido procesar.");
                }
            } catch (Exception e) {
                mostrarAlertaError("Error crítico de sistema: " + e.getMessage());
            }
        }
    }

    @FXML
    private void cambioNombre(ActionEvent event) {
        Activity actividadSeleccionada = lista_actividad.getSelectionModel().getSelectedItem();

        if (actividadSeleccionada == null) {
            mostrarAlertaSeleccion();
            return;
        }

        TextInputDialog dialog = new TextInputDialog(actividadSeleccionada.getName());
        dialog.setTitle("Renombrar Actividad");
        dialog.setHeaderText("Modificar el nombre del entrenamiento");
        dialog.setContentText("Nuevo nombre:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(nuevoNombre -> {
            if (!nuevoNombre.trim().isEmpty() && !nuevoNombre.equals(actividadSeleccionada.getName())) {
                actividadSeleccionada.setName(nuevoNombre);
                SportActivityApp.getInstance().renameActivity(actividadSeleccionada, nuevoNombre);
                lista_actividad.refresh();
            }
        });
    }

    @FXML
    private void borrarActividad(ActionEvent event) {
        Activity actividadSeleccionada = lista_actividad.getSelectionModel().getSelectedItem();

        if (actividadSeleccionada == null) {
            mostrarAlertaSeleccion();
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar borrado");
        confirmacion.setHeaderText("Vas a borrar permanentemente: " + actividadSeleccionada.getName());
        confirmacion.setContentText("¿Estás seguro?");

        Optional<ButtonType> result = confirmacion.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            SportActivityApp.getInstance().removeActivity(actividadSeleccionada);
            datos.remove(actividadSeleccionada);
        }
    }

    private void mostrarAlertaSeleccion() {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Atención");
        alerta.setHeaderText("Ninguna actividad seleccionada");
        alerta.setContentText("Por favor, selecciona una actividad de la lista.");
        alerta.showAndWait();
    }

    private void mostrarAlertaError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Fallo en la operación");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarDetallesActividad(Activity actividad) {

    }

    @FXML
    private void irInicio(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/inicio/Inicio.fxml"));
        Stage stage = (Stage) inicio.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
};