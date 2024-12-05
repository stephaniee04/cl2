package pe.edu.cibertec.cl2_stephanie_eusebio_garro.dto;

public record FilmCreateDto(String title,
                            String description,
                            Integer releaseYear,
                            Integer languageId,
                            Integer rentalDuration,
                            Double rentalRate,
                            Integer length,
                            Double replacementCost,
                            String rating,
                            String specialFeatures) {
}
