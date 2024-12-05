package pe.edu.cibertec.cl2_stephanie_eusebio_garro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.cl2_stephanie_eusebio_garro.dto.FilmCreateDto;
import pe.edu.cibertec.cl2_stephanie_eusebio_garro.dto.FilmDetailDto;
import pe.edu.cibertec.cl2_stephanie_eusebio_garro.dto.FilmDto;
import pe.edu.cibertec.cl2_stephanie_eusebio_garro.entity.Film;
import pe.edu.cibertec.cl2_stephanie_eusebio_garro.entity.Language;
import pe.edu.cibertec.cl2_stephanie_eusebio_garro.repository.*;
import pe.edu.cibertec.cl2_stephanie_eusebio_garro.service.MaintenanceService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {
    @Autowired
    FilmRepository filmRepository;

    @Autowired
    private FilmActorRepository filmActorRepository;

    @Autowired
    private FilmCategoryRepository filmCategoryRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Override
    public List<FilmDto> findAllFilms() {

        List<FilmDto> films = new ArrayList<FilmDto>();
        Iterable<Film> iterable = filmRepository.findAll();
        iterable.forEach(film -> {
            FilmDto filmDto = new FilmDto(film.getFilmId(),
                    film.getTitle(),
                    film.getLanguage().getName(),
                    film.getRentalDuration(),
                    film.getRentalRate());
            films.add(filmDto);
        });
        return films;

    }

    @Override
    public FilmDetailDto findFilmById(int id) {

        Optional<Film> optional = filmRepository.findById(id);
        return optional.map(film -> new FilmDetailDto(film.getFilmId(),
                film.getTitle(),
                film.getDescription(),
                film.getReleaseYear(),
                film.getLanguage().getLanguageId(),
                film.getLanguage().getName(),
                film.getRentalDuration(),
                film.getRentalRate(),
                film.getLength(),
                film.getReplacementCost(),
                film.getRating(),
                film.getSpecialFeatures(),
                film.getLastUpdate())
        ).orElse(null);

    }

    @Override
    public Boolean updateFilm(FilmDetailDto filmDetailDto) {
        Optional<Film> optional = filmRepository.findById(filmDetailDto.filmId());
        return optional.map(
                film -> {
                    film.setTitle(filmDetailDto.title());
                    film.setDescription(filmDetailDto.description());
                    film.setReleaseYear(filmDetailDto.releaseYear());
                    film.setRentalDuration(filmDetailDto.rentalDuration());
                    film.setRentalRate(filmDetailDto.rentalRate());
                    film.setLength(filmDetailDto.length());
                    film.setReplacementCost(filmDetailDto.replacementCost());
                    film.setRating(filmDetailDto.rating());
                    film.setSpecialFeatures(filmDetailDto.specialFeatures());
                    film.setLastUpdate(new Date());
                    filmRepository.save(film);
                    return true;
                }
        ).orElse(false);
    }

    @Override
    public Boolean deleteFilm(int filmId) {
        try {
            // 1. Eliminar relaciones en FilmActor
            filmActorRepository.deleteByFilmId(filmId);

            // 2. Eliminar relaciones en FilmCategory
            filmCategoryRepository.deleteByFilmId(filmId);

            rentalRepository.deleteByFilmId(filmId);

            // 3. Eliminar relaciones en Inventory
            inventoryRepository.deleteByFilmId(filmId);

            // 4. Eliminar la película
            /*filmRepository.deleteById(filmId);*/
            filmRepository.deleteByFilmId(filmId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean createFilm(FilmCreateDto filmCreateDto) {
        try {
            // Crear un nuevo objeto Film a partir del DTO
            Film film = new Film();
            film.setTitle(filmCreateDto.title());
            film.setDescription(filmCreateDto.description());
            film.setReleaseYear(filmCreateDto.releaseYear());

            // Obtener el objeto Language (asumiendo que tienes un repositorio para Language)
            Optional<Language> language = languageRepository.findById(filmCreateDto.languageId());
            if (language.isPresent()) {
                film.setLanguage(language.get());
            } else {
                throw new RuntimeException("Language not found with id " + filmCreateDto.languageId());
            }

            film.setRentalDuration(filmCreateDto.rentalDuration());
            film.setRentalRate(filmCreateDto.rentalRate());
            film.setLength(filmCreateDto.length());
            film.setReplacementCost(filmCreateDto.replacementCost());
            film.setRating(filmCreateDto.rating());
            film.setSpecialFeatures(filmCreateDto.specialFeatures());
            film.setLastUpdate(new Date()); // La fecha se puede establecer en el momento de la creación

            // Guardar la película en la base de datos
            filmRepository.save(film);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
