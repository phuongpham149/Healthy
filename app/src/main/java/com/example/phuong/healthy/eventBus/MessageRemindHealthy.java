package com.example.phuong.healthy.eventBus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by phuong on 16/01/2017.
 */
@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true)
@Data
public class MessageRemindHealthy {
    private boolean state;
}
