package edu.agh.atyranski.server.controller;

import edu.agh.atyranski.server.model.Commit;
import edu.agh.atyranski.server.model.CommitContent;
import edu.agh.atyranski.server.model.GithubSearchResponse;
import edu.agh.atyranski.server.model.GithubSearchResponse.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommitService {

    private final GithubAPIService githubAPIService;

    public List<Commit> getCommits(String nickname, String dateFrom, String dateTo, String githubAccessToken) {
        validateArguments(nickname, dateFrom, dateTo, githubAccessToken);

        final Optional<GithubSearchResponse> response =
                githubAPIService.pollAllCommits(nickname, dateFrom, dateTo, githubAccessToken);

        if (response.isEmpty()) {
            return List.of();
        }

        getCommitsContent(response.get(), githubAccessToken);

        return response.map(githubSearchResponse -> Arrays.stream(githubSearchResponse.getItems())
                        .map(CommitService::mapSearchItemToCommit)
                        .toList())
                    .orElse(Collections.emptyList());
    }

    private void getCommitsContent(GithubSearchResponse response, String githubAccessToken) {
        final HashMap<String, CompletableFuture<CommitContent>> completableFutures = new HashMap<>();

        for (Item item: response.getItems()) {
            completableFutures.put(
                    item.getSha(), githubAPIService.getCommitContent(item.getUrl(), githubAccessToken));
        }

        for (Item item: response.getItems()) {
            try {
                item.setCommitContent(completableFutures.get(item.getSha()).get());
            } catch (ExecutionException | InterruptedException e) {
                log.warn("problem with fetching commit content for commit with sha {}", item.getSha());
                System.out.printf("problem with fetching commit content for commit with sha %s\n", item.getSha());
            }
        }
    }

    private void validateArguments(String nickname, String dateFrom, String dateTo, String githubAccessToken) {
        if (nickname == null || nickname.length() == 0) {
            throw new IllegalArgumentException("account name is required");
        }

        if (dateFrom == null || dateFrom.length() == 0) {
            throw new IllegalArgumentException("dateFrom is required");
        }

        if (dateTo == null || dateTo.length() == 0) {
            throw new IllegalArgumentException("dateTo is required");
        }

        if (githubAccessToken == null || githubAccessToken.length() == 0) {
            throw new IllegalArgumentException(
                    "github access token should be added to headers as 'Authorization':GITHUB_ACCESS_TOKEN");
        }

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        final LocalDateTime dateTimeFrom = LocalDateTime.parse(dateFrom, formatter);
        final LocalDateTime dateTimeTo = LocalDateTime.parse(dateTo, formatter);

        if (dateTimeTo.isBefore(dateTimeFrom)) {
            throw new IllegalArgumentException("dateTo mustn't be earlier than dateFrom");
        }
    }

    private static Commit mapSearchItemToCommit(Item item) {
        return new Commit()
                .setAuthor(item.getCommit().getAuthor().getName())
                .setCreationDate(item.getCommit().getAuthor().getDate().toLocalDateTime())
                .setSha(item.getSha())
                .setMessage(item.getCommit().getMessage())
                .setRepositoryFullName(item.getRepository().getFullName())
                .setFiles(item.getCommitContent().getFiles())
                .setStats(item.getCommitContent().getFiles());
    }


}
