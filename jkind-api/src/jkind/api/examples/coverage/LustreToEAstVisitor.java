package jkind.api.examples.coverage;

import jkind.lustre.Location;
import jkind.lustre.parsing.LustreToAstVisitor;

import org.antlr.v4.runtime.ParserRuleContext;

public class LustreToEAstVisitor extends LustreToAstVisitor {
	@Override
	protected Location loc(ParserRuleContext ctx) {
		Location base = super.loc(ctx);
		int start = ctx.getStart().getStartIndex();
		int stop = ctx.getStop().getStopIndex();
		return new ELocation(base.line, base.charPositionInLine, start, stop);
	}
}
