package zoo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AnimalsController {
    @RequestMapping("/animals")
    public Map<String, String> getAnimals() {
        Map<String, String> result = new HashMap<>();
        result.put("tiger", "10");
        result.put("monkey", "100");
        return result;
    }
}
