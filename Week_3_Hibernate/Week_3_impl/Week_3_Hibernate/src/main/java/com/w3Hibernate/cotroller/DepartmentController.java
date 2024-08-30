package com.w3Hibernate.cotroller;

import com.w3Hibernate.dto.DepartmentDto;
import com.w3Hibernate.services.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/department")
public class DepartmentController {

	private final DepartmentService departmentService;

	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@GetMapping("/{id}")
	public DepartmentDto departmentById(@PathVariable("id")Long id){
		return departmentService.getDepartmentById(id);
	}

	@GetMapping("/all")
	public List<DepartmentDto> getAllDepartment(){
		return departmentService.getAllDepartment();
	}

	@PostMapping
	public DepartmentDto createDepartment(@RequestBody DepartmentDto departmentDto){
		return departmentService.createDepartment(departmentDto);
	}

	@PutMapping("{deptId}/manager/{empId}")
	public DepartmentDto setManager(@PathVariable("deptId")Long deptId, @PathVariable("empId")Long empId) {
		return departmentService.updateDepartment(deptId,empId);
	}

	@PutMapping("{deptId}/worker/{empId}")
	public DepartmentDto setWorkers(@PathVariable("deptId")Long deptId, @PathVariable("empId")Long empId) {
		return departmentService.setWorkersInDepartment(deptId,empId);
	}

}
