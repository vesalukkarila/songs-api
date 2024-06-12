package com.vesalukkarila.context;

import com.vesalukkarila.ApplicationLauncher;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = ApplicationLauncher.class)
public class ApplicationConfiguration {
}
