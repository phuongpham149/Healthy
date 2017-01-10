package com.example.phuong.healthy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by phuong on 08/01/2017.
 */
@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true)
@Data
public class Fav {
    private int id;
    private String name;
    private String image;
    private int type;
}
