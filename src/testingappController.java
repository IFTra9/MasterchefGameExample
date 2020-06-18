

import javafx.fxml.FXML;
import javafx.scene.text.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author ifat
 */
public class testingappController {
   
   private static final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/pictures/yellow_buttonPressed.png');";
	private static final String BUTTON_FREE_STYLE =  "-fx-background-color: transparent; -fx-background-image: url('/pictures/yellow_button.png');";
   private static final String FONT_PATH = "src/resources/fonts/kenvector_future.ttf";
   
   @FXML private Button helpButton;
   @FXML private Button scoresButton;
   @FXML private Button playButton;
   @FXML private Button exitButton;

   @FXML private AnchorPane mainPane;
   @FXML private AnchorPane helpPane;
   @FXML private AnchorPane scoresPane;
   @FXML private AnchorPane chefPickerPane;
   
      
	private AnchorPane sceneToHide;
   private Button b;
   
   private boolean isHidden;
   private String winner;
   private Scene menuScene;
   private Stage menuStage;
   private Stage prevWindow;
   
   
   public void initialize() {
          setLabelFont();
          createButtons();
          isHidden = true;
	
   }   
  
   private void setLabelFont() {
      try {
         exitButton.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),23));
         playButton.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),23));
         scoresButton.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),23));
         helpButton.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),23));  
      } catch (FileNotFoundException ex) {
      }
    }
    
   private void initializeListeners(Button b) {
      
         b.setOnMousePressed((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY))
               setButtonPressedStyle(b);
         });	
   b.setOnMouseReleased((MouseEvent event) -> {
      if(event.getButton().equals(MouseButton.PRIMARY))
         setButtonReleasedStyle(b);
         });	
				
		b.setOnMouseEntered((MouseEvent event) -> {
         b.setEffect(new DropShadow());
         } //when a button was entered there will be a dropshadow effect
         );
		
		b.setOnMouseExited((MouseEvent event) -> {
         b.setEffect(null);
         });
	}
     
   private void setButtonPressedStyle(Button b) {
		
		b.setStyle(BUTTON_PRESSED_STYLE);
		b.setPrefHeight(45);
		b.setLayoutY(b.getLayoutY()+4);
	}
//there is a difference between the images by 4 pixels 
	private void setButtonReleasedStyle(Button b) {
			
			b.setStyle(BUTTON_FREE_STYLE);
			b.setPrefHeight(49);
			b.setLayoutY(b.getLayoutY()-4);
		}
	
   private void createButtons(){  
   
      initializeListeners(playButton);
      initializeListeners(helpButton);
      initializeListeners(scoresButton);
      initializeListeners(exitButton);     
}

   @FXML private void handleHelpButton(){
   
   if(helpPane!=null) mainPane.getChildren().remove(helpPane);
   ChangeFxml object = new ChangeFxml();
   helpPane = object.getPage("helpSubScene");
   helpPane.setLayoutX(1030);
   helpPane.setLayoutY(100);
   showSubScene(helpPane);
   mainPane.getChildren().add(helpPane);
}

   private void movesubScene(AnchorPane hide) {
		TranslateTransition transition = new TranslateTransition(); 
		transition.setDuration(Duration.seconds(0.4));
		transition.setNode(hide);
		
		if(isHidden) {
		transition.setToX(-660);
		isHidden=false;
		} 
		else {
			transition.setToX(0);
			isHidden= true;
		}
		transition.play();

	}

	private void showSubScene(AnchorPane sub) {
      

		if(sceneToHide != null&&!sub.equals(sceneToHide))movesubScene(sceneToHide);
		movesubScene(sub);
		sceneToHide = sub;
	}

   @FXML private void handlePlayButton(ActionEvent event){
   
   if(chefPickerPane!=null) mainPane.getChildren().remove(chefPickerPane);
   ChangeFxml object = new ChangeFxml();
   
   chefPickerPane = object.getPage("ChefPickerSubScene");
         
   chefPickerPane.setLayoutX(1030);
   chefPickerPane.setLayoutY(100);
   showSubScene(chefPickerPane); 
   mainPane.getChildren().add(chefPickerPane);
   //startGame();
}

   @FXML private void handleScoresButton(){
   
   if(scoresPane!=null) mainPane.getChildren().remove(scoresPane);
   ChangeFxml object = new ChangeFxml();
   scoresPane = object.getPage("scoresSubScene");
   scoresPane.setLayoutX(1030);
   scoresPane.setLayoutY(100);
   showSubScene(scoresPane);
   mainPane.getChildren().add(scoresPane);
}
     
   @FXML private void handleExitButton(ActionEvent event){
      
         Stage gameWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
         gameWindow.close();
   }
   
public void init(Stage prevWindow,Stage menuStage){
   
   this.menuStage = menuStage;
   Scene scene = new Scene(mainPane);
   menuStage.setScene(scene);
   this.prevWindow= prevWindow;
   prevWindow.hide();
   menuStage.show();
   
}

}