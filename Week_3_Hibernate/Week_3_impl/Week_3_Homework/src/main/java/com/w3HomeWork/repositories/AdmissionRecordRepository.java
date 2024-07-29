package com.w3HomeWork.repositories;

import com.w3HomeWork.entities.AdmissionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdmissionRecordRepository extends JpaRepository<AdmissionRecord,Long> {

//	@Query(value = "SELECT ar.class_name AS className, COUNT(ar.id) as count FROM admission ar GROUP BY ar.class_name",nativeQuery = true)
//	List<AdmissionRecord> findGroupedByClassName();

	Optional<AdmissionRecord> findByClassName(Integer className);
}
