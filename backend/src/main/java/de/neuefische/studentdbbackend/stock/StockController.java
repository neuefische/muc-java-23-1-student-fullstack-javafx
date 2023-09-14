package de.neuefische.studentdbbackend.stock;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

@Cacheable(value = "stock", key = "#root.methodName")
@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final WebClient webClient = WebClient.create("https://finnhub.io/api/v1/quote");

    private final String TOKEN = "&token=cdpnvraad3ia8s05f5egcdpnvraad3ia8s05f5f0";
    private final String APPLE = "?symbol=AAPL";
    private final String NVIDIA = "?symbol=NVDA";
    private final String MICROSOFT = "?symbol=MSFT";
    private final String GOOGLE = "?symbol=GOOGL";

    @GetMapping("/apple")
    public Stock getStockPriceApple() {
        StockResponse stockResponse = webClient.get().uri(APPLE + TOKEN)
                .retrieve()
                .bodyToMono(StockResponse.class)
                .block();

        // Create a new Stock record with the "c" attribute as currentPrice
        return new Stock("Apple", stockResponse != null ? stockResponse.c() : 0.0, LocalDateTime.now());
    }

    @GetMapping("/nvidia")
    public Stock getStockPriceNvidia() {
        StockResponse stockResponse = webClient.get().uri(NVIDIA + TOKEN)
                .retrieve()
                .bodyToMono(StockResponse.class)
                .block();

        // Create a new Stock record with the "c" attribute as currentPrice
        return new Stock("Nvidia", stockResponse != null ? stockResponse.c() : 0.0, LocalDateTime.now());
    }

    @GetMapping("/microsoft")
    public Stock getStockPriceMicrosoft() {
        StockResponse stockResponse = webClient.get().uri(MICROSOFT + TOKEN)
                .retrieve()
                .bodyToMono(StockResponse.class)
                .block();

        // Create a new Stock record with the "c" attribute as currentPrice
        return new Stock("Microsoft", stockResponse != null ? stockResponse.c() : 0.0, LocalDateTime.now());
    }

    @GetMapping("/google")
    public Stock getStockPriceGoogle() {
        StockResponse stockResponse = webClient.get().uri(GOOGLE + TOKEN)
                .retrieve()
                .bodyToMono(StockResponse.class)
                .block();

        // Create a new Stock record with the "c" attribute as currentPrice
        return new Stock("Google", stockResponse != null ? stockResponse.c() : 0.0, LocalDateTime.now());
    }

    private record Stock(
            String name,
            double currentPrice,
            LocalDateTime dateTime
    ) {
    }

    private record StockResponse(
            double c,
            double d,
            double dp
    ) {
    }
}