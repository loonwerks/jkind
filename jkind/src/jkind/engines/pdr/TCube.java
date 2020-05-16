package jkind.engines.pdr;

public class TCube implements Comparable<TCube> {
	public static final int FRAME_NULL = -1;
	public static final int FRAME_INF = Integer.MAX_VALUE;

	private final Cube cube;
	private int frame;

	public TCube(Cube cube, int frame) {
		this.cube = cube;
		this.frame = frame;
	}

	public Cube getCube() {
		return cube;
	}

	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		assert (this.frame == FRAME_NULL);
		this.frame = frame;
	}

	public TCube next() {
		return new TCube(cube, frame + 1);
	}

	@Override
	public int compareTo(TCube other) {
		assert (frame != FRAME_NULL);
		assert (frame != FRAME_INF);
		assert (other.frame != FRAME_NULL);
		assert (other.frame != FRAME_INF);

		return frame - other.frame;
	}

	@Override
	public String toString() {
		return frame + ": " + cube;
	}
}
