package lpnu.resource;

import lpnu.dto.FilmDTO;
import lpnu.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1")
@RestController
public class FilmResource {
    @Autowired
    private FilmService filmService;

    @GetMapping("/films")
    public List<FilmDTO> getAllHalls() {
        return filmService.getAllFilms();
    }

    @GetMapping("/films/{id}")
    public FilmDTO getHallById(@PathVariable final Long id) {
        return filmService.getFilmById(id);
    }

    @PostMapping("/films")
    public FilmDTO saveHall(@Validated @RequestBody final FilmDTO filmDTO) {
        return filmService.saveFilm(filmDTO);
    }

    @PutMapping("/films")
    public FilmDTO updateHall(@Validated @RequestBody final FilmDTO filmDTO) {
        return filmService.updateFilm(filmDTO);
    }

    @DeleteMapping("/films/{id}")
    public ResponseEntity deleteHallById(@PathVariable final Long id) {
        filmService.deleteFilmById(id);
        return ResponseEntity.ok().build();
    }
}
