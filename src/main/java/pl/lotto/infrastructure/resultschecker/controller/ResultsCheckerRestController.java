package pl.lotto.infrastructure.resultschecker.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.resultschecker.ResultsCheckerFacade;

@RestController
@AllArgsConstructor
public class ResultsCheckerRestController {

    private final ResultsCheckerFacade resultsCheckerFacade;

//    @GetMapping(value = "/generate/{date}")
//    public ResponseEntity<Integer> getResultsForUuid(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
//        int someCount = resultsCheckerFacade.generateLotteryResultsForDrawDate(date);
//        return ResponseEntity.ok(someCount);
//    }

    @GetMapping(value = "/generate")
    public ResponseEntity<Integer> getResultsForUuid() {
        String dateTimeString = "2023-02-18T12:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime parse = LocalDateTime.parse(dateTimeString, formatter);
        int someCount = resultsCheckerFacade.generateLotteryResultsForDrawDate(parse);
        return ResponseEntity.ok(someCount);
    }

}
