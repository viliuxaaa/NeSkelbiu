package lt.neskelbiu.java.main.poster;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum City {
	@JsonProperty("Vilnius")
	VILNIUS("Vilnius"),

	@JsonProperty("Kaunas")
	KAUNAS("Kaunas"),

	@JsonProperty("Klaipėda")
	KLAIPEDA("Klaipėda"),

	@JsonProperty("Šiauliai")
	SIAULIAI("Šiauliai"),

	@JsonProperty("Panevežys")
	PANEVEZYS("Panevežys"),

	@JsonProperty("Alytus")
	ALYTUS("Alytus"),

	@JsonProperty("Marijampolė")
	MARIJAMPOLE("Marijampolė"),

	@JsonProperty("Mažeikiai")
	MAZEIKIAI("Mažeikiai"),

	@JsonProperty("Jonava")
	JONAVA("Jonava"),

	@JsonProperty("Utena")
	UTENA("Utena"),

	@JsonProperty("Neringa")
	NERINGA("Neringa"),

	@JsonProperty("Pagėgiai")
	PAGEGIAI("Pagėgiai"),

	@JsonProperty("Palanga")
	PALANGA("Palanga"),

	@JsonProperty("Kalvarija")
	KALVARIJA("Kalvarija"),

	@JsonProperty("Rietavas")
	RIETAVAS("Rietavas"),

	@JsonProperty("Birštonas")
	BIRSTONAS("Birštonas"),

	@JsonProperty("Druskininkai")
	DRUSKININKAI("Druskininkai"),

	@JsonProperty("Kazlų Rūda")
	KAZLURUDA("Kazlų Rūda"),

	@JsonProperty("Elektrėnai")
	ELEKTRENAI("Elektrėnai"),

	@JsonProperty("Visaginas")
	VISAGINAS("Visaginas"),

	@JsonProperty("Akmenės Rajonas")
	AKMENES_RAJONAS("Akmenės Rajonas"),

	@JsonProperty("Alytaus Rajonas")
	ALYTAUS_RAJONAS("Alytaus Rajonas"),

	@JsonProperty("Anykščių Rajonas")
	ANYKSCIU_RAJONAS("Anykščių Rajonas"),

	@JsonProperty("Biržų Rajonas")
	BIRZU_RAJONAS("Biržų Rajonas"),

	@JsonProperty("Ignalinos Rajonas")
	IGNALINOS_RAJONAS("Ignalinos Rajonas"),

	@JsonProperty("Jonavos Rajonas")
	JONAVOS_RAJONAS("Jonavos Rajonas"),

	@JsonProperty("Joniškio Rajonas")
	JONISKIO_RAJONAS("Joniškio Rajonas"),

	@JsonProperty("Jurbarko Rajonas")
	JURBARKO_RAJONAS("Jurbarko Rajonas"),

	@JsonProperty("Kaišiadorių Rajonas")
	KAISIADO_RIURAJONAS("Kaišiadorių Rajonas"),

	@JsonProperty("Kauno Rajonas")
	KAUNO_RAJONAS("Kauno Rajonas"),

	@JsonProperty("Kėdainių Rajonas")
	KEDAINIU_RAJONAS("Kėdainių Rajonas"),

	@JsonProperty("Kelmės Rajonas")
	KELMES_RAJONAS("Kelmės Rajonas"),

	@JsonProperty("Klaipėdos Rajonas")
	KLAIPEDOS_RAJONAS("Klaipėdos Rajonas"),

	@JsonProperty("Kretingos Rajonas")
	KRETINGOS_RAJONAS("Kretingos Rajonas"),

	@JsonProperty("Kupiškio Rajonas")
	KUPISKIO_RAJONAS("Kupiškio Rajonas"),

	@JsonProperty("Lazdijų Rajonas")
	LAZDIJU_RAJONAS("Lazdijų Rajonas"),

	@JsonProperty("Pakruojo Rajonas")
	PAKRUOJO_RAJONAS("Pakruojo Rajonas"),

	@JsonProperty("Mažeikių Rajonas")
	MAZEIKIU_RAJONAS("Mažeikių Rajonas"),

	@JsonProperty("Molėtų Rajonas")
	MOLETU_RAJONAS("Molėtų Rajonas"),

	@JsonProperty("Panevėžio Rajonas")
	PANEVEZIO_RAJONAS("Panevėžio Rajonas"),

	@JsonProperty("Pasvalio Rajonas")
	PASVALIO_RAJONAS("Pasvalio Rajonas"),

	@JsonProperty("Plungės Rajonas")
	PLUNGES_RAJONAS("Plungės Rajonas"),

	@JsonProperty("Prienų Rajonas")
	PRIENU_RAJONAS("Prienų Rajonas"),

	@JsonProperty("Radviliškio Rajonas")
	RADVILISKIO_RAJONAS("Radviliškio Rajonas"),

	@JsonProperty("Raseinių Rajonas")
	RASEINIU_RAJONAS("Raseinių Rajonas"),

	@JsonProperty("Rokiškio Rajonas")
	ROKISKIO_RAJONAS("Rokiškio Rajonas"),

	@JsonProperty("Skuodo Rajonas")
	SKUODO_RAJONAS("Skuodo Rajonas"),

	@JsonProperty("Šakių Rajonas")
	SAKIU_RAJONAS("Šakių Rajonas"),

	@JsonProperty("Šalčininkų Rajonas")
	SALCININKU_RAJONAS("Šalčininkų Rajonas"),

	@JsonProperty("Šiaulių Rajonas")
	SIAULIU_RAJONAS("Šiaulių Rajonas"),

	@JsonProperty("Šilalės Rajonas")
	SILALES_RAJONAS("Šilalės Rajonas"),

	@JsonProperty("Šilutės Rajonas")
	SILUTES_RAJONAS("Šilutės Rajonas"),

	@JsonProperty("Širvintų Rajonas")
	SIRVINTU_RAJONAS("Širvintų Rajonas"),

	@JsonProperty("Švenčionių Rajonas")
	SVENCIONIU_RAJONAS("Švenčionių Rajonas"),

	@JsonProperty("Tauragės Rajonas")
	TAURAGES_RAJONAS("Tauragės Rajonas"),

	@JsonProperty("Telšių Rajonas")
	TELSIU_RAJONAS("Telšių Rajonas"),

	@JsonProperty("Trakų Rajonas")
	TRAKU_RAJONAS("Trakų Rajonas"),

	@JsonProperty("Ukmergės Rajonas")
	UKMERGES_RAJONAS("Ukmergės Rajonas"),

	@JsonProperty("Utenos Rajonas")
	UTENOS_RAJONAS("Utenos Rajonas"),

	@JsonProperty("Varėnos Rajonas")
	VARENOS_RAJONAS("Varėnos Rajonas"),

	@JsonProperty("Vilkaviškio Rajonas")
	VILKAVISKIO_RAJONAS("Vilkaviškio Rajonas"),

	@JsonProperty("Vilniaus Rajonas")
	VILNIAUS_RAJONAS("Vilniaus Rajonas"),

	@JsonProperty("Zarasų Rajonas")
	ZARASU_RAJONAS("Zarasų Rajonas")

	@Getter
	private final String name;
}
