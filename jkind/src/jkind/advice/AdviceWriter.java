package jkind.advice;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import jkind.ExitCodes;
import jkind.JKindException;
import jkind.StdErr;
import jkind.lustre.Expr;
import jkind.lustre.VarDecl;

public class AdviceWriter {
	private Writer out;
	private final Advice advice = new Advice();

	public AdviceWriter(String outputFilename) {
		try {
			OutputStream stream = new GZIPOutputStream(new FileOutputStream(outputFilename));
			this.out = new BufferedWriter(new OutputStreamWriter(stream));
		} catch (IOException e) {
			StdErr.fatal(ExitCodes.INVALID_OPTIONS, "unable to open advice file for writing: " + e.getMessage());
		}
	}

	public void addVarDecls(List<VarDecl> varDecls) {
		advice.addVarDecls(varDecls);
	}

	public void addInvariants(List<Expr> invariants) {
		advice.addInvariants(invariants);
	}

	public void write() {
		try {
			out.write(AdviceEncoder.encode(advice));
			out.close();
		} catch (IOException e) {
			throw new JKindException("Unable to write advice file", e);
		}
	}
}
