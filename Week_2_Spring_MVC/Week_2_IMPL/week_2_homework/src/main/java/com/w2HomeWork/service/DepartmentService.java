package com.w2HomeWork.service;

import com.w2HomeWork.DTO.DepartmentDTO;
import com.w2HomeWork.Exception.ResourceNotFoundException;
import com.w2HomeWork.Repository.DepartmentRepository;
import com.w2HomeWork.entity.Department;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Service
public class DepartmentService {

	private final DepartmentRepository departmentRepository;

	private final ModelMapper modelMapper;

	public DepartmentService(DepartmentRepository departmentRepository, ModelMapper modelmapper){
		this.departmentRepository = departmentRepository;
		this.modelMapper = modelmapper;
	}

	public List<DepartmentDTO> getAllDepartment(){
		List<Department> departmentList = departmentRepository.findAll();
		return departmentList.stream().map(entity -> modelMapper.map(entity, DepartmentDTO.class)).collect(Collectors.toList());
	}

	public DepartmentDTO getDepartmentById(Long id){
		this.existById(id);
		Department department = departmentRepository.findById(id).get();
		return modelMapper.map(department,DepartmentDTO.class);
	}
	public DepartmentDTO createDepartment(DepartmentDTO departmentDTO){
		Department department = modelMapper.map(departmentDTO, Department.class);

		department = departmentRepository.save(department);

		return modelMapper.map(department,DepartmentDTO.class);
	}

	public boolean existById(Long id){
		boolean flag = departmentRepository.existsById(id);
		if(!flag){
			throw new ResourceNotFoundException("Employee Not Found with id: "+id);
		}
		return flag;
	}

	public void deleteDepartmentById(Long id){
		boolean flag = this.existById(id);
		if(flag){
			departmentRepository.deleteById(id);
		}
	}

	public DepartmentDTO updateDepartmentById(Long id, DepartmentDTO departmentDTO){
		this.existById(id);
		Department department = modelMapper.map(departmentDTO,Department.class);
		department.setId(id);
		department.setCreatedAt(LocalDateTime.now());
		Department department1 = departmentRepository.save(department);
		return modelMapper.map(department1,DepartmentDTO.class);
	}

	public DepartmentDTO patchUpdateDepartment(Long id, Map<String,Object> updates){
		boolean flag = this.existById(id);
		Department department;
		if(flag){
			department = departmentRepository.findById(id).get();
			updates.forEach((key,value) -> {
				Field field = ReflectionUtils.findRequiredField(Department.class, key);
				field.setAccessible(true);
				ReflectionUtils.setField(field,department,value);
			});
		} else {
			department = null;
		}
		return modelMapper.map(departmentRepository.save(department),DepartmentDTO.class);
	}
}
