package com.portfolio.model;

import java.util.List;

public class CvRequest {
    private CvProfile profile;
    private List<Experience> experiences;

    public CvProfile getProfile() {
        return profile;
    }

    public void setProfile(CvProfile profile) {
        this.profile = profile;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }
}
