package restfullwebservice03;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PasswordEncoder passwordEncoder;

	
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
		
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.
		csrf().disable().//To be able to use POST, PUT, PATCH, DELETE type csrf().disable()
		authorizeRequests().
		//if needed, add antmatchers with the following so it won't ask password for password
		antMatchers("/", "index", "/css/*", "/js/*").
		permitAll().
		antMatchers("/api/*").
		hasRole(ApplicationUserRoles.ADMIN.name()).
		antMatchers(HttpMethod.POST, "/api/*").
		hasAnyAuthority(ApplicationUserPermissions.ADMIN_WRITE.getPermissions()).
		anyRequest().
		authenticated().
		and().
		httpBasic();
		
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails student = User.
									builder().
									username("student1").
									password(passwordEncoder.encode("12345")).
									roles(ApplicationUserRoles.STUDENT.name()).
									build();
		UserDetails admin = User.
								builder().
								username("admin").
								password(passwordEncoder.encode("admin12345")).
								authorities(ApplicationUserRoles.ADMIN.getGrantedAuthorities()).
								//roles(ApplicationUserRoles.ADMIN.name()).
								
								build();
		return new InMemoryUserDetailsManager(student, admin);
		
	}

}

































