package com.myblog7.service.Impl;

import com.myblog7.entity.Comment;
import com.myblog7.entity.Post;
import com.myblog7.exception.ResourceNotFound;
import com.myblog7.payload.CommentDto;
import com.myblog7.repository.CommentRepository;
import com.myblog7.repository.PostRepository;
import com.myblog7.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,
                              ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment=mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with id" + postId)
        );
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        CommentDto dto=mapToDto(savedComment);
        return dto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () ->  new ResourceNotFound("Post not found with id"+postId)
        );
        List<Comment> comments = commentRepository.findBypostId(postId);
        List<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public CommentDto getCommentsById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () ->  new ResourceNotFound("Post not found with id"+postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () ->  new ResourceNotFound("Comment not found with id"+commentId)
        );

        CommentDto commentDto=mapToDto(comment);
        return commentDto;
    }

    @Override
    public List<CommentDto> getAllCommentsById() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () ->  new ResourceNotFound("Post not found with id"+postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () ->  new ResourceNotFound("Comment not found with id"+commentId)
        );

        commentRepository.deleteById(commentId);
    }

    private CommentDto mapToDto(Comment savedComment) {
        CommentDto dto = mapper.map(savedComment, CommentDto.class);
        return dto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);
        return comment;
    }
}
