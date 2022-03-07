package api.itau.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BreedResponseSqsDto {

    // TODO: Adicionar dados de qual tipo de busca foi solicitada e qual filtro utilizado

    private String email;

    private List<BreedDto> breeds;

    private Integer count;

}
