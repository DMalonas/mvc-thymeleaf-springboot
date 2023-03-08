package com.udacity.jwdnd.course1.cloudstorage.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Credential {
    private Integer credentialid;
    private String url;
    private String userName;
    private String key;
    private String password;
    private Integer userid;

}
