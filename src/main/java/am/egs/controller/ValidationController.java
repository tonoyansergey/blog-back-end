package am.egs.controller;

import am.egs.model.bean.UserBean;
import am.egs.model.dto.EmailDTO;
import am.egs.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
public class ValidationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationController.class);

    private ValidationService validationService;

    @Autowired
    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping("/validation/email")
    public ResponseEntity<?> validateEmailAndSendResetLink(@Valid @RequestBody final EmailDTO emailDTO) {
        validationService.validateUserByEmailAndGeneratePasswordResetLink(emailDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/validation/sign-up")
    public ResponseEntity<UserBean> validateUserSignUp(@Valid @RequestBody final UserBean userBean) {
        return new ResponseEntity<>(validationService.validateUserCredentialsAndGenerateEmailVerificationCode(userBean), HttpStatus.OK);
    }

    @PostMapping("validation/password-reset-token")
    public ResponseEntity<Boolean> validatePasswordResetToken(@RequestBody String token) {
        LOGGER.info("validating password reset token: {}", token);
        return new ResponseEntity<>(validationService.validatePasswordResetToken(token), HttpStatus.OK);
    }
}
