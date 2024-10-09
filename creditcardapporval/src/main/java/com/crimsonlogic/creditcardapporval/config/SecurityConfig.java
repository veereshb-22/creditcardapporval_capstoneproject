package com.crimsonlogic.creditcardapporval.config;




//@Configuration
//@EnableWebSecurity
public class SecurityConfig  {
	
/*
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    	   http
	            .csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/api/auth/**").permitAll()
	                .anyRequest().authenticated()
	            );
	        return http.build();
	    }

	    @Bean
	    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
	        AuthenticationManagerBuilder authenticationManagerBuilder =
	                http.getSharedObject(AuthenticationManagerBuilder.class);
	        authenticationManagerBuilder.inMemoryAuthentication()
	            .withUser("user").password("{noop}password").roles("USER")
	            .and()
	            .withUser("admin").password("{noop}admin").roles("ADMIN");
	        return authenticationManagerBuilder.build();
	}
*/
	
	
}
