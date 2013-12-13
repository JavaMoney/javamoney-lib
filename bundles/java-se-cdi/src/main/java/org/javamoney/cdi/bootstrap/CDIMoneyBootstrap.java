package org.javamoney.cdi.bootstrap;

import javax.money.bootstrap.Bootstrap;

public class CDIMoneyBootstrap {

	private static boolean initialized;

	public static void init() {
		if (!initialized) {
			Bootstrap.init(new CDIServices());
			initialized = true;
		}
	}

}
