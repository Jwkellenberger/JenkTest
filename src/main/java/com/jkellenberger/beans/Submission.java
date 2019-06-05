package com.jkellenberger.beans;

import java.time.LocalDateTime;

public class Submission {
	private int id;
	private String name;
	private int typeId;
	private String type;
	private float reimbPercent;
	private String status;
	private String info;
	private String dateTimeStr;
	private LocalDateTime dateTimeDt;
	private String location;
	private String workJustification;
	private int gradingTypeId;
	private int timeMissed;
	private float amount;
	private int submitterId;
	private Employee submitter;
	private int firstManagerId;
	private Employee firstManager;
	private int secondManagerId;
	private Employee secondManager;
	private int benCoId;
	private Employee benCo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDateTimeStr() {
		return dateTimeStr;
	}
	public void setDateTimeStr(String dateTimeStr) {
		this.dateTimeStr = dateTimeStr;
	}
	public LocalDateTime getDateTimeDt() {
		return dateTimeDt;
	}
	public void setDateTimeDt(LocalDateTime dateTimeDt) {
		this.dateTimeDt = dateTimeDt;
	}
	public int getSubmitterId() {
		return submitterId;
	}
	public void setSubmitterId(int submitterId) {
		this.submitterId = submitterId;
	}
	public int getFirstManagerId() {
		return firstManagerId;
	}
	public void setFirstManagerId(int firstManagerId) {
		this.firstManagerId = firstManagerId;
	}
	public int getSecondManagerId() {
		return secondManagerId;
	}
	public void setSecondManagerId(int secondManagerId) {
		this.secondManagerId = secondManagerId;
	}
	public int getBenCoId() {
		return benCoId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getWorkJustification() {
		return workJustification;
	}
	public void setWorkJustification(String workJustification) {
		this.workJustification = workJustification;
	}
	public int getGradingTypeId() {
		return gradingTypeId;
	}
	public void setGradingTypeId(int gradingTypeId) {
		this.gradingTypeId = gradingTypeId;
	}
	public int getTimeMissed() {
		return timeMissed;
	}
	public void setTimeMissed(int timeMissed) {
		this.timeMissed = timeMissed;
	}
	public void setBenCoId(int benCoId) {
		this.benCoId = benCoId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getReimbPercent() {
		return reimbPercent;
	}
	public void setReimbPercent(float reimbPercent) {
		this.reimbPercent = reimbPercent;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public Employee getSubmitter() {
		return submitter;
	}
	public void setSubmitter(Employee submitter) {
		this.submitter = submitter;
	}
	public Employee getFirstManager() {
		return firstManager;
	}
	public void setFirstManager(Employee firstManager) {
		this.firstManager = firstManager;
	}
	public Employee getSecondManager() {
		return secondManager;
	}
	public void setSecondManager(Employee secondManager) {
		this.secondManager = secondManager;
	}
	public Employee getBenCo() {
		return benCo;
	}
	public void setBenCo(Employee benCo) {
		this.benCo = benCo;
	}
	@Override
	public String toString() {
		return "Submission [id=" + id + ", name=" + name + ", typeId=" + typeId + ", type=" + type + ", reimbPercent="
				+ reimbPercent + ", status=" + status + ", info=" + info + ", dateTimeStr=" + dateTimeStr
				+ ", dateTimeDt=" + dateTimeDt + ", location=" + location + ", workJustification=" + workJustification
				+ ", gradingTypeId=" + gradingTypeId + ", timeMissed=" + timeMissed + ", amount=" + amount
				+ ", submitterId=" + submitterId + ", submitter=" + submitter + ", firstManagerId=" + firstManagerId
				+ ", firstManager=" + firstManager + ", secondManagerId=" + secondManagerId + ", secondManager="
				+ secondManager + ", benCoId=" + benCoId + ", benCo=" + benCo + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(amount);
		result = prime * result + ((benCo == null) ? 0 : benCo.hashCode());
		result = prime * result + ((firstManager == null) ? 0 : firstManager.hashCode());
		result = prime * result + id;
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(reimbPercent);
		result = prime * result + ((secondManager == null) ? 0 : secondManager.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((submitter == null) ? 0 : submitter.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + typeId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Submission other = (Submission) obj;
		if (Float.floatToIntBits(amount) != Float.floatToIntBits(other.amount))
			return false;
		if (benCo == null) {
			if (other.benCo != null)
				return false;
		} else if (!benCo.equals(other.benCo))
			return false;
		if (firstManager == null) {
			if (other.firstManager != null)
				return false;
		} else if (!firstManager.equals(other.firstManager))
			return false;
		if (id != other.id)
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Float.floatToIntBits(reimbPercent) != Float.floatToIntBits(other.reimbPercent))
			return false;
		if (secondManager == null) {
			if (other.secondManager != null)
				return false;
		} else if (!secondManager.equals(other.secondManager))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (submitter == null) {
			if (other.submitter != null)
				return false;
		} else if (!submitter.equals(other.submitter))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (typeId != other.typeId)
			return false;
		return true;
	}
	
	
}
