package echovibe.posts.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PostDTO {
    private String id;
    private String description;
    private List<PostData> like;
    private List<PostData> comment;
    private LocalDate createDate;
    private LocalDate lastUpdateDate = LocalDate.now();
    private Boolean isDeleted = false;
}
