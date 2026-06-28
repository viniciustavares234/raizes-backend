package br.com.raizes.service;

import br.com.raizes.dto.AuthenticationRequest;
import br.com.raizes.dto.AuthenticationResponse;
import br.com.raizes.dto.RefreshTokenRequest;
import br.com.raizes.entity.RefreshToken;
import br.com.raizes.entity.Usuario;
import br.com.raizes.repository.RefreshTokenRepository;
import br.com.raizes.repository.UsuarioRepository;
import br.com.raizes.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final UsuarioRepository usuarioRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getSenha()
                )
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return gerarTokens(usuario);
    }

    @Transactional
    public AuthenticationResponse refresh(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new IllegalArgumentException("Refresh token inválido"));

        if (refreshToken.isRevoked() || refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Refresh token expirado ou revogado");
        }

        Usuario usuario = refreshToken.getUsuario();
        if (!jwtService.isRefreshTokenValid(refreshToken.getToken(), usuario)) {
            throw new IllegalArgumentException("Refresh token inválido");
        }

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
        return gerarTokens(usuario);
    }

    @Transactional
    public void logout(String refreshTokenValue) {
        if (refreshTokenValue == null || refreshTokenValue.isBlank()) {
            return;
        }

        refreshTokenRepository.findByToken(refreshTokenValue)
                .ifPresent(refreshToken -> {
                    refreshToken.setRevoked(true);
                    refreshTokenRepository.save(refreshToken);
                });
    }

    private AuthenticationResponse gerarTokens(Usuario usuario) {
        String accessToken = jwtService.generateToken(usuario);
        String refreshTokenValue = jwtService.generateRefreshToken(usuario);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(refreshTokenValue);
        refreshToken.setUsuario(usuario);
        refreshToken.setExpiresAt(LocalDateTime.now().plusSeconds(jwtService.getRefreshExpiration() / 1000));
        refreshToken.setRevoked(false);
        refreshTokenRepository.save(refreshToken);

        return AuthenticationResponse.builder()
                .token(accessToken)
                .refreshToken(refreshTokenValue)
                .build();
    }
}
