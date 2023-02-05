package apple.app;

/**
 * Represents a result in a response from the iTunes Search API. This is
 * used by Gson to create an object from the JSON response body. This class
 * is provided with project's starter code, and the instance variables are
 * intentionally set to package private visibility.
 * @see <a href="https://developer.apple.com/library/archive/documentation/AudioVideo/
 Conceptual/iTuneSearchAPI/UnderstandingSearchResults.html#/
 /apple_ref/doc/uid/TP40017632-CH8-SW1">Understanding Search Results</a>
 */
public class ItunesResult extends Result {
    String wrapperType;
    String kind;
    String artworkUrl100;
    String artistName;
    String trackName;
    String trackViewUrl;
    // the rest of the result is intentionally omitted since we don't use it

    /**
     * This method gets the imageUrl.
     * @return artworkUrl100
     */
    public String getArtUrl() {
        return artworkUrl100;
    }

    /**
     * This method gets the trackname.
     * @return trackname
     */
    @Override
    public String getTrackName() {
        return trackName;
    }

    /**
     * This method gets the artistname.
     * @return artistname
     */
    @Override
    public String getArtist() {
        return artistName;
    }

    /**
     * This method gets the trackViewUrl.
     * @return trackViewUrl
     */
    @Override
    public String getLink() {
        return trackViewUrl;
    }
} // ItunesResult
