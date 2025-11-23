package com.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "airlines")
@AllArgsConstructor
@NoArgsConstructor
public class Airline {

    @Id
    private String id;

    private String airlineCode;
    private String airlineName;
    private String contactNumber;
    private Boolean isActive = true;
}
