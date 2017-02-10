package texttechno.task3.ortograph.newImp;

import java.util.ArrayList;

public class FloatSignature {

	Float field00, field01, field10, field11;

	
	/**
	 * Holder-Class for floate based signatures.
	 * @param field00
	 * @param field01
	 * @param field10
	 * @param field11
	 */
	public FloatSignature(Float field00, Float field01, Float field10, Float field11) {
		super();
		this.field00 = field00;
		this.field01 = field01;
		this.field10 = field10;
		this.field11 = field11;
	}

	/**
	 * Returns field 00
	 * @return Value of field 00. 
	 */
	public Float getField00() {
		return field00;
	}

	/**
	 * Sets field 00.
	 * @param field00 New Value of field 00.
	 */
	public void setField00(Float field00) {
		this.field00 = field00;
	}

	/**
	 * Returns field 01.
	 * @return Value of field 01
	 */
	public Float getField01() {
		return field01;
	}

	/**
	 * Sets value of field 01
	 * @param field01 New value of field 01.
	 */
	public void setField01(Float field01) {
		this.field01 = field01;
	}

	/**
	 * Returns value of field 10
	 * @return Value of field 10.
	 */
	public Float getField10() {
		return field10;
	}

	/**
	 * Sets value of field 10.
	 * @param field10 New Value of field 10
	 */
	public void setField10(Float field10) {
		this.field10 = field10;
	}

	/**
	 * Returns value of field 11
	 * @return Value of field 11
	 */
	public Float getField11() {
		return field11;
	}

	/**
	 * Sets the value of field 11
	 * @param field11 New value of field 11
	 */
	public void setField11(Float field11) {
		this.field11 = field11;
	}

	
	/**
	 * Returns all fields in a list. Order 00,01,10,11
	 * @return All field-values as a list.
	 */
	public ArrayList<Float> getValuesAsList() {
		ArrayList<Float> result = new ArrayList<>();
		result.add(field00);
		result.add(field01);
		result.add(field10);
		result.add(field11);
		return result;
	}

}
