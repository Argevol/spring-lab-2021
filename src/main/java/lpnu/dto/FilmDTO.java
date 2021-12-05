package lpnu.dto;

import lpnu.model.EnumTechnology;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class FilmDTO {
    private Long id;
    @Min(1)
    @Max(210)
    private int duration;
    @NotBlank
    private String name;
    @Min(0)
    @Max(18)
    private int minAge;
    private EnumTechnology technology;

    public FilmDTO(){

    }

    public FilmDTO(final Long id, final int duration, final String name, final int minAge, final EnumTechnology technology) {
        this.id = id;
        this.duration = duration;
        this.name = name;
        this.minAge = minAge;
        this.technology = technology;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(final int minAge) {
        this.minAge = minAge;
    }

    public EnumTechnology getTechnology() {
        return technology;
    }

    public void setTechnology(final EnumTechnology technology) {
        this.technology = technology;
    }
}
