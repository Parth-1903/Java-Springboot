package com.w3HomeWork.controller;

import com.w3HomeWork.dto.AdmissionRecordDto;
import com.w3HomeWork.services.AdmissionRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admission")
@RequiredArgsConstructor
public class AdmissionRecordDtoController {

	private final AdmissionRecordService admissionRecordService;

	@GetMapping("/all")
	public List<AdmissionRecordDto> getAllAdmssionBasedOnClass(){
		return admissionRecordService.getAdmissionBasedOnClass();
	}

	@PostMapping
	public AdmissionRecordDto createAdmission(@RequestBody AdmissionRecordDto admissionRecordDto){
		return admissionRecordService.createAdmissionRecord(admissionRecordDto);
	}

	@PutMapping("/{admissionId}")
	public AdmissionRecordDto updateAdmission(@PathVariable("admissionId")Long admissionId, @RequestBody AdmissionRecordDto admissionRecordDto){
		return admissionRecordService.updateAdmissionRecord(admissionId,admissionRecordDto);
	}

	@DeleteMapping("/{admissionId}")
	public boolean deleteAdmission(@PathVariable("admissionId") Long admissionId){
		return admissionRecordService.deleteAdmissionRecord(admissionId);
	}
}
