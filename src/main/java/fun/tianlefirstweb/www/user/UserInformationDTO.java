package fun.tianlefirstweb.www.user;
import fun.tianlefirstweb.www.user.enums.Gender;
import fun.tianlefirstweb.www.user.role.ApplicationRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationDTO {

    private Integer id;

    private String username;
    private String avatar;
    private Gender gender;
    private List<ApplicationRole> roles;
}
