package lpnu.service.impl;

import lpnu.dto.FilmDTO;
import lpnu.entity.Film;
import lpnu.mapper.FilmToFilmDTOMapper;
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
        filmRepository.saveFilm(film);
        return filmMapper.toDTO(film);
    }
}
