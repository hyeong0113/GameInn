package com.cmpt276.gameinn.repositories.Clip;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cmpt276.gameinn.models.Clip;

public class ClipRepository implements IClipRepositoryCustom {
    @PersistenceContext private EntityManager entityManager;

    public Clip findClipByTitle(String title) {
        Clip found = (Clip)entityManager.find(Clip.class, title);
		return found;
    }
}
