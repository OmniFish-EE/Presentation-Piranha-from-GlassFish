package ee.omnifish.piranhafromgf.piranha.rest;

import cloud.piranha.core.api.AnnotationInfo;
import cloud.piranha.core.api.AnnotationManager;
import cloud.piranha.core.api.WebApplication;
import cloud.piranha.core.api.WebApplicationExtension;
import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;
import org.glassfish.jersey.jsonb.internal.JsonBindingProvider;
import org.glassfish.jersey.servlet.init.JerseyServletContainerInitializer;

public class MyExtension implements WebApplicationExtension {

    private class MyInitializer implements ServletContainerInitializer {

        private static final Class[] ANNOTATED_CLASSES = new Class[]{
            RestApplication.class,
            PiranhaCloudRestResource.class,
            JsonBindingProvider.class
        };

        private WebApplication webApplication;

        // for some reason Jersey isn't initialized from service loader when running in AWS Lambda, 
        // so we initialize manually just in case
        private final ServletContainerInitializer restInitializer = new JerseyServletContainerInitializer();

        public MyInitializer(WebApplication webApplication) {
            this.webApplication = webApplication;
        }

        @Override
        public void onStartup(Set<Class<?>> set, ServletContext sc) throws ServletException {
            final AnnotationManager annotationManager = webApplication.getManager().getAnnotationManager();
            Stream.of(ANNOTATED_CLASSES).forEach(cls -> addAnnotationsForClass(annotationManager, cls));

            restInitializer.onStartup(Set.of(ANNOTATED_CLASSES), sc);
        }

        private void addAnnotationsForClass(final AnnotationManager annotationManager, Class<?> cls) {
            Arrays.stream(cls.getAnnotations()).forEach(annotationInstance -> {
                annotationManager.addAnnotation(new AnnotationInfo<Annotation>() {
                    @Override
                    public Annotation getInstance() {
                        return annotationInstance;
                    }

                    @Override
                    public AnnotatedElement getTarget() {
                        return cls;
                    }
                });
            });
        }
    }

    @Override
    public void configure(WebApplication webApplication) {
        System.out.println("My extension configuring.");
        webApplication.addInitializer(new MyInitializer(webApplication));
    }

}
