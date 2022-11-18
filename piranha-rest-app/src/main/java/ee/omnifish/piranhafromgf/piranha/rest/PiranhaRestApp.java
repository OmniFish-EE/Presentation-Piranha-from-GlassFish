package ee.omnifish.piranhafromgf.piranha.rest;

import cloud.piranha.embedded.EmbeddedPiranha;
import cloud.piranha.embedded.EmbeddedPiranhaBuilder;
import cloud.piranha.extension.annotationscan.AnnotationScanExtension;
import cloud.piranha.extension.coreprofile.CoreProfileExtension;
import cloud.piranha.http.impl.DefaultHttpServer;
import cloud.piranha.http.webapp.HttpWebApplicationServerProcessor;
import jakarta.servlet.ServletException;
import java.io.IOException;

public class PiranhaRestApp {

    public static void main(String[] args) throws IOException, ServletException {
        EmbeddedPiranha piranha = new EmbeddedPiranhaBuilder()
                .extensions(AnnotationScanExtension.class,
                        MyExtension.class,
                        CoreProfileExtension.class)
                .build()
                .start();

        var httpProcessor = new HttpWebApplicationServerProcessor(piranha);
        var httpServer = new DefaultHttpServer(8080, httpProcessor, false);

        cleanUpAtEnd(httpServer, piranha);

        httpServer.start();
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
}
