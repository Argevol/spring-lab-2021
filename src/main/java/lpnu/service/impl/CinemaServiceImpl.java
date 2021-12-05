package lpnu.service.impl;

import lpnu.dto.CinemaDTO;
import lpnu.dto.FilmDTO;
import lpnu.dto.HallDTO;
import lpnu.entity.Cinema;
import lpnu.entity.Hall;
import lpnu.exception.ServiceException;
import lpnu.mapper.CinemaToCinemaDTOMapper;
import lpnu.mapper.FilmToFilmDTOMapper;
import lpnu.mapper.HallToHallDTOMapper;
import lpnu.repository.CinemaRepository;
import lpnu.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    private CinemaToCinemaDTOMapper cinemaMapper;

    @Autowired
    private HallToHallDTOMapper hallMapper;

    @Autowired
    private FilmToFilmDTOMapper filmMapper;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Override
    public List<CinemaDTO> getAllCinemas() {
        return cinemaRepository.getAllCinemas().stream()
                .map(cinemaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CinemaDTO getCinemaById(final Long id) {
        return cinemaMapper.toDTO(cinemaRepository.getCinemaById(id));
    }

    @Override
    public void deleteCinemaById(final Long id) {
        cinemaRepository.getCinemaById(id);
        cinemaRepository.deleteCinemaById(id);
    }

    @Override
    public CinemaDTO updateCinema(final CinemaDTO cinemaDTO) {
        cinemaRepository.getCinemaById(cinemaDTO.getId());
        return cinemaMapper.toDTO(cinemaRepository.updateCinema(cinemaMapper.toEntity(cinemaDTO)));
    }

    @Override
    public CinemaDTO saveCinema(final CinemaDTO cinemaDTO) {
        final Cinema cinema = cinemaMapper.toEntity(cinemaDTO);
        cinemaRepository.saveCinema(cinema);
        return cinemaMapper.toDTO(cinema);
    }

    @Override
    public CinemaDTO addHall(final HallDTO hallDTO, final Long id) {
        final Cinema cinema = cinemaMapper.toEntity(getCinemaById(id));

        if (cinema.getHalls().stream().anyMatch(hallMapper.toEntity(hallDTO)::equals)) {
            throw new ServiceException(400, "identical films");
        } else {
            cinema.getHalls().add(hallMapper.toEntity(hallDTO));
        }
        return cinemaMapper.toDTO(cinema);
    }

    @Override
    public CinemaDTO addFilm(final FilmDTO filmDTO, final Long cinemaId, final Long hallId) {
        final Cinema cinema = cinemaMapper.toEntity(getCinemaById(cinemaId));
        final Hall hall = cinema.getHalls().get(hallId.intValue() - 1);

        if (hall.getFilms().stream().anyMatch(filmMapper.toEntity(filmDTO)::equals)) {
            throw new ServiceException(400, "identical films");
        } else {
            hall.getFilms().add(filmMapper.toEntity(filmDTO));
        }

        cinema.addHall(hall);
        return cinemaMapper.toDTO(cinema);
    }
}