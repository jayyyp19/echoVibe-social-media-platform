package echovibe.posts.service;

import echovibe.posts.domain.Post;
import echovibe.posts.model.PostData;
import echovibe.posts.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository repository;

    public Post saveEntity(Post dto) {
        return repository.save(dto);
    }

    public Post addComment(PostData dto) {
        Post post = repository.findByIdAndIsDeletedIsFalse(dto.getPostId());
        if (null != post) {
            if (post.getComment() == null) {
                List<PostData> data = new ArrayList<>();
                data.add(dto);
                post.setComment(data);
            } else {
                post.getComment().add(dto);
            }
            post = repository.save(post);
        }
        return post;
    }

    public Post addLike(PostData dto) {
        Post post = repository.findByIdAndIsDeletedIsFalse(dto.getPostId());
        if (null != post) {
            if (post.getLike() == null) {
                List<PostData> data = new ArrayList<>();
                data.add(dto);
                post.setLike(data);
            } else {
                post.getLike().add(dto);
            }
            post = repository.save(post);
        }
        return post;
    }

    public Post updateEntity(Post dto) {
        Post domain = repository.findByIdAndIsDeletedIsFalse(dto.getId());
        if (null != domain) {
            return repository.save(dto);
        }
        return domain;
    }

    public List<Post> getUsers() {
        return repository.findAllByIsDeletedIsFalse();
    }

    public Post deleteEntity(String id) {
        Post domain = repository.findByIdAndIsDeletedIsFalse(id);
        if (null != domain) {
            domain.setIsDeleted(true);
            repository.save(domain);
        }
        return domain;
    }

    public Post getById(String id) {
        return repository.findByIdAndIsDeletedIsFalse(id);
    }
}
