package com.w3HomeWork.Mapper;

import com.w3HomeWork.dto.AdmissionRecordDto;
import com.w3HomeWork.entities.AdmissionRecord;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
		nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AdmissionRecordMapper {

	AdmissionRecord toEntity(AdmissionRecordDto admissionRecordDto);

	AdmissionRecordDto toAdmissionRecordDto(AdmissionRecord admissionRecord);

	AdmissionRecord mergeAdmissionRecord(AdmissionRecordDto admissionRecordDto, @MappingTarget AdmissionRecord admissionRecord);

	List<AdmissionRecordDto> toAdmissionList(List<AdmissionRecord> admissionRecordDtoList);
}
