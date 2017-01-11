package com.example.phuong.healthy.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by phuong on 28/12/2016.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
@Data
public class Provices {
    private int id;
    private String name;
    private String image;

    public Provices() {
    }
}
