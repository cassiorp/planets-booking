package br.com.forttiori;

import br.com.forttiori.service.PlanetService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class FacadeIntegration {
    public final PlanetService planetService;

    public FacadeIntegration(PlanetService planetService) {
        this.planetService = planetService;
    }
}
