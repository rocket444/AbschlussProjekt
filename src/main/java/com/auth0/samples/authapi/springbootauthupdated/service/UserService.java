package com.auth0.samples.authapi.springbootauthupdated.service;

import com.auth0.samples.authapi.springbootauthupdated.user.ApplicationUser;
import com.auth0.samples.authapi.springbootauthupdated.user.ApplicationUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private ApplicationUserRepository applicationUserRepository;

    public UserService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    public ApplicationUser createUser(ApplicationUser user) {
        return applicationUserRepository.saveAndFlush(user);
    }

    public void deleteUser(long id) {
        applicationUserRepository.deleteById(id);
    }

    public ApplicationUser updateUser(ApplicationUser entry) {
        ApplicationUser entryFromDB = applicationUserRepository.getOne(entry.getId());
        entryFromDB.setUsername(entry.getUsername());
        return applicationUserRepository.saveAndFlush(entryFromDB);
    }

    public List<ApplicationUser> findAll() {
        return applicationUserRepository.findAll();
    }
}
