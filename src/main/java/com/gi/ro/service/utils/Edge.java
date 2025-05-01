package com.gi.ro.service.utils;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Edge {
    public UUID from;
    public UUID to;

    public UUID getOther(UUID node) {
        return from.equals(node) ? to : from;
    }

    public String getMinMaxKey() {
        List<String> ids = new ArrayList<>(List.of(from.toString(), to.toString()));
        ids.sort(String::compareTo);
        return ids.get(0) + "_" + ids.get(1);
    }
}
