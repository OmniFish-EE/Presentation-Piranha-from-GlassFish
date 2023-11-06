package ee.omnifish.piranhafromgf.piranha;

import cloud.piranha.embedded.EmbeddedPiranha;
import cloud.piranha.embedded.EmbeddedPiranhaBuilder;
import cloud.piranha.http.impl.DefaultHttpServer;
import cloud.piranha.http.webapp.HttpWebApplicationServer;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

public class PiranhaApp {

    public static void main(String[] args) {
        initLogging();

        EmbeddedPiranha piranha = new EmbeddedPiranhaBuilder()
                .servletMapped(PiranhaSimpleServlet.class, "/")
                .build();

        piranha.start();

        var webServer = new HttpWebApplicationServer();
        webServer.addWebApplication(piranha.getWebApplication());
        var httpServer = new DefaultHttpServer(8080);
        httpServer.setHttpServerProcessor(webServer);

        cleanUpAtEnd(httpServer, piranha);

        httpServer.start();
        System.out.println("Application started!");

    }

    private static void initLogging() throws SecurityException {
        InputStream stream = PiranhaApp.class.getClassLoader().
                getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void cleanUpAtEnd(DefaultHttpServer httpServer, EmbeddedPiranha piranha) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public synchronized void start() {
                httpServer.stop();
                piranha.stop()
                        .destroy();
            }

        });
    }
}
