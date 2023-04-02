package controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accommodation")
@RequiredArgsConstructor
public class ExampleController {

    @GetMapping
    public void ispisi()
    {
        System.out.println("Hello");
    }
}
