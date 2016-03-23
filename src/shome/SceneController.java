package shome;
 
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Button;
 
public class SceneController {
        
    @FXML private Text actiontarget;
    @FXML private Button loginbutton;
    
    @FXML protected void handleSubmitButtonAction(ActionEvent event) throws IOException { 
        if (true) {
        try{ 
        Stage stage;
        Parent root = FXMLLoader.load(getClass().getResource("Apartments.fxml"));
        
        stage=(Stage) loginbutton.getScene().getWindow();
        
        Scene scene = new Scene(root, 600, 550);
        
        stage.setTitle("sHome login");
        stage.setScene(scene);
        stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
          
        }
        else {
            actiontarget.setText("Something went wrong.");
        }
    }

}