package br.com.forttiori;

import br.com.forttiori.client.PlanetClient;
import br.com.forttiori.response.PlanetInfoResponse;
import br.com.forttiori.response.PlanetResultResponse;
import br.com.forttiori.service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/planets")
@RequiredArgsConstructor
public class PlanetController {

    private final PlanetService planetService;
    private final PlanetClient client;

    @GetMapping
    public PlanetResultResponse findAllPlanets(
            @Valid @RequestParam(value = "page", required = false)   Integer page
    ) {
        return this.planetService.findAllPlanets(page);
    }

    @GetMapping("/{id}")
    public PlanetInfoResponse findById(@Valid @PathVariable Integer id) {
        return this.planetService.findById(id);
    }


}
