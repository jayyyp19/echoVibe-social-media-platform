package echovibe.members.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MembersDTO {
    private String id;
    private String username;
    private String name;
    private String profile_image;
    private String phone_no;
    private String email;
    private String about;
    private String tempVerifyCode;
    private Boolean isVerified;
    private LocalDate createDate;
    private LocalDate updateDate;
    private Boolean isDeleted = false;
}
