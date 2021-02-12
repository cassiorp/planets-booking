package br.com.forttiori.client;

import br.com.forttiori.response.PlanetInfoResponse;
import br.com.forttiori.response.PlanetResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "starwarsapi", url = "${api.starwars}")
public interface PlanetClient {

    @GetMapping("/planets/")
    public PlanetResultResponse findAllPlanets(@RequestParam("page") Integer page);

    @GetMapping("/planets/{id}/")
    public PlanetInfoResponse findById(@PathVariable("id") Integer id);

}
