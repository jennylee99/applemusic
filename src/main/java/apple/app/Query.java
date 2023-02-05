package apple.app;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Created a query class to create uri.
 */
public class Query {
    private String term;
    private String media;
    private String limit;
    private String query;
    private String url;

    /**
     * Query constructor.
     * @param term
     * @param media
     * @param limit
     * @param query
     * @param url
     */
    private Query(String term, String media, String limit, String query, String url) {
        this.term = term;
        this.media = media;
        this.limit = limit;
        this.query = query;
        this.url = url;
    }

    /**
     * Returns the term.
     * @return String
     */
    public String getTerm() {
        return term;
    }

    /**
     * Returns the media type.
     * @return String
     */
    public String getMedia() {
        return media;
    }

    /**
     * Returns the limit.
     * @return String
     */
    public String getLimit() {
        return limit;
    }

    /**
     * Returns the entire query.
     * @return String
     */
    public String getQuery() {
        return query;
    }

    /**
     * Returns the url.
     * @return String
     */
    public String getUrl() {
        return url;
    }

    /**
     * QueryBuilder class.
     */
    public static class QueryBuilder {
        private String term;
        private String media;
        private String limit;
        private String query;
        private String url;

        /**
         * Sets the term of the query.
         * @param term
         * @return QueryBuilder
         */
        public QueryBuilder setTerm(String term) {
            this.term = URLEncoder.encode(term, StandardCharsets.UTF_8);
            return this;
        }

        /**
         * Sets the media.
         * @param media
         * @return QueryBuilder
         */
        public QueryBuilder setMedia(String media) {
            this.media = media;
            return this;
        }

        /**
         * Sets the QueryBuilder limit.
         * @param limit
         * @return QueryBuilder
         */
        public QueryBuilder setLimit(String limit) {
            this.limit = limit;
            return this;
        }

        /**
         * Sets the QueryBuilder query.
         * @param query
         * @return QueryBuilder
         */
        public QueryBuilder setQuery(String query) {
            this.query = query;
            return this;
        }

        /**
         * Sets the QueryBuilder url.
         * @param url
         * @return QueryBuilder
         */
        public QueryBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        /**
         * Builds the query.
         * @return Query
         */
        public Query build() {
            return new Query(term, media, limit, query, url);
        }
    }


}
