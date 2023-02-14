package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Profile {

    @JsonProperty("profile_id")
    private int profileId;
    @JsonProperty("user_id")
    private int userId;
    private String email;
    private String name;
    @JsonProperty("profile_image")
    private String profileImage;
    @JsonProperty("profile_bio")
    private String profileBio;

    public Profile(int profileId, int userId, String email, String name, String profileImage, String profileBio) {
        this.profileId = profileId;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
        this.profileBio = profileBio;
    }

    public Profile() {

    }

    public String getProfileBio() {
        return profileBio;
    }

    public void setProfileBio(String profileBio) {
        this.profileBio = profileBio;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
