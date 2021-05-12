package com.sr.SvetlanaReznikTask.repos;

import java.sql.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sr.SvetlanaReznikTask.beans.Job;

public interface JobRepository extends JpaRepository<Job, Long>
{
	Set<Job> findJobsByEndDate(Date endDate);
	Set<Job> findJobsByEndDateBetween(Date endDate1, Date endDate2);
	boolean existsJobsByDescription(String Description);
}
