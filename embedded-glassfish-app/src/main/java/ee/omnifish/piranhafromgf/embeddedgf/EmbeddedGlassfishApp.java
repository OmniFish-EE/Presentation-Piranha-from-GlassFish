package ee.omnifish.piranhafromgf.embeddedgf;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import org.glassfish.embeddable.Deployer;
import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;
import org.glassfish.embeddable.archive.ScatteredArchive;

public class EmbeddedGlassfishApp {

    public static void main(String[] args) throws GlassFishException, IOException {
        final GlassFishProperties gfProperties = new GlassFishProperties(copyOfSystemProperties());
        setPortFromSimplifiedPropertyOrDefault(gfProperties, "http.port", 8080);

        GlassFish glassfish = GlassFishRuntime.bootstrap().newGlassFish(gfProperties);
        glassfish.start();

        ScatteredArchive archive = new ScatteredArchive("simpleapp", ScatteredArchive.Type.WAR);
        archive.addCurrentClassPath();

        final Deployer deployer = glassfish.getDeployer();
        final String appName = deployer.deploy(archive.toURI(), "--contextroot=/");
        cleanUpAtEnd(deployer, appName, glassfish);
        System.out.println("Application deployed!");

    }

    private static void setPortFromSimplifiedPropertyOrDefault(final GlassFishProperties gfProperties, String simplifiedPropertyname, int defaultPort) {
        if (-1 == gfProperties.getPort("http-listener")) {
            gfProperties.setPort("http-listener", Optional.ofNullable(gfProperties.getProperties().getProperty(simplifiedPropertyname))
                    .map(EmbeddedGlassfishApp::stringToIntOrNull)
                    .filter(Objects::nonNull)
                    .orElse(defaultPort));
        }
    }

    private static Integer stringToIntOrNull(String s) {
        try {
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Properties copyOfSystemProperties() {
        return new Properties(System.getProperties());
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
