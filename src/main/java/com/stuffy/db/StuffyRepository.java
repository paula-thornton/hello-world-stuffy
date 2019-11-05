package com.stuffy.db;

import org.springframework.data.repository.CrudRepository;

import com.stuffy.business.Stuffy;

public interface StuffyRepository extends CrudRepository<Stuffy, Integer> {

}
