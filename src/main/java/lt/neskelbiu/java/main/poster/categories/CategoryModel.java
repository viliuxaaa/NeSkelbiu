package lt.neskelbiu.java.main.poster.categories;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CategoryModel {
    IPHONE("iPhone"),
    DELL("DELL"),
    MSI("MSI"),
    ASUS("ASUS"),
    KITA("Kita");

    @Getter
    private final String name;
}
