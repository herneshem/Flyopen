package flyopen_backend.controller;

import flyopen_backend.dto.AssignComponentRequest;
import flyopen_backend.dto.CreateDroneRequest;
import flyopen_backend.dto.DroneDTO;
import flyopen_backend.service.DroneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/drones")
public class DroneController {

    private final DroneService droneService;

    public DroneController(DroneService droneService){
        this.droneService = droneService;
    }

    @PostMapping
    public DroneDTO create(@RequestBody CreateDroneRequest request) {
        return droneService.create(request.name(), request.profileId());
    }

    @GetMapping("/{id}")
    public DroneDTO get(@PathVariable Long id) {
        return droneService.findById(id);
    }

    @PutMapping("/{droneId}/nodes/{nodeId}")
    public DroneDTO assign(@PathVariable Long droneId, @PathVariable Long nodeId,
                           @RequestBody AssignComponentRequest request) {
        return droneService.assignComponent(droneId, nodeId, request.componentId());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
}
