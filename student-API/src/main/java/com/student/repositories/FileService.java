package com.student.repositories;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.models.BaseEntity;
import com.student.models.Files;
import com.student.models.Files.FileType;
import com.student.models.Project;
import com.student.models.Student;
import com.student.utils.Utils;

@Service
public class FileService {

    @Autowired
    FilesRepository mFileRepositoy;
    @Autowired
    ProjectRepositoy mProjectRepository;

    /**
     * Get end point at which the file to be store. Create dir for student if not
     * exists Endpoint could be remote location
     * 
     * @param baseEntity
     * @param fileType
     * @return
     */
    public String getFileEndPoint(BaseEntity baseEntity, FileType fileType) {
	String endPoint = "";
	try {

	    if (baseEntity instanceof Student) {
		Student student = (Student) baseEntity;
		endPoint = String.format(Files.PROJECT_FILE_ENDPOINT_FORMAT, student.getId(), fileType);

		Path path = Paths.get(endPoint);
		if (!java.nio.file.Files.exists(path))
		    java.nio.file.Files.createDirectories(path);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return endPoint;
    }

    public Project createPhoto(Project project) {
	Files file = createPhoto(project.getBase64encoding(), project.getStudent());
	if (file != null)
	    project.setPhoto(file);

	return mProjectRepository.save(project);
    }

    /**
     * TODO: Extension validation logic
     * 
     * @param base64Data
     * @param project
     * @return
     */
    public Files createPhoto(String base64String, BaseEntity entity) {
	// TODO: Before update, replace old file with new file
	if (base64String == null || base64String.isBlank())
	    return null;

	OutputStream outputStream = null;
	Files uploadedFile = null;

	try {
	    String extension = Utils.getExtension(base64String);
	    FileType fileType = getFileType(extension);

	    if (fileType == null)
		return null;

	    String endPoint = getFileEndPoint(entity, fileType);
	    String name = Utils.getTimestamp() + "." + extension;
	    endPoint += File.separator + name;
	    byte[] byteData = Utils.decodeBase64(base64String);
	    File file = new File(endPoint);
	    outputStream = new BufferedOutputStream(new FileOutputStream(file));
	    outputStream.write(byteData);
	    outputStream.flush();
	    outputStream.close();

	    uploadedFile = new Files();
	    uploadedFile.setContent(fileType.getTag() + "/" + extension);
	    uploadedFile.setEndPoint(endPoint);
	    uploadedFile.setFileType(fileType);
	    uploadedFile.setName(name);
	    mFileRepositoy.save(uploadedFile);

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return uploadedFile;
    }

    /**
     * Find file type corresponding to supported extensions
     * 
     * @param fileExtension
     * @return
     */
    private FileType getFileType(String fileExtension) {
	if (FileType.IMAGE.getSupportedExtensions().contains(fileExtension))
	    return FileType.IMAGE;
	return null;
    }
}
