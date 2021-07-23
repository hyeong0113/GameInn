package com.cmpt276.gameinn.repositories.Clip;

import com.cmpt276.gameinn.models.Clip;

public interface IClipRepositoryCustom {
    Clip findClipByTitle(String title);
}
