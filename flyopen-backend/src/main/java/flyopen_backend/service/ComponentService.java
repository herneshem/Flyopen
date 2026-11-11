package flyopen_backend.service;

import flyopen_backend.dto.ComponentDTO;
import flyopen_backend.repository.ComponentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ComponentService {

    private final ComponentRepository componentRepository;

    public ComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    public List<ComponentDTO> findAll() {
        return componentRepository.findAll().stream()
                .map(ComponentDTO::from)
                .toList();
    }
}