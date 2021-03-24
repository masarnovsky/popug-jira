package com.masarnovsky.popugjira.auth.event;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public abstract class Event {
    private String name;
    // todo: add some useful information: createdAt, topic name etc.

    public Event(String name) {
        this.name = name;
    }
}
