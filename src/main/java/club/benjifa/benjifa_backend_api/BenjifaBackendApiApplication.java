package club.benjifa.benjifa_backend_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BenjifaBackendApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BenjifaBackendApiApplication.class, args);
	}

}
