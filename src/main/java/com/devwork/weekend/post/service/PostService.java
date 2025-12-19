package com.devwork.weekend.post.service;

import com.devwork.weekend.comment.service.CommentService;
import com.devwork.weekend.common.FileManager;
import com.devwork.weekend.follow.service.FollowService;
import com.devwork.weekend.like.service.LikeService;
import com.devwork.weekend.post.domain.Post;
import com.devwork.weekend.post.postDTO.*;
import com.devwork.weekend.post.repository.PostRepository;
import com.devwork.weekend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor  // 필수 멤버변수 를 생성자를 통해 대응
@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentService commentService;
    private final LikeService likeService;
    private final FollowService followService;

    public boolean createPost(WriteDTO writeDTO, long id) {


        String imagePath = FileManager.savaFile(id, writeDTO.getImagePath());

        User user = User.builder()
                        .id(id)
                        .build();

        Post post = Post.builder()
                    .user(user)
                    .contents(writeDTO.getContents())
                    .imagePath(imagePath)
                    .build();
        try {
            postRepository.save(post);
        } catch(DataAccessException e) {
            return false;
        }
        return true;
    }


    public SlicePostDTO getPostList(Pageable pageable, long userId) {

        Slice<Post> slicePosts = postRepository.findAllPost(PageRequest.of(0, 3));

        List<Post> posts = slicePosts.getContent();

        long id = 0;
        if(posts.size() > 0) {
            id = posts.get(posts.size() - 1).getId();
        }

        List<PostListDTO> postList = new ArrayList<>();

        for(Post post : posts) {
            PostListDTO postListDTO = new PostListDTO(post.getId()
                                                    , post.getUser().getId()
                                                    , post.getUser().getName()
                                                    , post.getUser().getProfileImage()
                                                    , post.getContents()
                                                    , post.getImagePath()
                                                    , commentService.getComment3(post.getId())
                                                    , commentService.getCommentCount(post.getId())
                                                    , likeService.getLikeCount(post.getId())
                                                    , likeService.isLikeByPostIdAndUserId(post.getId(), userId)
                                                    , followService.isFollow(userId, post.getUser().getId())
                                                    , followService.isFollow(post.getUser().getId(), userId)
                                                    , post.getCreatedAt()
                                                    , post.getUpdatedAt());
            postList.add(postListDTO);
        }

        SlicePostDTO slicePostDTO = SlicePostDTO.builder()
                .content(postList)
                .hasNext(slicePosts.hasNext())
                .number(slicePosts.getNumber())
                .size(slicePosts.getSize())
                .lastId(id)
                .pageable(slicePosts.getPageable())
                .nextPageable(slicePosts.nextPageable())
                .build();


        return slicePostDTO;
    }

    public SlicePostDTO getNextPostList(Pageable pageable, long lastId, long userId) {
        Slice<Post> slicePosts = postRepository.findAllNextPost(PageRequest.of(0, 3), lastId);
        List<Post> posts = slicePosts.getContent();
        long id = 0;
        if(posts.size() > 0) {
            id = posts.get(posts.size() - 1).getId();
        }
        List<PostListDTO> postList = new ArrayList<>();


        for(Post post : posts) {
            PostListDTO postListDTO = new PostListDTO(post.getId()
                    , post.getUser().getId()
                    , post.getUser().getName()
                    , post.getUser().getProfileImage()
                    , post.getContents()
                    , post.getImagePath()
                    , commentService.getComment3(post.getId())
                    , commentService.getCommentCount(post.getId())
                    , likeService.getLikeCount(post.getId())
                    , likeService.isLikeByPostIdAndUserId(post.getId(), userId)
                    , followService.isFollow(userId, post.getUser().getId())
                    , followService.isFollow(post.getUser().getId(), userId)
                    , post.getCreatedAt()
                    , post.getUpdatedAt());
            postList.add(postListDTO);

        }

        SlicePostDTO slicePostDTO = SlicePostDTO.builder()
                .content(postList)
                .hasNext(slicePosts.hasNext())
                .number(slicePosts.getNumber())
                .size(slicePosts.getSize())
                .lastId(id)
                .pageable(slicePosts.getPageable())
                .nextPageable(slicePosts.nextPageable())
                .build();

        return slicePostDTO;
    }

    public SlicePostDTO getPostListByFollow(Pageable pageable, long userId) {
        List<Long> followList = followService.getFollowList(userId);
        followList.add(userId);
        Slice<Post> slicePosts = postRepository.findAllPostByFollow(PageRequest.of(0, 3), followList);

        List<Post> posts = slicePosts.getContent();

        long id = 0;
        if(posts.size() > 0) {
            id = posts.get(posts.size() - 1).getId();
        }

        List<PostListDTO> postList = new ArrayList<>();

        for(Post post : posts) {
            PostListDTO postListDTO = new PostListDTO(post.getId()
                    , post.getUser().getId()
                    , post.getUser().getName()
                    , post.getUser().getProfileImage()
                    , post.getContents()
                    , post.getImagePath()
                    , commentService.getComment3(post.getId())
                    , commentService.getCommentCount(post.getId())
                    , likeService.getLikeCount(post.getId())
                    , likeService.isLikeByPostIdAndUserId(post.getId(), userId)
                    , followService.isFollow(userId, post.getUser().getId())
                    , followService.isFollow(post.getUser().getId(), userId)
                    , post.getCreatedAt()
                    , post.getUpdatedAt());
            postList.add(postListDTO);
        }

        SlicePostDTO slicePostDTO = SlicePostDTO.builder()
                .content(postList)
                .hasNext(slicePosts.hasNext())
                .number(slicePosts.getNumber())
                .size(slicePosts.getSize())
                .lastId(id)
                .pageable(slicePosts.getPageable())
                .nextPageable(slicePosts.nextPageable())
                .build();

        return slicePostDTO;
    }

    public SlicePostDTO getNextPostListByFollow(Pageable pageable, long lastId, long userId) {
        List<Long> followList = followService.getFollowList(userId);
        followList.add(userId);
        Slice<Post> slicePosts = postRepository.findAllNextPostByFollow(PageRequest.of(0, 3), followList, lastId);

        List<Post> posts = slicePosts.getContent();

        long id = 0;
        if(posts.size() > 0) {
            id = posts.get(posts.size() - 1).getId();
        }

        List<PostListDTO> postList = new ArrayList<>();


        for(Post post : posts) {
            PostListDTO postListDTO = new PostListDTO(post.getId()
                    , post.getUser().getId()
                    , post.getUser().getName()
                    , post.getUser().getProfileImage()
                    , post.getContents()
                    , post.getImagePath()
                    , commentService.getComment3(post.getId())
                    , commentService.getCommentCount(post.getId())
                    , likeService.getLikeCount(post.getId())
                    , likeService.isLikeByPostIdAndUserId(post.getId(), userId)
                    , followService.isFollow(userId, post.getUser().getId())
                    , followService.isFollow(post.getUser().getId(), userId)
                    , post.getCreatedAt()
                    , post.getUpdatedAt());
            postList.add(postListDTO);

        }

        SlicePostDTO slicePostDTO = SlicePostDTO.builder()
                .content(postList)
                .hasNext(slicePosts.hasNext())
                .number(slicePosts.getNumber())
                .size(slicePosts.getSize())
                .lastId(id)
                .pageable(slicePosts.getPageable())
                .nextPageable(slicePosts.nextPageable())
                .build();

        return slicePostDTO;
    }

    public boolean updatePost(PostModifyDTO postModifyDTO, long id) {

        Optional<Post> optionalPost = postRepository.findById(postModifyDTO.getId());

        String imagePath = FileManager.savaFile(id, postModifyDTO.getImagePath());
        Post post = null;
        if(optionalPost.isPresent()) {
            post = optionalPost.get();

            if(imagePath != null && post.getImagePath() != null) {
                FileManager.deleteFile(post.getImagePath());
                
            }

            if(imagePath == null) {
                imagePath = post.getImagePath();
            }

            post = post.toBuilder()
                    .contents(postModifyDTO.getContents())
                    .imagePath(imagePath)
                    .build();
        }
        try{
            postRepository.save(post);
            return true;
        } catch(DataAccessException e) {
            return false;
        }
    }

    public boolean deletePost(long id) {

        Optional<Post> optionalPost = postRepository.findById(id);
        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            postRepository.delete(post);
            return true;
        }

        return false;
    }

    public PostDTO getPost(long id) {

        Optional<Post> optionalPost = postRepository.findById(id);
        PostDTO postDTO = new PostDTO();

        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            postDTO.setId(post.getId());
            postDTO.setUserId(post.getUser().getId());
            postDTO.setContents(post.getContents());
            postDTO.setImagePath(post.getImagePath());
        }
        return postDTO;
    }

    public SlicePostDTO getPostListByLike(Pageable pageable, long userId) {
        List<Long> postIdList = likeService.getLikeList(userId);

        Slice<Post> slicePosts = postRepository.findALlPostByLike(PageRequest.of(0, 3), postIdList);

        List<Post> posts = slicePosts.getContent();

        long id = 0;
        if(posts.size() > 0) {
            id = posts.get(posts.size() - 1).getId();
        }

        List<PostListDTO> postList = new ArrayList<>();

        for(Post post : posts) {
            PostListDTO postListDTO = new PostListDTO(post.getId()
                    , post.getUser().getId()
                    , post.getUser().getName()
                    , post.getUser().getProfileImage()
                    , post.getContents()
                    , post.getImagePath()
                    , commentService.getComment3(post.getId())
                    , commentService.getCommentCount(post.getId())
                    , likeService.getLikeCount(post.getId())
                    , likeService.isLikeByPostIdAndUserId(post.getId(), userId)
                    , followService.isFollow(userId, post.getUser().getId())
                    , followService.isFollow(post.getUser().getId(), userId)
                    , post.getCreatedAt()
                    , post.getUpdatedAt());
            postList.add(postListDTO);
        }

        SlicePostDTO slicePostDTO = SlicePostDTO.builder()
                .content(postList)
                .hasNext(slicePosts.hasNext())
                .number(slicePosts.getNumber())
                .size(slicePosts.getSize())
                .lastId(id)
                .pageable(slicePosts.getPageable())
                .nextPageable(slicePosts.nextPageable())
                .build();

        return slicePostDTO;
    }

    public SlicePostDTO getNextPostListByLike(Pageable pageable, long lastId, long userId) {
        List<Long> postIdList = likeService.getLikeList(userId);

        Slice<Post> slicePosts = postRepository.findALlNextPostByLike(PageRequest.of(0, 3), postIdList, lastId);

        List<Post> posts = slicePosts.getContent();

        long id = 0;
        if(posts.size() > 0) {
            id = posts.get(posts.size() - 1).getId();
        }

        List<PostListDTO> postList = new ArrayList<>();

        for(Post post : posts) {
            PostListDTO postListDTO = new PostListDTO(post.getId()
                    , post.getUser().getId()
                    , post.getUser().getName()
                    , post.getUser().getProfileImage()
                    , post.getContents()
                    , post.getImagePath()
                    , commentService.getComment3(post.getId())
                    , commentService.getCommentCount(post.getId())
                    , likeService.getLikeCount(post.getId())
                    , likeService.isLikeByPostIdAndUserId(post.getId(), userId)
                    , followService.isFollow(userId, post.getUser().getId())
                    , followService.isFollow(post.getUser().getId(), userId)
                    , post.getCreatedAt()
                    , post.getUpdatedAt());
            postList.add(postListDTO);
        }

        SlicePostDTO slicePostDTO = SlicePostDTO.builder()
                .content(postList)
                .hasNext(slicePosts.hasNext())
                .number(slicePosts.getNumber())
                .size(slicePosts.getSize())
                .lastId(id)
                .pageable(slicePosts.getPageable())
                .nextPageable(slicePosts.nextPageable())
                .build();

        return slicePostDTO;
    }
}
