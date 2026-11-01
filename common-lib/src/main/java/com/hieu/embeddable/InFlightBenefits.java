package com.hieu.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InFlightBenefits {

    private Boolean complimentaryMeals = false;
    private Boolean premiumMealChoice = false;
    private Boolean inFlightInternet = false;
    private Boolean inFlightEntertainment = false;
    private Boolean complimentaryBeverages = false;
}
