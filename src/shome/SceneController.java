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
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;




 
/**
 * SceneController-luokka hallitsee ohjelman käyttöliittymänäkymien siirtymistä sekä käyttöliittymäkomponenttien toimintaa.
 * Luokka on varsin iso. Käyttöliittymäkomponentit ja niille tarkoitetut metodit ollaan lajiteltu näkymittäin.
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
 
            view = client.getUser(username.getCharacters().toString()).getView();
            viewPath = "fxml/" + client.getUser(username.getCharacters().toString()).getView();
            
            System.out.println("Tiedosto: " + viewPath);
            
            File file = new File("shome\\fxml\\" + view);

            if (!file.exists()) {
               System.out.println("Tiedostoa ei löydy laitteesta, haetaan palvelimelta...");
               receiveFile(view);
               try{
               Thread.sleep(3000);
               } catch (InterruptedException e) {}
            } else {
                System.out.println("Tiedosto löytyi!");
            }
            
            Stage primaryStage;
            Parent root = FXMLLoader.load(getClass().getResource(viewPath));
        
            primaryStage=(Stage) loginbutton.getScene().getWindow();
        
            Scene scene = new Scene(root, 600, 550);
        
            primaryStage.setTitle("sHome - Welcome");
            primaryStage.setScene(scene);
            primaryStage.show();
                        
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
    
    /**
     * Siirtää käyttäjän näkymään, jossa tämä voi hallita älykodin laitteita.
     * @param event
     * @throws Exception 
     */
    @FXML protected void devicesButtonAction(ActionEvent event) throws Exception {
        Stage stage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/AdminDevices.fxml"));
        
        stage=(Stage) devicesButton.getScene().getWindow();
        
        Scene scene = new Scene(root, 600, 550);
        
        stage.setTitle("sHome - Control Devices");            
        stage.setScene(scene);    
        stage.show();
    }
    
    
    /**
     * Siirtää käyttäjän näkymään, jossa tämä voi luoda uusia käyttäjiä.
     * @param event
     * @throws Exception 
     */
    @FXML protected void createUserButtonAction(ActionEvent event) throws Exception {
        Stage stage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/CreateUserView.fxml"));
            
        stage=(Stage) createUserButton.getScene().getWindow();
        
        Scene scene = new Scene(root, 600, 550);
        
        stage.setTitle("sHome - Create a User");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Kirjaa käyttäjän ulos ja palaa takaisin kirjautumisnäkymään.
     * @param event
     * @throws Exception 
     */
    @FXML protected void logOutButtonAction(ActionEvent event) throws Exception {
        Stage stage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));
        
        stage=(Stage) logOutButton.getScene().getWindow();
        
        Scene scene = new Scene(root, 300, 275);
        
        stage.setTitle("sHome login");
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Siirtää käyttäjän näkymään, jossa tämä voi poistaa muita käyttäjiä.
     * @param event
     * @throws Exception 
     */
    @FXML protected void deleteUserButtonAction(ActionEvent event) throws Exception {
        Stage stage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/DeleteUser.fxml"));
            
        stage=(Stage) deleteUserButton.getScene().getWindow();
        
        Scene scene = new Scene(root, 600, 550);
        
        stage.setTitle("sHome - Delete a User");
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Siirtää käyttäjän näkymään, jossa tämä voi vaihtaa käyttäjien salasanoja.
     * @param event
     * @throws Exception 
     */
    @FXML protected void changePasswordButtonAction(ActionEvent event) throws Exception {
        Stage stage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/ChangePassword.fxml"));
            
        stage=(Stage) changePasswordButton.getScene().getWindow();
        
        Scene scene = new Scene(root, 600, 550);
        
        stage.setTitle("sHome - Delete a User");
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
    
    /**
     * Metodi käsittelee luotavalle käyttäjälle asetetut määritykset ja luo näiden pohjalta uuden käyttäjän.
     * Metodi estää nimettömien ja samannimisten käyttäjien luonnin. 
     * @param event
     * @throws Exception 
     */
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
            
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("");
            alert.setHeaderText("User creation successful");
            alert.setContentText("The program needs to be restarted in order for the new user to work on this machine. Do you want to do so now or later?");

            ButtonType buttonTypeOne = new ButtonType("Shut down and restart manually");
            ButtonType buttonTypeCancel = new ButtonType("Later", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne){
                System.exit(0);
            } 
            
        }
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("Something went wrong, and the user couldn't be created!");
            alert.showAndWait();
        }
        
    }
    
    /**
     * Metodi palauttaa käyttäjän takaisin ylläpitäjän päänäkymään.
     * @param event
     * @throws Exception 
     */
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
     * DELETE USER VIEW
     */
    
    @FXML private ChoiceBox userlistDelete;
    @FXML private Button deleteButton;
    @FXML private Button goBackDeleteButton;
    
    /**
     * Metodi kysyy käyttäjältä varmistuksen toisen käyttäjän poistosta.
     * Jos käyttäjä varmistaa poiston, metodi poistaa ko. käyttäjän.
     * @param event
     * @throws Exception 
     */
    @FXML protected void deleteButtonAction(ActionEvent event) throws Exception {
        String victim = (String)userlistDelete.getValue();
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("");
            alert.setHeaderText("Confirm user deletion");
            alert.setContentText("Are you sure you want to delete user " + victim);

            ButtonType buttonTypeOne = new ButtonType("Delete user");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne){
                client.deleteUser(client.getUser(victim));
                System.out.println("User " + victim + " has been deleted. Good riddance!");
            } 
        
    }
    
    /**
     * Metodi palauttaa käyttäjän takaisin ylläpitäjän päänäkymään.
     * @param event
     * @throws Exception 
     */
    @FXML protected void goBackDeleteButtonAction(ActionEvent event) throws Exception {
        Stage stage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/AdminView.fxml"));
        
        stage=(Stage) goBackDeleteButton.getScene().getWindow();
        
        Scene scene = new Scene(root, 600, 550);
        
        stage.setTitle("sHome - Welcome");            
        stage.setScene(scene);    
        stage.show();
    }
    
    /**
     * Metodi päivittää olemassa olevat käyttäjät listaan.
     * @throws Exception 
     */
    @FXML protected void userlistDeleteAction() throws Exception {
        client = new SHomeClient();
        userlistDelete.setItems(FXCollections.observableArrayList(client.getUsers()));
    }
    
    
    
    /**
     * CHANGE PASSWORD VIEW
     */
    
    @FXML private ChoiceBox userListPassword;
    @FXML private Button submitPasswordChangeButton;
    @FXML private PasswordField passwordToCome;
    
    /**
     * Metodi päivittää olemassa olevat käyttäjät listaan.
     * @throws Exception 
     */
    @FXML public void userListPasswordAction() throws Exception {
        client = new SHomeClient();
        userListPassword.setItems(FXCollections.observableArrayList(client.getUsers()));
    }
    
    /**
     * Metodi vaihtaa valitun käyttäjän salasanan annettuun syötteeseen. Tämän jälkeen metodi palauttaa käyttäjän takaisin ylläpitäjän päänäkymään.
     * @param event
     * @throws Exception 
     */
    @FXML public void submitPasswordChangeButtonAction(ActionEvent event) throws Exception {
        String username = (String)userListPassword.getValue();
        client = new SHomeClient();
        client.changeUserPassword(client.getUser(username), passwordToCome.getCharacters().toString());
        
        Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("Success!");
            alert.setContentText("Changing password on account " + username + " successful!");
            alert.showAndWait();
            
        Stage stage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/AdminView.fxml"));
        
        stage=(Stage) submitPasswordChangeButton.getScene().getWindow();
        
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
    @FXML private Button refreshDevices;
    
    @FXML Text homeFeedback;
    @FXML Text cottageFeedback;
    
    /**
     * Metodi laittaa valon päälle tai pois päältä, riippuen valon tilasta palvelimella.
     * Metodi ilmoittaa laitteen muuttuneen tilan käyttäjälle tekstillä käyttöliittymässä.
     * @param event
     * @throws Exception 
     */
    @FXML protected void light1ButtonAction(ActionEvent event) throws Exception {
        try{
            client = new SHomeClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.lightSwitch("light1");
        if (client.getLightState("light1")){
            homeFeedback.setText("The hallway lights are now ON!");
        } else {
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
            homeFeedback.setText("The living room lights are now ON!");
        } else {
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
            homeFeedback.setText("The kitchen lights are now ON!");
        } else {
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
            homeFeedback.setText("The bedroom lights are now ON!");
        } else {
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
            homeFeedback.setText("The garage lights are now ON!");
        } else {
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
            cottageFeedback.setText("The hallway lights are now ON!");
        } else {
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
            cottageFeedback.setText("The living room lights are now ON!");
        } else {
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
            cottageFeedback.setText("The kitchen lights are now ON!");
        } else {
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
            cottageFeedback.setText("The bedroom lights are now ON!");
        } else {
            cottageFeedback.setText("The bedroom lights are now OFF!");
        }    
    }
    
    /**
     * Metodi laittaa auki tai lukkoon, riippuen oven tilasta palvelimella.
     * Metodi ilmoittaa laitteen muuttuneen tilan käyttäjälle tekstillä käyttöliittymässä.
     * @param event
     * @throws Exception 
     */
    @FXML protected void door1ButtonAction(ActionEvent event) throws Exception {
        try{
            client = new SHomeClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
            client.doorLockSwitch("door1");
            if (client.getDoorState("door1")){
                homeFeedback.setText("The entrance door to Home is now LOCKED!");
            } else {
                homeFeedback.setText("The entrance door to Home is now UNLOCKED");
            }
    }
    @FXML protected void door2ButtonAction(ActionEvent event) throws Exception {
        try{
            client = new SHomeClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
            client.doorLockSwitch("door2");
            if (client.getDoorState("door2")){
                homeFeedback.setText("The garage door to Home is now LOCKED!");
            } else {
                homeFeedback.setText("The garage door to Home is now UNLOCKED");
            }
    }
    @FXML protected void door3ButtonAction(ActionEvent event) throws Exception {
        try{
            client = new SHomeClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
            client.doorLockSwitch("door3");
            if (client.getDoorState("door3")){
                cottageFeedback.setText("The entrance door to Cottage is now LOCKED!");
            } else {
                cottageFeedback.setText("The entrance door to Cottage is now UNLOCKED");
            }
    }
    
    /**
     * Metodi laittaa TV:n päälle tai pois päältä, riippuen TV:n tilasta palvelimella.
     * Metodi ilmoittaa laitteen muuttuneen tilan käyttäjälle tekstillä käyttöliittymässä.
     * @param event
     * @throws Exception 
     */
    @FXML protected void tv1ButtonAction(ActionEvent event) throws Exception {
        try{
            client = new SHomeClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
            client.tvSwitch("tv1");
            if (client.getTvState("tv1")){
                homeFeedback.setText("The living room TV at Home is now ON!");
            } else {
                homeFeedback.setText("The living room TV at Home is now OFF!");
            }
    }
    @FXML protected void tv2ButtonAction(ActionEvent event) throws Exception {
        try{
            client = new SHomeClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
            client.tvSwitch("tv2");
            if (client.getTvState("tv2")){
                cottageFeedback.setText("The living room TV at Cottage is now ON!");
            } else {
                cottageFeedback.setText("The living room TV at Cottage is now OFF!");
            }
    }
    @FXML protected void tv3ButtonAction(ActionEvent event) throws Exception {
        try{
            client = new SHomeClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
            client.tvSwitch("tv3");
            if (client.getTvState("tv3")){
                cottageFeedback.setText("The kitchen TV at Cottage is now ON!");
            } else {
                cottageFeedback.setText("The kitchen TV at Cottage is now OFF!");
            }
    }
    
    /**
     * Metodi laittaa stereon päälle tai pois päältä, riippuen stereon tilasta palvelimella.
     * Metodi ilmoittaa laitteen muuttuneen tilan käyttäjälle tekstillä käyttöliittymässä.
     * @param event
     * @throws Exception 
     */
    @FXML protected void stereo1ButtonAction(ActionEvent event) throws Exception {
        try{
            client = new SHomeClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
            client.stereoSwitch("stereo1");
            if (client.getStereoState("stereo1")){
                homeFeedback.setText("The living room stereo system at Home is now ON!");
            } else {
                homeFeedback.setText("The living room stereo system at Home is now OFF!");
            }
    }
    @FXML protected void stereo2ButtonAction(ActionEvent event) throws Exception {
        try{
            client = new SHomeClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.stereoSwitch("stereo2");
        if (client.getStereoState("stereo2")){
            homeFeedback.setText("The kitchen stereo system at Home is now ON!");
        } else {
            homeFeedback.setText("The kitchen stereo system at Home is now OFF!");
        }
    }
    @FXML protected void stereo3ButtonAction(ActionEvent event) throws Exception {
        try{
            client = new SHomeClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.stereoSwitch("stereo3");
        if (client.getStereoState("stereo3")){
            cottageFeedback.setText("The bedroom stereo system at Cottage is now ON!");
        } else {
            cottageFeedback.setText("The bedroom stereo system at Cottage is now OFF!");
        }
    }
    
    /**
     * Metodi asettaa asunnolle uuden huoneenlämpötilan tai kosteuden.
     * @param event
     * @throws Exception 
     */
    @FXML protected void homeSetTemperatureButtonAction(ActionEvent event) throws Exception {
        
        client = new SHomeClient();
                  
        Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("");
            alert.setHeaderText("Choose the temperature you want from these presets.");
            alert.setContentText("Current apartment temperature is: " + Double.toString(client.getTemperatureValue("home")) + "°C");

            ButtonType buttonTypeOne = new ButtonType("17.2°C");
            ButtonType buttonTypeTwo = new ButtonType("18.0°C");
            ButtonType buttonTypeThree = new ButtonType("19.5°C");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == buttonTypeOne){
                client.setTemperatureValue("home", 17.2);
                homeFeedback.setText("Temperature is now set to " + Double.toString(client.getTemperatureValue("home")) + "°C!");
            }
            if (result.get() == buttonTypeTwo){
                client.setTemperatureValue("home", 18.0);
                homeFeedback.setText("Temperature is now set to " + Double.toString(client.getTemperatureValue("home")) + "°C!");
            } 
            if (result.get() == buttonTypeThree){
                client.setTemperatureValue("home", 19.5);
                homeFeedback.setText("Temperature is now set to " + Double.toString(client.getTemperatureValue("home")) + "°C!");
            } 
    }
    @FXML protected void homeSetHumidityButtonAction(ActionEvent event) throws Exception {
        
        client = new SHomeClient();
                  
        Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("");
            alert.setHeaderText("Choose the humidity you want from these presets.");
            alert.setContentText("Current apartment humidity is: " + Double.toString(client.getHumidityValue("home")) + "%");

            ButtonType buttonTypeOne = new ButtonType("47.5%");
            ButtonType buttonTypeTwo = new ButtonType("50.0%");
            ButtonType buttonTypeThree = new ButtonType("55.0%");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == buttonTypeOne){
                client.setHumidityValue("home", 47.5);
                homeFeedback.setText("Humidity is now set to " + Double.toString(client.getHumidityValue("home")) + "%!");
            }
            if (result.get() == buttonTypeTwo){
                client.setHumidityValue("home", 50.0);
                homeFeedback.setText("Humidity is now set to " + Double.toString(client.getHumidityValue("home")) + "%!");
            } 
            if (result.get() == buttonTypeThree){
                client.setHumidityValue("home", 55.0);
                homeFeedback.setText("Humidity is now set to " + Double.toString(client.getHumidityValue("home")) + "%!");
            } 
    }
    @FXML protected void cottageSetTemperatureButtonAction(ActionEvent event) throws Exception {
        
        client = new SHomeClient();
                  
        Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("");
            alert.setHeaderText("Choose the temperature you want from these presets.");
            alert.setContentText("Current apartment temperature is: " + Double.toString(client.getTemperatureValue("cottage")) + "°C");

            ButtonType buttonTypeOne = new ButtonType("13.0°C");
            ButtonType buttonTypeTwo = new ButtonType("17.2°C");
            ButtonType buttonTypeThree = new ButtonType("18.0°C");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == buttonTypeOne){
                client.setTemperatureValue("cottage", 13.0);
                cottageFeedback.setText("Temperature is now set to " + Double.toString(client.getTemperatureValue("cottage")) + "°C!");
            }
            if (result.get() == buttonTypeTwo){
                client.setTemperatureValue("cottage", 17.2);
                cottageFeedback.setText("Temperature is now set to " + Double.toString(client.getTemperatureValue("cottage")) + "°C!");             } 
            if (result.get() == buttonTypeThree){
                client.setTemperatureValue("cottage", 18.0);
                cottageFeedback.setText("Temperature is now set to " + Double.toString(client.getTemperatureValue("cottage")) + "°C!");      
            } 
    }
    @FXML protected void cottageSetHumidityButtonAction(ActionEvent event) throws Exception {
        
        client = new SHomeClient();
                  
        Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("");
            alert.setHeaderText("Choose the humidity you want from these presets.");
            alert.setContentText("Current apartment humidity is: " + Double.toString(client.getHumidityValue("cottage")) + "%");

            ButtonType buttonTypeOne = new ButtonType("47.5%");
            ButtonType buttonTypeTwo = new ButtonType("50.0%");
            ButtonType buttonTypeThree = new ButtonType("55.0%");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == buttonTypeOne){
                client.setHumidityValue("cottage", 47.5);
                cottageFeedback.setText("Humidity is now set to " + Double.toString(client.getHumidityValue("cottage")) + "%!");
            }
            if (result.get() == buttonTypeTwo){
                client.setHumidityValue("cottage", 50.0);
                cottageFeedback.setText("Humidity is now set to " + Double.toString(client.getHumidityValue("cottage")) + "%!");
            } 
            if (result.get() == buttonTypeThree){
                client.setHumidityValue("cottage", 55.0);
                cottageFeedback.setText("Humidity is now set to " + Double.toString(client.getHumidityValue("cottage")) + "%!");
            } 
    }
    
    /**
     * Metodi kirjaa käyttäjän ulos ja palaa takaisin kirjautumisnäkymään.
     * @param event
     * @throws Exception 
     */
    @FXML protected void logOutButtonDevicesAction(ActionEvent event) throws Exception {
        Stage stage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));
        
        stage=(Stage) logOutButtonDevices.getScene().getWindow();
        
        Scene scene = new Scene(root, 300, 275);
        
        stage.setTitle("sHome login");
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Metodi palauttaa käyttäjän takaisin ylläpitäjän päänäkymään.
     * @param event
     * @throws Exception 
     */
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
     * Metodi kutsuu päivitysmetodin ohjelmaan.
     * @param Event
     * @throws Exception 
     */
    @FXML protected void refreshDevicesButtonAction(ActionEvent Event) throws Exception {
        updaterThread();
    }
    
    
    
    /**
     * APUMETODIT 
     */
    
    /**
     * Päivittää ikkunan sekunnin välein kutsumalla update-metodia.
     */
    public void updaterThread(){ 
        Thread t = new Thread(){
         public void run(){
          try {
     while(true){
      Thread.sleep(1000);
      update();
     }
    } catch (Exception e) {
     e.printStackTrace();
    }
         }
         };
        t.start();
    }
    
    /**
     * Metodi käy yksi kerrallaan laitteiden tilat läpi ja asettaa niiden näkymän käyttöliittymässä sen mukaisesti. 
     * @throws Exception 
     */
    public void update() throws Exception {
        client = new SHomeClient();
        
        homeEntryLights.setSelected(client.getLightState("light1"));
        homeLivingLights.setSelected(client.getLightState("light2"));
        homeKitchenLights.setSelected(client.getLightState("light3"));
        homeBedroomLights.setSelected(client.getLightState("light4"));
        homeGarageLights.setSelected(client.getLightState("light5"));
        cottageEntryLights.setSelected(client.getLightState("light6"));
        cottageLivingLights.setSelected(client.getLightState("light7"));
        cottageKitchenLights.setSelected(client.getLightState("light8"));
        cottageBedroomLights.setSelected(client.getLightState("light9"));
        
        homeEntryDoor.setSelected(client.getDoorState("door1"));
        homeGarageDoor.setSelected(client.getDoorState("door2"));
        cottageEntryDoor.setSelected(client.getDoorState("door3"));
        
        homeLivingTv.setSelected(client.getTvState("tv1"));
        cottageLivingTv.setSelected(client.getTvState("tv2"));
        cottageKitchenTv.setSelected(client.getTvState("tv3"));
        
        homeLivingStereo.setSelected(client.getStereoState("stereo1"));
        homeKitchenStereo.setSelected(client.getStereoState("stereo2"));
        cottageBedroomStereo.setSelected(client.getStereoState("stereo3"));
        
        
    }
    
    /**
     * Lähettää palvelimelle pyydön tiedoston lähettämisen, sitten vastaanottaa kyseisen tiedoston ja tallentaa sen src/shome/fxml/ -kansioon. 
     * Käytä metodia ainoastaan FXML-tyyppisten tiedostojen hakemiseen!
     * @param filename vastaanotettavan tiedoston nimi
     */
    public void receiveFile(String filename) {
        
        try {
           client = new SHomeClient();    
           client.startSendFile(filename); // Asks the server to send the file. 
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
                fos = new FileOutputStream("shome\\fxml\\" + filename);
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
"            <Text layoutX=\"528.0\" layoutY=\"50.0\" strokeType=\"OUTSIDE\" strokeWidth=\"0.0\" text=\"Garage\" />\n" +
"            <Button fx:id=\"refreshDevices\" layoutX=\"545.0\" layoutY=\"5.0\" mnemonicParsing=\"false\" onAction=\"#refreshDevicesButtonAction\" text=\"Refresh\">\n" +
"               <font>\n" +
"                  <Font size=\"9.0\" />\n" +
"               </font>\n" +
"            </Button>\n";
        
        String homeLights = "<ToggleButton fx:id=\"homeEntryLights\" layoutX=\"25.0\" layoutY=\"65.0\" mnemonicParsing=\"false\" onAction=\"#light1ButtonAction\" text=\"Lights\" />\n" +
"            <ToggleButton fx:id=\"homeLivingLights\" layoutX=\"147.0\" layoutY=\"65.0\" mnemonicParsing=\"false\" onAction=\"#light2ButtonAction\" text=\"Lights\" />\n" +
"            <ToggleButton fx:id=\"homeKitchenLights\" layoutX=\"275.0\" layoutY=\"65.0\" mnemonicParsing=\"false\" onAction=\"#light3ButtonAction\" text=\"Lights\" />\n" +
"            <ToggleButton fx:id=\"homeBedroomLights\" layoutX=\"402.0\" layoutY=\"65.0\" mnemonicParsing=\"false\" onAction=\"#light4ButtonAction\" text=\"Lights\" />\n" +
"            <ToggleButton fx:id=\"homeGarageLights\" layoutX=\"523.0\" layoutY=\"65.0\" mnemonicParsing=\"false\" onAction=\"#light5ButtonAction\" text=\"Lights\" />\n";
        
        String homeDoors = "<ToggleButton fx:id=\"homeEntryDoor\" layoutX=\"14.0\" layoutY=\"98.0\" mnemonicParsing=\"false\" onAction=\"#door1ButtonAction\" text=\"Door Lock\" />\n" +
"            <ToggleButton fx:id=\"homeGarageDoor\" layoutX=\"511.0\" layoutY=\"98.0\" mnemonicParsing=\"false\" onAction=\"#door2ButtonAction\" text=\"Door Lock\" />\n";
        
        String homeTv = "<ToggleButton fx:id=\"homeLivingTv\" layoutX=\"156.0\" layoutY=\"98.0\" mnemonicParsing=\"false\" onAction=\"#tv1ButtonAction\" text=\"TV\" />\n";
        
        String homeStereo = "<ToggleButton fx:id=\"homeLivingStereo\" layoutX=\"146.0\" layoutY=\"131.0\" mnemonicParsing=\"false\" onAction=\"#stereo1ButtonAction\" text=\"Stereo\" />\n" +
"            <ToggleButton fx:id=\"homeKitchenStereo\" layoutX=\"273.0\" layoutY=\"98.0\" mnemonicParsing=\"false\" onAction=\"#stereo2ButtonAction\" text=\"Stereo\" />\n";
        
        String homeHeat = "<Button fx:id=\"homeSetTemperature\" layoutX=\"14.0\" layoutY=\"173.0\" mnemonicParsing=\"false\" onAction=\"#homeSetTemperatureButtonAction\" text=\"Apartment temperature\">\n" +
"               <font>\n" +
"                  <Font size=\"9.0\" />\n" +
"               </font>\n" +
"            </Button>\n";
        
        String homeHumidity = "<Button fx:id=\"homeSetHumidity\" layoutX=\"490.0\" layoutY=\"173.0\" mnemonicParsing=\"false\" onAction=\"#homeSetHumidityButtonAction\" text=\"Apartment humidity\">\n" +
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
        
        String cottageDoors = "<ToggleButton fx:id=\"cottageEntryDoor\" layoutX=\"46.0\" layoutY=\"99.0\" mnemonicParsing=\"false\" onAction=\"#door3ButtonAction\" text=\"Door Lock\" />\n";
        
        String cottageTv = "<ToggleButton fx:id=\"cottageLivingTv\" layoutX=\"213.0\" layoutY=\"99.0\" mnemonicParsing=\"false\" onAction=\"#tv2ButtonAction\" text=\"TV\" />\n" +
"            <ToggleButton fx:id=\"cottageKitchenTv\" layoutX=\"368.0\" layoutY=\"99.0\" mnemonicParsing=\"false\" onAction=\"#tv3ButtonAction\" text=\"TV\" />\n";
        
        String cottageStereo = "<ToggleButton fx:id=\"cottageBedroomStereo\" layoutX=\"496.0\" layoutY=\"99.0\" mnemonicParsing=\"false\" onAction=\"#stereo3ButtonAction\" text=\"Stereo\" />\n";
        
        String cottageHeat = "<Button fx:id=\"cottageSetTemperature\" layoutX=\"14.0\" layoutY=\"173.0\" mnemonicParsing=\"false\" onAction=\"#cottageSetTemperatureButtonAction\" text=\"Apartment temperature\">\n" +
"               <font>\n" +
"                  <Font size=\"9.0\" />\n" +
"               </font>\n" +
"            </Button>\n";
        
        String cottageHumidity = "<Button fx:id=\"cottageSetHumidity\" layoutX=\"492.0\" layoutY=\"173.0\" mnemonicParsing=\"false\" onAction=\"#cottageSetHumidityButtonAction\" text=\"Apartment humidity\">\n" +
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

}