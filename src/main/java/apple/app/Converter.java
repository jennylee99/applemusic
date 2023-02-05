package apple.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.http.HttpClient;

/**
 * My Converter converts Apple Music to Spotify Links.
 */
public class Converter extends Application {

    /**
     * HTTP client.
     */
    public HttpClient httpclient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)           // uses HTTP protocol version 2 where possible
        .followRedirects(HttpClient.Redirect.NORMAL)  // always redirects, except from HTTPS to HTTP
        .build();                                     // builds and returns a HttpClient object

    /**
     * Google {@code Gson} object for parsing JSON-formatted strings.
     */
    public Gson gson = new GsonBuilder()
        .setPrettyPrinting()                          // enable nice output when printing
        .create();                                    // builds and returns a Gson object

    private Stage stage;
    private Scene scene;
    private final HBox root;

    Label searchLabel;
    TextField searchbar;
    ChoiceBox streamingService;
    Button convert;
    AnchorPane anchor;
    VBox vbox;
    HBox menu;
    Image intialDisplay;
    SmartGrid smartGrid;
    HBox progressbox;
    Label progressLabel;
    ProgressBar progressbar;
    ScrollPane scroll;
    Label message;

    String[] choices = {"music"};
    boolean searchflag = true;

    public Converter() {
        this.stage = null;
        this.scene = null;
        this.root = new HBox();
        anchor = new AnchorPane();
        vbox = new VBox(15);
        menu = new HBox(10);
        menu.setPadding(new Insets(8, 8, 2, 8));
        smartGrid = new SmartGrid();
        progressbox = new HBox();
        progressbar = new ProgressBar();
        progressLabel = new Label("Images provided by Itunes");
        message = new Label("Type in a term, then click the button");

        AnchorPane.setTopAnchor(vbox, 0d);
        AnchorPane.setLeftAnchor(vbox, 0d);
        AnchorPane.setRightAnchor(vbox, 0d);
        AnchorPane.setBottomAnchor(vbox, 0d);

        AnchorPane.setTopAnchor(smartGrid, 0d);
        AnchorPane.setLeftAnchor(smartGrid, 0d);
        AnchorPane.setRightAnchor(smartGrid, 0d);
        AnchorPane.setBottomAnchor(smartGrid, 0d);

        convert = new Button("Find");
//        Runnable task = () -> smartGrid.search(searchbar.getText(), streamingService.getValue().toString());
//        convert.setOnAction(event -> run(task));
        convert.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //convert.setDisable(true);
                smartGrid.search(searchbar.getText(), streamingService.getValue().toString());
            }
        });
        searchLabel = new Label("Search:");
        //searchLabel.setTextAlignment(TextAlignment.CENTER);
        searchLabel.setMinSize(searchLabel.getMinWidth(), searchLabel.getMinHeight() + 25);
        searchLabel.setAlignment(Pos.BASELINE_CENTER);
        searchbar = new TextField("Type term(s), then click button!");
        searchbar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (searchflag == true) {
                    searchbar.setText("");
                    searchflag = false;
                }
            }
        });
        HBox.setHgrow(searchbar, Priority.ALWAYS);

        streamingService = new ChoiceBox(FXCollections.observableArrayList(choices));
        streamingService.setValue("music");


        smartGrid.setMinSize(700, 510); //setting size
        smartGrid.setPadding(new Insets(10, 10, 10 ,10)); //setting the padding
        smartGrid.setVgap(50); //vertical gap
        smartGrid.setHgap(50); //horizontal gap
        smartGrid.setAlignment(Pos.CENTER); //alignment

        smartGrid.getChildren().add(new ImageView(intialDisplay));
        menu.getChildren().addAll(searchLabel, searchbar, streamingService, convert);
        vbox.getChildren().addAll(menu);
        vbox.getChildren().addAll(smartGrid);
        progressbox.getChildren().addAll(progressbar, progressLabel);
        vbox.getChildren().addAll(progressbox);
        anchor.getChildren().add(vbox);


    } // Converter

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.scene = new Scene(anchor);

        // setup stage
        this.stage.setTitle("Converter");
        this.stage.setScene(scene);
        this.stage.setOnCloseRequest(event -> Platform.exit());
        this.stage.sizeToScene();
        this.stage.show();
    } // start

    /**
     * Run method.
     * @param runnable interface
     */
    public void run(Runnable runnable) {
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        t.start();
    } //run

} //Converter
