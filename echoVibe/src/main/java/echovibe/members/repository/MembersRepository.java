package echovibe.members.repository;
import echovibe.members.domain.Members;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembersRepository extends MongoRepository<Members, String> {
    Members findByIdAndIsDeletedIsFalse (String id);

    List<Members> findAllByIsDeletedIsFalse();
}
