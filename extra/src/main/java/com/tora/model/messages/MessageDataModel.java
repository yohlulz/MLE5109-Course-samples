package com.tora.model.messages;

import java.util.Arrays;

/**
 * @author Ovidiu Maja <ovidiu.maja@tora.com>
 * @version Dec 08, 2017
 */
public class MessageDataModel extends Message {
    private String[] fields;

    public MessageDataModel(String data) {
        this.fields = data.split("~");
    }

    @Override
    public String getGroup() {
        return fields[0];
    }

    @Override
    public String getId() {
        return fields[1];
    }

    public String getAction() {
        return fields[2];
    }

    public String getData() {
        return fields[3];
    }

    @Override
    public String toString() {
        return Arrays.toString(fields);
    }
}
