package ee.omnifish.piranhafromgf.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ondro Mihalyi
 */
@RestController
public class SimpleRESTController {

  @GetMapping("/")
  String hello() {
    return "Hello from Spring Boot REST resource!";
  }

}
