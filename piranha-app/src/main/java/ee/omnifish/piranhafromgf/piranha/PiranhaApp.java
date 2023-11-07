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

        ClassDataSharing.init(args);

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

        ClassDataSharing.shutdownIfCDS(httpServer, piranha);

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

    static class ClassDataSharing {

        private static boolean classDataSharing = false;

        public static void init(String[] args) {
            if (args.length > 0) {
                classDataSharing = args[0].equals("cds");
            }
        }

        public static void shutdownIfCDS(DefaultHttpServer httpServer, EmbeddedPiranha piranha) {
            if (classDataSharing) {
                if (httpServer != null) {
                    httpServer.stop();
                }
                piranha.stop()
                        .destroy();
                System.exit(0);
            }
        }

    }
}
