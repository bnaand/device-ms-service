package com.akenza.devicemsdemo.device;

import com.bol.secure.Encrypted;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "devices-collection")
public class DeviceEntity {

    @Id
    private String id;

    @Field
    private String name;

    @Field
    private String description;

    @Field
    @Encrypted
    private String secret;

    @Field
    private Map<String, Object> details;

    @Field
    private String user;

}
