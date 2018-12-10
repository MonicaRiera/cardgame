package tech.bts.cardgame.util;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.util.Map;

public class HandlebarsUtil {

    private static Handlebars handlebars;

    // static will only be executed once
    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".hbs");
        handlebars = new Handlebars(loader);
    }

    public static String getTemplate(String templateName, Map<String, Object> values) throws IOException {
        return handlebars.compile(templateName).apply(values);
    }
}
