package edu.agh.atyranski.server.controller;

import edu.agh.atyranski.server.model.Commit;
import edu.agh.atyranski.server.model.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/search")
@RequiredArgsConstructor
@Slf4j
public class CommitController {

    private final CommitService service;

    @GetMapping
    public Response<List<Commit>> getCommits(@RequestParam(name = "account")        String account,
                                             @RequestParam(name = "from")           String dateFrom,
                                             @RequestParam(name = "to")             String dateTo,
                                             @RequestHeader("Authorization") String githubAccessToken) {

        final List<Commit> commits = service.getCommits(
                account, dateFrom, dateTo, githubAccessToken);

        return Response.success(commits);
    }
}
