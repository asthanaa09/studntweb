package com.student.models;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;

import com.student.utils.Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO: Validate it later
 * 
 * @author Anurag Asthana
 *
 */
@Entity
@Setter
@Getter
public class Files extends BaseEntity {

    /**
     * Format to set file endpoint - projects/[STUDENT_ID]/[FILE_TYPE]/
     * 
     */
    public static final String PROJECT_FILE_ENDPOINT_FORMAT = "E:\\projects\\%s\\%s";

    /**
     * TODO: Add validation attributes e.g Max size, sequence etc.
     *
     */
    @AllArgsConstructor
    public enum FileType {
	IMAGE("img", Arrays.asList("jpg", "jpeg", "png"));

	String tag;
	List<String> supportedExtensions;

	public String getTag() {
	    return tag;
	}

	public List<String> getSupportedExtensions() {
	    return supportedExtensions;
	}
    }

    @Column(name = "name")
    String name;

    @Column(name = "content")
    String content;

    @Column(name = "file_type")
    FileType fileType;

    @Column(name = "endpoint")
    String endPoint;

    @Column(name = "update_time")
    Date updateTime;

    @PrePersist
    public void onUpdate() {
	this.setUpdateTime(Utils.now());
    }
}
