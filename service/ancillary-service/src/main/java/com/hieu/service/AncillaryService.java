package com.hieu.service;


import com.hieu.exception.ResourceNotFoundException;
import com.hieu.payload.request.AncillaryRequest;
import com.hieu.payload.response.AncillaryResponse;

import java.util.List;

public interface AncillaryService {

    AncillaryResponse create(Long userId, AncillaryRequest request) throws ResourceNotFoundException;

    AncillaryResponse getById(Long id) throws ResourceNotFoundException;

    List<AncillaryResponse> getAllByAirlineId(Long userId);

    AncillaryResponse update(Long id, AncillaryRequest request) throws ResourceNotFoundException;

    void delete(Long id);




}
