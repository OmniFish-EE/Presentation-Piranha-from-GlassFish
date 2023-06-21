package ee.omnifish.piranhafromgf.piranha.infra;

import com.fasterxml.jackson.annotation.JsonProperty;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.lambda.Alias;
import software.amazon.awscdk.services.lambda.AliasProps;
import software.amazon.awscdk.services.lambda.CfnFunction;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.FunctionProps;
import software.amazon.awscdk.services.lambda.IFunction;
import software.amazon.awscdk.services.lambda.IVersion;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.Version;
import software.amazon.awscdk.services.lambda.VersionProps;
import software.amazon.awscdk.services.logs.RetentionDays;

public class PiranhaFunctionAwsStack extends Stack {

    public PiranhaFunctionAwsStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public PiranhaFunctionAwsStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        // The code that defines your stack goes here
        Function myFunction = new Function(this, "ExampleWithLayer", FunctionProps.builder()
                .functionName("PiranhaFunction")
                .description("Function for the Piranha from GlassFish presentation")
                .handler("ee.omnifish.piranhafromgf.piranha.PiranhaFunction::handleRequest")
                .runtime(Runtime.JAVA_17)
                //        .environment(Map.of("JAVA_TOOL_OPTIONS", "-XX:TieredStopAtLevel=1"))
                .code(Code.fromAsset("../piranha-function/target/piranha-function-package.jar"))
                .memorySize(3_000)
                .logRetention(RetentionDays.ONE_DAY)
                .build());

        enableSnapstart(myFunction);

        publishFunctionVersion(myFunction, "MyVersion");
    }

    private void publishFunctionVersion(Function myFunction, String version) {
        Version myVersion = new Version(this, "version-" + version, new VersionProps() {
            @Override
            public IFunction getLambda() {
                return myFunction;
            }
        });
        new Alias(this, "alias-" + version, new AliasProps() {
            @Override
            public String getAliasName() {
                return version;
            }

            @Override
            public IVersion getVersion() {
                return myVersion;
            }
        });
    }

    private void enableSnapstart(Function myFunction) {
        ((CfnFunction) myFunction.getNode().getDefaultChild())
                .addPropertyOverride("SnapStart", new Object() {
                    @JsonProperty("ApplyOn")
                    public String getApplyOn() {
                        return "PublishedVersions";
                    }
                });
    }
}
