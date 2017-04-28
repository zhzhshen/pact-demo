package zoo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class AnimalsController {
    @RequestMapping("/animals")
    public List<String> allAnimals() {
        return Arrays.asList("tiger");
    }
}
