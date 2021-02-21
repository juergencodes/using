import java.lang.annotation.Annotation;

import org.perf4j.StopWatch;
import org.perf4j.aop.Profiled;

public class ProfiledClass implements Profiled {

	private String tag = "@@USE_METHOD_NAME";

	private String message = "";

	public ProfiledClass() {
	}

	public ProfiledClass(String tag, String message) {
		this.tag = tag;
		this.message = message;
	}

	public boolean el() {
		return true;
	}

	public String level() {
		return "INFO";
	}

	public boolean logFailuresSeparately() {
		return false;
	}

	public String logger() {
		return StopWatch.DEFAULT_LOGGER_NAME;
	}

	public String message() {
		return message;
	}

	public String tag() {
		return tag;
	}

	public long timeThreshold() {
		return 0;
	}

	public Class<? extends Annotation> annotationType() {
		return null;
	}

}