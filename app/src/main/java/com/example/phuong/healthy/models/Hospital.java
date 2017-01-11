package com.example.phuong.healthy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by phuong on 28/12/2016.
 */
@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true)
@Data
public class Hospital {
    private int id;
    private String name;
    private String address;
    private String image;
    private String website;
    private int idProvice;
    private String phone;
}
