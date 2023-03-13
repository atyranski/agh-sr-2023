package edu.agh.atyranski.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

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

        private Repository repository;
        private CommitContent commitContent;

        public String getUrl() {
            return url;
        }

        public String getSha() {
            return sha;
        }

        public Commit getCommit() {
            return commit;
        }

        public Repository getRepository() {
            return repository;
        }

        public CommitContent getCommitContent() {
            return commitContent;
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

        public Item setRepository(Repository repository) {
            this.repository = repository;
            return this;
        }

        public Item setCommitContent(CommitContent commitContent) {
            this.commitContent = commitContent;
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

        private String name;

        private ZonedDateTime date;

        public String getName() {
            return name;
        }

        public ZonedDateTime getDate() {
            return date;
        }

        public CommitAuthor setName(String name) {
            this.name = name;
            return this;
        }

        public CommitAuthor setDate(ZonedDateTime date) {
            this.date = date;
            return this;
        }
    }

    public static class Repository {

        @JsonProperty("full_name")
        private String fullName;

        public String getFullName() {
            return fullName;
        }

        public Repository setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }
    }
}