package com.akenza.devicemsdemo.device;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface DeviceRepository extends ReactiveMongoRepository<DeviceEntity, String> {

    Flux<DeviceEntity> findAllByUser(String user);

}
