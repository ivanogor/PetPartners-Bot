package pro.sky.petpartnersbot.util;

import pro.sky.petpartnersbot.entity.Message;

public class MessageUtils {
    public static Message getMessage() {
        return Message.builder()
                .text("text")
                .type("type")
                .build();
    }
}
