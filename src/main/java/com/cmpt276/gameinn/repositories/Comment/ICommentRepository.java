package com.cmpt276.gameinn.repositories.Comment;

import com.cmpt276.gameinn.models.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
}
