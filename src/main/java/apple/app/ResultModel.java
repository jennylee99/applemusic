package apple.app;

/**
 * ResultModel class.
 */

public class ResultModel {
    String imageUrl;
    String song;
    String artist;
    String link;

    /**
     * ResultModel.
     */

    public ResultModel(){}

    /**
     * ResultModel constructor.
     * @param imageUrl
     * @param song
     * @param artist
     * @param link
     */
    public ResultModel(String imageUrl, String song, String artist, String link) {
        this.imageUrl = imageUrl;
        this.song = song;
        this.artist = artist;
        this.link = link;
    }

    /**
     * Gets the imageUrl.
     * @return imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Updates the imageUrl.
     * @param imageUrl
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Gets the track name.
     * @return song
     */
    public String getSong() {
        return song;
    }

    /**
     * Updates the track name.
     * @param song
     */
    public void setSong(String song) {
        this.song = song;
    }

    /**
     * Returns the artist.
     * @return artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Updates the artist name.
     * @param artist
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Gets the link url.
     * @return link
     */
    public String getLink() {
        return link;
    }

    /**
     * Updates the link.
     * @param link
     */
    public void setLink(String link) {
        this.link = link;
    }
}
