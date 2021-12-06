package lpnu.repository;

import lpnu.entity.Film;
import lpnu.exception.ServiceException;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmRepository {
    private List<Film> films;
    private long id = 1;

    @PostConstruct
    public void init() {
        films = new ArrayList<>();
    }

    public List<Film> getAllFilms() {
        return new ArrayList<>(films);
    }

    public void deleteFilmById(final Long id) {
        for (final Film film : films) {
            if (film.getId().equals(id)) {
                films.remove(film);
                break;
            }
        }
    }

    public Film updateFilm(final Film film) {
        final Film savedFilm = getFilmById(film.getId());

        savedFilm.setName(film.getName());
        savedFilm.setDuration(film.getDuration());
        savedFilm.setMinAge(film.getMinAge());
        savedFilm.setPriceTechnology(film.getPriceTechnology());
        savedFilm.setTechnology(film.getTechnology());

        return savedFilm;
    }

    public Film saveFilm(final Film film) {
        film.setId(id);
        ++id;
        films.add(film);
        return film;
    }

    public Film getFilmById(final Long id) {
        return films.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ServiceException(400, "film with id '" + id + "' not found"));
    }
}
