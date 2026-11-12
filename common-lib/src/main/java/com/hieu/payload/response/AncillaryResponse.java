package com.hieu.payload.response;


import com.hieu.domain.metadata.AncillaryMetadata;
import com.hieu.enums.AncillaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AncillaryResponse {
    private Long id;
    private AncillaryType type;
    private String subType;
    private String rfisc;
    private String name;
    private String description;
    private String categoryDisplayName;
    private String categoryIcon;
    private String iconUrl;
    private AncillaryMetadata metadata;
    private List<InsuranceCoverageResponse> coverages;
    private Integer displayOrder;
    private Long airlineId;
}
