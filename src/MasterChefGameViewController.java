import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MasterChefGameViewController implements Initializable {

   private final static int TOMATO_RADIUS = 15;
	private final static int KNIFE_RADIUS = 20;	
	private final static int CHEF_RADIUS = 23;
   private Stage gameStage;
   private Scene gameScene;
   private Stage menuStage;

   @FXML private AnchorPane mainPane;
   @FXML private Label labelPoints;
   @FXML private ImageView newChefPlayer;
   @FXML private ImageView background;
   @FXML private ImageView background2;
   @FXML private ImageView thumbsUp;
   @FXML private ImageView thumbsDown;
   
   private ImageView smallChef;
   private ImageView[] tomatoes;
   private ImageView[] Knifes;
   private ImageView[] playersLife;
   
   private final static int BACKGROUND_WIDTH = 1000;
   private final static int BACKGROUND_HEIGHT = 562;
   
   private final static String TOMATO_IMAGE = "/pictures/tomato.png";
	private final static String KNIFE_IMAGE = "/pictures/knife.png";
   private final static String HAIM_LIFE = "/pictures/haimsmall.png";
   private final static String MICHAL_LIFE = "/pictures/michalsmall.png";
   private final static String EYAL_LIFE = "/pictures/eyallife.png";
   private final static String ISRAEL_LIFE = "/pictures/israelsmall.png";
   private final static String FONT_PATH = "src/resources/fonts/kenvector_future.ttf";

   private boolean isLeftKeyPressed;
   private boolean isRightKeyPressed;
 	private int angle;
   private int points;
   private int isHit;
   private int isMiss;
   private int playerLife;
   private int loopCount;
	
   private AnimationTimer gameTimer;
   private ParallelTransition parallelTransition;
   private Random randomPositionGenerator;
   
   @Override
   public void initialize(URL url, ResourceBundle rb) {
      initializeGame();
      initializeListenets();
      try {
         labelPoints.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),15));
      } catch (FileNotFoundException ex) {
         Logger.getLogger(MasterChefGameViewController.class.getName()).log(Level.SEVERE, null, ex);
      }
      isHit=0;
      isMiss=0;
      points=0;
      randomPositionGenerator = new Random();
      loopCount=0;
      moveBackground();
   }   

   public void moveBackground() {
  
   TranslateTransition translateTransition = new TranslateTransition(Duration.millis(10000), background);
   translateTransition.setFromX(0);
   translateTransition.setToX(-1 * BACKGROUND_WIDTH);
   translateTransition.setInterpolator(Interpolator.LINEAR);

   TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(10000), background2);
   translateTransition2.setFromX(0);
   translateTransition2.setToX(-1 * BACKGROUND_WIDTH);
   translateTransition2.setInterpolator(Interpolator.LINEAR);

   parallelTransition = new ParallelTransition( translateTransition, translateTransition2 );
   parallelTransition.setCycleCount(Animation.INDEFINITE);
   parallelTransition.play(); 
   }
   
   public void init(Stage menuStage,ImageView newPlayer){    
      newChefPlayer = new ImageView(newPlayer.getImage());
      newChefPlayer.setId(newPlayer.getId());
      newChefPlayer.setLayoutX(475);
      newChefPlayer.setLayoutY(410);
      this.menuStage = menuStage;
      menuStage.hide();
      mainPane.getChildren().add(newChefPlayer);
      createGameElements(newChefPlayer);
      createGameLoop();
      gameStage.show();      
   }

   private void initializeListenets() {
		
         
		gameScene.setOnKeyPressed((KeyEvent event) -> {
         if(event.getCode() == KeyCode.LEFT) {
            isLeftKeyPressed=true;
         }
         else if(event.getCode() == KeyCode.RIGHT) {
            isRightKeyPressed= true;
         }
      });
         
		gameScene.setOnKeyReleased((KeyEvent event) -> {
         if(event.getCode() == KeyCode.LEFT) {
            isLeftKeyPressed=false;
         }
         else if(event.getCode() == KeyCode.RIGHT) {
            isRightKeyPressed = false;
         }
      });
	} 
      
   private void moveChef() {
		
		if(!isRightKeyPressed && isLeftKeyPressed) {
			
			if(angle> -30)
				angle-=5;
			newChefPlayer.setRotate(angle);
			if(newChefPlayer.getLayoutX()>-20)
				newChefPlayer.setLayoutX(newChefPlayer.getLayoutX()-3);
		}
		
		if(isRightKeyPressed && !isLeftKeyPressed) {
			if(angle < 30)
				angle += 5;
			newChefPlayer.setRotate(angle);
			if(newChefPlayer.getLayoutX()< 920)
				newChefPlayer.setLayoutX(newChefPlayer.getLayoutX()+3);
		}
		
		if(!isRightKeyPressed && !isLeftKeyPressed) {
			if(angle<0)
				angle+=5;
			else if (angle>0)
				angle-=5;
			newChefPlayer.setRotate(angle);
		}
		if(isRightKeyPressed && isLeftKeyPressed) {	
			if(angle<0)
				angle+=5;
			else if (angle>0)
				angle-=5;
			newChefPlayer.setRotate(angle);
		}
	}
      
   private void createGameLoop() {		
         gameTimer = new AnimationTimer() {
            
			@Override
			public void handle(long now) {
				moveGameElements();
				checkIfElementsBehindTheShipAndRelocate();
				checkIfElementsCollied();
            if(isMiss == 1&&loopCount++>=45){
               isMiss=0;
               loopCount =0;
               thumbsDown.setVisible(false);
            }
            if(isHit ==1&&loopCount++>=45){
               isHit=0;
               loopCount=0;
               thumbsUp.setVisible((false));  
            }
				moveChef();
			}	
		};
		gameTimer.start();	
	}

	private void initializeGame() {
		gameScene = new Scene(mainPane,BACKGROUND_WIDTH,BACKGROUND_HEIGHT);
		gameStage = new Stage();
		gameStage.setScene(gameScene);
	}

  	private void createGameElements(ImageView choosenChef) {
		playerLife=2;
		playersLife=new ImageView[3];
      
		for(int i=0;i<playersLife.length;i++) {

			playersLife[i] = new ImageView((getPlayerLifeImage(choosenChef)).getImage());
			playersLife[i].setLayoutX(820+i*54);
			playersLife[i].setLayoutY(100);
			mainPane.getChildren().add(playersLife[i]);
		}
      
		tomatoes = new ImageView[7];
		Knifes = new ImageView[12];
		for(int i=0;i<Knifes.length;i++) {
			Knifes[i] = new ImageView(KNIFE_IMAGE);
			setNewElementsPosition(Knifes[i]);
			mainPane.getChildren().add(Knifes[i]);
		}
      
		for( int i=0; i<tomatoes.length ;i++) {
			tomatoes[i] = new ImageView(TOMATO_IMAGE);
			setNewElementsPosition(tomatoes[i]);
			mainPane.getChildren().add(tomatoes[i]);
		}
   }
   
   private ImageView getPlayerLifeImage(ImageView image){
      
      if("michal".equals(image.getId())) smallChef=new ImageView(MICHAL_LIFE);
      if("haim".equals(image.getId())) smallChef=new ImageView(HAIM_LIFE);
      if("israel".equals(image.getId())) smallChef=new ImageView(ISRAEL_LIFE);
      if("eyal".equals(image.getId())) smallChef=new ImageView(EYAL_LIFE);
      if(smallChef ==null) System.out.println("im here");
     return smallChef;
   }
   
   private void setNewElementsPosition(ImageView image) {
		
		image.setLayoutX(randomPositionGenerator.nextInt(950) );
		image.setLayoutY(-(randomPositionGenerator.nextInt(2400)+562));
	}
   
   private void moveGameElements() {
		
		for(int i=0;i<tomatoes.length;i++) {
			tomatoes[i].setLayoutY(tomatoes[i].getLayoutY()+7);
			tomatoes[i].setRotate(tomatoes[i].getRotate()+4);
		}
		
      for (ImageView Knife : Knifes) {
         Knife.setLayoutY(Knife.getLayoutY() + 7);
         Knife.setRotate(Knife.getRotate() + 4);
      }
	}
   
   private void checkIfElementsBehindTheShipAndRelocate() {

      for (ImageView tomatoe : tomatoes) {
         if (tomatoe.getLayoutY() > 563) {
            setNewElementsPosition(tomatoe);
         }
      }
      for (ImageView Knife : Knifes) {
         if (Knife.getLayoutY() > 563) {
            setNewElementsPosition(Knife);
         }
      }
	}

   private void removeLife() {
		
		mainPane.getChildren().remove(playersLife[playerLife]);
		playerLife--;
      thumbsDown.setVisible(true);
      isMiss = 1;
		if(playerLife<0) {
         gameStage.close();
         gameTimer.stop();    
         FXMLLoader loader = new FXMLLoader();
         try {
            loader.setLocation(getClass().getResource("AlertBoxFailed.fxml"));
            Parent root = loader.load();
            AlertBoxController controller = loader.getController();
            controller.init(menuStage);
         } catch (IOException ex) {
            Logger.getLogger(ChefPickerSubSceneController.class.getName()).log(Level.SEVERE, null, ex);
         }  
		}
	}
      
   private double calculateDistance(double x1,double x2, double y1,double y2 ) {
		
		return (Math.sqrt(Math.pow(x1-x2, 2)) + Math.pow(y1-y2, 2));
	}

	private void checkIfElementsCollied() {
		
		for (int i=0;i<tomatoes.length;i++) {
				if(TOMATO_RADIUS+CHEF_RADIUS>calculateDistance(newChefPlayer.getLayoutX()+49,tomatoes[i].getLayoutX()+20, newChefPlayer.getLayoutY()+37,tomatoes[i].getLayoutY()+20)) {
               points++;
               String textToSet = "POINTS: ";
               if(points<10) textToSet = textToSet+"0";
               labelPoints.setText(textToSet+points);
               thumbsUp.setVisible(true);
               isHit=1;
               if(points == 5) winnerIsFound();
               setNewElementsPosition(tomatoes[i]);
				}
			}
		for (int i=0;i<Knifes.length;i++) {	
			if(KNIFE_RADIUS+CHEF_RADIUS>calculateDistance(newChefPlayer.getLayoutX()+49,Knifes[i].getLayoutX()+20, newChefPlayer.getLayoutY()+37,Knifes[i].getLayoutY()+20)) {
				removeLife();
				setNewElementsPosition(Knifes[i]);
				}
			}	
	}
   
   private void winnerIsFound(){
            
      gameStage.close();
      gameTimer.stop();            
            FXMLLoader loader = new FXMLLoader();
      try {
         loader.setLocation(getClass().getResource("AlertBox.fxml"));
         Parent root = loader.load();
         AlertBoxController controller = loader.getController();
         controller.init(menuStage);
      } catch (IOException ex) {
         Logger.getLogger(ChefPickerSubSceneController.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
}