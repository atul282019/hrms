package com.cotodel.hrms.web.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
	private int orgId;
    private int employerId;
    private String createdBy;
    private List<UserDTO> userDTO;

    // Getters and Setters
    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<UserDTO> getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(List<UserDTO> userDTO) {
        this.userDTO = userDTO;
    }
}
