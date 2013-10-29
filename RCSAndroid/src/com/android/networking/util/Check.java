/* *******************************************
 * Copyright (c) 2011
 * HT srl,   All rights reserved.
 * Project      : RCS, AndroidService
 * File         : if(Cfg.DEBUG) Check.java //$NON-NLS-1$
 * Created      : Apr 9, 2011
 * Author		: zeno
 * *******************************************/

package com.android.networking.util;

import android.util.Log;

import com.android.networking.auto.Cfg;
import com.android.networking.file.AutoFile;
import com.android.networking.file.Path;

/**
 * The Class if(Cfg.DEBUG) Check.
 */
public final class Check {
	private static final String TAG = "Check"; //$NON-NLS-1$
	private static boolean enabled = Cfg.DEBUG;
	private static boolean error;

	/**
	 * Asserts, used to verify the truth of an expression
	 * 
	 * @param b
	 *            the b
	 * @param string
	 *            the string
	 */
	public static void asserts(final boolean b, final String string) {
		if (enabled && b != true) {
			if (Cfg.DEBUG) {
				Check.log(TAG + "##### Asserts - " + string + " #####");//$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	}

	/**
	 * Requires. Used to Check.prerequisites of a method. //$NON-NLS-1$
	 * 
	 * @param b
	 *            the b
	 * @param string
	 *            the string
	 */
	public static void requires(final boolean b, final String string) {
		if (enabled && b != true) {
			if (Cfg.DEBUG) {
				Check.log(TAG + "##### Requires - " + string + " #####");//$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	}

	/**
	 * Ensures. Check. to be done at the end of a method. //$NON-NLS-1$
	 * 
	 * @param b
	 *            the b
	 * @param string
	 *            the string
	 */
	public static void ensures(final boolean b, final String string) {
		if (enabled && b != true) {
			if (Cfg.DEBUG) {
				Check.log(TAG + "##### Ensures - " + string + " #####");//$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	}

	public static void log(String string) {		
		log(string, false);
	}

	public synchronized static void log(String string, boolean forced) {
		if (Cfg.DEBUG || forced || Cfg.DEBUGKEYS) {
			Log.d("QZ", string); //$NON-NLS-1$

			if (Cfg.FILE) {
				if (!Path.initialized()) {
					return;
				}

				final AutoFile file = new AutoFile(Path.logs(), Path.getCurLogfile());

				if (file.exists()) {
					final DateTime date = new DateTime();
					file.append(date.getOrderedString() + " - " + string + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
		}
	}

	public static void log(Throwable e) {
		if (Cfg.DEBUG || Cfg.EXCEPTION) {
			e.printStackTrace();
			log("Exception: " + e.toString(), true); //$NON-NLS-1$
		}
	}
}