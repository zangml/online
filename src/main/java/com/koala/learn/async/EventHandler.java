package com.koala.learn.async;

import java.util.List;

/**
 * Created by koala on 2017/11/21.
 */
public interface EventHandler {
    void doHandle(EventModel model);

    List<EventType> getSupportEventTypes();
}
