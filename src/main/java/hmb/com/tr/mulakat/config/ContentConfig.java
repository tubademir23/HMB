package hmb.com.tr.mulakat.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bitbucket.gt_tech.spring.data.querydsl.value.operators.experimental.QuerydslHttpRequestContextAwareServletFilter;
import org.bitbucket.gt_tech.spring.data.querydsl.value.operators.experimental.QuerydslPredicateArgumentResolverBeanPostProcessor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.querydsl.binding.QuerydslBindingsFactory;
import org.springframework.format.support.DefaultFormattingConversionService;

import hmb.com.tr.mulakat.user.entity.AppUser;
import hmb.com.tr.mulakat.user.entity.Todo;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE)
public class ContentConfig {

	@Bean
	public FilterRegistrationBean querydslHttpRequestContextAwareServletFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(new QuerydslHttpRequestContextAwareServletFilter(
				querydslHttpRequestContextAwareServletFilterMappings()));
		bean.setAsyncSupported(false);
		bean.setEnabled(true);
		bean.setName("querydslHttpRequestContextAwareServletFilter");
		bean.setUrlPatterns(
				Arrays.asList(new String[]{"/api/users", "/api/todos"}));
		// bean.setUrlPatterns(Arrays.asList(new
		// String[]{"/api/ydkKitapSonuc"}));
		bean.setOrder(Ordered.LOWEST_PRECEDENCE);
		return bean;
	}

	private Map<String, Class<?>> querydslHttpRequestContextAwareServletFilterMappings() {
		Map<String, Class<?>> mappings = new HashMap<>();
		mappings.put("/api/users", AppUser.class);
		mappings.put("/api/todos", Todo.class);
		return mappings;
	}

	@Bean
	public QuerydslPredicateArgumentResolverBeanPostProcessor querydslPredicateArgumentResolverBeanPostProcessor(
			QuerydslBindingsFactory factory,
			DefaultFormattingConversionService conversionServiceDelegate) {
		return new QuerydslPredicateArgumentResolverBeanPostProcessor(factory,
				conversionServiceDelegate);
	}
}