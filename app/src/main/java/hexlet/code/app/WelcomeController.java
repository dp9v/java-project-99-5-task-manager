package hexlet.code.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/welcome")
public final class WelcomeController {

    @GetMapping
    public String welcome() {
        return "Hello there!";
    }
}