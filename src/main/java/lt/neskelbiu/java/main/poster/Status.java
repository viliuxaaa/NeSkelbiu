package lt.neskelbiu.java.main.poster;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Status {
	ACTIVE("Aktyvus"),
	NOTACTIVE("Nematomas"),
	RESERVED("Rezervuotas");

	@Getter
	private final String name;
}
