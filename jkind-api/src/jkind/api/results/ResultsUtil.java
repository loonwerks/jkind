package jkind.api.results;

public class ResultsUtil {
	public static MultiStatus getMultiStatus(AnalysisResult result) {
		if (result instanceof CompositeAnalysisResult) {
			return ((CompositeAnalysisResult) result).getMultiStatus();
		} else if (result instanceof JKindResult) {
			return ((JKindResult) result).getMultiStatus();
		} else {
			return null;
		}
	}
}
