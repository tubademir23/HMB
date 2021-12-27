package hmb.com.tr.mulakat.config;

import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import hmb.com.tr.mulakat.user.projection.AppUserProjection;
@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

	@Autowired
	private EntityManager entityManager;

	@Override
	public void configureRepositoryRestConfiguration(
			RepositoryRestConfiguration config, CorsRegistry cors) {
		config.setBasePath("/api");
		config.setRepositoryDetectionStrategy(
				RepositoryDetectionStrategy.RepositoryDetectionStrategies.VISIBILITY);
		config.getProjectionConfiguration()
				.addProjection(AppUserProjection.class);
		config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream()
				.map(e -> e.getJavaType()).collect(Collectors.toList())
				.toArray(new Class[0]));

	}
}
