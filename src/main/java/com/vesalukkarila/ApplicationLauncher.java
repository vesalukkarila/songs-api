package com.vesalukkarila;

import com.vesalukkarila.context.ApplicationConfiguration;
import jakarta.servlet.ServletContext;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * The entry point of the Spring application that runs on an embedded Tomcat server.
 * This class initializes and starts the Tomcat server, sets up the Spring
 * application context, and configures the DispatcherServlet to handle
 * incoming requests.
 */
public class ApplicationLauncher {

    /**
     * The main method to launch the application.
     *
     * @param args command-line arguments (not used)
     * @throws LifecycleException if the Tomcat server encounters a lifecycle error during startup
     */
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();
        Context tomcatCtx = tomcat.addContext("", null);

        // Create and configure the Spring application context
        WebApplicationContext appCtx = createApplicationContext(tomcatCtx.getServletContext());

        // Initialize the DispatcherServlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet(appCtx);

        // Add the DispatcherServlet to the Tomcat context
        Wrapper servlet = Tomcat.addServlet(tomcatCtx, "dispatcherServlet", dispatcherServlet);
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/*");

        tomcat.start();
    }

    /**
     * Creates and configures a Spring WebApplicationContext for the application.
     *
     * @param servletContext the servlet context used to create the Spring application context
     * @return the configured WebApplicationContext
     */
    private static WebApplicationContext createApplicationContext(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(ApplicationConfiguration.class);
        ctx.setServletContext(servletContext);
        ctx.refresh();
        return ctx;
    }
}
