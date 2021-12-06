package lpnu.resource;

import lpnu.dto.FilmDTO;
import lpnu.dto.HallDTO;
import lpnu.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1")
@RestController
public class HallResource {
    @Autowired
    private HallService hallService;

    @GetMapping("/halls")
    public List<HallDTO> getAllHalls() {
        return hallService.getAllHalls();
    }

    @GetMapping("/halls/{id}")
    public HallDTO getHallById(@PathVariable final Long id) {
        return hallService.getHallById(id);
    }

    @PostMapping("/halls")
    public HallDTO saveHall(@Validated @RequestBody final HallDTO hallDTO) {
        return hallService.saveHall(hallDTO);
    }

    @PutMapping("/halls")
    public HallDTO updateHall(@Validated @RequestBody final HallDTO hallDTO) {
        return hallService.updateHall(hallDTO);
    }

    @PutMapping("/halls-film/{id}")
    public HallDTO addFilm(@Validated @RequestBody final FilmDTO filmDTO, @PathVariable final Long id){
        return hallService.addFilm(filmDTO, id);
    }

    @DeleteMapping("/halls/{id}")
    public ResponseEntity deleteHallById(@PathVariable final Long id) {
        hallService.deleteHallById(id);
        return ResponseEntity.ok().build();
    }
}
