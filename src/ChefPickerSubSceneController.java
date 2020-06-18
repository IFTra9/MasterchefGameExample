import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ChefPickerSubSceneController implements Initializable {

   @FXML private AnchorPane pane;
   @FXML private Button startGame;
   @FXML private Button chooseYourPlayer;
   @FXML private ToggleGroup choices;
   @FXML private ImageView haim;
   @FXML private ImageView eyal;
   @FXML private ImageView israel;
   @FXML private ImageView michal;
   @FXML private RadioButton michalRadio;
   @FXML private RadioButton haimRadio;
   @FXML private RadioButton israelRadio;
   @FXML private RadioButton eyalRadio;

   private ImageView player;
   private static final String FONT_PATH = "src/resources/fonts/kenvector_future.ttf";
   private RadioButton selectedRadioButton;
   
   @Override
   public void initialize(URL url, ResourceBundle rb) {
      try {
         startGame.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),15));
         chooseYourPlayer.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),25));
      } catch (FileNotFoundException ex) {
         Logger.getLogger(ChefPickerSubSceneController.class.getName()).log(Level.SEVERE, null, ex);
      }    
   }   
   
   @FXML private void handleStartButton(ActionEvent event){

      selectedRadioButton = (RadioButton) choices.getSelectedToggle();
      String playerName = selectedRadioButton.getId();
      if(playerName.equals(michalRadio.getId()))  player = michal;
      if(playerName.equals(haimRadio.getId()))  player = haim;
      if(playerName.equals(eyalRadio.getId()))  player = eyal;
      if(playerName.equals(israelRadio.getId()))  player = israel;
      startGame(event);
   }
   
   private void startGame(ActionEvent event){
      FXMLLoader loader = new FXMLLoader();
      try {
         loader.setLocation(getClass().getResource("MasterChefGameView.fxml"));
         Parent root = loader.load();
         MasterChefGameViewController controller = loader.getController();
         Stage gameWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
         controller.init(gameWindow,player);
      } catch (IOException ex) {
         Logger.getLogger(ChefPickerSubSceneController.class.getName()).log(Level.SEVERE, null, ex);
      }
   }   
}
