package hmb.com.tr.mulakat.user.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import hmb.com.tr.mulakat.enums.Gender;
import hmb.com.tr.mulakat.lookups.entity.LookupUserStatus;
import lombok.EqualsAndHashCode;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode
public class AppUser {
	// sample data : {"id":1910,"name":"Mahmudul
	// Hasan","email":"john.doe+730865@tsh.io","gender":"male","status":"active"}
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(length = 255)
	private String name;

	@Column(unique = true, length = 255)
	private String email;

	private String password;

	private Gender gender;
	public AppUser() {

	}
	public AppUser(Long id, String name, String email, String password,
			Gender gender, LookupUserStatus status, Set<Todo> todos) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.status = status;
		this.todos = todos;
	}
	@ManyToOne
	@EqualsAndHashCode.Exclude
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_USER_STATUS"))
	private LookupUserStatus status;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	@JsonIgnoreProperties({"user"})
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_APP_USER_TODO"))
	private Set<Todo> todos;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public LookupUserStatus getStatus() {
		return status;
	}
	public void setStatus(LookupUserStatus status) {
		this.status = status;
	}

	public Set<Todo> getTodos() {
		return todos;
	}
	public void setTodos(Set<Todo> todos) {
		this.todos = todos;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
