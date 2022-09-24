package ee.omnifish.piranhafromgf.springboot;

import javax.servlet.http.HttpServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
   @Bean    
   public ServletRegistrationBean<HttpServlet> servlet() {
       ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
       servRegBean.setServlet(new SpringBootSimpleServlet());
       servRegBean.addUrlMappings("/*");
       servRegBean.setLoadOnStartup(1);
       return servRegBean;
   }
}  