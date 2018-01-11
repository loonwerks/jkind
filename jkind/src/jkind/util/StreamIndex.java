package jkind.util;

import java.util.ArrayList;
import java.util.List;

import jkind.sexp.Sexp;
import jkind.sexp.Symbol;

public class StreamIndex {
	private final String stream;
	private final int index;

	public StreamIndex(String stream, int index) {
		this.stream = stream;
		this.index = index;
	}

	public String getStream() {
		return stream;
	}

	public int getIndex() {
		return index;
	}

	public Symbol getEncoded() {
		return new Symbol("$" + stream + getSuffix(index));
	}

	public static String getSuffix(int index) {
		if (index >= 0) {
			return "$" + index;
		} else {
			// Using - in symbol names causes parse issues, so we use ~ instead
			return "$~" + -index;
		}
	}

	public static StreamIndex decode(String encoded) {
		if (!isEncodedStreamIndex(encoded)) {
			return null;
		}

		int split = encoded.indexOf("$", 1);
		String stream = encoded.substring(1, split);
		int index = getIndex(encoded.substring(split + 1));
		return new StreamIndex(stream, index);
	}

	public static StreamIndex decode(Symbol encoded) {
		return decode(encoded.str);
	}

	public static List<StreamIndex> fromList(List<String> streams, int index) {
		List<StreamIndex> result = new ArrayList<>();
		for (String stream : streams) {
			result.add(new StreamIndex(stream, index));
		}
		return result;
	}

	public static List<Symbol> getEncoded(List<StreamIndex> streamIndexes) {
		List<Symbol> result = new ArrayList<>();
		for (StreamIndex streamIndex : streamIndexes) {
			result.add(streamIndex.getEncoded());
		}
		return result;
	}

	public static Sexp conjoinEncodings(List<String> streams, int index) {
		return SexpUtil.conjoin(getEncoded(fromList(streams, index)));
	}

	private static boolean isEncodedStreamIndex(String var) {
		return var.startsWith("$") && !SexpUtil.isEncodedFunction(var);
	}

	private static int getIndex(String indexStr) {
		if (indexStr.startsWith("~")) {
			return -Integer.parseInt(indexStr.substring(1));
		} else {
			return Integer.parseInt(indexStr);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		result = prime * result + ((stream == null) ? 0 : stream.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof StreamIndex)) {
			return false;
		}
		StreamIndex other = (StreamIndex) obj;
		if (index != other.index) {
			return false;
		}
		if (stream == null) {
			if (other.stream != null) {
				return false;
			}
		} else if (!stream.equals(other.stream)) {
			return false;
		}
		return true;
	}
}
