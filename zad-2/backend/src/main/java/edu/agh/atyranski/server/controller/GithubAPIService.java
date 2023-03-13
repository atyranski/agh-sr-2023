package edu.agh.atyranski.server.controller;

import edu.agh.atyranski.server.model.CommitContent;
import edu.agh.atyranski.server.model.GithubSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubAPIService {

    private static final String GITHUB_API_ACCEPT_HEADER = "application/vnd.github+json";
    private static final String BASE_URL = "https://api.github.com";
    private static final String API_SEARCH_ENDPOINT = "search/commits";


    public Optional<GithubSearchResponse> pollAllCommits(String account, String dateFrom, String dateTo, String githubAccessToken) {
        log.info("Polling commits for {}", account);

        final RestTemplate restTemplate = new RestTemplate();
        final String url = createSearchQueryUrl(account, dateFrom, dateTo);

        final HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, githubAccessToken);
        headers.set(HttpHeaders.ACCEPT, GITHUB_API_ACCEPT_HEADER);

        final HttpEntity<Void> request = new HttpEntity<>(headers);

        return Optional.ofNullable(
                restTemplate.exchange(url, HttpMethod.GET, request, GithubSearchResponse.class).getBody());
    }

    private String createSearchQueryUrl(String account, String dateFrom, String dateTo) {
        final String searchQuery = String.format("author:%s+author-date:%s..%s", account, dateFrom, dateTo);
        final String sortParam = "author-date";

        return String.format("%s/%s?q=%s&sort=%s", BASE_URL, API_SEARCH_ENDPOINT, searchQuery, sortParam);
    }
    @Async
    public CompletableFuture<CommitContent> getCommitContent(String url, String githubAccessToken) {
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, githubAccessToken);
        headers.set(HttpHeaders.ACCEPT, GITHUB_API_ACCEPT_HEADER);

        final HttpEntity<Void> request = new HttpEntity<>(headers);

        return CompletableFuture.completedFuture(
                restTemplate.exchange(url, HttpMethod.GET, request, CommitContent.class).getBody());
    }

}