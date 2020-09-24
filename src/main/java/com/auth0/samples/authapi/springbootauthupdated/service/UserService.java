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

    /**
     * Method that creates a user
     * @param user user which should be crated
     */
    public ApplicationUser createUser(ApplicationUser user) {
        return applicationUserRepository.saveAndFlush(user);
    }

    /**
     * Deletes user
     * @param id id of the user which should be deleted
     */
    public void deleteUser(long id) {
        applicationUserRepository.deleteById(id);
    }

    /**
     * Updates user
     * @param user user which should be updated
     */
    public ApplicationUser updateUser(ApplicationUser user) {
        ApplicationUser entryFromDB = applicationUserRepository.getOne(user.getId());
        entryFromDB.setUsername(user.getUsername());
        return applicationUserRepository.saveAndFlush(entryFromDB);
    }

    /**
     * Finds all users
     * @return list with all users
     */
    public List<ApplicationUser> findAll() {
        return applicationUserRepository.findAll();
    }
}
