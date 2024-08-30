package com.w3HomeWork.services;

import com.w3HomeWork.Mapper.AdmissionRecordMapper;
import com.w3HomeWork.dto.AdmissionRecordDto;
import com.w3HomeWork.dto.StudentDto;
import com.w3HomeWork.entities.AdmissionRecord;
import com.w3HomeWork.entities.Student;
import com.w3HomeWork.repositories.AdmissionRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdmissionRecordService {

	private final AdmissionRecordRepository admissionRecordRepository;
	private final AdmissionRecordMapper admissionRecordMapper;

	public List<AdmissionRecordDto> getAdmissionBasedOnClass(){
		List<AdmissionRecord> admissionRecordsList = admissionRecordRepository.findAll();

		return admissionRecordRepository.findAll().stream()
						.collect(Collectors.groupingBy(AdmissionRecord::getClassName))
						.entrySet().stream()
						.map(entry -> {
								AdmissionRecordDto admissionRecordDto = new AdmissionRecordDto();
							AdmissionRecord firstRecord = entry.getValue().get(0);
							admissionRecordDto.setClassName(entry.getKey());
							admissionRecordDto.setId(firstRecord.getId());
							admissionRecordDto.setFees(firstRecord.getFees());

								Set<StudentDto> studentDtoSet = (Set<StudentDto>) entry.getValue().stream()
										.flatMap(admission-> admission.getStudent().stream())
										.map(student -> {
											StudentDto studentDto = new StudentDto();
											studentDto.setId(student.getId());
											studentDto.setStudentName(student.getStudentName());
											studentDto.setClassName(student.getClassName());
											return studentDto;
										})
										.collect(Collectors.toSet());
								admissionRecordDto.setStudents(studentDtoSet);
								return admissionRecordDto;
						})
				.collect(Collectors.toList());
	}

	public AdmissionRecordDto createAdmissionRecord(AdmissionRecordDto admissionRecordDto){
		AdmissionRecord admissionRecord = admissionRecordMapper.toEntity(admissionRecordDto);
		return admissionRecordMapper.toAdmissionRecordDto(admissionRecordRepository.save(admissionRecord));
	}

	public AdmissionRecordDto updateAdmissionRecord(Long id, AdmissionRecordDto admissionRecordDto){

		AdmissionRecord admissionRecord = admissionRecordRepository.findById(id).orElse(null);

		AdmissionRecord admissionRecord1 = admissionRecordMapper.mergeAdmissionRecord(admissionRecordDto,admissionRecord);

		return admissionRecordMapper.toAdmissionRecordDto(admissionRecordRepository.save(admissionRecord1));
	}

	public boolean deleteAdmissionRecord(Long id){
		AdmissionRecord admissionRecord = admissionRecordRepository.findById(id).orElse(null);

		if(Objects.isNull(admissionRecord)){
			return false;
		}

		try {
			admissionRecordRepository.delete(admissionRecord);
			return true; // Successfully deleted AdmissionRecord and associated Students
		} catch (Exception e) {
			// Log the exception or handle it as needed
			return false; // Failed to delete AdmissionRecord or associated Students
		}
	}
}
