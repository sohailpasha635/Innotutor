package com.sohail.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sohail.main.entity.SavePrincipal;

@Repository
public interface SavePrincipalRepo extends JpaRepository<SavePrincipal, Long>{

}