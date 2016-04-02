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
import javafx.scene.control.Button;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Arrays;


 
/**
 * SceneController-luokka hallitsee ohjelman käyttöliittymänäkymien siirtymistä sekä käyttöliittymäkomponenttien toimintaa.
 * Luokka on varsin iso. Nappuloille tarkoitetut metodit ollaan lajiteltu näkymittäin.
 * @author Villeveikko
 */
public class SceneController {
        
    private static final long serialVersionUID = 1L;
    private SHomeClient client;
    
    public final static int SOCKET_PORT = 13267;      // you may change this
    public final static String SERVER = "127.0.0.1";  // localhost
    public final static int FILE_SIZE = 6022386; // file size temporary hard coded, should be bigger than the file to be downloaded
                                                
    
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
    @FXML protected void loginButtonAction(ActionEvent event) throws IOException { 
        
        System.out.println("Seuraavat arvot saatiin syötteenä: " + username.getCharacters().toString() + ", " + password.getCharacters().toString());
        
        client = new SHomeClient();
        if (client.login(username.getCharacters().toString(), password.getCharacters().toString())) {
            String view;
            String viewPath;
        try{ 
 
            view = client.getUser(username.getCharacters().toString(), password.getCharacters().toString()).getView();
            viewPath = "fxml/" + client.getUser(username.getCharacters().toString(), password.getCharacters().toString()).getView();
            
            File file = new File("src\\shome\\fxml\\" + view);

            if (!file.exists()) {
               receiveFile(view);
            }
      
            Stage stage;
            Parent root = FXMLLoader.load(getClass().getResource(viewPath));
        
            stage=(Stage) loginbutton.getScene().getWindow();
        
            Scene scene = new Scene(root, 600, 550);
        
            stage.setTitle("sHome - Welcome");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
          
        }
        else {
            actiontarget.setText("Username or password incorrect.");
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
        receiveFile("testi.fxml");
        /*try{
            
            client = new SHomeClient();
            client.lightSwitch("light1");
            if (client.getLightState("light1")){
                System.out.println("Wow! The light is now ON!");
            } else {
                System.out.println("Wow! The light is now OFF!");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }*/
            
    }
    
    /**
     * APUMETODIT 
     */
    
    
    public void receiveFile(String filename) {
        
        try {
           client = new SHomeClient();    
           client.startSendFile(filename); // Orders the server to send the file. 
        } catch (Exception e) { }
        
        System.out.println("Yritetään siirtää tiedosto " + filename + " palvelimelta asiakkaalle...");
        
        byte[] aByte = new byte[1];
        int bytesRead;

        Socket clientSocket = null;
        InputStream is = null;

        try {
            clientSocket = new Socket("127.0.0.1" , 3248);
            is = clientSocket.getInputStream();
        } catch (IOException ex) {
            // Do exception handling
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if (is != null) {

            FileOutputStream fos = null;
            BufferedOutputStream bos = null;
            try {
                fos = new FileOutputStream("src\\shome\\fxml\\" + filename);
                bos = new BufferedOutputStream(fos);
                bytesRead = is.read(aByte, 0, aByte.length);
                
                System.out.println("Receiving bytes..."); // Varmistus

                do {
                        baos.write(aByte);
                        bytesRead = is.read(aByte);                       
                } while (bytesRead != -1);

                System.out.println("Finished receiving bytes!"); // Varmistus
                
                bos.write(baos.toByteArray());
                bos.flush();
                bos.close();
                clientSocket.close();
                System.out.println("Tiedoston vastaanottaminen ja kirjoitus onnistui!");
            } catch (IOException ex) {
                // Do exception handling
                System.out.println("Jokin meni pieleen...");
            }
            
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