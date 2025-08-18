package com.algaworks.algasensors.device.management.api.client.factory;

import com.algaworks.algasensors.device.management.api.client.exception.SensorMonitoringClientBadGetwayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RestClientFactory {

    private final RestClient.Builder builder;

    public RestClient temperatureMonitoringRestClient() {
        return  builder.baseUrl("http://localhost:8082")
                .requestFactory(generateClientHttpRequestFactory())
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    throw new SensorMonitoringClientBadGetwayException();
                })
                .build();
    }

    private ClientHttpRequestFactory generateClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        //o valor padrão de produção é de 1 minuto
        requestFactory.setReadTimeout(Duration.ofSeconds(5));//limite de conexão
        requestFactory.setConnectTimeout(Duration.ofSeconds(3));//limite de leitura
        return requestFactory;
    }
}


