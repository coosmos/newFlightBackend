package com.app.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "passengers")
public class Passenger {

    @Id
    private String id;

    private String name;
    private String email;
    private String phone;
}
