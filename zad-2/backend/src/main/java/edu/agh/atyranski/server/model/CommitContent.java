package edu.agh.atyranski.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitContent {

    private File[] files;

    public File[] getFiles() {
        return files;
    }

    public CommitContent setFiles(File[] files) {
        this.files = files;
        return this;
    }

    public static class File {

        private String filename;

        private String status;
        private long additions;
        private long deletions;
        private long changes;

        public String getFilename() {
            return filename;
        }

        public String getStatus() {
            return status;
        }

        public long getAdditions() {
            return additions;
        }

        public long getDeletions() {
            return deletions;
        }

        public long getChanges() {
            return changes;
        }

        public File setFilename(String filename) {
            this.filename = filename;
            return this;
        }

        public File setStatus(String status) {
            this.status = status;
            return this;
        }

        public File setAdditions(long additions) {
            this.additions = additions;
            return this;
        }

        public File setDeletions(long deletions) {
            this.deletions = deletions;
            return this;
        }

        public File setChanges(long changes) {
            this.changes = changes;
            return this;
        }
    }
}
