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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import javafx.scene.control.Alert.AlertType;




 
/**
 * SceneController-luokka hallitsee ohjelman käyttöliittymänäkymien siirtymistä sekä käyttöliittymäkomponenttien toimintaa.
 * Luokka on varsin iso. Nappuloille tarkoitetut metodit ollaan lajiteltu näkymittäin.
 * @author Villeveikko
 */
public class SceneController {
        
    private static final long serialVersionUID = 1L;
    private SHomeClient client;                                        
    
    
    
    /**
     * LOGIN-SCREEN 
     */
    
    @FXML private Text loginTextField;
    @FXML private Button loginbutton;
    @FXML private TextField username;
    @FXML private PasswordField password;
    
    /**
     * Käsittelee käyttäjän kirjautumisen järjestelmään, sekä lataa tälle tarkoitetun käyttöliittymänäkymän.
     * @param event
     * @throws IOException 
     */
    @FXML protected void loginButtonAction(ActionEvent event) throws IOException { 
        
        System.out.println("Kirjautumista yritettiin seuraavin tunnuksin: " + username.getCharacters().toString() + ", " + password.getCharacters().toString());
        
        client = new SHomeClient();
        if (client.login(username.getCharacters().toString(), password.getCharacters().toString())) {
            System.out.println("Käyttäjätunnukset täsmäävät, kirjaudutaan sisään...");
            
            String view;
            String viewPath;
        try{ 
 
            view = client.getUser(username.getCharacters().toString(), password.getCharacters().toString()).getView();
            viewPath = "fxml/" + client.getUser(username.getCharacters().toString(), password.getCharacters().toString()).getView();
            
            System.out.println("Tiedosto: " + viewPath);
            
            File file = new File("src\\shome\\fxml\\" + view);

            if (!file.exists()) {
               System.out.println("Tiedostoa ei löydy laitteesta, haetaan palvelimelta...");
               receiveFile(view);
            }
      
            Stage stage;
            Parent root = FXMLLoader.load(getClass().getResource(viewPath));
        
            stage=(Stage) loginbutton.getScene().getWindow();
        
            Scene scene = new Scene(root, 600, 550);
        
            stage.setTitle("sHome - Welcome");
            stage.setScene(scene);
            stage.show();
            
            System.out.println("Kirjautuminen onnistui!");
        } catch (IOException e) {
            e.printStackTrace();
        }
          
        }
        else {
            loginTextField.setText("Username or password incorrect.");
        }
    }
    
    
    /**
     * ADMIN VIEW
     */
    
    @FXML private Button createUserButton;
    @FXML private Button deleteUserButton;
    @FXML private Button changePasswordButton;
    @FXML private Button devicesButton;
    @FXML private Button logOutButton;
    
    @FXML protected void devicesButtonAction(ActionEvent event) throws Exception {
        Stage stage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/AdminDevices.fxml"));
        
        stage=(Stage) devicesButton.getScene().getWindow();
        
        Scene scene = new Scene(root, 600, 550);
        
        stage.setTitle("sHome - Control Devices");            
        stage.setScene(scene);    
        stage.show();
    }
    
