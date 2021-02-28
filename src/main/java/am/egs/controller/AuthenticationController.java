package am.egs.controller;

import am.egs.util.exception.InvalidUserNameOrPasswordException;
import am.egs.model.dto.AuthenticationRequestDTO;
import am.egs.model.dto.AuthenticationResponseDTO;
import am.egs.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthenticationController(final AuthenticationManager authenticationManager, final UserDetailsService userDetailsService, final JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> createAuthenticationToken (@RequestBody final AuthenticationRequestDTO authenticationDTO) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationDTO.getUsername(),
                            authenticationDTO.getPassword()));
        } catch (BadCredentialsException e) {
            throw new InvalidUserNameOrPasswordException(e.getMessage());
        }

        final String jwt = jwtUtil.generateToken(userDetailsService.loadUserByUsername(authenticationDTO.getUsername()));

        return new ResponseEntity<>(new AuthenticationResponseDTO(jwt), HttpStatus.OK);
    }
}
