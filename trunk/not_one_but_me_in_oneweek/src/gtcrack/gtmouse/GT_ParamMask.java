package gtcrack.gtmouse;

import java.io.File;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import common.Util;

public class GT_ParamMask {

	private static Invocable invokeEngine;

	static {
		final ScriptEngineManager manager = new ScriptEngineManager();
		final ScriptEngine engine = manager.getEngineByMimeType("text/javascript");
		try {
			engine.eval(Util.loadFromFile(
					new File("src" + File.separator + "gtcrack" + File.separator + "gtmouse" + File.separator + "gt")));
		} catch (final ScriptException e) {
			throw new IllegalArgumentException(e);
		}
		invokeEngine = (Invocable) engine;
	}

	public static String get(final String l, final String challenge) throws Exception {
		return (String) invokeEngine.invokeFunction("ra", l, challenge);
	}

	public static void main(final String[] args) throws Exception {
	}

}
