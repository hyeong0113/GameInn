package com.cmpt276.gameinn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import com.cmpt276.gameinn.models.Clip;
import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.repositories.Clip.IClipRepository;
import com.cmpt276.gameinn.repositories.User.IUserRepository;

@Service
public class ClipService {
    @Autowired
    private IClipRepository clipRepository;

    @Autowired
    private IUserRepository userRepository;

    public Clip addGroupFinder(String title, String gameTitle, List<String> tags, String sourceLink, Date postedTime, Long writerId) {
        User user = userRepository.findById(writerId).orElseThrow(() -> new IllegalArgumentException("No User with " + writerId));
        Clip groupFinder = new Clip(title, gameTitle, tags, sourceLink, postedTime, user);
        return clipRepository.save(groupFinder);
    }

    public List<Clip> getClips() {
        return clipRepository.findAll();
    }

    public Clip getClipByID (Long id){
        return clipRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Clip with " + id));
    }

    public Clip updateGroupFinder(Clip clip) throws Exception {
        Clip found = clipRepository.findById(clip.getId()).orElseThrow(() -> new IllegalArgumentException("No GroupFinder with " + clip.getId()));
        if (found == null){
            throw new IllegalArgumentException("No GroupFinder with " + clip.getId());
        }

        if (clip.getUser().getId() != found.getUser().getId()) {
            throw new Exception("You are not authorized to edit clip.");
        }

        found.setTitle(clip.getTitle());
        found.setGameTitle(clip.getGameTitle());
        found.setTags(clip.getTags());
        found.setSourceLink(clip.getSourceLink());
        found.setPostedTime(clip.getPostedTime());

        clipRepository.save(found);
        return found;
    }

    public void deleteGroupFinder(Long id){
        Clip found = clipRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No GroupFinder with " + id));
        if (found == null){
            throw new IllegalArgumentException("No Clip with " + id);
        }

        clipRepository.delete(found);
    }


}
