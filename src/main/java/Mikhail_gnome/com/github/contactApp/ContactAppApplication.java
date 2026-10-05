package Mikhail_gnome.com.github.contactApp;

import Mikhail_gnome.com.github.contactApp.config.database.DatabaseInitializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ContactAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDatabase (DatabaseInitializer dbInitializer) {
		return  new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				dbInitializer.init();
			}
		};
	}

}
