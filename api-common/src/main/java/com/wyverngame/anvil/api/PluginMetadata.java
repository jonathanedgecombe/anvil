package com.wyverngame.anvil.api;

public final class PluginMetadata {
	private final String title, version, author;

	public PluginMetadata(String title, String version, String author) {
		this.title = title;
		this.version = version;
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public String getVersion() {
		return version;
	}

	public String getAuthor() {
		return author;
	}

	public boolean matches(PluginMetadata info) {
		return info.getTitle().equals(title) && info.getAuthor().equals(author);
	}

	@Override
	public String toString() {
		return "'" + title + "' [" + version + "] by " + author;
	}
}
