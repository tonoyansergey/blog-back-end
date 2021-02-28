package am.egs.service;

import am.egs.model.bean.UserBean;
import am.egs.model.bean.VerificationBean;
import am.egs.model.dto.EmailDTO;
import am.egs.model.dto.PasswordResetDTO;
import am.egs.model.entity.PasswordResetTokenEntity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface ValidationService {

    UserBean validateUserCredentialsAndGenerateEmailVerificationCode(@NotNull final UserBean userBean);

    void validateUserByEmailAndGeneratePasswordResetLink(@NotNull final EmailDTO emailDTO);

    Boolean validatePasswordResetToken(@NotBlank final String token);

    void verifyEmailVerificationCode(@NotNull final VerificationBean verificationBean);

    PasswordResetTokenEntity verifyPasswordResetToken(@NotNull final PasswordResetDTO passwordResetDTO);

    boolean isUserNameExists(@NotBlank final String username);

    boolean isEmailExists(@NotBlank final String email);

}
