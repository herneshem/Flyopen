package flyopen_backend.dto;

import flyopen_backend.entity.Drone;

import java.util.List;

public record DroneDTO(Long id, String name, String profileCode, List<NodeDTO> nodes) {

    public static DroneDTO from(Drone d) {
        return new DroneDTO(d.getId(), d.getName(), d.getProfile().getCode(),
                d.getNodes().stream().map(NodeDTO::from).toList());
    }
}
