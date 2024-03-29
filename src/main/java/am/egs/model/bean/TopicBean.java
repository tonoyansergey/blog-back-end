package am.egs.model.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicBean {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String definition;
}
