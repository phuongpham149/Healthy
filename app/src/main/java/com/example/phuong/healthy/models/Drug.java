package com.example.phuong.healthy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by phuong on 08/01/2017.
 */
@NoArgsConstructor
@Data
@AllArgsConstructor(suppressConstructorProperties = true)
public class Drug {
    private String name;
    private String manufacturer;
    private String indication;
    private String contraindication;
    private String dosage_and_usage;
    private String image;
}
