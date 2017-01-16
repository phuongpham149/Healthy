package com.example.phuong.healthy.eventBus;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by phuong on 12/01/2017.
 */
@Data
@AllArgsConstructor(suppressConstructorProperties = true)
public class MesageEvent {
    boolean isCheck;
}
