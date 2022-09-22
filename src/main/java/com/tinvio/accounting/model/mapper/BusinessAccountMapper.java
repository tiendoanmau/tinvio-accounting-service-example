package com.tinvio.accounting.model.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinvio.accounting.model.dto.BusinessAccount;
import com.tinvio.accounting.model.dto.Flags;
import com.tinvio.accounting.model.entity.FinanceBusinessAccount;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BusinessAccountMapper {

    BusinessAccountMapper INSTANCE = Mappers.getMapper(BusinessAccountMapper.class);

    BusinessAccount convert(FinanceBusinessAccount model);
    FinanceBusinessAccount convert(BusinessAccount model);

    default Map<String, Boolean> map(Flags value) {
        return new ObjectMapper().convertValue(value, Map.class);
    }

    default Flags map(Map<String, Boolean> value) {
        return new ObjectMapper().convertValue(value, Flags.class);
    }
}
