package com.example.restexpmongomvn.domain;

public class Sample
        extends AbstractLinkableEntity {

    private String title;

    public Sample() {
        // 
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
