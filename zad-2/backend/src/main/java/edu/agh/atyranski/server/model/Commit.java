package edu.agh.atyranski.server.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class Commit {

    private String author;

    private LocalDateTime creationDate;

    private List<Language> languages;

    private String sha;
    private String url;

    private String message;

    private String repositoryFullName;

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public List<Language> getLanguage() {
        return languages;
    }

    public String getSha() {
        return sha;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }

    public String getRepositoryFullName() {
        return repositoryFullName;
    }

    public Commit setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Commit setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public Commit setLanguages(List<Language> languages) {
        this.languages = languages;
        return this;
    }

    public Commit setSha(String sha) {
        this.sha = sha;
        return this;
    }

    public Commit setUrl(String url) {
        this.url = url;
        return this;
    }

    public Commit setMessage(String message) {
        this.message = message;
        return this;
    }

    public Commit setRepositoryFullName(String repositoryFullName) {
        this.repositoryFullName = repositoryFullName;
        return this;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!other.getClass().equals(this.getClass())) return false;

        Commit otherAccount = (Commit) other;
        return  Objects.equals(author, otherAccount.getAuthor()) &&
                Objects.equals(creationDate, otherAccount.getCreationDate()) &&
                Objects.equals(languages, otherAccount.getLanguage()) &&
                Objects.equals(sha, otherAccount.getSha()) &&
                Objects.equals(message, otherAccount.getMessage()) &&
                Objects.equals(repositoryFullName, otherAccount.getRepositoryFullName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                author, creationDate, languages, sha, message, repositoryFullName);
    }

    @Override
    public String toString() {
        return String.format("[Commit] " +
                        "author: %s | creationDate: %s | language: %s |" +
                        "sha: %s | message: %s | repositoryFullName: %s",
                author, creationDate, languages,
                sha, message, repositoryFullName);
    }
}
