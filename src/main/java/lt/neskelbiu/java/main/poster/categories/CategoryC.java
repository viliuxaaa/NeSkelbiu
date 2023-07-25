package lt.neskelbiu.java.main.poster.categories;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CategoryC {
    // Kompiuteriai
    NESIOJAMI_KOMPIUTERIAI("Nesiojami kompiuteriai"),
    PLANSETINIAI_KOMPIUTERIAI("Plansetiniai kompiuteriai"),
    STACIONARUS_KOMPIUTERIAI("Stacionarus kompiuteriai"),
    ZAIDIMU_KOMPIUTERIAI("Zaidimu kompiuteriai"),
    SERVERIAI("Serveriai"),
    ELEKTRONINES_PASLAUGOS("Elektronines paslaugos"),
    KOMPIUTERIAI_KITA("Kita"),

    // Kompiuteriai, paslaugos remontas
    SPAUSDINIMAS_3D("3D Spausdinimas"),
    REMONTAS("Remontas"),
    SPAUSDINIMAS("Spausdinimas, Kopijavimas"),
    SUPIRKIMAS("Supirkimas"),
    KOMPIUTERIAI_PASLAUGOS_REMONTAS_KITA("Kita");

    @Getter
    private final String name;
}
