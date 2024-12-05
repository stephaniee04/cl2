package pe.edu.cibertec.cl2_stephanie_eusebio_garro.service;

import pe.edu.cibertec.cl2_stephanie_eusebio_garro.dto.FilmCreateDto;
import pe.edu.cibertec.cl2_stephanie_eusebio_garro.dto.FilmDetailDto;
import pe.edu.cibertec.cl2_stephanie_eusebio_garro.dto.FilmDto;

import java.util.List;

public interface MaintenanceService {
    List<FilmDto> findAllFilms();

    FilmDetailDto findFilmById(int id);

    Boolean updateFilm(FilmDetailDto filmDetailDto);

    Boolean deleteFilm(int filmId);

    Boolean createFilm(FilmCreateDto filmCreateDto);
}
