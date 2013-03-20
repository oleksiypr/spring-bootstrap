#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;


@Configuration
@ComponentScan(basePackages = {
	"${package}.dao",
	"${package}.service"})
@ImportResource("classpath:/app-security.xml")
@Import({RepositoryConfig.class})
public class RootConfig {
	@Bean
	public PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer() {
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		ppc.setLocation(new ClassPathResource("db.properties"));
		ppc.setIgnoreUnresolvablePlaceholders(true);
		return ppc;
	}	
}
