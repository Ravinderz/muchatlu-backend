package com.muchatlu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Typing {
    private Long conversationId;
    private Long userIdFrom;
    private Long userIdTo;
    private Boolean isTyping;
}
