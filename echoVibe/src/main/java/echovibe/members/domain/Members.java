package echovibe.members.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Document(collection = "members")
public class Members {
    @Id
    private String id;
    private String username;
    private String name;
    private String profile_image;
    private String phone_no;
    private String email;
    private String about;
    private String tempVerifyCode;
    private Boolean isVerified = false;
    private LocalDate createDate;
    private LocalDate updateDate = LocalDate.now();
    private Boolean isDeleted = false;
}



