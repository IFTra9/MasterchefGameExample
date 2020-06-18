/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class HelpSubSceneController implements Initializable {

   @FXML private Text apples;
   @FXML private Text knife;
   @FXML private Text end;
   private static final String FONT_PATH = "src/resources/fonts/kenvector_future.ttf";

   @Override
   public void initialize(URL url, ResourceBundle rb) {
      try {
         apples.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),20));
         knife.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),20));
         end.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),20));

      } catch (FileNotFoundException ex) {
         Logger.getLogger(HelpSubSceneController.class.getName()).log(Level.SEVERE, null, ex);
      }

   }   
   
}
