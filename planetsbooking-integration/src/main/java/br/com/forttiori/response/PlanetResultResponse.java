package br.com.forttiori.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanetResultResponse {

    private List<PlanetInfoResponse> results;

}
