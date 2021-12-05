package lpnu.entity;

import lpnu.model.HallSeat;

import java.util.ArrayList;
import java.util.List;

public class Hall{
    private Long id;
    private List<Film> films = new ArrayList<>();
    private HallSeat hallSeat;

    public Hall(){

    }

    public Hall(Long id, List<Film> films, HallSeat hallSeat) {
        this.id = id;
        this.films = films;
        this.hallSeat = hallSeat;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(final List<Film> films) {
        this.films = films;
    }

    public HallSeat getHallSeat() {
        return hallSeat;
    }

    public void setHallSeat(final HallSeat hallSeat) {
        this.hallSeat = hallSeat;
    }

    public void addFilm(final Film film){
        films.add(film);
    }
}
