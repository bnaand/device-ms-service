package com.akenza.devicemsdemo;

import com.akenza.devicemsdemo.device.DeviceEntity;
import com.akenza.devicemsdemo.device.DeviceRepository;
import com.akenza.devicemsdemo.device.DeviceResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = DeviceResource.class)
@Import(TestSecurityConfiguration.class)
public class DeviceResourceTest {

    @MockBean
    DeviceRepository repository;

    @Autowired
    WebTestClient webClient;

    @Test
    @WithMockUser(username = "testuser")
    void testCreateDevice() {
        DeviceEntity device = createTestDevice();

        Mockito.when(repository.save(device)).thenReturn(Mono.just(device));
        webClient.post()
                .uri("/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(device))
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(repository, times(1)).save(device);
    }


    //    @Test
    @WithMockUser(username = "testuser")
    void testSearchDevice() {
        DeviceEntity device = createTestDevice();

        List<DeviceEntity> list = new ArrayList<>();
        list.add(device);

        Flux<DeviceEntity> deviceFlux = Flux.fromIterable(list);

        Mockito
                .when(repository.findAll(Example.of(device)))
                .thenReturn(deviceFlux);

        webClient.post().uri("/devices/search")
                .header(HttpHeaders.ACCEPT, "application/json")
                .body(device, DeviceEntity.class)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(DeviceEntity.class);

        Mockito.verify(repository, times(1)).findAll(Example.of(device));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testDeleteDevice() {
        Mockito
                .when(repository.findById("123"))
                .thenReturn(Mono.justOrEmpty(createTestDevice()));
        Mockito
                .when(repository.deleteById("123"))
                .thenReturn(Mono.empty());
        webClient.delete().uri("/devices/{id}", "123")
                .exchange()
                .expectStatus().isOk();
    }

    private DeviceEntity createTestDevice() {
        DeviceEntity device = new DeviceEntity();
        device.setId("111");
        device.setName("test device");
        device.setDescription("test device description");
        device.setSecret("123");
        device.setUser("testuser");
        return device;
    }

}
