package pe.edu.cibertec.cl2_stephanie_eusebio_garro.dto;

public record FilmDto(Integer filmId,
                      String title,
                      String language,
                      Integer rentalDuration,
                      Double rentalRate) {
}
