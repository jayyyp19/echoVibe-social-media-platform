package echovibe.posts.model;

import echovibe.members.model.BasicMember;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PostData {
    private String postId;
    private BasicMember member;
    private String value;
    private LocalDate createDate;
}
