package org.diframework.core.crud.facade;

import org.diframework.core.crud.dto.BaseRequestDto;
import org.diframework.core.crud.dto.BaseResponseDto;

import java.util.Collection;

public interface BaseFacade<REQUEST_DTO extends BaseRequestDto, RESPONSE_DTO extends BaseResponseDto> {

    void create(REQUEST_DTO requestDto);
    void update(REQUEST_DTO requestDto, Integer id);
    void delete(Integer id);
    RESPONSE_DTO findById(Integer id);
    Collection<RESPONSE_DTO> findAll();
}
