package com.example.phuong.healthy.eventBus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by phuong on 12/01/2017.
 */
@NoArgsConstructor
@Data
@AllArgsConstructor(suppressConstructorProperties = true)
public class MessageEvent {
    boolean isCheck;
}
