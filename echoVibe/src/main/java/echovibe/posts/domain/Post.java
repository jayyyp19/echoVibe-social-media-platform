package echovibe.posts.domain;

import echovibe.members.domain.Members;
import echovibe.posts.model.PostData;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Document(collection = "members")
public class Post {
    @Id
    private String id;
    private String description;
    private String postImage;
    private Members createdBy;
    private List<PostData> like;
    private List<PostData> comment;
    private LocalDate createDate;
    private LocalDate lastUpdateDate = LocalDate.now();
    private Boolean isDeleted = false;
}



