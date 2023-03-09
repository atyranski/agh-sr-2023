package edu.agh.atyranski.server.controller;

import edu.agh.atyranski.server.model.GithubSearchResponse;
import edu.agh.atyranski.server.model.GithubSearchResponse.Item;
import edu.agh.atyranski.server.model.Language;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubAPIService {

    private static final String GITHUB_API_ACCEPT_HEADER = "application/vnd.github+json";
    private static final String BASE_URL = "https://api.github.com";
    private static final String API_SEARCH_ENDPOINT = "search/commits";

    public Optional<GithubSearchResponse> pollCommits(String account, String dateFrom, String dateTo) {
        log.info("Polling commits for {}", account);

        String searchQuery = String.format("author:%s+author-date:%s..%s", account, dateFrom, dateTo);
        String sortParam = "author-date";
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s/%s?q=%s&sort=%s", BASE_URL, API_SEARCH_ENDPOINT, searchQuery, sortParam);

        return Optional.ofNullable(restTemplate.getForObject(url, GithubSearchResponse.class));
    }

    public List<Language> getCommitLanguage(Item item) {
        RestTemplate restTemplate = new RestTemplate();
        String url = item.getRepository().getLanguagesUrl();

        Optional<Map<String, Integer>> optional = Optional.ofNullable(restTemplate.getForObject(url, Map.class));

        if (optional.isEmpty()) {
            return Collections.emptyList();
        }

        List<Language> result = new LinkedList<>();

        for (Map.Entry<String, Integer> entry: optional.get().entrySet()) {
            result.add(new Language().setLanguage(entry.getKey()).setAmount(entry.getValue()));
        }

        return result;
    }

}