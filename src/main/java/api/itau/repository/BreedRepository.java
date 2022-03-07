package api.itau.repository;

import api.itau.entity.Breed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {

    Breed findByExternalId(String externalId);

    List<Breed> findAllByOrigin(String filterValue);

    @Query(value = "SELECT * FROM breed WHERE name ILIKE %:name% ", nativeQuery = true)
    List<Breed> findAllByName(String name);

    @Query(value = "SELECT * FROM breed WHERE temperament ILIKE %:temperament% ", nativeQuery = true)
    List<Breed> findAllByTemperament(@Param("temperament") String filterValue);

}
