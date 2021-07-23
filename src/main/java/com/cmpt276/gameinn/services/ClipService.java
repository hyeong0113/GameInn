package com.cmpt276.gameinn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.cmpt276.gameinn.models.Clip;
import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.repositories.Clip.IClipRepository;

@Service
public class ClipService {
    @Autowired
    private IClipRepository clipRepository;

    public Clip addClip(Clip clip, User user) {
        Clip created = new Clip(clip.getTitle(), clip.getGameTitle(), clip.getTags(), clip.getSourceLink(), clip.getPostedTime(), user);
        return clipRepository.save(created);
    }

    public List<Clip> getClips() {
        return clipRepository.findAll();
    }

    public Clip getClipByID (Long id){
        return clipRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Clip with " + id));
    }

    public Clip updateClip(Long id, Clip clip) throws Exception {
        Clip found = clipRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Clip with " + id));
        if (found == null){
            throw new IllegalArgumentException("No Clip with " + clip.getId());
        }

        found.setTitle(clip.getTitle());
        found.setGameTitle(clip.getGameTitle());
        found.setTags(clip.getTags());
        found.setSourceLink(clip.getSourceLink());
        found.setPostedTime(clip.getPostedTime());

        clipRepository.save(found);
        return found;
    }

    public void deleteClip(Long id){
        Clip found = clipRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Clip with " + id));
        if (found == null){
            throw new IllegalArgumentException("No Clip with " + id);
        }

        clipRepository.delete(found);
    }
}
