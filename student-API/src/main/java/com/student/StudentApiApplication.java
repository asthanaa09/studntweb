package com.student;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.student.repositories.FileService;
import com.student.repositories.FilesRepository;
import com.student.repositories.StudentRepository;
import com.student.service.StudentService;

@SpringBootApplication
public class StudentApiApplication {

    @Autowired
    StudentRepository s;
    @Autowired
    StudentService studentService;
    @Autowired
    FileService fileService;
    @Autowired
    FilesRepository fR;

    /**
     * After initialization of Bean set time zone application lavel
     */
    @PostConstruct
    public void setTimeZone() {
	System.out.println("TODO: Set time zone");
    }

    public static void main(String[] args) {
	SpringApplication.run(StudentApiApplication.class, args);
    }

    /**
     * Ref: https://stacktraceguru.com/springboot/run-method-on-startup
     */
    /*
     * @EventListener(ApplicationReadyEvent.class) private void createStudent() { //
     * List<Student> students = Stream.of( // new Student("Anurag", null, "Asthana",
     * "1234567890", "anc@gmail,com", null, "1233433242", null, null, null), // new
     * Student("Kapil", null, "Kumar", "1212123213", "asasa@gmail.com", null,
     * "343424242", null, null, null) // ).collect(Collectors.toList()); // //
     * s.saveAll(students);
     * 
     * try { Student student = s.findById(1l).orElseThrow(() -> new
     * RuntimeException("Failed to fetch student")); File file = new
     * File("C:\\Users\\user\\Pictures\\Saved Pictures\\tree.jpg");
     * System.out.println(file.getName());
     * 
     * Project project = new Project(); project.setName("HTML Layout");
     * project.setDuration(5); project.setId(1l);
     * project.setCreationTime(Utils.now());
     * 
     * Files files = new Files(); files.setName("Html Layout");
     * files.setContent("Img/JPG"); files.setFileType(Files.FileType.IMAGE);
     * 
     * String endPoint = fileService.getFileEndPoint(student, files.getFileType());
     * System.out.println("End Point : " + endPoint); File savedFile = new
     * File(endPoint + File.separator + file.getName());
     * if(savedFile.createNewFile()) System.out.println("File Created");
     * 
     * files.setEndPoint(savedFile.getAbsolutePath()); files = fR.save(files);
     * System.out.println("____________File id " + files.getId());
     * project.setPhoto(files); studentService.insert(project, 1l);
     * 
     * } catch (Exception e) { e.printStackTrace(); } }
     */
}
