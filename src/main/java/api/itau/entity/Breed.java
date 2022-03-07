package api.itau.entity;

import api.itau.dto.BreedDto;
import api.itau.dto.ImageDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String externalId;

    private String name;

    private String origin;

    private String temperament;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer rare;

    private Integer healthIssues;

    private String wikipediaUrl;

    private String imageUrl;

    public static Breed buildFromBreedDto(BreedDto breedDto) {
        return Breed.builder()
                .externalId(breedDto.getExternalId())
                .description(breedDto.getDescription())
                .healthIssues(breedDto.getHealthIssues())
                .imageUrl(breedDto.getImage() != null ? breedDto.getImage().getUrl() : null)
                .name(breedDto.getName())
                .origin(breedDto.getOrigin())
                .rare(breedDto.getRare())
                .temperament(breedDto.getTemperament())
                .wikipediaUrl(breedDto.getWikipediaUrl())
                .build();
    }
}
