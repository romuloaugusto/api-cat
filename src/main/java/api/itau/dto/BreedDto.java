package api.itau.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreedDto {

    @JsonProperty("id")
    private String externalId;

    private String name;

    private String origin;

    private String temperament;

    private String description;

    private Integer rare;

    @JsonProperty("health_issues")
    private Integer healthIssues;

    @JsonProperty("wikipedia_url")
    private String wikipediaUrl;

    private ImageDto image;

}
