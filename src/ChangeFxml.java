
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author ifat
 */
public class ChangeFxml {

   private AnchorPane view;
   private String fileurl;
   
   
   public AnchorPane getPage(String fileName){
      try{
          URL fileUrl = testingapp.class.getResource(""+fileName+".fxml");

          if(fileUrl == null) System.out.println("  its null  ");

               view = FXMLLoader.load(fileUrl);
         
            } catch (IOException ex) {
                           System.out.println("no page"+fileName+" please check the name");      
            }
      return view;
   }
   
   public String getUrl(){
      
      return fileurl;
      
   }

}
