package kz.iitu.springlab.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Value("${app.owner:unknown}")
    private String owner;

    @GetMapping("/hello")
    public Greeting hello(
            @RequestParam(defaultValue = "world") String name) {

        return new Greeting(
                "Hello, " + name + "!",
                owner,
                LocalDateTime.now()
        );
    }

    @GetMapping("/info")
    public Info info() {

        return new Info(
                owner,
                System.getProperty("java.version"),
                Runtime.getRuntime().availableProcessors()
        );
    }

    @GetMapping("/stats")
    public Stats stats(
            @RequestParam(defaultValue = "1,2,3,4,5") String numbers) {

        double[] values = Arrays.stream(numbers.split(","))
                .map(String::trim)
                .mapToDouble(Double::parseDouble)
                .toArray();

        DoubleSummaryStatistics statistics =
                Arrays.stream(values).summaryStatistics();

        return new Stats(
                statistics.getCount(),
                statistics.getMin(),
                statistics.getMax(),
                statistics.getAverage(),
                statistics.getSum()
        );
    }

    public record Greeting(
            String message,
            String owner,
            LocalDateTime timestamp
    ) {
    }

    public record Info(
            String owner,
            String javaVersion,
            int cpuCores
    ) {
    }

    public record Stats(
            long count,
            double min,
            double max,
            double average,
            double sum
    ) {
    }
}