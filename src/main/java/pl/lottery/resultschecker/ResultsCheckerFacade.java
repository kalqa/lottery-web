package pl.lottery.resultschecker;

import pl.lottery.infrastructure.winningnumberservice.dto.WinningNumberStatus;
import pl.lottery.infrastructure.winningnumberservice.dto.WinningNumbersResponseDto;
import pl.lottery.numberreceiver.NumberReceiverFacade;
import pl.lottery.numberreceiver.dto.ReceiverResponseDto;
import pl.lottery.resultschecker.dto.CheckerDto;
import pl.lottery.resultschecker.dto.CheckerStatus;

import java.time.LocalDateTime;
import java.util.*;

public class ResultsCheckerFacade {
    private final NumberReceiverFacade numberReceiverFacade;
    private final WinningNumberGeneratorPort winningNumbersService;
    private final LotteryResultsGenerator lotteryResultsGenerator;
    private final ResultsCheckerRepository resultsCheckerRepository;

    public ResultsCheckerFacade(NumberReceiverFacade numberReceiverFacade, WinningNumberGeneratorPort winningNumbersService,
                                LotteryResultsGenerator lotteryResultsGenerator, ResultsCheckerRepository resultsCheckerRepository) {
        this.numberReceiverFacade = numberReceiverFacade;
        this.winningNumbersService = winningNumbersService;
        this.lotteryResultsGenerator = lotteryResultsGenerator;
        this.resultsCheckerRepository = resultsCheckerRepository;
    }

    public int generateLotteryResultsForDrawDate(LocalDateTime drawDate) {
        WinningNumbersResponseDto winningNumbersResponseDto = winningNumbersService.retrieveWinningNumbers(drawDate);
        if (winningNumbersResponseDto.status() == WinningNumberStatus.NOT_FOUND) {
            return -1;
        }
        List<Integer> winningNumbersForThisDrawDate = winningNumbersResponseDto.winningNumbers();
        List<ReceiverResponseDto> couponsForThisDrawDate = numberReceiverFacade.getUserCouponListForDrawDate(drawDate);
        List<LotteryResults> lotteryResults = lotteryResultsGenerator.generateLotteryResultsList(couponsForThisDrawDate, winningNumbersForThisDrawDate);
        resultsCheckerRepository.saveAll(lotteryResults);
        return lotteryResults.size();
    }

    public CheckerDto getResultsForId(UUID uuid) {
        Optional<LotteryResults> lotteryResultsOptional = resultsCheckerRepository.findById(uuid);
        if (lotteryResultsOptional.isEmpty()) {
            return notFoundDto();
        }
        return LotteryResultsMapper.toDto(lotteryResultsOptional.get(), CheckerStatus.OK);
    }

    public CheckerDto deleteLotteryResultsForUuid(UUID uuid) {
        Optional<LotteryResults> lotteryResultsOptional = resultsCheckerRepository.findById(uuid);
        if (lotteryResultsOptional.isEmpty()) {
            return notFoundDto();
        }
        resultsCheckerRepository.deleteById(uuid);
        return LotteryResultsMapper.toDto(lotteryResultsOptional.get(), CheckerStatus.DELETED);
    }

    public List<CheckerDto> getLotteryResultsForDrawDate(LocalDateTime drawDate) {
        List<LotteryResults> lotteryResults = resultsCheckerRepository.findByDrawDate(drawDate);
        return LotteryResultsMapper.toDtoList(lotteryResults, CheckerStatus.OK);
    }

    public List<CheckerDto> getLotteryResultsDrawDateWinnersOnly(LocalDateTime drawDate) {
        List<LotteryResults> lotteryResults = resultsCheckerRepository.findByDrawDateAndIsWinner(drawDate, true);
        return LotteryResultsMapper.toDtoList(lotteryResults, CheckerStatus.OK);
    }

    public List<CheckerDto> getAllLotteryResults() {
        List<LotteryResults> lotteryResults = resultsCheckerRepository.findAll();
        return LotteryResultsMapper.toDtoList(lotteryResults, CheckerStatus.OK);
    }

    private CheckerDto notFoundDto() {
        return new CheckerDto(null, null, null, null, null, false, CheckerStatus.NOT_FOUND);
    }

}
