
package forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

public class GovernmentAgentForm {

	private int		id;
	private String	name;
	private String	surname;
	private String	email;
	private String	phone;
	private String	address;
	private String	nif;
	private String	nickname;
	private String	username;
	private String	password;
	private String	repeatPassword;
	private String	registerCode;
	private boolean	canCreateMoney;
	private boolean	canOrganiseElection;
	private boolean	termsAndConditions;


	public GovernmentAgentForm() {
		super();
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(final String nickname) {
		this.nickname = nickname;
	}

	@Email
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNif() {
		return this.nif;
	}

	public void setNif(final String nif) {
		this.nif = nif;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Pattern(regexp = "^[a-zA-Z]{6}$")
	public String getRegisterCode() {
		return this.registerCode;
	}

	public void setRegisterCode(final String registerCode) {
		this.registerCode = registerCode;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getRepeatPassword() {
		return this.repeatPassword;
	}
	public void setRepeatPassword(final String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public Boolean getTermsAndConditions() {
		return this.termsAndConditions;
	}

	public void setTermsAndConditions(final Boolean termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	public Boolean getCanCreateMoney() {
		return this.canCreateMoney;
	}

	public void setCanCreateMoney(final Boolean canCreateMoney) {
		this.canCreateMoney = canCreateMoney;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public boolean getCanOrganiseElection() {
		return this.canOrganiseElection;
	}

	public void setCanOrganiseElection(final Boolean canOrganiseElection) {
		this.canOrganiseElection = canOrganiseElection;
	}

}
