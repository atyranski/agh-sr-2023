package edu.agh.atyranski.server.model;

import edu.agh.atyranski.server.exception.UnknownFileStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class Commit {

    private String author;
    private LocalDateTime creationDate;
    private String sha;
    private String message;
    private String repositoryFullName;
    private Stats stats;
    private CommitContent.File[] files;

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getSha() {
        return sha;
    }

    public String getMessage() {
        return message;
    }

    public String getRepositoryFullName() {
        return repositoryFullName;
    }

    public Stats getStats() {
        return stats;
    }

    public CommitContent.File[] getFiles() {
        return files;
    }

    public Commit setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Commit setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }
    public Commit setSha(String sha) {
        this.sha = sha;
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

    public Commit setStats(Stats stats) {
        this.stats = stats;
        return this;
    }

    public Commit setStats(CommitContent.File[] files) {
        int total = 0;
        int added = 0;
        int removed = 0;
        int modified = 0;
        int copied = 0;
        int changed = 0;
        int unchanged = 0;

        for (CommitContent.File file: files) {
            total++;

            switch (file.getStatus()) {
                case "added" -> added++;
                case "removed" -> removed++;
                case "modified" -> modified++;
                case "copied" -> copied++;
                case "changed" -> changed++;
                case "unchanged" -> unchanged++;
                default -> throw new UnknownFileStatus(file.getStatus());
            }
        }

        setStats(new Stats().setTotal(total)
                .setAdded(added)
                .setRemoved(removed)
                .setModified(modified)
                .setCopied(copied)
                .setChanged(changed)
                .setUnchanged(unchanged));

        return this;
    }

    public Commit setFiles(CommitContent.File[] files) {
        this.files = files;
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
                Objects.equals(sha, otherAccount.getSha()) &&
                Objects.equals(message, otherAccount.getMessage()) &&
                Objects.equals(repositoryFullName, otherAccount.getRepositoryFullName()) &&
                Arrays.equals(files, otherAccount.getFiles()) &&
                Objects.equals(stats, otherAccount.getStats());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                author, creationDate, sha, message, repositoryFullName, Arrays.hashCode(files), stats);
    }

    @Override
    public String toString() {
        return String.format("[Commit] " +
                        "author: %s | creationDate: %s | sha: %s | " +
                        "message: %s | repositoryFullName: %s | files: %s | stats: %s",
                author, creationDate,
                sha, message, repositoryFullName, Arrays.toString(files), stats);
    }

    public static class Stats {

        private long total;
        private long added;
        private long removed;
        private long modified;
        private long renamed;
        private long copied;
        private long changed;
        private long unchanged;

        public long getTotal() {
            return total;
        }

        public long getAdded() {
            return added;
        }

        public long getRemoved() {
            return removed;
        }

        public long getModified() {
            return modified;
        }

        public long getRenamed() {
            return renamed;
        }

        public long getCopied() {
            return copied;
        }

        public long getChanged() {
            return changed;
        }

        public long getUnchanged() {
            return unchanged;
        }

        public Stats setTotal(long total) {
            this.total = total;
            return this;
        }

        public Stats setAdded(long added) {
            this.added = added;
            return this;
        }

        public Stats setRemoved(long removed) {
            this.removed = removed;
            return this;
        }

        public Stats setModified(long modified) {
            this.modified = modified;
            return this;
        }

        public Stats setRenamed(long renamed) {
            this.renamed = renamed;
            return this;
        }

        public Stats setCopied(long copied) {
            this.copied = copied;
            return this;
        }

        public Stats setChanged(long changed) {
            this.changed = changed;
            return this;
        }

        public Stats setUnchanged(long unchanged) {
            this.unchanged = unchanged;
            return this;
        }
    }
}
