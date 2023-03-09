package edu.agh.atyranski.server.controller;

import edu.agh.atyranski.server.model.Commit;
import edu.agh.atyranski.server.model.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@Slf4j
public class CommitController {

    private final CommitService service;

    @GetMapping
    public Response<List<Commit>> getCommits(@RequestParam(name = "account")        String account,
                                             @RequestParam(name = "from")           String dateFrom,
                                             @RequestParam(name = "to")             String dateTo,
                                             @RequestParam(name = "repo", required = false) String repositoryName) {

        List<Commit> commits = service.getCommits(account, dateFrom, dateTo, repositoryName);

        return Response.success(commits);
    }
}
