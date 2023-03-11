package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.persistence.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialsService {
    private UtilService utilService;
    private final CredentialsMapper credentialsMapper;

    public CredentialsService(UtilService utilService, CredentialsMapper credentialsMapper) {
        this.utilService = utilService;
        this.credentialsMapper = credentialsMapper;
    }

    public void addCredentials(String url, String credentialUserName, String password) {
        List<String> encryptedData = utilService.getEncryptedData(password);
        credentialsMapper.insert(new Credential(url, credentialUserName, encryptedData.get(0), encryptedData.get(1), utilService.getUserId()));
    }

    public Credential getCredential(Integer credentialId) {
        return credentialsMapper.getCredential(credentialId);
    }

    public List<Credential> getCredentials(Integer id) {
        return credentialsMapper.getCredentialsByUserId(id);
    }

    public void deleteCredential(Integer noteId) {
        credentialsMapper.deleteCredential(noteId);
    }

    public void updateCredential(Integer credentialId, String newUserName, String url, String password) {
        List<String> encryptedData = utilService.getEncryptedData(password);
        credentialsMapper.updateCredential(credentialId, newUserName, url, encryptedData.get(0), encryptedData.get(1));
    }
}
