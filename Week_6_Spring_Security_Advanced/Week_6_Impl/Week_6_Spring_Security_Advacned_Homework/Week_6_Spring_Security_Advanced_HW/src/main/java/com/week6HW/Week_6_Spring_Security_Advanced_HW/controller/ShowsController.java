package com.week6HW.Week_6_Spring_Security_Advanced_HW.controller;

import com.week6HW.Week_6_Spring_Security_Advanced_HW.dto.ShowsDto;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.dto.UserDto;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.service.ShowsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows")
@RequiredArgsConstructor
public class ShowsController {

	private final ShowsService showsService;

	@GetMapping
	public ResponseEntity<List<ShowsDto>> getAllShows(){
		return ResponseEntity.ok(showsService.getAllShows());
	}

	@GetMapping("/{moviename}")
	public ResponseEntity<List<ShowsDto>> getShowByMovieName(@PathVariable("moviename") String movieName){
		List<ShowsDto> showsDto = showsService.getShowsByMovieName(movieName);
		return ResponseEntity.ok(showsDto);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<ShowsDto> createShow(@RequestBody ShowsDto showsDto){

		ShowsDto savedShow = showsService.createShows(showsDto);

		return ResponseEntity.ok(savedShow);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{moviename}")
	public ResponseEntity<Boolean> deleteByMovieName(@PathVariable("moviename") String movieName){
		return ResponseEntity.ok(showsService.deleteShows(movieName));
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("/{moviename}")
	public ResponseEntity<ShowsDto> updateByMovieName(@PathVariable("moviename") String movieName, @RequestBody ShowsDto showsDto){
		ShowsDto shows = showsService.updateShows(movieName,showsDto);
		return ResponseEntity.ok(shows);
	}

}
