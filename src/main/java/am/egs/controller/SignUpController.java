package am.egs.controller;

import am.egs.model.bean.UserBean;
import am.egs.model.bean.VerificationBean;
import am.egs.model.dto.SignUpDTO;
import am.egs.model.dto.UserProfile;
import am.egs.service.UserService;
import am.egs.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@Validated
public class SignUpController {

    private UserService userService;
    private ValidationService validationService;

    @Autowired
    public SignUpController(final UserService userService,
                            final ValidationService validationService) {
        this.userService = userService;
        this.validationService = validationService;
    }

    @PutMapping("/sign-up")
    public ResponseEntity<UserProfile> signUpUser(@Valid @RequestBody final SignUpDTO signUpDTO) {
        final UserBean userBean = signUpDTO.getUserBean();
        final VerificationBean verificationBean = signUpDTO.getVerificationBean();
        verificationBean.setEmail(userBean.getEmail());
        validationService.verifyEmailVerificationCode(verificationBean);
        final UserProfile user = userService.save(userBean);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
