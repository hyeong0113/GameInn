package com.cmpt276.gameinn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.cmpt276.gameinn.models.*;
import com.cmpt276.gameinn.repositories.Comment.ICommentRepository;


@Service
public class CommentService {
    @Autowired
    private ICommentRepository commentRepository;

    public Comment addComment(Comment comment, User user, GroupFinder groupfinder) {
        Comment created = new Comment(comment.getLevel(), comment.getContent(), user, groupfinder);
        return commentRepository.save(created);
    }

    public List<Comment> getCommentsWithGroupfinderId(GroupFinder groupfinder) {
        List<Comment> comments = commentRepository.findAll();
        List<Comment> filteredComments = new ArrayList<Comment>();

        Long groupfinder_id = groupfinder.getId();
        for(Comment comment : comments) {
            if (groupfinder_id == comment.getGroupFinder().getId()) {
                filteredComments.add(comment);
            }
        }

        filteredComments.sort(Comparator.comparing(Comment::getPostedTime).reversed());

        return filteredComments;
    }

    public Comment updateComment(Long id, Comment comment) throws Exception {
        Comment found = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Comment with " + id));

        found.setLevel(comment.getLevel());
        found.setContent(comment.getContent());

        commentRepository.save(found);
        return found;
    }

    public void deleteComment(Long id){
        Comment found = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Comment with " + id));

        commentRepository.delete(found);
    }
}
