package de.shop.util;


public class NotFoundException extends RuntimeException {
	
	/**
	 * @autor: <a href="mailto:Juergen.Zimmermann@HS-Karlsruhe.de">J&uuml;rgen Zimmermann</a>
	 */
	
	private static final long serialVersionUID = 8449022232751591762L;
	private final Object[] args;

	public NotFoundException(String msg, Object... args) {
		super(msg);
		this.args = args;
	}

	public Object[] getArgs() {
		return args.clone();
	}
}
