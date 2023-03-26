package org.app.model;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.logging.Log;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.sql.Date;

@Entity
public class Job extends PanacheEntity {


    private int jobId;
    private String type;
    private Date created;
    private int organizationId;
    private String organizationName;
    private int inventoryId;
    private String inventoryName;
    private String launchType;
    private String status;
    private String name;
    private Date started;
    private Date finished;
    private Float elapsed;


    @Transient
    private boolean isPersistable;

    private String failed;

    public Job() {
    }

    public Job(String jobLine) {
        String[] fields = jobLine.split(",");
        this.isPersistable = true;
        this.jobId = Integer.parseInt(fields[JobFields.ID.ordinal()]);
        this.name = fields[JobFields.NAME.ordinal()];
        this.type = fields[JobFields.MODEL.ordinal()];
        this.organizationId = Integer.parseInt(fields[JobFields.ORGANIZATION_ID.ordinal()]);
        this.organizationName = fields[JobFields.ORGANIZATION_NAME.ordinal()];
        this.created = trimDate(fields[JobFields.CREATED.ordinal()]);
        this.organizationId = Integer.parseInt(fields[JobFields.ORGANIZATION_ID.ordinal()]);
        this.organizationName = fields[JobFields.ORGANIZATION_NAME.ordinal()];
        this.launchType = fields[JobFields.LAUNCH_TYPE.ordinal()];
        this.status = fields[JobFields.STATUS.ordinal()];
        this.started = trimDate(fields[JobFields.STARTED.ordinal()]);
        this.finished = trimDate(fields[JobFields.FINISHED.ordinal()]);
        this.elapsed = Float.parseFloat(fields[JobFields.ELAPSED.ordinal()]);
        this.failed = fields[JobFields.FAILED.ordinal()];
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public String getLaunchType() {
        return launchType;
    }

    public void setLaunchType(String launchType) {
        this.launchType = launchType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public Date getFinished() {
        return finished;
    }

    public void setFinished(Date finished) {
        this.finished = finished;
    }

    public Float getElapsed() {
        return elapsed;
    }

    public void setElapsed(Float elapsed) {
        this.elapsed = elapsed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFailed() {
        return failed;
    }

    public void setFailed(String failed) {
        this.failed = failed;
    }

    public boolean isPersistable() {
        return isPersistable;
    }

    public void setPersistable(boolean persistable) {
        isPersistable = persistable;
    }


    private Date trimDate(String dateString) {
        //2023-02-10 19:40:59.200783+00 to yyyy-mm-dd
        String[] splittedDate = dateString.split(" ");
        if (splittedDate[0].equals("")) {
            this.isPersistable = false;
            Log.info("Dropping job because is not terminated yet.");
            return null;
        }
        try {
            return Date.valueOf(splittedDate[0]);
        } catch (IllegalArgumentException e) {
            Log.error(String.format("Got Date %s", splittedDate[0]));
            e.printStackTrace();
        }
        return null;
    }

}
