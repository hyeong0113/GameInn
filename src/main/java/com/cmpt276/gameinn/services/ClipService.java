package com.cmpt276.gameinn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.cmpt276.gameinn.models.Clip;
import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.repositories.Clip.IClipRepository;

@Service
public class ClipService {
    @Autowired
    private IClipRepository clipRepository;

    public Clip addClip(Clip clip, User user) {
        Clip created = new Clip(clip.getTitle(), clip.getGameTitle(), clip.getTags(), clip.getPlatform(), clip.getSourceLink(), user);
        return clipRepository.save(created);
    }

    public List<Clip> getClips(String query) {
        List<Clip> clips = clipRepository.findAll();

        List<Clip> filteredClips = search(query, clips);

        filteredClips.sort(Comparator.comparing(Clip::getPostedTime).reversed());
        return filteredClips;
    }

    public Clip getClipByID (Long id){
        return clipRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Clip with " + id));
    }

    public Clip updateClip(Long id, Clip clip) throws Exception {
        Clip found = clipRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Clip with " + id));
    
        
        found.setTitle(clip.getTitle());
        found.setGameTitle(clip.getGameTitle());
        found.setTags(clip.getTags());
        found.setPlatform(clip.getPlatform());
        found.setSourceLink(clip.getSourceLink());
        // found.setPostedTime(new Date().getTime());
        found.setPostedTime(found.getPostedTime().getTime());


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

    private List<Clip> search(String query, List<Clip> list) {
        if (query != null && !query.isEmpty()) {
            List<Clip> filteredList = new ArrayList<Clip>();
            for (Clip element : list){
                if (element.getTitle().toLowerCase().contains(query.toLowerCase()) || element.getGameTitle().toLowerCase().contains(query.toLowerCase())){
                    filteredList.add(element);
                }
            }
            return filteredList;
        }
        return list;
    }
}
