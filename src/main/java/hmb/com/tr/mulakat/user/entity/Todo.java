package hmb.com.tr.mulakat.user.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import hmb.com.tr.mulakat.lookups.entity.LookupTodoStatus;
import lombok.EqualsAndHashCode;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode
public class Todo implements Serializable {

	// sample data:{"id":218,"user_id":127,"title":"Demoror adeptio subvenio
	// demergo
	// cado.","due_on":"2022-01-03T00:00:00.000+05:30","status":"pending"}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String title;

	private Date dueOn;

	@ManyToOne
	@EqualsAndHashCode.Exclude
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_TODO_STATUS"))
	private LookupTodoStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_APP_USER_TODO"))
	AppUser user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDueOn() {
		return dueOn;
	}

	public void setDueOn(Date dueOn) {
		this.dueOn = dueOn;
	}

	public LookupTodoStatus getStatus() {
		return status;
	}

	public void setStatus(LookupTodoStatus status) {
		this.status = status;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

}
