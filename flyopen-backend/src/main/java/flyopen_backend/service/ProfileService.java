package flyopen_backend.service;

import flyopen_backend.dto.ProfileDTO;
import flyopen_backend.repository.MissionProfileRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    private final MissionProfileRepository profileRepository;

    public ProfileService(MissionProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Transactional(readOnly = true)
    public List<ProfileDTO> findAll() {
        return profileRepository.findAll().stream().map(ProfileDTO::from).toList();
    }
}
