package com.hyoseok.dynamicdatasource.web.http.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateJwtRequest {

    @Positive(message = "adminId는 0보다 큰 수를 입력해야 합니다.")
    private Integer adminId;

    @NotBlank(message = "hasRole은 비어 있을 수 없습니다.")
    private String hasRole;
}
