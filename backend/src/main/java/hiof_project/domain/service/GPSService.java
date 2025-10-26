package hiof_project.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GPSService {

    private final RestTemplate restTemplate;

    public GPSService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getVehiclePositions() {
        // Entur API URL for sanntids kjøretøyposisjoner
        String url = "https://api.entur.io/realtime/v1/gtfs-rt/vehicle-positions?datasource=RUT"; // Endepunkt for utvalgt sone (RUT=Ruter, denne er fleksibel)

        // Sender GET-forespørsel til Entur API
        return restTemplate.getForObject(url, String.class);
    }
}
