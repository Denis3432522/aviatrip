package com.example.aviatrip.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum City {
    MOSCOW,
    VLADIVOSTOK,
    SAMARA,
    NOVOSIBIRSK,
    UFA,
    OMSK,
    KRASNODAR,
    VORONEZH,
    PERM,
    KEMEROVO,
    ROSTOV;

    @JsonValue
    public String getSerializedCity() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}
