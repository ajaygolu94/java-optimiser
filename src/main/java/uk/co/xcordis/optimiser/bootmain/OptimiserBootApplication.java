package uk.co.xcordis.optimiser.bootmain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

import uk.co.xcordis.optimiser.util.ApplicationConstants;

/**
 * The <code>OptimiserBootApplication</code> class is responsible to start application or provide Spring Boot nature to project in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@SpringBootApplication
@ImportResource({ ApplicationConstants.SERVLET_CONTEXT_XML_FILE_LABEL })
public class OptimiserBootApplication extends SpringBootServletInitializer {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.boot.web.support.SpringBootServletInitializer#configure(org.springframework.boot.builder.SpringApplicationBuilder)
	 */
	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {

		return application.sources(OptimiserBootApplication.class);
	}

	public static void main(final String[] args) throws Exception {

		SpringApplication.run(OptimiserBootApplication.class, args);
	}
}
