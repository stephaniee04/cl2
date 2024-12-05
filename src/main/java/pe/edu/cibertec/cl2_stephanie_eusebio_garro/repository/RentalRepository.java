package pe.edu.cibertec.cl2_stephanie_eusebio_garro.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.cibertec.cl2_stephanie_eusebio_garro.entity.Rental;

@Repository
public interface RentalRepository extends CrudRepository<Rental, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Rental r WHERE r.inventory.id IN (SELECT i.id FROM Inventory i WHERE i.film.id = :filmId)")
    void deleteByFilmId(Integer filmId);
}
