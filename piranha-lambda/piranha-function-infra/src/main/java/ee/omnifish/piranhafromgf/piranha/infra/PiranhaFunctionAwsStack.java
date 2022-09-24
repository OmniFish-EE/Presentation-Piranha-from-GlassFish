package ee.omnifish.piranhafromgf.piranha.infra;

import java.util.Arrays;
import static java.util.Collections.singletonList;
import java.util.Map;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.FunctionProps;
import software.amazon.awscdk.services.lambda.LayerVersion;
import software.amazon.awscdk.services.lambda.LayerVersionProps;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.logs.RetentionDays;

public class PiranhaFunctionAwsStack extends Stack {

    public PiranhaFunctionAwsStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public PiranhaFunctionAwsStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        // The code that defines your stack goes here
        LayerVersion java17layer = new LayerVersion(this, "Java17Layer", LayerVersionProps.builder()
                .layerVersionName("Java17Layer")
                .description("Java 17")
                .compatibleRuntimes(Arrays.asList(Runtime.PROVIDED_AL2))
                .code(Code.fromAsset("java17layer.zip"))
                .build());
        
        Function exampleWithLayer = new Function(this, "ExampleWithLayer", FunctionProps.builder()
        .functionName("PiranhaFunction")
        .description("Function for the Piranha from GlassFish presentation")
        .handler("ee.omnifish.piranhafromgf.piranha.PiranhaFunction::handleRequest")
        .runtime(Runtime.PROVIDED_AL2)
//        .environment(Map.of("JAVA_TOOL_OPTIONS", "-XX:TieredStopAtLevel=1"))
        .code(Code.fromAsset("../piranha-function/target/piranha-function-package.jar"))
        .memorySize(3_000)
        .logRetention(RetentionDays.ONE_DAY)
        .layers(singletonList(java17layer))
        .build());
    }
}
