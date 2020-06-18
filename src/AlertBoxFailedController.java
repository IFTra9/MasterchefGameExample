
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class AlertBoxFailedController implements Initializable {

   @FXML private AnchorPane mainPane;
   @FXML private Button closeButton;
   private Stage menuStage;
   private Stage alertStage;
   private Scene alertScene;
   
   @Override
   public void initialize(URL url, ResourceBundle rb) {
      createWindow();  
   }
      
   public void init(Stage menuStage){
      this.menuStage= menuStage;
    
   }
   public void createWindow(){
   
      alertScene = new Scene(mainPane);
		alertStage = new Stage();
      alertStage.setTitle("Looser");
		alertStage.setScene(alertScene);
      alertStage.show();
   }
   
    @FXML private void handleCloseButton (ActionEvent event){

      FXMLLoader loader = new FXMLLoader();
      try {
         loader.setLocation(getClass().getResource("testingapp.fxml"));
         Parent root = loader.load();
         testingappController controller = loader.getController();
         controller.init(alertStage,menuStage);
      } catch (IOException ex) {
         Logger.getLogger(ChefPickerSubSceneController.class.getName()).log(Level.SEVERE, null, ex);
      }          
   }  
}
