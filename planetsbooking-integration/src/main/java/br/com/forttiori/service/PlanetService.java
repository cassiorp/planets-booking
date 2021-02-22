package br.com.forttiori.service;

import br.com.forttiori.client.PlanetClient;
import br.com.forttiori.response.PlanetInfoResponse;
import br.com.forttiori.response.PlanetResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanetService {

    private final PlanetClient planetClient;

    public PlanetResultResponse findAllPlanets(Integer page) {
        Optional<PlanetResultResponse> results = Optional.ofNullable(this.planetClient.findAllPlanets(page));
        return results.orElseThrow(() -> new RuntimeException());
    }

    public PlanetInfoResponse findById(Integer id) {
        Optional<PlanetInfoResponse> planet = Optional.ofNullable(this.planetClient.findById(id));
        return planet.orElseThrow(() -> new RuntimeException());
    }

    public PlanetResultResponse getAllPlanetsWithoutPagination(){
        PlanetResultResponse resultResponse = new PlanetResultResponse();
        for(int i = 1; i <= 6; i++){
            resultResponse.setResults(this.planetClient.findAllPlanets(i).getResults());
        }
        return resultResponse;
    }
}
