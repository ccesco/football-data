package fr.cyrilcesco.footballdata.initservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InitServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InitServiceApplication.class, args);
	}

}
