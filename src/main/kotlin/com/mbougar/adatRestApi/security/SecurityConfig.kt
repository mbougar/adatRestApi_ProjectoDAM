package com.mbougar.adatRestApi.security

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Autowired
    private lateinit var rsaKeys: RSAKeysProperties

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { auth -> auth
                .requestMatchers("/users/register").permitAll()
                .requestMatchers("/users/login").permitAll()

                .requestMatchers(HttpMethod.GET, "/users/{id}").authenticated()
                .requestMatchers(HttpMethod.GET, "/users").authenticated()
                .requestMatchers(HttpMethod.PUT, "/users/{id}").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/users/{id}").authenticated()

                .requestMatchers(HttpMethod.POST, "/recipes").authenticated()
                .requestMatchers(HttpMethod.PUT, "/recipes/{id}").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/recipes/{id}").authenticated()
                .requestMatchers(HttpMethod.GET, "/recipes").permitAll()
                .requestMatchers(HttpMethod.GET, "/recipes/{id}").permitAll()

                .requestMatchers(HttpMethod.POST, "/comments").authenticated()
                .requestMatchers(HttpMethod.PUT, "/comments/comment/{id}").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/comments/comment/{id}").authenticated()
                .requestMatchers(HttpMethod.GET, "/comments/{recipe_id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/comments").permitAll()

                .requestMatchers(HttpMethod.POST, "/favorites").authenticated()
                .requestMatchers(HttpMethod.GET, "/favorites/{id}").authenticated()

                .anyRequest().authenticated()
            }
            .oauth2ResourceServer { oauth2 -> oauth2.jwt(Customizer.withDefaults()) }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .httpBasic(Customizer.withDefaults())
            .build()
    }

    @Bean
    fun passwordEncoder() : BCryptPasswordEncoder { // Especifico BCrypt porque sino no se detecta el Bean en UsuarioService
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration) : AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }


    @Bean
    fun jwtEncoder(): JwtEncoder {
        val jwk: JWK = RSAKey.Builder(rsaKeys.publicKey).privateKey(rsaKeys.privateKey).build()
        val jwks: JWKSource<SecurityContext> = ImmutableJWKSet(JWKSet(jwk))
        return NimbusJwtEncoder(jwks)
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey).build()
    }
}