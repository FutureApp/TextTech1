package texttechno.task3.ortograph.newImp;

public class ExpectedRate {

	Integer Yw1Yw2;
	Integer Yw1Nw2;
	Integer Nw1Yw2;
	Integer Nw1Nw2;

	public ExpectedRate(Integer yw1Yw2, Integer yw1Nw2, Integer nw1Yw2, Integer nw1Nw2) {
		super();
		Yw1Yw2 = yw1Yw2;
		Yw1Nw2 = yw1Nw2;
		Nw1Yw2 = nw1Yw2;
		Nw1Nw2 = nw1Nw2;
	}

	public Integer getYw1Yw2() {
		return Yw1Yw2;
	}

	public void setYw1Yw2(Integer yw1Yw2) {
		Yw1Yw2 = yw1Yw2;
	}

	public Integer getYw1Nw2() {
		return Yw1Nw2;
	}

	public void setYw1Nw2(Integer yw1Nw2) {
		Yw1Nw2 = yw1Nw2;
	}

	public Integer getNw1Yw2() {
		return Nw1Yw2;
	}

	public void setNw1Yw2(Integer nw1Yw2) {
		Nw1Yw2 = nw1Yw2;
	}

	public Integer getNw1Nw2() {
		return Nw1Nw2;
	}

	public void setNw1Nw2(Integer nw1Nw2) {
		Nw1Nw2 = nw1Nw2;
	}

}
