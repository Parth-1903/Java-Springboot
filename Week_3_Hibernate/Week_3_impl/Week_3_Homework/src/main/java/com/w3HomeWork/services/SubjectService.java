package com.w3HomeWork.services;

import com.w3HomeWork.Mapper.SubjectMapper;
import com.w3HomeWork.dto.SubjectDto;
import com.w3HomeWork.entities.Subject;
import com.w3HomeWork.repositories.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

	private final SubjectRepository subjectRepository;
	private final SubjectMapper subjectMapper;

	public SubjectDto getSubjectById(Long id){
		boolean flag = subjectRepository.existsById(id);
		if(flag){
			return subjectMapper.toSubjectDto(subjectRepository.findById(id).get());
		}
		return null;
	}

	public List<SubjectDto> getAllSubject(){
		return subjectMapper.toSubjectList(subjectRepository.findAll());
	}

	public SubjectDto createSubject(SubjectDto subjectDto){
		Subject subject = subjectMapper.toEntity(subjectDto);
		return subjectMapper.toSubjectDto(subjectRepository.save(subject));
	}

}
