package am.egs.controller;

import am.egs.model.bean.UserBean;
import am.egs.model.dto.PasswordResetDTO;
import am.egs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@Validated
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<UserBean> getUserByUserName(@RequestParam("user-name") @NotBlank final String username) {
        return new ResponseEntity<>(userService.getByUsername(username), HttpStatus.OK);
    }

    @PatchMapping("/users/reset/password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody final PasswordResetDTO passwordResetDTO) {
        userService.resetPassword(passwordResetDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
