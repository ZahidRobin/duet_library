package com.rrp.duetlibrary;

public class AddBookClass {
    public String ssbn,bookName,authorName,language,edition,numberOfCopy,uri;

    public AddBookClass(){

    }

    public AddBookClass(String ssbn, String bookName, String authorName, String language, String edition, String numberOfCopy, String uri) {
        this.ssbn = ssbn;
        this.bookName = bookName;
        this.authorName = authorName;
        this.language = language;
        this.edition = edition;
        this.numberOfCopy = numberOfCopy;
        this.uri = uri;
    }

    public String getSsbn() {
        return ssbn;
    }

    public void setSsbn(String ssbn) {
        this.ssbn = ssbn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getNumberOfCopy() {
        return numberOfCopy;
    }

    public void setNumberOfCopy(String numberOfCopy) {
        this.numberOfCopy = numberOfCopy;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
