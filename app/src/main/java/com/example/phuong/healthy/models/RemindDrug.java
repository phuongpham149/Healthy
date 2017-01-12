package com.example.phuong.healthy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by phuong on 12/01/2017.
 */
@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true)
@Data
public class RemindDrug {
    private boolean isStatus;
    private String hour;
    private String min;
}
