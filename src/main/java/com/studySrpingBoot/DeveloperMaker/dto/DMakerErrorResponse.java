package com.studySrpingBoot.DeveloperMaker.dto;

import com.studySrpingBoot.DeveloperMaker.exception.DMakerErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DMakerErrorResponse {
    private DMakerErrorCode errorCode;
    private String errorMessage;
}
