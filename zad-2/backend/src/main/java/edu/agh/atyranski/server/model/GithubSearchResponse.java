package edu.agh.atyranski.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubSearchResponse {

    @JsonProperty("total_count")
    private int totalCount;

    private Item[] items;

    public int getTotalCount() {
        return totalCount;
    }

    public Item[] getItems() {
        return items;
    }

    public GithubSearchResponse setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public GithubSearchResponse setItems(Item[] items) {
        this.items = items;
        return this;
    }

    public static class Item {

        private String url;
        private String sha;

        private Commit commit;

        private Author author;

        private Repository repository;

        @JsonIgnore
        private List<Language> languages;

        public String getUrl() {
            return url;
        }

        public String getSha() {
            return sha;
        }

        public Commit getCommit() {
            return commit;
        }

        public Author getAuthor() {
            return author;
        }

        public Repository getRepository() {
            return repository;
        }

        public List<Language> getLanguages() {
            return languages;
        }

        public Item setUrl(String url) {
            this.url = url;
            return this;
        }

        public Item setSha(String sha) {
            this.sha = sha;
            return this;
        }

        public Item setCommit(Commit commit) {
            this.commit = commit;
            return this;
        }

        public Item setAuthor(Author author) {
            this.author = author;
            return this;
        }

        public Item setRepository(Repository repository) {
            this.repository = repository;
            return this;
        }

        public Item setLanguages(List<Language> languages) {
            this.languages = languages;
            return this;
        }
    }

    public static class Commit {

        private CommitAuthor author;

        private String message;

        public CommitAuthor getAuthor() {
            return author;
        }

        public String getMessage() {
            return message;
        }

        public Commit setAuthor(CommitAuthor author) {
            this.author = author;
            return this;
        }

        public Commit setMessage(String message) {
            this.message = message;
            return this;
        }
    }

    public static class CommitAuthor {

        private ZonedDateTime date;

        public ZonedDateTime getDate() {
            return date;
        }

        public CommitAuthor setDate(ZonedDateTime date) {
            this.date = date;
            return this;
        }
    }

    public static class Author {

        private String login;

        public String getLogin() {
            return login;
        }

        public Author setLogin(String login) {
            this.login = login;
            return this;
        }
    }

    public static class Repository {

        @JsonProperty("full_name")
        private String fullName;

        @JsonProperty("languages_url")
        private String languagesUrl;

        public String getFullName() {
            return fullName;
        }

        public String getLanguagesUrl() {
            return languagesUrl;
        }

        public Repository setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Repository setLanguagesUrl(String languagesUrl) {
            this.languagesUrl = languagesUrl;
            return this;
        }
    }
}