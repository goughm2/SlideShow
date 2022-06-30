import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;



public class MainSceneController {

    //final HashMap<Image, String> imagesHM = new HashMap<Image, String>();

    // Two arraylists are used, poor space complexity and non-optimal solution
    final ArrayList<Image> imagesAL = new ArrayList<>();
    final ArrayList<String> imageName = new ArrayList<>();
    private int counter = 0;

    @FXML
    private ImageView ivImg;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnPause;
    @FXML
    private Button btnRestart;
    @FXML
    private Label curImg;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *  *** CALLED WHEN SINGLE IMAGE IS ADDED TO SLIDESHOW *** *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Opens finder (Mac OS)                                   * 
     * User has option to choose a JPEG image                  * 
     * Image is sent to addImgToMap()                          *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    @FXML
    private void addSinglePhoto(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select Single File");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg"));
        File file = fc.showOpenDialog(null);
        if(file != null){
            String path = file.getAbsolutePath();
            addImgToMap(new Image("file:" + file), path);
        }
    }

    @FXML
    private void addMultiplePhotos(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select Multiple Files");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg"));
        List<File> files = fc.showOpenMultipleDialog(null);
        if (files != null) {
            for (int i = 0; i < files.size(); i++) {
                String path = files.get(i).getAbsolutePath();
                addImgToMap(new Image("file:" + files.get(i)), path);
            }
        }   
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *                  *** CALLED WHEN USER CHOOSES TO START SLIDESHOW ***                        *           
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *                       
     * First if condition will prevent IndexOutOfBounds exception                                  *
     * Repeat function call (non-optimal) made to ensure the first image is set without delay      *
     * Image View value is set to first image stored in arraylist                                  *
     * Title is set to first string in 2nd arraylist                                               *   
     * Image number of x images is concatenated to the title label                                 *
     * Once counter reaches end, set back to 0 to ensure the above point is correct on each cycle  *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    @FXML
    private void startSlideShow() {
        ivImg.setCache(true);
        if(imagesAL.size() > 1 && imageName.size() > 1) {
            addImgToImageView(this.imagesAL.get(this.counter),
                              this.imageName.get(this.counter) + "  " + (this.counter + 1) + "/" + this.imageName.size());
            this.counter++;
        }
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            addImgToImageView(this.imagesAL.get(this.counter),
                              this.imageName.get(this.counter) + "  " + (this.counter + 1) + "/" + this.imageName.size());
            this.counter++;
            if (this.counter == this.imagesAL.size()){
                this.counter = 0;
            }
        }));
        
        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         *         *** USED TO TERMINATE SLIDE SHOW  ***         *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
         * Action called when "Clear Images" button is clicked   *
         * Stops timeline animation                              *
         * Clears imageview pane                                 *
         * Clears image ArrayList                                *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        btnClear.setOnAction(e -> {
            timeline.stop();        
            ivImg.setImage(null);  
            curImg.setText(null); 
            this.imagesAL.clear();    
            this.imageName.clear();
        });

        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         *         *** USED TO PAUSE OR RESUME SLIDESHOW ANIMATION *** *           *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *     
         * Action called when "Pause SlideShow" button is clicked                  * 
         * If animation status is on "PAUSED" the animation will play and continue *
         * If not the animation will pause                                         * 
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        btnPause.setOnAction(e -> {
            if(timeline.getStatus() == Status.PAUSED) {
                timeline.play();
                System.out.println("RESUME");
            } else {
                timeline.pause();     
                System.out.println("PAUSED"); 
            }  
        });

        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         *                  *** USED TO RESTART SLIDESHOW ANIMATION *** *                  *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *    
         * Action called when "Restart SlideShow" button is clicked                        * 
         * Image view and title are set to the value stored in index 0 of their array list *
         * If not the animation will pause                                                 * 
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        btnRestart.setOnAction(e -> {
            //timeline.playFromStart();
            ivImg.setImage(this.imagesAL.get(0));
            curImg.setText(this.imageName.get(0)+ "  " + 1 + "/" + this.imageName.size());
            this.counter = 1;

        });
        timeline.setCycleCount(Timeline.INDEFINITE);    // Animation will run on an infinite loop
        timeline.play();    // Call to start animation
    }

    private void addImgToMap(Image image, String path) {
        this.imagesAL.add(image);
        String fileName = removePath(path);
        this.imageName.add(fileName);
    }

    private void addImgToImageView(Image img, String title) {
        ivImg.setImage(img);
        curImg.setText(title);
    }

    public static String removePath(String filePath) {
        int position = filePath.lastIndexOf(File.separator);
        if(position > -1)
           return filePath.substring(position + 1);
        else
           return filePath;
     }
}
