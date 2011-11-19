package rubyquest.model;

public class ArchiveNumber {

	private final String archiveNumber;

	public ArchiveNumber(final Integer archiveNumber) {
		this.archiveNumber = archiveNumber.toString();
	}

	public String getArchiveNumber() {
		return archiveNumber;
	}

	@Override
	public String toString() {
		return archiveNumber;
	}
}
