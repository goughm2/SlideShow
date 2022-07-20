SlideShow Project by Max Gough

A JavaFX desctop application was created that will display a SlideShow of images.

Run Information.
-----------------
The application uses JavaFX version 18.0.1 - I have not tested to see if it is functional on earlier versions.
As Visual Studio Code has been my editor of choice you must do the following to be able to run the application:
    - Install JavaFx.
    - Open launch.json file that is in .vscode folder.
    - Add "vmArgs": "--module-path /path/to/javafx-sdk-17.0.1/lib --add-modules javafx.controls,javafx.fxml"
    - Change --module-path  above to the path of your javaFX directory.
    - Add javaFX jar files to Referenced Libraries.
    - Main java classes are in /src.
    - To start Application call the main method in App.java.

Application Information
-----------------------
The user has control over many aspects of the slideshow such as:
    - Add 1..n images that the user wants to be in the slideshow animation.
    - Once happy with the images selected the start button can be clicked.
    - Even during the animation, the user can add more images. For these the changes to take effect, click
      the restart button.  
    - The restart button can be clicked anytime after the animation has begun.
    - If the user added an image they didn't want to, they have the option to clear their entire image selection.
    - This button will also terminate the slideshow if it is running.
    - The pause button will indefinitely freeze the image on screen until clicked again.
    
