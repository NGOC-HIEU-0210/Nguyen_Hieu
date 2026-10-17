package com.hieu.airline_core_service.model;

import com.hieu.embeddable.Support;
import com.hieu.enums.AirlineStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 2)
    private String iataCode;

    @Column(unique = true, nullable = false, length = 3)
    private String icaoCode;


    @Column(nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private String name;

    @Embedded
    private Support support;

    private String alias;

    @Column(length = 500)
    private String logoUrl;

    private String website;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AirlineStatus status = AirlineStatus.ACTIVE;

    private String alliance;


    private Long headquartersCityId;


    private Long updateById;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;


    @PrePersist
    void applyDefaults() {
        if (status == null) {
            status = AirlineStatus.ACTIVE;
        }
    }
}