package edu.agh.atyranski.server.controller;

import edu.agh.atyranski.server.model.Commit;
import edu.agh.atyranski.server.model.GithubSearchResponse;
import edu.agh.atyranski.server.model.GithubSearchResponse.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommitService {

    private final GithubAPIService githubAPIService;

    public List<Commit> getCommits(String nickname, String dateFrom, String dateTo, String repositoryName) {
        Optional<GithubSearchResponse> response = githubAPIService.pollCommits(nickname, dateFrom, dateTo);

        if (response.isEmpty()) {
            return Collections.emptyList();
        }

        return Arrays.stream(response.get().getItems())
                .map(item -> item.setLanguages(githubAPIService.getCommitLanguage(item)))
                .map(CommitService::mapSearchItemToCommit)
                .toList();
    }

    private static Commit mapSearchItemToCommit(Item item) {
        return new Commit()
                .setAuthor(item.getAuthor().getLogin())
                .setCreationDate(item.getCommit().getAuthor().getDate().toLocalDateTime())
                .setLanguages(item.getLanguages())
                .setSha(item.getSha())
                .setUrl(item.getUrl())
                .setMessage(item.getCommit().getMessage())
                .setRepositoryFullName(item.getRepository().getFullName());
    }
}
