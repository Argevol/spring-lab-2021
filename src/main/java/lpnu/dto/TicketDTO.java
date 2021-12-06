package lpnu.dto;

import javax.validation.constraints.Min;

public class TicketDTO {
    private static final double MARK_UP = 30;
    private static final double STANDART_PRICE = 110;
    private Long id;
    @Min(80)
    private double price;
    @Min(1)
    private int sit;
    @Min(1)
    private int row;
    @Min(1)
    private Long cinemaId;
    @Min(1)
    private Long hallId;
    @Min(1)
    private Long filmId;

    public TicketDTO() {

    }

    public TicketDTO(final Long id, final int sit, final int row, final Long cinemaId, final Long hallId, final Long filmId) {
        this.id = id;
        this.sit = sit;
        this.row = row;
        this.cinemaId = cinemaId;
        this.hallId = hallId;
        this.filmId = filmId;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public int getSit() {
        return sit;
    }

    public void setSit(final int sit) {
        this.sit = sit;
    }

    public int getRow() {
        return row;
    }

    public void setRow(final int row) {
        this.row = row;
    }

    public Long getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(final Long cinemaId) {
        this.cinemaId = cinemaId;
    }

    public Long getHallId() {
        return hallId;
    }

    public void setHallId(final Long hallId) {
        this.hallId = hallId;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(final Long filmId) {
        this.filmId = filmId;
    }
}