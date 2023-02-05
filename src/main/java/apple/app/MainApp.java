package apple.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Represents an Apple App.
 */
public class MainApp extends Application {

    Stage stage;
    Scene scene;

    /** The root container for the application scene graph. */
    VBox root;

    /**
     * Constructs a {@code MainApp} object.
     */
    public MainApp() {
        this.stage = null;
        this.scene = null;

        // initializing the root of the scene graph
        this.root = new VBox();
    } // MainApp

    /** {@inheritDoc} */
    @Override
    public void init() {
        System.out.println("init() called");
    } // init

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        this.stage = stage;

        // Creating each object of root's child nodes
        // ImageLoader imgLoader = new ImageLoader();
        // TextLoader txtLoader = new TextLoader();
        // BarLoader barLoader = new BarLoader();

        // Downloaded images in ButtonLoader class will be placed in ImageViews of ImageLoader class
//        ButtonLoader btnLoader = new ButtonLoader(imgLoader, txtLoader, barLoader);

        // Adding four child nodes to its parent root VBox
//        root.getChildren().addAll(btnLoader, txtLoader, imgLoader, barLoader);

        // Setting the vbox to be the root of the scene
        this.scene = new Scene(this.root);

        // Setting up the stage and setting it to be visible
        this.stage.setOnCloseRequest(event -> Platform.exit());
        this.stage.setTitle("Hello User");
        this.stage.setScene(this.scene);
        this.stage.sizeToScene();
        this.stage.show();
        Platform.runLater(() -> this.stage.setResizable(false));
    } // start

    /** {@inheritDoc} */
    @Override
    public void stop() {
        System.out.println("stop() called");
    } // stop

} // MainApp
