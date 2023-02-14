package com.techelevator.dao;

import com.techelevator.model.Profile;

import java.util.List;

public interface ProfileDao {

    public Profile getProfile(String username);

    public List<Profile> getProfiles();

    public int createProfile(Profile profile);

    public void updateProfile(Profile profile);

    public List<String> getPhotosByUser(String username);
}
