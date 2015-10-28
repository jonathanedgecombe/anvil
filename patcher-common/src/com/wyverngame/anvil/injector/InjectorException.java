package com.wyverngame.anvil.injector;

public final class InjectorException extends RuntimeException {
	private static final long serialVersionUID = -3253324031828582873L;

	public InjectorException(String msg) {
		super(msg);
	}

	public InjectorException(Throwable cause) {
		super(cause);
	}
}
