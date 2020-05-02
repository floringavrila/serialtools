package ro.paha.serialtools.console.config;

import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import ro.paha.serialtools.Connector;
import ro.paha.serialtools.console.shell.InputReader;
import ro.paha.serialtools.console.shell.ShellHelper;

@Configuration
public class SpringShellConfig {

    @Bean
    public ShellHelper shellHelper(@Lazy Terminal terminal) {
        return new ShellHelper(terminal);
    }

    @Bean
    public Connector portConnector() {
        return new Connector();
    }

    @Bean
    public InputReader inputReader(@Lazy LineReader lineReader, ShellHelper shellHelper) {
        return new InputReader(lineReader, shellHelper);
    }
}
