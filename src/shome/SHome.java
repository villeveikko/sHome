
package shome;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Asiakaspuolen pääluokka ohjelmassa. 
 * @author Villeveikko
 */
public class SHome extends Application {
    
    /**
     * Alustaa ja lataa aloitusikkunan erillisestä tiedostosta.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));
        
        Scene scene = new Scene(root, 300, 275);
        
        stage.setTitle("sHome login");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
