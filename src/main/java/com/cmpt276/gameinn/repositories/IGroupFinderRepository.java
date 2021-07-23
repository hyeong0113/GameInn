package com.cmpt276.gameinn.repositories;

import com.cmpt276.gameinn.models.GroupFinder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGroupFinderRepository extends JpaRepository<GroupFinder, Long>,
    IGroupFinderRepositoryCustom {}

