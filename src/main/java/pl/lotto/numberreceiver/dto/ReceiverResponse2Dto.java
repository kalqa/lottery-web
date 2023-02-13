package pl.lotto.numberreceiver.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReceiverResponse2Dto(UUID uuid,
                                   String creationDateTime,
                                   String drawDateTime,
                                   String expirationDateTime,
                                   List<Integer> typedNumbers,
                                   InputStatus status) {
}
