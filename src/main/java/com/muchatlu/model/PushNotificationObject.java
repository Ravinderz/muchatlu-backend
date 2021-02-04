package com.muchatlu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushNotificationObject {
    private String to;
    private String title;
    private String body;
    private Message data;
}
