package lt.neskelbiu.java.main.poster.categories;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CategoryA {
    KOMPIUTERIJA("kompiuterija"),
    TECHNIKA("Technika");

    @Getter
    private final String name;
}
