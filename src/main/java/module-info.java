/**
 * Provides the <strong>apple-app</strong> application.
 */
module apple.app {
    requires transitive java.logging;
    requires transitive java.net.http;
    requires transitive javafx.controls;
    requires transitive com.google.gson;
    opens apple.app;
} // module
