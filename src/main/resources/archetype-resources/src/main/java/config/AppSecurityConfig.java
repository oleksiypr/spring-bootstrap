#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.annotation.Jsr250Voter;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.ExpressionBasedFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;


@Configuration
public class AppSecurityConfig {
	@Autowired
	private UserDetailsService authUserDetailService;
	
	
	@Bean
	public FilterChainProxy springSecurityFilterChain() throws Exception {
		SecurityFilterChain chain = new DefaultSecurityFilterChain(new AntPathRequestMatcher("/**"), 
				concurrentSessionFilter(),
                securityContextPersistenceFilter(),
            	logoutFilter(),
                x509AuthenticationFilter(),
                requestCacheAwareFilter(),
                usernamePasswordAuthenticationFilter(),
                securityContextHolderAwareRequestFilter(),
                anonymousAuthenticationFilter(),
                sessionManagementFilter(),
                exceptionTranslationFilter(),
               	filterSecurityInterceptor());

		FilterChainProxy filterChainProxy = new FilterChainProxy(Arrays.<SecurityFilterChain>asList(chain));
        return filterChainProxy;		
	}	
	

	private ConcurrentSessionFilter concurrentSessionFilter() {
		ConcurrentSessionFilter concurrentSessionFilter = new ConcurrentSessionFilter(sessionRegistry());
        concurrentSessionFilter.afterPropertiesSet();
        return concurrentSessionFilter;
	}
	
	
	private Filter securityContextPersistenceFilter() {
		return new SecurityContextPersistenceFilter(httpSessionSecurityContextRepository());
	}
	
	
	private Filter 	logoutFilter() {
		LogoutFilter logoutFilter = new LogoutFilter("/index", new SecurityContextLogoutHandler());
		return logoutFilter;
	}
	
	
	private Filter x509AuthenticationFilter() throws Exception {
		X509AuthenticationFilter x509AuthenticationFilter = new X509AuthenticationFilter();
		x509AuthenticationFilter.setAuthenticationManager(authenticationManager());
		x509AuthenticationFilter.afterPropertiesSet();
		return x509AuthenticationFilter;
	}
	
	
	private Filter requestCacheAwareFilter() {
		return new RequestCacheAwareFilter();
	}
	
	
	private Filter usernamePasswordAuthenticationFilter() throws Exception {
		UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationFilter();
		usernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
		
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setDefaultTargetUrl("/home");
		usernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
		
		AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler("/login?login_error=1");
		usernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);
		
		usernamePasswordAuthenticationFilter.setSessionAuthenticationStrategy(new SessionFixationProtectionStrategy());		
		return usernamePasswordAuthenticationFilter;
	}

		
	private Filter securityContextHolderAwareRequestFilter() {
		return new SecurityContextHolderAwareRequestFilter();
	}
	
	
	private Filter anonymousAuthenticationFilter() {
		AnonymousAuthenticationFilter anonymousAuthenticationFilter = new AnonymousAuthenticationFilter("uniqueAnonimusAuthFilterKey");
		return anonymousAuthenticationFilter ;
	}	
	
	
	private Filter sessionManagementFilter() {
		return new SessionManagementFilter(httpSessionSecurityContextRepository(), concurrentSessionControlStrategy());
	}
	
	
	private Filter exceptionTranslationFilter() {
		ExceptionTranslationFilter exceptionTranslationFilter = new ExceptionTranslationFilter(entryPoint());
        exceptionTranslationFilter.setAccessDeniedHandler(new AccessDeniedHandlerImpl());
        exceptionTranslationFilter.afterPropertiesSet();
        return exceptionTranslationFilter;
	}	
	
	
	private FilterSecurityInterceptor filterSecurityInterceptor() throws Exception {
		FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setAuthenticationManager(authenticationManager());
        filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager());
        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> map = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();        

        map.put(new AntPathRequestMatcher("/"), Arrays.<ConfigAttribute>asList(new SecurityConfig("isAnonymous()")));
        map.put(new AntPathRequestMatcher("/login"), Arrays.<ConfigAttribute>asList(new SecurityConfig("isAnonymous()")));
        map.put(new AntPathRequestMatcher("/index"), Arrays.<ConfigAttribute>asList(new SecurityConfig("isAnonymous()")));
        map.put(new AntPathRequestMatcher("/index.*"), Arrays.<ConfigAttribute>asList(new SecurityConfig("isAnonymous()")));
        map.put(new AntPathRequestMatcher("/**"), Arrays.<ConfigAttribute>asList(new SecurityConfig("isAuthenticated()")));
        
        ExpressionBasedFilterInvocationSecurityMetadataSource ms = new ExpressionBasedFilterInvocationSecurityMetadataSource(map, securityExpressionHandler());
        filterSecurityInterceptor.setSecurityMetadataSource(ms);
        filterSecurityInterceptor.afterPropertiesSet();
        
        return filterSecurityInterceptor;
	}	
	
	
	private SecurityContextRepository httpSessionSecurityContextRepository() {
		return new HttpSessionSecurityContextRepository();
	}
	

	private AuthenticationManager authenticationManager() throws Exception {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(new PlaintextPasswordEncoder());
		authenticationProvider.setUserDetailsService(authUserDetailService);		
		AnonymousAuthenticationProvider anonymousProvider = new AnonymousAuthenticationProvider("uniqueAnonimusAuthFilterKey");		
		ProviderManager authenticationManager =  new ProviderManager(Arrays.<AuthenticationProvider>asList(authenticationProvider, anonymousProvider));        
        return authenticationManager;
	}	
	
	
	private AuthenticationEntryPoint entryPoint() {
		LoginUrlAuthenticationEntryPoint entryPoint = new LoginUrlAuthenticationEntryPoint("/login");
		return entryPoint;
	}
	

	private SecurityExpressionHandler<FilterInvocation> securityExpressionHandler() {
		SecurityExpressionHandler<FilterInvocation> securityExpressionHandler = new DefaultWebSecurityExpressionHandler();
		return securityExpressionHandler;
	}

	
	@SuppressWarnings("rawtypes")
	private  AccessDecisionManager accessDecisionManager() {
		List<AccessDecisionVoter> voters = Arrays.<AccessDecisionVoter>asList(new RoleVoter(), new WebExpressionVoter(), new Jsr250Voter());
        AccessDecisionManager accessDecisionManager = new AffirmativeBased(voters);
		return accessDecisionManager;
	}	

	
	private SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}	
	
	
	private SessionAuthenticationStrategy concurrentSessionControlStrategy() {
		return new ConcurrentSessionControlStrategy(sessionRegistry());
	}
}