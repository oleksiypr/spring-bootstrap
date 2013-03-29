#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.config;

import java.util.Set;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;
import org.jboss.resteasy.plugins.spring.SpringContextLoaderListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {
	private static final Logger logger = LoggerFactory.getLogger(WebAppInitializer.class);
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		// Context init parameters
		servletContext.setInitParameter(ContextLoader.CONTEXT_CLASS_PARAM, 
				"org.springframework.web.context.support.AnnotationConfigWebApplicationContext");
		
		servletContext.setInitParameter("resteasy.servlet.mapping.prefix", "/rest");
		servletContext.setInitParameter("defaultHtmlEscape", "true");
		servletContext.setInitParameter(ContextLoader.CONTEXT_INITIALIZER_CLASSES_PARAM, 
				"${package}.config.RootContextInitializer");
		
		// Web-app listeners
		servletContext.addListener(new ResteasyBootstrap());
		servletContext.addListener(new SpringContextLoaderListener());
		servletContext.addListener(new RequestContextListener());
		
		// Filters
		FilterRegistration.Dynamic securityFilterRegistration = servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
		securityFilterRegistration.addMappingForUrlPatterns(null, true, "/*");
		
		FilterRegistration.Dynamic encodingFilterRegistration = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
		encodingFilterRegistration.setInitParameter("encoding", "UTF-8");
		encodingFilterRegistration.setInitParameter("forceEncoding", "true");
		encodingFilterRegistration.addMappingForUrlPatterns(null, true, "/*");
		
		FilterRegistration.Dynamic hibernateFilter = servletContext.addFilter("hibernateFilter", new OpenSessionInViewFilter());
		hibernateFilter.setInitParameter("sessionFactoryBeanName", "sessionFactory");
		hibernateFilter.addMappingForUrlPatterns(null, true, "/*");
		
		// Servlets
		AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
		mvcContext.register(MvcConfiguration.class);

		ServletRegistration.Dynamic appServlet = servletContext.addServlet("appServlet", new DispatcherServlet(mvcContext));
		appServlet.setLoadOnStartup(1);
		Set<String> mappingConflicts = appServlet.addMapping("/");
		handleServletMappingConflicts(appServlet, mappingConflicts);
		
		ServletRegistration.Dynamic restServlet = servletContext.addServlet("resteasy-servlet", new HttpServletDispatcher());
		mappingConflicts = restServlet.addMapping("/rest/*");
		handleServletMappingConflicts(restServlet, mappingConflicts);
	}
	
	private void handleServletMappingConflicts(ServletRegistration.Dynamic servlet, Set<String>mappingConflicts) {
		if (!mappingConflicts.isEmpty()) {
			for (String s : mappingConflicts) {
				logger.error("Mapping conflict: " + s);
			}
			
			throw new IllegalStateException(servlet.getName() + " cannot be mapped to " + servlet.getMappings());
		}
	}		
}
