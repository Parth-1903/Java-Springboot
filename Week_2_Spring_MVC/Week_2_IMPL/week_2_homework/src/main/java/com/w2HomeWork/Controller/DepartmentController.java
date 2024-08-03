package com.w2HomeWork.Controller;

import com.w2HomeWork.DTO.DepartmentDTO;
import com.w2HomeWork.Exception.ApiResponse;
import com.w2HomeWork.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Data
@RestController
@RequestMapping("/v1/department")
public class DepartmentController {

	private final DepartmentService departmentService;


	@GetMapping
	public ApiResponse<List<DepartmentDTO>> getAllDepartment(){
		List<DepartmentDTO> departmentList = departmentService.getAllDepartment();
		return new ApiResponse<>(HttpStatus.OK,"Get all department Successfully retrieved",departmentList);
	}

	@GetMapping("{id}")
	public ApiResponse<DepartmentDTO> getDepartmentById(@PathVariable("id") Long id){
		DepartmentDTO departmentDTO = departmentService.getDepartmentById(id);
		return new ApiResponse<>(HttpStatus.OK,"Get department by id Successfully retrieved",departmentDTO);
	}

	@PostMapping
	public ApiResponse<DepartmentDTO> createDepartment(@RequestBody @Valid DepartmentDTO departmentDTO){
		DepartmentDTO departmentDTO1 = departmentService.createDepartment(departmentDTO);

		return new ApiResponse<>(HttpStatus.CREATED,"Department Created Successfully",departmentDTO1);
	}

	@DeleteMapping("{id}")
	public ApiResponse<Object> deleteDepartment(@PathVariable(name="id") Long id){
		departmentService.deleteDepartmentById(id);
		return new ApiResponse<>(HttpStatus.ACCEPTED,"Successfully Deleted User with ID: " +id);
	}

	@PatchMapping("/{id}")
	public ApiResponse<DepartmentDTO> patchUpdateDepartment(@PathVariable("id") Long id, @RequestBody Map<String, Object> updates){
		DepartmentDTO departmentDTO = departmentService.patchUpdateDepartment(id,updates);
		return new ApiResponse<>(HttpStatus.OK,"Updated some fields successfully",departmentDTO);
	}

	@PutMapping("/{id}")
	public ApiResponse<DepartmentDTO> updateDepartment(@PathVariable("id")Long id, @RequestBody DepartmentDTO departmentDTO){
		DepartmentDTO departmentDTO1 = departmentService.updateDepartmentById(id,departmentDTO);
		return new ApiResponse<>(HttpStatus.OK,"Updated Department successfully",departmentDTO1);
	}
}
