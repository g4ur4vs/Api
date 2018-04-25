package hello.util;
import java.util.Calendar;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;


public class LogFormatter extends Formatter {

	private Calendar mCalendar = Calendar.getInstance();

	public synchronized String format(LogRecord record) {

		final String sourceClassName = record.getSourceClassName();
		final String sourceMethodName = record.getSourceMethodName();

		mCalendar.setTimeInMillis(record.getMillis());

		final StringBuilder sb = new StringBuilder();
		sb.append(String.format("%1$tD %1$tT.%1$tL [%2$6s] ", mCalendar, record.getLevel().toString()));

		if (sourceClassName != null) {
			sb.append(sourceClassName);
		} else {
			String loggerName = record.getLoggerName();
			sb.append(loggerName);
		}
		sb.append(" ");

		if (sourceMethodName != null) {
			sb.append(String.format("#%s", sourceMethodName));
		}
		sb.append(" ");
		sb.append(formatMessage(record));
		sb.append("\n");
		Throwable thrown = record.getThrown();
		if (thrown != null) {
			sb.append(record.getThrown());
		}
		return sb.toString();
	}
}