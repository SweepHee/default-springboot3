package com.example.demo.config

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun localFilterChain(http: HttpSecurity): SecurityFilterChain? = http
        .headers { header ->
            header.frameOptions { it.sameOrigin().xssProtection{ } }
        }
        .authorizeHttpRequests {
            it
                .requestMatchers("/master", "/master/**").hasRole("ADMIN")
                .anyRequest().permitAll()
        }
        .formLogin {
            it
                .loginPage("/signin")
                .defaultSuccessUrl("/")
                .successHandler(LoginSuccessHandler())
                .failureHandler(LoginFailureHandler())
        }
        .logout {
            it.logoutRequestMatcher(AntPathRequestMatcher("/logout"))
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
        }
        .sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
        }
        .exceptionHandling {
            it.authenticationEntryPoint(DivideAuthenticationEntryPoint())
        }
        // mock 테스트땜에 추가함
        .cors { it.configurationSource(CorsConfig().corsConfigurationSource()) }
        .csrf { it.disable() }
        .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        }
    }
}

class LoginSuccessHandler : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val referer = request?.getHeader("referer")
        if (referer == "" || referer == null) response?.sendRedirect("/")
        referer!!
        response!!
        val returnUri = request.getParameter("returnUri") ?: ""
        println(returnUri)
        if (returnUri != "") {
            response.sendRedirect("/${returnUri}")
        } else {
            when {
                referer.contains("/master/signin") -> response.sendRedirect("/master")
                referer.contains("/signin") -> response.sendRedirect("/")
                else -> response.sendRedirect("/")
            }
        }
    }
}

class LoginFailureHandler : AuthenticationFailureHandler {
    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {
        val referer = request?.getHeader("referer")
        if (referer == "" || referer == null) response?.sendRedirect("/")
        referer!!
        response!!

        if (referer.contains("/master")) {
            response.sendRedirect("/master/signin?error=1")
        } else {
            response.sendRedirect("/signin?error=1")
        }
    }
}

class DivideAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        val requestURI: String = request?.requestURI ?: ""
        if (requestURI == "") {
            response?.sendRedirect("/")
        } else {
            when {
                requestURI.contains("/master") -> response?.sendRedirect("/master/signin")
                else -> response?.sendRedirect("/signin?warn=1")
            }
        }
    }
}


@Configuration
class CorsConfig {
    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.allowedOriginPatterns = listOf("*")
        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
        config.allowedHeaders = listOf("*")
        config.exposedHeaders = listOf("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return source
    }
}

