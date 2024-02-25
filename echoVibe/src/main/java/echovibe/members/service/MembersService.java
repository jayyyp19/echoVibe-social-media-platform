package echovibe.members.service;
import echovibe.members.domain.Members;
import echovibe.members.repository.MembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembersService {
    @Autowired
    private MembersRepository repository;
    public Members saveEntity(Members dto) {
        return repository.save(dto);
    }

    public Members updateEntity(Members dto) {
            Members domain = repository.findByIdAndIsDeletedIsFalse(dto.getId());
            if(null != domain){
                return repository.save(dto);
            }
            return domain;
    }

    public List<Members> getUsers() {
        return repository.findAllByIsDeletedIsFalse();
    }

    public Members deleteEntity(String id) {
        Members domain = repository.findByIdAndIsDeletedIsFalse(id);
        if(null != domain){
            domain.setIsDeleted(true);
            repository.save(domain);
        }
        return domain;
    }

    public Members getById(String id) {
        return repository.findByIdAndIsDeletedIsFalse(id);
    }
}
