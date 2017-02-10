package texttechno.task3.ortograph.newImp;

import java.util.ArrayList;

public class FloatSignature {

	Float field00, field01, field10, field11;

	public FloatSignature(Float field00, Float field01, Float field10, Float field11) {
		super();
		this.field00 = field00;
		this.field01 = field01;
		this.field10 = field10;
		this.field11 = field11;
	}

	public Float getField00() {
		return field00;
	}

	public void setField00(Float field00) {
		this.field00 = field00;
	}

	public Float getField01() {
		return field01;
	}

	public void setField01(Float field01) {
		this.field01 = field01;
	}

	public Float getField10() {
		return field10;
	}

	public void setField10(Float field10) {
		this.field10 = field10;
	}

	public Float getField11() {
		return field11;
	}

	public void setField11(Float field11) {
		this.field11 = field11;
	}

	public ArrayList<Float> getValuesAsList() {
		ArrayList<Float> result = new ArrayList<>();
		result.add(field00);
		result.add(field01);
		result.add(field10);
		result.add(field11);
		return result;
	}

}
