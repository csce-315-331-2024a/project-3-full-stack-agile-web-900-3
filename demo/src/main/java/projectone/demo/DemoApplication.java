package projectone.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import projectone.demo.controller.MenuBoard;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		MenuBoard test = new MenuBoard();
		test.categoryMap.forEach((key, value) -> {
			//System.out.println("Category: " + key);
			//value.forEach(System.out::println);
		});

		SpringApplication.run(DemoApplication.class, args);
	}
	// @RequestMapping("/")
  //       public String index() {
  //           return "index.html";
  //       }


}
