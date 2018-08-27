package servlet;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class TemplateProcessor {
    private static final String HTML_DIR = "tml";
    private static TemplateProcessor instance = new TemplateProcessor();

    private final Configuration configuration;

    private TemplateProcessor(){
        configuration = new Configuration();
    }

    static TemplateProcessor getInstance(){
        return instance;
    }

    String getPage(String filename, Map<String, Object> data) throws IOException{
        try (Writer stream = new StringWriter()){
            configuration.setDirectoryForTemplateLoading(new File("tml"));
            Template template = configuration.getTemplate(filename);
            template.process(data, stream);
            return stream.toString();
        } catch (TemplateException e){
            throw new IOException(e);
        }
    }
}
