package lpnu.service.impl;

import lpnu.dto.FilmDTO;
import lpnu.dto.HallDTO;
import lpnu.entity.Hall;
import lpnu.exception.ServiceException;
import lpnu.mapper.FilmToFilmDTOMapper;
import lpnu.mapper.HallToHallDTOMapper;
import lpnu.repository.HallRepository;
import lpnu.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HallServiceImpl implements HallService {
    @Autowired
    private HallToHallDTOMapper hallMapper;

    @Autowired
    private FilmToFilmDTOMapper filmMapper;

    @Autowired
    private HallRepository hallRepository;

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
        hallRepository.saveHall(hall);
        return hallMapper.toDTO(hall);
    }

    @Override
    public HallDTO addFilm(final FilmDTO filmDTO, final Long id){
        final Hall hall = hallMapper.toEntity(getHallById(id));

        if(hall.getFilms().stream().anyMatch(filmMapper.toEntity(filmDTO)::equals)){
            throw new ServiceException(400, "identical films");
        }else{
            hall.getFilms().add(filmMapper.toEntity(filmDTO));
        }
        return hallMapper.toDTO(hall);
    }
}
