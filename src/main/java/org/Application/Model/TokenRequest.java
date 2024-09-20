package org.Application.Model;


import lombok.Data;

import java.time.LocalDateTime;

@Data

public class TokenRequest {

    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private Integer customerId;
    private String roomType;


}
