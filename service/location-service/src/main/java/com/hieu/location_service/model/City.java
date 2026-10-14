package com.hieu.location_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private  String name;

    @Column(nullable = false, unique = true)
    private  String cityCode;

    @Column(nullable = false)
    private  String countryCode;

    @Column(nullable = false)
    private  String countryName;

    @Size(max = 10)
    private  String regionCode;

    @Column(name = "time_zone_id", length = 500)
    private   String timeZoneId;

}
