package ru.korotkov.bot;

public class ModelHeadHunter {
    private String id;
    private String nameVakanse;
    private String nameCiti;
    private String salaryFrom;
    private String salaryTo;
    private String salaryCurrency;
    private String nameEmployer;
    private String published_at;
    private String requirement;
    private String responsibility;

    public String getNameVakanse() {
        return nameVakanse;
    }

    public void setNameVakanse(String nameVakanse) {
        this.nameVakanse = nameVakanse;
    }

    public String getNameCiti() {
        return nameCiti;
    }

    public void setNameCiti(String nameCiti) {
        this.nameCiti = nameCiti;
    }

    public String getSalaryFrom() {
        return salaryFrom;
    }

    public void setSalaryFrom(String salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public String getSalaryTo() {
        return salaryTo;
    }

    public void setSalaryTo(String salaryTo) {
        this.salaryTo = salaryTo;
    }

    public String getSalaryCurrency() {
        return salaryCurrency;
    }

    public void setSalaryCurrency(String salaryCurrency) {
        this.salaryCurrency = salaryCurrency;
    }

    public String getNameEmployer() {
        return nameEmployer;
    }

    public void setNameEmployer(String nameEmployer) {
        this.nameEmployer = nameEmployer;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at.substring(0, 10);
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
