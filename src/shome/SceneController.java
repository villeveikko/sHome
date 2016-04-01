package shome;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Button;


 
/**
 * SceneController-luokka hallitsee ohjelman käyttöliittymänäkymien siirtymistä sekä käyttöliittymäkomponenttien toimintaa.
 * Luokka on varsin iso. Nappuloille tarkoitetut metodit ollaan lajiteltu näkymittäin.
 * @author Villeveikko
 */
public class SceneController {
        
    private static final long serialVersionUID = 1L;
    private SHomeClient client;
    
    @FXML private Text actiontarget;
    @FXML private Button loginbutton;
    @FXML private TextField username;
    @FXML private PasswordField password;
    
    /**
     * LOGIN-SCREEN 
     */
    
    /**
     * Käsittelee käyttäjän kirjautumisen järjestelmään, sekä lataa tälle tarkoitetun käyttöliittymänäkymän.
     * @param event
     * @throws IOException 
     */
    @FXML protected void handleSubmitButtonAction(ActionEvent event) throws IOException { 
        
        System.out.println("Seuraavat arvot saatiin syötteenä: " + username.getCharacters().toString() + ", " + password.getCharacters().toString());
        
        client = new SHomeClient();
        if (client.login(username.getCharacters().toString(), password.getCharacters().toString())) {
            String view;
        try{ 
 
            view = client.getUser(username.getCharacters().toString(), password.getCharacters().toString()).getView();
      
            Stage stage;
            Parent root = FXMLLoader.load(getClass().getResource(view));
        
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
    
    
    /**
     * ADMIN VIEW
     */
    
    /**
     * Testimetodi.
     * @param event
     * @throws Exception 
     */
    @FXML protected void testButtonAction(ActionEvent event) throws Exception {
        try{
            
            client = new SHomeClient();
            client.lightSwitch("light1");
            if (client.getLightState("light1")){
                System.out.println("Wow! The light is now ON!");
            } else {
                System.out.println("Wow! The light is now OFF!");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
    
    
    /**
     * PÃ¤ivittÃ¤Ã¤ ikkunan sekunnin vÃ¤lein
     */
    public void updaterThread(){
        Thread t = new Thread(){
         public void run(){
          try {
     while(true){
      Thread.sleep(1000);
      update();
     }
    } catch (InterruptedException e) {
     e.printStackTrace();
    }
         }
         };
        t.start();
    }
    
    public void update() {
        
    }

}