package hmb.com.tr.mulakat.enums;

public enum Gender {
	MALE("Male"), FEMALE("Female"), UNKNOWN("Unknown");

	private final String name;

	private Gender(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
}