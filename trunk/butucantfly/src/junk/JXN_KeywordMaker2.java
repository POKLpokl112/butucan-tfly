package junk;

import java.io.File;
import java.util.Base64;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import common.Util;

public class JXN_KeywordMaker2 {

	private static JXN_KeywordMaker2 INSTANCE;

	public static JXN_KeywordMaker2 getInstance() {
		if (INSTANCE == null) {
			synchronized (JXN_KeywordMaker2.class) {
				if (INSTANCE == null) {
					INSTANCE = new JXN_KeywordMaker2();
				}
			}
		}
		return INSTANCE;
	}

	public Invocable invokeEngine;

	private JXN_KeywordMaker2() {
		final ScriptEngineManager manager = new ScriptEngineManager();
		final ScriptEngine engine = manager.getEngineByMimeType("text/javascript");
		try {
			engine.eval(Util
					.loadFromFile(new File("src" + File.separator + "COM_JXN_INDEX_江西企业公示" + File.separator + "js")));
		} catch (final ScriptException e) {
			throw new IllegalArgumentException(e);
		}
		invokeEngine = (Invocable) engine;
	}

	public String encode(final String keyword) {

		try {
			return (String) invokeEngine.invokeFunction("encode", keyword);
		} catch (final Exception e) {
			throw new IllegalArgumentException();
		}
	}

	public String decode(final String code) {
		try {
			return (String) invokeEngine.invokeFunction("decode", code);
		} catch (final Exception e) {
			throw new IllegalArgumentException();
		}
	}

	public static void main(final String[] args) {
		System.out.println(new String(Base64.getEncoder().encode("建设".getBytes())));
	}

}
