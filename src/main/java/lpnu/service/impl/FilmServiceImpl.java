package lpnu.service.impl;

import lpnu.dto.FilmDTO;
import lpnu.entity.Film;
import lpnu.exception.ServiceException;
import lpnu.mapper.FilmToFilmDTOMapper;
import lpnu.model.EnumTechnology;
import lpnu.repository.FilmRepository;
import lpnu.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmServiceImpl implements FilmService {
    @Autowired
    private FilmToFilmDTOMapper filmMapper;

    @Autowired
    private FilmRepository filmRepository;

    @Override
    public List<FilmDTO> getAllFilms() {
        return filmRepository.getAllFilms().stream()
                .map(filmMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FilmDTO getFilmById(final Long id) {
        return filmMapper.toDTO(filmRepository.getFilmById(id));
    }

    @Override
    public void deleteFilmById(final Long id) {
        filmRepository.getFilmById(id);
        filmRepository.deleteFilmById(id);
    }

    @Override
    public FilmDTO updateFilm(final FilmDTO filmDTO) {
        filmRepository.getFilmById(filmDTO.getId());
        return filmMapper.toDTO(filmRepository.updateFilm(filmMapper.toEntity(filmDTO)));
    }

    @Override
    public FilmDTO saveFilm(final FilmDTO filmDTO) {
        final Film film = filmMapper.toEntity(filmDTO);
        double price = 0;

        for(final EnumTechnology technology : EnumTechnology.values()){
            if(technology.getName().equals(film.getTechnology())){
                price = technology.getPrice();
            }
        }
        if(price == 0){
            throw new ServiceException(400, "enum doesn't contain " + film.getTechnology() + " technology");
        }

        film.setPriceTechnology(price);
        filmRepository.saveFilm(film);

        return filmMapper.toDTO(film);
    }
}
