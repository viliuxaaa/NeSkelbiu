package lt.neskelbiu.java.main.poster.categories;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CategoryB {
    //  Kompiuterija
    KOMPIUTERIAI("Kompiuteriai"),
    ISORINIAI_IRENGINIAI("Isoriniai irenginiai"),
    KOMPIUTERIU_KOMPONENTAI("Kompiuteriu komponentai"),
    PRIEDAI_AKSESUARAI("Priedai, aksesuarai"),
    PROGRAMINE_IRANGA("Programine iranga, zaidimai"),
    TINKLO_IRANGA("Tinklo iranga"),
    KOMPIUTERIJA_PASLAUGOS_REMONTAS("Paslaugos remontas"),
    KOMPIUTERIJA_KITA("Kita"),

    //  Technika
    AUDIO("Audio"),
    VIDEO("Video"),
    BUITINE_TECHNIKA("Buitine technika"),
    FOTO_OPTIKA("Foto, optika"),
    BIURO_PREKYBINE_TECHNIKA("Biuro, prekybine technika"),
    SODUI("Sodui, darzui, miskui"),
    PRAMONINE_TECHNIKA("Pramonine technika"),
    TECHNIKA_PASLAUGOS_REMONTAS("Paslaugos, remontas"),
    TECHNIKA_KITA("Kita");


    @Getter
    private final String name;
}
