package com.wyverngame.anvil.injector;

public final class InjectorException extends RuntimeException {
	public InjectorException(String msg) {
		super(msg);
	}

	public InjectorException(Throwable cause) {
		super(cause);
	}
}
