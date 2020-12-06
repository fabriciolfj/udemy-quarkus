package com.github.fabriciolfj.ifodd.mp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Localizacao {

    private Long id;
    private Double latitude;
    private Double longitude;
}
