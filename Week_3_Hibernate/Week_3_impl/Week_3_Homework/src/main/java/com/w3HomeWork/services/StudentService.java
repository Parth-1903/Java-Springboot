package com.w3HomeWork.services;

import com.w3HomeWork.Mapper.StudentMapper;
import com.w3HomeWork.dto.ProfessorDto;
import com.w3HomeWork.dto.StudentDto;
import com.w3HomeWork.entities.AdmissionRecord;
import com.w3HomeWork.entities.Professor;
import com.w3HomeWork.entities.Student;
import com.w3HomeWork.entities.Subject;
import com.w3HomeWork.repositories.AdmissionRecordRepository;
import com.w3HomeWork.repositories.StudentRepository;
import com.w3HomeWork.repositories.SubjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

	private final StudentRepository studentRepository;
	private final AdmissionRecordRepository admissionRecordRepository;
	private final StudentMapper studentMapper;

	private final SubjectRepository subjectRepository;

	public List<StudentDto> getAllStudents(){
		return studentMapper.toStudentList(studentRepository.findAll());
	}

	public StudentDto getStudentById(Long studentId){
		boolean flag = studentRepository.existsById(studentId);
		if(flag){
			return studentMapper.toStudentDto(studentRepository.findById(studentId).get());
		}
		return null;
	}

	public StudentDto createStudent(StudentDto studentDto){
		Student student = studentMapper.toEntity(studentDto);
		Optional<AdmissionRecord> optionalAdmissionRecord = admissionRecordRepository.findByClassName(student.getClassName());
		if(optionalAdmissionRecord.isPresent()){
			student.setAdmissionRecord(optionalAdmissionRecord.get());
			optionalAdmissionRecord.get().getStudent().add(student);
			admissionRecordRepository.save(optionalAdmissionRecord.get());
		}
		else{
			return studentMapper.toStudentDto(studentRepository.save(student));
		}
		return studentMapper.toStudentDto(student);
	}

	@Transactional
	public StudentDto assignSubjectToStudent(Long studentId, Long subjectId) {
		Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
		Optional<Student> optionalStudent = studentRepository.findById(studentId);

		if (optionalSubject.isPresent() && optionalStudent.isPresent()) {
			Subject subject = optionalSubject.get();
			Student student = optionalStudent.get();

			if (!subject.getStudents().contains(student)) {
				subject.getStudents().add(student);
			}

			if (!student.getSubjects().contains(subject)) {
				student.getSubjects().add(subject);
			}

			// Save both entities
			Student s = studentRepository.save(student);
			subjectRepository.save(subject);

			return studentMapper.toStudentDto(s);
		}
		return null;
	}

}
