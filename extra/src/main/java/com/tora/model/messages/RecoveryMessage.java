package com.tora.model.messages;

/**
 * Marker of type recovery for {@link MessageDataModel}
 *
 * @author Ovidiu Maja <ovidiu.maja@tora.com>
 * @version Dec 08, 2017
 */
public class RecoveryMessage extends MessageDataModel {
    public final String data;

    public RecoveryMessage(String data) {
        super(data);
        this.data = data;
    }
}
