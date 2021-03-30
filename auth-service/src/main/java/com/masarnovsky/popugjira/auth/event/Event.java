package com.masarnovsky.popugjira.auth.event;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public abstract class Event {
    private final String name;
    private final String service;

    public Event(String name, String service) {
        this.name = name;
        this.service = service;
    }
}
