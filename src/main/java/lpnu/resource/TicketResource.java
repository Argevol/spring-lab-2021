package lpnu.resource;

import lpnu.dto.TicketDTO;
import lpnu.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TicketResource {
    @Autowired
    private TicketService ticketService;

    @GetMapping("/tickets")
    public List<TicketDTO> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/tickets/{id}")
    public TicketDTO getTicketById(@PathVariable final Long id) {
        return ticketService.getTicketById(id);
    }

    @PostMapping("/halls")
    public TicketDTO saveTicket(@Validated @RequestBody final TicketDTO ticketDTO) {
        return ticketService.saveTicket(ticketDTO);
    }

    @PutMapping("/tickets")
    public TicketDTO updateTicket(@Validated @RequestBody final TicketDTO ticketDTO) {
        return ticketService.updateTicket(ticketDTO);
    }

    @DeleteMapping("/tickets/{id}")
    public ResponseEntity deleteTicketById(@PathVariable final Long id) {
        ticketService.deleteTicketById(id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/tickets-user/{id}")
    public void removeTicketFromUserByTicketId(@PathVariable final Long id){
        ticketService.removeTicketFromUserByTicketId(id);
    }

    @PutMapping("/tickets/{ticketId}/{userId}")
    public TicketDTO addTicketToUserById(@PathVariable final Long ticketId, @PathVariable final Long userId) {
        return ticketService.addTicketToUserById(ticketId, userId);
    }
}