    @FXML protected void createUserButtonAction(ActionEvent event) throws Exception {
        Stage stage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/CreateUserView.fxml"));
            
        stage=(Stage) createUserButton.getScene().getWindow();
        
        Scene scene = new Scene(root, 600, 550);
        
        stage.setTitle("sHome - Create a User");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML protected void logOutButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));
        
        stage=(Stage) logOutButton.getScene().getWindow();
        
        Scene scene = new Scene(root, 300, 275);
        
        stage.setTitle("sHome login");
        stage.setScene(scene);
        stage.show();
    }
    
    
    /**
     * CREATE NEW USER VIEW
     */
    
    @FXML private TextField newUsername;
    @FXML private PasswordField newPassword;
    
    @FXML private RadioButton homeLights;
    @FXML private RadioButton homeDoors;
    @FXML private RadioButton homeTv;
    @FXML private RadioButton homeStereo;
    @FXML private RadioButton homeHeat;
    @FXML private RadioButton homeHumidity;
    @FXML private RadioButton cottageLights;
    @FXML private RadioButton cottageDoors;
    @FXML private RadioButton cottageTv;
    @FXML private RadioButton cottageStereo;
    @FXML private RadioButton cottageHeat;
    @FXML private RadioButton cottageHumidity;
    
    @FXML private Button createNewUser;
    @FXML private Button goBackUser;
    
    @FXML protected void createNewUserButtonAction(ActionEvent event) throws Exception {
        
        String newUsernameString = newUsername.getCharacters().toString();
        String newPasswordString = newPassword.getCharacters().toString();
        
        newUsernameString = newUsernameString.replaceAll(" ", "");
        
        client = new SHomeClient();
        
        if (newUsernameString.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("You can't leave the username field empty!");
            alert.showAndWait();
            return;
        }
        
        if(client.doesUserExist(newUsernameString)) {  
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("A user with the same username already exists!");
            alert.showAndWait();
            return;
        }
        
        
        Boolean[] truthTable;
        truthTable = new Boolean[12];
        truthTable[0] = homeLights.isSelected(); 
        truthTable[1] = homeDoors.isSelected();
        truthTable[2] = homeTv.isSelected();
        truthTable[3] = homeStereo.isSelected();
        truthTable[4] = homeHeat.isSelected();
        truthTable[5] = homeHumidity.isSelected();
        truthTable[6] = cottageLights.isSelected();
        truthTable[7] = cottageDoors.isSelected();
        truthTable[8] = cottageTv.isSelected();
        truthTable[9] = cottageStereo.isSelected();
        truthTable[10] = cottageHeat.isSelected();
        truthTable[11] = cottageHumidity.isSelected();
        
        String content = buildFxml(truthTable);
        
        client.createUser(newUsernameString, newPasswordString, content);
                
        if(client.doesUserExist(newUsernameString)) {
            newUsername.clear();
            newPassword.clear();
            
            homeLights.setSelected(false);
            homeDoors.setSelected(false);
            homeTv.setSelected(false);
            homeStereo.setSelected(false);
            homeHeat.setSelected(false);
            homeHumidity.setSelected(false);
            cottageLights.setSelected(false);
            cottageDoors.setSelected(false);
            cottageTv.setSelected(false);
            cottageStereo.setSelected(false);
            cottageHeat.setSelected(false);
            cottageHumidity.setSelected(false);
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("Creating the user was successful!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("Something went wrong, and the user couldn't be created!");
            alert.showAndWait();
        }
        
    }
    
    @FXML protected void goBackUserButtonAction(ActionEvent event) throws Exception {
        Stage stage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/AdminView.fxml"));
        
        stage=(Stage) goBackUser.getScene().getWindow();
        
        Scene scene = new Scene(root, 600, 550);
        
        stage.setTitle("sHome - Welcome");            
        stage.setScene(scene);    
        stage.show();
    }
    
    /**
     * DEVICE CONTROL VIEW
     */
    
    @FXML private ToggleButton homeEntryLights;
    @FXML private ToggleButton homeLivingLights;
    @FXML private ToggleButton homeKitchenLights;
    @FXML private ToggleButton homeBedroomLights;
    @FXML private ToggleButton homeGarageLights;
    @FXML private ToggleButton homeEntryDoor;
    @FXML private ToggleButton homeGarageDoor;
    @FXML private ToggleButton homeLivingTv;
    @FXML private ToggleButton homeLivingStereo;
    @FXML private ToggleButton homeKitchenStereo;
    @FXML private ToggleButton cottageEntryLights;
    @FXML private ToggleButton cottageLivingLights;
    @FXML private ToggleButton cottageKitchenLights;
    @FXML private ToggleButton cottageBedroomLights;
    @FXML private ToggleButton cottageEntryDoor;
    @FXML private ToggleButton cottageLivingTv;
    @FXML private ToggleButton cottageKitchenTv;
    @FXML private ToggleButton cottageBedroomStereo;
    
    @FXML private Button homeSetTemperature;
    @FXML private Button homeSetHumidity;
    @FXML private Button cottageSetTemperature;
    @FXML private Button cottageSetHumidity;
    @FXML private Button logOutButtonDevices;
    @FXML private Button goBackDevices;
    
    @FXML Text homeFeedback;
    @FXML Text cottageFeedback;
    
    @FXML protected void light1ButtonAction(ActionEvent event) throws Exception {

        try{
            client = new SHomeClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        client.lightSwitch("light1");
        if (client.getLightState("light1")){
            System.out.println("Wow! The hallway lights are now ON!");
            homeFeedback.setText("The hallway lights are now ON!");
        } else {
            System.out.println("Wow! The hallway lights are now OFF!");
            homeFeedback.setText("The hallway lights are now OFF!");
        }    
    }
    @FXML protected void light2ButtonAction(ActionEvent event) throws Exception {

        try{
            client = new SHomeClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        client.lightSwitch("light2");
        if (client.getLightState("light2")){
            System.out.println("Wow! The living room lights are now ON!");
            homeFeedback.setText("The living room lights are now ON!");
        } else {
            System.out.println("Wow! The living room lights are now OFF!");
            homeFeedback.setText("The living room lights are now OFF!");
        }    
    }
    @FXML protected void light3ButtonAction(ActionEvent event) throws Exception {

        try{
            client = new SHomeClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        client.lightSwitch("light3");
        if (client.getLightState("light3")){
            System.out.println("Wow! The kitchen lights are now ON!");
            homeFeedback.setText("The kitchen lights are now ON!");
        } else {
            System.out.println("Wow! The kitchen lights are now OFF!");
            homeFeedback.setText("The kitchen lights are now OFF!");
        }    
    }
    @FXML protected void light4ButtonAction(ActionEvent event) throws Exception {

        try{
            client = new SHomeClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        client.lightSwitch("light4");
        if (client.getLightState("light4")){
            System.out.println("Wow! The bedroom lights are now ON!");
            homeFeedback.setText("The bedroom lights are now ON!");
        } else {
            System.out.println("Wow! The bedroom lights are now OFF!");
            homeFeedback.setText("The bedroom lights are now OFF!");
        }    
    }
    @FXML protected void light5ButtonAction(ActionEvent event) throws Exception {

        try{
            client = new SHomeClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        client.lightSwitch("light5");
        if (client.getLightState("light5")){
            System.out.println("Wow! The garage lights are now ON!");
            homeFeedback.setText("The garage lights are now ON!");
        } else {
            System.out.println("Wow! The garage lights are now OFF!");
            homeFeedback.setText("The garage lights are now OFF!");
        }    
    }
    
    @FXML protected void light6ButtonAction(ActionEvent event) throws Exception {

        try{
            client = new SHomeClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        client.lightSwitch("light6");
        if (client.getLightState("light6")){
            System.out.println("Wow! The hallway lights are now ON!");
            cottageFeedback.setText("The hallway lights are now ON!");
        } else {
            System.out.println("Wow! The hallway lights are now OFF!");
            cottageFeedback.setText("The hallway lights are now OFF!");
        }    
    }
    @FXML protected void light7ButtonAction(ActionEvent event) throws Exception {

        try{
            client = new SHomeClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        client.lightSwitch("light7");
        if (client.getLightState("light7")){
            System.out.println("Wow! The living room lights are now ON!");
            cottageFeedback.setText("The living room lights are now ON!");
        } else {
            System.out.println("Wow! The living room lights are now OFF!");
            cottageFeedback.setText("The living room lights are now OFF!");
        }    
    }
    @FXML protected void light8ButtonAction(ActionEvent event) throws Exception {

        try{
            client = new SHomeClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        client.lightSwitch("light8");
        if (client.getLightState("light8")){
            System.out.println("Wow! The kitchen lights are now ON!");
            cottageFeedback.setText("The kitchen lights are now ON!");
        } else {
            System.out.println("Wow! The kitchen lights are now OFF!");
            cottageFeedback.setText("The kitchen lights are now OFF!");
        }    
    }
    @FXML protected void light9ButtonAction(ActionEvent event) throws Exception {

        try{
            client = new SHomeClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        client.lightSwitch("light9");
        if (client.getLightState("light9")){
            System.out.println("Wow! The bedroom lights are now ON!");
            cottageFeedback.setText("The bedroom lights are now ON!");
        } else {
            System.out.println("Wow! The bedroom lights are now OFF!");
            cottageFeedback.setText("The bedroom lights are now OFF!");
        }    
    }
    
    
    
    @FXML protected void doorButtonAction(ActionEvent event) throws Exception {
        try{
            client = new SHomeClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
            client.lightSwitch("door1");
            if (client.getDoorState("door1")){
                System.out.println("Wow! Door 1 is now LOCKED!");
            } else {
                System.out.println("Wow! Door 1 is now UNLOCKED");
            }
    }
    
    @FXML protected void tvButtonAction(ActionEvent event) throws Exception {
        //TODO
    }
    
    @FXML protected void stereoButtonAction(ActionEvent event) throws Exception {
        //TODO
    }
    
    @FXML protected void temperatureButtonAction(ActionEvent event) throws Exception {
        //TODO
    }
    
    @FXML protected void humidityButtonAction(ActionEvent event) throws Exception {
        //TODO
    }
    
    @FXML protected void logOutButtonDevicesAction(ActionEvent event) throws Exception {
        Stage stage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));
        
        stage=(Stage) logOutButtonDevices.getScene().getWindow();
        
        Scene scene = new Scene(root, 300, 275);
        
        stage.setTitle("sHome login");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML protected void goBackDevicesButtonAction(ActionEvent event) throws Exception {
        Stage stage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/AdminView.fxml"));
        
        stage=(Stage) goBackDevices.getScene().getWindow();
        
        Scene scene = new Scene(root, 600, 550);
        
        stage.setTitle("sHome - Welcome");            
        stage.setScene(scene);    
        stage.show();
    }
    
    /**
     * APUMETODIT 
     */
    
    /**
     * Lähettää palvelimelle pyydön tiedoston lähettämisen, sitten vastaanottaa kyseisen tiedoston ja tallentaa sen src/shome/fxml/ -kansioon. 
     * Käytä metodia ainoastaan FXML-tyyppisten tiedostojen hakemiseen!
     * @param filename vastaanotettavan tiedoston nimi
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
     * Metodi rakentaa jokaiselle käyttäjälle oman näkymän, riippuen ylläpitäjän tekemistä rajoituksista.
     * @param truthTable määrittää elementit, jotka sisällytetään metodin palautukseen.
     * @return String-olio, jonka voi kirjoittaa suoraan fxml-tyyppiseen tiedostoon.
     */
    public String buildFxml(Boolean[] truthTable) {
        String content;
        String home = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"\n" +
"<?import javafx.scene.text.*?>\n" +
"<?import java.lang.*?>\n" +
"<?import javafx.scene.control.*?>\n" +
"<?import javafx.scene.layout.*?>\n" +
"\n" +
"<SplitPane dividerPositions=\"0.5\" maxHeight=\"-Infinity\" maxWidth=\"-Infinity\" minHeight=\"-Infinity\" minWidth=\"-Infinity\" orientation=\"VERTICAL\" prefHeight=\"400.0\" prefWidth=\"600.0\" xmlns:fx=\"http://javafx.com/fxml/1\" xmlns=\"http://javafx.com/javafx/8\" fx:controller=\"shome.SceneController\">\n" +
"  <items>\n" +
"    <AnchorPane minHeight=\"0.0\" minWidth=\"0.0\" prefHeight=\"100.0\" prefWidth=\"160.0\">\n" +
"         <children>\n" +
"            <Text layoutX=\"274.0\" layoutY=\"22.0\" strokeType=\"OUTSIDE\" strokeWidth=\"0.0\" text=\"Home\">\n" +
"               <font>\n" +
"                  <Font size=\"19.0\" />\n" +
"               </font>\n" +
"            </Text>\n" +
"            <Text layoutX=\"14.0\" layoutY=\"50.0\" strokeType=\"OUTSIDE\" strokeWidth=\"0.0\" text=\"Entry hallway\" />\n" +
"            <Text layoutX=\"140.0\" layoutY=\"50.0\" strokeType=\"OUTSIDE\" strokeWidth=\"0.0\" text=\"Living room\" />\n" +
"            <Text layoutX=\"279.0\" layoutY=\"50.0\" strokeType=\"OUTSIDE\" strokeWidth=\"0.0\" text=\"Kitchen\" />\n" +
"            <Text layoutX=\"402.0\" layoutY=\"50.0\" strokeType=\"OUTSIDE\" strokeWidth=\"0.0\" text=\"Bedroom\" />\n" +
"            <Text layoutX=\"528.0\" layoutY=\"50.0\" strokeType=\"OUTSIDE\" strokeWidth=\"0.0\" text=\"Garage\" />\n";
        
        String homeLights = "<ToggleButton fx:id=\"homeEntryLights\" layoutX=\"25.0\" layoutY=\"65.0\" mnemonicParsing=\"false\" onAction=\"#light1ButtonAction\" text=\"Lights\" />\n" +
"            <ToggleButton fx:id=\"homeLivingLights\" layoutX=\"147.0\" layoutY=\"65.0\" mnemonicParsing=\"false\" onAction=\"#light2ButtonAction\" text=\"Lights\" />\n" +
"            <ToggleButton fx:id=\"homeKitchenLights\" layoutX=\"275.0\" layoutY=\"65.0\" mnemonicParsing=\"false\" onAction=\"#light3ButtonAction\" text=\"Lights\" />\n" +
"            <ToggleButton fx:id=\"homeBedroomLights\" layoutX=\"402.0\" layoutY=\"65.0\" mnemonicParsing=\"false\" onAction=\"#light4ButtonAction\" text=\"Lights\" />\n" +
"            <ToggleButton fx:id=\"homeGarageLights\" layoutX=\"523.0\" layoutY=\"65.0\" mnemonicParsing=\"false\" onAction=\"#light5ButtonAction\" text=\"Lights\" />\n";
        
        String homeDoors = "<ToggleButton fx:id=\"homeEntryDoor\" layoutX=\"14.0\" layoutY=\"98.0\" mnemonicParsing=\"false\" onAction=\"#doorButtonAction\" text=\"Door Lock\" />\n" +
"            <ToggleButton fx:id=\"homeGarageDoor\" layoutX=\"511.0\" layoutY=\"98.0\" mnemonicParsing=\"false\" onAction=\"#doorButtonAction\" text=\"Door Lock\" />\n";
        
        String homeTv = "<ToggleButton fx:id=\"homeLivingTv\" layoutX=\"156.0\" layoutY=\"98.0\" mnemonicParsing=\"false\" onAction=\"#tvButtonAction\" text=\"TV\" />\n";
        
        String homeStereo = "<ToggleButton fx:id=\"homeLivingStereo\" layoutX=\"146.0\" layoutY=\"131.0\" mnemonicParsing=\"false\" onAction=\"#stereoButtonAction\" text=\"Stereo\" />\n" +
"            <ToggleButton fx:id=\"homeKitchenStereo\" layoutX=\"273.0\" layoutY=\"98.0\" mnemonicParsing=\"false\" onAction=\"#stereoButtonAction\" text=\"Stereo\" />\n";
        
        String homeHeat = "<Button fx:id=\"homeSetTemperature\" layoutX=\"14.0\" layoutY=\"173.0\" mnemonicParsing=\"false\" onAction=\"#temperatureButtonAction\" text=\"Apartment temperature\">\n" +
"               <font>\n" +
"                  <Font size=\"9.0\" />\n" +
"               </font>\n" +
"            </Button>\n";
        
        String homeHumidity = "<Button fx:id=\"homeSetHumidity\" layoutX=\"490.0\" layoutY=\"173.0\" mnemonicParsing=\"false\" onAction=\"#humidityButtonAction\" text=\"Apartment humidity\">\n" +
"               <font>\n" +
"                  <Font size=\"9.0\" />\n" +
"               </font>\n" +
"            </Button>\n";
        
        String cottage = "<Text fx:id=\"homeFeedback\" layoutX=\"140.0\" layoutY=\"177.0\" strokeType=\"OUTSIDE\" strokeWidth=\"0.0\" wrappingWidth=\"319.0\" />\n" +
"         </children></AnchorPane>\n" +
"    <AnchorPane minHeight=\"0.0\" minWidth=\"0.0\" prefHeight=\"100.0\" prefWidth=\"160.0\">\n" +
"         <children>\n" +
"            <Text layoutX=\"228.0\" layoutY=\"22.0\" strokeType=\"OUTSIDE\" strokeWidth=\"0.0\" text=\"Summer Cottage\">\n" +
"               <font>\n" +
"                  <Font size=\"19.0\" />\n" +
"               </font>\n" +
"            </Text>\n" +
"            <Text layoutX=\"47.0\" layoutY=\"49.0\" strokeType=\"OUTSIDE\" strokeWidth=\"0.0\" text=\"Entry hallway\" />\n" +
"            <Text layoutX=\"197.0\" layoutY=\"49.0\" strokeType=\"OUTSIDE\" strokeWidth=\"0.0\" text=\"Living room\" />\n" +
"            <Text layoutX=\"363.0\" layoutY=\"49.0\" strokeType=\"OUTSIDE\" strokeWidth=\"0.0\" text=\"Kitchen\" />\n" +
"            <Text layoutX=\"497.0\" layoutY=\"49.0\" strokeType=\"OUTSIDE\" strokeWidth=\"0.0\" text=\"Bedroom\" />\n";
        
        String cottageLights = "<ToggleButton fx:id=\"cottageEntryLights\" layoutX=\"58.0\" layoutY=\"66.0\" mnemonicParsing=\"false\" onAction=\"#light6ButtonAction\" text=\"Lights\" />\n" +
"            <ToggleButton fx:id=\"cottageLivingLights\" layoutX=\"204.0\" layoutY=\"66.0\" mnemonicParsing=\"false\" onAction=\"#light7ButtonAction\" text=\"Lights\" />\n" +
"            <ToggleButton fx:id=\"cottageKitchenLights\" layoutX=\"359.0\" layoutY=\"66.0\" mnemonicParsing=\"false\" onAction=\"#light8ButtonAction\" text=\"Lights\" />\n" +
"            <ToggleButton fx:id=\"cottageBedroomLights\" layoutX=\"497.0\" layoutY=\"66.0\" mnemonicParsing=\"false\" onAction=\"#light9ButtonAction\" text=\"Lights\" />\n";
        
        String cottageDoors = "<ToggleButton fx:id=\"cottageEntryDoor\" layoutX=\"46.0\" layoutY=\"99.0\" mnemonicParsing=\"false\" onAction=\"#doorButtonAction\" text=\"Door Lock\" />\n";
        
        String cottageTv = "<ToggleButton fx:id=\"cottageLivingTv\" layoutX=\"213.0\" layoutY=\"99.0\" mnemonicParsing=\"false\" onAction=\"#tvButtonAction\" text=\"TV\" />\n" +
"            <ToggleButton fx:id=\"cottageKitchenTv\" layoutX=\"368.0\" layoutY=\"99.0\" mnemonicParsing=\"false\" onAction=\"#tvButtonAction\" text=\"TV\" />\n";
        
        String cottageStereo = "<ToggleButton fx:id=\"cottageBedroomStereo\" layoutX=\"496.0\" layoutY=\"99.0\" mnemonicParsing=\"false\" onAction=\"#stereoButtonAction\" text=\"Stereo\" />\n";
        
        String cottageHeat = "<Button fx:id=\"cottageSetTemperature\" layoutX=\"14.0\" layoutY=\"173.0\" mnemonicParsing=\"false\" onAction=\"#temperatureButtonAction\" text=\"Apartment temperature\">\n" +
"               <font>\n" +
"                  <Font size=\"9.0\" />\n" +
"               </font>\n" +
"            </Button>\n";
        
        String cottageHumidity = "<Button fx:id=\"cottageSetHumidity\" layoutX=\"492.0\" layoutY=\"173.0\" mnemonicParsing=\"false\" onAction=\"#humidityButtonAction\" text=\"Apartment humidity\">\n" +
"               <font>\n" +
"                  <Font size=\"9.0\" />\n" +
"               </font>\n" +
"            </Button>\n";
        
        String end ="<Text fx:id=\"cottageFeedback\" layoutX=\"139.0\" layoutY=\"158.0\" strokeType=\"OUTSIDE\" strokeWidth=\"0.0\" wrappingWidth=\"319.0\" />\n" +
"            <Button fx:id=\"logOutButtonDevices\" layoutX=\"277.0\" layoutY=\"173.0\" mnemonicParsing=\"false\" onAction=\"#logOutButtonDevicesAction\" text=\"Log out\">\n" +
"               <font>\n" +
"                  <Font size=\"9.0\" />\n" +
"               </font>\n" +
"            </Button>\n" +
"         </children></AnchorPane>\n" +
"  </items>\n" +
"</SplitPane>\n";
        
        content = home;
        if (truthTable[0]) {
            content = content + homeLights;
        }
        if (truthTable[1]) {
            content = content + homeDoors;
        }
        if (truthTable[2]) {
            content = content + homeTv;
        }
        if (truthTable[3]) {
            content = content + homeStereo;
        }
        if (truthTable[4]) {
            content = content + homeHeat;
        }
        if (truthTable[5]) {
            content = content + homeHumidity;
        }
        
        content = content + cottage;
        
        if (truthTable[6]) {
            content = content + cottageLights;
        }
        if (truthTable[7]) {
            content = content + cottageDoors;
        }
        if (truthTable[8]) {
            content = content + cottageTv;
        }
        if (truthTable[9]) {
            content = content + cottageStereo;
        }
        if (truthTable[10]) {
            content = content + cottageHeat;
        }
        if (truthTable[11]) {
            content = content + cottageHumidity;
        }
        
        content = content + end;
        
        return content;
    }
    
    
    /**
     * Päivittää ikkunan sekunnin välein
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