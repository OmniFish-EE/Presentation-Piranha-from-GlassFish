package ee.omnifish.piranhafromgf.embeddedgf;

import java.io.File;
import java.io.IOException;
import org.glassfish.embeddable.Deployer;
import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;

public class EmbeddedGlassfishExplodedWarApp {

    public static void main(String[] args) throws GlassFishException, IOException {
        final GlassFishProperties gfProperties = new GlassFishProperties();
        gfProperties.setPort("http-listener", 8080);

        GlassFish glassfish = GlassFishRuntime.bootstrap().newGlassFish(gfProperties);
        glassfish.start();

        final Deployer deployer = glassfish.getDeployer();
        final String appName = deployer.deploy(new File("target", "webapp.war").toURI(), 
                "--contextroot=/", "--type=war");
        cleanUpAtEnd(deployer, appName, glassfish);
        System.out.println("Application deployed!");

    }

    private static void cleanUpAtEnd(final Deployer deployer, final String appName, GlassFish glassfish) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public synchronized void start() {
                try {
                    deployer.undeploy(appName);
                    glassfish.stop();
                    glassfish.dispose();
                } catch (GlassFishException ex) {
                }
            }

        });
    }
}
