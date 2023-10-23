package fr.cyrilcesco.footballdata.initservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InitServiceApplicationTests {

	@Autowired
	private InitController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
