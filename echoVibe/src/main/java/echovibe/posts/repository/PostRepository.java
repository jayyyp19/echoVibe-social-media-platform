package echovibe.posts.repository;
import echovibe.posts.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    Post findByIdAndIsDeletedIsFalse (String id);

    List<Post> findAllByIsDeletedIsFalse();
}
