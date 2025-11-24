package com.app.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "airlines")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Airline {

    @Id
    private String id;

    private String airlineCode;
    private String airlineName;
    private String contactNumber;
    private Boolean isActive = true;
}
