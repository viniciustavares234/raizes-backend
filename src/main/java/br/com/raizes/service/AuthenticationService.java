package br.com.raizes.service;

// import br.com.raizes.dto.AuthenticationRequest;
// import br.com.raizes.dto.AuthenticationResponse;
// import br.com.raizes.dto.RegisterRequest;
// import br.com.raizes.entity.Usuario;
// import br.com.raizes.enums.Role;
// import br.com.raizes.repository.UsuarioRepository;
// import br.com.raizes.security.JwtService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
// @RequiredArgsConstructor
public class AuthenticationService {
    
    // private final UsuarioRepository repository;
    // private final PasswordEncoder passwordEncoder;
    // private final JwtService jwtService;
    // private final AuthenticationManager authenticationManager;

    // public AuthenticationResponse register(RegisterRequest request) {
    //     var user = new Usuario();
    //     user.setEmail(request.getEmail());
    //     user.setSenha(passwordEncoder.encode(request.getSenha()));
    //     user.setRole(request.getRole() != null ? request.getRole() : Role.ROLE_USER);
        
    //     repository.save(user);
    //     var jwtToken = jwtService.generateToken(user);
        
    //     return AuthenticationResponse.builder()
    //             .token(jwtToken)
    //             .build();
    // }

    // public AuthenticationResponse authenticate(AuthenticationRequest request) {
    //     authenticationManager.authenticate(
    //             new UsernamePasswordAuthenticationToken(
    //                     request.getEmail(),
    //                     request.getSenha()
    //             )
    //     );
        
    //     var user = repository.findByEmail(request.getEmail())
    //             .orElseThrow();
                
    //     var jwtToken = jwtService.generateToken(user);
        
    //     return AuthenticationResponse.builder()
    //             .token(jwtToken)
    //             .build();
    // }
}
