package com.example.neewfeed.like.service;

import com.example.neewfeed.comment.entity.Comment;
import com.example.neewfeed.comment.repository.CommentRepository;
import com.example.neewfeed.like.entity.CommentLike;
import com.example.neewfeed.like.repository.CommentLikeRepository;
import com.example.neewfeed.user.entity.User;
import com.example.neewfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void createCommentLike(Long userId, Long commentId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 댓글입니다."));
        if (comment.getUser().equals(findUser)) {
            throw new IllegalStateException("본인의 댓글에는 좋아요를 누를 수 없습니다.");
        }
        if(commentLikeRepository.exists(commentLikeRepository.findByUserAndComment(findUser,comment)))
        {
            throw new IllegalStateException("이미 좋아요가 생성되어 있습니다.");
        }
        CommentLike commentLike = new CommentLike(findUser, comment);
        commentLikeRepository.save(commentLike);
        comment.addLike();
    }

    @Transactional
    public void deleteCommentLike(Long userId, Long commentId, Long likeId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 댓글입니다."));
        CommentLike commentLike = commentLikeRepository.findById(likeId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 좋아요입니다."));

        if (!commentLike.getUser().equals(findUser)) {
            throw new IllegalStateException("본인의 좋아요만 삭제할 수 있습니다.");
        }
        commentLikeRepository.delete(commentLike);
        comment.minusLike();
    }
}
