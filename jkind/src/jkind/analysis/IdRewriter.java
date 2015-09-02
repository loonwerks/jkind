package jkind.analysis;

import jkind.lustre.Expr;
import jkind.lustre.IdExpr;

public interface IdRewriter {
    public Expr rewrite(IdExpr id);
}
