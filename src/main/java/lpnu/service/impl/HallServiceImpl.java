package lpnu.service.impl;

import lpnu.dto.FilmDTO;
import lpnu.dto.HallDTO;
import lpnu.entity.Hall;
import lpnu.exception.ServiceException;
import lpnu.mapper.FilmToFilmDTOMapper;
import lpnu.mapper.HallToHallDTOMapper;
import lpnu.repository.FilmRepository;
import lpnu.repository.HallRepository;
import lpnu.service.HallService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HallServiceImpl implements HallService {
    private final HallToHallDTOMapper hallMapper;
    private final FilmToFilmDTOMapper filmMapper;
    private final HallRepository hallRepository;
    private final FilmRepository filmRepository;

    public HallServiceImpl(final HallToHallDTOMapper hallMapper, final FilmToFilmDTOMapper filmMapper,
                           final HallRepository hallRepository, final FilmRepository filmRepository) {
        this.hallMapper = hallMapper;
        this.filmMapper = filmMapper;
        this.hallRepository = hallRepository;
        this.filmRepository = filmRepository;
    }

    @Override
    public List<HallDTO> getAllHalls() {
        return hallRepository.getAllHalls().stream()
                .map(hallMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HallDTO getHallById(final Long id) {
        return hallMapper.toDTO(hallRepository.getHallById(id));
    }

    @Override
    public void deleteHallById(final Long id) {
        hallRepository.getHallById(id);
        hallRepository.deleteHallById(id);
    }

    @Override
    public HallDTO updateHall(final HallDTO hallDTO) {
        hallRepository.getHallById(hallDTO.getId());
        return hallMapper.toDTO(hallRepository.updateHall(hallMapper.toEntity(hallDTO)));
    }

    @Override
    public HallDTO saveHall(final HallDTO hallDTO) {
        final Hall hall = hallMapper.toEntity(hallDTO);

        if (hallRepository.getAllHalls().stream().map(e -> e.equals(hall)).findAny().isPresent()) {
            throw new ServiceException(400, "hall is already saved");
        }

        hallRepository.saveHall(hall);
        return hallMapper.toDTO(hall);
    }

    @Override
    public HallDTO addFilm(final FilmDTO filmDTO, final Long id) {
        final Hall hall = hallMapper.toEntity(getHallById(id));

//        if (hallRepository.getHallById(id).getFilms(filmRepository.getFilmById(filmDTO.getId().intValue())).stream().anyMatch(filmMapper.toEntity(filmDTO)::equals)) {
//            throw new ServiceException(400, "there is already such film");
//        } else {
//            hall.getFilms().add(filmMapper.toEntity(filmDTO));
//        }
        return hallMapper.toDTO(hall);
    }
}
