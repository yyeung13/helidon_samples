package io.helidon.yyeung.examples.apifirst.se;

public class Promotion implements java.io.Serializable {


	private String m_name;
	private String m_code;
	private String m_validFrom;
	private String m_validTo;
	private String m_description;
	private String m_discount;

	public Promotion(String name, String code, String validFrom, String validTo, String description, String discount) {
		m_name = name;
		m_code = code;
		m_validFrom = validFrom;
		m_validTo = validTo;
		m_description = description;
		m_discount = discount;
	}
	public void setName(String name) {
		m_name = name;
	}
	public String getName() {
		return m_name;
	}
	public void setCode(String code) {
		m_code = code;
	}
	public String getCode() {
		return m_code;
	}
	public void setValidFrom(String validFrom) {
		m_validFrom = validFrom;
	}
	public String getValidFrom() {
		return m_validFrom;
	}
	public void setValidTo(String validTo) {
		m_validTo = validTo;
	}
	public String getValidTo() {
		return m_validTo;
	}
	public void setDescription(String description) {
		m_description = description;
	}
	public String getDescription() {
		return m_description;
	}
	public void setDiscount(String discount) {
		m_discount = discount;
	}
	public String getDiscount() {
		return m_discount;
	}

}
