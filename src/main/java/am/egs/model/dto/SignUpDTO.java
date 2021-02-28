package am.egs.model.dto;

import am.egs.model.bean.UserBean;
import am.egs.model.bean.VerificationBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDTO {

    @NotNull
    private UserBean userBean;

    @NotNull
    private VerificationBean verificationBean;
}
