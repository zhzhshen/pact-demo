package zoo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZooController {
    @RequestMapping("/")
    public String index() {
        return "zoo index";
    }
}
