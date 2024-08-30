package com.week6HW.Week_6_Spring_Security_Advanced_HW.service;

import com.week6HW.Week_6_Spring_Security_Advanced_HW.dto.ShowsDto;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.entities.Shows;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.exception.ResourceNotFoundException;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.repository.ShowsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowsService {

	private final ShowsRepository showsRepository;
	private final ModelMapper modelMapper;

	public List<ShowsDto> getShowsByMovieName(String movieName){
		List<Shows> shows = showsRepository.findByMovieNameContainingIgnoreCase(movieName);
		if(shows.isEmpty()){
			throw new ResourceNotFoundException("No Search Found!");
		}
		return shows.stream().map(dto -> modelMapper.map(dto, ShowsDto.class)).collect(Collectors.toList());
	}

	public List<ShowsDto> getAllShows(){
		List<Shows> getAllShows = showsRepository.findAll();
		if(getAllShows.isEmpty()){
			throw new ResourceNotFoundException("No movies are found!!");
		}

		return getAllShows.stream().map(map -> modelMapper.map(map, ShowsDto.class)).collect(Collectors.toList());
	}

	@Transactional
	public ShowsDto createShows(ShowsDto showsDto){
		Optional<Shows> optionalShows = showsRepository.findByMovieNameIgnoreCase(showsDto.getMovieName());

		if(optionalShows.isPresent()){
			throw new ResourceNotFoundException("This movie is already exists!!");
		}

		Shows createdShows = modelMapper.map(showsDto, Shows.class);

		Shows savedShows = showsRepository.save(createdShows);

		return modelMapper.map(savedShows, ShowsDto.class);

	}

	@Transactional
	public ShowsDto updateShows(String showName,ShowsDto showsDto){
		Optional<Shows> optionalShows = showsRepository.findByMovieNameIgnoreCase(showName);

		if(optionalShows.isEmpty()){
			throw new ResourceNotFoundException("This movie doesn't exists!!");
		}

		showsDto.setId(optionalShows.get().getId());
		modelMapper.map(showsDto,optionalShows.get());
		Shows updatedShows = showsRepository.save(optionalShows.get());

		return modelMapper.map(updatedShows, ShowsDto.class);
	}

	@Transactional
	public boolean deleteShows(String movieName){
		if(showsRepository.existsByMovieNameIgnoreCase(movieName)){
			showsRepository.deleteByMovieName(movieName);
			return true;
		}
		throw new ResourceNotFoundException("This movie doesn't exists!!");
	}


}
