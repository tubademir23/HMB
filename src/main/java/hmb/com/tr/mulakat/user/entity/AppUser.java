package hmb.com.tr.mulakat.user.entity;

import java.util.Set;

import javax.persistence.CascadeType;
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
	private String name;
	private String email;
	private Gender gender;

	@ManyToOne
	@EqualsAndHashCode.Exclude
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_USER_STATUS"))
	private LookupUserStatus status;
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

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	@JsonIgnoreProperties({"user"})
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_APP_USER_TODO"))
	private Set<Todo> todos;
	public Set<Todo> getTodos() {
		return todos;
	}
	public void setTodos(Set<Todo> todos) {
		this.todos = todos;
	}

}
