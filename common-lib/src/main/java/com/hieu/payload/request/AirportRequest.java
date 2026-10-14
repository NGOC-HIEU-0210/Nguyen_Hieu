package com.hieu.payload.request;

import com.hieu.embeddable.Address;
import com.hieu.embeddable.GeoCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirportRequest {

    @NotBlank(message = "IATA code is mandatory")
    @Size(min = 3, max = 3, message = "IATA code must be exactly e characters")
    private String iataCode;

    @NotBlank(message = "Airport name is mandatory")
    private String name;

    private ZoneId timeZone;

    @Valid
    private Address address;

    @NotNull(message = "City ID is mandatory")
    private Long cityId;

    @Valid
    private GeoCode geoCode;

}
