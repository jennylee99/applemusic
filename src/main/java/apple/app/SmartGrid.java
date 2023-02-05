package apple.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.concurrent.Task;
import javafx.application.Platform;
import javafx.geometry.Insets;
import java.net.URI;
import java.net.URL;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.net.URLEncoder;

/**
 * SmartGrid declaration.
 */
public class SmartGrid extends GridPane {
    String url = null;
    ProgressBar progressbar = null;
    HashMap<Integer, VBox> nodeMap = new HashMap<>(); // store index position in grid to access and remove easier
    String spotifyResults = "";
    Converter barUpdate; // reference object of Converter class's bar

    HttpResponse<String> response;

    /**
     * SmartGrid constructor.
     */
    public SmartGrid() {
        ColumnConstraints column1 = new ColumnConstraints(150,100, 200);
        column1.setHgrow(Priority.NEVER);
        ColumnConstraints column2 = new ColumnConstraints(150,100, 200);
        column2.setHgrow(Priority.NEVER);
        ColumnConstraints column3 = new ColumnConstraints(150,100, 200);
        column3.setHgrow(Priority.NEVER);

        this.getColumnConstraints().addAll(column1, column2, column3);
        this.setStyle("-fx-background-color: light green");
    }

    /**
     * Updates spotifyResults.
     * @param results
     */
    public void setSpotifyResults(String results) {
        spotifyResults = results;
    }
    /**
     * Sets the progressbar.
     * @param pb
     */
    public void setProgressBar(ProgressBar pb) {
        this.progressbar = pb;
    }

    /**
     * Returns the url.
     * @return String
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Returns the progressbar status.
     * @return double
     */
    public double getProgress() {
        return progressbar.getProgress();
    }

    /**
     * Adds the items in the given list of results to the GUI.
     *
     * @param results the list of results to add to the GUI
     */
    private void addContents(ArrayList<Result> results) {
        for (VBox item: nodeMap.values()) { //removes current elements from gridpane
            this.getChildren().remove(item);
        }
        if (results.size() == 0) {
            // If the list is empty, do not continue.
            System.out.println("Results size is empty");
            return;
        }

        for (int i = 0; i < results.size() && i < 9 ; i++) { //either iterates through size or up to 9 results
            VBox content = new VBox();
            content.setStyle("-fx-background-color: 255, 255, 255");
            content.setAlignment(Pos.CENTER);
            content.setPadding(new Insets(10, 0, 0, 0));
            //round corners
            ImageView imageview = new ImageView(new Image(results.get(i).getArtUrl()));
            imageview.setFitWidth(80);
            imageview.setFitHeight(80);
            String trackName = results.get(i).getTrackName();
            String artistName = results.get(i).getArtist();
            Label song = new Label(results.get(i).getTrackName());
            song.setTextOverrun(OverrunStyle.ELLIPSIS);
            Label singer= new Label(results.get(i).getArtist());
            singer.setTextOverrun(OverrunStyle.ELLIPSIS);
            Hyperlink hyperlink = new Hyperlink("Music Link");
            String applelink = results.get(i).getLink();
            // Set the action for the hyperlink
            hyperlink.setOnAction(event -> {
                try {
                    URI link = new URI(applelink);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Music Link");
                    alert.setHeaderText("Here is the music link:");
                    alert.setContentText(applelink);
                    Button copyButton = new Button("Copy Link!");
                    copyButton.setOnAction(e -> {
                        Clipboard clipboard = Clipboard.getSystemClipboard();
                        ClipboardContent clipboardContent = new ClipboardContent();
                        clipboardContent.putString(applelink);
                        clipboard.setContent(clipboardContent);
                        alert.setHeaderText("Linked copied to clipboard!");
                    });
                    alert.getButtonTypes().add(ButtonType.CANCEL);
                    alert.getDialogPane().setContent(copyButton);
                    alert.show();
                } catch (URISyntaxException e) {
                    // Handle the exception
                }
            });

            content.getChildren().addAll(imageview, song, singer, hyperlink);
            int finalI = i;
            this.add(content, finalI % 3, finalI / 3); //adds in rows of 3
            nodeMap.put(this.getChildren().indexOf(content), content);
            //convert.setDisable(false);
        } //for
    }

    /**
     * Accepts the textfield's input.
     * @param text
     * @param category
     */
    public void search(String text, String category) {
        HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        Query searchQuery = new Query.QueryBuilder()
                .setTerm(text)
                .setMedia(category)
                .setLimit("200")
                .setQuery(String.format("?term=%s&media=%s&limit=%s", URLEncoder.encode(text, StandardCharsets.UTF_8), category, "200"))
                .setUrl(Constants.ITUNES_API)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(searchQuery.getUrl() + searchQuery.getQuery()))
                .build();
        // send request / receive response in the form of a String
        // setMessage(getQuery());
        ArrayList <Result> results = new ArrayList<>();
        try {
//            HttpResponse<String> response = HTTP_CLIENT.send(request, BodyHandlers.ofString());w
            response = HTTP_CLIENT.send(request, BodyHandlers.ofString());
            // ensure the request is okay
            if (response.statusCode() != 200) {
                throw new IOException(response.toString());
            } // if
            // get request body (the content we requested)
            String jsonString = response.body();
            Gson g = new GsonBuilder().create();
            ItunesResponse jsonResponse = g.fromJson(jsonString, ItunesResponse.class);
//            AtomicInteger progress = new AtomicInteger(0);
//            JProgressBar progressBar = new JProgressBar();
            for (int i = 0; i < jsonResponse.resultCount && i < 9; i++) {
//                int finalI = i;
//                Runnable task = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Thread.progressbar.setProgress();
//                    }
//                }).start();
//                int finalI = i;
                //progressbar.setProgress((i* 1.0)/ 9);
                ItunesResult result = new ItunesResult();
                result.artworkUrl100 = jsonResponse.results[i].artworkUrl100;
                result.artistName = jsonResponse.results[i].artistName;
                result.trackName = jsonResponse.results[i].trackName;
                System.out.println(result.trackName);
                result.trackViewUrl = jsonResponse.results[i].trackViewUrl;
                results.add(result);
//                int finalCount = count;
//                Runnable task = () -> progressbar.setProgress(finalCount /9);
            }
            if (results.size() == 0) {
            }
            addContents(results);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } //try-catch
    } //search

    private void errorMessage() {
        Runnable errorTask = () -> {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            String errorMsg = response.toString();
            Exception ex = new IOException(errorMsg);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String excepMsg = "Exception:" + sw.toString();
            TextArea area = new TextArea(excepMsg);
            area.setEditable(false);
            area.setWrapText(true);
            area.setMaxWidth(Double.MAX_VALUE);
            area.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(area, Priority.ALWAYS);
            GridPane.setHgrow(area, Priority.ALWAYS);
            GridPane content = new GridPane();
            content.setMaxWidth(Double.MAX_VALUE);
            content.add(area, 0, 1);
            alert.getDialogPane().setExpandableContent(content);
            alert.showAndWait();
        };
        Platform.runLater(errorTask);
    } // errorMessage

} // SmartGrid
