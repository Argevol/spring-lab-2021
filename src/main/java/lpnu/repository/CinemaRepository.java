package lpnu.repository;


import lpnu.entity.Cinema;
import lpnu.entity.Film;
import lpnu.entity.Hall;
import lpnu.exception.ServiceException;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CinemaRepository {
    private List<Cinema> cinemas;
    private long id = 1;

    @PostConstruct
    public void init() {
        cinemas = new ArrayList<>();
    }

    public List<Cinema> getAllCinemas() {
        return new ArrayList<>(cinemas);
    }

    public void deleteCinemaById(final Long id) {
        for (final Cinema cinema : cinemas) {
            if (cinema.getId().equals(id)) {
                cinemas.remove(cinema);
                break;
            }
        }
    }

    public Cinema updateCinema(final Cinema cinema) {
        final Cinema savedCinema = getCinemaById(cinema.getId());

        savedCinema.setName(cinema.getName());
        savedCinema.setHalls(cinema.getHalls());

        return savedCinema;
    }

    public Cinema saveCinema(final Cinema cinema) {
        cinema.setId(id);
        ++id;
        cinemas.add(cinema);
        return cinema;
    }

    public Cinema getCinemaById(final Long id) {
        return cinemas.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ServiceException(400, "film with id '" + id + "' not found"));
    }

    public Cinema addHall(final Hall newHall, final Long id) {
        final Cinema savedCinema = getCinemaById(id);

        if (savedCinema.getHalls().stream().anyMatch(newHall::equals)) {
            throw new ServiceException(400, "identical films");
        } else {
            savedCinema.getHalls().add(newHall);
        }
        return savedCinema;
    }

    public Cinema addFilm(final Film newFilm, final Long cinemaId, final Long hallId) {
        final Cinema savedCinema = getCinemaById(cinemaId);
        final Hall savedHall = savedCinema.getHalls().get(hallId.intValue() - 1);

        if (savedHall.getFilms().stream().anyMatch(newFilm::equals)) {
            throw new ServiceException(400, "identical films");
        } else {
            savedHall.getFilms().add(newFilm);
        }

        savedCinema.addHall(savedHall);
        return savedCinema;
    }
}
