package flyopen_backend.service;


import flyopen_backend.dto.DroneDTO;
import flyopen_backend.entity.*;
import flyopen_backend.repository.ComponentRepository;
import flyopen_backend.repository.DroneRepository;
import flyopen_backend.repository.MissionProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class DroneService {

    private final DroneRepository droneRepository;
    private final MissionProfileRepository profileRepository;
    private final ComponentRepository componentRepository;

    public DroneService(DroneRepository droneRepository,
                        MissionProfileRepository profileRepository,
                        ComponentRepository componentRepository) {
        this.droneRepository = droneRepository;
        this.profileRepository = profileRepository;
        this.componentRepository = componentRepository;
    }


    @Transactional
    public DroneDTO create(String name, Long profileId) {
        MissionProfile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("Perfil no encontrado: " + profileId));

        Drone drone = new Drone();
        drone.setName(name);
        drone.setProfile(profile);

        // Un nodo vacío por cada hueco de la plantilla
        Map<String, DroneNode> byType = new HashMap<>();
        for (ProfileSlot slot : profile.getSlots()) {
            DroneNode node = new DroneNode();
            node.setDrone(drone);
            node.setSlotType(slot.getComponentType());
            node.setQuantity(slot.getQuantity());
            drone.getNodes().add(node);
            byType.put(slot.getComponentType(), node);
        }
        // Enlazar cada hueco con su padre
        for (ProfileSlot slot : profile.getSlots()) {
            if (slot.getParentType() != null) {
                byType.get(slot.getComponentType()).setParent(byType.get(slot.getParentType()));
            }
        }
        return DroneDTO.from(droneRepository.save(drone));
    }

    @Transactional(readOnly = true)
    public DroneDTO findById(Long id) {
        return DroneDTO.from(droneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dron no encontrado: " + id)));
    }

    @Transactional
    public DroneDTO assignComponent(Long droneId, Long nodeId, Long componentId) {
        Drone drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new IllegalArgumentException("Dron no encontrado: " + droneId));

        DroneNode node = drone.getNodes().stream()
                .filter(n -> n.getId().equals(nodeId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Hueco no encontrado: " + nodeId));

        if (componentId == null) {
            node.setComponent(null);
        } else {
            Component component = componentRepository.findById(componentId)
                    .orElseThrow(() -> new IllegalArgumentException("Pieza no encontrada: " + componentId));

            if (!node.getSlotType().equals(component.getType())) {
                throw new IllegalArgumentException("La pieza es de tipo " + component.getType()
                        + " y este hueco admite " + node.getSlotType());
            }
            node.setComponent(component);
        }
        return DroneDTO.from(drone);
    }

}
