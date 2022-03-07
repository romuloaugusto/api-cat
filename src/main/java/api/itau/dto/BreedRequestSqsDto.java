package api.itau.dto;

import api.itau.enumeration.SearchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BreedRequestSqsDto {

    private SearchType searchType;

    private String email;

    private String callback;

    private String filterValue;

}
