package pe.edu.cibertec.cl2_stephanie_eusebio_garro.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.cibertec.cl2_stephanie_eusebio_garro.entity.Film;

@Repository
public interface FilmRepository extends CrudRepository<Film, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Film f WHERE f.id = :filmId")
    void deleteByFilmId(Integer filmId);

   // @Cacheable(value ="films")
   // Iterable<Film> findAll();

    @CacheEvict(value = "films", allEntries = true)
    Film save(Film film);

}
