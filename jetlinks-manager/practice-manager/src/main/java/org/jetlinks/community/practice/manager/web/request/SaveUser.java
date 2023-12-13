package org.jetlinks.community.practice.manager.web.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author Wen-Tao
 * @see
 * @since 2.2
 */
@Getter
@Setter

public class SaveUser implements Serializable {
      private List<String> roleIdList;
      private AddUser user;

    public SaveUser() {

    }
}

@Getter
@Setter
class AddUser {
    private String confirmPassword;
    private String name;
    private String password;
    private List<String> roleIdList;
    private String username;
}