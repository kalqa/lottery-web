package pl.lotto.infrastructure.winningnumbergenerator.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.winningnumbergenerator.WinningNumberGeneratorFacade;

@RestController
@AllArgsConstructor
public class WinningNumberGeneratorRestController {

    private final WinningNumberGeneratorFacade winningNumberGeneratorFacade;

//    @GetMapping(value = "/generatewinningnumbers/{date}")
//    public ResponseEntity<Boolean> getResultsForUuid(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
//        boolean b = winningNumberGeneratorFacade.generateWinningNumbers(date);
//        return ResponseEntity.ok(b);
//    }

    @GetMapping(value = "/generatewinningnumbers")
    public ResponseEntity<Boolean> getResultsForUuid() {
        String dateTimeString = "2023-02-18T12:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime parse = LocalDateTime.parse(dateTimeString, formatter);
        boolean b = winningNumberGeneratorFacade.generateWinningNumbers(parse);
        return ResponseEntity.ok(b);
    }

}
