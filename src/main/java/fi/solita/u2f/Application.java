package fi.solita.u2f;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.data.map.repository.config.EnableMapRepositories;

/**
 * Spring boot Application settings.
 * @author markkuko
 */
@SpringBootApplication
@ComponentScan(basePackages = { "fi.solita.u2f", "fi.solita.u2f.controller" })
@EnableMapRepositories
public class Application implements CommandLineRunner {


    @Override
    public void run(String... arg0) {
        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new Application.ExitException();
        }
    }

    public static void main(String[] args) {
        new SpringApplication(Application.class).run(args);
    }

    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }
}