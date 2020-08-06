package com.akenza.devicemsdemo.device;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Slf4j
@RestController
@RequestMapping("/devices")
public class DeviceResource {

    @Autowired
    DeviceRepository repository;

    @PostMapping
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    Mono<DeviceEntity> add(@RequestBody DeviceEntity device, Principal principal) {
        device.setUser(principal.getName());
        return repository.save(device);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    Mono<Void> delete(@PathVariable("id") String id, Principal principal) {
        Optional<DeviceEntity> device = repository.findById(id).blockOptional();
        if (device.isPresent() && device.get().getUser().equals(principal.getName())) {
            return repository.deleteById(id);
        }
        log.warn("device not deleted [id: {}]", id);
        return Mono.create(null);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    Mono<DeviceEntity> update(@PathVariable("id") String id, @RequestBody DeviceEntity devicePayload, Principal principal) {
        Optional<DeviceEntity> deviceOptional = repository.findById(id).blockOptional();
        if (!deviceOptional.isPresent() || !deviceOptional.get().getUser().equals(principal.getName())) {
            log.warn("device not found [id: {}]", id);
            return Mono.justOrEmpty(null);
        }
        DeviceEntity device = deviceOptional.get();
        device.setDescription(devicePayload.getDescription());
        device.setDetails(devicePayload.getDetails());
        device.setName(devicePayload.getName());
        device.setSecret(devicePayload.getSecret());
        return repository.save(device);
    }

    @PostMapping("/search")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    Flux<DeviceEntity> list(@RequestBody DeviceEntity device, Principal principal) {
        device.setUser(principal.getName());
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withMatcher("id", exact())
                .withMatcher("user", exact())
                .withMatcher("name", contains().ignoreCase())
                .withMatcher("description", contains().ignoreCase());
        return repository.findAll(Example.of(device, matcher));
    }

}