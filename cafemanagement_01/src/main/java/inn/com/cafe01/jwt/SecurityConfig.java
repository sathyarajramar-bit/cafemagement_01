package inn.com.cafe01.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	CustomerUserDetailsService  customerUserDetailsService;
	
	@Autowired
	JwtFilter jwtFilter;
	
	@Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    	auth.userDetailsService(customerUserDetailsService);
	        
	    }
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	@Bean(name=BeanIds.AUTHENTICATION_MANAGER)
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().configurationSource(request->new CorsConfiguration().applyPermitDefaultValues())
				                                     .and()
				                                     .csrf().disable()
				                                     .authorizeHttpRequests()
				                                     .antMatchers("/user/signup","/user/login","/user/forgotpassword")
				                                     .permitAll().anyRequest()
				                                     .authenticated()
				                                     .and().exceptionHandling()
				                                     .and().sessionManagement()
				                                     .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

	
}
