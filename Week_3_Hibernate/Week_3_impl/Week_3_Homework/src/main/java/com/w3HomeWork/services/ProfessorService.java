package com.w3HomeWork.services;

import com.w3HomeWork.Mapper.ProfessorMapper;
import com.w3HomeWork.dto.ProfessorDto;
import com.w3HomeWork.entities.Professor;
import com.w3HomeWork.entities.Student;
import com.w3HomeWork.entities.Subject;
import com.w3HomeWork.repositories.ProfessorRepository;
import com.w3HomeWork.repositories.StudentRepository;
import com.w3HomeWork.repositories.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfessorService {

	private final ProfessorRepository professorRepository;
	private final ProfessorMapper professorMapper;

	private final SubjectRepository subjectRepository;

	private final StudentRepository studentRepository;

	public ProfessorDto getProfessorById(Long id){
		boolean flag = professorRepository.existsById(id);
		if(flag){
			return professorMapper.toProfessorDto(professorRepository.findById(id).get());
		}
		return null;
	}

	public List<ProfessorDto> getAllProfessor(){
		return professorMapper.toProfessorList(professorRepository.findAll());
	}

	public ProfessorDto createProfessor(ProfessorDto professorDto){
		Professor professor = professorMapper.toEntity(professorDto);

		return professorMapper.toProfessorDto(professorRepository.save(professor));
	}

	public ProfessorDto assignedSubjectToProfessor(Long professorId, Long subjectId){
		Optional<Professor> optionalProfessor = professorRepository.findById(professorId);
		Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);

		if(optionalProfessor.isPresent() && optionalSubject.isPresent()){
			Professor professor = optionalProfessor.get();
			Subject subject = optionalSubject.get();

			professor.getSubjects().add(subject);
			subject.setProfessor(professor);

			subjectRepository.save(subject);

			return professorMapper.toProfessorDto(professorRepository.save(professor));
		}
		return null;
	}

	public ProfessorDto assignedStudentToProfessor(Long professorId, Long studentId){
		Optional<Professor> optionalProfessor = professorRepository.findById(professorId);
		Optional<Student> optionalStudent = studentRepository.findById(studentId);

		if(optionalProfessor.isPresent() && optionalStudent.isPresent()){
			Professor professor = optionalProfessor.get();
			Student student = optionalStudent.get();

			professor.getStudents().add(student);
			student.getProfessors().add(professor);

			studentRepository.save(student);

			return professorMapper.toProfessorDto(professorRepository.save(professor));
		}
		return null;
	}


}
