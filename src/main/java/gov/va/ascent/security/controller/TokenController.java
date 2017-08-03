package gov.va.ascent.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gov.va.ascent.security.jwt.PersonTraits;
import gov.va.ascent.security.util.GenerateToken;

/**
 * Created by vgadda on 5/15/17.
 */
@Controller
public class TokenController{
	@RequestMapping(value = "/token", method = RequestMethod.GET)
	public String getTokenForm(Model model){
		model.addAttribute("person", new PersonDto());
		return "tokenform";
	}

	@RequestMapping(value = "/tokenform", method = RequestMethod.POST, consumes = {"application/x-www-form-urlencoded"})
	public String getToken(@ModelAttribute PersonDto personDto, Model model){
		PersonTraits person = new PersonTraits();
		person.setDodedipnid(personDto.getEdi());
		person.setPid(personDto.getId());
		person.setFirstName(personDto.getFirstName());
		person.setLastName(personDto.getLastName());
		person.setMiddleName(personDto.getMiddleName());
		model.addAttribute("token", GenerateToken.generateJwt(person));
		return "token";
	}

}

class PersonDto{
	protected String username;
	protected String id;
	protected String edi;
	protected String firstName;
	protected String middleName;
	protected String lastName;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEdi() {
		return edi;
	}

	public void setEdi(String edi) {
		this.edi = edi;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
