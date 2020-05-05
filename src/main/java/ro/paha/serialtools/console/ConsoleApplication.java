package ro.paha.serialtools.console;

import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.PromptProvider;

@SpringBootApplication(exclude = {JmxAutoConfiguration.class})
public class ConsoleApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(ConsoleApplication.class, args);
    }

    @Bean
    public PromptProvider myPromptProvider() {
        return () -> new AttributedString("serial-tools:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }
}
