package ee.omnifish.piranhafromgf.embeddedgf;

import java.io.File;
import java.io.IOException;
import org.glassfish.embeddable.Deployer;
import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;
import org.glassfish.embeddable.archive.ScatteredArchive;

public class EmbeddedGlassfishApp {

    public static void main(String[] args) throws GlassFishException, IOException {
        final GlassFishProperties gfProperties = new GlassFishProperties();
        gfProperties.setPort("http-listener", 8080);

        GlassFish glassfish = GlassFishRuntime.bootstrap().newGlassFish(gfProperties);
        glassfish.start();

        ScatteredArchive archive = new ScatteredArchive("simpleapp", ScatteredArchive.Type.WAR);
        archive.addCurrentClassPath();

        final Deployer deployer = glassfish.getDeployer();
        final String appName = deployer.deploy(archive.toURI(), "--contextroot=/");
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

    private static final String REGEX_FS = File.separator.replace("\\", "\\\\");

    private static boolean jarFileMatchesAny(String pathElem, String... matches) {
        for (String match : matches) {
            if (pathElem.matches(".*" + REGEX_FS + match + "[^" + REGEX_FS + "]*\\.jar")) {
                return true;
            }
        }
        return false;
    }

}
