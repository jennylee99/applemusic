package apple.app;

/**
 * This is the abstract result class.
 */
public abstract class Result {
    /**
     * This method gets the art URL associated with the result.
     * @return String
     */
    public abstract String getArtUrl();

    /**
     * This method gets the track name associated with the result.
     * @return String
     */

    public abstract String getTrackName();

    /**
     * This method gets the artist name associated with the result.
     * @return String
     */
    public abstract String getArtist();

    /**
     * This method gets the link associated with the result.
     * @return String
     */
    public abstract String getLink();
}
