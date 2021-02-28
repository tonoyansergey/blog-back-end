package am.egs.service.impl;

import am.egs.model.TokenStatus;
import am.egs.model.bean.VerificationBean;
import am.egs.model.dto.PasswordResetDTO;
import am.egs.model.entity.PasswordResetTokenEntity;
import am.egs.model.entity.VerificationEntity;
import am.egs.repository.PasswordResetTokenRepository;
import am.egs.repository.UserRepository;
import am.egs.repository.VerificationRepository;
import am.egs.util.exception.NotUniqueCredentialsException;
import am.egs.util.exception.PersistFailureException;
import am.egs.util.exception.RecordNotFoundException;
import am.egs.model.bean.UserBean;
import am.egs.model.dto.EmailDTO;
import am.egs.service.ValidationService;
import am.egs.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@Service
@Validated
public class ValidationServiceImpl implements ValidationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationServiceImpl.class);
    private static final Long EXPIRATION_TIME = 3L;

    private VerificationRepository verificationRepository;
    private PasswordResetTokenRepository passwordResetTokenRepository;
    private UserRepository userRepository;
    private EmailService emailService;

    @Autowired
    public ValidationServiceImpl(final VerificationRepository verificationRepository,
                                 final PasswordResetTokenRepository passwordResetTokenRepository,
                                 final EmailService emailService,
                                 final UserRepository userRepository) {
        this.verificationRepository = verificationRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @Override
    public UserBean validateUserCredentialsAndGenerateEmailVerificationCode(@NotNull final UserBean userBean) {
        LOGGER.info("check existence for username and email");
        checkUserNameAndEmailUniqueness(userBean);

        LOGGER.info("start email verification process");
        emailService.generateVerificationCode(userBean.getEmail());
        return userBean;
    }

    @Override
    public void validateUserByEmailAndGeneratePasswordResetLink(@NotNull final EmailDTO emailDTO) {
        final String email = emailDTO.getEmail();
        if (!isEmailExists(email)) {
            throw new RecordNotFoundException("User not found with specified email");
        }
        emailService.generatePasswordResetLink(email);
    }

    @Override
    public Boolean validatePasswordResetToken(@NotBlank final String token) {
        return passwordResetTokenRepository.getByTokenAndStatusAndCreatedOnIsGreaterThan(token, TokenStatus.ACTIVE, LocalDateTime.now().minusMinutes(EXPIRATION_TIME)).isPresent();
    }

    @Override
    public void verifyEmailVerificationCode(@NotNull final VerificationBean verificationBean) {
        VerificationEntity verificationEntity = verificationRepository.
                findByVerCodeAndEmailAndStatusAndCreatedOnIsGreaterThan(verificationBean.getVerCode(), verificationBean.getEmail(), TokenStatus.ACTIVE, LocalDateTime.now().minusMinutes(EXPIRATION_TIME))
                .orElseThrow(()
                        -> new RecordNotFoundException("not found with specified vCode and email"));

        verificationEntity.setStatus(TokenStatus.INACTIVE);

        Optional.of(verificationRepository.save(verificationEntity)).orElseThrow(() -> new PersistFailureException("error while email verification entity persistence"));
    }

    @Override
    public PasswordResetTokenEntity verifyPasswordResetToken(@NotNull PasswordResetDTO passwordResetDTO) {
        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenRepository.getByTokenAndStatusAndCreatedOnIsGreaterThan(passwordResetDTO.getToken(), TokenStatus.ACTIVE, LocalDateTime.now().minusMinutes(EXPIRATION_TIME))
                .orElseThrow(() -> new RecordNotFoundException("Token is invalid or expired"));

        passwordResetTokenEntity.setStatus(TokenStatus.INACTIVE);
        return Optional.of(passwordResetTokenRepository.save(passwordResetTokenEntity)).orElseThrow(() -> new PersistFailureException("error while password reset token entity persistence"));
    }

    @Override
    public boolean isUserNameExists(@NotBlank final String username) {
        LOGGER.info("check the existence of username: {}", username);
        return userRepository.findOneByUsername(username).isPresent();
    }

    @Override
    public boolean isEmailExists(@NotBlank final String email) {
        LOGGER.info("check the existence of email: {}", email);
        return userRepository.findOneByEmail(email).isPresent();
    }

    private void checkUserNameAndEmailUniqueness(@NotNull final UserBean userBean) {
        final boolean usernameExists = isUserNameExists(userBean.getUsername());
        final boolean emailExists = isEmailExists(userBean.getEmail());

        if (usernameExists || emailExists) {
            throw new NotUniqueCredentialsException(new HashMap<String, Boolean>() {
                {
                    put("username", usernameExists);
                    put("email", emailExists);
                }});
        }
    }
}
