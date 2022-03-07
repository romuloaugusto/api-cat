package api.itau.respository;

import api.itau.entity.Breed;
import api.itau.repository.BreedRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BreedRepositoryTest {

    @Autowired
    private BreedRepository breedRepository;

    @Test
    void givenAnExternalId_WhenCallFindByExternalId_ThenReturnOneRow() {
        String externalId = "abys";
        String name = "Abyssinian";

        Breed breed = breedRepository.findByExternalId(externalId);

        Assertions.assertNotNull(breed);
        Assertions.assertEquals(externalId, breed.getExternalId());
        Assertions.assertEquals(name, breed.getName());
    }

    @Test
    void givenAnName_WhenCallFindByName_ThenReturnAList() {
        String name = "American";

        List<Breed> allByName = breedRepository.findAllByName(name);

        Assertions.assertNotNull(allByName);
        Assertions.assertEquals(4, allByName.size());

        allByName.stream().forEach(
                breed -> Assertions.assertTrue(breed.getName().toLowerCase().contains(name.toLowerCase())));
    }

    @Test
    void givenAnName_WhenCallFindByTemperament_ThenReturnAList() {
        String temperament = "Intelligent";

        List<Breed> allByTemperament = breedRepository.findAllByTemperament(temperament);

        Assertions.assertNotNull(allByTemperament);
        Assertions.assertEquals(46, allByTemperament.size());

        allByTemperament.stream().forEach(
                breed -> Assertions.assertTrue(breed.getTemperament().toLowerCase().contains(temperament.toLowerCase())));
    }

    @Test
    void givenAnName_WhenCallFindByOrigin_ThenReturnAList() {
        String origin = "Canada";

        List<Breed> allByOrigin = breedRepository.findAllByOrigin(origin);

        Assertions.assertNotNull(allByOrigin);
        Assertions.assertEquals(3, allByOrigin.size());

        allByOrigin.stream().forEach(
                breed -> Assertions.assertTrue(breed.getOrigin().toLowerCase().contains(origin.toLowerCase())));
    }


}
