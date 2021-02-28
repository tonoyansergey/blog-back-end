package am.egs.service.impl;

import am.egs.model.entity.PasswordResetTokenEntity;
import am.egs.model.entity.VerificationEntity;
import am.egs.repository.PasswordResetTokenRepository;
import am.egs.repository.VerificationRepository;
import am.egs.service.EmailService;
import am.egs.util.MailSenderClient;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;

@Service
@Validated
@PropertySource("classpath:email.properties")

public class EmailServiceImpl implements EmailService {

    @Value("${VER_SUBJECT}")
    private String VER_SUBJECT;
    @Value("${VER_CONTENT}")
    private String VER_CONTENT;
    @Value("${RESET_SUBJECT}")
    private String RESET_SUBJECT;
    @Value("${RESET_CONTENT}")
    private String RESET_CONTENT;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
    private MailSenderClient mailSender;
    private VerificationRepository verificationRepository;
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    public EmailServiceImpl(final MailSenderClient mailSender, final VerificationRepository verificationRepository, final PasswordResetTokenRepository tokenRepository) {
        this.mailSender = mailSender;
        this.verificationRepository = verificationRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void generateVerificationCode(@NotBlank final String email) {
        final String vCode = RandomStringUtils.random(10, true, true);
        verificationRepository.save(new VerificationEntity(vCode, email));
        new Thread(() -> mailSender.sendMail(email, VER_CONTENT + "\n" + vCode, VER_SUBJECT)).start();
    }

    @Override
    public void generatePasswordResetLink(@NotBlank final String email) {
        final String token = RandomStringUtils.random(64, true, true);
        final String passResetLink = "http://localhost:4200/reset-password?token=" + token;
        tokenRepository.save(new PasswordResetTokenEntity(token, email));
        new Thread(() -> mailSender.sendMail(email, RESET_CONTENT + "\n" + passResetLink, RESET_SUBJECT)).start();
    }
}
