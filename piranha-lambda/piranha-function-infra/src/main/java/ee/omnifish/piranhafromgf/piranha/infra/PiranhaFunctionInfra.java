package ee.omnifish.piranhafromgf.piranha.infra;

import software.amazon.awscdk.App;
import software.amazon.awscdk.StackProps;


public class PiranhaFunctionInfra {
    public static void main(final String[] args) {
        App app = new App();

        new PiranhaFunctionAwsStack(app, "PiranhaFunctionStack", StackProps.builder()
                .build());

        app.synth();
    }
}

