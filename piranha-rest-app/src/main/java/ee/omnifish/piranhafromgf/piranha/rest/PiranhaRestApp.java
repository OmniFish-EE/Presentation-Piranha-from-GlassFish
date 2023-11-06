package ee.omnifish.piranhafromgf.piranha.rest;

import cloud.piranha.embedded.EmbeddedPiranha;
import cloud.piranha.embedded.EmbeddedPiranhaBuilder;
import cloud.piranha.extension.annotationscan.AnnotationScanExtension;
import cloud.piranha.extension.coreprofile.CoreProfileExtension;
import cloud.piranha.http.impl.DefaultHttpServer;
import cloud.piranha.http.webapp.HttpWebApplicationServer;
import jakarta.servlet.ServletException;
import java.io.IOException;

public class PiranhaRestApp {

    public static void main(String[] args) throws IOException, ServletException {

        ClassDataSharing.init(args);

        EmbeddedPiranha piranha = new EmbeddedPiranhaBuilder()
                .extensions(AnnotationScanExtension.class,
                        MyExtension.class,
                        CoreProfileExtension.class)
                .build()
                .start();

        var webServer = new HttpWebApplicationServer();
        webServer.addWebApplication(piranha.getWebApplication());
        var httpServer = new DefaultHttpServer(8080);
        httpServer.setHttpServerProcessor(webServer);

        cleanUpAtEnd(httpServer, piranha);

        httpServer.start();

        ClassDataSharing.shutdownIfCDS(httpServer, piranha);

        System.out.println("Application started!");

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

    private static class ClassDataSharing {

        private static boolean classDataSharing = false;

        public static void init(String[] args) {
            if (args.length > 0) {
                classDataSharing = args[0].equals("cds");
            }
        }

        public static void shutdownIfCDS(DefaultHttpServer httpServer, EmbeddedPiranha piranha) {
            if (classDataSharing) {
                httpServer.stop();
                piranha.stop()
                        .destroy();
                System.exit(0);
            }
        }

    }
}
