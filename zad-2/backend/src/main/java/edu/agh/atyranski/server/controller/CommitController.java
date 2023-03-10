package edu.agh.atyranski.server.controller;

import edu.agh.atyranski.server.model.Commit;
import edu.agh.atyranski.server.model.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
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

        final List<Commit> commits = service.getCommits(account, dateFrom, dateTo, repositoryName);
//        final HttpHeaders reponseHeaders = new HttpHeaders();
//        reponseHeaders.set("Access-Control-Allow-Origin", "http://localhost:3000");

//        return ResponseEntity.ok()
//                .headers(reponseHeaders)
//                .body(commits);
        return Response.success(commits);
    }
}
