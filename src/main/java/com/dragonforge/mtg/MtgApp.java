package com.dragonforge.mtg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.dragonforge.ComponentScanMarker;

@SpringBootApplication(scanBasePackageClasses = {ComponentScanMarker.class})
public class MtgApp {

  public static void main(String[] args) {
    SpringApplication.run(MtgApp.class, args);

  }

}
