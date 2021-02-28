package am.egs.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @NotNull
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String photo;

    @NotBlank
    private String rating;
}
