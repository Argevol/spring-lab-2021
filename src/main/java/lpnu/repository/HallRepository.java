package lpnu.repository;

import lpnu.entity.Film;
import lpnu.entity.Hall;
import lpnu.exception.ServiceException;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HallRepository {
    private List<Hall> halls;
    private long id = 1;

    @PostConstruct
    public void init() {
        halls = new ArrayList<>();
    }

    public List<Hall> getAllHalls() {
        return new ArrayList<>(halls);
    }

    public void deleteHallById(final Long id) {
        for (final Hall hall : halls) {
            if (hall.getId().equals(id)) {
                halls.remove(hall);
                break;
            }
        }
    }

    public Hall updateHall(final Hall hall) {
        final Hall savedHall = getHallById(hall.getId());

        savedHall.setFilms(hall.getFilms());
        savedHall.setHallSeat(hall.getHallSeat());

        return savedHall;
    }

    public Hall saveHall(final Hall hall) {
        hall.setId(id);
        ++id;
        halls.add(hall);
        return hall;
    }

    public Hall getHallById(final Long id) {
        return halls.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ServiceException(400, "hall with id '" + id + "' not found"));
    }

    public Hall addFilm(final Film newFilm, final Long id){
        final Hall savedHall = getHallById(id);

        if(savedHall.getFilms().stream().anyMatch(newFilm::equals)){
            throw new ServiceException(400, "identical films");
        }else{
            savedHall.getFilms().add(newFilm);
        }
        return savedHall;
    }
}
