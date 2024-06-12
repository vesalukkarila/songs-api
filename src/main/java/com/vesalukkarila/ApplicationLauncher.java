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

public class ApplicationLauncher {
    public static void main(String[] args) throws LifecycleException {
        System.out.println("basic project structure implemented");
        /*TODO: implement Tomcat server&servlet container*/
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();
        Context tomcatCtx = tomcat.addContext("", null);// only 1 application

        /*TODO: implement Applicationcontext, link to tomcat's servletcontext*/
        WebApplicationContext appCtx = createApplicationContext(tomcatCtx.getServletContext());

        /*TODO: implement DispatcherServlet*/
        DispatcherServlet dispatcherServlet = new DispatcherServlet(appCtx);

        /*TODO: add dispatcherservlet to tomcat*/
        Wrapper servlet = Tomcat.addServlet(tomcatCtx, "dispatcherServlet", dispatcherServlet);
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/*");

        tomcat.start();
    }

    private static WebApplicationContext createApplicationContext(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(ApplicationConfiguration.class);
        ctx.setServletContext(servletContext);
        ctx.refresh();
        return ctx;
    }
}
