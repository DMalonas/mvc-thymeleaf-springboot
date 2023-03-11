package com.udacity.jwdnd.course1.cloudstorage.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credential {
    private Integer credentialId;
    private String url;
    private String userName;
    private String key;
    private String password;
    private Integer userid;

    public Credential(String url, String userName, String key, String password, Integer userid) {
        this.url = url;
        this.userName = userName;
        this.key = key;
        this.password = password;
        this.userid = userid;
    }

    public Credential(Integer credentialId, String url, String userName, String password) {
        this.credentialId = credentialId;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }
}
