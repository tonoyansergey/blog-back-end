package am.egs.service;

import javax.validation.constraints.NotBlank;

public interface EmailService {

    void generateVerificationCode(@NotBlank final String email);

    void generatePasswordResetLink(@NotBlank final String email);
}
