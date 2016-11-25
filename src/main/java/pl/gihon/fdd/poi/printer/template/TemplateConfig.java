package pl.gihon.fdd.poi.printer.template;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.util.TemplateModeUtils;

/**
 * Created by luk on 2016-11-11.
 */
@Configuration
public class TemplateConfig {

    @Value("${printer.html.templates.folder}")
    String templateFolder;

    @Bean
    SpringResourceTemplateResolver templateResolver(ApplicationContext appCtx) {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(appCtx);
        resolver.setPrefix("classpath:templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        return resolver;
    }


    @Bean
    SpringResourceTemplateResolver xmlTemplateResolver(ApplicationContext appCtx) {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();

        templateResolver.setApplicationContext(appCtx);

        templateResolver.setPrefix("file:///"+templateFolder);
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);

        return templateResolver;
    }

    @Bean
    SpringTemplateEngine templateEngineForHtml(ApplicationContext appCtx) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(xmlTemplateResolver(appCtx));
        return templateEngine;
    }

    @Bean
    SpringTemplateEngine templateEngine(ApplicationContext appCtx) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(templateResolver(appCtx));
        return templateEngine;
    }

}
