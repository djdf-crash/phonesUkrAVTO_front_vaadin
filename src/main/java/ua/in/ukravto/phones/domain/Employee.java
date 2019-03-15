package ua.in.ukravto.phones.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Employee {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("post")
    @Expose
    private String post;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone_mobile")
    @Expose
    private String phoneMobile;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("organization_name")
    @Expose
    private String organizationName;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("organization_id")
    @Expose
    private Integer organizationId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("last_update")
    @Expose
    private String lastUpdate;
    @SerializedName("delete")
    @Expose
    private Boolean delete;

    public Integer getiD() {
        return iD;
    }

    public void setiD(Integer iD) {
        this.iD = iD;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneMobile() {
        return phoneMobile;
    }

    public void setPhoneMobile(String phoneMobile) {
        this.phoneMobile = phoneMobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }
}
