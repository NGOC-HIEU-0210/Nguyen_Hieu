package com.hieu.payload.response;

import com.hieu.embeddable.Support;
import com.hieu.enums.AirlineStatus;
import com.hieu.payload.dto.UserDto;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirlineResponse {

    private Long id;

    private  String iataCode;
    private String icaoCode;

    private String name;
    private String alias;

    private String logoUrl;

    private String website;

    private AirlineStatus status;
    private String alliance;

    private Instant createdAt;
    private Instant updatedAt;

    private Long ownerId;
    private UserDto owner;
    private Long updatedById;

    private CityResponse headquartersCity;
    private Support support;
}
