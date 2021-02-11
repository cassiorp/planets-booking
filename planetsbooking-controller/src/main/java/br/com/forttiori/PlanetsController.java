package br.com.forttiori;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/planets")
@RequiredArgsConstructor
public class PlanetsController {

    @GetMapping
    public String teste() {
        return "TesteE";
    }

}
