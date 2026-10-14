package com.hieu.location_service.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hieu.embeddable.Address;
import com.hieu.embeddable.GeoCode;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true,nullable = false, length = 3)
    private  String iataCode;

    @Column(nullable = false)
    private String name;



    @Embedded
    private Address address;

    @Embedded
    private GeoCode geoCode;

    @Column(name = "time_zone_id", length = 50)
    private String timeZone;

    @ManyToOne
    @JsonIgnore
    private City city;

    @JsonIgnore
    @Transient
    public String getDetailedName(){
        if(city != null && city.getCountryCode() != null){
            return name.toUpperCase() + "/" + city.getCountryCode() ;
        }
        return name.toUpperCase();
    }
}
