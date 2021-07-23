package com.cmpt276.gameinn.repositories.Clip;

import com.cmpt276.gameinn.models.Clip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClipRepository extends JpaRepository<Clip, Long>, IClipRepositoryCustom {
    
}
