package lpnu.service.impl;

import lpnu.dto.TicketDTO;
import lpnu.entity.Hall;
import lpnu.entity.Ticket;
import lpnu.entity.User;
import lpnu.exception.ServiceException;
import lpnu.mapper.CinemaToCinemaDTOMapper;
import lpnu.mapper.FilmToFilmDTOMapper;
import lpnu.mapper.HallToHallDTOMapper;
import lpnu.mapper.TicketToTicketDTOMapper;
import lpnu.repository.HallRepository;
import lpnu.repository.TicketRepository;
import lpnu.repository.UserRepository;
import lpnu.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketToTicketDTOMapper ticketMapper;
    private final CinemaToCinemaDTOMapper cinemaMapper;
    private final HallToHallDTOMapper hallMapper;
    private final FilmToFilmDTOMapper filmMapper;
    private final TicketRepository ticketRepository;
    private final HallRepository hallRepository;
    private final UserRepository userRepository;

    public TicketServiceImpl(final TicketToTicketDTOMapper ticketMapper, final CinemaToCinemaDTOMapper cinemaMapper,
                             final HallToHallDTOMapper hallMapper, final FilmToFilmDTOMapper filmMapper,
                             final TicketRepository ticketRepository, final HallRepository hallRepository,
                             final UserRepository userRepository) {
        this.ticketMapper = ticketMapper;
        this.cinemaMapper = cinemaMapper;
        this.hallMapper = hallMapper;
        this.filmMapper = filmMapper;
        this.ticketRepository = ticketRepository;
        this.hallRepository = hallRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TicketDTO> getAllTickets() {
        return ticketRepository.getAllTickets().stream()
                .map(ticketMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TicketDTO getTicketById(final Long id) {
        return ticketMapper.toDTO(ticketRepository.getTicketById(id));
    }

    @Override
    public void deleteTicketById(final Long id) {
        ticketRepository.getTicketById(id);
        ticketRepository.deleteTicketById(id);
    }

    @Override
    public TicketDTO updateTicket(final TicketDTO ticketDTO) {
        ticketRepository.getTicketById(ticketDTO.getId());
        return ticketMapper.toDTO(ticketRepository.updateTicket(ticketMapper.toEntity(ticketDTO)));
    }

    @Override
    public TicketDTO saveTicket(final TicketDTO ticketDTO) {
        final Ticket ticket = ticketMapper.toEntity(ticketDTO);
        final Hall hall = hallRepository.getHallById(ticket.getHallId());

        if (ticketRepository.getAllTickets().stream().map(e -> e.equals(ticket)).findAny().isPresent())
            throw new ServiceException(400, "ticket is already saved");

        if (ticket.getRow() > hall.getHallSeat().getRows() || ticket.getSit() > hall.getHallSeat().getColumns()) {
            throw new ServiceException(400, "hall doesn't have such sit or/and row");
        }

        double price = ticket.getRow() == hall.getHallSeat().getRows()
                ? hall.getFilms().get(ticket.getFilmId().intValue()).getPriceTechnology() + Ticket.MARK_UP + Ticket.STANDART_PRICE
                : hall.getFilms().get(ticket.getFilmId().intValue()).getPriceTechnology() + Ticket.STANDART_PRICE;

        ticketRepository.saveTicket(ticket);
        return ticketMapper.toDTO(ticket);
    }

    @Override
    public void removeTicketFromUserByTicketId(final Long id) {
        ticketRepository.getTicketById(id);
        ticketRepository.removeTicketFromUserByTicketId(id);
    }

    @Override
    public TicketDTO addTicketToUserById(final Long ticketId, final Long userId) {
        final Ticket ticket = ticketRepository.getTicketById(ticketId);
        final User user = userRepository.getUserById(userId);
        user.getTicketDTOList().add(ticketMapper.toDTO(ticket));
        return ticketMapper.toDTO(ticket);
    }

}
