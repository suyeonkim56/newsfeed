package com.example.neewfeed.like.service;


import com.example.neewfeed.like.entity.PostLike;
import com.example.neewfeed.like.repository.PostLikeRepository;
import com.example.neewfeed.post.entity.Post;
import com.example.neewfeed.post.repository.PostRepository;
import com.example.neewfeed.user.entity.User;
import com.example.neewfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    public void createPostLike(AuthUser authUser, Long postId) {
        User findUser = userRepository.findById(authUser.getId())
                .orElseThrow(()->new IllegalStateException("존재하지 않는 유저입니다."));
        Post findPost = postRepository.findById(postId)
                .orElseThrow(()-> new IllegalStateException("존재하지 않는 게시물입니다."));

        if(findPost.getUser().equals(findUser)){
            throw new IllegalStateException("본인의 게시물에 좋아요를 남길 수 없습니다.");
        }

        if(postLikeRepository.exists(postLikeRepository.findByUserAndPost(findUser,findPost)))
        {
            throw new IllegalStateException("이미 좋아요가 생성되어 있습니다.");
        }

        PostLike postLike = new PostLike(findUser,findPost);
        postLikeRepository.save(postLike);

        findPost.addLike();
    }

    public void deletePostLike(AuthUser authUser, Long postId, Long likeId) {
        User findUser = userRepository.findById(authUser.getId())
                .orElseThrow(()->new IllegalStateException("존재하지 않는 유저입니다."));
        Post findPost = postRepository.findById(postId)
                .orElseThrow(()-> new IllegalStateException("존재하지 않는 게시물입니다."));
        PostLike postLike = postLikeRepository.findById(likeId)
                .orElseThrow(()->new IllegalStateException("존재하지 않는 좋아요입니다."));

        if(!postLike.getUser().equals(findUser)){
            throw new IllegalStateException("본인의 좋아요만 삭제할 수 있습니다.");
        }

        postLikeRepository.delete(postLike);
        findPost.minusLike();
    }
}
