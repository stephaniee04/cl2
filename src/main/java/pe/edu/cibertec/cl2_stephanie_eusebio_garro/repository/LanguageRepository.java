package pe.edu.cibertec.cl2_stephanie_eusebio_garro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.cibertec.cl2_stephanie_eusebio_garro.entity.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
}
