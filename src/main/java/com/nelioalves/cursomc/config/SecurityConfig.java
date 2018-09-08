package com.nelioalves.cursomc.config;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment env;

	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**"
	};
	
	private static final String[] PUBLIC_MATCHERS_GET = {			
			"/produtos/**",
			"/categorias/**"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{		
		
		//pega os profiles do projeto, se o profile test estiver ativo, significa que eu vou querer acessar o h2
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable(); // esse comando libera o acesso ao h2
		}		
		
		//habilita o corsConfigurationSource (método abaixo) e desabilita o CSRF (contra ataques), pois não estamos armazenando dados na sessão
		http.cors().and().csrf().disable();
		
		// libera a autorização de acesso para as informações do array e para todo o resto exige a autenticação
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.anyRequest().authenticated();
		
		// configuração necessária para assegurar que o backend não vá criar sessão de usuário
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		/* libera acesso para quaisquer tipos de entradas(any cross-origin) de requisição com as configurações mínimas) */
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
