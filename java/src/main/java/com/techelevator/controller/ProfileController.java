package com.techelevator.controller;

import com.techelevator.dao.PostDao;
import com.techelevator.dao.ProfileDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Post;
import com.techelevator.model.Profile;
import com.techelevator.model.UpdatePasswordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Profiles;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ProfileController {

    @Autowired
    private ProfileDao dao;

    @Autowired
    private PostDao postDao;

    @RequestMapping(path="/profiles", method = RequestMethod.GET)
    public List<Profile> getProfiles() {
        return dao.getProfiles();
    }

    @RequestMapping(path="/profiles/{username}", method = RequestMethod.GET)
    public Profile getProfile(@PathVariable String username) {
        return dao.getProfile(username);
    }

    @RequestMapping(path="/profiles/create", method = RequestMethod.POST)
    public int createProfile(@RequestBody Profile profile) {
        return dao.createProfile(profile);
    }

    @RequestMapping(path="/profiles/{username}/posts", method = RequestMethod.GET)
    public List<Post> getPostsByUsername(@PathVariable String username) {
        return postDao.getPostsByUsername(username);
    }
    @RequestMapping(path="/profiles/update", method = RequestMethod.PUT)
    public void updateProfile(@RequestBody Profile profile){
        dao.updateProfile(profile);
    }

    @RequestMapping(path="/profiles/{username}/photos", method = RequestMethod.GET)
    public List<String> getPhotosByUser(@PathVariable String username){
        return dao.getPhotosByUser(username);
    }


}
