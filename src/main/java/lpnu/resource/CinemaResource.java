package lpnu.resource;

import lpnu.dto.CinemaDTO;
import lpnu.dto.FilmDTO;
import lpnu.dto.HallDTO;
import lpnu.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1")
@RestController
public class CinemaResource {
    @Autowired
    private CinemaService cinemaService;

    @GetMapping("/cinemas")
    public List<CinemaDTO> getAllCinemas() {
        return cinemaService.getAllCinemas();
    }

    @GetMapping("/cinemas/{id}")
    public CinemaDTO getCinemaById(@PathVariable final Long id) {
        return cinemaService.getCinemaById(id);
    }

    @PostMapping("/cinemas")
    public CinemaDTO saveCinema(@Validated @RequestBody final CinemaDTO cinemaDTO) {
        return cinemaService.saveCinema(cinemaDTO);
    }

    @PutMapping("/cinemas")
    public CinemaDTO updateHall(@Validated @RequestBody final CinemaDTO cinemaDTO) {
        return cinemaService.updateCinema(cinemaDTO);
    }

    @PutMapping("/cinemas/{id}")
    public CinemaDTO addHall(@Validated @RequestBody final HallDTO hallDTO, @PathVariable final Long id){
        return cinemaService.addHall(hallDTO, id);
    }

    @PutMapping("/cinemas/{id}")
    public CinemaDTO addFilm(@Validated @RequestBody final FilmDTO filmDTO, @PathVariable final Long cinemaId, @PathVariable final Long hallId){
        return cinemaService.addFilm(filmDTO, cinemaId, hallId);
    }

    @DeleteMapping("/cinemas/{id}")
    public ResponseEntity deleteHallById(@PathVariable final Long id) {
        cinemaService.deleteCinemaById(id);
        return ResponseEntity.ok().build();
    }
}
